package apps.xenione.com.swipelayout.example.swipe;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.lib.AbsCoordinatorLayout;
import apps.xenione.com.swipelayout.lib.SwipeLayout;

/**
 * Created on 06/04/16.
 */
public class TwoStepRightCoordinatorLayout extends AbsCoordinatorLayout implements SwipeLayout.OnTranslateChangeListener {

    private ImageView mBg;

    public interface OnDismissListener {
        void onDismissed();
    }

    private View mDelete;
    private View mAction;
    private SwipeLayout mForegroundView;
    private OnDismissListener mOnDismissListener;

    public Runnable initializeViews = new Runnable() {
        @Override
        public void run() {
            mForegroundView.translateTo(0);
        }
    };

    public TwoStepRightCoordinatorLayout(Context context) {
        super(context);
    }

    public TwoStepRightCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoStepRightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TwoStepRightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void doInitialViewsLocation() {
        mForegroundView.anchor(0, mDelete.getRight(), mAction.getRight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mBg=(ImageView)findViewById(R.id.bg_disc);
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
        if (index == 0) {
            mBg.setColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorAccent, getContext().getTheme()), PorterDuff.Mode.MULTIPLY);
        } else if (index == 1) {
            mBg.setColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorSecondaryAccent, getContext().getTheme()), PorterDuff.Mode.MULTIPLY);
        }
    }
}
