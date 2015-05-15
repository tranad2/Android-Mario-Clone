package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.sql.Time;

/**
 * Created by Alex on 5/15/2015.
 */
public class Item extends Sprite implements TimeConscious{

    private Bitmap image;

    public Item(int x, int y, Bitmap image){
        super(x,y);
        this.image = image;
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();
        dst = new Rect(x, y, (int)(x+imageWidth), (int)(y+imageHeight));

        top = new Rect(x, y, (int)(x+imageWidth), (int)(y+imageHeight/4));
        bot = new Rect(x, (int)(y+3*imageHeight/4), (int)(x+imageWidth), (int)(y+imageHeight));

        left = new Rect(x, (int)(y+imageHeight/4), (int)(x+imageWidth/2), (int)(y+imageHeight/4));
        right = new Rect((int)(x+imageWidth/2), (int)(y+3*imageHeight/4), (int)(x+imageWidth), (int)(y+3*imageHeight/4));
    }

    public void tick(Canvas c){

    }

    protected void draw(Canvas c){

    }




}
