package apps.xenione.com.swipelayout.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import apps.xenione.com.swipelayout.R;

/**
 * Created on 06/04/16.
 */
public class CoordinatorLayout extends FrameLayout implements SwipeLayout.OnTranslateChangeListener {

    private View mBackgroundView;
    private SwipeLayout mForegroundView;

    public CoordinatorLayout(Context context) {
        super(context);
    }

    public CoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    }

    @Override
    public void onTranslateChange(float percent) {

    }
}
