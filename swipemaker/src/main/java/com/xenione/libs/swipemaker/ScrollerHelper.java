package com.xenione.libs.swipemaker;

import android.content.Context;
import android.widget.OverScroller;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class ScrollerHelper {

    private OverScroller mScroller;

    public ScrollerHelper(Context context) {
        mScroller = new OverScroller(context);
    }

    public boolean startScroll(int start, int end) {
        if (start == end) {
            return false;
        }
        int delta = end - start;
        mScroller.startScroll(start, 0, delta, 0);
        return true;
    }

    public boolean startScroll(int start, int end, int duration) {
        if (start == end) {
            return false;
        }
        int delta = end - start;
        mScroller.startScroll(start, 0, delta, 0, duration);
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


