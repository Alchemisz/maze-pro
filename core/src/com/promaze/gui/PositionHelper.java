package com.promaze.gui;

public class PositionHelper {

    public int initialPosition,margin;

    public PositionHelper(int initialPosition, int margin) {
        this.initialPosition = initialPosition;
        this.margin = margin;
    }

    public int getNewRelativePosition(int height)
    {
        initialPosition -= (height + margin);
        return initialPosition;
    }

    public int getRelativePosition()
    {
        return initialPosition;
    }
}
