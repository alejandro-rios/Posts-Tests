package com.alejandrorios.poststest;

import com.alejandrorios.poststest.models.Comment;
import com.alejandrorios.poststest.models.Post;
import com.alejandrorios.poststest.models.User;

import java.util.List;

public class PostDetailsPresenter implements PostDetailsView.Presenter, PostDetailsView.GetUserInteractor.OnFinishedListener, PostDetailsView.GetCommentsInteractor.OnFinishedListener{

	private final PostDetailsView view;
	private final PostDetailsView.GetUserInteractor userInteractor;
	private final PostDetailsView.GetCommentsInteractor commentsInteractor;
	private Post postData;
	private User userData;

	public PostDetailsPresenter(final PostDetailsView view, final PostDetailsView.GetUserInteractor userInteractor, final PostDetailsView.GetCommentsInteractor commentsInteractor) {
		this.view = view;
		this.userInteractor = userInteractor;
		this.commentsInteractor = commentsInteractor;
	}

	@Override
	public void fetchPostData(final Post post) {
		postData = post;
		view.showProgress(true);
		userInteractor.getUserData(postData.getUserId(), this);
	}

	@Override
	public void markAsFavorite() {

	}

	@Override
	public void onFinishedUser(final List<User> user) {
		userData = user.get(0);
		commentsInteractor.getPostComments(postData.getId(), this);
	}

	@Override
	public void onFailureUser(final Throwable t) {

	}

	@Override
	public void onFinishedComment(final List<Comment> comments) {
		view.showProgress(false);
		view.fillData(postData, userData, comments);
	}

	@Override
	public void onFailureComment(final Throwable t) {

	}
}
