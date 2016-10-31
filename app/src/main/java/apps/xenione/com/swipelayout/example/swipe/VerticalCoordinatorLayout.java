package apps.xenione.com.swipelayout.example.swipe;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.xenione.libs.swipemaker.AbsCoordinatorLayout;
import com.xenione.libs.swipemaker.SwipeLayout;
import com.xenione.libs.swipemaker.SwipeLayout.Orientation;

import apps.xenione.com.swipelayout.R;


/**
 * Created on 06/04/16.
 */
public class VerticalCoordinatorLayout extends AbsCoordinatorLayout {

    private ImageView mBg;
    private View mDelete;
    private View mAction;
    private SwipeLayout mForegroundView;

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

    public VerticalCoordinatorLayout(Context context) {
        super(context);
    }

    public VerticalCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VerticalCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void doInitialViewsLocation() {
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mForegroundView.setOrientation(Orientation.VERTICAL);
        mBg = (ImageView) findViewById(R.id.bg_disc);
        mDelete = findViewById(R.id.delete);
        mAction = findViewById(R.id.action);
        int deleteTop = mForegroundView.getHeight() - mDelete.getTop();
        int actionTop = mForegroundView.getHeight() - mAction.getTop();
        mForegroundView.anchor(-actionTop, -deleteTop, 0);
    }

    @Override
    public void onTranslateChange(float global, int index, float relative) {
        if (index == 0) {
            if (relative == 0) {
                removeColorFilter();
            } else {
                applyColorFilter(Color.PINK);
            }
        } else if (index == 1 && relative > 0) {
            applyColorFilter(Color.BLUE);
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
