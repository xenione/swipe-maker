package apps.xenione.com.swipelayout.example.swipe;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xenione.libs.swipemaker.AbsCoordinatorLayout;
import com.xenione.libs.swipemaker.SwipeLayout;

import apps.xenione.com.swipelayout.R;


/**
 * Created on 06/04/16.
 */
public class SwingCoordinatorLayout extends AbsCoordinatorLayout {

    public interface OnDismissListener {
        void onLeftDismissed();
        void onRightDismissed();
    }

    public enum Color {
        PINK(R.color.colorAccent),
        BLUE(R.color.colorSecondaryAccent);

        private int resId;
        private ColorFilter color;

        Color(int resId) {
            this.resId = resId;
        }

        public ColorFilter getColor(Context context) {
            if (color == null) {
                color = new PorterDuffColorFilter(
                        ResourcesCompat.getColor(context.getResources(), resId, context.getTheme()), PorterDuff.Mode.MULTIPLY);
            }
            return color;
        }
    }
    private ImageView mBg;
    private SwipeLayout mForegroundView;
    private OnDismissListener mOnDismissListener;

    public SwingCoordinatorLayout(Context context) {
        super(context);
    }

    public SwingCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwingCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwingCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void doInitialViewsLocation() {
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mBg=(ImageView)findViewById(R.id.bg_disc);
        mForegroundView.anchor(-this.getWidth(), 0, this.getWidth());
        mForegroundView.setPivotX(this.getWidth() / 2);
        mForegroundView.setPivotY(0);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mOnDismissListener = listener;
    }

    @Override
    public void onTranslateChange(float global, int index, float relative) {
        mForegroundView.setRotation((0.5f - global) * 60);
        if (index == 0) {
            if (relative == 1) {
                removeColorFilter();
                mForegroundView.setAlpha(1);
            } else {
                mForegroundView.setAlpha(relative);
                applyColorFilter(Color.PINK);
            }
        } else {
            if (relative == 0) {
                removeColorFilter();
                mForegroundView.setAlpha(1);
            } else {
                mForegroundView.setAlpha(1 - relative);
                applyColorFilter(Color.BLUE);
            }
        }

        if (global == 1) {
            mOnDismissListener.onRightDismissed();
        } else if (global == 0) {
            mOnDismissListener.onLeftDismissed();
        }
    }

    private void removeColorFilter(){
        mBg.clearColorFilter();
    }

    private void applyColorFilter(Color color) {
        if (DrawableCompat.getColorFilter(mBg.getDrawable()) == color.getColor(getContext())) {
            return;
        }
        mBg.setColorFilter(color.getColor(getContext()));
    }
}
