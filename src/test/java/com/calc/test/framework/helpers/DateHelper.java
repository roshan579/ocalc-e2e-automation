package com.calc.test.framework.helpers;


import org.apache.commons.lang3.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
* Created by Roshan
*/
public class DateHelper {


    public static String DEFAULT_DATE_FORMAT = "dd MMM yyyy";
    public static String CALENDAR_DATE_FORMAT = "EEEE MMMM d yyyy";
    public static String COMBINED_DATE_FORMAT = "ddMMMyyyy";
    public static String COMBINED_DATE_TIME_FORMAT = "dd MMM yyyy HHmm";
    public static String COMBINED_DATE_MONTH_FORMAT = "dd MMM";
    public static String DATE_TIME_FORMAT = "ddMMMyyyy HH:mm";
    public static String TIMESTAMP_DATE_FORMAT_MILLISEC = "ddMMMyyyy HH:mm:ss.SSS";
    public static String TIMESTAMP_DATE_FORMAT = "ddMMMyyyy HH:mm:ss";
    public static String PNR_CREATION_DATE_FORMAT = "dd-MMM-yyyy";
    public static String FLIGHT_DATE_FORMAT = "dd-MMM-yy";
    public static String TIME_ZONE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static String TIME_ONLY_FORMAT_HH_MM = "HH:mm";
    public static String SQL_DATE_FORMAT = "yyyy-MM-dd";
    public static String DAY_MONTH_FORMAT = "dd MMM";
    public static String TIME_ZONE_DEFAULT_STRING = "GMT+04:00";



    public static Date getCurrentDate() {
        return new Date();
    }

