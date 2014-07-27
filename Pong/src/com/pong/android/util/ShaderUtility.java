package com.pong.android.util;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

import java.util.Scanner;

import android.content.Context;
import android.util.Log;

/**
 * @author felix
 *
 */
public class ShaderUtility {
    
    private static boolean loggingOn = true;
    private static final String TAG = "ShaderHelper";
    
    public static void turnLoggingOn(){
        loggingOn = true;
    }
    
    public static void turnLoggingOff(){
        loggingOn = false;
    }


    /**
     * Compiles a vertex shader, delegating the actual work too compileShader.
     * Returns the OpenGL object ID.
     * 
     * @param shaderCode The string that represents the code for the shader.
     * @return  The object ID
     */
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    /**
     * Compiles a fragment shader, delegating the actual work too compileShader.
     * Returns the OpenGL object ID.
     * 
     * @param shaderCode The string that represents the code for the shader.
     * @return  The object ID
     */
    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    /**
     * Compiles a shader, returning the OpenGL object ID.
     * 
     * @param type  The type of shader to compile, Vertex of Fragment
     * @param shaderCode    The string that represents the code for the shader.
     * @return  The object ID
     */
    private static int compileShader(int type, String shaderCode) {

        // Create a new shader object.
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            if (loggingOn) {
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }

        // Pass in the shader source and compile.
        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);
        // Get the compilation status.
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if (loggingOn) {
            // Print the shader info log to the Android log output.
            Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode
                + "\n:" + glGetShaderInfoLog(shaderObjectId));
        }

        // Verify the compile status.
        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.
            glDeleteShader(shaderObjectId);

            if (loggingOn) {
                Log.w(TAG, "Compilation of shader failed.");
            }

            return 0;
        }
        // Return the shader object ID.
        return shaderObjectId;
    }

    /**
     * Links a vertex shader and a fragment shader together into an OpenGL
     * program. Returns the OpenGL program object ID, or 0 if linking failed.
     * 
     * @param vertexShaderId
     * @param fragmentShaderId
     * @return
     */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {

        // Create a new program object.
        final int programObjectId = glCreateProgram();

        if (programObjectId == 0) {
            if (loggingOn) {
                Log.w(TAG, "Could not create new program");
            }

            return 0;
        }

        // Attach the vertex shader to the program.
        glAttachShader(programObjectId, vertexShaderId);
        // Attach the fragment shader to the program.
        glAttachShader(programObjectId, fragmentShaderId);

        // Link the two shaders together into a program.
        glLinkProgram(programObjectId);

        // Get the link status.
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if (loggingOn) {
            // Print the program info log to the Android log output.
            Log.v(TAG, "Results of linking program:\n"
                + glGetProgramInfoLog(programObjectId));
        }

        // Verify the link status.
        if (linkStatus[0] == 0) {
            // If it failed, delete the program object.
            glDeleteProgram(programObjectId);
            if (loggingOn) {
                Log.w(TAG, "Linking of program failed.");
            }
            return 0;
        }

        // Return the program object ID.
        return programObjectId;
    }

    /**
     * Validates an OpenGL program. Should only be called when developing the
     * application.
     * 
     * @param programObjectId
     * @return
     */
    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0]
            + "\nLog:" + glGetProgramInfoLog(programObjectId));
        
        // if (LoggerConfig.ON) {
        // ShaderUtility.validateProgram(program);
        // }

        return validateStatus[0] != 0;
    }

    /**
     * @param context
     * @param resourceId
     * @return
     */
    public static String readTextFileFromResource(Context context,
        int resourceId) {
        StringBuilder body = new StringBuilder();

        try {
            final Scanner scanner =
                new Scanner(context.getResources().openRawResource(resourceId));
            while (scanner.hasNext()) {
                body.append(scanner.nextLine());
            }
            scanner.close();
        } catch (Error e) {
            e.printStackTrace();
        }
        return body.toString();
    }

}
