package com.test;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ebay.apis.eblbasecomponents.GetSellerTransactionsResponse.TransactionArray.Transaction;
import ebay.apis.eblbasecomponents.GetSellerTransactionsResponse.TransactionArray.Transaction.Buyer.BuyerInfo.ShippingAddress;

public class PDFGenerator {

	public void generatePDF(Transaction sellerTransaction) {

		final String THANK_YOU_MSG = "Thank you for your business. Please treat this invoice as bill and warranty for 1 year. Please verify it thoroughly once you receive this item.";
		final String SIGNATURE = "Thanks & Regards,";
		final String COMPANY = "Pickncart";
		final String INR_CODE = "Rs. ";
		final int POSTAGE_CHARGE = 100;
		final String PAYMENT_METHOD = "BANK TRANSFER";

		Document document = new Document(PageSize.A4);
		PdfWriter docWriter = null;

		String buyerName = sellerTransaction.getBuyer().getBuyerInfo()
				.getShippingAddress().getName();
		String pdfName = buyerName + "_" + sellerTransaction.getTransactionID()
				+ ".pdf";
		File pdfFile = new File("C:/PDF/invoices/" + pdfName);

		try {

			FileOutputStream fos = new FileOutputStream(pdfFile);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			docWriter = PdfWriter.getInstance(document, baos);
			document.open();

			Image image = Image.getInstance("C:/PDF/pickncart.png");
			image.setAlignment(Image.MIDDLE);
			float documentWidth = (document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin()) / 2;
			image.scaleToFit(documentWidth, image.getHeight());
			document.add(image);
			document.add(Chunk.NEWLINE);

			Paragraph invoiceHeader = new Paragraph();
			Font hdrFont = new Font();
			hdrFont.setSize(15);
			hdrFont.setStyle(Font.BOLD);
			invoiceHeader.setFont(hdrFont);
			invoiceHeader.add("Invoice");
			invoiceHeader.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(invoiceHeader);

			document.add(Chunk.NEWLINE);

			PdfPTable sellerTable = new PdfPTable(2);
			Phrase p = new Phrase();
			Chunk sellerName = new Chunk("L. Rajeshwar Rao" + "\n", new Font(
					null, 10, Font.BOLD, Color.black));
			Chunk mobile = new Chunk("Mobile: 9949303336" + "\n", new Font(
					null, 10, Font.NORMAL, Color.black));
			Chunk emailAddress = new Chunk("Rajesh1376@gmail.com" + "\n",
					new Font(null, 10, Font.NORMAL, Color.black));
			Chunk address1 = new Chunk("Flat No:1302, Pearl Block," + "\n",
					new Font(null, 10, Font.NORMAL, Color.black));
			Chunk address2 = new Chunk("My Home Jewel Apartments," + "\n",
					new Font(null, 10, Font.NORMAL, Color.black));
			Chunk address3 = new Chunk("Madinaguda, HYD – 500049" + "\n",
					new Font(null, 10, Font.NORMAL, Color.black));

			p.add(sellerName);
			p.add(mobile);
			p.add(emailAddress);
			p.add(address1);
			p.add(address2);
			p.add(address3);

			sellerTable.getDefaultCell().setBorderWidth(0);
			sellerTable.addCell(p);
			sellerTable.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_RIGHT);
			sellerTable.addCell(new Phrase());
			sellerTable.setTotalWidth(document.right() - document.left());
			sellerTable.setSpacingAfter(15f);

			document.add(sellerTable);

			PdfPTable invDetailsTable = new PdfPTable(3);
			Phrase p1 = new Phrase();
			Phrase p2 = new Phrase();
			Phrase p3 = new Phrase();
			Phrase p4 = new Phrase();
			Phrase p5 = new Phrase();
			Phrase p6 = new Phrase();

			Chunk invNoHeader = new Chunk(" INVOICE NUMBER" + "\n", new Font(
					null, 10, Font.BOLD, Color.black));
			p1.add(invNoHeader);

			Chunk dateHeader = new Chunk(" Date" + "\n", new Font(null, 10,
					Font.BOLD, Color.black));
			p2.add(dateHeader);

			Chunk descHeader = new Chunk(" Description" + "\n", new Font(null,
					10, Font.BOLD, Color.black));
			p3.add(descHeader);

			Chunk invNo = new Chunk(" "
					+ String.valueOf(sellerTransaction.getTransactionID())
					+ "\n", new Font(null, 10, Font.BOLD, Color.black));
			p4.add(invNo);

			Date transactionDate = sellerTransaction.getCreatedDate()
					.toGregorianCalendar().getTime();
			DateFormat df = new SimpleDateFormat("dd/MM/yy");
			String transactionDateStr = df.format(transactionDate);
			Chunk transDate = new Chunk(" " + transactionDateStr + "\n",
					new Font(null, 10, Font.BOLD, Color.black));
			p5.add(transDate);

			Chunk description = new Chunk(" "
					+ sellerTransaction.getItem().getItemID() + "\n", new Font(
					null, 10, Font.BOLD, Color.black));
			p6.add(description);

			PdfPCell invNoCell = new PdfPCell(p1);
			invNoCell.setBackgroundColor(new Color(250, 245, 245));
			invDetailsTable.addCell(invNoCell);
			PdfPCell invNoCellVal = new PdfPCell(p4);
			invNoCellVal.setColspan(2);
			invDetailsTable.addCell(invNoCellVal);
			invDetailsTable.completeRow();
			PdfPCell dateCell = new PdfPCell(p2);
			dateCell.setBackgroundColor(new Color(250, 245, 245));
			invDetailsTable.addCell(dateCell);
			PdfPCell dateCellVal = new PdfPCell(p5);
			dateCellVal.setColspan(2);
			invDetailsTable.addCell(dateCellVal);
			invDetailsTable.completeRow();
			PdfPCell descCell = new PdfPCell(p3);
			descCell.setBackgroundColor(new Color(250, 245, 245));
			invDetailsTable.addCell(descCell);
			PdfPCell descCellVal = new PdfPCell(p6);
			descCellVal.setColspan(2);
			invDetailsTable.addCell(descCellVal);
			invDetailsTable.completeRow();
			invDetailsTable.setSpacingAfter(15f);
			invDetailsTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			invDetailsTable.setWidthPercentage(80);
			document.add(invDetailsTable);

			PdfPTable buyerTable = new PdfPTable(2);
			Phrase p7 = new Phrase();

			ShippingAddress shipAddr = sellerTransaction.getBuyer()
					.getBuyerInfo().getShippingAddress();
			Chunk buyerHeader = new Chunk("To:" + "\n", new Font(null, 10,
					Font.BOLD, Color.black));
			p7.add(buyerHeader);

			Chunk buyerNameChk = new Chunk(buyerName + "\n", new Font(null, 10,
					Font.NORMAL, Color.black));
			p7.add(buyerNameChk);

			if (shipAddr.getStreet1() != null
					&& shipAddr.getStreet1().trim().length() > 0) {
				Chunk street1 = new Chunk(shipAddr.getStreet1() + "\n",
						new Font(null, 10, Font.NORMAL, Color.black));
				p7.add(street1);
			}

			if (shipAddr.getStreet2() != null
					&& shipAddr.getStreet2().trim().length() > 0) {
				Chunk street2 = new Chunk(shipAddr.getStreet2() + "\n",
						new Font(null, 10, Font.NORMAL, Color.black));
				p7.add(street2);
			}

			if (shipAddr.getCityName() != null
					&& shipAddr.getCityName().trim().length() > 0) {
				Chunk cityName = new Chunk(shipAddr.getCityName() + "\n",
						new Font(null, 10, Font.NORMAL, Color.black));
				p7.add(cityName);
			}

			if (shipAddr.getStateOrProvince() != null
					&& shipAddr.getStateOrProvince().trim().length() > 0) {
				Chunk state = new Chunk(shipAddr.getStateOrProvince() + "\n",
						new Font(null, 10, Font.NORMAL, Color.black));
				p7.add(state);
			}

			buyerTable.getDefaultCell().setBorderWidth(0);
			PdfPCell cell = new PdfPCell(p7);
			cell.setBackgroundColor(new Color(250, 245, 245));
			cell.setBorderWidth(0);
			buyerTable.addCell(cell);
			buyerTable.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_RIGHT);
			buyerTable.addCell(new Phrase());
			buyerTable.setTotalWidth(document.right() - document.left());
			buyerTable.setSpacingAfter(15f);
			document.add(buyerTable);

