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
        for(Item i : itemList){
            i.revive();
        }
        for(Obstacle o : scene){
            o.revive();
            if (o.hasItem()){
                o.getItem().setVisible(false);
            }
        }
        for(Sprite e : enemies){
            e.revive();
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
                o.setBackgroundDx(-18f);
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
                s.setBackgroundDx(-18f);
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
                i.setBackgroundDx(-18f);
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
                f.setBackgroundDx(-20f);
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

        Bitmap block1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.block1, options);          //0
        Bitmap block2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.block2, options);          //1
        Bitmap brick1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.brick1, options);          //2
        Bitmap tile1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.tile1, options);            //3
        Bitmap mushroom1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.mushroom1, options);    //4
        Bitmap mushroom2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.mushroom2, options);    //5
        Bitmap flower = BitmapFactory.decodeResource(view.getResources(), R.drawable.flower1, options);         //6
        Bitmap coin = BitmapFactory.decodeResource(view.getResources(), R.drawable.coin1, options);             //7
        Bitmap pipe1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.pipe1, options);            //8
        Bitmap pipe2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.pipe2, options);            //9
        Bitmap pipe3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.pipe3, options);            //10
        Bitmap flag = BitmapFactory.decodeResource(view.getResources(), R.drawable.flag, options);              //11


        imageLoader.add(block1);
        imageLoader.add(block2);
        imageLoader.add(brick1);
        imageLoader.add(tile1);
        imageLoader.add(mushroom1);
        imageLoader.add(mushroom2);
        imageLoader.add(flower);
        imageLoader.add(coin);
        imageLoader.add(pipe1);
        imageLoader.add(pipe2);
        imageLoader.add(pipe3);
        imageLoader.add(pipe3);
        imageLoader.add(flag);
    }

    public abstract void addElements(MarioSurfaceView view);

    public void addLine(int x, int y, int length, Bitmap b){
        for(int i = 0; i<length; i++){
            scene.add(new Obstacle(x+b.getWidth()*i, y, b));
        }
    }

    public void addStairsR(int x, int y, int length, Bitmap b){
        for(int i = 0; i<length; i++){
            for(int j = i; j<length ; j++){
                scene.add(new Obstacle(x+tileWidth*j, y-tileWidth*i, b));
            }
        }

    }

    public void addStairsL(int x, int y, int length, Bitmap b){
        for(int i = 0; i<length; i++){
            for(int j = i; j<length ; j++){
                scene.add(new Obstacle(x-tileWidth*j, y-tileWidth*i, b));
            }
        }

    }
}
