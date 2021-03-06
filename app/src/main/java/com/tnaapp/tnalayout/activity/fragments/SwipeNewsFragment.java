package com.tnaapp.tnalayout.activity.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.adapter.NewsSwipeFragmentAdapter;
import com.tnaapp.tnalayout.control.SlideUpViewGroup;
import com.tnaapp.tnalayout.model.CustomViewPager;
import com.tnaapp.tnalayout.model.DepthPageTransformer;
import com.tnaapp.tnalayout.model.NewsFragmentItem;
import com.tnaapp.tnalayout.model.NewsItem;

import java.util.List;

/**
 * Created by dfChicken on 09/10/2015.
 */
public class SwipeNewsFragment extends Fragment implements CustomViewPager.CustomViewPagerListener {
    private static CustomViewPager mViewPager;
    private List<NewsItem> mNewsItems;
    private int viewItem;
    private FloatingActionButton mFloatingActionButton;
    private SlideUpViewGroup mSlideUpViewGroup;

    public List<NewsItem> getNewsItems() {
        return mNewsItems;
    }


    public void setNewsItems(List<NewsItem> mNewsItems) {
        this.mNewsItems = mNewsItems;
    }

    public void setDefaultViewItem(int position) {
        if (position >= 0) {
            this.viewItem = position;
            return;
        }
        this.viewItem = 0;
    }

    public SwipeNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_swipe_news, container, false);

        final View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        mViewPager = (CustomViewPager) rootView.findViewById(R.id.viewpager_news);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        if (mNewsItems != null) {
            setupViewPager(mViewPager);
            jumpToNews(viewItem);
        }
        mViewPager.setCustomViewPagerListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (currentPosition < position) {
                    Log.d("onPageScrolled", "Swipe Left to " + position);
                } else if (currentPosition > position) {
                    Log.d("onPageScrolled", "Swipe Right to " + position);
                }
                currentPosition = position; // Update current position
            }

            @Override
            public void onPageSelected(int arg0) {
                if (lastPage > arg0) {
                    Log.d("onPageSelected", "User Move to left");
                } else if (lastPage < arg0) {
                    Log.d("onPageSelected", "User Move to right");
                }
                lastPage = arg0;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d("onPageScrollChanged", "State: " + state);
            }
        });
        mSlideUpViewGroup = (SlideUpViewGroup) rootView.findViewById(R.id.newsVideosLayout);
        mSlideUpViewGroup.minimize();

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab_news);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClickFAB", "true");
                mSlideUpViewGroup.setVisibility(View.VISIBLE);
                mSlideUpViewGroup.maximize();

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final View decorView = getActivity().getWindow().getDecorView();
                decorView.setSystemUiVisibility(getView().SYSTEM_UI_FLAG_VISIBLE);
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    fragmentTransaction.replace(R.id.container_body, MainActivity.mHomeFragment);
                    fragmentTransaction.commit();
                    MainActivity.mFragmentDrawer.selectItem(0);
                    MainActivity.mToolbar.setTitle(getResources().getString(R.string.title_home));
                    return true;
                }
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        NewsSwipeFragmentAdapter adapter = new NewsSwipeFragmentAdapter(getChildFragmentManager());
        Log.d("mNewsItems", String.valueOf(mNewsItems.size()));
        for (int i = 0; i < mNewsItems.size(); i++) {
            NewsFragmentItem newsFragmentItem = new NewsFragmentItem();
            newsFragmentItem.setNewsItem(mNewsItems.get(i));
            adapter.addFragment(newsFragmentItem);
        }
        viewPager.setAdapter(adapter);
    }

    private void jumpToNews(int position) {
        Log.d("jumpToNews", String.valueOf(position));
        mViewPager.setCurrentItem(position);
    }

    private int currentPosition, lastPage;

    @Override
    public void onSwipeUp() {
        mFloatingActionButton.animate().translationY(mFloatingActionButton.getHeight() * 1.5f).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    @Override
    public void onSwipeDown() {
        mFloatingActionButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
}
