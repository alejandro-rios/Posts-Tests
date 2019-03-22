package com.alejandrorios.poststest.fragments;

import android.content.Context;
import android.content.Intent;

import com.alejandrorios.poststest.PostDetails;
import com.alejandrorios.poststest.adapters.PostListAdapter;
import com.alejandrorios.poststest.models.Post;
import com.google.gson.Gson;

import java.util.List;

public class AllPostsFragmentPresenter implements AllPostsFragmentView.Presenter, AllPostsFragmentView.GetPostInteractor.OnFinishedListener, PostListAdapter.Delegate {

	private Context context;
	private final AllPostsFragmentView view;
	private final AllPostsFragmentView.GetPostInteractor postList;

	public AllPostsFragmentPresenter(final Context context, final AllPostsFragmentView view, final AllPostsFragmentView.GetPostInteractor postList) {
		this.context = context;
		this.view = view;
		this.postList = postList;
	}

	@Override
	public void getPostList() {
		view.showProgress(true);
		postList.getPostList(this);
	}

	@Override
	public void onFinished(final List<Post> postList) {
		if (view != null) {
			view.showProgress(false);
			view.setupPostList(postList);
		}
	}

	@Override
	public void onFailure(final Throwable t) {
		if (view != null) {
			view.showProgress(false);
		}
	}

	@Override
	public void onItemClicked(final Post post) {
		final Intent intent = new Intent(context, PostDetails.class);
		final Gson gson = new Gson();
		final String postJson = gson.toJson(post);

		intent.putExtra("post", postJson);
		context.startActivity(intent);
	}
}
