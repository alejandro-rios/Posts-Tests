package com.alejandrorios.poststest.ui.favoritePosts;

import com.alejandrorios.poststest.adapters.FavoritesPostAdapter;
import com.alejandrorios.poststest.models.PostRealm;

import java.util.List;

public interface FavoritesPostsFragmentView {
	interface Presenter extends FavoritesPostAdapter.Delegate {
		void getFavoritePosts();
	}

	void setupFavoritePostList(List<PostRealm> postList);

	void showEmptyMsg(boolean show);
}
