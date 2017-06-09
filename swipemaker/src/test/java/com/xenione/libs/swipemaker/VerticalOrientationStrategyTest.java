package com.xenione.libs.swipemaker;
/*
Copyright 05/06/2017 Eugeni Josep Senent i Gabriel

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

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import com.xenione.libs.swipemaker.orientation.VerticalOrientationStrategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class VerticalOrientationStrategyTest {

    final int touchSlop = 50;
    private View view;
    private ViewParent parent;
    private VerticalOrientationStrategy vOStrategy;

    @Before
    public void setup() {
        view = mock(View.class);
        parent = mock(ViewParent.class);
        when(view.getParent()).thenReturn(parent);
        vOStrategy = new VerticalOrientationStrategy(view, touchSlop);
    }

    @Test
    public void whenSlideGesture_ShouldDisableParentInterceptTouch() {
        applySlideGesture();
        Mockito.verify(parent).requestDisallowInterceptTouchEvent(true);
    }

    @Test
    public void whenSlideGesture_ShouldForwardToOnTouchEvent() {
        boolean intercept = applySlideGesture();
        assertThat(intercept, is(Boolean.TRUE));
    }

    @Test
    public void whenCancelTouch_ShouldEnableParentInterceptTouch() {
        applySlideGesture();

        MotionEvent eventCancel = mock(MotionEvent.class);
        when(eventCancel.getAction()).thenReturn(MotionEvent.ACTION_CANCEL);
        when(eventCancel.getY()).thenReturn(0f);
        vOStrategy.onTouchEvent(eventCancel);

        Mockito.verify(parent).requestDisallowInterceptTouchEvent(false);
    }

    @Test
    public void whenSlideGesture_ShouldViewTranslate() {
        applySlideGesture();

        MotionEvent eventDrag = mock(MotionEvent.class);
        when(eventDrag.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(eventDrag.getY()).thenReturn(100f);
        vOStrategy.onTouchEvent(eventDrag);

        Mockito.verify(view).setTranslationY(49f);
    }

    @Test
    public void whenSlideGestureAndThenNoIncreaseTouch_ShouldViewTranslateWithZero() {
        applySlideGesture();

        MotionEvent eventDrag = mock(MotionEvent.class);
        when(eventDrag.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(eventDrag.getY()).thenReturn(touchSlop + 1f);
        vOStrategy.onTouchEvent(eventDrag);

        Mockito.verify(view, never()).setTranslationX(anyInt());
    }

    @Test
    public void whenSlideGesture_ShouldNotifyChangePositionToListener() {
        applySlideGesture();

        MotionEvent eventDrag = mock(MotionEvent.class);
        when(eventDrag.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(eventDrag.getY()).thenReturn(100f);

        SwipeLayout.OnTranslateChangeListener listener = mock(SwipeLayout.OnTranslateChangeListener.class);
        vOStrategy.setAnchor(0, 100);
        vOStrategy.setOnTranslateChangeListener(listener);
        vOStrategy.onTouchEvent(eventDrag);

        verify(listener).onTranslateChange(0.49f, 0, 0.49f);
    }

    @Test(expected=IllegalArgumentException.class)
    public void whenSetLessThanTwoAnchors_ShouldThrowAnException() {
        vOStrategy.setAnchor(0);
    }

    private boolean applySlideGesture(){
        MotionEvent eventDown = mock(MotionEvent.class);
        when(eventDown.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(eventDown.getY()).thenReturn(0f);
        vOStrategy.onInterceptTouchEvent(eventDown);

        MotionEvent eventMove = mock(MotionEvent.class);
        when(eventMove.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(eventMove.getY()).thenReturn(touchSlop + 1f);
        return vOStrategy.onInterceptTouchEvent(eventMove);
    }
}
