package com.xenione.libs.swipemaker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created on 06/04/16.
 */
public abstract class AbsCoordinatorLayout extends FrameLayout implements SwipeLayout.OnTranslateChangeListener {

    private SwipeLayout mForegroundView;

    public Runnable initializeViews = new Runnable() {
        @Override
        public void run() {
            mForegroundView.translateTo(0);
        }
    };

    public AbsCoordinatorLayout(Context context) {
        super(context);
    }

    public AbsCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AbsCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        doInitialViewsLocation();
    }

    public abstract void doInitialViewsLocation();

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mForegroundView.setOnTranslateChangeListener(this);
        sync();
    }

    public void sync() {
        if (!isInEditMode()) {
            ViewCompat.postOnAnimation(this, initializeViews);
        }
    }
}
