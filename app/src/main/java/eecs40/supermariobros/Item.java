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

    private int imageWidth, imageHeight;
    private Bitmap image;
    private ArrayList<Bitmap> imageLoader;
    public int type;
    private float dx=0, dy;
    private static final float gravity = 3f;
    private boolean ground;
    private ArrayList<Obstacle> scene;
    private MarioSurfaceView view;
    private float screenHeight, screenWidth;

    public Item(int x, int y, int type, MarioSurfaceView view, ArrayList<Obstacle> scene){
        super(x, y);
        initX = x;
        initY = y;
        this.scene = scene;
        this.view = view;
        screenHeight = view.getHeight();
        screenWidth = view.getWidth();

        loadImages(view);
        image = imageLoader.get(type);
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        dst = new Rect(x, y, (x+imageWidth), (y+imageHeight));

        top = new Rect((x+imageWidth/4), y, (x+imageWidth-imageWidth/4), (y+imageHeight/4));
        bot = new Rect((x+imageWidth/4), (y+imageHeight-imageHeight/4), (x+imageWidth-imageWidth/4), (y+imageHeight));

        left = new Rect(x, (y+imageHeight/4), (x+imageWidth/2), (y+imageHeight-imageHeight/4));
        right = new Rect((x+imageWidth-imageWidth/2), (y+imageHeight/4), (x+imageWidth), (y+imageHeight-imageHeight/4));
    }

    public Item(int x, int y, int type, MarioSurfaceView view){
        super(x, y);
        this.scene = scene;
        this.view = view;
        screenHeight = view.getHeight();
        screenWidth = view.getWidth();

        loadImages(view);
        image = imageLoader.get(type);
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        dst = new Rect(x, y, (x+imageWidth), (y+imageHeight));

        top = new Rect((x+imageWidth/4), y, (x+imageWidth-imageWidth/4), (y+imageHeight/4));
        bot = new Rect((x+imageWidth/4), (y+imageHeight-imageHeight/4), (x+imageWidth-imageWidth/4), (y+imageHeight));

        left = new Rect(x, (y+imageHeight/4), (x+imageWidth/2), (y+imageHeight-imageHeight/4));
        right = new Rect((x+imageWidth-imageWidth/2), (y+imageHeight/4), (x+imageWidth), (y+imageHeight-imageHeight/4));
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
        //checkPlatformIntersect();
        if(y>screenHeight+imageHeight)
            die();
        /*
        if(type == 0){
            dx = 7f;
            if(ground){
                dy = 0;
            }
            else if(!ground){
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
        */
        y += dy;
        //dy += gravity;
        x+=dx+bgdx;
        setLocation(x, y);
        draw(c);
    }

    public void setLocation(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        top.set(x, y, (x+imageWidth), (y+imageHeight/4));
        bot.set(x, (y+imageHeight-imageHeight/4), (x+imageWidth), (y+imageHeight));
        left.set(x, (y+imageHeight/4), (x+imageWidth/2), (y+imageHeight-imageHeight/4));
        right.set((x + imageWidth - imageWidth / 2), (y + imageHeight / 4), (x + imageWidth), (y + imageHeight - imageHeight / 4));
        dst.set(xPos, yPos, (xPos + imageWidth), (yPos + imageHeight));
    }

    public void checkPlatformIntersect(){
        for(Obstacle o : scene) {
            if(bot.intersect(o.getTop())){
                ground=true;
                dy = 0;
                float d = Math.abs(y+imageHeight-o.getTop().top);  //distance of intersection sides
                y-=d-2;						//touch ground (top of object), counter against gravity
                break;
            } else if(!bot.intersect(o.getTop())){
                ground = false;
                //if none of objects' top touch mario bottom, no ground
            }
        }
    }

    public void revive(){
        dead = false;
        visible = false;
        x = initX;
        y = initY;
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

    public String toString(){
        return "Item: ("+x+","+y+") Visible: "+visible;
    }
}
