package com.alejandrorios.poststest.ui.favoritePosts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alejandrorios.poststest.R;
import com.alejandrorios.poststest.adapters.FavoritesPostAdapter;
import com.alejandrorios.poststest.models.PostRealm;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesPostsFragment extends Fragment implements FavoritesPostsFragmentView {

	@BindView(R.id.rvFavoritesList)
	RecyclerView rvFavoritesList;

	@BindView(R.id.txtFavoritesEmpty)
	TextView favoritesEmpty;

	private FavoritesPostsFragmentPresenter presenter;
	private FavoritesPostAdapter favoritesAdapter;
	private Context context;

	public FavoritesPostsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_favorites_posts, container, false);
		ButterKnife.bind(this, view);
		context = view.getContext();
		return view;
	}

	@Override
	public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		presenter = new FavoritesPostsFragmentPresenter(this, context);
	}

	@Override
	public void onStart() {
		super.onStart();
		presenter.getFavoritePosts();
	}

	@Override
	public void setupFavoritePostList(final List<PostRealm> favoritesList) {
		favoritesAdapter = new FavoritesPostAdapter(context, favoritesList);
		final LinearLayoutManager llm = new LinearLayoutManager(context);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		rvFavoritesList.setLayoutManager(llm);
		rvFavoritesList.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
		rvFavoritesList.setAdapter(favoritesAdapter);
		favoritesAdapter.setDelegate(presenter);
	}

	@Override
	public void showEmptyMsg(final boolean show) {
		rvFavoritesList.setVisibility(show ? View.GONE : View.VISIBLE);
		favoritesEmpty.setVisibility(show ? View.VISIBLE : View.GONE);
	}
}
