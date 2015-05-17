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
    private float dx=-5f, dy, imageWidth, imageHeight;
    private boolean ground;
    private int goombaTimer=0;
    private final float gravity = 1.4f;
    private ArrayList<Obstacle> scene;
    private ArrayList<Bitmap> spriteLoader;
//    private Mario mario;

    public Goomba(int x, int y, MarioSurfaceView view, ArrayList<Obstacle> scene){
        super(x, y);
        //Load images
        loadImages(view);
        currentImage = spriteLoader.get(0);

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

    public void loadImages(MarioSurfaceView view){
        spriteLoader = new ArrayList<>();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap goomba1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.goomba1, options);
        Bitmap goomba2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.goomba2, options);

        spriteLoader.add(goomba1);
        spriteLoader.add(goomba2);
    }

    public void doAnim() {
        if(goombaTimer <= 10){
            currentImage = spriteLoader.get(0);
            goombaTimer++;
        } else if(goombaTimer <= 20) {
            currentImage = spriteLoader.get(1);
            goombaTimer++;
        } else if (goombaTimer <=30) {
            goombaTimer = 0;
        }
    }

    public void tick(Canvas c){
        checkSideIntersect();
        checkPlatformIntersect();
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
        doAnim();
        draw(c);

    }

    protected void draw(Canvas c){
        Paint paint = new Paint();
        c.drawBitmap(currentImage, null, dst, paint);
    }
    public void checkSideIntersect(){
        for(Obstacle o: scene){

            if(o.getLeft().intersect(right)){
                if(dx>0){
                    dx=0;
                    float d = Math.abs(x+imageWidth-o.getLeft().left);
                    this.x-=d-1;
                }
            } if (o.getRight().intersect(left)){
                if(dx<0) {
                    dx = 0;
                    float d = Math.abs(x-o.getRight().right);
                    this.x+=d-1;
                }
            }

        }
    }

    public void checkPlatformIntersect(){
        for(Obstacle o : scene) {
            if(bot.intersect(o.getTop())){//Top intersect
                Log.v(TAG,"TOP");
                ground=true;
                dy = 0;
                float d = Math.abs(y+imageHeight-o.getTop().top);  //distance of intersection sides use for offset
                y-=d-2;
                break;
            }
            else if(!bot.intersect(o.getTop())){//Free fall
                ground = false; //if none of objects' top touch bottom, no ground
            }

            if(top.intersect(o.getBot())){//Bot intersect
                //if top touch object bottom (ceiling), no ground and stop moving in that direction, offset outside rectangle
                dy = -dy;
                float d = Math.abs(y-o.getBot().bottom);  //distance of intersection sides
                this.y+=d;
                ground = false;
                break;
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
