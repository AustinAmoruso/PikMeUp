package com.pmu.android.util;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.ksoap2.serialization.SoapObject;

public class SoapParser {

	public static HashMap<String, String> GetValues(SoapObject soap) {
		HashMap<String, String> props = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			// builder = factory.newDocumentBuilder();
			// Document doc = builder.parse(new InputSource(new StringReader(
			// XMLParser.getXML(soap))));
			// props = XMLParser.getProductValues(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}

	public static String getContent(SoapObject so) {
		String result = "";
		if (so != null) {
			for (int i = 0; i < so.getPropertyCount(); i++) {
				result += so.getPropertyAsString(i);
			}
		}
		return result;
	}

}
