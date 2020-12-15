package com.xenione.libs.swipemaker.orientation;

import android.content.Context;
import androidx.core.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.OverScroller;

import com.xenione.libs.swipemaker.Anchors;
import com.xenione.libs.swipemaker.Position;
import com.xenione.libs.swipemaker.ScrollerHelper;
import com.xenione.libs.swipemaker.SwipeLayout;

/**
 * Created by Eugeni on 28/09/2016.
 */
public abstract class OrientationStrategy implements Runnable {


    private Position mPositionInfo;
    private View mView;
    private SwipeLayout.OnTranslateChangeListener mOnTranslateChangeListener;
    final int mTouchSlop;
    ScrollerHelper mHelperScroller;


    public OrientationStrategy(View view) {
        this(view, ViewConfiguration.get(view.getContext()).getScaledTouchSlop());
    }

    public OrientationStrategy(View view, int touchSlop) {
        mView = view;
        Context context = view.getContext();
        mTouchSlop = touchSlop;
        mHelperScroller = new ScrollerHelper(new OverScroller(context));
        mPositionInfo = new Position();
    }

    public void setAnchor(Integer... points) {
        mPositionInfo.setAnchors(Anchors.make(points));
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
        int end = endPositionFrom(start);
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

    private int endPositionFrom(int currPosition) {
        return mPositionInfo.closeTo(currPosition);
    }

    void disallowParentInterceptTouchEvent(boolean disallow) {
        ViewParent parent = mView.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallow);
        }
    }

    public void startWith(int position) {
        mPositionInfo.setCurrPos(position);
    }
}
