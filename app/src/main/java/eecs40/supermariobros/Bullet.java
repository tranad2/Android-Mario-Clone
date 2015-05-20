package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
    private float dx;

    public Bullet(int x, int y, float dx,MarioSurfaceView view, ArrayList<Obstacle> scene){
        super(x, y);
        initX = x;
        initY = y;
        initDx = dx;
        this.dx = dx;
        //Load images
        BitmapFactory.Options options = new BitmapFactory.Options();

        currentImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.bulletbill1, options);

        imageWidth = currentImage.getWidth();
        imageHeight = currentImage.getHeight();

        dst = new Rect(x, y, (x+imageWidth), (y+imageHeight));

        this.scene = scene;

        //Design hitbox
        top = new Rect((x+imageWidth/6), y, (x+imageWidth-imageWidth/6), (y+imageHeight/4));
        bot = new Rect((x+imageWidth/6), (y+imageHeight-imageHeight/4), (x+imageWidth-imageWidth/6), (y+imageHeight));

        left = new Rect(x, (y+imageHeight/4), (x+imageWidth/2), (y+imageHeight/4));
        right = new Rect((x+imageWidth-imageWidth/2), (y-imageHeight/4), (x+imageWidth), (y+imageHeight-imageHeight/4));

    }

    public void tick(Canvas c){
        checkSideIntersect();
        if(visible) {
            x += dx + bgdx;
        }
        setLocation(x, y);
        draw(c);

    }

    protected void draw(Canvas c){
        Paint paint = new Paint();
        c.drawBitmap(currentImage, null, dst, paint);
    }
    public void checkSideIntersect(){
        //boolean sidecollision = false;
        for(Obstacle o: scene){

            if(o.getLeft().intersect(right) || o.getRight().intersect(left)){
                visible = false;
            }

        }
    }

    public void setLocation(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        int x2 = (x + imageWidth);
        int y2 = (y + imageHeight);
        dst.set(x, y, x2, y2);
        top.set(x,y,x2,(y+imageHeight/4));
        bot.set(x,(y2-imageHeight/4),x2,y2);
        left.set(x,y,(x+imageWidth/2),y2);
        right.set( (x2 - imageWidth / 2),  y,  x2,  y2);
    }
}
