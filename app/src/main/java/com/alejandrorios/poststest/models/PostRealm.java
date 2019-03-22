package com.alejandrorios.poststest.models;

import com.alejandrorios.poststest.utils.RealmManager;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PostRealm extends RealmObject {
	@PrimaryKey
	@SerializedName("id")
	private String id;
	@SerializedName("userId")
	private String userId;
	@SerializedName("title")
	private String title;
	@SerializedName("body")
	private String body;
	private boolean isRead;
	private boolean isFavorite;

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

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean favorite) {
		isFavorite = favorite;
	}

	public static List<PostRealm> getPostsInDatabase(){
		return RealmManager.getInstance().getRealmInstance().where(PostRealm.class)
				.findAll();
	}
}
