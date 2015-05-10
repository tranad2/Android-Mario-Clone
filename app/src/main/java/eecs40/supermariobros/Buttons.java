package eecs40.supermariobros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Buttons implements TimeConscious {

    private  int x1, x2, y1, y2;
    private Bitmap buttonLeft, buttonRight, buttonA, buttonB;
    Rect dst1, dst2, dst3, dst4;

    public Buttons ( MarioSurfaceView view ) {
        //Load bitmaps
        BitmapFactory.Options   options = new BitmapFactory.Options();
        buttonLeft = BitmapFactory.decodeResource(view.getResources(), R.drawable.buttonleft, options);
        buttonRight = BitmapFactory.decodeResource(view.getResources(), R.drawable.buttonright, options);
        buttonA = BitmapFactory.decodeResource(view.getResources(), R.drawable.buttona, options);
        buttonB = BitmapFactory.decodeResource(view.getResources(), R.drawable.buttonb, options);

        //Initialize background position
        x1 = view.getWidth() / 12;
        y1 = 3 * view.getHeight() / 4;
        x2 = x1 + buttonLeft.getWidth();
        y2 = y1 + buttonLeft.getHeight();
        dst1 = new Rect( x1, y1, x2, y2 );  //Left button
        x1 = x2;
        x2 = x1 + buttonRight.getWidth();
        dst2 = new Rect( x1, y1, x2, y2 );  //Right button
        x1 = 5 * view.getWidth() / 6;
        x2 = x1 + buttonA.getWidth();
        dst3 = new Rect( x1, y1, x2, y2 );  //A button
        x2 = x1 - view.getWidth() / 40;
        x1 = x2 - buttonB.getWidth();
        dst4 = new Rect( x1, y1, x2, y2 );
    }

    public void tick(Canvas c) {
        draw(c);
    }

    protected void draw ( Canvas c ) {
        Paint paint = new Paint();
        paint.setAlpha(175);
        c.drawBitmap(buttonLeft, null, dst1, paint);
        c.drawBitmap(buttonRight, null, dst2, paint);
        c.drawBitmap(buttonA, null, dst3, paint);
        c.drawBitmap(buttonB, null, dst4, paint);
    }
}