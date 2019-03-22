package com.alejandrorios.poststest.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;

import com.alejandrorios.poststest.R;

public class ConfirmationDialog implements DialogInterface {
	public interface Delegate {
		void confirm();
	}

	private final Context context;
	private Delegate delegate;
	private AlertDialog dialog;

	public ConfirmationDialog(final Context context) {
		this.context = context;
	}

	@Override
	public void cancel() {
		if (dialog != null) {
			dialog.cancel();
		}
	}

	@Override
	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	public void setDelegate(final Delegate delegate) {
		this.delegate = delegate;
	}

	public void show(final int messageId) {
		final Resources resources = context.getResources();
		final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogStyle);
		final String title = resources.getString(R.string.dialog_confirm_title);
		final String message = resources.getString(messageId);
		final String cancelButton = resources.getString(R.string.button_cancel);
		final String confirmButton = resources.getString(R.string.button_confirm);

		builder.setTitle(title).setMessage(message)
				.setPositiveButton(cancelButton, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setNegativeButton(confirmButton, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						delegate.confirm();
					}
				});
		dialog = builder.show();
	}
}
