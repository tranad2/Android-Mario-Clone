package eecs40.supermariobros;

import android.graphics.Rect;

public class Sprite{

    protected float x, y;
    protected Rect dst, top, bot, left, right;
    protected boolean visible = true;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
    }

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
        visible = false;
    }

    public void setVisible(boolean vis){
        visible = vis;
    }

    public boolean isVisible(){
        return visible;
    }
}