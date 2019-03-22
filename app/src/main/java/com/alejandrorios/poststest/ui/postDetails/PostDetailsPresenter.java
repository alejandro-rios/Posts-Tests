package com.alejandrorios.poststest.ui.postDetails;

import com.alejandrorios.poststest.models.Comment;
import com.alejandrorios.poststest.models.PostRealm;
import com.alejandrorios.poststest.models.User;
import com.alejandrorios.poststest.utils.RealmManager;

import java.util.List;

public class PostDetailsPresenter implements PostDetailsView.Presenter, PostDetailsView.GetUserInteractor.OnFinishedListener, PostDetailsView.GetCommentsInteractor.OnFinishedListener {

	private final PostDetailsView view;
	private final PostDetailsView.GetUserInteractor userInteractor;
	private final PostDetailsView.GetCommentsInteractor commentsInteractor;
	private PostRealm postData;
	private User userData;

	public PostDetailsPresenter(final PostDetailsView view, final PostDetailsView.GetUserInteractor userInteractor, final PostDetailsView.GetCommentsInteractor commentsInteractor) {
		this.view = view;
		this.userInteractor = userInteractor;
		this.commentsInteractor = commentsInteractor;
	}

	@Override
	public void fetchPostData(final PostRealm post) {
		postData = post;
		view.showProgress(true);
		userInteractor.getUserData(postData.getUserId(), this);
	}

	@Override
	public void markAsFavorite(final PostRealm post) {
		post.setRead(false);
		post.setFavorite(true);
		RealmManager.getInstance().update(post);
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
