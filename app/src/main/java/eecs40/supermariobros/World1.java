package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 5/10/2015.
 */
public class World1 {

    private static final String TAG = "World1";
    private int screenWidth, screenHeight;
    private ArrayList<Obstacle> scene;
    public World1(MarioSurfaceView view){
        screenWidth = view.getWidth();
        screenHeight = view.getHeight();

        scene = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap imgBlock = BitmapFactory.decodeResource(view.getResources(), R.drawable.block1, options);
        int imgHeight = imgBlock.getHeight();
        scene.add(new Obstacle(screenWidth/2,screenHeight-imgHeight, imgBlock));
        Log.v(TAG,""+scene.isEmpty());
    }

    public void tick(Canvas c){
        for(Obstacle o : scene){
            o.tick(c);
        }

    }

    protected void draw(Canvas c){
        for(Obstacle o : scene){
            o.draw(c);
        }
    }

    public ArrayList<Obstacle> getObstacles(){
        return scene;
    }

}
