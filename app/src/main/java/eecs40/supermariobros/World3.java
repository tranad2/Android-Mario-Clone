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
        int offset = 0;

        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<50; j++) {
                scene.add(new Obstacle(j*imageLoader.get(3).getWidth(),screenHeight-imageLoader.get(3).getHeight()*i , imageLoader.get(3)));
            }
        }

        Item item = new Item(imageLoader.get(0).getWidth()*6,screenHeight-imageLoader.get(0).getHeight()*8, 0,view);
        scene.add(new Obstacle(imageLoader.get(0).getWidth()*10,screenHeight-imageLoader.get(0).getHeight()*5, imageLoader.get(0)));
        enemies.add(new Goomba(2 * screenWidth / 3, screenHeight / 2, view, scene));

        scene.add(new Obstacle(imageLoader.get(0).getWidth() * 6, screenHeight - imageLoader.get(0).getHeight() * 7, imageLoader.get(0), item, itemList));

        offset = ((scene.get(scene.size()-1)).getX()+tileWidth);  //New x after last obstacle

        for(int i = 1; i<=2; i++) {
            for(int j = 0; j<10; j++) {
                scene.add(new Obstacle(j*imageLoader.get(3).getWidth()+2*offset,screenHeight-imageLoader.get(3).getHeight()*i , imageLoader.get(3)));
            }
        }
    }
}
