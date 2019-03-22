package com.alejandrorios.poststest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
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
import butterknife.OnClick;

public class FavoritesPostAdapter extends RecyclerView.Adapter<FavoritesPostAdapter.FavoritesHolder> {
	public interface Delegate {
		void onItemClicked(PostRealm post);

		void deleteFavorite(PostRealm post);
	}

	private Context context;
	private List<PostRealm> favoritesList;
	private RecyclerView recyclerView = null;
	private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
	private Delegate delegate;

	public FavoritesPostAdapter(final Context context, final List<PostRealm> favoritesList) {
		this.context = context;
		this.favoritesList = favoritesList;
		viewBinderHelper.setOpenOnlyOne(true);
	}

	@NonNull
	@Override
	public FavoritesHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
		return new FavoritesHolder(v);
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);

		this.recyclerView = recyclerView;
	}

	@Override
	public void onBindViewHolder(@NonNull final FavoritesHolder holder, final int position) {
		final PostRealm favoritePost = favoritesList.get(position);

		viewBinderHelper.bind(holder.srlFavorite, favoritePost.getId());
		holder.txtFavorite.setText(favoritePost.getTitle());

		holder.lytItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				delegate.onItemClicked(favoritePost);
			}
		});

		holder.favoriteDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TransitionManager.beginDelayedTransition(recyclerView);
				delegate.deleteFavorite(favoritePost);
				favoritesList.remove(position);
				notifyItemRemoved(position);
			}
		});
	}

	@Override
	public int getItemCount() {
		return favoritesList.size();
	}

	public class FavoritesHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.txtFavorite)
		TextView txtFavorite;
		@BindView(R.id.srlFavorite)
		SwipeRevealLayout srlFavorite;
		@BindView(R.id.lytFavoriteItem)
		View lytItem;
		@BindView(R.id.favoriteDelete)
		View favoriteDelete;

		public FavoritesHolder(final View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public TextView getTxtFavorite() {
			return txtFavorite;
		}

		public void setTxtFavorite(TextView txtFavorite) {
			this.txtFavorite = txtFavorite;
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