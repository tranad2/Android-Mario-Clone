package eecs40.supermariobros;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MarioSurfaceView extends SurfaceView implements SurfaceHolder.Callback, TimeConscious {
    private MarioRenderThread    renderThread;
    //Background background;
    World w1;
    Mario mario;
    Buttons buttons;

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
        mario = new Mario(w1, this);
        w1.setMario(mario);
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
        switch (e.getAction() & MotionEvent.ACTION_MASK ) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_POINTER_DOWN:
                for (int i = 0; i < e.getPointerCount(); i++) {
                    if ((e.getY(i) >= 3 * getHeight() / 4) && (e.getY(i) <= 3 * getHeight() / 4 + buttons.getButtonLength())) {
                        if ((e.getX(i) >= getWidth() / 12) && (e.getX(i) <= getWidth() / 12 + buttons.getButtonLength())) {
                            //Left button
                            mario.setDirection(-1);
                            mario.setDx(-15f);
                            mario.setMoveLeftFlag(true);
                            mario.setMoveRightFlag(false);
                        }
                        if ((e.getX(i) >= getWidth() / 12 + buttons.getButtonLength()) && (e.getX(i) <= getWidth() / 12 + 2 * buttons.getButtonLength())) {
                            //Right button
                            mario.setDirection(1);
                            mario.setDx(15f);
                            mario.setMoveRightFlag(true);
                            mario.setMoveLeftFlag(false);

                        }
                        if ((e.getX(i) >= 5 * getWidth() / 6) && (e.getX(i) <= 5 * getWidth() / 6 + buttons.getButtonLength())) {
                            //A button
                            mario.setJumpFlag(true);
                        }
                        if ((e.getX(i) >= 5 * getWidth() / 6 - getWidth() / 40 - buttons.getButtonLength()) && (e.getX(i) <= 5 * getWidth() / 6 - getWidth() / 40)) {
                            //B button
                            //mario.setForm(2);
                            mario.setFireballFlag(true);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mario.setJumpFlag(false);
                mario.setFireballFlag(false);
                mario.setMoveLeftFlag(false);
                mario.setMoveRightFlag(false);
                mario.setDx(0);
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        //background.draw(c);
        w1.draw(c);
        mario.draw(c);
        buttons.draw(c);
    }

    @Override
    public void tick( Canvas c ) {
        //background.tick(c);

        //Fill background
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#6B8CFF"));
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