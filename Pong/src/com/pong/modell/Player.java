package com.pong.modell;

public class Player extends Rectangle{
    
    private float speed = 0.05f;
    private int direction = 0;
    
    private Float target = 0f; 

    public Player(float w, float h, float topLX, float topLY,
        int offsetVertexData) {
        super(w, h, topLX, topLY, offsetVertexData);
    }
    
    public void touch(Float f){
        target = f;
    }
    
    public void setDirection(int i){
        direction = i;
    }
    
    public void tick(){
        if(target > topLeftY){
            move(0.0f, speed * direction);
        }
        
        direction = 0;
    }

}
