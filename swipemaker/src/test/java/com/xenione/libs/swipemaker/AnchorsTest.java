package com.xenione.libs.swipemaker;
/*
Copyright 04/06/2017 Eugeni Josep Senent i Gabriel

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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class AnchorsTest {

    @Test
    public void closeTo_whenNegativeAnchors_giveClosestAnchor() {
        Anchors anchors = Anchors.make(new Integer[]{-20, -10, 0});
        int anchor = anchors.closeTo(1, -1);
        assertThat(anchor, is(0));
    }

    @Test
    public void closeTo_whenPositiveAnchors_giveClosestAnchor() {
        Anchors anchors = Anchors.make(new Integer[]{0, 10, 20});
        int anchor = anchors.closeTo(0, 1);
        assertThat(anchor, is(0));
    }

    @Test
    public void cropInLimits_whenPositivePositionExceedLimits_keepInLimits() {
        Anchors anchors = Anchors.make(new Integer[]{0, 10, 20});
        int anchor = anchors.cropInLimits(30);
        assertThat(anchor, is(20));
    }

    @Test
    public void cropInLimits_whenNegativePositionExceedLimits_keepInLimits() {
        Anchors anchors = Anchors.make(new Integer[]{0, 10, 20});
        int anchor = anchors.cropInLimits(-10);
        assertThat(anchor, is(0));
    }

    @Test
    public void sectionFor_whenPositionInASection_giveSection() {
        Anchors anchors = Anchors.make(new Integer[]{0, 10, 20});
        int anchor = anchors.sectionFor(5);
        assertThat(anchor, is(0));
    }

    @Test
    public void sectionFor_whenPositionIsAnchor_giveSection() {
        Anchors anchors = Anchors.make(new Integer[]{0, 10, 20, 30});
        int anchor = anchors.sectionFor(10);
        assertThat(anchor, either(is(0)).or(is(1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sectionFor_whenPositionExceedLimits_throwException() {
        Anchors anchors = Anchors.make(new Integer[]{0, 10, 20, 30});
        anchors.sectionFor(50);
    }

    @Test
    public void distance_whenPositionWithinSection_giveDistanceFromInfAnchor() {
        Anchors anchors = Anchors.make(new Integer[]{0, 10, 20, 30});
        float distance = anchors.distance(0, 9);
        assertEquals(0.9, distance, 1);
    }
}
