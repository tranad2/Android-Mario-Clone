package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class World2 extends World{

    private static final String TAG = "World2";

    public World2(MarioSurfaceView view) {
        super(view);
        addElements(view);
    }

    public void addElements(MarioSurfaceView view){

        addLine(tileWidth*0, screenHeight-tileWidth*1, 9, imageLoader.get(3));
        addLine(tileWidth*0, screenHeight-tileWidth*2, 9, imageLoader.get(3));
        addLine(tileWidth*12, screenHeight-tileWidth*5, 7, imageLoader.get(2));
        addLine(tileWidth*14, screenHeight-tileWidth*8, 1, imageLoader.get(2));
        addLine(tileWidth*15, screenHeight-tileWidth*8, 1, imageLoader.get(0));
        addLine(tileWidth*16, screenHeight-tileWidth*8, 1, imageLoader.get(2));
        addLine(tileWidth*23, screenHeight-tileWidth*2, 1, imageLoader.get(8));
        addLine(tileWidth*29, screenHeight-tileWidth*4, 1, imageLoader.get(10));
        addLine(tileWidth*30, screenHeight-tileWidth*7, 1, imageLoader.get(0));
        addLine(tileWidth*37, screenHeight-tileWidth*1, 33, imageLoader.get(3));
        addLine(tileWidth*37, screenHeight-tileWidth*2, 33, imageLoader.get(3));

        addLine(tileWidth*70, screenHeight-tileWidth*1, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*2, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*3, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*4, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*5, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*6, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*7, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*8, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*9, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*10, 10, imageLoader.get(3));
        addLine(tileWidth*70, screenHeight-tileWidth*11, 10, imageLoader.get(3));

    }
}
