package com.alejandrorios.poststest;

import com.alejandrorios.poststest.models.Comment;
import com.alejandrorios.poststest.models.Post;
import com.alejandrorios.poststest.models.User;

import java.util.List;

public interface PostDetailsView {
	interface Presenter {

		void fetchPostData(Post post);

		void markAsFavorite();

	}

	interface GetUserInteractor {

		interface OnFinishedListener {
			void onFinishedUser(List<User> user);

			void onFailureUser(Throwable t);
		}

		void getUserData(String userId, OnFinishedListener onFinishedListener);
	}

	interface GetCommentsInteractor {

		interface OnFinishedListener {
			void onFinishedComment(List<Comment> comments);

			void onFailureComment(Throwable t);
		}

		void getPostComments(String postId, OnFinishedListener onFinishedListener);
	}

	void showProgress(boolean show);

	void fillData(Post post, User user, List<Comment> comment);
}
