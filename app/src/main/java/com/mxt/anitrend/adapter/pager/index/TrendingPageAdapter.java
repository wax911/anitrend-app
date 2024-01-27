package com.mxt.anitrend.adapter.pager.index;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mxt.anitrend.R;
import com.mxt.anitrend.base.custom.pager.BaseStatePageAdapter;
import com.mxt.anitrend.util.KeyUtil;
import com.mxt.anitrend.util.graphql.GraphUtil;
import com.mxt.anitrend.view.fragment.list.MediaLatestList;

/**
 * Created by max on 2017/10/30.
 */

public class TrendingPageAdapter extends BaseStatePageAdapter {

    public TrendingPageAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, context);
        setPagerTitles(R.array.trending_title);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MediaLatestList.newInstance(getParams(), GraphUtil.INSTANCE.getDefaultQuery(true)
                        .putVariable(KeyUtil.arg_mediaType, KeyUtil.ANIME)
                        .putVariable(KeyUtil.arg_sort, KeyUtil.TRENDING + KeyUtil.DESC));
            case 1:
                return MediaLatestList.newInstance(getParams(), GraphUtil.INSTANCE.getDefaultQuery(true)
                        .putVariable(KeyUtil.arg_mediaType, KeyUtil.MANGA)
                        .putVariable(KeyUtil.arg_sort, KeyUtil.TRENDING + KeyUtil.DESC));
            case 2:
                return MediaLatestList.newInstance(getParams(), GraphUtil.INSTANCE.getDefaultQuery(true)
                        .putVariable(KeyUtil.arg_mediaType, KeyUtil.ANIME)
                        .putVariable(KeyUtil.arg_sort, KeyUtil.ID + KeyUtil.DESC));
        }
        return null;
    }
}
