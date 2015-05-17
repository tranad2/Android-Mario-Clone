package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Alex on 5/15/2015.
 */
public class Item extends Sprite implements TimeConscious{

    private float bmpWidth, bmpHeight;
    private Bitmap image;
    private ArrayList<Bitmap> imageLoader;
    public int type;
    private float dx=0, dy;
    private static final float gravity = 3f;
    private boolean ground;
    private ArrayList<Obstacle> scene;


    public Item(int x, int y, int type, MarioSurfaceView view, ArrayList<Obstacle> scene){
        super(x, y);
        this.scene = scene;
        loadImages(view);
        image = imageLoader.get(type);
        bmpWidth = image.getWidth();
        bmpHeight = image.getHeight();
        dst = new Rect(x, y, (int)(x+bmpWidth), (int)(y+bmpHeight));

        top = new Rect((int)(x+bmpWidth/4), y, (int)(x+bmpWidth-bmpWidth/4), (int)(y+bmpHeight/4));
        bot = new Rect((int)(x+bmpWidth/4), (int)(y+bmpHeight-bmpHeight/4), (int)(x+bmpWidth-bmpWidth/4), (int)(y+bmpHeight));

        left = new Rect(x, (int)(y+bmpHeight/4), (int)(x+bmpWidth/2), (int)(y+bmpHeight-bmpHeight/4));
        right = new Rect((int)(x+bmpWidth-bmpWidth/2), (int)(y+bmpHeight/4), (int)(x+bmpWidth), (int)(y+bmpHeight-bmpHeight/4));
    }

    public void loadImages(MarioSurfaceView view){
        imageLoader = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap mushroom1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.mushroom1, options);
        Bitmap flower1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.flower1, options);
        Bitmap coin1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.coin1, options);
        imageLoader.add(mushroom1);
        imageLoader.add(flower1);
        imageLoader.add(coin1);

    }

    public void tick(Canvas c){
        checkPlatformIntersect();
        if(type == 0){
            dx = 10f;
            if(ground){
                dy = 0;
            }
            else if(!ground){
                y += dy;
                dy += gravity;
                if (Math.abs(dy) > 100) {
                    if (dy > 0) {
                        dy = 100;
                    }
                    else {
                        dy = -100;
                    }
                }
            }
        }

        x+=dx+bgdx;
        setLocation((int) x, (int) y);
        draw(c);
    }

    public void setLocation(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        top.set((int)x, (int)y, (int)(x+bmpWidth), (int)(y+bmpHeight/4));
        bot.set((int)x, (int)(y+bmpHeight-bmpHeight/4), (int)(x+bmpWidth), (int)(y+bmpHeight));
        left.set((int)x, (int)(y+bmpHeight/4), (int)(x+bmpWidth/2), (int)(y+bmpHeight-bmpHeight/4));
        right.set((int) (x + bmpWidth - bmpWidth / 2), (int) (y + bmpHeight / 4), (int) (x + bmpWidth), (int) (y + bmpHeight - bmpHeight / 4));
        dst.set(xPos, yPos, (int) (xPos + bmpWidth), (int) (yPos + bmpHeight));
    }

    public void checkPlatformIntersect(){
        //TODO
        //Log.v(TAG,"Enter"+scene.size());
        for(Obstacle o : scene) {
            if(bot.intersect(o.getTop())){
                ground=true;
                dy = 0;
                float d = Math.abs(y+bmpHeight-o.getTop().top);  //distance of intersection sides
                y-=d - 2;						//touch ground (top of object), counter against gravity
                break;
            } else if(!bot.intersect(o.getTop())){
                ground = false;
                //if none of objects' top touch mario bottom, no grou
                //return ground;
            } else if(top.intersect(o.getBot())){			//if mario top touch object bottom (ceiling), no ground and stop moving in that direction, offset outside rectangle

                //ground=false;
                //dy = 0;

                //this.y1+=.1f;
            }
        }
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
