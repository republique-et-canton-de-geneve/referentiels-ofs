/*******************************************************************************
 * Copyright (c) 2013 Metadata Technology Ltd.
 *  
 * All rights reserved. This program and the accompanying materials are made 
 * available under the terms of the GNU Lesser General Public License v 3.0 
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * This file is part of the SDMX Component Library.
 * 
 * The SDMX Component Library is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with The SDMX Component Library If not, see 
 * http://www.gnu.org/licenses/lgpl.
 * 
 * Contributors:
 * Metadata Technology - initial API and implementation
 ******************************************************************************/
package org.sdmxsource.sdmx.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.xml.bind.ValidationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.xmlbeans.XmlCalendar;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;


/**
 * Class provided to perform date operations 
 */
public class DateUtil {
	//Regular expressions 
	private static final String xmlDatePatternString =
			"[0-9][0-9][0-9][0-9]-" +
					"((01|03|05|07|08|10|12)-((0[1-9])|(1[0-9])|(2[0-9])|3[0-1])" +
					"|02-((0[1-9])|(1[0-9])|(2[0-9]))" +
					"|(04|06|09|11)-((0[1-9])|(1[0-9])|(2[0-9])|30))";
	private static final String xmlHourPatternString = "([0-1][0-9]|2[0-3])";
	private static final String xmlTimePatternString = xmlHourPatternString+":([0-5][0-9])(:[0-5][0-9])?(.[0-9]*)?(Z|((\\+|-)([0-1][0-9]|2[0-3]):([0-5][0-9])))?";

	private static final Pattern xmlMinutelyPattern = Pattern.compile(xmlDatePatternString + "T" + xmlTimePatternString);
	private static final Pattern xmlHourlyPattern = Pattern.compile(xmlDatePatternString + "T" + xmlHourPatternString);
	private static final Pattern xmlDailyPattern = Pattern.compile(xmlDatePatternString);
	private static final Pattern xmlWeeklyPattern = Pattern.compile("[0-9][0-9][0-9][0-9]-W([1-9]|[1-4][0-9]|5[0-3])");
	private static final Pattern xmlMonthlyPattern = Pattern.compile("[0-9][0-9][0-9][0-9]-(0[1-9]|1[0-2])");
	private static final Pattern xmlQuarterlyPattern = Pattern.compile("[0-9][0-9][0-9][0-9]-Q[1-4]");
	private static final Pattern xmlSemiAnnualPattern = Pattern.compile("[0-9][0-9][0-9][0-9]-B[1-2]");
	private static final Pattern xmlYearlyPattern = Pattern.compile("[0-9][0-9][0-9][0-9]");

	public static Calendar createCalendar(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
		Calendar cal = getCalendar();
		cal.setTime(date);
		return cal;
	}

