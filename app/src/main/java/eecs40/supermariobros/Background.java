package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/*test*/

public class Background implements TimeConscious {

    Bitmap                  bitmap;
    BitmapFactory.Options   options = new BitmapFactory.Options();
    public  int             x1, X1;
    public  int             x2, X2;
    public  int             y1, Y1;
    public  int             y2, Y2;
    public  int             screenWidth, screenHeight;
    Rect                    dst1, dst2;

    public Background ( MarioSurfaceView view ) {
        //Load background bitmap
        this.bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.mariobackground1, options);

        //Initialize background position
        screenWidth = view.getWidth();
        screenHeight = view.getHeight();
        x1 = 0;
        x2 = screenWidth;
        y1 = 0;
        y2 = screenHeight;
        X1 = this.x2;
        X2 = this.X1 + screenWidth;
        Y1 = 0;
        Y2 = screenHeight;

        //Initialize rectanges for drawing
        dst1 = new Rect( x1, y1, x2, y2 );
        dst2 = new Rect( X1, Y1, X2, Y2 );
    }

    //Change background location
    public void setLocation( int x1, int y1, int x2, int y2, int X1, int Y1, int X2, int Y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        dst1.set(x1, y1, x2, y2);

        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        dst2.set(X1, Y1, X2, Y2);
    }

    public void tick( Canvas canvas ) {
        //Set background scrolling speed
        x1 -= 10;
        x2 -= 10;
        X1 -= 10;
        X2 -= 10;

        //Looping background algorithm
        if (x2 <= 0) {
            x1 = this.X2;
            x2 = this.x1 + screenWidth;
        }
        else if (this.X2 <= 0) {
            X1 = this.x2;
            X2 = this.X1 + screenWidth;
        }
        setLocation( x1, y1, x2, y2, X1, Y1, X2, Y2);
        draw (canvas);
    }

    protected void draw ( Canvas c ) {
        Paint paint = new Paint();
        c.drawBitmap ( bitmap, null, dst1, paint );
        c.drawBitmap ( bitmap, null, dst2, paint );
    }
}