package com.alejandrorios.poststest.utils;

import com.alejandrorios.poststest.models.PostRealm;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmManager {

	private Realm realm;

	private static RealmManager ourInstance = new RealmManager();

	public static RealmManager getInstance() {
		return ourInstance;
	}

	public RealmManager() {
		realm = Realm.getDefaultInstance();
	}

	public Realm getRealmInstance() {
		return realm;
	}

	public List<PostRealm> getAll() {
		final List<PostRealm> results = realm.where(PostRealm.class).sort("id", Sort.ASCENDING).findAll();

		return realm.copyFromRealm(results);
	}

	public List<PostRealm> getFavorites() {
		final List<PostRealm> results = realm.where(PostRealm.class).equalTo("isFavorite", true).findAll();

		return realm.copyFromRealm(results);
	}

	public <E extends RealmObject> void update(final E object) {
		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(@NotNull Realm realm) {
				realm.copyToRealmOrUpdate(object);
			}
		});
	}

	public <E extends RealmObject> void updateList(final Iterable<E> objects) {
		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(@NotNull Realm realm) {
				realm.copyToRealmOrUpdate(objects);
			}
		});
	}

	public <E extends RealmObject> void saveList(final Iterable<E> objects, final Class<E> clazz) {
		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(@NotNull Realm realm) {
				realm.copyToRealmOrUpdate(objects);
			}
		});
	}

	public <E extends RealmObject> void delete(final String postId) {
		final RealmObject result = realm.where(PostRealm.class).equalTo("id", postId).findFirst();

		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(@NotNull Realm realm) {
				if (result == null) {
					return;
				}

				result.deleteFromRealm();
			}
		});
	}

	public <E extends RealmObject> void deleteAll() {
		final RealmResults<PostRealm> results = realm.where(PostRealm.class).findAll();

		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(@NotNull Realm realm) {
				if (results == null) {
					return;
				}

				results.deleteAllFromRealm();
			}
		});
	}
}
