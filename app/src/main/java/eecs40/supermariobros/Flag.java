package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Alex on 5/21/2015.
 */
public class Flag extends Obstacle {

    private Bitmap image;

    public Flag(int x, int y, MarioSurfaceView view){
        super(x, y);
        BitmapFactory.Options options = new BitmapFactory.Options();
        image = BitmapFactory.decodeResource(view.getResources(), R.drawable.flag, options);
        dst = new Rect(x, y-image.getHeight(), x+image.getWidth(), y);
        top = new Rect(x, y-image.getHeight(), x+image.getWidth(), y);
        bot = new Rect(x, y-image.getHeight(), x+image.getWidth(), y);
        left = new Rect(x, y-image.getHeight(), x+image.getWidth(), y);
        right = new Rect(x, y-image.getHeight(), x+image.getWidth(), y);
    }

    public void setLocation(int x, int y){
        dst.set(x, y-image.getHeight(), x+image.getWidth(), y);
        top.set(x, y-image.getHeight(), x+image.getWidth(), y);
        bot.set(x, y-image.getHeight(), x+image.getWidth(), y);
        left.set(x, y-image.getHeight(), x+image.getWidth(), y);
        right.set(x, y-image.getHeight(), x+image.getWidth(), y);
    }

    public void tick(Canvas c){
        x+=bgdx;
        setLocation(x,y);
        draw(c);
    }

    protected void draw(Canvas c){
        if(visible){
            Paint paint = new Paint();
            c.drawBitmap(image, null, dst, paint);
        }
    }
}
