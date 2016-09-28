package com.xenione.libs.swipemaker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.xenione.libs.swipemaker.orientation.HorizontalOrientationStrategy;
import com.xenione.libs.swipemaker.orientation.OrientationStrategy;
import com.xenione.libs.swipemaker.orientation.OrientationStrategyFactory;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class SwipeLayout extends FrameLayout {

    private static final String TAG = "SwipeLayout";

    public interface OnTranslateChangeListener {
        void onTranslateChange(float globalPercent, int index, float relativePercent);
    }

    OrientationStrategy orientationStrategy;

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
        orientationStrategy = new HorizontalOrientationStrategy(this);
    }

    public void setOrientation(int orientation) {
        orientationStrategy = OrientationStrategyFactory.get(orientation).make(this);
    }

    public void anchor(Integer... points) {
        orientationStrategy.setAnchor(points);
    }

    public void setOnTranslateChangeListener(OnTranslateChangeListener listener) {
        orientationStrategy.setOnTranslateChangeListener(listener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return orientationStrategy.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = orientationStrategy.onTouchEvent(event);
        if(!handled){
            super.onTouchEvent(event);
        }
        return true;
    }

    public void translateTo(int position) {
        orientationStrategy.translateTo(position);
    }
}
