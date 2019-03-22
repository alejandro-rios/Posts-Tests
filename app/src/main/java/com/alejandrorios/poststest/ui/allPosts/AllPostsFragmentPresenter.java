package com.alejandrorios.poststest.ui.allPosts;

import android.content.Context;
import android.content.Intent;

import com.alejandrorios.poststest.adapters.AllPostAdapter;
import com.alejandrorios.poststest.models.PostRealm;
import com.alejandrorios.poststest.ui.postDetails.PostDetails;
import com.alejandrorios.poststest.utils.ConfirmationDialog;
import com.alejandrorios.poststest.utils.RealmManager;
import com.google.gson.Gson;

import java.util.List;

public class AllPostsFragmentPresenter implements AllPostsFragmentView.Presenter, AllPostsFragmentView.GetPostInteractor.OnFinishedListener, AllPostAdapter.Delegate, ConfirmationDialog.Delegate {

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
		view.showMsg(false);
		view.showProgress(true);
		final List<PostRealm> allPosts = RealmManager.getInstance().getAll();

		if (allPosts.size() > 0) {
			view.showProgress(false);
			view.setupPostList(allPosts);
		} else {
			postList.getPostList(this);
		}
	}

	@Override
	public void onFinished(final List<PostRealm> postList) {
		if (view != null) {
			view.showProgress(false);
			view.setupPostList(postList);
		}
	}

	@Override
	public void onFailure(final Throwable t) {
		if (view != null) {
			view.showProgress(false);
			view.showMsg(true);
		}
	}

	@Override
	public void onItemClicked(final PostRealm post) {
		final Intent intent = new Intent(context, PostDetails.class);
		final Gson gson = new Gson();
		final String postJson = gson.toJson(post);

		post.setRead(false);
		RealmManager.getInstance().update(post);

		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.putExtra("post", postJson);
		context.startActivity(intent);
	}

	@Override
	public void deletePost(final PostRealm post) {
		RealmManager.getInstance().delete(post.getId());
		view.updatePostList();
	}

	@Override
	public void deleteAllPost() {
		RealmManager.getInstance().deleteAll();
		view.clearPosts();
		view.showMsg(true);
	}

	@Override
	public void confirm() {
		deleteAllPost();
	}
}
