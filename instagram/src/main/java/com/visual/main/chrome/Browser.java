package com.visual.main.chrome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import com.visual.main.model.User;
import com.visual.main.properties.PropertyReader;
import com.visual.main.timing.AppTimer;

public class Browser {

	// DRIVER CONFIGURATION PROPERTIES
	private final String driverPath = PropertyReader.readProperty("chromedriverPath");
	private final String driverlogPath = PropertyReader.readProperty("chromeLogfilePath");
	private final String driverVerbose = PropertyReader.readProperty("chromeVerbose");
	private final String visible = PropertyReader.readProperty("chromeVisible");
	private final String size = PropertyReader.readProperty("chromeSize");

	private static Browser browser;
	private WebDriver driver;
	private JavascriptExecutor jse;

	private WebDriver getDriver() {

		System.setProperty("webdriver.chrome.driver", driverPath);
		System.setProperty("webdriver.chrome.logfile", driverlogPath);

		// verbose mode
		if (driverVerbose.equals("yes")) {
			System.setProperty("webdriver.chrome.verboseLogging", "true");
		} else {
			System.setProperty("webdriver.chrome.verboseLogging", "false");
		}

		ChromeOptions options = new ChromeOptions();

		// headless mode
		if (visible.equals("no")) {
			options.addArguments("headless");
		}
		options.addArguments(new String[] { "--disable-gpu" });
		options.addArguments(size);

		return new ChromeDriver(options);
	}

	private Browser() {
		driver = getDriver();
		// wait for elements to appear limit 3 seconds
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		jse = (JavascriptExecutor) driver;
	}

	public boolean logIn() {

		try {

			driver.get("https://www.instagram.com/accounts/login/");
			// CREDENTIALS PROPERTIES
			String username = PropertyReader.readProperty("username");
			String password = PropertyReader.readProperty("password");

			driver.findElement(By.xpath("//*[@name=\"username\"]")).sendKeys(new CharSequence[] { username });
			driver.findElement(By.xpath("//*[@name=\"password\"]")).sendKeys(new CharSequence[] { password });
			driver.findElement(By.xpath("//*[@class=\"_qv64e       _gexxb _4tgw8      _njrw0   \"]")).click();

			driver.findElement(By.className("logged-in"));

			System.out.println(AppTimer.getCurrentTime() + " " + "Success login");
		} catch (Exception e) {
			System.out.println(AppTimer.getCurrentTime() + " " + "Failed login");
			return false;
		}
		return true;
	}

	public void search(User user) {
		System.out.println();
		driver.get("https://www.instagram.com/" + user.getUsername());
		AppTimer.sleep(700);
		// System.out.println("Searching user: "+user);
	}

	public void searchURL(String url) {
		driver.get(url);
		AppTimer.sleep(200);
	}

	public void search(String user) {
		System.out.println();
		driver.get("https://www.instagram.com/" + user);
		// System.out.println("Searching user: "+user);
	}

	public void clickFollowers() {
		driver.findElement(By.partialLinkText("followers")).click();
		// sleep();
		AppTimer.sleep(700);
		reload();
	}

	public void clickFollowing() {
		driver.findElement(By.partialLinkText("following")).click();
		// sleep();
		AppTimer.sleep(700);
		reload();
	}

	public void reload() {
		//JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("location.reload();", new Object[0]);
	}

	/**
	 * Only when extracting followers and followings
	 * 
	 * @return
	 */
	public boolean isScrapperDetected() {
		if (driver.findElements(By.xpath("//div[contains(@class, '_anzsd  _o5uzb ') ]")).size() != 0) {
			return true;

		}
		return false;
	}

	public void scrapPosts(User user) {
		HashSet<String> set = new HashSet<String>();
		int posts = user.getPostsSize();
		int postsDelay = Integer.parseInt(PropertyReader.readProperty("postsDelay"));
		final String mainURL = "https://www.instagram.com";

		for (int i = 0; i < posts;) {

			Long scrollStart = getYoffset();
			scrollDown();
			AppTimer.sleep(postsDelay);
			Long scrollEnd = getYoffset();

			if (isScrapperDetected(scrollStart, scrollEnd)) {
				System.out.println("***   scrapper detected   ****");
				break;
			}

			List<WebElement> posts_elems = driver.findElements(By.xpath("//div[@class='_mck9w _gvoze  _tn0ps']"));
			for (WebElement element : posts_elems) {

				String url = findPostUrl(element);
				set.add(mainURL + url);
				i = set.size();
			}
			System.out.print("\r" + "" + AppTimer.getCurrentTime() + " " + i + "/" + posts);
		}
		scrapDetails(convertSetToList(set));
	}
	
	
	
	

