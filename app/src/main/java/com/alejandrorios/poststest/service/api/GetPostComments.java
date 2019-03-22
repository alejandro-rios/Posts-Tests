package com.alejandrorios.poststest.service.api;

import android.util.Log;

import com.alejandrorios.poststest.ui.postDetails.PostDetailsView;
import com.alejandrorios.poststest.models.Comment;
import com.alejandrorios.poststest.service.network.RetrofitProvider;
import com.alejandrorios.poststest.service.network.RetrofitProviderImpl;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPostComments implements PostDetailsView.GetCommentsInteractor {
	@Override
	public void getPostComments(final String postId, final OnFinishedListener onFinishedListener) {
		final RetrofitProvider service = RetrofitProviderImpl.getRetrofitProvider().create(RetrofitProvider.class);
		final Call<List<Comment>> call = service.getPostComments(postId);
		Log.wtf("URL Called", call.request().url() + "");

		call.enqueue(new Callback<List<Comment>>() {
			@Override
			public void onResponse(@NotNull final Call<List<Comment>> call, @NotNull final Response<List<Comment>> response) {
				onFinishedListener.onFinishedComment(response.body());

			}

			@Override
			public void onFailure(@NotNull final Call<List<Comment>> call, @NotNull final Throwable t) {
				onFinishedListener.onFailureComment(t);
			}
		});

	}
}
