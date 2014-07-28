
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
