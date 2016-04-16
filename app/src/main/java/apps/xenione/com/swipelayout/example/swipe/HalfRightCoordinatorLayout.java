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
public class HalfRightCoordinatorLayout extends AbsCoordinatorLayout implements SwipeLayout.OnTranslateChangeListener {

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

    public HalfRightCoordinatorLayout(Context context) {
        super(context);
    }

    public HalfRightCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HalfRightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HalfRightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void doInitialViewsLocation() {
        mForegroundView.anchor(mBackgroundView.getRight(), mBackgroundView.getLeft());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mBackgroundView = findViewById(R.id.backgroundView);
        mBackgroundView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDismissListener.onDismissed();
            }
        });
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mOnDismissListener = listener;
    }

    @Override
    public void onTranslateChange(float global, int index, float relative) {
    }
}
