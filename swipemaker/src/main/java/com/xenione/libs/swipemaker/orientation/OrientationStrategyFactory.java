package com.xenione.libs.swipemaker.orientation;

import android.view.View;

/**
 * Created by Eugeni on 28/09/2016.
 */
public abstract class OrientationStrategyFactory {

    public abstract OrientationStrategy make(View view);

    public static OrientationStrategyFactory get(int type) {
        switch (type) {
            case OrientationStrategy.VERTICAL:
                return new VerticalOrientationStrategyFactory();

            default:
            case OrientationStrategy.HORIZONTAL: {
                return new HorizontalOrientationStrategyFactory();
            }
        }
    }

    private static class HorizontalOrientationStrategyFactory extends OrientationStrategyFactory {

        @Override
        public OrientationStrategy make(View view) {
            return new HorizontalOrientationStrategy(view);
        }
    }

    private static class VerticalOrientationStrategyFactory extends OrientationStrategyFactory {

        @Override
        public OrientationStrategy make(View view) {
            return new VerticalOrientationStrategy(view);
        }
    }
}
