package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 5/9/2015.
 */

public class Obstacle extends Sprite implements TimeConscious{

    private static final String TAG = "Obstacle";
    private int imageWidth, imageHeight;
    private Bitmap image;
    public Item item;

    public Obstacle(int x, int y, Bitmap img){
        super(x, y);
        image = img;
        imageWidth = img.getWidth();
        imageHeight = img.getHeight();
        dst = new Rect(x, y, (x+imageWidth), (y+imageHeight));

        top = new Rect((x+imageWidth/4), y, (x+imageWidth-imageWidth/4), (y+imageHeight/4));
        bot = new Rect((x+imageWidth/4), (y+imageHeight-imageHeight/4), (x+imageWidth-imageWidth/4), (y+imageHeight));

        left = new Rect(x, (y+imageHeight/4), (x+imageWidth/2), (y+imageHeight-imageHeight/4));
        right = new Rect((x+imageWidth-imageWidth/2), (y+imageHeight/4), (x+imageWidth), (y+imageHeight-imageHeight/4));
    }

    public Obstacle(int x, int y, Bitmap img, Item item, ArrayList<Item> itemList){
        super(x,y);
        this.item = item;
        item.setVisible(false);
        item.setDead(false);

        Log.v(TAG,""+item);
        itemList.add(item);

        image = img;
        imageWidth = img.getWidth();
        imageHeight = img.getHeight();
        dst = new Rect(x, y, (x+imageWidth), (y+imageHeight));

        top = new Rect((x+imageWidth/4), y, (x+imageWidth-imageWidth/4), (y+imageHeight/4));
        bot = new Rect((x+imageWidth/4), (y+imageHeight-imageHeight/4), (x+imageWidth-imageWidth/4), (y+imageHeight));

        left = new Rect(x, (y+imageHeight/4), (x+imageWidth/2), (y+imageHeight-imageHeight/4));
        right = new Rect((x+imageWidth-imageWidth/2), (y+imageHeight/4), (x+imageWidth), (y+imageHeight-imageHeight/4));
    }

    public Obstacle(int x, int y){
        super(x, y);
    }

    public Bitmap flipImageV(Bitmap src) {
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public void tick(Canvas c){
        x += bgdx;
        setLocation(x, y);
        draw(c);
    }

    //Change obstacle location
    public void setLocation(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        top.set(x, y, (x+imageWidth), (y+imageHeight/4));
        bot.set(x, (y+imageHeight-imageHeight/4), (x+imageWidth), (y+imageHeight));
        left.set(x, (y+imageHeight/4), (x+imageWidth/2), (y+imageHeight-imageHeight/4));
        right.set((x + imageWidth - imageWidth / 2), (y + imageHeight / 4), (x + imageWidth), (y + imageHeight - imageHeight / 4));
        dst.set(xPos, yPos, (xPos + imageWidth), (yPos + imageHeight));
    }

    protected void draw(Canvas c){
        if(visible) {
            Paint paint = new Paint();
            c.drawBitmap(image, null, dst, paint);
        }
    }

    //@return true if obstacle carrying item
    public boolean hasItem(){
        if(item!=null) {
            Log.v("Obstacle","HasItem"+true);
            return true;
        }
        else
            return false;
    }

    //return item obstacle is carrying
    public Item getItem(){

        if(item!=null) {
            return item;
        }
        else {
            return null;
        }
    }
}