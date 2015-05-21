package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 5/15/2015.
 */
public abstract class World implements TimeConscious{


    protected ArrayList<Obstacle> scene;
    protected ArrayList<Item> itemList;
    protected ArrayList<Sprite> enemies;
    protected ArrayList<Bitmap> imageLoader;
    protected int screenWidth, screenHeight, tileWidth;
    protected Mario mario;
    protected boolean end;


    public World(MarioSurfaceView view){
        screenWidth = view.getWidth();
        screenHeight = view.getHeight();


        loadImages(view);

        tileWidth = imageLoader.get(0).getWidth();

        scene = new ArrayList<>();
        enemies = new ArrayList<>();
        itemList = new ArrayList<>();
    }

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
                Log.v("World", "Dead");
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

    public boolean end(){
        return end;
    }

    public void start(Canvas c){
        end = false;
        tick(c);
    }

    public void reset(){
        for(Obstacle o : scene){
            o.revive();
        }
        for(Sprite e : enemies){
            e.revive();
        }
        for(Item i : itemList){
            i.revive();
        }
    }

    public void setMario(Mario mario){ this.mario = mario; }

    public void tick(Canvas c){

        boolean backgroundMove = false;

        if (mario.getMoveRightFlag() && !mario.getMoveLeftFlag() && mario.getX2() >= screenWidth / 2  && mario.getDx() >= 0 && !mario.isDead()) {
            backgroundMove = true;
        }
        for(Obstacle o : scene){
            if (backgroundMove) {
                o.setBackgroundDx(-15f);
            }
            else if ( mario.getDx()== 0 ){
                o.setBackgroundDx(0f);
                backgroundMove = false;
            }
            else {
                o.setBackgroundDx(0f);
                backgroundMove = false;
            }
            o.tick(c);
        }
        for(Sprite s : enemies){
            if (backgroundMove) {
                s.setBackgroundDx(-15f);
            } else if (mario.getDx() == 0) {
                s.setBackgroundDx(0f);
                backgroundMove = false;
            } else {
                s.setBackgroundDx(0f);
                backgroundMove = false;
            }
            s.tick(c);
        }

        for(Item i : itemList){
            if (backgroundMove) {
                i.setBackgroundDx(-15f);
            } else if (mario.getDx() == 0) {
                i.setBackgroundDx(0f);
                backgroundMove = false;
            } else {
                i.setBackgroundDx(0f);
                backgroundMove = false;
            }
            i.tick(c);
        }

        for(Fireball f : mario.getFireballs()) {
            if (backgroundMove) {
                f.setBackgroundDx(-15f);
            } else if (mario.getDx() == 0) {
                f.setBackgroundDx(0f);
                backgroundMove = false;
            } else {
                f.setBackgroundDx(0f);
                backgroundMove = false;
            }
        }
    }

    protected void draw(Canvas c){
        for(Obstacle o : scene){
            o.draw(c);
        }
        for(Sprite s : enemies){
            s.draw(c);
        }
        for(Item i : itemList){
            i.draw(c);
        }
    }

    public void loadImages(MarioSurfaceView view){
        imageLoader = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap block1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.block1, options);
        Bitmap block2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.block2, options);
        Bitmap brick1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.brick1, options);
        Bitmap tile1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.tile1, options);
        Bitmap mushroom1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.mushroom1, options);

        imageLoader.add(block1);
        imageLoader.add(block2);
        imageLoader.add(brick1);
        imageLoader.add(tile1);
        imageLoader.add(mushroom1);
    }

    public abstract void addElements(MarioSurfaceView view);
}
