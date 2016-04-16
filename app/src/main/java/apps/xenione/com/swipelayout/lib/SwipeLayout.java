package apps.xenione.com.swipelayout.lib;

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

import java.util.Arrays;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class SwipeLayout extends FrameLayout implements Runnable {

    private static final String TAG = "SwipeLayout";

    public interface OnTranslateChangeListener {
        void onTranslateChange(float globalPercent, int index, float relativePercent);
    }

    private int mTouchSlop;
    private ScrollerHelper mHelperScroller;
    private int mLastTouchX;
    private boolean mIsDragging = false;
    private OnTranslateChangeListener mOnTranslateChangeListener;
    private PositionInfo mPositionInfo;

    private class PositionInfo {
        public Anchors anchors;
        public int currX;
        public int section;
        public float relative;
        public float global;

        public void decSection() {
            if (section == 0) {
                return;
            }
            section--;
        }

        public void incSection() {
            if (section == anchors.size() - 1) {
                return;
            }
            section++;
        }

        public boolean moveToRight(int newX) {
            return newX > currX;
        }

        private void updateSection(int newX){
            if (moveToRight(newX) && (newX <= anchors.anchorFor(mPositionInfo.section))) {
                decSection();
            } else if (newX > anchors.anchorFor(mPositionInfo.section + 1)) {
                incSection();
            }
        }

        public void updatePosition(int newX) {
            if (currX == newX) {
                return;
            }
            updateSection(newX);
            mPositionInfo.currX = newX;
            mPositionInfo.relative = relative(newX);
            mPositionInfo.global = global(newX);
        }

        public float global(int posX) {
            return anchors.distance(posX);
        }

        public float relative(int posX){
            return anchors.distance(mPositionInfo.section, posX);
        }

        public int closeTo(int posX) {
            return anchors.closeTo(section, posX);
        }

        public int cropInLimits(int posX){
            return anchors.cropInLimits(posX);
        }
    }

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
        mPositionInfo = new PositionInfo();
    }

    public void anchor(Integer... points) {
        mPositionInfo.anchors = Anchors.make(points);
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
        int endX = calculateEndX(startX);
        mHelperScroller.startScroll(startX, endX);
        ViewCompat.postOnAnimation(this, this);
    }

    private int calculateEndX(int currX) {
        return mPositionInfo.closeTo(currX);
    }

    public void translateTo(int x) {
        int croppedX = ensureInsideBounds(x);
        if (getTranslationX() == croppedX) {
            return;
        }
        setDeltaX(croppedX);
        updatePosition(croppedX);
    }

    private void updatePosition(int newX) {
        mPositionInfo.updatePosition(newX);
        notifyListener();
    }


    private void notifyListener() {
        if (mOnTranslateChangeListener != null) {
            mOnTranslateChangeListener.onTranslateChange(mPositionInfo.global, mPositionInfo.section, mPositionInfo.relative);
        }

        Log.i(TAG, "global x: " + mPositionInfo.global + " section:" + mPositionInfo.section + " relative:" + mPositionInfo.relative);
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
        return mPositionInfo.cropInLimits(x);
    }
}
