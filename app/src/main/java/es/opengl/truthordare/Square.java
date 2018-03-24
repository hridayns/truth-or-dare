package es.opengl.truthordare;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by hridayns on 15-08-2016.
 */
public class Square {


    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
      -0.5f,0.5f,0.0f,//top left
      -0.5f,-0.5f,-0.0f,//bottom left
      0,5f,-0.5f,0.0f,//bottom ight
      0.5f,0.5f,0.0f//top right
    };



    private short drawOrder[] = {
      0,1,2,0,2,3
    };


    public Square() {
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }
}
