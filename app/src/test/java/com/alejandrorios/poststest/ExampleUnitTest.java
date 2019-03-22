package com.alejandrorios.poststest;

import android.content.Context;

import com.alejandrorios.poststest.models.PostRealm;
import com.alejandrorios.poststest.service.api.GetPostList;
import com.alejandrorios.poststest.ui.allPosts.AllPostsFragmentPresenter;
import com.alejandrorios.poststest.ui.allPosts.AllPostsFragmentView;
import com.alejandrorios.poststest.utils.RealmDbModule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

	@Mock
	private GetPostList postListCall;

	@Mock
	private AllPostsFragmentView view;

	@Captor
	private ArgumentCaptor<GetPostList.OnFinishedListener> postCallCaptor;

	private AllPostsFragmentPresenter presenter;
	private Context context = mock(Context.class);
	private static List<PostRealm> postList = new ArrayList<PostRealm>();

	@Before
	public void setupAllPostsFragmentPresenter() {
		MockitoAnnotations.initMocks(this);

		presenter = new AllPostsFragmentPresenter(context, view, postListCall);

		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
		postList.add(new PostRealm());
	}

	@Test
	public void getPostsFromRepoAndShowIntoView() {
		presenter.getPostList();

		verify(postListCall).getPostList(postCallCaptor.capture());
		postCallCaptor.getValue().onFinished(postList);

		verify(view).showProgress(false);
		verify(view).setupPostList(postList);

	}
}