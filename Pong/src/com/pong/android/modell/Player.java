package com.pong.android.modell;

public class Player extends Rectangle {

    private float speed = 0.05f;
    private int direction = 0;

    private Float target = 0f;

    public Player(float w, float h, float topLX, float topLY,
        int offsetVertexData) {
        super(w, h, topLX, topLY, offsetVertexData);
    }

    public void touch(Float f) {
        target = f + (float) (HEIGHT / 2);
    }

    public void setDirection(int i) {
        direction = i;
    }

    public void tick() {
        if (target >= topLeftY) {
            // If the distance to the target is less than the speed
            // then the location will be set to the target coordinate.
            if (target <= topLeftY + speed) {
                topLeftY = target;
            } else {
                move(0.0f, speed);
            }
        } else {
            if (target >= topLeftY - speed) {
                topLeftY = target;
            } else {
                move(0.0f, -speed);
            }
        }
        direction = 0;
    }

}
