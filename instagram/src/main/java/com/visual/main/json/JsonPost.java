package com.visual.main.json;

import java.util.ArrayList;

public class JsonPost {
	
		private String url;
		private int likes;
		private int comments;
		private ArrayList<String> likedList;
		private ArrayList<String> commentList;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getLikes() {
			return likes;
		}
		public void setLikes(int likes) {
			this.likes = likes;
		}
		public int getComments() {
			return comments;
		}
		public void setComments(int comments) {
			this.comments = comments;
		}
		public ArrayList<String> getLikedList() {
			return likedList;
		}
		public void setLikedList(ArrayList<String> likedList) {
			this.likedList = likedList;
		}
		public ArrayList<String> getCommentList() {
			return commentList;
		}
		public void setCommentList(ArrayList<String> commentList) {
			this.commentList = commentList;
		}
		public JsonPost(String url, int likes, int comments, ArrayList<String> likedList, ArrayList<String> commentList) {
			super();
			this.url = url;
			this.likes = likes;
			this.comments = comments;
			this.likedList = likedList;
			this.commentList = commentList;
		}
		@Override
		public String toString() {
			return "Post [url=" + url + ", likes=" + likes + ", comments=" + comments + ", likedList=" + likedList
					+ ", commentList=" + commentList + "]";
		}
}
