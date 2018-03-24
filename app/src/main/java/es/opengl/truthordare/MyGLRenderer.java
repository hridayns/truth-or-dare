package es.opengl.truthordare;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hridayns on 14-08-2016.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Context context;

    private Triangle mTriangle;
    private Square mSquare;

    //-----------------------------------------
    public MyGLRenderer(Context context) {
        this.context = context;   // Get the application context (NEW)
    }
    //-----------------------------------------
    public static int loadShader(int type,String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader,shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mTriangle = new Triangle();
        //mSquare = new Square();

        mTriangle.loadGLTexture(gl, this.context);

        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);

        gl.glEnable(GL10.GL_TEXTURE_2D);
    }

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private final float[] mRotationMatrix = new float[16];



    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        float scratch[] = new float[16];

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //long time = SystemClock.uptimeMillis()%4000L;
        //float angle = 0.090f * ((int)time);
        Matrix.setRotateM(mRotationMatrix,0,mAngle, 0, 0, 1.0f);


        Matrix.multiplyMM(scratch,0,mMVPMatrix,0,mRotationMatrix,0);



       mTriangle.draw(scratch,gl);
    }
    public volatile float mAngle;

    public void setAngle(float angle) {
        mAngle = angle%360;
    }
    public float getAngle() {
        return mAngle;
    }

}

