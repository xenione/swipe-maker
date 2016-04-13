package apps.xenione.com.swipelayout.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import apps.xenione.com.swipelayout.R;

/**
 * Created on 06/04/16.
 */
public class CoordinatorLayout extends FrameLayout implements SwipeLayout.OnTranslateChangeListener {

    public interface OnDismissListener {
        void onDismissed();
    }

    private View mBackgroundView;
    private SwipeLayout mForegroundView;
    private OnDismissListener mOnDismissListener;

    public Runnable initializeViews = new Runnable() {
        @Override
        public void run() {
            mForegroundView.translateTo(0);
        }
    };

    public CoordinatorLayout(Context context) {
        super(context);
    }

    public CoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mForegroundView.setRightLimit(right);
        mForegroundView.setLeftLimit(left);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackgroundView = findViewById(R.id.backgroundView);
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mForegroundView.setOnTranslateChangeListener(CoordinatorLayout.this);
        init();
    }

    public void init() {
        if (!isInEditMode()) {
            ViewCompat.postOnAnimation(this, initializeViews);
        }
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mOnDismissListener = listener;
    }

    @Override
    public void onTranslateChange(float percent) {
        if (percent == 1) {
            mOnDismissListener.onDismissed();
        }
    }
}
