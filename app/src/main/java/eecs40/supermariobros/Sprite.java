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

    public void die(){
        dead = true;
        //visible = false;
    }

    public void setDead(boolean val){
        dead = val;
    }

    public void revive(){
        dead = false;
        visible = true;
        x = initX;
        y = initY;
    }

    public boolean isDead(){
        return dead;
    }

    public float getBgdx(){
        return bgdx;
    }

    public void setVisible(boolean vis){
        visible = vis;
    }

    public boolean isVisible(){
        return visible;
    }

    public abstract void tick(Canvas c);

    protected abstract void draw(Canvas c);


}