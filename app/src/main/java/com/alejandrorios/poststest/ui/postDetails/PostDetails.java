package com.alejandrorios.poststest.ui.postDetails;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alejandrorios.poststest.R;
import com.alejandrorios.poststest.adapters.PostCommentsAdapter;
import com.alejandrorios.poststest.models.Comment;
import com.alejandrorios.poststest.models.PostRealm;
import com.alejandrorios.poststest.models.User;
import com.alejandrorios.poststest.service.api.GetPostComments;
import com.alejandrorios.poststest.service.api.GetUserData;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetails extends AppCompatActivity implements PostDetailsView {

	@BindView(R.id.pbFav)
	ProgressBar pbFav;

	@BindView(R.id.frPostDetails)
	View frPostDetails;

	@BindView(R.id.txtPostContent)
	TextView postContent;

	@BindView(R.id.txtUserName)
	TextView userName;

	@BindView(R.id.txtUserEmail)
	TextView userEmail;

	@BindView(R.id.txtUserPhone)
	TextView userPhone;

	@BindView(R.id.txtUserWebsite)
	TextView userWebsite;

	@BindView(R.id.rvPostComments)
	RecyclerView rvPostComments;

	private PostCommentsAdapter commentsAdapter;
	private PostDetailsPresenter presenter;
	private PostRealm postData = new PostRealm();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_details);
		ButterKnife.bind(this);
		final Bundle arguments = getIntent().getExtras();

		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (arguments != null) {
			final Gson gson = new Gson();
			postData = gson.fromJson(getIntent().getStringExtra("post"), PostRealm.class);
		}

		presenter = new PostDetailsPresenter(this, new GetUserData(), new GetPostComments());
		presenter.fetchPostData(postData);
	}

	@Override
	public void fillData(final PostRealm post, final User user, final List<Comment> comments) {
		postContent.setText(post.getBody());
		userName.setText(String.format("%s %s", getResources().getString(R.string.post_detail_username), user.getName()));
		userEmail.setText(String.format("%s %s", getResources().getString(R.string.post_detail_user_email), user.getEmail()));
		userPhone.setText(String.format("%s %s", getResources().getString(R.string.post_details_user_phone), user.getPhone()));
		userWebsite.setText(String.format("%s %s", getResources().getString(R.string.post_details_user_web), user.getWebsite()));

		setupCommentsList(comments);
	}

	private void setupCommentsList(final List<Comment> comments) {
		commentsAdapter = new PostCommentsAdapter(this, comments);
		final LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		rvPostComments.setLayoutManager(llm);
		rvPostComments.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
		rvPostComments.setAdapter(commentsAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.menu_fav, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final int id = item.getItemId();

		if (id == R.id.action_favorite) {
			presenter.markAsFavorite(postData);
			Toast.makeText(this, "Marked as favorite", Toast.LENGTH_SHORT).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}

	@Override
	public void showProgress(final boolean show) {
		final int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

		frPostDetails.setVisibility(show ? View.GONE : View.VISIBLE);
		frPostDetails.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(final Animator animation) {
				frPostDetails.setVisibility(show ? View.GONE : View.VISIBLE);
			}
		});
		pbFav.setVisibility(show ? View.VISIBLE : View.GONE);
		pbFav.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(final Animator animation) {
				pbFav.setVisibility(show ? View.VISIBLE : View.GONE);
			}
		});
	}
}
