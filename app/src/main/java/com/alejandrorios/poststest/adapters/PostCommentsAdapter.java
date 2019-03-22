package com.alejandrorios.poststest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alejandrorios.poststest.R;
import com.alejandrorios.poststest.models.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostCommentsAdapter extends RecyclerView.Adapter<PostCommentsAdapter.CommentHolder> {

	private Context context;
	private List<Comment> commentList;
	private RecyclerView recyclerView = null;

	public PostCommentsAdapter(final Context context, final List<Comment> commentList) {
		this.context = context;
		this.commentList = commentList;
	}

	@NonNull
	@Override
	public CommentHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
		return new CommentHolder(v);
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);

		this.recyclerView = recyclerView;
	}

	@Override
	public void onBindViewHolder(@NonNull final CommentHolder holder, final int position) {
		final Comment comment = commentList.get(position);

		holder.comment.setText(comment.getBody());
	}

	@Override
	public int getItemCount() {
		return commentList.size();
	}

	public class CommentHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.txtComment)
		TextView comment;

		public CommentHolder(final View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public TextView getComment() {
			return comment;
		}

		public void setComment(TextView comment) {
			this.comment = comment;
		}
	}
}