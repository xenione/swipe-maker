package apps.xenione.com.swipelayout.example.swipe;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.lib.AbsCoordinatorLayout;
import apps.xenione.com.swipelayout.lib.SwipeLayout;

/**
 * Created on 06/04/16.
 */
public class BothSideCoordinatorLayout extends AbsCoordinatorLayout implements SwipeLayout.OnTranslateChangeListener {

    public interface OnDismissListener {
        void onDismissed();
    }

    private View mDelete;
    private View mAction;
    private SwipeLayout mForegroundView;

    public Runnable initializeViews = new Runnable() {
        @Override
        public void run() {
            mForegroundView.translateTo(0);
        }
    };

    public BothSideCoordinatorLayout(Context context) {
        super(context);
    }

    public BothSideCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BothSideCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BothSideCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void doInitialViewsLocation() {
        mForegroundView.anchor(-mAction.getWidth(), 0, mDelete.getRight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mDelete = findViewById(R.id.delete);
        mAction = findViewById(R.id.action);
    }

    public void init() {
        if (!isInEditMode()) {
            ViewCompat.postOnAnimation(this, initializeViews);
        }
    }

    @Override
    public void onTranslateChange(float global, int index, float relative) {
    }
}