	public void scrapDetails(ArrayList<String> urlList) {
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		String postURL;	
		String imageurlXPATH = "//*[@id='react-root']/section/main/div/div/article";
		String videourlXPATH = "//*[@id='react-root']/section/main/div/div/article/div[1]/div/div/div/div[1]/div";
							
		for (int i = 0; i < urlList.size(); i++) {
			int counter = i+1;
			System.out.print("\r" + "" + AppTimer.getCurrentTime() + " " + counter+ "/" + urlList.size());
			browser.searchURL(urlList.get(i));
			postURL = urlList.get(i);
			
			
			// find URL of image or video
			String imageUrl = null;
			String videoUrl = null;
			String finalUrl = null;

			WebElement imageElement;
			WebElement videoElement;
			
			int numberOfViewsOrLikes = 0;

		    String likesDiv ="_4rbun";
		    
			String type = null;
			
			
			// its an image
			if(driver.findElements( By.className(likesDiv) ).size() != 0 ){
				imageElement = driver.findElement(By.xpath(imageurlXPATH));
				imageUrl = findImageUrl(imageElement);
				numberOfViewsOrLikes = findLikes();
				finalUrl = imageUrl;
				type = "image";
				
			}else {
				
				videoElement = driver.findElement(By.xpath(videourlXPATH));
				videoUrl = findVideoUrl(videoElement);
				numberOfViewsOrLikes = findViews();
				finalUrl = videoUrl;
				type ="video";
			}
			
			System.out.println();
			System.out.println(postURL + " likes: " + numberOfViewsOrLikes);
			System.out.println("type: "+type + " "+finalUrl);
			System.out.println();
			
			
		}
	}
	
	
	public void test() {
		
	}
	
	
	
	public int findLikes() {
		String numOfLikesXPATH = "//*[@id='react-root']/section/main/div/div/article/div[2]/section[2]/div/a/span";
		int numberOfLikes = 0;
		try {
			WebElement element = driver.findElement(By.xpath(numOfLikesXPATH));
			numberOfLikes = Integer.parseInt(element.getText());
		}
		catch(Exception e){
			// 0 likes
			e.printStackTrace();
			return numberOfLikes;
			
		}
		return numberOfLikes;
	}
	
	public int findViews() {
		String numOfViewsXPATH = "//*[@id='react-root']/section/main/div/div/article/div[2]/section[2]/div/span";
		int numberOfViews = 0;
		try {
			WebElement element = driver.findElement(By.xpath(numOfViewsXPATH));
			numberOfViews = Integer.parseInt(element.getText().split(" views")[0]);
		}
		catch(Exception e){
			findLikes();
			
			// 0 views
			// videos may have have likes
			int likes = findLikes();
			if(likes > 0) {
				numberOfViews = likes;
			}
				
			e.printStackTrace();
			return numberOfViews;
		
		}
		return numberOfViews;
	}

	public ArrayList<String> convertSetToList(HashSet<String> hashset) {
		return new ArrayList<String>(hashset);
	}

	public void scrollDown() {
		jse.executeScript("window.scrollBy(0,550)", "");
		//
		
	}

	public String findPostUrl(WebElement webElement) {
		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(webElement.getAttribute("innerHTML"));
		String url = null;
		if (m.find()) {
			url = m.group(1); // this variable contains the link URL
		}
		// delimiter between links
		return url;
	}

	public String findVideoUrl(WebElement webElement) {

		Pattern p = Pattern.compile("src=\"(.*?)\"");
		Matcher m = p.matcher(webElement.getAttribute("innerHTML"));
		String url = null;
		if (m.find()) {
			url = m.group(1); // this variable contains the link URL
		}

		return url;
	}

	public String findImageUrl(WebElement webElement) {
		Pattern p = Pattern.compile("srcset=\"(.*?)\"");
		Matcher m = p.matcher(webElement.getAttribute("innerHTML"));
		String url = null;
		if (m.find()) {
			url = m.group(1); // this variable contains the link URL
		}
		// delimiter between links
		// contains 3 links
		return url.split(" 640w,")[0];
	}

	/**
	 * Only when extracting posts url
	 * 
	 * @return
	 */
	public boolean isScrapperDetected(Long scrollStart, Long scrollEnd) {
		if (scrollStart.compareTo(scrollEnd) == 0) {
			return true;
		}
		return false;
	}

	public Long getYoffset() {
		return (Long) jse.executeScript("return window.pageYOffset;");
	}

