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
    private float imageWidth, imageHeight;
    private float dx;

    public Bullet(int x, int y, float dx,MarioSurfaceView view, ArrayList<Obstacle> scene){
        super(x, y);

        //Load images
        BitmapFactory.Options options = new BitmapFactory.Options();

        currentImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.bulletbill1, options);

        imageWidth = currentImage.getWidth();
        imageHeight = currentImage.getHeight();

        dst = new Rect(x, y, (int)(x+imageWidth), (int)(y+imageHeight));

        this.scene = scene;

        //Design hitbox
        top = new Rect((int)(x+imageWidth/6), y, (int)(x+imageWidth-imageWidth/6), (int)(y+imageHeight/4));
        bot = new Rect((int)(x+imageWidth/6), (int)(y+imageHeight-imageHeight/4), (int)(x+imageWidth-imageWidth/6), (int)(y+imageHeight));

        left = new Rect(x, (int)(y+imageHeight/4), (int)(x+imageWidth/2), (int)(y+imageHeight/4));
        right = new Rect((int)(x+imageWidth-imageWidth/2), (int)(y-imageHeight/4), (int)(x+imageWidth), (int)(y+imageHeight-imageHeight/4));

    }

    public void tick(Canvas c){
        checkSideIntersect();
        x+=dx+bgdx;
        setLocation((int)x, (int)y);
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
        float x2 = (x + imageWidth);
        float y2 = (y + imageHeight);
        dst.set((int)x, (int)y, (int)x2, (int)y2);
        top.set((int)x,(int)y,(int)x2,(int)(y+imageHeight/4));
        bot.set((int)x,(int)(y2-imageHeight/4),(int)x2,(int)y2);
        left.set((int)x,(int)y,(int)(x+imageWidth/2),(int)y2);
        right.set((int) (x2 - imageWidth / 2), (int) y, (int) x2, (int) y2);
    }
}
