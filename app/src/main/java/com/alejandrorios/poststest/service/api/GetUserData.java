package com.alejandrorios.poststest.service.api;

import com.alejandrorios.poststest.models.User;
import com.alejandrorios.poststest.service.network.RetrofitProvider;
import com.alejandrorios.poststest.service.network.RetrofitProviderImpl;
import com.alejandrorios.poststest.ui.postDetails.PostDetailsView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserData implements PostDetailsView.GetUserInteractor {
	@Override
	public void getUserData(final String userId, final OnFinishedListener onFinishedListener) {
		final RetrofitProvider service = RetrofitProviderImpl.getRetrofitProvider().create(RetrofitProvider.class);
		final Call<List<User>> call = service.getUserData(userId);

		call.enqueue(new Callback<List<User>>() {
			@Override
			public void onResponse(final Call<List<User>> call, final Response<List<User>> response) {
				onFinishedListener.onFinishedUser(response.body());

			}

			@Override
			public void onFailure(final Call<List<User>> call, final Throwable t) {
				onFinishedListener.onFailureUser(t);
			}
		});
	}
}
