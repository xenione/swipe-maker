package com.xenione.libs.swipemaker;

import android.content.Context;
import android.widget.OverScroller;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class ScrollerHelper {

    private static final int SCROLL_ANIMATION_DURATION = 3 * 1000;

    private OverScroller mScroller;

    public ScrollerHelper(Context context) {
        mScroller = new OverScroller(context);
    }

    public boolean startScroll(int startX, int endX) {
        if (startX == endX) {
            return false;
        }
        int deltaX = endX - startX;
        mScroller.startScroll(startX, 0, deltaX, SCROLL_ANIMATION_DURATION);
        return true;
    }

    public void finish() {
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
    }

    public boolean isFinished() {
        return mScroller.isFinished();
    }

    public boolean computeScrollOffset() {
        return mScroller.computeScrollOffset();
    }

    public int getCurrX() {
        return mScroller.getCurrX();
    }
}


