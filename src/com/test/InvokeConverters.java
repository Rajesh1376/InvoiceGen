package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ebay.apis.eblbasecomponents.GetSellerTransactionsResponse;
import ebay.apis.eblbasecomponents.GetSellerTransactionsResponse.TransactionArray.Transaction;

public class InvokeConverters {

	public static void main(String args[]) {

		XMLUnMarshaller xmlUnmarshaller = new XMLUnMarshaller();
		GetSellerTransactionsResponse invoiceData = xmlUnmarshaller
				.unMarshallData();
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		Calendar cal = Calendar.getInstance();
		
		JavaToXLSConverter javaToXls = new JavaToXLSConverter();
		HSSFWorkbook workbook = javaToXls.convertToSpreadsheet(invoiceData);
		
		try {
			FileOutputStream out = 
					new FileOutputStream(new File("C://PDF/Invoices_" +dateFormat.format(cal.getTime()))+ ".xls");
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (invoiceData != null && invoiceData.getTransactionArray() != null
				&& invoiceData.getTransactionArray().getTransaction() != null
				&& invoiceData.getTransactionArray().getTransaction().size() > 0) {

			List<Transaction> transactionList = invoiceData.getTransactionArray()
					.getTransaction();

			int i = 0;
			for (Transaction transaction : transactionList) {
				PDFGenerator pdfGen = new PDFGenerator();
				pdfGen.generatePDF(transaction);
				i++;
				if(i==1){
					break;
				}
				
			}
		}
	}
}
