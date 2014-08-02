/*
 * The MIT License
 *
 * Copyright 2014 Felix B�rring <felixbarring@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 * @author Felix B�rring <felixbarring@gmail.com>
 */

package com.pong.android.modell.breakout;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniform4f;

public class Rectangle {

    public final float WIDTH;
    public final float HEIGHT;
    public final int OFFSET_VERTEX_DATA;

    public final float INITIAL_TOPLEFT_X;
    public final float INITIAL_TOPLEFT_Y;

    public float topLeftX;
    public float topLeftY;

    protected final float RED;
    protected final float GREEN;
    protected final float BLUE;

    public Rectangle(float w, float h, float topLX, float topLY,
        int offsetVertexData, float r, float g, float b) {
        WIDTH = w;
        HEIGHT = h;
        INITIAL_TOPLEFT_X = topLX;
        INITIAL_TOPLEFT_Y = topLY;
        topLeftX = topLX;
        topLeftY = topLY;
        OFFSET_VERTEX_DATA = offsetVertexData;

        RED = r;
        GREEN = g;
        BLUE = b;
    }

    // Write a test for this later!

    /**
     * A method that returns if the rectangle in the param intersects with the
     * rectangle object that the method was called on.
     * 
     * @param that
     *            - The rectangle that we will check whether it intersect with
     *            this rectangle.
     * @return If they intersect or not.
     */
    public boolean intersects(Rectangle that) {

        boolean intersectsOnX =
            (that.topLeftX >= this.topLeftX && that.topLeftX <= this.topLeftX
                + this.WIDTH)
                || (that.topLeftX + that.WIDTH >= this.topLeftX && that.topLeftX
                    + that.WIDTH <= this.topLeftX + this.WIDTH);

        boolean intersectsOnY =
            (that.topLeftY <= this.topLeftY && that.topLeftY >= this.topLeftY
                - this.HEIGHT)
                || (that.topLeftY - that.HEIGHT <= this.topLeftY && that.topLeftY
                    - that.HEIGHT >= this.topLeftY - this.HEIGHT);

        return intersectsOnX && intersectsOnY;
    }

    /**
     * Moves the triangle.
     * 
     * @param x
     *            - The amount to move for the x coordinate.
     * @param y
     *            - The amount to move for the y coordinate.
     */
    public void move(float x, float y) {
        topLeftX = topLeftX + x;
        topLeftY = topLeftY + y;
    }

    /**
     * Draw the rectangle. It consists of 2 triangles which vertices are grouped
     * from the OFFSET_VERTEX_DATA to OFFSET_VERTEX_DATA+6.
     */
    public void draw() {
        // glUniform4f(uColorLocation, RED, GREEN, BLUE, 0.0f);
        glUniform4f(BreakOutRenderer.uColorLocation, 0.0f, 1.0f, 0.0f, 0.0f);
        glUniform4f(BreakOutRenderer.uPositionOffsetLocation, topLeftX
            - INITIAL_TOPLEFT_X, topLeftY - INITIAL_TOPLEFT_Y, 0.0f, 0.0f);
        glDrawArrays(GL_TRIANGLES, OFFSET_VERTEX_DATA, 6);
    }

}
