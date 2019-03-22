package com.alejandrorios.poststest.ui.allPosts;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.alejandrorios.poststest.R;
import com.alejandrorios.poststest.adapters.AllPostAdapter;
import com.alejandrorios.poststest.models.PostRealm;
import com.alejandrorios.poststest.service.api.GetPostList;
import com.alejandrorios.poststest.ui.MainActivity;
import com.alejandrorios.poststest.utils.ConfirmationDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllPostsFragment extends Fragment implements AllPostsFragmentView, View.OnClickListener {

	private List<PostRealm> list;
	private AllPostAdapter postsAdapter;
	private AllPostsFragmentPresenter presenter;
	private Context context;
	private ConfirmationDialog deleteDialog;
	private FloatingActionButton fabDelete;

	@BindView(R.id.pbPost)
	View pbPost;

	@BindView(R.id.rvPostList)
	RecyclerView rvPostList;

	@BindView(R.id.txtAllEmpty)
	TextView allEmpty;

	public AllPostsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
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
		try {
			fabDelete = ((MainActivity) getActivity()).getFabDelete();
			fabDelete.setOnClickListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		presenter = new AllPostsFragmentPresenter(context, this, new GetPostList());
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && list != null) {
			presenter.getPostList();
		}
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
			presenter.deleteAllPost();
			presenter.getPostList();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
		presenter.getPostList();
	}

	@Override
	public void setupPostList(final List<PostRealm> postList) {
		final LinearLayoutManager llm = new LinearLayoutManager(context);
		final int resId = R.anim.recycler_animation_falldown;
		final LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);

		list = postList;
		postsAdapter = new AllPostAdapter(context, list);

		llm.setOrientation(LinearLayoutManager.VERTICAL);
		rvPostList.setLayoutManager(llm);
		rvPostList.setLayoutAnimation(animation);
		rvPostList.setAdapter(postsAdapter);
		postsAdapter.setDelegate(presenter);

	}

	@Override
	public void showMsg(boolean show) {
		rvPostList.setVisibility(show ? View.GONE : View.VISIBLE);
		allEmpty.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	@Override
	public void updatePostList() {
		postsAdapter.notifyDataSetChanged();
	}

	@Override
	public void clearPosts() {
		list.clear();
		updatePostList();
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

	@Override
	public void onClick(View v) {
		if (deleteDialog == null) {
			deleteDialog = new ConfirmationDialog(context);
			deleteDialog.setDelegate(presenter);
		}

		deleteDialog.show(R.string.dialog_delete_message);
	}
}