			PdfPTable itemDetailsTable = new PdfPTable(8);
			Phrase iph1 = new Phrase();
			Phrase iph2 = new Phrase();
			Phrase iph3 = new Phrase();
			Phrase iph4 = new Phrase();
			Phrase iph5 = new Phrase();
			Phrase iph6 = new Phrase();
			Phrase iph7 = new Phrase();
			Phrase ip1 = new Phrase();
			Phrase ip2 = new Phrase();
			Phrase ip3 = new Phrase();
			Phrase ip4 = new Phrase();
			Phrase ip5 = new Phrase();
			Phrase ip6 = new Phrase();
			Phrase ip7 = new Phrase();

			Chunk snoHeader = new Chunk("S.No" + "\n", new Font(null, 7,
					Font.BOLD, Color.black));
			iph1.add(snoHeader);
			Chunk itemDescHeader = new Chunk("Description" + "\n", new Font(
					null, 7, Font.BOLD, Color.black));
			iph2.add(itemDescHeader);
			Chunk qtyHeader = new Chunk("Qty" + "\n", new Font(null, 7,
					Font.BOLD, Color.black));
			iph3.add(qtyHeader);
			Chunk priceHeader = new Chunk("Price per Unit" + "\n", new Font(
					null, 7, Font.BOLD, Color.black));
			iph4.add(priceHeader);
			Chunk totalHeader = new Chunk("Total" + "\n", new Font(null, 7,
					Font.BOLD, Color.black));
			iph5.add(totalHeader);
			Chunk taxHeader = new Chunk("Tax" + "\n", new Font(null, 7,
					Font.BOLD, Color.black));
			iph6.add(taxHeader);
			Chunk grossHeader = new Chunk("Gross" + "\n", new Font(null, 7,
					Font.BOLD, Color.black));
			iph7.add(grossHeader);

