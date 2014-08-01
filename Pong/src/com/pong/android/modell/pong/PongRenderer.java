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

package com.pong.android.modell.pong;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.concurrent.LinkedBlockingQueue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.pong.android.IFGameEvents;
import com.pong.android.MenuActivity;
import com.pong.android.R;
import com.pong.android.util.ShaderUtility;

public class PongRenderer implements Renderer, IFGameEvents {
    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";
    private static final String U_POSITION_OFFSET = "u_offset";
    private static final String U_MATRIX = "u_Matrix";
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;
    private final Context context;
    private int program;
    public static int uColorLocation;
    public static int aPositionLocation;
    public static int uPositionOffsetLocation;
    public static int uMatrixLocation;

    public static final float[] projectionMatrix = new float[16];

    public static PongPlayer player;
    public static PongOpponent opponent;
    public static PongBall ball;
    public static Board board;

    public static LinkedBlockingQueue<Float> queueOfTouchCoordinates =
        new LinkedBlockingQueue<Float>();

    private final Activity activity;

    public PongRenderer(Activity activity) {
        this.context = activity;
        this.activity = activity;

        player = new PongPlayer(0.1f, 0.6f, -0.95f, 0.3f, 0);
        opponent = new PongOpponent(0.1f, 0.6f, 0.85f, 0.3f, 6);
        ball = new PongBall(0.1f, 0.1f, -0.05f, 0.05f, 12, this);
        board = new Board(2.0f, 2.0f, -1.0f, 1.0f, 18);

        float[] tableVerticesWithTriangles =
            {
                // Player Triangle 1
                player.topLeftX,
                player.topLeftY,
                player.topLeftX,
                player.topLeftY - player.HEIGHT,
                player.topLeftX + player.WIDTH,
                player.topLeftY - player.HEIGHT,
                // Player Triangle 2
                player.topLeftX + player.WIDTH,
                player.topLeftY,
                player.topLeftX,
                player.topLeftY,
                player.topLeftX + player.WIDTH,
                player.topLeftY - player.HEIGHT,

                // Opponent triangle 1
                opponent.topLeftX,
                opponent.topLeftY,
                opponent.topLeftX,
                opponent.topLeftY - opponent.HEIGHT,
                opponent.topLeftX + opponent.WIDTH,
                opponent.topLeftY - opponent.HEIGHT,
                // Opponent triangle 2
                opponent.topLeftX + opponent.WIDTH,
                opponent.topLeftY,
                opponent.topLeftX,
                opponent.topLeftY,
                opponent.topLeftX + opponent.WIDTH,
                opponent.topLeftY - opponent.HEIGHT,

                // Ball triangle 1
                ball.topLeftX,
                ball.topLeftY,
                ball.topLeftX,
                ball.topLeftY - ball.HEIGHT,
                ball.topLeftX + ball.WIDTH,
                ball.topLeftY - ball.HEIGHT,
                // Ball triangle 2
                ball.topLeftX + ball.WIDTH,
                ball.topLeftY,
                ball.topLeftX,
                ball.topLeftY,
                ball.topLeftX + ball.WIDTH,
                ball.topLeftY - ball.HEIGHT,

                // Board triangle 1
                board.topLeftX, board.topLeftY, board.topLeftX,
                board.topLeftY - board.HEIGHT,
                board.topLeftX + board.WIDTH,
                board.topLeftY - board.HEIGHT,
                // Ball triangle 2
                board.topLeftX + board.WIDTH, board.topLeftY, board.topLeftX,
                board.topLeftY, board.topLeftX + board.WIDTH,
                board.topLeftY - board.HEIGHT,

            };

        vertexData =
            ByteBuffer
                .allocateDirect(
                    tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        int vertexShader =
            ShaderUtility.compileVertexShader(ShaderUtility
                .readTextFileFromResource(context, R.raw.vertex_shader));

        int fragmentShader =
            ShaderUtility.compileFragmentShader(ShaderUtility
                .readTextFileFromResource(context, R.raw.fragment_shader));

        program = ShaderUtility.linkProgram(vertexShader, fragmentShader);

        GLES20.glUseProgram(program);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        uPositionOffsetLocation =
            GLES20.glGetUniformLocation(program, U_POSITION_OFFSET);
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        // Bind our data, specified by the variable vertexData, to the vertex
        // attribute at location A_POSITION_LOCATION.
        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation,
            POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData);

        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);

        if (width > height) {
            float aspectRatio = (float) width / (float) height;
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio,
                -1.0f, 1.0f, -1.0f, 1.0f);
        } else {
            float aspectRatio = (float) height / (float) width;
            Matrix.orthoM(projectionMatrix, 0, -1.0f, 1.0f, -aspectRatio,
                aspectRatio, -1.0f, 1.0f);
        }
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Update game logic
        gameTick();
        for (Float i : queueOfTouchCoordinates) {
            player.touch(i);
        }
        queueOfTouchCoordinates.clear();

        // Clear the rendering surface and start drawing.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix,
            0);

        // Draw the board black
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 0.0f);
        board.draw();

        // All other components shall be drawn in white
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 0.0f);
        player.draw();
        opponent.draw();
        ball.draw();
    }

    private void gameTick() {
        ball.tick();
        opponent.tick();
        player.tick();
    }

    // Interface IFGameEvents

    @Override
    public void playerWin() {
        // Lol will never happen
        MenuActivity.dis.gameOver(5);
    }

    @Override
    public void playerLose(int number) {
        MenuActivity.dis.gameOver(number);
    }

}
