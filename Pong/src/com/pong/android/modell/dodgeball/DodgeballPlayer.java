package com.pong.android.modell.dodgeball;

import android.opengl.Matrix;

import com.pong.android.modell.pong.PongRenderer;

public class DodgeballPlayer extends Rectangle{
    
    
    private float speed = 1.0f;
    private Float target = 0f;
    
    public DodgeballPlayer(float topLX, float topLY, int offsetVertexData, float r, float g, float b) {
        super(0.1f, 0.25f, topLX, topLY, offsetVertexData, r, g, b);
    }

    
    public void touch(Float f) {

        // multiplyMV(float[] resultVec, int resultVecOffset, float[] lhsMat,
        // int lhsMatOffset, float[] rhsVec, int rhsVecOffset)
        final float[] rhsV = { 0.0f, f, 0.0f, 0.0f };
        final float[] resultVec = new float[4];
        Matrix.multiplyMV(resultVec, 0, PongRenderer.projectionMatrix, 0, rhsV,
            0);
        target = (f + (f - resultVec[1])) + (float) (HEIGHT / 2);
    }
    
    public void tick() {
        if (target > 1.0f){
            target = 1.0f;
        }
        
        if (target < -1.0f+HEIGHT){
            target = -1.0f+HEIGHT;
        }
        
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
    }
}
