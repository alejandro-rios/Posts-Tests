package com.alejandrorios.poststest.ui.favoritePosts;

import android.content.Context;
import android.content.Intent;

import com.alejandrorios.poststest.adapters.FavoritesPostAdapter;
import com.alejandrorios.poststest.models.PostRealm;
import com.alejandrorios.poststest.ui.postDetails.PostDetails;
import com.alejandrorios.poststest.utils.RealmManager;
import com.google.gson.Gson;

import java.util.List;

public class FavoritesPostsFragmentPresenter implements FavoritesPostsFragmentView.Presenter, FavoritesPostAdapter.Delegate {

	private final Context context;
	private final FavoritesPostsFragmentView view;

	public FavoritesPostsFragmentPresenter(final FavoritesPostsFragmentView view, final Context context) {
		this.context = context;
		this.view = view;
	}

	@Override
	public void getFavoritePosts() {
		final List<PostRealm> favoritePosts = RealmManager.getInstance().getFavorites();

		if (favoritePosts.size() > 0) {
			view.showEmptyMsg(false);
			view.setupFavoritePostList(favoritePosts);
		} else {
			view.showEmptyMsg(true);
		}
	}

	@Override
	public void onItemClicked(final PostRealm post) {
		final Intent intent = new Intent(context, PostDetails.class);
		final Gson gson = new Gson();
		final String postJson = gson.toJson(post);

		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.putExtra("post", postJson);
		context.startActivity(intent);
	}

	@Override
	public void deleteFavorite(final PostRealm post) {
		RealmManager.getInstance().delete(post.getId());
		view.updateFavorites();
	}
}
