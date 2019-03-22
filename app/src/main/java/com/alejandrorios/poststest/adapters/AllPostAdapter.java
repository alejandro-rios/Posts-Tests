package com.alejandrorios.poststest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alejandrorios.poststest.R;
import com.alejandrorios.poststest.models.PostRealm;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllPostAdapter extends RecyclerView.Adapter<AllPostAdapter.PostHolder> {
	public interface Delegate {
		void onItemClicked(PostRealm post);

		void deletePost(PostRealm post);
	}

	private Context context;
	private List<PostRealm> postList;
	private RecyclerView recyclerView = null;
	private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
	private Delegate delegate;

	public AllPostAdapter(final Context context, final List<PostRealm> postList) {
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
		final PostRealm post = postList.get(position);

		viewBinderHelper.bind(holder.srlAll, post.getId());
		holder.postTitle.setText(post.getTitle());
		holder.postBadge.setVisibility(post.isRead() ? View.VISIBLE : View.INVISIBLE);
		holder.postStar.setVisibility(post.isFavorite() ? View.VISIBLE : View.INVISIBLE);

		holder.lytItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				delegate.onItemClicked(post);
			}
		});

		holder.postDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TransitionManager.beginDelayedTransition(recyclerView);
				delegate.deletePost(post);
				postList.remove(position);
				notifyItemRemoved(position);
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
		@BindView(R.id.srlAll)
		SwipeRevealLayout srlAll;
		@BindView(R.id.lytItem)
		View lytItem;
		@BindView(R.id.postDelete)
		View postDelete;

		public PostHolder(final View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	public void setDelegate(final Delegate delegate) {
		this.delegate = delegate;
	}
}