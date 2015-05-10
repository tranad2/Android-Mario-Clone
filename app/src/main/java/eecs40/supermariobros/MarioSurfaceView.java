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

    public MarioSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderThread = new MarioRenderThread(this);
        renderThread.start();
        //background = new Background(this);
        mario = new Mario(this);
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
                mario.setTouchFlag(true);
                break;
            case MotionEvent.ACTION_UP:
                //...
                mario.setTouchFlag(false);
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        //background.draw(c);
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

        mario.tick(c);
        buttons.draw(c);

        //Start screen

        //Main game screen

        //Check collisions

        //Game over screen
    }
}