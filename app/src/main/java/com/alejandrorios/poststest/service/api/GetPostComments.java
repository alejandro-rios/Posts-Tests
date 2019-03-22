package com.alejandrorios.poststest.service.api;

import com.alejandrorios.poststest.models.Comment;
import com.alejandrorios.poststest.service.network.RetrofitProvider;
import com.alejandrorios.poststest.service.network.RetrofitProviderImpl;
import com.alejandrorios.poststest.ui.postDetails.PostDetailsView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPostComments implements PostDetailsView.GetCommentsInteractor {
	@Override
	public void getPostComments(final String postId, final OnFinishedListener onFinishedListener) {
		final RetrofitProvider service = RetrofitProviderImpl.getRetrofitProvider().create(RetrofitProvider.class);
		final Call<List<Comment>> call = service.getPostComments(postId);

		call.enqueue(new Callback<List<Comment>>() {
			@Override
			public void onResponse(final Call<List<Comment>> call, final Response<List<Comment>> response) {
				onFinishedListener.onFinishedComment(response.body());

			}

			@Override
			public void onFailure(final Call<List<Comment>> call, final Throwable t) {
				onFinishedListener.onFailureComment(t);
			}
		});

	}
}
