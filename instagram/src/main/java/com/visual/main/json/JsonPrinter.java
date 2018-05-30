package com.visual.main.json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.visual.main.model.User;



public class JsonPrinter {
	
	 static Map<String, Object> theMap = new LinkedHashMap<>();
	
	
	public static Map<String, Object> getTheMap() {
		return theMap;
	}


	public static void setTheMap(Map<String, Object> theMap) {
		JsonPrinter.theMap = theMap;
	}


	public static void add(User user) {
		
		 /** dummy data **/
		 ArrayList<String> likedList = new ArrayList<String>();
         likedList.add("test_user");
         
         ArrayList<String> commentList = new ArrayList<String>();
	     commentList.add("comment");
         
		 JsonPost post = new JsonPost("htttp://not implemented",1,1,likedList,commentList);
		 
		 ArrayList<JsonPost> postList = new ArrayList<>();
	     postList.add(post);
	     
	     
	    
	     
		 /** dummy data **/
	     
	     JsonUser jsuser = new JsonUser(user.getUsername(),user.getFollowersSize(),user.getFollowingSize(),user.getFollowersList(),user.getFollowingList(),postList);
	     
	     theMap.put(user.getUsername(), jsuser);
	     
	 
	}
	
	

	public static void createJSON() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File("data.json"), theMap);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

}
