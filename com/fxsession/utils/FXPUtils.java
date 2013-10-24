package com.fxsession.utils;


/**
 * @author Dmitry Vulf
 * 
 * Common class containing different useful things
 *
 */

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FXPUtils {
	
	static private SimpleDateFormat dateformat= new SimpleDateFormat("HH:mm:ss");   
	
	/*
	 * Converts timestamp  + microseconds to String representation 
	 */
 public static String toTimestampMcs(long value, long microsecs){
       
     Calendar cal = Calendar.getInstance();
     cal.setTimeZone(TimeZone.getTimeZone("GMT"));

     int hour = (int) (value / 10000000);
     value %= 10000000;
     int min = (int) (value / 100000);
     value %= 100000;
     int sec = (int) (value / 1000);
     cal.set(0, 0, 0, hour, min, sec);     
	 Date dt = cal.getTime();
	 int mls = (int) (microsecs / 1000);
	 int msc = (int) (microsecs % 1000);
	 String date = dateformat.format(dt) + "."+ mls + "." + msc;
	 return date;
  }
 
 public static String toTimestampMls(long value, long microsecs){
     Calendar cal = Calendar.getInstance();
     cal.setTimeZone(TimeZone.getTimeZone("GMT"));

     int hour = (int) (value / 10000000);
     value %= 10000000;
     int min = (int) (value / 100000);
     value %= 100000;
     int sec = (int) (value / 1000);
     cal.set(0, 0, 0, hour, min, sec);     
	 Date dt = cal.getTime();
	 int mls = (int) (microsecs / 1000);
	 String date = dateformat.format(dt) + "."+ mls;
	 return date;
 }
 
 public static Integer string2Int(String _size) throws FXPException {
	 try {
		 if (_size.equals(null) || _size.trim().isEmpty())
		 	return 0;   
		 else 
		 	return Integer.valueOf(_size.trim());
	 } catch (Exception e){
     	throw new FXPException(e);
	 }
 }
 
 public static Double string2Double(String _px) throws FXPException{
 	 try {
	 	if (_px.equals(null) || _px.trim().isEmpty())
			return 0d;
	 	else 
	 		return Double.valueOf(_px);
 	}catch (Exception e){
   		throw new FXPException(e);
	}
  }
 
   
 public static String Double2String (Double _px, int decplaces) {
 	DecimalFormat myFormatter = new DecimalFormat("#.0000000");
 	myFormatter.setMaximumFractionDigits(decplaces);
	String output = myFormatter.format(_px);
	return output;
 } 
}
