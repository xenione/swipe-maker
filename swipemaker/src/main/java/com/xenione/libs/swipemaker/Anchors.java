package com.xenione.libs.swipemaker;

import java.util.Arrays;

/**
 * Created by Eugeni on 16/04/2016.
 */
public class Anchors {
    AnchorHelper anchorHelper;

    private static class AnchorHelper {

        private Integer[] anchors;

        public AnchorHelper(Integer[] anchor) {
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

        public int sectionFromInf(int position) {
            int section = 0;
            for (int i = 0; i < anchors.length - 1; i++) {
                if (anchors[i] > position) {
                    section = i;
                    break;
                }
            }

            return section;
        }

        public int sectionFromSup(int position) {
            int section = 0;
            for (int i = anchors.length - 1; i >= 0; i--) {
                if (anchors[i] < position) {
                    section = i;
                    break;
                }
            }

            return section;
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

    private Anchors(Integer[] anchor) {
        this.anchorHelper = new AnchorHelper(anchor);
    }

    public static Anchors make(Integer[] anchors) {
        if (anchors.length < 2) {
            throw new IllegalArgumentException("Amount of anchor points provided to SwipeLayout have to be bigger than 2");
        }
        Arrays.sort(anchors);
        return new Anchors(anchors);
    }

    public int size() {
        return anchorHelper.size();
    }

    /**
     * Gives distance relative (1%) within the superior limit and the
     * Inferior limit from the inferior limit.
     * @param x point from where to get relative distance
     * @return 1% from inf limit
     */
    public float distance(int x) {
        return distance(x, anchorHelper.getSupLimit(), anchorHelper.getInfLimit());
    }

    /**
     * Gives distance relative (1%) within the superior Section limit and the
     * Inferior Section limit from the inferior limit.
     * @param x point from where to get relative distance
     * @return 1% from inf limit
     */

    public float distance(int section, int x) {
        return distance(x, anchorHelper.next(section), anchorHelper.pos(section));
    }

    private float distance(int x, int limitSup, int limitInf) {
        return (float) (x - limitInf) / (limitSup - limitInf);
    }

    public int anchorFor(int section) {
        return anchorHelper.pos(section);
    }

    public int sectionFor(int position) {
        if ((position > anchorHelper.getSupLimit()) || (position < anchorHelper.getInfLimit())) {
            throw new IllegalArgumentException("position exceed limits");
        }
        return anchorHelper.sectionFromSup(position);
    }

    public int closeTo(int section, int point) {
        int distInf = AnchorHelper.distance(point, anchorHelper.pos(section));
        int distSup = AnchorHelper.distance(point, anchorHelper.next(section));
        if (distInf < distSup) {
            return anchorHelper.pos(section);
        }
        return anchorHelper.next(section);
    }

    public int cropInLimits(int x) {
        int inBounds = x;
        if (x < anchorHelper.getInfLimit()) {
            inBounds = anchorHelper.getInfLimit();
        } else if (x > anchorHelper.getSupLimit()) {
            inBounds = anchorHelper.getSupLimit();
        }
        return inBounds;
    }
}
