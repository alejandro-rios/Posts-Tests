package com.alejandrorios.poststest.service.network;

import com.alejandrorios.poststest.models.Comment;
import com.alejandrorios.poststest.models.Post;
import com.alejandrorios.poststest.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitProvider {

	@GET("posts")
	Call<List<Post>> getPostsList();

	@GET("users")
	Call<List<User>> getUserData(@Query("id") String userId);

	@GET("comments")
	Call<List<Comment>> getPostComments(@Query("postId") String postId);
}
