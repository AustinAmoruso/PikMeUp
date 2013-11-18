package com.pmu.android.api.obj.impl;

import java.sql.Timestamp;

public class Time extends BaseAsyncObject {

	public static final String DATE_DISPLAY = "DateDisplay";
	public static final String TIME_DISPLAY = "TimeDisplay";
	private int hour;
	private int minute;
	private int day;
	private int month;
	private int year;

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTime() {
		return String.valueOf(Timestamp.valueOf(
				String.format("%d-%s-%s %s:%s:00.00", year, digit(month),
						digit(day), digit(hour), digit(minute))).getTime());
	}

	public String digit(int num) {
		if (num < 10) {
			return "0" + num;
		}
		return String.valueOf(num);
	}

	public void setTimeDisplay() {
		onUpdate(TIME_DISPLAY, this);
	}

	public void setDateDisplay() {
		onUpdate(DATE_DISPLAY, this);
	}

	public String getTimeDisplay() {
		return String.format("%d:%d", hour, minute);
	}

	public String getDateDisplay() {
		return String.format("%d/%d/%d", month, day, year);
	}
	
	public void parse(String time){
		
	}
	
	@Override
	public String toString() {
		return "01/23/2013 12:35AM (+/-15 min)";
	}

}
