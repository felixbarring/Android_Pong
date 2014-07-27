package com.pong.modell;

import com.pong.android.PongRenderer;

public class Ball extends Rectangle {

    private int xDirection = -1;
    private float xSpeed = 0.001f;
    
    private int yDirection = -1;
    private float ySpeed = 0.005f;

    public Ball(float w, float h,
        float topLX, float topLY, int offsetVertexData) {
        super(w, h, topLX, topLY, offsetVertexData);
    }

    public void tick() {
        if(topLeftY > 1 || topLeftY < -1){
            toggleYDirection();
        }
        
        if (PongRenderer.opponent.intersects(this)) {
            toggleXDirection();
            move(2 * xSpeed * xDirection, 0.0f);
        }
        if (PongRenderer.player.intersects(this)) {
            toggleXDirection();
            move(2 * xSpeed * xDirection, 0.0f);
        } else {
            move(xSpeed * xDirection, ySpeed * yDirection);
        }
    }

    private void toggleXDirection() {
        xDirection = -xDirection;
    }
    
    private void toggleYDirection(){
        yDirection = -yDirection;
    }
}
