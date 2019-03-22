package com.alejandrorios.poststest.ui.allPosts;

import com.alejandrorios.poststest.adapters.AllPostAdapter;
import com.alejandrorios.poststest.models.PostRealm;

import java.util.List;

public interface AllPostsFragmentView {
	interface Presenter extends AllPostAdapter.Delegate {
		void getPostList();

		void deleteAllPost();
	}

	interface GetPostInteractor {

		interface OnFinishedListener {
			void onFinished(List<PostRealm> postList);

			void onFailure(Throwable t);
		}

		void getPostList(OnFinishedListener onFinishedListener);
	}

	void showProgress(boolean show);

	void setupPostList(List<PostRealm> postList);

	void showMsg(boolean show);

	void updatePostList();

	void clearPosts();
}
