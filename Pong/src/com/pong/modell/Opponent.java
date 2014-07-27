package com.pong.modell;

import com.pong.android.PongRenderer;

public class Opponent extends Rectangle{

    public Opponent(float w, float h, float topLX, float topLY,
        int offsetVertexData) {
        super(w, h, topLX, topLY, offsetVertexData);
    }
    
    public void tick(){
        float ballCenter = PongRenderer.ball.topLeftY-(PongRenderer.ball.HEIGHT/2);
        float opponentCenter = this.topLeftY-(HEIGHT/2);
        move(0.0f, ballCenter - opponentCenter);
    }

}
