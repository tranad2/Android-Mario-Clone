package eecs40.supermariobros;

/**
 * Created by Alex on 5/18/2015.
 */
public class World3 extends World {

    private static final String TAG = "World3";

    public World3(MarioSurfaceView view) {
        super(view);
        addElements(view);
    }

    public void addElements(MarioSurfaceView view){

        addLine(tileWidth*0, screenHeight-tileWidth*1, 9, imageLoader.get(3));
        addLine(tileWidth*0, screenHeight-tileWidth*2, 9, imageLoader.get(3));
        scene.add(new Obstacle(tileWidth * 6, screenHeight - tileWidth * 6, imageLoader.get(0), new Item(tileWidth * 6, screenHeight - tileWidth * 7, 1, view), itemList));
        addLine(tileWidth * 13, screenHeight - tileWidth * 2, 1, imageLoader.get(8));
        enemies.add(new Goomba(tileWidth * 13, screenHeight - tileWidth * 3, view, scene));
        addLine(tileWidth * 19, screenHeight - tileWidth * 3, 1, imageLoader.get(9));
        enemies.add(new Goomba(tileWidth * 19, screenHeight - tileWidth * 4, view, scene));
        addLine(tileWidth * 25, screenHeight - tileWidth * 4, 1, imageLoader.get(10));
        enemies.add(new Goomba(tileWidth * 25, screenHeight - tileWidth * 5, view, scene));
        addLine(tileWidth * 31, screenHeight - tileWidth * 4, 1, imageLoader.get(10));
        enemies.add(new Goomba(tileWidth * 31, screenHeight - tileWidth * 5, view, scene));
        addLine(tileWidth * 37, screenHeight - tileWidth * 3, 1, imageLoader.get(9));
        enemies.add(new Goomba(tileWidth * 37, screenHeight - tileWidth * 4, view, scene));
        addLine(tileWidth * 43, screenHeight - tileWidth * 4, 1, imageLoader.get(10));
        enemies.add(new Goomba(tileWidth * 43, screenHeight - tileWidth * 5, view, scene));
        addLine(tileWidth * 49, screenHeight - tileWidth * 2, 1, imageLoader.get(8));
        enemies.add(new Goomba(tileWidth * 49, screenHeight - tileWidth * 3, view, scene));
        addLine(tileWidth * 55, screenHeight - tileWidth * 1, 20, imageLoader.get(3));
        addLine(tileWidth*55, screenHeight-tileWidth*2, 20, imageLoader.get(3));

    }
}
