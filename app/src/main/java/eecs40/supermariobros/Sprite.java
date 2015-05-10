package eecs40.supermariobros;

import android.graphics.Rect;

/**
 * Created by Alex on 5/9/2015.
 */
public class Sprite{

    protected float x, y;
    protected Rect dst, top, bot, left, right;

    public Sprite(float x, float y){
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

    public Rect getLeft(){
        return left;
    }
}