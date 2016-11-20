package com.xenione.libs.swipemaker.orientation;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Eugeni on 28/09/2016.
 */
public class HorizontalOrientationStrategy extends OrientationStrategy {

    private int mLastTouchX;
    private boolean mIsDragging;
    private View mView;

    public HorizontalOrientationStrategy(View view) {
        super(view);
        mView = view;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            fling();
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mLastTouchX = (int) ev.getX();
                mHelperScroller.finish();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = Math.abs((int) ev.getX() - mLastTouchX);
                mIsDragging = deltaX > mTouchSlop;
                if (mIsDragging) {
                    disallowParentInterceptTouchEvent(true);
                    mLastTouchX = (int) ev.getX();
                }
            }
        }

        return mIsDragging;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            if (mIsDragging) {
                disallowParentInterceptTouchEvent(false);
            }
            boolean isFling = fling();
            boolean handled = mIsDragging | isFling;
            mIsDragging = false;
            return handled;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mLastTouchX = (int) event.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = (int) event.getX() - mLastTouchX;
                if (mIsDragging) {
                    translateBy(deltaX);
                } else if (Math.abs(deltaX) > mTouchSlop) {
                    disallowParentInterceptTouchEvent(true);
                    mLastTouchX = (int) event.getX();
                    mIsDragging = true;
                }
                break;
            }
        }

        return mIsDragging;
    }

    @Override
    int getDelta() {
        return (int) mView.getTranslationX();
    }

    @Override
    void setDelta(int delta) {
        mView.setTranslationX(delta);
    }
}