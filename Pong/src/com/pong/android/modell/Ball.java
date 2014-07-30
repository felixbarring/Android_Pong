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

package com.pong.android.modell;

import android.widget.Toast;

import com.pong.android.IFGameEvents;
import com.pong.android.PongRenderer;

public class Ball extends Rectangle {

    private int xDirection = -1;
    private float xSpeed = 0.005f;

    private int yDirection = -1;
    private float ySpeed = 0.0f;

    private float speedIncrease = 0.002f;

    private final IFGameEvents gameEvents;
    
    private int successfullHits = 0;

    public Ball(float w, float h, float topLX, float topLY,
        int offsetVertexData, IFGameEvents gameEvents) {
        super(w, h, topLX, topLY, offsetVertexData);
        this.gameEvents = gameEvents;
    }

    public void tick() {

        if (topLeftY > 1 || topLeftY - HEIGHT < -1) {
            toggleYDirection();
        }
        if (topLeftX < -0.85) { // Hardcoded
            if (PongRenderer.player.intersects(this)) {
                toggleXDirection();
                move(2 * xSpeed * xDirection, 0.0f);
                successfullHits++;
            } else {
         	   gameEvents.playerLose(successfullHits);
			}
        } else if (topLeftX+WIDTH > 0.85) {
            if (PongRenderer.opponent.intersects(this)) {
                toggleXDirection();
                move(2 * xSpeed * xDirection, 0.0f);
            } else {
	            gameEvents.playerWin();
			}
        } else {
            move(xSpeed * xDirection, ySpeed * yDirection);
        }
    }

    private void toggleXDirection() {
        xDirection = -xDirection;
        xSpeed = xSpeed + speedIncrease;
        ySpeed = ySpeed + (float)((Math.random() - 0.5)/50);
    }

    private void toggleYDirection() {
        yDirection = -yDirection;
    }
}
