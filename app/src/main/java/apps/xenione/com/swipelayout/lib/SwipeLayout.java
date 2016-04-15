package apps.xenione.com.swipelayout.lib;

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

import java.util.Arrays;

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
    private Anchor mAnchors;
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

    public void anchor(Integer... points) {
        mAnchors = new Anchor(points);
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
        return mAnchors.closeTo(currX);
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
            mOnTranslateChangeListener.onTranslateChange((float) desX / (mAnchors.getSupLimit() - mAnchors.getInfLimit()));
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
        return mAnchors.cropInLimts(x);
    }

    public static class Anchor {
        private Integer[] anchors;

        public Anchor(Integer[] anchors) {
            if (anchors.length < 2) {
                throw new IllegalArgumentException("Amount of anchor points provided to SwipeLayout have to be bigger than 2");
            }
            Arrays.sort(anchors);
            this.anchors = anchors;
        }

        public int getSupLimit(){
            return anchors[anchors.length-1];
        }

        public int getInfLimit(){
            return anchors[0];
        }

        private int closeTo(int point) {
            for (int i = 1; i < anchors.length; i++) {
                if (Math.abs(point - anchors[i]) > Math.abs(point - anchors[i - 1])) {
                    return anchors[i - 1];
                }
            }
            return anchors[anchors.length - 1];
        }
        public int cropInLimts(int x) {
            int inBounds = x;
            if (x < getInfLimit()) {
                inBounds = getInfLimit();
            } else if (x > getSupLimit()) {
                inBounds = getSupLimit();
            }
            return inBounds;
        }


    }
}