	public static XMLGregorianCalendar createXMLGregorianCalendar(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
		if(date == null) {
			return null;
		}
		try {
			Calendar now = getCalendar();
			now.setTime(date);
			GregorianCalendar cal = new GregorianCalendar(now.get(Calendar.YEAR), 
					now.get(Calendar.MONTH), 
					now.get(Calendar.DATE),
					now.get(Calendar.HOUR),
					now.get(Calendar.MINUTE),
					now.get(Calendar.SECOND));
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch(Throwable th) {
			throw new RuntimeException(th);
		}		
	}


	/**
	 * Returns the end value for the specified date. The end value is determined by the TIME_FORMAT.
	 * If the TIME_FORMAT is year then the returned date will be the end of the year. 
	 * @param date the date to analyse
	 * @param timeFormat The timeFormat to use.
	 * @return The end date for the specified timeFormat
	 */
	public static Date moveToEndofPeriod(Date date, TIME_FORMAT timeFormat) {
		String dateAsString = formatDate(date, timeFormat);
		return formatDateEndPeriod(dateAsString);
	}

	/**
	 * Formats a String as a date.
	 * <p/>
	 * The expected string must be in either of the two following formats.
	 * <ol>
	 *   <li>yyyy-MM-dd'T'HH:mm:ss.SSSz</li>
	 *   <li>yyyy-MM-dd</li>
	 * </ol>
	 * @param dateObject
	 * @param startOfPeriod formats the date to the start of the period, otherwise it will be end of period
	 * @return
	 */
	public static Date formatDate(Object dateObject, boolean startOfPeriod) {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
		if(dateObject == null) {
			return null;
		}
		if(dateObject instanceof Date) {
			return (Date)dateObject;
		} 
		if(dateObject instanceof XmlCalendar) {
			if(startOfPeriod) {
				return formatDateStartPeriod(dateObject.toString());
			}
			return formatDateEndPeriod(dateObject.toString());
			
		}
		if(dateObject instanceof XMLGregorianCalendar) {
			XMLGregorianCalendar gregorianCal = (XMLGregorianCalendar)dateObject;
			Calendar cal = getCalendar();
			cal.set(Calendar.YEAR, gregorianCal.getYear());
			cal.set(Calendar.MONTH, gregorianCal.getMonth()-1);
			cal.set(Calendar.DATE, gregorianCal.getDay());
			cal.set(Calendar.AM_PM, 0);
			if (gregorianCal.getHour() > 0) {
				cal.set(Calendar.HOUR, gregorianCal.getHour());
			} else {
				cal.set(Calendar.HOUR, 0);
			}

			if (gregorianCal.getMinute() > 0) {
				cal.set(Calendar.MINUTE, gregorianCal.getMinute());
			} else {
				cal.set(Calendar.MINUTE, 0);
			}
			if (gregorianCal.getSecond() > 0) {
				cal.set(Calendar.SECOND, gregorianCal.getSecond());
			} else {
				cal.set(Calendar.SECOND, 0);
			}
			cal.set(Calendar.MILLISECOND, 0);

			return cal.getTime();
		}
		
		// Last chance: Our input is now either a String or it's invalid.
		String dateString = null;
		if(dateObject instanceof String) {
			dateString = (String)dateObject;
		} else {
			throw new IllegalArgumentException("Date type not recognised : " + dateObject.getClass().getName());
		}

		if (dateString.length() == 0) {
			return null;
		}
		if (startOfPeriod) {
			return formatDateStartPeriod(dateString);
		}
		return formatDateEndPeriod(dateString);
	}

	private static Date formatDateStartPeriod(String value) {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
		TIME_FORMAT timeFormat = getTimeFormatOfDate(value);

		DateFormat df = null;
		String formatValue = null;
		String[] split = null;
		int quarter = 0;
		Calendar cal = null;
		switch(timeFormat) {
		case DATE :
			formatValue = value;
			df = getDateFormatter("yyyy-MM-dd");
			break;
		case DATE_TIME :
			break;
		case HALF_OF_YEAR :
			split = value.split("-B");
			quarter = Integer.parseInt(split[1]);
			switch(quarter) {
			case 1: formatValue = split[0] + "-01-01"; break;
			case 2: formatValue = split[0] + "-07-01"; break;
			}
			df = getDateFormatter("yyyy-MM-dd");
			break;
		case HOUR :
			formatValue = value;
			if(formatValue.length() == 13) {
				df = new SimpleDateFormat("yyyy-MM-dd'T'HH");//.SSSz
			} else if(formatValue.length() == 16) {
				df = getDateFormatter("yyyy-MM-dd'T'HH:mm");//.SSSz
			} else {
				df = getDateFormatter("yyyy-MM-dd'T'HH:mm:ss");//.SSSz
			}
			break;
		case MONTH :
			formatValue = value + "-01";
			df = getDateFormatter("yyyy-MM-dd");
			break;
		case QUARTER_OF_YEAR :
			split = value.split("-Q");
			quarter = Integer.parseInt(split[1]);
			switch(quarter) {
			case 1: formatValue = split[0] + "-01-01"; break;
			case 2: formatValue = split[0] + "-04-01"; break;
			case 3: formatValue = split[0] + "-07-01"; break;
			case 4: formatValue = split[0] + "-10-01"; break;
			}
			df = getDateFormatter("yyyy-MM-dd");
			break;
		case THIRD_OF_YEAR :
			break;
		case WEEK :
			split = value.split("-W");
			cal = getCalendar();
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.YEAR, Integer.parseInt(split[0]));
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(split[1]));
			return cal.getTime();
		case YEAR :
			formatValue = value + "-01-01";
			df = getDateFormatter("yyyy-MM-dd");
			break;
		default: throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "formatting date of type " + timeFormat);
		}

		try {
			return df.parse(formatValue);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	private static Date formatDateEndPeriod(String value) {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
		TIME_FORMAT timeFormat = getTimeFormatOfDate(value);

		DateFormat df = getDateFormatter("yyyy-MM-dd'T'HH:mm:ss");
		String formatValue = null;
		String[] split = null;
		int days = 0;
		int quarter = 0;
		Calendar cal = null;
		switch(timeFormat) {
		case DATE :
			formatValue = value;
			df = getDateFormatter("yyyy-MM-dd");
			break;
		case DATE_TIME :
			break;
		case HALF_OF_YEAR :
			split = value.split("-B");
			quarter = Integer.parseInt(split[1]);
			switch(quarter) {
			case 1: formatValue = split[0] + "-06-30T23:59:59"; break;
			case 2: formatValue = split[0] + "-12-31T23:59:59"; break;
			}
			break;
		case HOUR :
			formatValue = value;
			if(formatValue.length() == 13) {  //HOURLY
				formatValue = formatValue + ":59:59"; //Add the 59 minutes and 59 seconds
			} else if(formatValue.length() == 16) { //MINUTELY
				formatValue = formatValue + ":59:59"; //Add the 59 seconds
			}
			df = getDateFormatter("yyyy-MM-dd'T'HH:mm:ss");//.SSSz
			break;
		case MONTH :
			split = value.split("-");
			cal = getCalendar();
			cal.clear();
			cal.set(Calendar.YEAR, Integer.parseInt(split[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(split[1]) - 1);
			
			days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			formatValue = value + "-" + days+"T23:59:59";
			break;
		case QUARTER_OF_YEAR :
			split = value.split("-Q");
			quarter = Integer.parseInt(split[1]);
			switch(quarter) {
			case 1: formatValue = split[0] + "-03-31T23:59:59"; break;
			case 2: formatValue = split[0] + "-06-30T23:59:59"; break;
			case 3: formatValue = split[0] + "-09-30T23:59:59"; break;
			case 4: formatValue = split[0] + "-12-31T23:59:59"; break;
			}
			break;
		case THIRD_OF_YEAR :
			break;
		case WEEK :
			split = value.split("-W");
			cal = getCalendar();
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			cal.set(Calendar.YEAR, Integer.parseInt(split[0]));
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(split[1]));
			return cal.getTime();
		case YEAR :
			formatValue = value + "-12-31T23:59:59";
			break;
		default: throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "formatting date of type " + timeFormat);
		}

		try {
			return df.parse(formatValue);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Validates the date is in SDMX date/time format, and returns the Time Format for the date
	 * @param dateStr
	 * @return
	 * @throws ValidationException if the data format is invalid, with regards to the allowed SDMX date formats
	 */
	public static TIME_FORMAT getTimeFormatOfDate(String dateStr) {
		if (dateStr==null){
			throw new IllegalArgumentException("Could not determine date format, date null");	
		} 

		if(dateStr.endsWith("Z")) {
			dateStr = dateStr.substring(0, dateStr.length()-1);
		}
		if (xmlYearlyPattern.matcher(dateStr).matches()){
			return TIME_FORMAT.YEAR;
		} else if (xmlSemiAnnualPattern.matcher(dateStr).matches()){
			return TIME_FORMAT.HALF_OF_YEAR;
		} else if (xmlQuarterlyPattern.matcher(dateStr).matches()){
			return TIME_FORMAT.QUARTER_OF_YEAR;
		} else if (xmlMonthlyPattern.matcher(dateStr).matches()){
			return TIME_FORMAT.MONTH;
		} else if (xmlWeeklyPattern.matcher(dateStr).matches()){
			return TIME_FORMAT.WEEK;
		} else if (xmlDailyPattern.matcher(dateStr).matches()){
			return TIME_FORMAT.DATE;
		} else if (xmlHourlyPattern.matcher(dateStr).matches()){
			return TIME_FORMAT.HOUR;
		} else if (xmlMinutelyPattern.matcher(dateStr).matches()){
			return TIME_FORMAT.HOUR;
		} else{
			throw new SdmxSemmanticException(ExceptionCode.INVALID_DATE_FORMAT, dateStr);
		}	
	}

	public static String formatDate(Date dt) {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
		if (dt == null) {
			return null;
		}
		DateFormat df = getDateTimeFormat();
		return df.format(dt);
	}

	/**
	 * Returns a DateFormat object that can be used to format a date 
	 * string as a date object.  
	 * <p/>
	 * For a String to be successfully formatted as a Date using the 
	 * returned DateFormat object, it must conform to the ISO8601 Standard Date/Time format.
	 * @return DateFormat object
	 */
	public static DateFormat getDateTimeFormat() {
		// ISO8601 Standard Date/Time format
		return getDateFormatter("yyyy-MM-dd'T'HH:mm:ss");
	}

	public static DateFormat getDateFormat() {
		return getDateFormatter("yyyy-MM-dd");
	}

	public static DateFormat getYearFormat() {
		return getDateFormatter("yyyy");
	}

	public static DateFormat getMonthFormat() {
		return getDateFormatter("yyyy-MM");
	}

	public static DateFormat geWeekFormat() {
		return getDateFormatter("yyyy-ww");
	}

	/**
	 * Creates a list of all the date values between the from and to dates.  The dates are iterated by the 
	 * time format.  The format of the dates is also dependent on the time format.
	 * @param dateFrom
	 * @param dateTo
	 * @param format
	 * @return
	 */
	public static List<String> createTimeValues(Date dateFrom, Date dateTo, TIME_FORMAT format) {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
		List<String> returnList = new ArrayList<String>();
		switch(format) {
		case DATE : 
			iterateDailyValues(dateFrom, dateTo, returnList);
			break;
		case DATE_TIME : 
			throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, format); //FUNC Support iteration of date time	

		case HALF_OF_YEAR :
			iterateHalfYearlyValues(dateFrom, dateTo, returnList);
			break;	

		case HOUR :
			iterateHourlyValues(dateFrom, dateTo, returnList);
			break;	

		case MONTH : 
			iterateMonthlyValues(dateFrom, dateTo, returnList);
			break;	

		case QUARTER_OF_YEAR :
			iterateQuarterlyValues(dateFrom, dateTo, returnList);
			break;

		case THIRD_OF_YEAR :
			iterateThirdOfYearValues(dateFrom, dateTo, returnList);
			break;

		case WEEK :
			iterateWeeklyValues(dateFrom, dateTo, returnList);
			break;

		case YEAR :
			iterateYearlyValues(dateFrom, dateTo, returnList);
			break;

		default :
			throw new RuntimeException("Unsupported time format : " + format); 
		}
		return returnList;
	}

	private static void iterateDailyValues(Date dateFrom, Date dateTo, List<String> returnList) {
		iterateDateValues(dateFrom, dateTo, returnList, Calendar.DATE, 1, TIME_FORMAT.DATE);
	}

	private static void iterateHalfYearlyValues(Date dateFrom, Date dateTo, List<String> returnList) {
		iterateDateValues(dateFrom, dateTo, returnList, Calendar.MONTH, 6, TIME_FORMAT.HALF_OF_YEAR);
	}

	private static void iterateHourlyValues(Date dateFrom, Date dateTo, List<String> returnList) {
		iterateDateValues(dateFrom, dateTo, returnList, Calendar.HOUR, 1, TIME_FORMAT.HOUR);
	}

	private static void iterateMonthlyValues(Date dateFrom, Date dateTo, List<String> returnList) {
		iterateMonthValue(dateFrom, dateTo, returnList, 1, TIME_FORMAT.MONTH);
	}

	private static void iterateQuarterlyValues(Date dateFrom, Date dateTo, List<String> returnList) {
		iterateMonthValue(dateFrom, dateTo, returnList, 3, TIME_FORMAT.QUARTER_OF_YEAR);
	}

	private static void iterateThirdOfYearValues(Date dateFrom, Date dateTo, List<String> returnList) {
		iterateDateValues(dateFrom, dateTo, returnList, Calendar.MONTH, 4, TIME_FORMAT.THIRD_OF_YEAR);
	}

	private static void iterateWeeklyValues(Date dateFrom, Date dateTo, List<String> returnList) {
		iterateWeekValue(dateFrom, dateTo, returnList, 1, TIME_FORMAT.WEEK);
	}

	private static void iterateYearlyValues(Date dateFrom, Date dateTo, List<String> returnList) {
		iterateDateValues(dateFrom, dateTo, returnList, Calendar.YEAR, 1, TIME_FORMAT.YEAR);
	}


	private static void iterateDateValues(
			Date dateFrom, Date dateTo, List<String> returnList, int duration, int number, TIME_FORMAT format) {

		Calendar cal = getCalendar();
		cal.setTime(dateFrom);

		Calendar calTo = getCalendar();
		calTo.setTime(dateTo);

		while (!cal.getTime().after(calTo.getTime())) {
			returnList.add(formatDate(cal.getTime(), format));
			cal.add(duration, number);
		}
	}

	private static void iterateMonthValue(
			Date dateFrom, Date dateTo, List<String> returnList, int number, TIME_FORMAT format) {

		LocalDate dateTimeFrom = new LocalDate(dateFrom, DateTimeZone.UTC);
		LocalDate dateTimeTo = new LocalDate(dateTo, DateTimeZone.UTC);

		while (!dateTimeFrom.isAfter(dateTimeTo)) {
			returnList.add(formatDate(dateTimeFrom.toDate(), format));
			dateTimeFrom = dateTimeFrom.plusMonths(number);
		}
	}

	private static void iterateWeekValue(
			Date dateFrom, Date dateTo, List<String> returnList, int number, TIME_FORMAT format) {

		LocalDate dateTimeFrom = new LocalDate(dateFrom, DateTimeZone.UTC);
		LocalDate dateTimeTo = new LocalDate(dateTo, DateTimeZone.UTC);

		while (!dateTimeFrom.isAfter(dateTimeTo)) {
			returnList.add(formatDate(dateTimeFrom.toDate(), format));
			dateTimeFrom = dateTimeFrom.plusWeeks(number);
		}
	}


	/**
	 * Return an SDMX-ML formatted time period, based on the frequency supplied. The value
	 * returned from this method will be determined based on the frequency and the date value
	 * supplied as follows:
	 * 
	 * Annual: The year of the date
	 * Biannual: January 1 - June 30 = 1st half, July 1 - December 31 = 2nd half
	 * Triannual: January 1 - April 30 = 1st third, May 1 - August 30 = 2nd third, September 1 - December 31 = 3rd third
	 * Quarterly: January 1 - March 31 = 1st quarter, April 1 - June 30 = 2nd quarter, July 1 - September 30 = 3rd quarter, October 1 - December 31 = 4th quarter
	 * Monthly: The month of the date
	 * Weekly: The week of the year according to ISO 8601.
	 * Daily: The date only
	 * Hourly: The full date time representation.
	 * 
	 * @param date	the date to convert
	 * @param freq	the frequency on which the conversion should be based
	 * @return		The date in SDMX-ML <code>TimePeriodType</code> format.
	 */
	public static String formatDate(Date date, TIME_FORMAT format) {
		DateFormat df = null;
		String formatted = null;
		Calendar cal;
		switch(format) {
		case DATE : 
			df = getDateFormat();
			return df.format(date);

		case DATE_TIME : 
			df = getDateTimeFormat();
			return df.format(date);

		case YEAR :
			df = getYearFormat();
			formatted = df.format(date);
			cal = getCalendar();
			cal.setTime(date);
			return formatted;				

		case HALF_OF_YEAR :
			df = getYearFormat();
			formatted = df.format(date);
			cal = getCalendar();
			cal.setTime(date);
			if(cal.get(Calendar.MONTH) <= 6) {
				formatted += "-B1";
			} else {
				formatted += "-B2";
			}
			return formatted;

		case HOUR :
			df = getDateTimeFormat();
			return df.format(date);

		case MONTH : 
			df = getMonthFormat();
			return df.format(date);

		case QUARTER_OF_YEAR :
			df = getYearFormat();
			formatted = df.format(date);
			cal = getCalendar();
			cal.setTime(date);
			if(cal.get(Calendar.MONTH) <= 2) {
				formatted += "-Q1";
			} else if(cal.get(Calendar.MONTH) <= 5) { 
				formatted += "-Q2";
			} else if(cal.get(Calendar.MONTH) <= 8) { 
				formatted += "-Q3";
			} else {
				formatted += "-Q4";
			}
			return formatted;

		case THIRD_OF_YEAR :
			df = getYearFormat();
			formatted = df.format(date);
			cal = getCalendar();
			cal.setTime(date);
			if(cal.get(Calendar.MONTH) <= 3) {
				formatted += "-T1";
			} else if(cal.get(Calendar.MONTH) <= 7) { 
				formatted += "-T2";
			} else { 
				formatted += "-T3";
			}
			return formatted;

		case WEEK :
			// JodaTime is very useful here since it has methods to provide the week of the year
			// Important note: When using getWeekOfWeekyear() the associated year is obtained
			// through the call getWeekYear() rather than getYear().
			LocalDate dateTimeFrom = new LocalDate(date, DateTimeZone.UTC);
			int weekNum = dateTimeFrom.getWeekOfWeekyear();
			int year = dateTimeFrom.getWeekyear(); 
			
			formatted = year + "-W" + weekNum;
			return formatted;			
		}
		return null;
	}

	public static String getDateTimeStringNow() {
		DateFormat df = getDateFormatter("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Date d = new Date();
		return df.format(d);
	}

	/**
	 * This method should be used instead of <code>Calendar.getInstance()</code> since it 
	 * constructs a Calendar with the appropriate default settings which ensures Dates are calculated correctly.  
	 * The use of a Calendar with TimeZone of GMT is important since GMT is NOT a TimeZone
	 * that has any daylight saving hours issues.
	 * @return A Calendar
	 */
	public static Calendar getCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTimeZone(TimeZone.getTimeZone("Europe/Zurich"));
		cal.setMinimalDaysInFirstWeek(4);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		
		return cal;
	}

	public static Date parseDate(String date, String dateFormat)  {
		DateFormat sdf = getDateFormatter(dateFormat);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns a DateFormat object which has its TimeZone set to GMT.
	 * The use of a Calendar with TimeZone of GMT is important since GMT is NOT a TimeZone
	 * that has any daylight saving hours issues.
	 */
	public static DateFormat getDateFormatter(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Zurich"));
		sdf.getCalendar().setMinimalDaysInFirstWeek(4);
		sdf.getCalendar().setFirstDayOfWeek(Calendar.MONDAY);
		return sdf;
	}
}
