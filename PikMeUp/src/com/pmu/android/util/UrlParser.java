package com.pmu.android.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class UrlParser {

	public static boolean hasUrlParameter(URL url, String paramater) {
		try {
			List<NameValuePair> l = URLEncodedUtils.parse(url.toURI(), null);
			for (int i = 0; i < l.size(); i++) {
				if (l.get(i).getName().equals(paramater)) {
					return true;
				}
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String getDirectories(URL url) {
		return url.getFile();
	}

	public static String getFile(URL url) {
		String result = url.getFile();
		return result.substring(result.lastIndexOf('/') + 1);
	}

	public static String getUrlParameter(URL url, String paramater) {
		try {
			List<NameValuePair> l = URLEncodedUtils.parse(url.toURI(), null);
			for (int i = 0; i < l.size(); i++) {
				if (l.get(i).getName().equals(paramater)) {
					return l.get(i).getValue();
				}
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isHost(String host, String[] hosts) {
		for (String s : hosts) {
			if (s.equals(host)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFile(URL url, String urlFile) {
		String file = url.getFile();
		file = file.substring(1, file.indexOf("?"));
		return file.equals(urlFile);
	}
}