			Chunk snoRow1 = new Chunk("1" + "\n", new Font(null, 9,
					Font.NORMAL, Color.black));
			ip1.add(snoRow1);
			Chunk itemDescRow1 = new Chunk(sellerTransaction.getItem()
					.getItemID() + "\n", new Font(null, 9, Font.BOLD,
					Color.black));
			ip2.add(itemDescRow1);
			Chunk qtyRow1 = new Chunk(sellerTransaction.getQuantityPurchased()
					+ "\n", new Font(null, 9, Font.NORMAL, Color.black));
			ip3.add(qtyRow1);
			String amt = INR_CODE
					+ String.valueOf((int) sellerTransaction
							.getTransactionPrice().getValue());
			Chunk priceRow1 = new Chunk(amt + "\n", new Font(null, 9,
					Font.NORMAL, Color.black));
			ip4.add(priceRow1);
			int totalAmt = (int) (sellerTransaction.getQuantityPurchased() * sellerTransaction
					.getTransactionPrice().getValue());
			String total = INR_CODE + String.valueOf(totalAmt);
			Chunk totalRow1 = new Chunk(total + "\n", new Font(null, 9,
					Font.NORMAL, Color.black));
			ip5.add(totalRow1);
			Chunk taxRow1 = new Chunk("NA" + "\n", new Font(null, 9,
					Font.NORMAL, Color.black));
			ip6.add(taxRow1);
			int grossAmt = totalAmt;
			String gross = total;
			Chunk grossRow1 = new Chunk(gross + "\n", new Font(null, 9,
					Font.NORMAL, Color.black));
			ip7.add(grossRow1);

			itemDetailsTable.addCell(new PdfPCell(iph1));
			PdfPCell descHdrCell = new PdfPCell(iph2);
			descHdrCell.setColspan(2);
			itemDetailsTable.addCell(descHdrCell);
			itemDetailsTable.addCell(new PdfPCell(iph3));
			itemDetailsTable.addCell(new PdfPCell(iph4));
			itemDetailsTable.addCell(new PdfPCell(iph5));
			itemDetailsTable.addCell(new PdfPCell(iph6));
			itemDetailsTable.addCell(new PdfPCell(iph7));
			itemDetailsTable.completeRow();
			itemDetailsTable.addCell(new PdfPCell(ip1));
			PdfPCell itmDescCell = new PdfPCell(ip2);
			itmDescCell.setColspan(2);
			itmDescCell.setNoWrap(false);
			itemDetailsTable.addCell(itmDescCell);
			PdfPCell qtyCell = new PdfPCell(ip3);
			qtyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			itemDetailsTable.addCell(qtyCell);
			PdfPCell priceCell = new PdfPCell(ip4);
			priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			itemDetailsTable.addCell(priceCell);
			PdfPCell totCell = new PdfPCell(ip5);
			totCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			itemDetailsTable.addCell(totCell);
			PdfPCell taxCell = new PdfPCell(ip6);
			taxCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			itemDetailsTable.addCell(taxCell);
			PdfPCell grossCell = new PdfPCell(ip7);
			grossCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			itemDetailsTable.addCell(grossCell);
			itemDetailsTable.completeRow();
			itemDetailsTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			itemDetailsTable.setWidthPercentage(100);
			itemDetailsTable.setSpacingAfter(15f);
			document.add(itemDetailsTable);

