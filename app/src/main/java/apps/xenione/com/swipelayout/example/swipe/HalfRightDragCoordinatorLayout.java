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
public class HalfRightDragCoordinatorLayout extends AbsCoordinatorLayout {

    private View mBackgroundView;
    private SwipeLayout mForegroundView;

    public HalfRightDragCoordinatorLayout(Context context) {
        super(context);
    }

    public HalfRightDragCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HalfRightDragCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HalfRightDragCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void doInitialViewsLocation() {
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mBackgroundView = findViewById(R.id.backgroundView);
        mForegroundView.anchor(mBackgroundView.getRight(), mBackgroundView.getLeft());
    }

    @Override
    public void onTranslateChange(float global, int index, float relative) {
        mBackgroundView.setTranslationX((global - 1) * mBackgroundView.getWidth());
    }
}
