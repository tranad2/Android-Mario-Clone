package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 5/10/2015.
 */
public class World1 extends World{

    private static final String TAG = "World1";

    public World1(MarioSurfaceView view){
        super(view);
        addElements(view);
    }

    public void addElements(MarioSurfaceView view){
        int offset = 0;

        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<50; j++) {
                scene.add(new Obstacle(j*imageLoader.get(3).getWidth(),screenHeight-imageLoader.get(3).getHeight()*i , imageLoader.get(3)));
            }
        }
        scene.add(new Obstacle(tileWidth*10,screenHeight-tileWidth*3, imageLoader.get(3)));
        addLine(tileWidth*40, screenHeight-tileWidth*4, 10, imageLoader.get(2));
        addStairsR(tileWidth*15, screenHeight-tileWidth*3, 5, imageLoader.get(3));
        addStairsL(tileWidth*24, screenHeight-tileWidth*3, 5, imageLoader.get(3));
        enemies.add(new Goomba(2 * screenWidth / 3, screenHeight / 2, view, scene));

        scene.add(new Obstacle(tileWidth*6,screenHeight-tileWidth*7, imageLoader.get(0), new Item(tileWidth*6,screenHeight-tileWidth*8, 0,view), itemList));

        offset = ((scene.get(scene.size()-1)).getX()+tileWidth);  //New x after last obstacle
        Log.v(TAG,"Offset: "+offset);
        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<10; j++) {
                scene.add(new Obstacle(j*tileWidth+2*offset,screenHeight-tileWidth*i , imageLoader.get(2)));
            }
        }
    }
}
