package com.visual.main.timing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.visual.main.properties.PropertyReader;

public class AppTimer {

	public static void randomSleep() {

		int MIN_VALUE = 400; // less than second
		// int MAX_VALUE = 2300; // 2.3 seconds

		int MAX_VALUE = Integer.parseInt(PropertyReader.readProperty("scrollSleepMAX"));
		

		Random random = new Random();
		int myRandomNumber = random.nextInt(MAX_VALUE - MIN_VALUE) + MIN_VALUE;

		try {
			Thread.sleep(myRandomNumber);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sleep(long miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void pause() {

		// int MIN_VALUE = 70000; // 1.16 minutes
		// int MAX_VALUE = 200000; // 3.3 minutes

		int MIN_VALUE = Integer.parseInt(PropertyReader.readProperty("recordSleepMIN"));
		int MAX_VALUE = Integer.parseInt(PropertyReader.readProperty("recordSleepMAX"));

		Random random = new Random();
		int myRandomNumber = random.nextInt(MAX_VALUE - MIN_VALUE) + MIN_VALUE;
		double seconds = myRandomNumber / 1000.0;
	
		System.out.println(" ***  pause " + seconds + " sec ***");
		try {
			// TimeUnit.MINUTES.sleep(myRandomNumber);
			Thread.sleep(myRandomNumber);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void humanizeDelay(int value) {
		// every ~500 records pauses
		//int mod = 500;
		int mod = Integer.parseInt(PropertyReader.readProperty("records"));
		if (value % mod == 0 || value % mod == 1 || value % mod == 2 || value % mod == 3 || value % mod == 4
				|| value % mod == 5 || value%mod == 6 || value%mod == 7 || value%mod == 8 || value%mod == 8 || value%mod==9) {
			pause();
		}
	}
	
	 public static String getCurrentTime() {
	        Calendar cal = Calendar.getInstance();
	        Date date=cal.getTime();
	        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	        String formattedDate = dateFormat.format(date);
	        return formattedDate;
	    }

}
