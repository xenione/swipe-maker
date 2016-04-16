package apps.xenione.com.swipelayout.lib;

import android.content.Context;
import android.widget.OverScroller;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class ScrollerHelper {

    private static final int SCROLL_ANIMATION_DURATION = 3 * 1000;

    private OverScroller mScroller;

    public ScrollerHelper(Context context) {
        mScroller = new OverScroller(context);
    }

    public void startScroll(int startX, int endX) {
        if (startX == endX) {
            return;
        }
        int deltaX = endX - startX;
        mScroller.startScroll(startX, 0, deltaX, SCROLL_ANIMATION_DURATION);
    }

    public void finish() {
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
    }

    public boolean computeScrollOffset() {
        return mScroller.computeScrollOffset();
    }

    public int getCurrX() {
        return mScroller.getCurrX();
    }
}


