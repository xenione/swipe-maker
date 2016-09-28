package apps.xenione.com.swipelayout.example.swipe;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.xenione.libs.swipemaker.AbsCoordinatorLayout;
import com.xenione.libs.swipemaker.SwipeLayout;

import apps.xenione.com.swipelayout.R;

/**
 * Created on 06/04/16.
 */
public class BothSideCoordinatorLayout extends AbsCoordinatorLayout {

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
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mDelete = findViewById(R.id.delete);
        mAction = findViewById(R.id.action);
        mForegroundView.anchor(-mAction.getWidth(), 0, mDelete.getRight());
    }

    @Override
    public void onTranslateChange(float global, int index, float relative) {
    }
}
