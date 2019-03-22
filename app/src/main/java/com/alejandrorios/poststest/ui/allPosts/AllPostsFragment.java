package com.alejandrorios.poststest.ui.allPosts;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alejandrorios.poststest.R;
import com.alejandrorios.poststest.adapters.AllPostAdapter;
import com.alejandrorios.poststest.models.PostRealm;
import com.alejandrorios.poststest.service.api.GetPostList;
import com.alejandrorios.poststest.utils.ConfirmationDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllPostsFragment extends Fragment implements AllPostsFragmentView {

	private List<PostRealm> list;
	private AllPostAdapter postsAdapter;
	private AllPostsFragmentPresenter presenter;
	private Context context;
	private ConfirmationDialog deleteDialog;

	@BindView(R.id.pbPost)
	View pbPost;

	@BindView(R.id.rvPostList)
	RecyclerView rvPostList;

	public AllPostsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NotNull final LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_all_posts, container, false);
		ButterKnife.bind(this, view);
		setHasOptionsMenu(true);
		context = view.getContext();
		return view;
	}

	@Override
	public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		presenter = new AllPostsFragmentPresenter(context, this, new GetPostList());
	}

	@Override
	public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
		inflater.inflate(R.menu.menu_main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final int id = item.getItemId();

		if (id == R.id.action_refresh) {
			clearPosts();
			presenter.getPostList();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@OnClick(R.id.fabDelete)
	void onClickFab(final View view) {
		if (deleteDialog == null) {
			deleteDialog = new ConfirmationDialog(context);
			deleteDialog.setDelegate(presenter);
		}

		deleteDialog.show(R.string.dialog_delete_message);
	}

	@Override
	public void onStart() {
		super.onStart();
		presenter.getPostList();
	}

	@Override
	public void setupPostList(final List<PostRealm> postList) {
		list = postList;
		postsAdapter = new AllPostAdapter(context, list);
		final LinearLayoutManager llm = new LinearLayoutManager(context);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		rvPostList.setLayoutManager(llm);
		rvPostList.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
		rvPostList.setAdapter(postsAdapter);
		postsAdapter.setDelegate(presenter);
	}

	@Override
	public void clearPosts() {
		list.clear();
		postsAdapter.notifyDataSetChanged();
	}

	@Override
	public void showProgress(final boolean show) {
		final int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

		rvPostList.setVisibility(show ? View.GONE : View.VISIBLE);
		rvPostList.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(final Animator animation) {
				rvPostList.setVisibility(show ? View.GONE : View.VISIBLE);
			}
		});
		pbPost.setVisibility(show ? View.VISIBLE : View.GONE);
		pbPost.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(final Animator animation) {
				pbPost.setVisibility(show ? View.VISIBLE : View.GONE);
			}
		});
	}


}
