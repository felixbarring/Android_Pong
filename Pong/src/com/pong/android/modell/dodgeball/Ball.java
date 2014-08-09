package com.pong.android.modell.dodgeball;

public class Ball extends Rectangle{

    private float speed;
    
    public Ball(float topLX, float topLY, float speed, int offsetVertexData, float r, float g, float b) {
        super(0.1f, 0.1f, topLX, topLY, offsetVertexData, r, g, b);
        this.speed = speed;
    }

    public void tick(){
        
        this.move(-speed, 0.0f);
    }
    
    public boolean isDead(){
        if (this.topLeftX < -1.2){
            return true;
        }
        return false;
    }
}
