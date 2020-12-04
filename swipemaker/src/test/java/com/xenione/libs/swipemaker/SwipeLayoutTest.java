package com.xenione.libs.swipemaker;
/*
Copyright 07/06/2017 Eugeni Josep Senent i Gabriel

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.xenione.libs.swipemaker.orientation.OrientationStrategy;
import com.xenione.libs.swipemaker.orientation.OrientationStrategyFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@RunWith(JUnit4.class)
public class SwipeLayoutTest {

    private SwipeLayout swipeLayout;
    private OrientationStrategy orientationStrategy;

    @Before
    public void setup() {
        Context mockContext = mock(Context.class);
        swipeLayout = new SwipeLayout(mockContext);
        orientationStrategy = mock(OrientationStrategy.class);
        OrientationStrategyFactory orientationStrategyFactory = new OrientationStrategyFactory() {
            @Override
            public OrientationStrategy make(View view) {
                return orientationStrategy;
            }
        };
        swipeLayout.setOrientation(orientationStrategyFactory);
        verifyNoMoreInteractions(orientationStrategy);
    }

    @Test
    public void whenOnInterceptTouchEvent_forwardToOrientationStrategy() {
        MotionEvent event = mock(MotionEvent.class);
        swipeLayout.onInterceptTouchEvent(event);
        verify(orientationStrategy).onInterceptTouchEvent(event);
        verifyNoMoreInteractions(orientationStrategy);
    }

    @Test
    public void whenOnTouchEvent_forwardToOrientationStrategy() {
        MotionEvent event = mock(MotionEvent.class);
        swipeLayout.onTouchEvent(event);
        verify(orientationStrategy).onTouchEvent(event);
        verifyNoMoreInteractions(orientationStrategy);
    }

    @Test
    public void whenStartWith_forwardToOrientationStrategy() {
        swipeLayout.startWith(0);
        verify(orientationStrategy).startWith(0);
        verifyNoMoreInteractions(orientationStrategy);
    }

    @Test
    public void whenSetAnchor_forwardToOrientationStrategy() {
        Integer[] anchors = new Integer[]{9};
        swipeLayout.anchor(anchors);
        verify(orientationStrategy).setAnchor(anchors);
        verifyNoMoreInteractions(orientationStrategy);
    }

    @Test
    public void whenTranslateTo_forwardToOrientationStrategy() {
        swipeLayout.translateTo(0);
        verify(orientationStrategy).translateTo(0);
        verifyNoMoreInteractions(orientationStrategy);
    }

    @Test
    public void whenSetOnTranslateChangeListener_forwardToOrientationStrategy() {
        SwipeLayout.OnTranslateChangeListener listener = new SwipeLayout.OnTranslateChangeListener() {
            @Override
            public void onTranslateChange(float globalPercent, int index, float relativePercent) {

            }
        };
        swipeLayout.setOnTranslateChangeListener(listener);
        verify(orientationStrategy).setOnTranslateChangeListener(listener);
        verifyNoMoreInteractions(orientationStrategy);
    }

    @Test
    public void whenGetOnTheOrientationHorizontalEnum_getTheCorrectFactory() {
        SwipeLayout.Orientation orientation = SwipeLayout.Orientation.HORIZONTAL;
        assertThat(orientation.get(),instanceOf(SwipeLayout.HorizontalOrientationStrategyFactory.class));
    }

    @Test
    public void whenGetOnTheOrientationVerticalEnum_getTheCorrectFactory() {
        SwipeLayout.Orientation orientation = SwipeLayout.Orientation.VERTICAL;
        assertThat(orientation.get(),instanceOf(SwipeLayout.VerticalOrientationStrategyFactory.class));
    }





}
