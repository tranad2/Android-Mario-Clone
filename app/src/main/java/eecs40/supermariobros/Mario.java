package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;

public class Mario implements TimeConscious {

    private ArrayList<Bitmap> spriteLoader;
    //private ArrayList<Obstacle> scene;
    private Bitmap smallRedMario;
    private Bitmap bmp;
    private boolean visible = true, ground, touchFlag;
    private int x1, y1, x2, y2, marioWidth, marioHeight, screenHeight;
    private int dir = 0, timer = 0;
    private float dy;
    private float gravity = 5;
    private Rect dst;

    public Mario(/*ArrayList<Obstacle> scene,*/ MarioSurfaceView view) {
        //Load default bitmap
        //this.scene = scene;

        //Load Mario bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        smallRedMario = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallredmario1, options);

        //Scale Mario bitmap
        marioWidth = smallRedMario.getWidth() * 2;
        marioHeight = smallRedMario.getHeight() * 2;
        smallRedMario = Bitmap.createScaledBitmap(smallRedMario, marioWidth, marioHeight, true);

        //Initialize Mario position
        this.x1 = view.getWidth() / 4;
        this.y1 = view.getHeight() / 2;
        this.x2 = this.x1 + marioWidth;
        this.y2 = this.y1 + marioHeight;
        dst = new Rect(x1, y1, x2, y2);

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

    public void loadImages(){
        //TODO add image frames to array

    }

    public void doAnim(int direction){
        //TODO
        //Run through animation
        /*if(direction == 1){   //Right direction

        } else if( direction == -1){   //Left direction

        } else{     //Standing still
            if(){   //if still after move right, choose right-face

            } else{ //if still after move left, choose left face

            }

        }*/
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

    /*public boolean checkCollision(){

        //TODO determine if list contains enemies or only interactable
        boolean collide = false;
        for(Obstacle o : scene){
            if(dst.intersect(o.getRect())){
                collide = true;
            }
        }
        return collide;
    }
    */
    /*
    public void checkPlatformIntersect(ArrayList<Obstacle> obs){
        //TODO
        for(Obstacle a: obs){
            if(bot.intersect(a.getTop())){
                ground=true;
                dy = 0;
                this.y-=.10f;						//touch ground (top of object), counter against gravity
                break;
            } else if(!bot.intersect(a.getTop())){
                ground = false;
                //if none of objects' top touch mario bottom, no ground
                //return ground;
            } if(top.intersect(a.getBot())){			//if mario top touch object bottom (ceiling), no ground and stop moving in that direction, offset outside rectangle

                ground=false;
                dy = 0;

                this.y+=.1f;
            }
        }
    }
    */

    protected void draw(Canvas c) {
        Paint paint = new Paint();
        c.drawBitmap(smallRedMario, null, dst, paint);
    }
}
