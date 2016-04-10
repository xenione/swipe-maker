package apps.xenione.com.swipelayout.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.OverScroller;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class SwipeLayout extends FrameLayout {

    private static final String TAG = "SwipeLayout";

    private int mTouchSlop;
    private OverScroller mScroller;
    private int mLastTouchX;
    private boolean mIsDragging = false;
    private int mRightLimit = 300;
    private int mLeftLimit = 0;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
        mScroller = new OverScroller(this.getContext());
    }

    public void setRightLimit(int rightLimit) {
        mRightLimit = rightLimit;
    }
    public void setLeftLimit(int leftLimit) {
        mLeftLimit = leftLimit;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mIsDragging = false;
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if(!mScroller.isFinished()) {
                    mScroller.forceFinished(true);
                }
                mLastTouchX = (int) ev.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = Math.abs((int) ev.getX() - mLastTouchX);
                mIsDragging = deltaX > mTouchSlop;
                if (mIsDragging) {
                    disallowParentInterceptTouchEvent(true);
                }
                return mIsDragging;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = MotionEventCompat.getActionMasked(event);

        if (action == MotionEvent.ACTION_CANCEL) {
            mIsDragging = false;
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if(!mScroller.isFinished()) {
                    mScroller.forceFinished(true);
                }
                mLastTouchX = (int) event.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = (int) event.getX() - mLastTouchX;
                if (mIsDragging) {
                    translateBy(deltaX);
                } else if (Math.abs(deltaX) > mTouchSlop) {
                    disallowParentInterceptTouchEvent(true);
                    mIsDragging = true;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                disallowParentInterceptTouchEvent(false);
                fling();
                mIsDragging = false;
                break;
            }
        }

        return true;
    }

    public void disallowParentInterceptTouchEvent(boolean disallow) {
        ViewParent parent = this.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallow);
        }
    }

    private void fling() {
        int startX = 0;
        int endX = (getDeltaX() > ((mRightLimit - mLeftLimit) / 2)) ? mRightLimit : mLeftLimit;
        int deltaX = endX - getDeltaX();
        mScroller.startScroll(startX, 0, deltaX, 3 * 1000);
        ViewCompat.postOnAnimation(this, new Runnable() {
            @Override
            public void run() {
                if (mScroller.computeScrollOffset()) {
                    translateBy(mScroller.getCurrX());
                    ViewCompat.postOnAnimation(SwipeLayout.this, this);
                }
            }
        });
    }

    public void translateTo(int x) {
        int croppedX = ensureInsideBounds(x);
        if (getTranslationX() == croppedX) {
            return;
        }
        setDeltaX(croppedX);
        Log.i(TAG, "translation to: " + croppedX);
    }

    public void translateBy(int deltaX) {
        translateTo(getDeltaX() + deltaX);
    }

    public void setDeltaX(int deltaX) {
        setTranslationX(deltaX);
    }

    public int getDeltaX() {
        return (int) getTranslationX();
    }

    private int ensureInsideBounds(int x) {
        int inBounds = x;
        if (x < mLeftLimit) {
            inBounds = mLeftLimit;
        } else if (x > mRightLimit) {
            inBounds = mRightLimit;
        }
        return inBounds;
    }
}
