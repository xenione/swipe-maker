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
public class BothSideCoordinatorLayout extends AbsCoordinatorLayout implements SwipeLayout.OnTranslateChangeListener {

    private View mDelete;
    private View mAction;
    private SwipeLayout mForegroundView;

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

    @Override
    public void onTranslateChange(float global, int index, float relative) {
    }
}
