package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class World1 extends World{

    private static final String TAG = "World1";

    public World1(MarioSurfaceView view){
        super(view);
        addElements(view);
    }

    public void addElements(MarioSurfaceView view){

        addLine(tileWidth*40, screenHeight-tileWidth*4, 10, imageLoader.get(2));
        addStairsR(tileWidth*15, screenHeight-tileWidth*3, 5, imageLoader.get(3));
        addStairsL(tileWidth*24, screenHeight-tileWidth*3, 5, imageLoader.get(3));
        enemies.add(new Goomba(2 * screenWidth / 3, screenHeight / 2, view, scene));

        scene.add(new Obstacle(tileWidth*6,screenHeight-tileWidth*7, imageLoader.get(0), new Item(tileWidth*6,screenHeight-tileWidth*8, 0,view), itemList));
    }
}
