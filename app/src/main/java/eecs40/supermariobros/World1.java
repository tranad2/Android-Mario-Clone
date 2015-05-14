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
    private ArrayList<Bitmap> imageLoader;

    public World1(MarioSurfaceView view){
        screenWidth = view.getWidth();
        screenHeight = view.getHeight();

        scene = new ArrayList<>();
        loadImages(view);

        float tileWidth = imageLoader.get(0).getWidth();
        int offset = 0;

        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<10; j++) {
                scene.add(new Obstacle(j*imageLoader.get(3).getWidth(),screenHeight-imageLoader.get(3).getHeight()*i , imageLoader.get(3)));
            }
        }

        offset = (int)((scene.get(scene.size()-1)).getX()+tileWidth);  //New x after last obstacle
/*
        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<10; j++) {
                scene.add(new Obstacle(j*imageLoader.get(3).getWidth()+2*offset,screenHeight-imageLoader.get(3).getHeight()*i , imageLoader.get(3)));
            }
        }
*/

        Log.v(TAG,""+scene.isEmpty());
    }

    public void loadImages(MarioSurfaceView view){
        imageLoader = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap block1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.block1, options);
        Bitmap block2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.block2, options);
        Bitmap brick1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.brick1, options);
        Bitmap tile1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.tile1, options);

        imageLoader.add(block1);
        imageLoader.add(block2);
        imageLoader.add(brick1);
        imageLoader.add(tile1);



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
