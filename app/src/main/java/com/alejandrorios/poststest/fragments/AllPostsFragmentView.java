package com.alejandrorios.poststest.fragments;

import com.alejandrorios.poststest.adapters.PostListAdapter;
import com.alejandrorios.poststest.models.Post;

import java.util.List;

public interface AllPostsFragmentView {
	interface Presenter extends PostListAdapter.Delegate {
		void getPostList();
	}

	interface GetPostInteractor {

		interface OnFinishedListener {
			void onFinished(List<Post> postList);

			void onFailure(Throwable t);
		}

		void getPostList(OnFinishedListener onFinishedListener);
	}

	void showProgress(boolean show);

	void setupPostList(List<Post> postList);
}
