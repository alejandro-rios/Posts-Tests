package com.alejandrorios.poststest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alejandrorios.poststest.R;
import com.alejandrorios.poststest.models.Post;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostHolder> {
	public interface Delegate {
		void onItemClicked(final Post post);
	}

	private Context context;
	private List<Post> postList;
	private RecyclerView recyclerView = null;
	private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
	private Delegate delegate;

	public PostListAdapter(final Context context, final List<Post> postList) {
		this.context = context;
		this.postList = postList;
		viewBinderHelper.setOpenOnlyOne(true);
	}

	@NonNull
	@Override
	public PostHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
		return new PostHolder(v);
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);

		this.recyclerView = recyclerView;
	}

	@Override
	public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {
		final int adapterPosition = holder.getAdapterPosition();
		final Post post = postList.get(position);

		viewBinderHelper.bind(holder.swipeRevealLayout, post.getId());
		holder.postTitle.setText(post.getTitle());
		holder.postBadge.setVisibility(position <= 19 ? View.VISIBLE : View.INVISIBLE);
		holder.postStar.setVisibility(View.VISIBLE);

		holder.lytItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				delegate.onItemClicked(post);
			}
		});
	}

	@Override
	public int getItemCount() {
		return postList.size();
	}

	public class PostHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.txtPostTitle)
		TextView postTitle;
		@BindView(R.id.postBadge)
		View postBadge;
		@BindView(R.id.postStar)
		View postStar;
		@BindView(R.id.swipeRevealLayout)
		SwipeRevealLayout swipeRevealLayout;
		@BindView(R.id.lytItem)
		View lytItem;

		public PostHolder(final View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public TextView getPostTitle() {
			return postTitle;
		}

		public void setPostTitle(TextView postTitle) {
			this.postTitle = postTitle;
		}

		public View getPostBadge() {
			return postBadge;
		}

		public void setPostBadge(View postBadge) {
			this.postBadge = postBadge;
		}

		public View getPostStar() {
			return postStar;
		}

		public void setPostStar(View postStar) {
			this.postStar = postStar;
		}

		@OnClick(R.id.postDelete)
		void deletePost() {
			Log.wtf("Adapter", "Click example");
		}
	}

	public void setDelegate(final Delegate delegate) {
		this.delegate = delegate;
	}
}