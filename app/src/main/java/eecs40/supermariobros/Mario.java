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

public class Mario extends Sprite implements TimeConscious {

    private static final String TAG = "Mario";
    private ArrayList<Bitmap> spriteLoader;
    private ArrayList<Fireball> fireballs;
    private ArrayList<Obstacle> scene;
    private ArrayList<Item> items;
    private ArrayList<Sprite> enemies;
    private Bitmap currentImage, smallMario1, smallMario2, smallMario3, smallMario4, smallMario5, bigMario1, bigMario2, bigMario3, bigMario4, bigMario5, fireMario1, fireMario2, fireMario3, fireMario4, fireMario5, marioDeath1;
    private boolean ground = true, jumpFlag, fireballFlag, moveLeftFlag, moveRightFlag, deathTimer = false;
    private int marioWidth, marioHeight, screenHeight, screenWidth, x2, y2;
    private int dir = 1, timer = 0, fireballDelay;
    private int form = 0; //0 small, 1 big, 2 fire
    private float dx = 0, dy;
    private static final float gravity = 1.4f;
    private MarioSurfaceView view;


    public Mario(int x, int y, World w, MarioSurfaceView view) {
        super(x, y);
        this.view = view;

        //Load default bitmap
        scene = w.getObstacles();
        items = w.getItems();
        enemies = w.getEnemies();

        //Load Mario bitmaps
        loadImages(view);
        currentImage=spriteLoader.get(0);


        //Mario dimensions
        marioWidth = currentImage.getWidth();
        marioHeight = currentImage.getHeight();
        currentImage = Bitmap.createScaledBitmap(currentImage, marioWidth, marioHeight, true);


        //Initialize Mario position
        this.x = x;
        this.y = y;
        x2 = x + marioWidth;
        y2 = y + marioHeight;
        dst = new Rect(x, y, x2, y2);

        //Design hitboxes
        top = new Rect(x+marioWidth/6, y, x2-marioWidth/6 , y+marioHeight/4);
        bot = new Rect(x+marioWidth/6, y2-marioHeight/4, x2-marioWidth/6, y2);

        left = new Rect(x, y+marioHeight/4, x+marioWidth/2, y+marioHeight/4);
        right = new Rect(x2-marioWidth/2, y2-marioHeight/4, x2, y2-marioHeight/4);

        //Initialize fireballs
        fireballs = new ArrayList<>();

        screenHeight = view.getHeight();
        screenWidth = view.getWidth();
        jumpFlag = false;
        setForm(0);
    }

    public void setLocation(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        x2 = x + marioWidth;
        y2 = y + marioHeight;
        dst.set(x, y, x2, y2);

        top.set(x+marioWidth/6,y,x2-marioWidth/6,y+marioHeight/4);
        bot.set(x+marioWidth/6,y2-marioHeight/4,x2-marioWidth/6,y2);
        left.set(x,y+marioHeight/4,x+marioWidth/2,y2-marioHeight/4);
        right.set(x2-marioWidth/2,y+marioHeight/4,x2,y2-marioHeight/4);
    }

    public int getX2() { return x2; }

    public float getDx() { return dx; }

    public boolean getMoveRightFlag() { return moveRightFlag ;}

    public boolean getMoveLeftFlag() { return moveLeftFlag ;}

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

