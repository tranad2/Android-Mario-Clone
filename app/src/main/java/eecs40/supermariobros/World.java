package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Alex on 5/15/2015.
 */
public abstract class World implements TimeConscious{


    protected ArrayList<Obstacle> scene;
    protected ArrayList<Item> itemList;
    protected ArrayList<Sprite> enemies;
    protected int screenWidth, screenHeight;
    protected Mario mario;


    public World(MarioSurfaceView view){
        screenWidth = view.getWidth();
        screenHeight = view.getHeight();

        scene = new ArrayList<>();
        enemies = new ArrayList<>();
        itemList = new ArrayList<>();
    }

    public abstract void tick(Canvas c);

    public ArrayList<Obstacle> getObstacles(){
        return scene;
    }

    public ArrayList<Item> getItems(){
        return itemList;
    }

    public ArrayList<Sprite> getEnemies(){
        return enemies;
    }

    public void clean(){
        for(int i = 0; i<itemList.size(); i++){
            Item item = itemList.get(i);
            if(item.isDead()){
                itemList.remove(i);
                i--;
            }
        }
        for(int j = 0; j<enemies.size(); j++){
            Sprite e = enemies.get(j);
            if(e.isDead()){
                enemies.remove(j);
                j--;
            }
        }

    }

    public void setMario(Mario mario){ this.mario = mario; }

    protected abstract void draw(Canvas c);
}
