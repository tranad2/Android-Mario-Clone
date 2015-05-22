package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 5/15/2015.
 */
public class Bullet extends Sprite implements TimeConscious{

    private Bitmap currentImage;
    private ArrayList<Obstacle> scene;
    private int imageWidth, imageHeight;
    private int screenWidth, screenHeight;
    private int initX2;

    public Bullet(int x, int y, float dx,MarioSurfaceView view, ArrayList<Obstacle> scene){
        super(x, y);
        initX = x;
        initX2 = initX;
        initY = y;
        this.dx = dx;
        initDx = dx;

        screenWidth = view.getWidth();
        screenHeight = view.getHeight();
        //Load images
        BitmapFactory.Options options = new BitmapFactory.Options();

        currentImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.bulletbill1, options);

        imageWidth = currentImage.getWidth();
        imageHeight = currentImage.getHeight();

        dst = new Rect(x, y, (x+imageWidth), (y+imageHeight));

        this.scene = scene;

        //Design hitbox
        int x2 = x+imageWidth;
        int y2 = y+imageHeight;
        top = new Rect(x, y, x2, y+imageHeight/4);
        bot = new Rect(x, y2-imageHeight/4, x2, y2);

        left = new Rect(x+imageWidth/6, y+imageHeight/4, x+imageWidth/2, y+imageHeight/4);
        right = new Rect(x2-imageWidth/2, y2-imageHeight/4, x2-imageWidth/6, y2-imageHeight/4);

    }

    public void tick(Canvas c){
        checkSideIntersect();
        if(x<-screenWidth/6) {
            x = initX2;
            dead = false;
            visible = true;
        }
        x += dx + bgdx;
        initX2+=bgdx;
        setLocation(x, y);
        draw(c);

    }

    protected void draw(Canvas c){
        if(visible) {
            Paint paint = new Paint();
            c.drawBitmap(currentImage, null, dst, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            c.drawRect(top, paint);
            c.drawRect(bot,paint);
            paint.setColor(Color.CYAN);
            c.drawRect(left,paint);
            c.drawRect(right, paint);
        }
    }

    //Checks side collision and resets position
    public void checkSideIntersect(){
        for(Obstacle o: scene){
            if(o.getLeft().intersect(right) || o.getRight().intersect(left)){
                visible = false;
                dead = true;
            }

        }
    }

    //Change Bullet location
    public void setLocation(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        int x2 = (x + imageWidth);
        int y2 = (y + imageHeight);
        dst.set(x, y, x2, y2);

        top.set(x,y,x2,y+imageHeight/4);
        bot.set(x,y2-imageHeight/4,x2,y2);
        left.set(x+imageWidth/6,y+imageHeight/4,x+imageWidth/2,y2-imageHeight/4);
        right.set(x2-imageWidth/2,y+imageHeight/4,x2-imageWidth/6,y2-imageHeight/4);
    }
}
