package com.pong.android;

import java.util.concurrent.LinkedBlockingQueue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class BreakOutRenderer implements Renderer {
    
    public static LinkedBlockingQueue<Float> queueOfTouchCoordinates =
        new LinkedBlockingQueue<Float>();

    @Override
    public void onDrawFrame(GL10 arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
        // TODO Auto-generated method stub
        
    }

}