			PdfPTable postageDetailsTable = new PdfPTable(4);
			Phrase pph1 = new Phrase();
			Phrase pph2 = new Phrase();
			Phrase pph3 = new Phrase();
			Phrase pp1 = new Phrase();
			Phrase pp2 = new Phrase();
			Phrase pp3 = new Phrase();

			Chunk postageHdr = new Chunk(
					"Postage(with Tax) + Insurance" + "\n", new Font(null, 8,
							Font.NORMAL, Color.black));
			pph1.add(postageHdr);
			Chunk grndTotHdr = new Chunk("Grand Total" + "\n", new Font(null,
					8, Font.NORMAL, Color.black));
			pph2.add(grndTotHdr);
			Chunk pymtMthdHdr = new Chunk("Payment Method" + "\n", new Font(
					null, 8, Font.NORMAL, Color.black));
			pph3.add(pymtMthdHdr);

			Chunk postage = new Chunk(INR_CODE + String.valueOf(POSTAGE_CHARGE)
					+ "\n", new Font(null, 9, Font.NORMAL, Color.black));
			pp1.add(postage);
			Chunk grandTot = new Chunk(INR_CODE
					+ String.valueOf(POSTAGE_CHARGE + grossAmt) + "\n",
					new Font(null, 9, Font.NORMAL, Color.black));
			pp2.add(grandTot);
			Chunk pymtMthd = new Chunk(PAYMENT_METHOD + "\n", new Font(null, 9,
					Font.NORMAL, Color.black));
			pp3.add(pymtMthd);

			PdfPCell postageCellHdr = new PdfPCell(pph1);
			postageCellHdr.setBackgroundColor(new Color(250, 245, 245));
			postageCellHdr.setColspan(2);
			postageDetailsTable.addCell(postageCellHdr);
			PdfPCell postageCell = new PdfPCell(pp1);
			postageCell.setColspan(2);
			postageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			postageDetailsTable.addCell(postageCell);

			PdfPCell grndTotCellHdr = new PdfPCell(pph2);
			grndTotCellHdr.setBackgroundColor(new Color(250, 245, 245));
			grndTotCellHdr.setColspan(2);
			postageDetailsTable.addCell(grndTotCellHdr);
			PdfPCell grndTotCell = new PdfPCell(pp2);
			grndTotCell.setColspan(2);
			grndTotCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			postageDetailsTable.addCell(grndTotCell);

			PdfPCell pymtMthdCellHdr = new PdfPCell(pph3);
			pymtMthdCellHdr.setBackgroundColor(new Color(250, 245, 245));
			pymtMthdCellHdr.setColspan(2);
			postageDetailsTable.addCell(pymtMthdCellHdr);
			PdfPCell pymtMthdCell = new PdfPCell(pp3);
			pymtMthdCell.setColspan(2);
			pymtMthdCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			postageDetailsTable.addCell(pymtMthdCell);
			postageDetailsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
			postageDetailsTable.setWidthPercentage(50);
			postageDetailsTable.setSpacingAfter(15f);
			document.add(postageDetailsTable);

			Paragraph thankYouMsg = new Paragraph();
			Font paraFont = new Font();
			paraFont.setSize(9);
			paraFont.setStyle(Font.NORMAL);
			thankYouMsg.setFont(paraFont);
			thankYouMsg.add(THANK_YOU_MSG);
			thankYouMsg.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(thankYouMsg);

			document.add(Chunk.NEWLINE);

			Paragraph signature = new Paragraph();
			signature.setFont(paraFont);
			signature.add(SIGNATURE);
			signature.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(signature);
			Paragraph company = new Paragraph();
			paraFont.setStyle(Font.BOLD);
			company.setFont(paraFont);
			company.add(COMPANY);
			company.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(company);

			document.close();
			docWriter.flush();
			docWriter.close();

			byte[] bytesArray = baos.toByteArray();

			fos.write(bytesArray);

			fos.flush();

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
