package eecs40.supermariobros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MarioSurfaceView extends SurfaceView implements SurfaceHolder.Callback, TimeConscious {
    private MarioRenderThread    renderThread;
    //Background background;
    World w1;
    Mario mario;
    Buttons buttons;
    Bitmap title;
    Rect dst;
    int gameState = 0; //0 = title, 1 = world 1, 2 = world 2, 3 = world 3, 4 = dead, 5 = game over
    int score = 0, hiScore = 0, lives = 3, time = 15000;

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

        BitmapFactory.Options options = new BitmapFactory.Options();
        title = BitmapFactory.decodeResource(getResources(), R.drawable.title, options);
        dst= new Rect(getWidth()/2 - title.getWidth()/2, getHeight()/8, getWidth()/2 + title.getWidth()/2, getHeight()/8 + title.getHeight());

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
                if (gameState == 0) {
                    gameState++;
                }
                else if (gameState > 0 && gameState < 4) {
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
                                mario.setFireballFlag(true);
                            }
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
        if (gameState > 0 && gameState < 4) {
            if (gameState == 1) {
                w1.draw(c);
            }
            mario.draw(c);
            buttons.draw(c);
        }
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

        if (gameState == 0) {
            paint = new Paint();
            c.drawBitmap(title, null, dst, paint);
            paint.setTextSize(getWidth()/16);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            c.drawText("Tap anywhere to start", getWidth() / 2, 5* getHeight() / 6, paint);
        }
        else if (gameState > 0 && gameState < 4) {
            time--;
            if (gameState == 1) {
                w1.tick(c);
            }
            mario.tick(c);
            buttons.draw(c);
            drawScore(c);
            drawLives(c);
            drawTime(c);
        }


        //Start screen

        //Main game screen

        //Check collisions

        //Game over screen
    }

    protected void drawScore( Canvas c ) {
        //Score
        Paint paint = new Paint() ;
        paint.setTextSize(getWidth() / 36);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        c.drawText("Mario", getWidth() / 12, getHeight() / 20, paint);
        c.drawText(Integer.toString(score / 10), getWidth() / 12, getHeight() / 9, paint);
    }

    protected void drawLives( Canvas c ) {
        //Start with 3 lives
        Paint paint = new Paint() ;
        paint.setTextSize(getWidth() / 36);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        c.drawText("Lives", getWidth() / 2, getHeight() / 20, paint);
        c.drawText(String.format("%02d", lives), getWidth()/2, getHeight()/9, paint);
    }

    protected void drawTime( Canvas c) {
        Paint paint = new Paint() ;
        paint.setTextSize(getWidth() / 36);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        c.drawText("Time", 11 * getWidth() / 12, getHeight() / 20, paint);
        c.drawText(Integer.toString(time/100), 11 * getWidth() / 12, getHeight()/9, paint);
    }
}