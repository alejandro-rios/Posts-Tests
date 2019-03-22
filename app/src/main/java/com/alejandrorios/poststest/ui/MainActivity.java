package com.alejandrorios.poststest.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.alejandrorios.poststest.R;
import com.alejandrorios.poststest.ui.allPosts.AllPostsFragment;
import com.alejandrorios.poststest.ui.favoritePosts.FavoritesPostsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.vpMain)
	ViewPager vpMain;

	@BindView(R.id.tabsMain)
	TabLayout tabsMain;

	@BindView(R.id.fabDelete)
	FloatingActionButton fabDelete;

	public FloatingActionButton getFabDelete() {
		return fabDelete;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		setSupportActionBar(toolbar);
		setUpViewPager(vpMain);

		tabsMain.setupWithViewPager(vpMain);
		vpMain.addOnPageChangeListener(this);
	}

	private void setUpViewPager(final ViewPager vpMain) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(new AllPostsFragment(), "ALL");
		adapter.addFragment(new FavoritesPostsFragment(), "FAVORITES");
		vpMain.setAdapter(adapter);
	}

	@Override
	public void onPageScrolled(int i, float v, int i1) {

	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
			case 0:
				fabDelete.show();
				break;

			default:
				fabDelete.hide();
				break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int i) {

	}

	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();

		public ViewPagerAdapter(final FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		public void addFragment(final Fragment fragment, final String title) {
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(final int position) {
			return mFragmentTitleList.get(position);
		}
	}
}
