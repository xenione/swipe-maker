package com.xenione.libs.swipemaker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.xenione.libs.swipemaker.orientation.HorizontalOrientationStrategy;
import com.xenione.libs.swipemaker.orientation.OrientationStrategy;
import com.xenione.libs.swipemaker.orientation.OrientationStrategyFactory;
import com.xenione.libs.swipemaker.orientation.VerticalOrientationStrategy;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class SwipeLayout extends FrameLayout {

    private static final String TAG = "SwipeLayout";

    public enum Orientation {
        HORIZONTAL(0),
        VERTICAL(1);

        Orientation(int id) {
            this.id = id;
        }

        final int id;

        OrientationStrategyFactory get() {

            switch (this.id) {
                case 0: {
                    return new HorizontalOrientationStrategyFactory();
                }
                default:
                case 1: {
                    return new VerticalOrientationStrategyFactory();
                }
            }
        }
    }

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
        orientationStrategy = Orientation.HORIZONTAL.get().make(this);
    }

    public void setOrientation(Orientation orientation) {
        orientationStrategy = orientation.get().make(this);
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


    static class HorizontalOrientationStrategyFactory implements OrientationStrategyFactory {

        @Override
        public OrientationStrategy make(View view) {
            return new HorizontalOrientationStrategy(view);
        }
    }

    static class VerticalOrientationStrategyFactory implements OrientationStrategyFactory {

        @Override
        public OrientationStrategy make(View view) {
            return new VerticalOrientationStrategy(view);
        }
    }
}
