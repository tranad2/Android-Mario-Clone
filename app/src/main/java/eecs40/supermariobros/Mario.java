package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;

public class Mario implements TimeConscious {

    private ArrayList<Bitmap> spriteLoader;
    private ArrayList<Obstacle> scene;
    private Bitmap currentImage;
    private boolean visible = true, ground, touchFlag;
    private int x1, y1, x2, y2, marioWidth, marioHeight, screenHeight;
    private int dir = 0, timer = 0;
    private float dy;
    private float gravity = 5;
    private Rect dst, top, bot, left, right;

    public Mario(/*ArrayList<Obstacle> scene,*/ MarioSurfaceView view) {
        //Load default bitmap
        //this.scene = scene;

        //Load Mario bitmaps
        loadImages(view);
        currentImage=spriteLoader.get(0);

        //Initialize Mario position
        this.x1 = view.getWidth() / 4;
        this.y1 = view.getHeight() / 2;
        this.x2 = this.x1 + marioWidth;
        this.y2 = this.y1 + marioHeight;
        dst = new Rect(x1, y1, x2, y2);

        //Design hitboxes
        top = new Rect(x1, y1, x2 , y1+marioHeight/4);
        bot = new Rect(x1, y2-marioHeight/4, x2, y2);

        left = new Rect(x1, y1+marioHeight/4, x1+marioWidth/2, y1+marioHeight/4);
        right = new Rect(x2-marioWidth/2, y2-marioHeight/4, x2, y2-marioHeight/4);

        screenHeight = view.getHeight();
        touchFlag = false;
    }

    public void setLocation(int xPos, int yPos) {
        this.x1 = xPos;
        this.y1 = yPos;
        this.x2 = this.x1 + marioWidth;
        this.y2 = this.y1 + marioHeight;
        dst.set(x1, y1, x2, y2);
    }

    public void setTouchFlag(boolean flag) {
        touchFlag = flag;
    }

    //Loads scaled images into array
    public void loadImages(MarioSurfaceView view){
        //TODO add image frames to array
        spriteLoader = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap smallRedMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario1, options);
        marioWidth = smallRedMario1.getWidth() * 2;
        marioHeight = smallRedMario1.getHeight() * 2;
        smallRedMario1 = Bitmap.createScaledBitmap(smallRedMario1, marioWidth, marioHeight, true);

        Bitmap smallRedMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario2, options);
        marioWidth = smallRedMario2.getWidth() * 2;
        marioHeight = smallRedMario2.getHeight() * 2;
        smallRedMario2 = Bitmap.createScaledBitmap(smallRedMario2, marioWidth, marioHeight, true);

        Bitmap smallRedMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario3, options);
        marioWidth = smallRedMario3.getWidth() * 2;
        marioHeight = smallRedMario3.getHeight() * 2;
        smallRedMario3 = Bitmap.createScaledBitmap(smallRedMario3, marioWidth, marioHeight, true);

        Bitmap smallRedMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario4, options);
        marioWidth = smallRedMario4.getWidth() * 2;
        marioHeight = smallRedMario4.getHeight() * 2;
        smallRedMario4 = Bitmap.createScaledBitmap(smallRedMario4, marioWidth, marioHeight, true);

        Bitmap smallRedMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario5, options);
        marioWidth = smallRedMario5.getWidth() * 2;
        marioHeight = smallRedMario5.getHeight() * 2;
        smallRedMario5 = Bitmap.createScaledBitmap(smallRedMario5, marioWidth, marioHeight, true);

        spriteLoader.add(smallRedMario1);
        spriteLoader.add(smallRedMario2);
        spriteLoader.add(smallRedMario3);
        spriteLoader.add(smallRedMario4);
        spriteLoader.add(smallRedMario5);

    }

    //Animates through Mario sprite frames
    public void doAnim(){
        //TODO
        //Run through animation
        if(dir == 1){   //Right direction
            if(timer <= 15){
                currentImage = spriteLoader.get(1);
                timer++;
            } else if(timer <= 30){
                currentImage = spriteLoader.get(2);
                timer++;
            } else if(timer <= 45){
                currentImage = spriteLoader.get(3);
                timer++;
            } else if(timer <= 60){
                currentImage = spriteLoader.get(2);
                timer = 0;
            }
        } else if( dir == -1){   //Left direction
            if(timer <= 15){
                currentImage = spriteLoader.get(1);
                timer++;
            } else if(timer <= 30){
                currentImage = spriteLoader.get(2);
                timer++;
            } else if(timer <= 45){
                currentImage = spriteLoader.get(3);
                timer++;
            } else if(timer <= 60){
                currentImage = spriteLoader.get(2);
                timer = 0;
            }
        } else{     //Standing still
            if(currentImage == spriteLoader.get(0) || currentImage == spriteLoader.get(1) || currentImage == spriteLoader.get(2) || currentImage == spriteLoader.get(3)){   //if still after move right, choose right-face
                currentImage = spriteLoader.get(0);
            } else{ //if still after move left, choose left face
                currentImage = spriteLoader.get(0);     //TODO temporary directional sprite
            }

        }
    }

    //Sets direction of Mario sprite
    public void setDirection(int value){
        dir = value;
    }


    @Override
    public void tick(Canvas c) {
        //Jumping
        if (touchFlag && dy == 0) {
            y1 -= 40;
        }
        else {
            y1 += dy;
            dy += gravity;
        }

        //Keep Mario on screen
        if (y1 > screenHeight - marioHeight) {     //Bottom bound
            y1 = screenHeight - marioHeight;
            dy = 0;
        }

        setLocation(x1, y1);
        draw(c);
    }

    public void checkEnemyCollision(ArrayList<Sprite> enemies) {

        //TODO determine action for different collisions
        for (Sprite s : enemies) {
            if (right.intersect(s.getLeft())) {
                die();
                break;
            } else if (left.intersect(s.getRight())) {
                die();
                break;
            } else if (top.intersect(s.getBot())) {
                die();
                break;
            } else if (bot.intersect(s.getTop())) {
                s.die();
                break;
            }
        }
    }

    public void checkPlatformIntersect(ArrayList<Obstacle> obs){
        //TODO
        for(Obstacle a: obs){
            if(bot.intersect(a.getTop())){
                ground=true;
                dy = 0;
                y1-=.10f;						//touch ground (top of object), counter against gravity
                break;
            } else if(!bot.intersect(a.getTop())){
                ground = false;
                //if none of objects' top touch mario bottom, no ground
                //return ground;
            } if(top.intersect(a.getBot())){			//if mario top touch object bottom (ceiling), no ground and stop moving in that direction, offset outside rectangle

                ground=false;
                dy = 0;

                this.y1+=.1f;
            }
        }
    }

    public void die(){
        visible = false;
    }

    protected void draw(Canvas c) {
        if(visible) {
            Paint paint = new Paint();
            c.drawBitmap(currentImage, null, dst, paint);
        }
    }
}
