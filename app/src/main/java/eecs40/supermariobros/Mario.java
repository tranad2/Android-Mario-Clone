package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Mario implements TimeConscious {

    private static final String TAG = "Mario";
    private ArrayList<Bitmap> spriteLoader;
    private ArrayList<Fireball> fireballs;
    private ArrayList<Obstacle> scene;
    private ArrayList<Item> items;
    private Bitmap currentImage;
    private boolean visible = true, ground = true, jumpFlag, fireballFlag, moveLeftFlag, moveRightFlag;
    private int x1, y1, x2, y2, marioWidth, marioHeight, screenHeight, screenWidth;
    private int dir = 1, timer = 0, fireballDelay;
    private int form = 0; //0 small, 1 big, 2 fire
    private float dx = 0, dy;
    private static final float gravity = 1.4f;
    private MarioSurfaceView view;
    private Rect dst, top, bot, left, right;

    public Mario(World w1, MarioSurfaceView view) {
        this.view = view;

        //Load default bitmap
        scene = w1.getObstacles();
        items = w1.getItems();

        //Load Mario bitmaps
        loadImages(view);
        currentImage=spriteLoader.get(0);


        //Mario dimensions
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
        top = new Rect(x1+marioWidth/6, y1, x2-marioWidth/6 , y1+marioHeight/4);
        bot = new Rect(x1+marioWidth/6, y2-marioHeight/4, x2-marioWidth/6, y2);

        left = new Rect(x1, y1+marioHeight/4, x1+marioWidth/2, y1+marioHeight/4);
        right = new Rect(x2-marioWidth/2, y2-marioHeight/4, x2, y2-marioHeight/4);

        //Initialize fireballs
        fireballs = new ArrayList<>();

        screenHeight = view.getHeight();
        screenWidth = view.getWidth();
        jumpFlag = false;
        setForm(0);
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

    public int getX2() { return x2; }

    public float getDx() { return dx; }

    public boolean moveRightFlag() { return moveRightFlag ;}

    public boolean moveLeftFlag() { return moveLeftFlag ;}

    public int getDir() { return dir; }

    //True if A button is pressed
    public void setJumpFlag(boolean flag) {
        jumpFlag = flag;
    }

    //True if B button is pressed
    public void setFireballFlag(boolean flag) {
        fireballFlag = flag;
    }

    //True if left or right button is pressed
    public void setMoveLeftFlag(boolean flag) {
        moveLeftFlag = flag;
    }
    public void setMoveRightFlag(boolean flag) { moveRightFlag = flag; }

    //Sets direction of Mario sprite
    public void setDirection(int value){
        dir = value;
    }

    //Set moving speed
    public void setDx(float value){
        dx = value;
    }

    //set form (small, big, fire)
    public void setForm(int value) {
        form = value;
        if (form > 0) {
            marioHeight = currentImage.getHeight();
        }
    }

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    //Loads scaled images into array
    public void loadImages(MarioSurfaceView view){
        spriteLoader = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap smallRedMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario1, options);

        Bitmap smallRedMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario2, options);

        Bitmap smallRedMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario3, options);

        Bitmap smallRedMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario4, options);

        Bitmap smallRedMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario5, options);

        Bitmap bigRedMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario1, options);

        Bitmap bigRedMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario2, options);

        Bitmap bigRedMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario3, options);

        Bitmap bigRedMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario4, options);

        Bitmap bigRedMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario5, options);

        Bitmap fireMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario1, options);

        Bitmap fireMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario2, options);

        Bitmap fireMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario3, options);

        Bitmap fireMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario4, options);

        Bitmap fireMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario5, options);

        spriteLoader.add(smallRedMario1);
        spriteLoader.add(smallRedMario2);
        spriteLoader.add(smallRedMario3);
        spriteLoader.add(smallRedMario4);
        spriteLoader.add(smallRedMario5);
        spriteLoader.add(bigRedMario1);
        spriteLoader.add(bigRedMario2);
        spriteLoader.add(bigRedMario3);
        spriteLoader.add(bigRedMario4);
        spriteLoader.add(bigRedMario5);
        spriteLoader.add(fireMario1);
        spriteLoader.add(fireMario2);
        spriteLoader.add(fireMario3);
        spriteLoader.add(fireMario4);
        spriteLoader.add(fireMario5);
    }

    public Bitmap flipImage(Bitmap src) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    //Animates through Mario sprite frames
    public void doAnim(){
        //TODO
        //Run through animation

        //Jumping
        if (!ground) {
            if(dir == 1) {
                if (form == 0) {
                    currentImage = spriteLoader.get(4);     //Jumping right
                } else if (form == 1) {
                    currentImage = spriteLoader.get(9);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(14);
                }
            } else if (dir == -1){
                if (form == 0) {
                    currentImage = spriteLoader.get(4);     //Jumping left
                } else if (form == 1) {
                    currentImage = spriteLoader.get(9);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(14);
                }
                currentImage = flipImage(currentImage);
            }
        }

        //Moving
        else if(dir == 1 && ground && (moveLeftFlag||moveRightFlag)) {   //Moving right
            if(timer <= 5){
                if (form == 0) {
                    currentImage = spriteLoader.get(1);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(6);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(11);
                }
                timer++;
            } else if(timer <= 10){
                if (form == 0) {
                    currentImage = spriteLoader.get(2);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(7);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(12);
                }
                timer++;
            } else if(timer <= 15){
                if (form == 0) {
                    currentImage = spriteLoader.get(3);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(8);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(13);
                }
                timer++;
            } else if(timer <= 20){
                if (form == 0) {
                    currentImage = spriteLoader.get(2);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(7);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(12);
                }
                timer++;
            } else if(timer <= 30) {
                timer = 0;
            }
        } else if(dir == -1 && ground && (moveLeftFlag||moveRightFlag)){   //Left direction
            if(timer <= 5){
                if (form == 0) {
                    currentImage = spriteLoader.get(1);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(6);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(11);
                }
                currentImage = flipImage(currentImage);
                timer++;
            } else if(timer <= 10){
                if (form == 0) {
                    currentImage = spriteLoader.get(2);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(7);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(12);
                }
                currentImage = flipImage(currentImage);
                timer++;
            } else if(timer <= 15){
                if (form == 0) {
                    currentImage = spriteLoader.get(3);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(8);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(13);
                }
                currentImage = flipImage(currentImage);
                timer++;
            } else if(timer <= 20){
                if (form == 0) {
                    currentImage = spriteLoader.get(2);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(7);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(12);
                }
                currentImage = flipImage(currentImage);
                timer++;
            } else if(timer <= 30) {
                timer = 0;
            }
        }

        //Standing still
        else{
            if(currentImage == spriteLoader.get(0) || currentImage == spriteLoader.get(1) || currentImage == spriteLoader.get(2) || currentImage == spriteLoader.get(3) || currentImage == spriteLoader.get(4) || dir == 1){   //if still after move right, choose right-face
                if (form == 0) {
                    currentImage = spriteLoader.get(0);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(5);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(10);
                }
            } else{ //if still after move left, choose left face
                if (form == 0) {
                    currentImage = spriteLoader.get(0);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(5);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(10);
                }
                currentImage = flipImage(currentImage);
            }

        }
    }

    @Override
    public void tick(Canvas c) {
        //Log.v(TAG, ""+x1+" "+y1+ " Ground:"+ground+" Visible:"+visible+" DY:"+dy+" Scene"+scene.size());
        checkItem();
        if (dy >= 0) {
            checkPlatformIntersect();
        }
        checkSideIntersect();
        //Horizontal moving bounds
        if (x1 <= 0 && dir== -1) {
            dx = 0;
        }
        else if (x2 >= screenWidth / 2 && dir == 1) {
            dx = 0;
        }

        //Mario falls into pit
        if (y1 >= screenHeight - marioHeight && !jumpFlag) {     //Bottom bound
            //mario dies
        }

        //Jumping
        if (jumpFlag && dy == 0) {
            dy = -35;
            ground = false;
        }
        else if(ground){
            dy = 0;
        }
        else if(!ground){
            y1 += dy;
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

        //Shooting fireball
        fireballDelay++;
        if (fireballFlag && form == 2 &&  fireballDelay > 15) {
            fireballDelay = 0;
            if (dir == 1) {
                fireballs.add(new Fireball(view, x2, y2 - marioHeight / 2, dir));
            } else {
                fireballs.add(new Fireball(view, x1, y2 - marioHeight / 2, dir));
            }

        }

        x1+=dx;
        setLocation(x1, y1);
        doAnim();
        draw(c);

        //Draw fireballs
        for(Fireball f : fireballs){
            f.tick(c);
        }
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
            } else if (bot.intersect(s.getTop())) {     //Stomp enemy, enemy dies
                s.die();
                break;
            }
        }
    }

    public void checkPlatformIntersect(){
        //TODO
        Log.v(TAG,"Ground: "+ground);
        for(Obstacle o : scene) {
            if(bot.intersect(o.getTop())){//Top intersect
                Log.v(TAG,"TOP");
                ground=true;
                dy = 0;
                float d = Math.abs(y1+marioHeight-o.getTop().top);  //distance of intersection sides
                y1-=d-2;						//touch ground (top of object), counter against gravity
                break;
            }
            else if(!bot.intersect(o.getTop())){//Free fall
                ground = false;
                //if none of objects' top touch mario bottom, no grou
                //return ground;
                //Log.v(TAG,"FREE FALL");
            }

            if(top.intersect(o.getBot())){//Bot intersect			//if mario top touch object bottom (ceiling), no ground and stop moving in that direction, offset outside rectangle
                Log.v(TAG,"SUCCESS");
                dy = 0;
                float d = Math.abs(y1-o.getTop().bottom);  //distance of intersection sides
                this.y1+=d;
                ground = false;
                break;
            }
        }
    }

    public void checkSideIntersect(){
        //boolean sidecollision = false;
        for(Obstacle o: scene){

            if(o.getLeft().intersect(right)){
                if(dx>0){
                    dx=0;
                    float d = Math.abs(x1+marioWidth-o.getLeft().left);
                    this.x1-= d - 1;
                }
            } if (o.getRight().intersect(left)){
                if(dx<0) {
                    dx = 0;
                    float d = Math.abs(x1-o.getRight().right);
                    this.x1 += d - 1;
                }
            }

        }
    }

    public void checkItem(){
        for(int i = 0; i<items.size(); i++){
            Item item = items.get(i);
            if(dst.intersect(item.getRect())){
                if(item.type == 0){//Mushroom

                }else if(item.type == 1){//Fire Flower

                }else{

                }

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
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            c.drawRect(top, paint);
            //c.drawRect(bot,paint);
            //c.drawRect(left,paint);
            //c.drawRect(right,paint);
        }
    }
}