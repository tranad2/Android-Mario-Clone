package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 5/10/2015.
 */
public class World2 extends World{

    private static final String TAG = "World1";
    private ArrayList<Bitmap> imageLoader;

    public World2(MarioSurfaceView view){
        super(view);
        screenWidth = view.getWidth();
        screenHeight = view.getHeight();


        loadImages(view);

        float tileWidth = imageLoader.get(0).getWidth();
        int offset = 0;

        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<50; j++) {
                scene.add(new Obstacle(j*imageLoader.get(3).getWidth(),screenHeight-imageLoader.get(3).getHeight()*i , imageLoader.get(1)));
            }
        }
        scene.add(new Obstacle(imageLoader.get(0).getWidth()*10,screenHeight-imageLoader.get(0).getHeight()*3, imageLoader.get(0)));
        enemies.add(new Goomba(2*screenWidth/3,screenHeight/2,view, scene));
        itemList.add(new Item(imageLoader.get(0).getWidth()*10,screenHeight-imageLoader.get(0).getHeight()*4, 0, view, scene));
        scene.add(new Obstacle(imageLoader.get(0).getWidth()*6,screenHeight-imageLoader.get(0).getHeight()*7, imageLoader.get(0)));

        offset = (int)((scene.get(scene.size()-1)).getX()+tileWidth);  //New x after last obstacle

        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<10; j++) {
                scene.add(new Obstacle(j*imageLoader.get(3).getWidth()+2*offset,screenHeight-imageLoader.get(3).getHeight()*i , imageLoader.get(3)));
            }
        }


        Log.v(TAG, "" + scene.isEmpty());
    }

    public void tick(Canvas c){
        clean();
        boolean backgroundMove = false;
        if (mario.moveRightFlag() && !mario.moveLeftFlag() && mario.getX2() >= screenWidth / 2  && mario.getDx() >= 0) {
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



}
