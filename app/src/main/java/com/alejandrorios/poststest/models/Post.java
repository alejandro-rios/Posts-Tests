package com.alejandrorios.poststest.models;

import com.google.gson.annotations.SerializedName;

public class Post {

	@SerializedName("id")
	private String id;
	@SerializedName("userId")
	private String userId;
	@SerializedName("title")
	private String title;
	@SerializedName("body")
	private String body;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
