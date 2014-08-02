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

import java.util.List;

import com.pong.android.IFGameEvents;

public class BreakOutBall extends Rectangle {

    private int xDirection = -1;
    private float xSpeed = 0.01f;
    private int yDirection = -1;
    private float ySpeed = 0.0f;
    private float speedIncrease = 0.002f;
    private final IFGameEvents gameEvents;
    private int successfullHits = 0;

    private final Rectangle player;
    private final List<Brick> bricks;

    public BreakOutBall(float w, float h, float topLX, float topLY,
        int offsetVertexData, float r, float g, float b, Rectangle player,
        List<Brick> bricks, IFGameEvents gameEvents) {
        super(w, h, topLX, topLY, offsetVertexData, r, g, b);

        this.gameEvents = gameEvents;
        this.player = player;
        this.bricks = bricks;

    }

    public void tick() {

        if (topLeftY > 1 || topLeftY - HEIGHT < -1) {
            toggleYDirection();
        }
        if (player.intersects(this)) {
            toggleXDirection();
            move(2 * xSpeed * xDirection, 0.0f);
            successfullHits++;
        }

        if (this.topLeftX < -0.8f) {
            gameEvents.playerLose(successfullHits);
        } else if (this.topLeftX > 0.9f) {
            toggleXDirection();
        }

        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            if (brick.intersects(this)) {
                toggleXDirection();
                move(2 * xSpeed * xDirection, 0.0f);
                brick.destroy();
                bricks.remove(brick);
            }
        }
        move(xSpeed * xDirection, ySpeed * yDirection);
    }

    private void toggleXDirection() {
        xDirection = -xDirection;
        ySpeed = ySpeed + (float) ((Math.random() - 0.5) / 50);
    }

    private void toggleYDirection() {
        yDirection = -yDirection;
    }

}
