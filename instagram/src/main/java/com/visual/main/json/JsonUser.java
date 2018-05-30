package com.visual.main.json;

import java.util.ArrayList;


public class JsonUser {

    private String username;
    private int followerSize;
    private int followingSize;
    private ArrayList<String> followersList;
    private ArrayList<String> followingList;
    private ArrayList<JsonPost> postList;
    
	@Override
	public String toString() {
		return "User [username=" + username + ", followerSize=" + followerSize + ", followingSize=" + followingSize
				+ ", followersList=" + followersList + ", followingList=" + followingList + ", postList=" + postList
				+ "]";
	}
	public JsonUser(String username, int followerSize, int followingSize, ArrayList<String> followersList,
			ArrayList<String> followingList, ArrayList<JsonPost> postList) {
		super();
		this.username = username;
		this.followerSize = followerSize;
		this.followingSize = followingSize;
		this.followersList = followersList;
		this.followingList = followingList;
		this.postList = postList;
	}
	public ArrayList<JsonPost> getPostList() {
		return postList;
	}
	public void setPostList(ArrayList<JsonPost> postList) {
		this.postList = postList;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getFollowerSize() {
		return followerSize;
	}
	public void setFollowerSize(int followerSize) {
		this.followerSize = followerSize;
	}
	public int getFollowingSize() {
		return followingSize;
	}
	public void setFollowingSize(int followingSize) {
		this.followingSize = followingSize;
	}
	public ArrayList<String> getFollowersList() {
		return followersList;
	}
	public void setFollowersList(ArrayList<String> followersList) {
		this.followersList = followersList;
	}
	public ArrayList<String> getFollowingList() {
		return followingList;
	}
	public void setFollowingList(ArrayList<String> followingList) {
		this.followingList = followingList;
	}
}
