package com.xenione.libs.swipemaker;
/*
Copyright 08/06/2017 Eugeni Josep Senent i Gabriel

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

import android.widget.OverScroller;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ScrollerHelperTest {

    private ScrollerHelper scrollerHelper;
    private OverScroller overScroller;

    @Before
    public void setup() {
        overScroller = mock(OverScroller.class);
        scrollerHelper = new ScrollerHelper(overScroller);
    }

    @Test
    public void WhenSrtAndEndAreDifferent_startScrollOnOverScroll() {
        scrollerHelper.startScroll(0, 100);
        verify(overScroller).startScroll(0, 0, 100, 0);
    }

    @Test
    public void WhenSrtAndEndAreTheSame_noStartScrollOnOverScroll() {
        scrollerHelper.startScroll(0, 0);
        verify(overScroller, never()).startScroll(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void WhenScrollingAndFinish_forceFinish() {
        scrollerHelper.startScroll(0, 100);
        scrollerHelper.finish();
        verify(overScroller).forceFinished(true);
    }

    @Test
    public void WhenIsFinished_forwardToOverScroll() {
        scrollerHelper.isFinished();
        verify(overScroller).isFinished();
    }

    @Test
    public void WhenComputeScrollOffset_forwardToOverScroll() {
        scrollerHelper.computeScrollOffset();
        verify(overScroller).computeScrollOffset();
    }

    @Test
    public void WhenCurrX_forwardToOverScroll() {
        scrollerHelper.getCurrX();
        verify(overScroller).getCurrX();
    }
}
