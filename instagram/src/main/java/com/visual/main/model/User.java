package com.visual.main.model;

import java.util.ArrayList;

public class User {
	
	private String username;
	private int followersSize;
	private int followingSize;
	private int postsSize;
	
	
	private ArrayList<String> followersList;
	private ArrayList<String> followingList;
	private ArrayList<Post> postsList;
	
	public User(String username, int followersSize, int followingSize, int postsSize
			) {
		super();
		this.username = username;
		this.followersSize = followersSize;
		this.followingSize = followingSize;
		this.postsSize = postsSize;
	
	}
	public int getPostsSize() {
		return postsSize;
	}
	public void setPostsSize(int postsSize) {
		this.postsSize = postsSize;
	}
	public ArrayList<Post> getPostList() {
		return postsList;
	}
	public void setPostLinks(ArrayList<Post> postList) {
		this.postsList = postList;
	}
	public void setFollowersSize(int followersSize) {
		this.followersSize = followersSize;
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
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getFollowersSize() {
		return followersSize;
	}

	public int getFollowingSize() {
		return followingSize;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", followersSize=" + followersSize + ", followingSize=" + followingSize
				+ ", postsSize=" + postsSize + ", followersList=" + followersList + ", followingList=" + followingList
				+ ", postLinks=" + postsList + "]";
	}
}
