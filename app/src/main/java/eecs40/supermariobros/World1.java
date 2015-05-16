package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 5/10/2015.
 */
public class World1 extends World{

    private static final String TAG = "World1";
    private int screenWidth, screenHeight;
    private ArrayList<Obstacle> scene;
    private ArrayList<Item> itemList;
    private ArrayList<Sprite> enemies;
    private Mario mario;
    private boolean end;

    public World1(MarioSurfaceView view){
        super(view);
        screenWidth = view.getWidth();
        screenHeight = view.getHeight();

        scene = new ArrayList<>();
        enemies = new ArrayList<>();
        loadImages(view);

        float tileWidth = imageLoader.get(0).getWidth();
        int offset = 0;

        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<50; j++) {
                scene.add(new Obstacle(j*imageLoader.get(3).getWidth(),screenHeight-imageLoader.get(3).getHeight()*i , imageLoader.get(3)));
            }
        }
        scene.add(new Obstacle(imageLoader.get(0).getWidth()*10,screenHeight-imageLoader.get(0).getHeight()*3, imageLoader.get(0)));
        enemies.add(new Goomba(2*screenWidth/3,screenHeight/2,view, scene));

        offset = (int)((scene.get(scene.size()-1)).getX()+tileWidth);  //New x after last obstacle

        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<10; j++) {
                scene.add(new Obstacle(j*imageLoader.get(3).getWidth()+2*offset,screenHeight-imageLoader.get(3).getHeight()*i , imageLoader.get(3)));
            }
        }


        Log.v(TAG, "" + scene.isEmpty());
    }

    public void tick(Canvas c){
        boolean backgroundMove = false;
        if (mario.moveRightFlag() && !mario.moveLeftFlag() && mario.getX2() >= screenWidth / 2  && mario.getDx() >= 0) {
            backgroundMove = true;
        }
        for(Obstacle o : scene){
            if (backgroundMove) {
                o.setDx(-15f);
            }
            else if ( mario.getDx()== 0 ){
                o.setDx(0f);
                backgroundMove = false;
            }
            else {
                o.setDx(0f);
                backgroundMove = false;
            }
            o.tick(c);
        }
        for(Sprite s : enemies){
            if(s instanceof Goomba) {
                if (backgroundMove) {
                    ((Goomba)(s)).setBackgroundDx(-15f);
                } else if (mario.getDx() == 0) {
                    ((Goomba)(s)).setBackgroundDx(0f);
                    backgroundMove = false;
                } else {
                    ((Goomba)(s)).setBackgroundDx(0f);
                    backgroundMove = false;
                }
                ((Goomba) (s)).tick(c);
            }
        }
    }

    protected void draw(Canvas c){
        for(Obstacle o : scene){
            o.draw(c);
        }
        for(Sprite s : enemies){
            if(s instanceof Goomba)
                ((Goomba)s).tick(c);
        }
    }

    public ArrayList<Obstacle> getObstacles(){
        return scene;
    }

    public boolean end(){
        return end;
    }

    public void start(Canvas c){
        end = true;
        tick(c);
    }

    public void loadImages(MarioSurfaceView view){
        imageLoader = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap block1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.block1, options);
        Bitmap block2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.block2, options);
        Bitmap brick1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.brick1, options);
        Bitmap tile1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.tile1, options);

        imageLoader.add(block1);
        imageLoader.add(block2);
        imageLoader.add(brick1);
        imageLoader.add(tile1);
    }

    public void setMario(Mario mario){ this.mario = mario; }

}
