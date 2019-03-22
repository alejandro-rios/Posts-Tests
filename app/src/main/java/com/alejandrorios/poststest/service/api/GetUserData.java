package com.alejandrorios.poststest.service.api;

import android.util.Log;

import com.alejandrorios.poststest.PostDetailsView;
import com.alejandrorios.poststest.models.User;
import com.alejandrorios.poststest.service.network.RetrofitProvider;
import com.alejandrorios.poststest.service.network.RetrofitProviderImpl;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserData implements PostDetailsView.GetUserInteractor {
	@Override
	public void getUserData(final String userId, final OnFinishedListener onFinishedListener) {
		final RetrofitProvider service = RetrofitProviderImpl.getRetrofitProvider().create(RetrofitProvider.class);
		final Call<List<User>> call = service.getUserData(userId);
		Log.wtf("URL Called", call.request().url() + "");

		call.enqueue(new Callback<List<User>>() {
			@Override
			public void onResponse(@NotNull final Call<List<User>> call, @NotNull final Response<List<User>> response) {
				onFinishedListener.onFinishedUser(response.body());

			}

			@Override
			public void onFailure(@NotNull final Call<List<User>> call, @NotNull final Throwable t) {
				onFinishedListener.onFailureUser(t);
			}
		});
	}
}
