package com.xenione.libs.swipemaker.orientation;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;

import com.xenione.libs.swipemaker.Anchors;
import com.xenione.libs.swipemaker.Position;
import com.xenione.libs.swipemaker.ScrollerHelper;
import com.xenione.libs.swipemaker.SwipeLayout;

/**
 * Created by Eugeni on 28/09/2016.
 */
public abstract class OrientationStrategy implements Runnable {

    ScrollerHelper mHelperScroller;
    final int mTouchSlop;

    private final Position mPositionInfo;
    private View mView;
    private SwipeLayout.OnTranslateChangeListener mOnTranslateChangeListener;

    public OrientationStrategy(View view) {
        mView = view;
        Context context = view.getContext();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mHelperScroller = new ScrollerHelper(context);
        mPositionInfo = new Position();
    }

    public void setAnchor(Integer... points) {
        mPositionInfo.anchors = Anchors.make(points);
    }

    public void setOnTranslateChangeListener(SwipeLayout.OnTranslateChangeListener listener) {
        mOnTranslateChangeListener = listener;
    }

    public abstract boolean onTouchEvent(MotionEvent e);

    public abstract boolean onInterceptTouchEvent(MotionEvent ev);

    abstract int getDelta();

    abstract void setDelta(int delta);

    public void translateBy(int delta){
        translateTo(getDelta() + delta);
    }

    public void translateTo(int distance) {
        int cropped = ensureInsideBounds(distance);
        if (getDelta() == cropped) {
            return;
        }
        setDelta(cropped);
        updatePosition(cropped);
    }

    private void updatePosition(int newPosition) {
        mPositionInfo.updatePosition(newPosition);
        notifyListener();
    }
    private void notifyListener() {
        if (mOnTranslateChangeListener != null) {
            mOnTranslateChangeListener.onTranslateChange(mPositionInfo.global, mPositionInfo.section, mPositionInfo.relative);
        }

        Log.i("translate", "global x: " + mPositionInfo.global + " section:" + mPositionInfo.section + " relative:" + mPositionInfo.relative);
    }

    private int ensureInsideBounds(int x) {
        return mPositionInfo.cropInLimits(x);
    }

    boolean fling() {
        int start = getDelta();
        int end = calculateEnd(start);
        boolean started = mHelperScroller.startScroll(start, end);
        ViewCompat.postOnAnimation(mView, this);
        return started;
    }

    boolean isFling() {
        return !mHelperScroller.isFinished();
    }

    @Override
    public void run() {
        if (mHelperScroller.computeScrollOffset()) {
            translateTo(mHelperScroller.getCurrX());
            ViewCompat.postOnAnimation(mView, this);
        }
    }

    private int calculateEnd(int currPosition) {
        return mPositionInfo.closeTo(currPosition);
    }

    public void disallowParentInterceptTouchEvent(boolean disallow) {
        ViewParent parent = mView.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallow);
        }
    }
}