    public static Date constructDateWithTime(String date, String dateFormat, String time) throws Exception{
        try{
            Date returnDate = new SimpleDateFormat(dateFormat).parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(returnDate);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,2)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(2,4)));
            if(time.length() > 4){
                calendar.set(Calendar.SECOND, Integer.parseInt(time.substring(4)));
            }else{
                calendar.set(Calendar.SECOND, 0);
            }
            return calendar.getTime();
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public static Date addHoursToGivenDate(Date givenDate, int NumberOfHours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.HOUR_OF_DAY, NumberOfHours);
        return cal.getTime();
    }

    public static Date addMinutesToGivenDate(Date givenDate, int NumberOfMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.MINUTE, NumberOfMinutes);
        return cal.getTime();
    }

    public static Date addDaysToGivenDate(Date givenDate, int NumberOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.DATE, NumberOfDays);
        return cal.getTime();
    }

    public static String addDaysToGivenDate(Date givenDate, int NumberOfDays, String dateFormat) {
        SimpleDateFormat formats = new SimpleDateFormat(dateFormat);
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.DATE, NumberOfDays);
        return formats.format(cal.getTime());
    }

    public static String systemFutureDate(int NumberOfDays, String dateFormat) {
        SimpleDateFormat formats = new SimpleDateFormat(dateFormat);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, NumberOfDays);
        return formats.format(cal.getTime());
    }

    public static String systemPastDate(int NumberOfDays, String dateFormat) {
        SimpleDateFormat formats = new SimpleDateFormat(dateFormat);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -NumberOfDays);
        return formats.format(cal.getTime());
    }

    public static long getDayCount(Date dateStart, Date dateEnd) {
        if (dateStart == null) {
            dateStart = new Date();
        }
        long diff = -1;
        try {
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception ex) {
        }
        return diff;
    }

    public static String currentDateInFormat(String format) {
        SimpleDateFormat formats = new SimpleDateFormat(format);
        return formats.format(getCurrentDate());
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat(TIMESTAMP_DATE_FORMAT_MILLISEC).format(getCurrentDate());
    }

    public static String getFutureDateInYears(int years, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.YEAR, years);
        return simpleDateFormat.format(calender.getTime());
    }

    public static String getFutureDate(int days, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DATE, days);
        return simpleDateFormat.format(calender.getTime());
    }


    public static String getDateInRequiredFormat(String inputDate, String inputDateFormat, String desiredDateFormat) throws Exception {
        try {
            Date inputDateObject = new SimpleDateFormat(inputDateFormat).parse(inputDate);
            return new SimpleDateFormat(desiredDateFormat).format(inputDateObject);
        } catch (ParseException e) {
            throw new Exception("Could not parse input date");
        }
    }

    public static String getDateInRequiredFormat(String inputDate, String desiredDateFormat) throws Exception {
        try {
            Date inputDateObject = new SimpleDateFormat(COMBINED_DATE_FORMAT).parse(inputDate);
            return new SimpleDateFormat(desiredDateFormat).format(inputDateObject);
        } catch (ParseException e) {
            throw new Exception("Could not parse input date");
        }
    }

    public static String getDateInRequiredFormat(Date date, String desiredDateFormat) throws Exception {
        try {
            return new SimpleDateFormat(desiredDateFormat).format(date);
        } catch (Exception ex) {
            throw new Exception("Could not format input date");
        }
    }

    public static Date getDateInRequiredFormat(String date) throws Exception {
        try {
            return new SimpleDateFormat(DateHelper.DEFAULT_DATE_FORMAT).parse(date);
        } catch (Exception ex) {
            throw new Exception("Could not parse input date");
        }
    }

    public static Date getDateInDesiredFormat(String format, String date) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date);
    }

    public static String getTimeZone() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        return simpleDateFormat.getTimeZone().toString();

    }

    public static Date getDate(String inputDate, String inputFormat) throws Exception {
        try {
            return new SimpleDateFormat(inputFormat).parse(inputDate);
        } catch (ParseException e) {
            throw new Exception("Could not parse input date");
        }
    }

    public static Long dateDifferenceInDays(Date fromDate, Date toDate) throws Exception{
        long diff = fromDate.getTime() - toDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return Long.valueOf(diffDays);
    }

    public static Long dateDifferenceInHours(Date fromDate, Date toDate) throws Exception{
        long diff = fromDate.getTime() - toDate.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        return Long.valueOf(diffHours);
    }

    public static Long dateDifferenceInMinutes(Date fromDate, Date toDate) throws Exception{
        long diff = fromDate.getTime() - toDate.getTime();
        long diffMinutes = diff / (60 * 1000);
        return Long.valueOf(diffMinutes);
    }


    public static String getDateInTimeZoneFormat(Date date, String timeZoneString) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(TIME_ZONE_DATE_FORMAT);
            if(StringUtils.isBlank(timeZoneString)){
                sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_DEFAULT_STRING));
            }else{
                sdf.setTimeZone(TimeZone.getTimeZone(timeZoneString));
            }
            return sdf.format(date);
        } catch (Exception ex) {
            throw new Exception("Could not format input date in time zone format with time zone string : "+timeZoneString);
        }
    }


    public static String getDateDifferenceWithFromDate(Date fromDate, Date toDate) throws Exception{
        long diff = fromDate.getTime() - toDate.getTime();
        boolean containsMinus = false;
        long diffMinutes = diff / (60 * 1000) % 60;
        if(!containsMinus){
            if(diffMinutes < 0){
                containsMinus = true;
            }
        }
        long diffHours = diff / (60 * 60 * 1000) % 24;
        if(!containsMinus){
            if(diffHours < 0){
                containsMinus = true;
            }
        }
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if(!containsMinus){
            if(diffDays < 0){
                containsMinus = true;
            }
        }
        StringBuilder stringBuilder = new StringBuilder("");
        if(containsMinus){
            stringBuilder.append("-");
        }
        if(diffDays >= 0){
            stringBuilder.append(" "+diffDays+"d");
        }else if(diffDays < 0){
            stringBuilder.append(" "+(diffDays * -1)+"d");
        }
        if(diffHours >= 0){
            stringBuilder.append(" "+diffHours+"h");
        }else if(diffHours < 0){
            stringBuilder.append(" "+(diffHours * -1)+"h");
        }
        if(diffMinutes >= 0){
            stringBuilder.append(" "+diffMinutes+"m");
        }else if(diffMinutes < 0){
            stringBuilder.append(" "+(diffMinutes * -1)+"m");
        }
        return stringBuilder.toString().trim();
    }


    public static String getConnectingTimeString(long totalMinutes) throws Exception{
        boolean containsMinus = false;
        long diffMinutes = totalMinutes % 60;
        if(!containsMinus){
            if(diffMinutes < 0){
                containsMinus = true;
            }
        }
        long diffHours = totalMinutes / (60) % 24;
        if(!containsMinus){
            if(diffHours < 0){
                containsMinus = true;
            }
        }
        long diffDays = totalMinutes / (24 * 60);
        if(!containsMinus){
            if(diffDays < 0){
                containsMinus = true;
            }
        }

        StringBuilder stringBuilder = new StringBuilder("");
        if(containsMinus){
            stringBuilder.append("-");
        }
        if(diffDays > 0){
            stringBuilder.append(" "+diffDays+" day(s)");
        }else if(diffDays < 0){
            stringBuilder.append(" "+(diffDays * -1)+" day(s)");
        }
        if(diffHours >= 0){
            stringBuilder.append(" "+diffHours+" hr(s)");
        }else if(diffHours < 0){
            stringBuilder.append(" "+(diffHours * -1)+" hr(s)");
        }
        if(diffMinutes >= 0){
            stringBuilder.append(" "+diffMinutes+" min(s)");
        }else if(diffMinutes < 0){
            stringBuilder.append(" "+(diffMinutes * -1)+" min(s)");
        }
        return stringBuilder.toString().trim();
    }

    public static String getJourneyTimeString(Date fromDate, Date toDate) throws Exception{
        long diff = fromDate.getTime() - toDate.getTime();
        boolean containsMinus = false;
        long diffMinutes = diff / (60 * 1000) % 60;
        if(!containsMinus){
            if(diffMinutes < 0){
                containsMinus = true;
            }
        }
        long diffHours = diff / (60 * 60 * 1000) % 24;
        if(!containsMinus){
            if(diffHours < 0){
                containsMinus = true;
            }
        }
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if(!containsMinus){
            if(diffDays < 0){
                containsMinus = true;
            }
        }
        StringBuilder stringBuilder = new StringBuilder("");
        if(containsMinus){
            stringBuilder.append("-");
        }
        if(diffDays > 0){
            if(diffDays > 0 && diffDays < 10){
                stringBuilder.append("0"+diffDays+"d");
            }else{
                stringBuilder.append(diffDays+"d");
            }
        }else if(diffDays < 0){
            if(diffDays > -10){
                stringBuilder.append("0"+(diffDays * -1)+"d");
            }else{
                stringBuilder.append((diffDays * -1)+"d");
            }
        }
        if(diffHours >= 0){
            if(diffHours > 0 && diffHours < 10){
                stringBuilder.append("0"+diffHours+"h");
            }else{
                stringBuilder.append(diffHours+"h");
            }
        }else if(diffHours < 0){
            if(diffHours > -10){
                stringBuilder.append("0"+(diffHours * -1)+"h");
            }else{
                stringBuilder.append((diffHours * -1)+"h");
            }
        }
        if(diffMinutes >= 0){
            if(diffMinutes > 0 && diffMinutes < 10){
                stringBuilder.append("0"+diffMinutes+"m");
            }else{
                stringBuilder.append(diffMinutes+"m");
            }
        }else if(diffMinutes < 0){
            if(diffMinutes > -10){
                stringBuilder.append("0"+(diffMinutes * -1)+"m");
            }else{
                stringBuilder.append((diffMinutes * -1)+"m");
            }
        }
        return stringBuilder.toString().trim();
    }




}
