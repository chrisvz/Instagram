package com.visual.main.instagram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
	

	/**
	 * Reads user from the file and returns an ArrayList
	 * @return null or ArrayList
	 */
	public ArrayList<String> getUsers(){
		
		ArrayList<String> list;
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("app.users"));
			list = new ArrayList<String>();
			while (scanner.hasNext()){
			    list.add(scanner.next());
			}
			scanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		return list;
	}
	
}
