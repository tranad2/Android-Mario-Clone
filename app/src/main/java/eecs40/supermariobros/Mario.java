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
    private float dx = 0, dy;
    private float gravity = 3;
    private Rect dst, top, bot, left, right;

    public Mario(/*ArrayList<Obstacle> scene,*/ MarioSurfaceView view) {
        //Load default bitmap
        //this.scene = scene;

        //Load Mario bitmaps
        loadImages(view);
        currentImage=spriteLoader.get(0);


        //Scale Mario bitmap
        marioWidth = currentImage.getWidth();
        marioHeight = currentImage.getHeight();
        currentImage = Bitmap.createScaledBitmap(currentImage, marioWidth, marioHeight, true);


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

    public void setDx(float value){
        dx = value;
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

        Bitmap smallRedMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario2, options);

        Bitmap smallRedMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario3, options);

        Bitmap smallRedMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario4, options);

        Bitmap smallRedMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario5, options);

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
            if(timer <= 1){
                currentImage = spriteLoader.get(1);
                timer++;
            } else if(timer <= 2){
                currentImage = spriteLoader.get(2);
                timer++;
            } else if(timer <= 3){
                currentImage = spriteLoader.get(3);
                timer++;
            } else if(timer <= 4){
                currentImage = spriteLoader.get(2);
                timer = 0;
            }
        } else if( dir == -1){   //Left direction
            if(timer <= 1){
                currentImage = spriteLoader.get(1);
                timer++;
            } else if(timer <= 2){
                currentImage = spriteLoader.get(2);
                timer++;
            } else if(timer <= 3){
                currentImage = spriteLoader.get(3);
                timer++;
            } else if(timer <= 4){
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
            y1 -= 25;
        }
        else {
            y1 += dy*.25;
            dy += gravity;
        }

        //Keep Mario on screen
        if (y1 > screenHeight - marioHeight) {     //Bottom bound
            y1 = screenHeight - marioHeight;
            dy = 0;
        }
        x1+=dx;
        setLocation(x1, y1);
        doAnim();
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
