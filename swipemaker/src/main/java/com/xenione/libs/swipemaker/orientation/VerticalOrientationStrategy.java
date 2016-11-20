package com.xenione.libs.swipemaker.orientation;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Eugeni on 28/09/2016.
 */
public class VerticalOrientationStrategy extends OrientationStrategy {

    private int mLastTouchY;
    private boolean mIsDragging;
    private View mView;

    public VerticalOrientationStrategy(View view) {
        super(view);
        mView = view;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            fling();
            mIsDragging = false;
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mLastTouchY = (int) ev.getY();
                mHelperScroller.finish();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaY = Math.abs((int) ev.getY() - mLastTouchY);
                mIsDragging = deltaY > mTouchSlop;
                if (mIsDragging) {
                    disallowParentInterceptTouchEvent(true);
                    mLastTouchY = (int) ev.getY();
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
                mLastTouchY = (int) event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaY = (int) event.getY() - mLastTouchY;
                if (mIsDragging) {
                    translateBy(deltaY);
                } else if (Math.abs(deltaY) > mTouchSlop) {
                    disallowParentInterceptTouchEvent(true);
                    mLastTouchY = (int) event.getY();
                    mIsDragging = true;
                }
                break;
            }
        }

        return mIsDragging;
    }

    @Override
    int getDelta() {
        return (int) mView.getTranslationY();
    }

    @Override
    void setDelta(int delta) {
        mView.setTranslationY(delta);
    }
}