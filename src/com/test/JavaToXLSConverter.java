package com.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import ebay.apis.eblbasecomponents.GetSellerTransactionsResponse;
import ebay.apis.eblbasecomponents.GetSellerTransactionsResponse.TransactionArray.Transaction;
import ebay.apis.eblbasecomponents.GetSellerTransactionsResponse.TransactionArray.Transaction.Buyer.BuyerInfo.ShippingAddress;

public class JavaToXLSConverter {

	private HSSFWorkbook workbook = new HSSFWorkbook();
	private HSSFSheet sheet = workbook.createSheet("InvoiceTransactions");
	private int rownum = 0;

	public HSSFWorkbook convertToSpreadsheet(
			GetSellerTransactionsResponse invoiceData) {		

		List<Transaction> transactionList = invoiceData.getTransactionArray()
				.getTransaction();
		int sno = 1;

		Map<String, Object[]> header = new HashMap();
		Map<String, Object[]> data = new HashMap();

		header.put(String.valueOf(sno++), new Object[] { "Buyer Name",
				"UserID", "Transaction ID", "ItemID", "Item Price",
				"Shipping Price", "Shipping address name", "Street1",
				"Street2", "CityName", "StateOrProvince", "CountryName",
				"Phone", "PostalCode", "QuantityPurchased", "AmountPaid" });

		for (Transaction transaction : transactionList) {

			ShippingAddress shippingAddr = transaction.getBuyer()
					.getBuyerInfo().getShippingAddress();

			data.put(
					String.valueOf(sno++),
					new Object[] {
							transaction.getBuyer().getUserFirstName() + " "
									+ transaction.getBuyer().getUserLastName(),
							transaction.getBuyer().getUserID(),
							transaction.getTransactionID(),
							transaction.getItem().getItemID(),
							transaction.getTransactionPrice().getValue(),
							transaction.getActualShippingCost().getValue(),
							shippingAddr.getName(), shippingAddr.getStreet1(),
							shippingAddr.getStreet2(),
							shippingAddr.getCityName(),
							shippingAddr.getStateOrProvince(),
							shippingAddr.getCountryName(),
							shippingAddr.getPhone(),
							shippingAddr.getPostalCode(),
							transaction.getQuantityPurchased(),
							transaction.getAmountPaid().getValue()});
		}

		Set<String> headerKeyset = header.keySet();
		for (String key : headerKeyset) {
			Object[] objArr = header.get(key);
			writeToCell(objArr);
		}
		Set<String> keyset = data.keySet();
		for (String key : keyset) {
			Object[] objArr = data.get(key);
			writeToCell(objArr);
		}

		return workbook;
	}

	private void writeToCell(Object[] dataRow) {

		
		Row row = sheet.createRow(rownum++);

		int cellnum = 0;
		for (Object obj : dataRow) {
			Cell cell = row.createCell(cellnum++);
			if (obj instanceof Date)
				cell.setCellValue((Date) obj);
			else if (obj instanceof Boolean)
				cell.setCellValue((Boolean) obj);
			else if (obj instanceof String)
				cell.setCellValue((String) obj);
			else if (obj instanceof Long)
				cell.setCellValue((Long) obj);
			else if (obj instanceof Double)
				cell.setCellValue((Double) obj);
			else if (obj instanceof Float)
				cell.setCellValue((Float) obj);
			else if (obj instanceof Byte)
				cell.setCellValue((Byte) obj);
		}

	}

}
