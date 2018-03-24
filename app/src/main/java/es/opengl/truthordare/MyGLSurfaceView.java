package es.opengl.truthordare;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.WindowManager;


public class MyGLSurfaceView extends GLSurfaceView {



    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
       super(context);
       setEGLContextClientVersion(2);
       mRenderer = new MyGLRenderer(context);
       setRenderer(mRenderer);
       setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

   }

    private volatile boolean isAnimation = false;
    private long pressTime = -1l;
    private long releaseTime = -1l;
    private long touchDuration = -1l;
    private float deceleration;

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        int index = e.getActionIndex();
        int action = e.getActionMasked();
        int pointerId = e.getPointerId(index);


        switch(e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_DOWN:
                if(isAnimation) {
                    return true;
                }
                pressTime = System.currentTimeMillis();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //isTouching = false;
                releaseTime = System.currentTimeMillis();
                touchDuration = releaseTime - pressTime;
                if(touchDuration > 1000L) touchDuration = 1000L;


                //float time = 0.000090f * touchDuration;
                float time = 0.0000065f * touchDuration;
                deceleration = 0;


                Log.d("Press/release/dura", pressTime + " / " + releaseTime + " / " + touchDuration);
                isAnimation = true;
                while(time - deceleration > 0) {

                    mRenderer.setAngle(
                            mRenderer.getAngle() + time - deceleration
                    );

                    requestRender();

                    deceleration += 0.0000010f * time;

                }

                isAnimation = false;


                break;
        }
        return true;
    }
}
