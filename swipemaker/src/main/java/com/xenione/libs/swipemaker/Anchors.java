package com.xenione.libs.swipemaker;

import java.util.Arrays;

/**
 * Created by Eugeni on 16/04/2016.
 */
public class Anchors {
    Anchor anchor;

    private static class Anchor {

        private Integer[] anchors;

        public Anchor(Integer[] anchor) {
            this.anchors = anchor;
        }

        public int next(int index) {
            return anchors[index + 1];
        }

        public int prev(int index) {
            return anchors[index - 1];
        }

        public int pos(int index) {
            return anchors[index];
        }

        public int size() {
            return anchors.length;
        }

        public int getSupLimit() {
            return anchors[anchors.length - 1];
        }

        public int getInfLimit() {
            return anchors[0];
        }

        public static int distance(int init, int end) {
            return Math.abs(init - end);
        }
    }

    private Anchors(Anchor anchor) {
        this.anchor = anchor;
    }

    public static Anchors make(Integer[] anchors) {
        if (anchors.length < 2) {
            throw new IllegalArgumentException("Amount of anchor points provided to SwipeLayout have to be bigger than 2");
        }
        Arrays.sort(anchors);
        return new Anchors(new Anchor(anchors));
    }

    public int size() {
        return anchor.size();
    }

    public float distance(int x) {
        return distance(x, anchor.getSupLimit(), anchor.getInfLimit());
    }

    public float distance(int section, int x) {
        return distance(x, anchor.next(section), anchor.pos(section));
    }

    private float distance(int x, int limitSup, int limitInf) {
        return (float) (x - limitInf) / (limitSup - limitInf);
    }

    public int anchorFor(int section) {
        return anchor.pos(section);
    }

    public int closeTo(int section, int point) {
        int distInf = Anchor.distance(point, anchor.pos(section));
        int distSup = Anchor.distance(point, anchor.next(section));
        if (distInf < distSup) {
            return anchor.pos(section);
        }
        return anchor.next(section);
    }

    public int cropInLimits(int x) {
        int inBounds = x;
        if (x < anchor.getInfLimit()) {
            inBounds = anchor.getInfLimit();
        } else if (x > anchor.getSupLimit()) {
            inBounds = anchor.getSupLimit();
        }
        return inBounds;
    }
}
