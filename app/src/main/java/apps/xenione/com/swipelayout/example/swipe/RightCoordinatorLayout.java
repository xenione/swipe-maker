package apps.xenione.com.swipelayout.example.swipe;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.lib.AbsCoordinatorLayout;
import apps.xenione.com.swipelayout.lib.SwipeLayout;

/**
 * Created on 06/04/16.
 */
public class RightCoordinatorLayout extends AbsCoordinatorLayout implements SwipeLayout.OnTranslateChangeListener {

    public interface OnDismissListener {
        void onDismissed();
    }

    private View mBackgroundView;
    private SwipeLayout mForegroundView;
    private OnDismissListener mOnDismissListener;

    public RightCoordinatorLayout(Context context) {
        super(context);
    }

    public RightCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void doInitialViewsLocation() {
        mForegroundView.anchor(this.getRight(), this.getLeft());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackgroundView = findViewById(R.id.backgroundView);
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mOnDismissListener = listener;
    }

    @Override
    public void onTranslateChange(float global, int index, float relative) {
        mBackgroundView.setAlpha(global);
        mForegroundView.setAlpha(1 - global);
        if (global == 1) {
            mOnDismissListener.onDismissed();
        }
    }
}