    //set form (small, big, fire)
    public void setForm(int value) {
        form = value;
        if (form > 0) {
            marioHeight = spriteLoader.get(5).getHeight();
        } else {
            marioHeight = spriteLoader.get(0).getHeight();
        }
    }

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    //Loads scaled images into array
    public void loadImages(MarioSurfaceView view){
        spriteLoader = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();

        if (view.color == 0) {
            smallMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario1, options);
            smallMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario2, options);
            smallMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario3, options);
            smallMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario4, options);
            smallMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario5, options);
            bigMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario1, options);
            bigMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario2, options);
            bigMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario3, options);
            bigMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario4, options);
            bigMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario5, options);
            fireMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario1, options);
            fireMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario2, options);
            fireMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario3, options);
            fireMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario4, options);
            fireMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemario5, options);
            marioDeath1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.mariodeath1, options);
        }
        else if (view.color == 1) {
            smallMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallgreenmario1, options);
            smallMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallgreenmario2, options);
            smallMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallgreenmario3, options);
            smallMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallgreenmario4, options);
            smallMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallgreenmario5, options);
            bigMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.biggreenmario1, options);
            bigMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.biggreenmario2, options);
            bigMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.biggreenmario3, options);
            bigMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.biggreenmario4, options);
            bigMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.biggreenmario5, options);
            fireMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenfiremario1, options);
            fireMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenfiremario2, options);
            fireMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenfiremario3, options);
            fireMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenfiremario4, options);
            fireMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenfiremario5, options);
            marioDeath1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.greenmariodeath1, options);
        }
        else if (view.color == 2) {
            smallMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallyellowmario1, options);
            smallMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallyellowmario2, options);
            smallMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallyellowmario3, options);
            smallMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallyellowmario4, options);
            smallMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallyellowmario5, options);
            bigMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigyellowmario1, options);
            bigMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigyellowmario2, options);
            bigMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigyellowmario3, options);
            bigMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigyellowmario4, options);
            bigMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigyellowmario5, options);
            fireMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowfiremario1, options);
            fireMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowfiremario2, options);
            fireMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowfiremario3, options);
            fireMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowfiremario4, options);
            fireMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowfiremario5, options);
            marioDeath1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.yellowmariodeath1, options);
        }
        else if (view.color == 3) {
            smallMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallpurplemario1, options);
            smallMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallpurplemario2, options);
            smallMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallpurplemario3, options);
            smallMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallpurplemario4, options);
            smallMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallpurplemario5, options);
            bigMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigpurplemario1, options);
            bigMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigpurplemario2, options);
            bigMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigpurplemario3, options);
            bigMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigpurplemario4, options);
            bigMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigpurplemario5, options);
            fireMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplefiremario1, options);
            fireMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplefiremario2, options);
            fireMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplefiremario3, options);
            fireMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplefiremario4, options);
            fireMario5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplefiremario5, options);
            marioDeath1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.purplemariodeath1, options);
        }

        spriteLoader.add(smallMario1);
        spriteLoader.add(smallMario2);
        spriteLoader.add(smallMario3);
        spriteLoader.add(smallMario4);
        spriteLoader.add(smallMario5);
        spriteLoader.add(bigMario1);
        spriteLoader.add(bigMario2);
        spriteLoader.add(bigMario3);
        spriteLoader.add(bigMario4);
        spriteLoader.add(bigMario5);
        spriteLoader.add(fireMario1);
        spriteLoader.add(fireMario2);
        spriteLoader.add(fireMario3);
        spriteLoader.add(fireMario4);
        spriteLoader.add(fireMario5);
        spriteLoader.add(marioDeath1);
    }

    public Bitmap flipImageH(Bitmap src) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    //Animates through Mario sprite frames
    public void doAnim(){
        //Run through animation
        if (dead) {
            currentImage = spriteLoader.get(15);
        }
        //Jumping
        else if (!ground) {
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
                currentImage = flipImageH(currentImage);
            }
        }

        //Moving
        else if(dir == 1 && (moveLeftFlag||moveRightFlag)) {   //Moving right
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
        } else if(dir == -1 && (moveLeftFlag||moveRightFlag)){   //Left direction
            if(timer <= 5){
                if (form == 0) {
                    currentImage = spriteLoader.get(1);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(6);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(11);
                }
                currentImage = flipImageH(currentImage);
                timer++;
            } else if(timer <= 10){
                if (form == 0) {
                    currentImage = spriteLoader.get(2);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(7);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(12);
                }
                currentImage = flipImageH(currentImage);
                timer++;
            } else if(timer <= 15){
                if (form == 0) {
                    currentImage = spriteLoader.get(3);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(8);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(13);
                }
                currentImage = flipImageH(currentImage);
                timer++;
            } else if(timer <= 20){
                if (form == 0) {
                    currentImage = spriteLoader.get(2);
                } else if (form == 1) {
                    currentImage = spriteLoader.get(7);
                } else if (form == 2) {
                    currentImage = spriteLoader.get(12);
                }
                currentImage = flipImageH(currentImage);
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
                currentImage = flipImageH(currentImage);
            }

        }
    }

    @Override
    public void tick(Canvas c) {
        Log.v("TAG", "Is visible: "+visible);
        checkEnemyCollision();
        //Mario falls into pit
        if (y >= screenHeight - marioHeight && !jumpFlag) {     //Bottom bound
            die();
        }
        if (isDead()) {
            if (Math.abs(dy) > 100) {
                if (dy > 0) {
                    dy = 100;
                } else {
                    dy = -100;
                }
            }
            if (y >= 3 * screenHeight) {
                visible = false;
            }
        }
        else {
            //Check collisions with matter
            checkItem();
            checkPlatformIntersect();
            checkSideIntersect();
            //Moving
            if (moveLeftFlag) {
                dx = -15f;
            } else if (moveRightFlag) {
                dx = 15f;
            } else {
                dx = 0;
            }

            //Horizontal moving bounds
            if (x <= 0 && dir == -1) {
                dx = 0;
            } else if (x2 >= screenWidth / 2 && dir == 1) {
                dx = 0;
            }

            //Jumping limit
            if (ground) {
                dy = 0;
            } else {
                if (Math.abs(dy) > 100) {
                    if (dy > 0) {
                        dy = 100;
                    } else {
                        dy = -100;
                    }
                }
            }
            //Jumping
            if (jumpFlag && ground) {
                dy = -35;
                ground = false;

            }

            //Shooting fireball
            fireballDelay++;
            if (fireballFlag && form == 2 && fireballDelay > 15) {
                fireballDelay = 0;
                if (dir == 1) {
                    fireballs.add(new Fireball(view, x2, y2 - marioHeight / 2, dir));
                } else {
                    fireballs.add(new Fireball(view, x, y2 - marioHeight / 2, dir));
                }

            }
            x += dx;
        }
        y += dy;
        dy += gravity;
        Log.v("TAG","dy = "+dy);
        setLocation(x, y);
        doAnim();
        draw(c);

        //Draw fireballs
        for(Fireball f : fireballs){
            f.tick(c);
        }
    }

    public void checkEnemyCollision() {
        for (Sprite s : enemies) {
            if (right.intersect(s.getLeft())) {
                if (form == 0) {
                    die();
                    dy = -35f;
                    visible = true;
                } else { setForm(--form); }
                break;
            } else if (left.intersect(s.getRight())) {
                if (form == 0) {
                    die();
                    dy = -35f;
                    visible = true;
                } else { setForm(--form); }
                break;
            } else if (top.intersect(s.getBot())) {
                if (form == 0) {
                    die();
                    dy = -35f;
                    visible = true;
                } else { setForm(--form); }
                break;
            } else if (bot.intersect(s.getTop())) {     //Stomp enemy, enemy dies
                dy=-15f;
                s.die();
                view.score+=1000;
                break;
            }
        }
    }

    public void checkPlatformIntersect(){

        for(Obstacle o : scene) {
            if(bot.intersect(o.getTop())){//Top intersect
                ground=true;
                dy = 0;
                Log.v("TAG","check platform: dy = "+dy);
                float d = Math.abs(y+marioHeight-o.getTop().top);  //distance of intersection sides use for offset
                y-=d-2;
                break;
            }
            else if(!bot.intersect(o.getTop())){//Free fall
                ground = false; //if none of objects' top touch mario bottom, no ground
            }

            if(top.intersect(o.getBot())){//Bot intersect
                //if mario top touch object bottom (ceiling), no ground and stop moving in that direction, offset outside rectangle
                dy = -dy;
                float d = Math.abs(y-o.getBot().bottom);  //distance of intersection sides
                this.y+=d;
                ground = false;
                break;
            }
        }
    }

    public void checkSideIntersect(){
        for(Obstacle o: scene){
                if(o.getLeft().intersect(right)){
                    dx=0;
                    float d = Math.abs(x+marioWidth-o.getLeft().left);
                    this.x -= d - 2;
                    break;
                }
                else if (o.getRight().intersect(left)){
                    dx = 0;
                    float d = Math.abs(x-o.getRight().right);
                    this.x += d - 2;
                    break;
                }
        }
    }

    //Item interaction and score increment
    public void checkItem(){
        for(int i = 0; i<items.size(); i++){
            Item item = items.get(i);
            if(dst.intersect(item.getRect())){
                if(item.type == 0){//Mushroom
                    setForm(2);
                    view.score += 1000;
                    item.die();
                }else if(item.type == 1){//Fire Flower
                    setForm(2);
                    view.score += 1000;
                    item.die();
                }else{//Coin
                    view.score+= 100;
                    item.die();
                }

            }
        }
    }

    protected void draw(Canvas c) {
        if(visible) {
            Paint paint = new Paint();
            c.drawBitmap(currentImage, null, dst, paint);
            /*
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            c.drawRect(top, paint);
            c.drawRect(bot,paint);
            paint.setColor(Color.CYAN);
            c.drawRect(left,paint);
            c.drawRect(right,paint);
            */
        }
        else {
            if (!deathTimer){
                view.lives--;
                deathTimer = true;
            }
            else {
                if (view.lives > 0) {
                    view.gameState = 4;
                } else {
                    view.gameState = 5;
                }
            }
        }
    }
}