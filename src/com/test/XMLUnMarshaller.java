package com.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import ebay.apis.eblbasecomponents.GetSellerTransactionsResponse;

public class XMLUnMarshaller {

	private static final String JAXB_PACKAGE = "ebay.apis.eblbasecomponents";
	private static final String FEATURE_NAMESPACES = "http://xml.org/sax/features/namespaces";
	private static final String FEATURE_NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";

	public GetSellerTransactionsResponse unMarshallData() {

		JAXBContext jContext;
		GetSellerTransactionsResponse obj = null;
		
		try {
			jContext = JAXBContext.newInstance(JAXB_PACKAGE);

			// create the unmarshaller and unmarshal
			Unmarshaller unmarshaller = jContext.createUnmarshaller();

			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			xmlReader.setFeature(FEATURE_NAMESPACES, true);
			xmlReader.setFeature(FEATURE_NAMESPACE_PREFIXES, true);

			xmlReader.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId,
						String systemId) throws SAXException, IOException {
					// TODO: Check if systemId really references root.dtd
					return new InputSource(XMLUnMarshaller.class
							.getClassLoader().getResourceAsStream("ebay.xml"));
				}
			});

			obj = (GetSellerTransactionsResponse) unmarshaller
					.unmarshal(new FileInputStream(
							"C:/PDF/ebay.xml"));
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}
	

}
