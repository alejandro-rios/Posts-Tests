package com.alejandrorios.poststest.service.api;

import android.util.Log;

import com.alejandrorios.poststest.fragments.AllPostsFragmentView;
import com.alejandrorios.poststest.models.Post;
import com.alejandrorios.poststest.service.network.RetrofitProvider;
import com.alejandrorios.poststest.service.network.RetrofitProviderImpl;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPostList implements AllPostsFragmentView.GetPostInteractor {
	@Override
	public void getPostList(final OnFinishedListener onFinishedListener) {
		final RetrofitProvider service = RetrofitProviderImpl.getRetrofitProvider().create(RetrofitProvider.class);
		final Call<List<Post>> call = service.getPostsList();
		Log.wtf("URL Called", call.request().url() + "");

		call.enqueue(new Callback<List<Post>>() {
			@Override
			public void onResponse(@NotNull final Call<List<Post>> call, @NotNull final Response<List<Post>> response) {
				onFinishedListener.onFinished(response.body());

			}

			@Override
			public void onFailure(@NotNull final Call<List<Post>> call, @NotNull final Throwable t) {
				onFinishedListener.onFailure(t);
			}
		});
	}
}
