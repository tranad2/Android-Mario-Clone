package eecs40.supermariobros;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Sprite{

    protected int x, y;
    protected float bgdx, dx=0, dy=0;
    protected Rect dst, top, bot, left, right;
    protected boolean visible = true;
    protected boolean dead = false;
    protected int initX, initY;
    protected float initDx, initDy;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        initX = x;
        initY = y;
        initDx = dx;
        initDy = dy;
    }

    //set background speed
    public void setBackgroundDx(float bgdx) { this.bgdx = bgdx; }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){this.x = x;}

    public void setY(int y){this.y= y;}

    public Rect getRect(){
        return dst;
    }

    public Rect getTop(){
        return top;
    }

    public Rect getBot(){
        return bot;
    }

    public Rect getRight(){
        return right;
    }

    public Rect getLeft(){ return left; }

    //sprite is dead
    public void die(){
        dead = true;
    }

    //@param val set death to val
    public void setDead(boolean val){
        dead = val;
    }

    //return sprite to initial living state
    public void revive(){
        dead = false;
        visible = true;
        x = initX;
        y = initY;
    }

    //@return dead
    public boolean isDead(){
        return dead;
    }

    //@return background speed
    public float getBgdx(){
        return bgdx;
    }

    //@param set visibility
    public void setVisible(boolean vis){
        visible = vis;
    }

    //@return visible
    public boolean isVisible(){
        return visible;
    }

    public abstract void tick(Canvas c);

    protected abstract void draw(Canvas c);


}