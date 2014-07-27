package com.pong.modell;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniform4f;

import com.pong.android.PongRenderer;

/**
 * Designed to be extended by other classes.
 * 
 * @author felix
 *
 */
public abstract class Rectangle {

    public final float WIDTH;
    public final float HEIGHT;
    public final int OFFSET_VERTEX_DATA;

    public final float INITIAL_TOPLEFT_X;
    public final float INITIAL_TOPLEFT_Y;
    
    public float topLeftX;
    public float topLeftY;

    public Rectangle(float w, float h, float topLX, float topLY, int offsetVertexData) {
        WIDTH = w;
        HEIGHT = h;
        INITIAL_TOPLEFT_X = topLX;
        INITIAL_TOPLEFT_Y = topLY;
        topLeftX = topLX;
        topLeftY = topLY;
        OFFSET_VERTEX_DATA = offsetVertexData;
    }

    // Write a test for this later!
    
    /**
     * A method that returns if the rectangle in the param intersects with the rectangle object
     * that the method was called on.
     * 
     * @param that - The rectangle that we will check whether it intersect with this rectangle.
     * @return If they intersect or not.
     */
    public boolean intersects(Rectangle that) {
        
        boolean intersectsOnX = (that.topLeftX >= this.topLeftX && that.topLeftX <= this.topLeftX + this.WIDTH) || 
            (that.topLeftX + that.WIDTH >= this.topLeftX && that.topLeftX + that.WIDTH <= this.topLeftX + this.WIDTH);
        
        boolean intersectsOnY = (that.topLeftY <= this.topLeftY && that.topLeftY >= this.topLeftY - this.HEIGHT) || 
        (that.topLeftY - that.HEIGHT <= this.topLeftY && that.topLeftY - that.HEIGHT >= this.topLeftY - this.HEIGHT);
        
        return intersectsOnX && intersectsOnY;
    }
    
    /**
     * Moves the triangle.
     * 
     * @param x - The amount to move for the x coordinate.
     * @param y - The amount to move for the y coordinate.
     */
    public void move(float x, float y){
        topLeftX =  topLeftX + x;
        topLeftY = topLeftY + y;
    }
    
    /**
     * Draw the rectangle.
     * It consists of 2 triangles which vertices are grouped from the 
     * OFFSET_VERTEX_DATA to OFFSET_VERTEX_DATA+6.
     * 
     */
    public void draw(){
        glUniform4f(PongRenderer.uPositionOffsetLocation, 
            topLeftX - INITIAL_TOPLEFT_X, topLeftY - INITIAL_TOPLEFT_Y, 0.0f, 0.0f);
        glDrawArrays(GL_TRIANGLES, OFFSET_VERTEX_DATA, 6);
    }
 
}
