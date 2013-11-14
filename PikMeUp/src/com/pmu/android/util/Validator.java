package com.pmu.android.util;

public class Validator {

	public static boolean isValidCreditCardNumber(String s) {
		return doLuhn(s, false) % 10 == 0;
	}

	private static int doLuhn(String s, boolean evenPosition) {
		int sum = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(s.substring(i, i + 1));
			if (evenPosition) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			evenPosition = !evenPosition;
		}

		return sum;
	}

	public static boolean notBlank(String s) {
		return (s.trim().length() > 0);
	}

	public static boolean isMultiLine(String s) {
		return (s.trim().contains("\n"));
	}

	public static boolean isValidDate(String mmyyyy) {
		return mmyyyy.matches("^((0[1-9])|(1[0-2]))/((2009)|(20[1-2][0-9]))$");
	}

	public static boolean isValidCVV(String cvv) {
		try {
			if (cvv.length() >= 3 && cvv.length() <= 4) {
				Integer i = Integer.valueOf(cvv);
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	public static boolean isValidZip(String zip) {
		return zip.matches("^\\d{5}(-\\d{4})?$");
	}

	public static boolean isValidEmail(String email) {
		return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	public static boolean isValidPhone(String phone) {
		return phone.matches("\\d{10}");
	}
}
