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

public class BreakOutActivity extends Activity {
    private GLSurfaceView glSurfaceView;
    private boolean sucsessfylCreated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);

        final PongRenderer renderer = new PongRenderer(this);

        boolean supportsOpenGl20 =
            ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                .getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;

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

        glSurfaceView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    final float normalizedY =
                        -(event.getY() / (float) v.getHeight() * 2 - 1);
                    BreakOutRenderer.queueOfTouchCoordinates.add(normalizedY);
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
