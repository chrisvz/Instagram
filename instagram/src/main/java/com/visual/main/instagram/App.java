package com.visual.main.instagram;

import java.util.ArrayList;



import com.visual.main.chrome.Browser;
import com.visual.main.json.JsonPrinter;
import com.visual.main.model.User;
import com.visual.main.timing.AppTimer;


public class App {

	private FileReader fileReader;
	private ArrayList<String> usersFileList;
	private ArrayList<User> users = new ArrayList<User>();
	private Browser browser;

	public App() {
		browser = Browser.getInstance();

		if (browser.logIn()) {

			if (readUsersFile()) {

				createUsers();

				for (int i = 0; i < users.size(); i++) {
					
					User user = users.get(i);
					System.out.println();
					System.out.println(AppTimer.getCurrentTime()+" "+"Process start for: " + user.toString());
				
					
					
	
					browser.search(user);
					
//					ArrayList<String> temp= new ArrayList<>();
//					temp.add("https://www.instagram.com/p/BdLim7plBcl/?taken-by=nickasv");
//					temp.add( "https://www.instagram.com/p/BMVJROgh5Tz/?taken-by=nickasv"    );
//					  temp.add("https://www.instagram.com/p/BdLim7plBcl/?taken-by=nickasv"    );
//					  temp.add( "https://www.instagram.com/p/BMVJROgh5Tz/?taken-by=nickasv"    );
//					   temp.add("https://www.instagram.com/p/BdLim7plBcl/?taken-by=nickasv"    );
//					                                                                               
//					browser.scrapDetails(temp);
					browser.scrapPosts(user);
					
					
//					
//					System.out.println(AppTimer.getCurrentTime()+ " "+"followers scrapping . . .");
//					browser.scrapFollowers(user);
//					
//					browser.search(user);
//					
//					System.out.println(AppTimer.getCurrentTime()+ " "+"following scrapping . . .");
//					browser.scrapFollowing(user);
//					
//					browser.search(user);
//					
//					
//					browser.scrapPosts(user);
					
				//	JsonPrinter.add(user);
					
					System.out.println();
					System.out.println();
				
				}
			//	JsonPrinter.createJSON();
			}

			//browser.quit();

		} else {

		}
	}
	
	
	
	
	

	public void createUsers() {
		for (int i = 0; i < usersFileList.size(); i++) {
			String username = usersFileList.get(i);
			browser.search(username);
			int followersSize = browser.getFollowersSize(username);
			int followingSize = browser.getFollowingSize(username);
			int postsSize = browser.getPostsSize(username);

			User user = new User(username, followersSize, followingSize,postsSize);
			users.add(user);
			// System.out.println(user.toString());
		}
	}

	public boolean readUsersFile() {
		fileReader = new FileReader();
		usersFileList = fileReader.getUsers();
		if (usersFileList != null && usersFileList.size() >= 1) {
			//System.out.println("Success reading users from file");
			System.out.println(AppTimer.getCurrentTime()+" users"+usersFileList);
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new App();

	}
}
