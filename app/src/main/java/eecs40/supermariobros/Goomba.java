package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Goomba extends Sprite implements TimeConscious{

    private static final String TAG = "Goomba";
    private Bitmap currentImage;
    private Mario mario;
    private float dx=-5f, bgdx, dy, imageWidth, imageHeight;
    private boolean ground;
    private final float gravity = 0.75f;
    private ArrayList<Obstacle> scene;
//    private Mario mario;

    public Goomba(int x, int y, MarioSurfaceView view, ArrayList<Obstacle> scene){
        super(x,y);
        //Load images
        BitmapFactory.Options options = new BitmapFactory.Options();

        currentImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.goomba1, options);

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

    public void setBackgroundDx(float bgdx) { this.bgdx = bgdx; }

    public void tick(Canvas c){
        checkSideIntersect();
        checkPlatformIntersect();
        Log.v("STUFF"," X:"+x+" Y:"+y);
        if(ground){
            dy = 0;
        }
        else if(!ground){
            y += dy;
            dy += gravity;
            if (Math.abs(dy) > 60) {
                if (dy > 0) {
                    dy = 60;
                }
                else {
                    dy = -60;
                }
            }
        }
        x+=dx + bgdx;
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

            if(o.getLeft().intersect(right)){
                if(dx>0){
                    dx = -dx;
                    float d = Math.abs(x+imageWidth-o.getLeft().left);
                    x-=1.85f;
                }
            } if (o.getRight().intersect(left)){
                if(dx<0) {
                    dx = -dx;
                    float d = Math.abs(x-o.getRight().right);
                    x += 1.85f;
                }
            }

        }
    }

    public void checkPlatformIntersect(){
        //TODO
        //Log.v(TAG,"Enter"+scene.size());
        for(Obstacle o : scene) {
            if(bot.intersect(o.getTop())){
                Log.v(TAG, "Intersect");
                ground=true;
                dy = 0;
                float d = Math.abs(y+imageHeight-o.getTop().top);  //distance of intersection sides
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

    public void setLocation(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        float x2 = (x + imageWidth);
        float y2 = (y + imageHeight);
        dst.set((int)x, (int)y, (int)x2, (int)y2);
        top.set((int)x,(int)y,(int)x2,(int)(y+imageHeight/4));
        bot.set((int)x,(int)(y2-imageHeight/4),(int)x2,(int)y2);
        left.set((int)x,(int)y,(int)(x+imageWidth/2),(int)y2);
        right.set((int)(x2-imageWidth/2),(int)y,(int)x2,(int)y2);
    }
}
