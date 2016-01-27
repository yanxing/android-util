package com.photo.browse.util.scrollerproxy;

import android.content.Context;

/**
 * Created by Gavin on 2015/8/24.
 */
public class IcsScroller extends GingerScroller {

    public IcsScroller(Context context) {
        super(context);
    }

    @Override
    public boolean computeScrollOffset() {
        return mScroller.computeScrollOffset();
    }
}
