package com.mxt.anitrend.adapter.pager.index;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mxt.anitrend.view.index.fragment.CompletedAnimeFragment;
import com.mxt.anitrend.view.index.fragment.DroppedAnimeFragment;
import com.mxt.anitrend.view.index.fragment.OnHoldAnimeFragment;
import com.mxt.anitrend.view.index.fragment.PlanToWatchFragment;
import com.mxt.anitrend.view.index.fragment.WatchingFragment;
import com.mxt.anitrend.viewmodel.pager.DefaultStatePagerAdapter;

import java.util.Locale;

/**
 * Created by Maxwell on 11/20/2016.
 */

public class MyAnimePageAdapter extends DefaultStatePagerAdapter {

    private int user_id;

    public MyAnimePageAdapter(FragmentManager manager, int id, String[] titles) {
        super(manager);
        user_id = id;
        mTitles = titles;
        mPages = 5;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return WatchingFragment.newInstance(user_id);
            case 1:
                return PlanToWatchFragment.newInstance(user_id);
            case 2:
                return CompletedAnimeFragment.newInstance(user_id);
            case 3:
                return OnHoldAnimeFragment.newInstance(user_id);
            case 4:
                return DroppedAnimeFragment.newInstance(user_id);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale locale = Locale.getDefault();
        return mTitles[position].toUpperCase(locale);
    }
}
