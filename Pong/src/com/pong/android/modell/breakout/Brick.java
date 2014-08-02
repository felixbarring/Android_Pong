package com.pong.android.modell.breakout;

public class Brick extends Rectangle{

    private boolean alive = true;
    
    public Brick(float w, float h, float topLX, float topLY,
        int offsetVertexData, float r, float g, float b) {
        super(w, h, topLX, topLY, offsetVertexData, r, g, b);
        // TODO Auto-generated constructor stub
    }
    
    
    public void destroy(){
        alive = false;
    }
    
    
    @Override
    public void draw(){
        if(alive){
            super.draw();
        }
    }
    
}
