package com.alejandrorios.poststest.service.api;

import com.alejandrorios.poststest.models.Post;
import com.alejandrorios.poststest.models.PostRealm;
import com.alejandrorios.poststest.service.network.RetrofitProvider;
import com.alejandrorios.poststest.service.network.RetrofitProviderImpl;
import com.alejandrorios.poststest.ui.allPosts.AllPostsFragmentView;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPostList implements AllPostsFragmentView.GetPostInteractor {

	private class OrderingById extends Ordering<Post> {
		@Override
		public int compare(Post s1, Post s2) {
			return Ints.compare(Integer.parseInt(s1.getId()), Integer.parseInt(s2.getId()));
		}
	}

	@Override
	public void getPostList(final OnFinishedListener onFinishedListener) {
		final RetrofitProvider service = RetrofitProviderImpl.getRetrofitProvider().create(RetrofitProvider.class);
		final Call<List<Post>> call = service.getPostsList();

		call.enqueue(new Callback<List<Post>>() {
			@Override
			public void onResponse(final Call<List<Post>> call, final Response<List<Post>> response) {
				final List<Post> newPostList = new OrderingById().sortedCopy(response.body());
				final List<PostRealm> realmPosts = Post.savePostsListToRealm(newPostList);

				onFinishedListener.onFinished(realmPosts);
			}

			@Override
			public void onFailure(final Call<List<Post>> call, final Throwable t) {
				onFinishedListener.onFailure(t);
			}
		});
	}
}
