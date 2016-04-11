package apps.xenione.com.swipelayout.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class SwipeLayout extends FrameLayout implements Runnable{

    private static final String TAG = "SwipeLayout";

    public interface OnTranslateChangeListener {
        void onTranslateChange(float percent);
    }

    private int mTouchSlop;
    private ScrollerHelper mHelperScroller;
    private int mLastTouchX;
    private boolean mIsDragging = false;
    private int mRightLimit;
    private int mLeftLimit;
    private OnTranslateChangeListener mOnTranslateChangeListener;

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
        mHelperScroller = new ScrollerHelper(this.getContext());
    }

    public void setRightLimit(int rightLimit) {
        mRightLimit = rightLimit;
    }
    public void setLeftLimit(int leftLimit) {
        mLeftLimit = leftLimit;
    }

    public void setOnTranslateChangeListener(OnTranslateChangeListener listener) {
        mOnTranslateChangeListener = listener;
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
                mHelperScroller.finish();
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
                mHelperScroller.finish();
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

    @Override
    public void run() {
        if (mHelperScroller.computeScrollOffset()) {
            translateTo(mHelperScroller.getCurrX());
            ViewCompat.postOnAnimation(SwipeLayout.this, this);
        }
    }

    public void disallowParentInterceptTouchEvent(boolean disallow) {
        ViewParent parent = this.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallow);
        }
    }

    private void fling() {
        int startX = getDeltaX();
        int endX = (startX > ((mRightLimit - mLeftLimit) / 2)) ? mRightLimit : mLeftLimit;
        mHelperScroller.startScroll(startX, endX);
        ViewCompat.postOnAnimation(this, this);
    }

    public void translateTo(int x) {
        int croppedX = ensureInsideBounds(x);
        if (getTranslationX() == croppedX) {
            return;
        }
        setDeltaX(croppedX);
        notifyListener(croppedX);
    }

    private void notifyListener(int desX) {
        if (mOnTranslateChangeListener != null) {
            mOnTranslateChangeListener.onTranslateChange((float) desX / (mRightLimit - mLeftLimit));
        }
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
