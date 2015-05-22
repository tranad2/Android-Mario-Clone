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
        addLine(0, screenHeight - tileWidth * 2, 60, imageLoader.get(3));
        addLine(0, screenHeight - tileWidth, 60, imageLoader.get(3));
        addLine(tileWidth*10, screenHeight - tileWidth*5, 8, imageLoader.get(1));
        addLine(tileWidth * 10, screenHeight - tileWidth * 8, 1, imageLoader.get(1));
        addLine(tileWidth * 10, screenHeight - tileWidth * 11, 21, imageLoader.get(1));
        scene.add(new Obstacle(tileWidth * 11, screenHeight - tileWidth * 8, imageLoader.get(0), new Item(tileWidth * 11, screenHeight - tileWidth * 9, 0, view), itemList));
        addLine(tileWidth * 14, screenHeight - tileWidth * 8, 2, imageLoader.get(1));
        addLine(tileWidth * 18, screenHeight - tileWidth * 8, 6, imageLoader.get(1));
        addLine(tileWidth * 20, screenHeight - tileWidth * 5, 5, imageLoader.get(1));
        addLine(tileWidth * 27, screenHeight - tileWidth * 5, 1, imageLoader.get(1));
        addLine(tileWidth * 27, screenHeight - tileWidth * 8, 5, imageLoader.get(1));
        addLine(tileWidth * 30, screenHeight - tileWidth * 5, 4, imageLoader.get(1));

        enemies.add(new Bullet(16 * tileWidth, screenHeight - tileWidth * 3, -15, view, scene));
        enemies.add(new Bullet(16 * tileWidth, screenHeight - tileWidth * 4, -15, view, scene));
        addVertLine(17 * tileWidth, screenHeight - tileWidth * 3, 2, imageLoader.get(3));

        enemies.add(new Bullet(26 * tileWidth, screenHeight - tileWidth * 6, -15, view, scene));
        enemies.add(new Bullet(26 * tileWidth, screenHeight - tileWidth * 7, -15, view, scene));
        addVertLine(27 * tileWidth, screenHeight - tileWidth * 6, 2, imageLoader.get(3));

        enemies.add(new Bullet(30 * tileWidth, screenHeight - tileWidth * 9, -15, view, scene));
        enemies.add(new Bullet(30 * tileWidth, screenHeight - tileWidth * 10, -15, view, scene));
        addVertLine(31 * tileWidth, screenHeight - tileWidth * 9, 3, imageLoader.get(3));

        enemies.add(new Bullet(32 * tileWidth, screenHeight - tileWidth * 3, -15, view, scene));
        enemies.add(new Bullet(32 * tileWidth, screenHeight - tileWidth * 4, -15, view, scene));
        addVertLine(33 * tileWidth, screenHeight - tileWidth * 3, 2, imageLoader.get(3));

        addStairsR(45 * tileWidth, screenHeight - tileWidth * 3, 5, imageLoader.get(3));

        enemies.add(new Bullet(55 * tileWidth, screenHeight - tileWidth * 8, -15, view, scene));
        enemies.add(new Bullet(55 * tileWidth, screenHeight - tileWidth*9, -15, view, scene));
        enemies.add(new Bullet(55 * tileWidth, screenHeight - tileWidth*10, -15, view, scene));
        addVertLine(56 * tileWidth, screenHeight - tileWidth * 8, 3, imageLoader.get(3));

        scene.add(new Flag(60*tileWidth, screenHeight - tileWidth*1, view));
    }
}
