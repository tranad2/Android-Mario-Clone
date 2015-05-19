package eecs40.supermariobros;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Sprite{

    protected int x, y;
    protected float bgdx;
    protected Rect dst, top, bot, left, right;
    protected boolean visible = true;
    protected boolean dead = false;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setBackgroundDx(float bgdx) { this.bgdx = bgdx; }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

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
        visible = false;
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