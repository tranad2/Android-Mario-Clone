package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Mario implements TimeConscious {

    private static final String TAG = "Mario";
    private ArrayList<Bitmap> spriteLoader;
    private ArrayList<Obstacle> scene;
    private Bitmap currentImage;
    private boolean visible = true, ground, touchFlag;
    private int x1, y1, x2, y2, marioWidth, marioHeight, screenHeight;
    private int dir = 0, timer = 0;
    private float dx = 0, dy;
    private float gravity = 3;
    private Rect dst, top, bot, left, right;

    public Mario(ArrayList<Obstacle> scene, MarioSurfaceView view) {
        //Load default bitmap
        this.scene = scene;

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
        top.set(x1,y1,x2,y1+marioHeight/4);
        bot.set(x1,y2-marioHeight/4,x2,y2);
        left.set(x1,y1,x1+marioWidth/2,y2);
        right.set(x2-marioWidth/2,y1,x2,y2);
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
        if (!ground) {
            if(dir == 1) {
                currentImage = spriteLoader.get(4);     //Jumping right
            } else {
                currentImage = spriteLoader.get(4);     //Jumping left
            }
        }
        else if(dir == 1) {   //Right direction
            if(timer <= 10){
                currentImage = spriteLoader.get(1);
                timer++;
            } else if(timer <= 20){
                currentImage = spriteLoader.get(2);
                timer++;
            } else if(timer <= 30){
                currentImage = spriteLoader.get(3);
                timer++;
            } else if(timer <= 40){
                currentImage = spriteLoader.get(2);
                timer = 0;
            }
        } else if(dir == -1){   //Left direction
            if(timer <= 10){
                currentImage = spriteLoader.get(1);
                timer++;
            } else if(timer <= 20){
                currentImage = spriteLoader.get(2);
                timer++;
            } else if(timer <= 30){
                currentImage = spriteLoader.get(3);
                timer++;
            } else if(timer <= 40){
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
        Log.v(TAG, ""+x1+" "+y1+ " Ground:"+ground+" Visible:"+visible+" DY:"+dy+" Scene"+scene.size());
        checkPlatformIntersect();
        //Keep Mario on screen
        if (y1 > screenHeight - marioHeight && !touchFlag) {     //Bottom bound
            y1 = screenHeight - marioHeight;
            dy = 0;
            ground = true;
        }
        //Jumping
        else if (touchFlag && dy == 0) {
            y1 -= 25;
            ground = false;
        }
        else if(ground){
            dy = 0;
        }
        else if(!ground){
            y1 += dy*.25;
            dy += gravity;
        }

        x1+=dx;

        Log.v(TAG,"Mario");
        setLocation(x1, y1);
        doAnim();
        Log.v(TAG,"DRAW");
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

    public void checkPlatformIntersect(){
        //TODO
        //Log.v(TAG,"Enter"+scene.size());
        for(Obstacle o : scene) {
            if(bot.intersect(o.getTop())){
                Log.v(TAG,"Intersect");
                ground=true;
                dy = 0;
                float d = Math.abs(y1+marioHeight-o.getTop().top);  //distance of intersection sides
                y1-=d;						//touch ground (top of object), counter against gravity
                break;
            } else if(!bot.intersect(o.getTop())){
                //ground = false;
                //if none of objects' top touch mario bottom, no grou
                //return ground;
            } else if(top.intersect(o.getBot())){			//if mario top touch object bottom (ceiling), no ground and stop moving in that direction, offset outside rectangle

                //ground=false;
                //dy = 0;

                //this.y1+=.1f;
            }
        }
        Log.v(TAG,"Check done");
    }

    public void die(){
        visible = false;
    }

    protected void draw(Canvas c) {
        if(visible) {
            Paint paint = new Paint();
            c.drawBitmap(currentImage, null, dst, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            c.drawRect(top, paint);
            c.drawRect(bot,paint);
            c.drawRect(left,paint);
            c.drawRect(right,paint);
        }
    }
}
