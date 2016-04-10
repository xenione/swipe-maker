package apps.xenione.com.swipelayout.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
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
    private float mLastTouchX;
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
                mLastTouchX = ev.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float deltaX = Math.abs(ev.getX() - mLastTouchX);
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
                mLastTouchX = event.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float deltaX = event.getX() - mLastTouchX;
                if (mIsDragging) {
                    translateX(deltaX);
                } else if (Math.abs(deltaX) > mTouchSlop) {
                    disallowParentInterceptTouchEvent(true);
                    mIsDragging = true;
                }

                break;

            }
            case MotionEvent.ACTION_UP: {
                disallowParentInterceptTouchEvent(false);
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

    public void translateX(float deltaX) {
        float croppedX = ensureInsideBounds(getTranslationX() + deltaX);
        setTranslationX(croppedX);
        Log.i(TAG, "translation to: " + croppedX);
    }

    private float ensureInsideBounds(float x) {
        float inBounds = x;
        if (x < mLeftLimit) {
            inBounds = mLeftLimit;
        } else if (x > mRightLimit) {
            inBounds = mRightLimit;
        }
        return inBounds;
    }
}
