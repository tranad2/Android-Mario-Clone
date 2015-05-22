package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Fireball extends Sprite implements TimeConscious{
    private static final String TAG = "Fireball";
    private ArrayList<Bitmap> spriteLoader;
    private ArrayList<Sprite> enemies;
    private ArrayList<Obstacle> scene;
    private Bitmap currentImage,fireball1, fireball2, fireball3, fireball4;
    private int x, y, dir = 1, timer = 0, fireballWidth, fireballHeight, screenWidth, screenHeight;
    private float dx = 40f;
    private Rect dst;
    private MarioSurfaceView view;

    public Fireball( MarioSurfaceView view, int marioX, int marioY, int marioDir, ArrayList<Sprite> enemies, ArrayList<Obstacle> scene) {
        super(marioX,marioY);
        this.enemies = enemies;
        this.scene = scene;
        this.view = view;
        //Load fireball bitmaps
        loadImages(view);
        currentImage = spriteLoader.get(0);

        //Fireball dimensions
        fireballWidth = spriteLoader.get(0).getWidth();
        fireballHeight = spriteLoader.get(0).getHeight();

        //Initialize position
        x = marioX;
        y = marioY;
        dst = new Rect(marioX, marioY, marioX + fireballWidth, marioY + fireballHeight);
        dir = marioDir;

        //Screen dimensions
        screenHeight = view.getHeight();
        screenWidth = view.getWidth();
    }

    //Load images to array
    public void loadImages(MarioSurfaceView view){
        spriteLoader = new ArrayList<>();
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (view.color == 0) {
            fireball1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.fireball1, options);
            fireball2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.fireball2, options);
            fireball3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.fireball3, options);
            fireball4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.fireball4, options);
        } else if (view.color == 1) {
            fireball1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenfireball1, options);
            fireball2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenfireball2, options);
            fireball3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenfireball3, options);
            fireball4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenfireball4, options);
        } else if (view.color == 2){
            fireball1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowfireball1, options);
            fireball2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowfireball2, options);
            fireball3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowfireball3, options);
            fireball4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowfireball4, options);
        } else if (view.color == 3) {
            fireball1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplefireball1, options);
            fireball2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplefireball2, options);
            fireball3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplefireball3, options);
            fireball4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplefireball4, options);
        }

        spriteLoader.add(fireball1);
        spriteLoader.add(fireball2);
        spriteLoader.add(fireball3);
        spriteLoader.add(fireball4);
    }

    //Flip bitmap
    public Bitmap flipImage(Bitmap src) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    //Animated fireball
    public void doAnim() {
        if(dir == 1) {
            if(timer <= 2){
                currentImage = spriteLoader.get(0);
                timer++;
            } else if(timer <= 4){
                currentImage = spriteLoader.get(1);
                timer++;
            } else if(timer <= 6){
                currentImage = spriteLoader.get(2);
                timer++;
            } else if(timer <= 8) {
                currentImage = spriteLoader.get(3);
                timer++;
            } else if(timer <= 10) {
                timer = 0;
            }
        } else if (dir == -1){
            if(timer <= 2){
                currentImage = spriteLoader.get(0);
                currentImage = flipImage(currentImage);
                timer++;
            } else if(timer <= 4){
                currentImage = spriteLoader.get(1);
                currentImage = flipImage(currentImage);
                timer++;
            } else if(timer <= 6){
                currentImage = spriteLoader.get(2);
                currentImage = flipImage(currentImage);
                timer++;
            } else if(timer <= 8){
                currentImage = spriteLoader.get(3);
                currentImage = flipImage(currentImage);
                timer++;
            } else if(timer <= 10) {
                timer = 0;
            }
        }
    }

    //Change fireball location
    public void setLocation(int xPos, int yPos) {
        dst.set(xPos, yPos, xPos + fireballWidth, yPos + fireballHeight);

    }

    public void draw (Canvas c) {
        Paint paint = new Paint();
        c.drawBitmap(currentImage, null, dst, paint);
    }

    public void tick(Canvas c) {
        if (dir == 1) {
            x += dx + bgdx;
        } else {
            x -= dx - bgdx;
        }
        if (x >= screenWidth) {
            visible = false;
        }
        setLocation(x, y);
        doAnim();
        draw(c);
    }

    //Check if fireball hit enemy
    public void checkFireball(){
        for(Sprite s : enemies){
            if (s.isVisible()) {
                if (dst.intersect(s.getRect())) {
                    s.die();
                    s.setVisible(false);
                    visible = false;
                    view.score += 1000;
                }
            }
        }
    }

    //Check side collision and disappears
    public void checkSideIntersect(){
        for(Obstacle o: scene){
            if(dst.intersect(o.getLeft()) || dst.intersect(o.getRight())){
                visible = false;
                break;
            }
        }
    }
}
