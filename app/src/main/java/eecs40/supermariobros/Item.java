package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.sql.Time;

/**
 * Created by Alex on 5/15/2015.
 */
public class Item extends Sprite implements TimeConscious{

    private float bmpWidth, bmpHeight;
    private Bitmap image;

    public Item(int x, int y, Bitmap img){
        super(x, y);
        image = img;
        bmpWidth = img.getWidth();
        bmpHeight = img.getHeight();
        dst = new Rect(x, y, (int)(x+bmpWidth), (int)(y+bmpHeight));

        top = new Rect((int)(x+bmpWidth/4), y, (int)(x+bmpWidth-bmpWidth/4), (int)(y+bmpHeight/4));
        bot = new Rect((int)(x+bmpWidth/4), (int)(y+bmpHeight-bmpHeight/4), (int)(x+bmpWidth-bmpWidth/4), (int)(y+bmpHeight));

        left = new Rect(x, (int)(y+bmpHeight/4), (int)(x+bmpWidth/2), (int)(y+bmpHeight-bmpHeight/4));
        right = new Rect((int)(x+bmpWidth-bmpWidth/2), (int)(y+bmpHeight/4), (int)(x+bmpWidth), (int)(y+bmpHeight-bmpHeight/4));
    }

    public void tick(Canvas c){
        x+=bgdx;
        setLocation((int) x, (int) y);
        draw(c);
    }

    public void setLocation(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        top.set((int)x, (int)y, (int)(x+bmpWidth), (int)(y+bmpHeight/4));
        bot.set((int)x, (int)(y+bmpHeight-bmpHeight/4), (int)(x+bmpWidth), (int)(y+bmpHeight));
        left.set((int)x, (int)(y+bmpHeight/4), (int)(x+bmpWidth/2), (int)(y+bmpHeight-bmpHeight/4));
        right.set((int)(x+bmpWidth-bmpWidth/2), (int)(y+bmpHeight/4), (int)(x+bmpWidth), (int)(y+bmpHeight-bmpHeight/4));
        dst.set(xPos, yPos, (int) (xPos + bmpWidth), (int) (yPos + bmpHeight));
    }

    public void draw(Canvas c){
        if(visible) {
            Paint paint = new Paint();
            c.drawBitmap(image, null, dst, paint);
            //c.drawRect(top, paint);
            //c.drawRect(bot,paint);
            //c.drawRect(left,paint);
            //c.drawRect(right,paint);
        }
    }

}
