package com.alejandrorios.poststest;

import android.app.Application;
import android.content.Context;

import com.alejandrorios.poststest.utils.RealmDbModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PostsTestApplication extends Application {
	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();

		mContext = this;

		Realm.init(this);
		RealmConfiguration config = new RealmConfiguration.Builder()
				.name("poststest.realm")
				.modules(new RealmDbModule())
				.build();
		Realm.setDefaultConfiguration(config);
	}

	public static Context getContext() {
		return mContext;
	}
}
