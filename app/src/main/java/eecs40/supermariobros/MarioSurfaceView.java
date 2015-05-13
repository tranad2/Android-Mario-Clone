package eecs40.supermariobros;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MarioSurfaceView extends SurfaceView implements SurfaceHolder.Callback, TimeConscious {
    private MarioRenderThread    renderThread;
    //Background background;
    Buttons buttons;
    Mario mario;
    World1 w1;

    public MarioSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderThread = new MarioRenderThread(this);
        renderThread.start();
        //background = new Background(this);
        w1 = new World1(this);
        mario = new Mario(w1.getObstacles(),this);
        buttons = new Buttons(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Respond to surface changes, e.g., aspect ratio changes.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The cleanest way to stop a thread is by interrupting it.
        // The thread must regularly check its interrupt flag.
        renderThread.interrupt();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //...
                if ((e.getY() >= 3 * getHeight() / 4) && (e.getY() <= 3 * getHeight() / 4 + buttons.getButtonLength())) {
                    if ((e.getX() >= getWidth() / 12) && (e.getX() <= getWidth() / 12 + buttons.getButtonLength())) {
                        //Left button
                        mario.setDirection(-1);
                        mario.setDx(-15f);
                    }
                    if ((e.getX() >= getWidth() / 12 + buttons.getButtonLength()) && (e.getX() <= getWidth() / 12 + 2 * buttons.getButtonLength())) {
                        //Right button
                        mario.setDirection(1);
                        mario.setDx(15f);

                    }
                    if ((e.getX() >= 5 * getWidth() / 6) && (e.getX() <= 5 * getWidth() / 6 + buttons.getButtonLength())) {
                        //A button
                        mario.setTouchFlag(true);
                    }
                    if ((e.getX() >= 5 * getWidth() / 6 - getWidth() / 40 - buttons.getButtonLength()) && (e.getX() <= 5 * getWidth() / 6 - getWidth() / 40)) {
                        //B button
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //...
                mario.setTouchFlag(false);
                mario.setDirection(0);
                mario.setDx(0);
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        //background.draw(c);
        w1.tick(c);
        mario.draw(c);
        buttons.draw(c);
        //...
    }

    @Override
    public void tick( Canvas c ) {
        //background.tick(c);

        //Fill background
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.CYAN);
        paint.setAntiAlias(true);
        c.drawPaint(paint);
        w1.tick(c);
        mario.tick(c);
        buttons.draw(c);

        //Start screen

        //Main game screen

        //Check collisions

        //Game over screen
    }
}