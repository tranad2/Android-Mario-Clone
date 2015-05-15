package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Alex on 5/15/2015.
 */
public abstract class World implements TimeConscious{

    protected Mario mario;
    protected ArrayList<Bitmap> imageLoader;
    public World(MarioSurfaceView view){
        //loadImages(view);
    }

    public abstract void tick(Canvas c);


}
