package com.tnaapp.tnalayout.activity.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.adapter.NewsSwipeFragmentAdapter;
import com.tnaapp.tnalayout.model.DepthPageTransformer;
import com.tnaapp.tnalayout.model.NewsFragmentItem;
import com.tnaapp.tnalayout.model.NewsItem;

import java.util.List;

/**
 * Created by dfChicken on 09/10/2015.
 */
public class SwipeNewsFragment extends Fragment {
    private static ViewPager mViewPager;
    private List<NewsItem> mNewsItems;
    private int viewItem;
    public List<NewsItem> getNewsItems() {
        return mNewsItems;
    }

    public void setNewsItems(List<NewsItem> mNewsItems) {
        this.mNewsItems = mNewsItems;
    }

    public void setDefaultViewItem(int position) {
        if(position>=0){
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

        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_news);
        mViewPager.setPageTransformer(true,new DepthPageTransformer());
        if(mNewsItems!=null){
            setupViewPager(mViewPager);
            jumpToNews(viewItem);
        }
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
        Log.d("jumpToNews",String.valueOf(position));
        mViewPager.setCurrentItem(position);
    }
}
