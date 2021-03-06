package com.alejandrorios.poststest.models;

import com.alejandrorios.poststest.utils.RealmManager;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

	private PostRealm saveToRealm() {
		final PostRealm postRealm = new PostRealm();
		final int idPost = Integer.parseInt(id);

		postRealm.setId(id);
		postRealm.setBody(body);
		postRealm.setTitle(title);
		postRealm.setUserId(userId);
		postRealm.setRead(idPost <= 20);
		postRealm.setFavorite(false);
		return postRealm;
	}

	public static List<PostRealm> savePostsListToRealm(final List<Post> posts) {
		List<PostRealm> postRealms = new ArrayList<>();
		for (Post post : posts) {
			postRealms.add(post.saveToRealm());
		}
		RealmManager.getInstance().saveList(postRealms, PostRealm.class);

		return postRealms;
	}
}
