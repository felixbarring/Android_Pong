/*
 * The MIT License
 *
 * Copyright 2014 Felix Bärring <felixbarring@gmail.com>.
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
 * @author Felix Bärring <felixbarring@gmail.com>
 */

package com.pong.android.modell;

import android.opengl.GLES20;

public class BreakOutBrick extends Rectangle {

    private final int uColorLocation;

    private final float RED;
    private final float GREEN;
    private final float BLUE;

    public BreakOutBrick(float w, float h, float topLX, float topLY,
        float topLeftX, float topLeftY, int offsetVertexData,
        int uColorLocation, float r, float g, float b) {
        super(w, h, topLX, topLY, offsetVertexData);

        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        
        this.uColorLocation = uColorLocation;
        RED = r;
        GREEN = g;
        BLUE = b;
    }

    @Override
    public void draw() {
        GLES20.glUniform4f(uColorLocation, RED, GREEN, BLUE, 1.0f);
        super.draw();
    }

}
