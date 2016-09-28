package com.xenione.libs.swipemaker;

/**
 * Created by Eugeni on 23/04/2016.
 */
public class Position {

    public Anchors anchors;
    public int currX;
    public int section;
    public float relative;
    public float global;

    public void updatePosition(int newX) {
        if (currX == newX) {
            return;
        }
        updateSection(newX);
        this.currX = newX;
        this.relative = relative(newX);
        this.global = global(newX);
    }

    private void decSection() {
        if (section == 0) {
            return;
        }
        section--;
    }

    private void incSection() {
        if (section == anchors.size() - 1) {
            return;
        }
        section++;
    }

    private boolean moveToLeft(int newX) {
        return currX > newX;
    }

    private void updateSection(int newX) {
        if (moveToLeft(newX) && (newX <= anchors.anchorFor(this.section))) {
            decSection();
        } else if (newX > anchors.anchorFor(this.section + 1)) {
            incSection();
        }
    }

    public float global(int posX) {
        return anchors.distance(posX);
    }

    public float relative(int posX) {
        return anchors.distance(this.section, posX);
    }

    public int closeTo(int posX) {
        return anchors.closeTo(section, posX);
    }

    public int cropInLimits(int posX) {
        return anchors.cropInLimits(posX);
    }
}

