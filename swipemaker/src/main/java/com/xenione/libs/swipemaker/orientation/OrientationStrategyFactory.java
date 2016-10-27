package com.xenione.libs.swipemaker.orientation;

import android.view.View;

/**
 * Created by Eugeni on 28/09/2016.
 */
public interface  OrientationStrategyFactory {
    OrientationStrategy make(View view);
}
