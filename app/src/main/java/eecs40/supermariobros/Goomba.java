package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Goomba extends Sprite implements TimeConscious {

    private static final String TAG = "Goomba";
    private Bitmap currentImage;
    private Mario mario;
    private boolean ground;
    private int goombaTimer = 0, moveGoomba = 0, imageWidth, imageHeight;
    private final float gravity = 1.4f;
    private ArrayList<Obstacle> scene;
    private ArrayList<Bitmap> spriteLoader;

    public Goomba(int x, int y, MarioSurfaceView view, ArrayList<Obstacle> scene) {
        super(x, y);
        initX = x;
        initY = y;
        dx = 5f;
        initDx = dx;
        initDy = dy;
        //Load images
        loadImages(view);
        currentImage = spriteLoader.get(0);

        imageWidth = currentImage.getWidth();
        imageHeight = currentImage.getHeight();

        dst = new Rect(x, y, (int) (x + imageWidth), (int) (y + imageHeight));

        this.scene = scene;
        //Design hitbox

        top = new Rect((x + imageWidth / 6), y,  (x + imageWidth - imageWidth / 6),  (y + imageHeight / 4));
        bot = new Rect( (x + imageWidth / 6),  (y + imageHeight - imageHeight / 4), (x + imageWidth - imageWidth / 6),  (y + imageHeight));

        left = new Rect(x,  (y + imageHeight / 4),  (x + imageWidth / 2),  (y + imageHeight - imageHeight / 4));
        right = new Rect( (x + imageWidth - imageWidth / 2),  (y + imageHeight / 4),  (x + imageWidth),  (y + imageHeight - imageHeight / 4));

    }

    //Load images to array
    public void loadImages(MarioSurfaceView view) {
        spriteLoader = new ArrayList<>();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap goomba1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.goomba1, options);
        Bitmap goomba2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.goomba2, options);

        spriteLoader.add(goomba1);
        spriteLoader.add(goomba2);
    }

    //Run thorugh animation
    public void doAnim() {
        if (goombaTimer <= 10) {
            currentImage = spriteLoader.get(0);
            goombaTimer++;
        } else if (goombaTimer <= 20) {
            currentImage = spriteLoader.get(1);
            goombaTimer++;
        } else if (goombaTimer <= 30) {
            goombaTimer = 0;
        }
    }

    public void tick(Canvas c) {
        checkSideIntersect();
        checkPlatformIntersect();
        if (ground) {
            dy = 0;
        } else if (!ground) {

            if (Math.abs(dy) > 60) {
                if (dy > 0) {
                    dy = 60;
                } else {
                    dy = -60;
                }
            }
        }
        if (moveGoomba <= 25) {
            dx = 5f;
            moveGoomba++;
        } else if (moveGoomba <= 50) {
            dx = -5f;
            moveGoomba++;
        } else if (moveGoomba <= 75) {
            moveGoomba = 0;
        }
        y += dy;
        dy += gravity;
        x += dx + bgdx;
        setLocation(x, y);
        doAnim();
        draw(c);

    }

    protected void draw(Canvas c) {
        if (visible) {
            Paint paint = new Paint();
            c.drawBitmap(currentImage, null, dst, paint);
            /*
            paint.setColor(Color.BLUE);
            c.drawRect(top, paint);
            c.drawRect(bot, paint);
            paint.setColor(Color.MAGENTA);
            c.drawRect(left, paint);
            c.drawRect(right, paint);
            */
        }
    }

    //Check for side collision
    public void checkSideIntersect() {
        for (Obstacle o : scene) {

            if (o.getLeft().intersect(right)) {
                if (dx > 0) {
                    dx = -dx;
                    float d = Math.abs(x + imageWidth - o.getLeft().left);
                    this.x -= d - 1;
                }
            }
            if (o.getRight().intersect(left)) {
                if (dx < 0) {
                    dx = -dx;
                    float d = Math.abs(x - o.getRight().right);
                    this.x += d - 1;
                }
            }

        }
    }

    //Check for behavior on platforms
    public void checkPlatformIntersect() {
        for (Obstacle o : scene) {
            if (bot.intersect(o.getTop())) {//Top intersect
                ground = true;
                dy = 0;
                float d = Math.abs(y + imageHeight - o.getTop().top);  //distance of intersection sides use for offset
                y -= d - 2;
                break;
            } else if (!bot.intersect(o.getTop())) {//Free fall
                ground = false; //if none of objects' top touch bottom, no ground
            }

            if (top.intersect(o.getBot())) {//Bot intersect
                //if top touch object bottom (ceiling), no ground and stop moving in that direction, offset outside rectangle
                dy = -dy;
                float d = Math.abs(y - o.getBot().bottom);  //distance of intersection sides
                this.y += d;
                ground = false;
                break;
            }
        }
    }

    //Change goomba location
    public void setLocation(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        float x2 = (x + imageWidth);
        float y2 = (y + imageHeight);
        dst.set(x, y, (int) x2, (int) y2);

        top.set((int) (x + imageWidth / 6), (int) y, (int) (x + imageWidth - imageWidth / 6), (int) (y + imageHeight / 4));
        bot.set((int) (x + imageWidth / 6), (int) (y + imageHeight - imageHeight / 4), (int) (x + imageWidth - imageWidth / 6), (int) (y + imageHeight));

        left.set(x, (int) (y + imageHeight / 4), (int) (x + imageWidth / 2), (int) (y + imageHeight - imageHeight / 4));
        right.set((int) (x + imageWidth - imageWidth / 2), (int) (y + imageHeight / 4), (int) (x + imageWidth), (int) (y + imageHeight - imageHeight / 4));
    }

    //Resets goomba move timer, visibile, dead, x, y, dx, dy
    public void revive(){
        super.revive();
        moveGoomba = 0;
        goombaTimer = 0;
    }
}