	public void scrapFollowers(User user) {

		driver.findElement(By.partialLinkText("follower")).click();
		int size = getFollowersSize(user.getUsername());
		AppTimer.sleep(700);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("followersbox = document.getElementsByClassName('_gs38e')[0];", new Object[0]);
		long last_height = ((Long) js.executeScript("return followersbox.scrollHeight;", new Object[0])).longValue();

		int repeat = 0;
		for (;;) {

			js.executeScript("followersbox.scrollTo(0, " + last_height + ");", new Object[0]);
			String followers_xpath = "/html/body/div[4]/div/div[2]/div/div[2]/ul/div/li";
			List<WebElement> followers_elems = driver.findElements(By.xpath(followers_xpath));
			// followers counter
			System.out.print("\r" + "" + AppTimer.getCurrentTime() + " " + followers_elems.size() + "/" + size);

			AppTimer.randomSleep();
			long new_height = ((Long) js.executeScript("return followersbox.scrollHeight;", new Object[0])).longValue();
			if (new_height == last_height) {

				if (repeat != 0) {

					if (isScrapperDetected()) {
						System.out.println();
						System.out.println(AppTimer.getCurrentTime() + " " + "*** Scrapper detected  *** ");
					}
					break;
				}
				repeat = 1;

			} else {
				last_height = new_height;

				if (followers_elems.size() != size) {
					AppTimer.humanizeDelay(followers_elems.size());
				}

			}

		}

		AppTimer.randomSleep();

		System.out.println();
		String followers_xpath = "/html/body/div[4]/div/div[2]/div/div[2]/ul/div/li";
		List<WebElement> followers_elems = driver.findElements(By.xpath(followers_xpath));

		ArrayList<String> followersList = new ArrayList<String>();
		for (WebElement we : followers_elems) {
			String[] strFollowers = we.getText().split("\n");
			followersList.add(strFollowers[0]);
		}

		user.setFollowersList(followersList);

	}

	public void scrapFollowing(User user) {

		driver.findElement(By.partialLinkText("following")).click();
		int size = getFollowingSize(user.getUsername());
		AppTimer.sleep(700);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("followingbox = document.getElementsByClassName('_gs38e')[0];", new Object[0]);
		long last_height = ((Long) js.executeScript("return followingbox.scrollHeight;", new Object[0])).longValue();

		int repeat = 0;
		for (;;) {

			js.executeScript("followingbox.scrollTo(0, " + last_height + ");", new Object[0]);
			String following_xpath = "/html/body/div[4]/div/div[2]/div/div[2]/ul/div/li";
			List<WebElement> following_elems = driver.findElements(By.xpath(following_xpath));

			System.out.print("\r" + "" + AppTimer.getCurrentTime() + " " + following_elems.size() + "/" + size);

			AppTimer.randomSleep();
			long new_height = ((Long) js.executeScript("return followingbox.scrollHeight;", new Object[0])).longValue();
			if (new_height == last_height) {

				if (repeat != 0) {

					if (isScrapperDetected()) {
						System.out.println();
						System.out.println(AppTimer.getCurrentTime() + " " + "*** Scrapper detected *** ");
						// driver.quit();
						// System.exit(-1);
					}
					break;
				}
				repeat = 1;

			} else {
				last_height = new_height;
				if (following_elems.size() != size) {
					AppTimer.humanizeDelay(following_elems.size());
				}
			}

		}

		AppTimer.randomSleep();

		System.out.println();
		String following_xpath = "/html/body/div[4]/div/div[2]/div/div[2]/ul/div/li";
		List<WebElement> following_elems = driver.findElements(By.xpath(following_xpath));

		ArrayList<String> followingList = new ArrayList<String>();
		for (WebElement we : following_elems) {
			String[] strFollowing = we.getText().split("\n");
			// System.out.println(strFollowers[0]);
			followingList.add(strFollowing[0]);
		}
		user.setFollowingList(followingList);
	}

	public WebDriver getChrome() {
		return driver;
	}

	public void spaceKey() {

		Actions action = new Actions(driver);
		action.sendKeys(new CharSequence[] { Keys.SPACE }).build().perform();
	}

	public int getFollowersSize(String user) {

		int count;
		WebElement followerElement = driver.findElement(By.partialLinkText("follower"));
		String innerHtml = followerElement.getAttribute("innerHTML");
		Document document = Jsoup.parse(innerHtml);
		Elements spanElem = document.select("span");
		count = Integer.parseInt(spanElem.attr("title").replace(",", ""));
		// System.out.println("Followers : " + count);
		return count;

	}

	public int getFollowingSize(String user) {
		int count;
		WebElement followerElement = driver.findElement(By.partialLinkText("following"));
		String innerHtml = followerElement.getAttribute("innerHTML");
		Document document = Jsoup.parse(innerHtml);
		count = Integer.parseInt(document.select("span").text().replace(",", ""));
		// System.out.println("Following : " + count);
		// System.out.println();
		return count;
	}

	public int getPostsSize(String user) {
		int count;
		WebElement webel = driver.findElement(By.className("_fd86t "));
		String s = webel.getText().replaceAll(",", "");
		count = Integer.parseInt(s);
		return count;
	}

	public void quit() {
		if (driver != null) {
			driver.quit();
		}
	}

	public static Browser getInstance() {
		if (browser == null) {
			browser = new Browser();
		}
		return browser;
	}
}
