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
        addLine(tileWidth * 12, screenHeight - tileWidth * 5, 7, imageLoader.get(2));
        for (int i = 0; i < 7; i++)
            itemList.add(new Item(tileWidth * (12+i), screenHeight - tileWidth * 6, 2, view));
        addLine(tileWidth*14, screenHeight - tileWidth * 8, 1, imageLoader.get(2));
        scene.add(new Obstacle(tileWidth * 15, screenHeight - tileWidth * 8, imageLoader.get(0), new Item(tileWidth * 15, screenHeight - tileWidth * 9, 0, view), itemList)); //mushroom
        addLine(tileWidth*16, screenHeight - tileWidth * 8, 1, imageLoader.get(2));
        addLine(tileWidth*23, screenHeight-tileWidth*2, 1, imageLoader.get(8));
        addLine(tileWidth*29, screenHeight-tileWidth*3, 1, imageLoader.get(9));
        addLine(tileWidth*35, screenHeight - tileWidth * 4, 1, imageLoader.get(10));
        scene.add(new Obstacle(tileWidth * 36, screenHeight - tileWidth * 7, imageLoader.get(0), new Item(tileWidth * 36, screenHeight - tileWidth * 8, 1, view), itemList)); //flower
        addLine(tileWidth*43, screenHeight-tileWidth*1, 33, imageLoader.get(3));
        addLine(tileWidth*43, screenHeight-tileWidth*2, 33, imageLoader.get(3));
        enemies.add(new Goomba(tileWidth*50, screenHeight-tileWidth*3, view, scene));

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
