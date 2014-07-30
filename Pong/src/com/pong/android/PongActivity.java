
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

package com.pong.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class PongActivity extends Activity {
    private GLSurfaceView glSurfaceView;
    private boolean sucsessfylCreated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        
        final PongRenderer renderer = new PongRenderer(this);
        
        // Rewrite this...
        boolean supportsOpenGl20 = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
       
//        boolean supportsOpenGl20 =
//            ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
//                .getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000
//                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && (Build.FINGERPRINT
//                    .startsWith("generic")
//                    || Build.FINGERPRINT.startsWith("unknown")
//                    || Build.MODEL.contains("google_sdk")
//                    || Build.MODEL.contains("Emulator") || Build.MODEL
//                        .contains("Android SDK built for x86")));

        if (supportsOpenGl20) {
            // Request an OpenGL ES 2.0 compatible context and assign our
            // renderer.
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(renderer);
            sucsessfylCreated = true;
        } else {
            // We are out of luck since the device dose not support OpenGL ES
            // 2.0
            // It is possible to use OpenGL 1.0, but that will be to much work.
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                Toast.LENGTH_LONG).show();
            return;
        }
        
        
        glSurfaceView.setOnTouchListener( new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event != null){
                    final float normalizedY = -(event.getY() / (float) v.getHeight() * 2 -1);
                    PongRenderer.queueOfTouchCoordinates.add(normalizedY);
                }
                return true;
            }
        });
        
        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sucsessfylCreated) {
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sucsessfylCreated) {
            glSurfaceView.onResume();
        }
    }
}