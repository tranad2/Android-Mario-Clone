package eecs40.supermariobros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class MarioSurfaceView extends SurfaceView implements SurfaceHolder.Callback, TimeConscious {
    private MarioRenderThread    renderThread;
    private ArrayList<World> levels;
    private ArrayList<Bitmap> colorSelect;
    private Bitmap redMario, greenMario, yellowMario, purpleMario;
    private Rect redRect, greenRect, yellowRect, purpleRect;
    private int redTouch, greenTouch, yellowTouch, purpleTouch, timer = 0;
    World w1, w2;
    Mario mario;
    Buttons buttons;
    Bitmap title;
    Rect dst;
    float gameState = 0; //0 = title, 0.5 = color select, 0.75 = stage select,  1 = world 1, 2 = world 2, 3 = world 3, 4 = dead, 5 = game over
    int world = 0, score = 0, lives = 3, time = 9900, color = 0;

    public MarioSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderThread = new MarioRenderThread(this);
        renderThread.start();

        levels = new ArrayList<>();
        w1 = new World1(this);
        w2 = new World2(this);
        levels.add(w1);

        mario = new Mario(this.getWidth()/4,this.getHeight()/2,w1, this);
        w1.setMario(mario);

        BitmapFactory.Options options = new BitmapFactory.Options();
        title = BitmapFactory.decodeResource(getResources(), R.drawable.title, options);
        dst= new Rect(getWidth()/2 - title.getWidth()/2, getHeight()/8, getWidth()/2 + title.getWidth()/2, getHeight()/8 + title.getHeight());

        buttons = new Buttons(this);

        loadImages(this);
        redMario = colorSelect.get(0);
        greenMario = colorSelect.get(4);
        yellowMario = colorSelect.get(8);
        purpleMario = colorSelect.get(12);

        redRect = new Rect(2 * getWidth() / 7, 2 * getHeight() / 5, 2 * getWidth() / 7 + redMario.getWidth(), 2 * getHeight() / 5 + redMario.getHeight());
        greenRect = new Rect(3 * getWidth() / 7, 2 * getHeight() / 5, 3 * getWidth() / 7 + greenMario.getWidth(), 2 * getHeight() / 5 + greenMario.getHeight());
        yellowRect = new Rect(4 * getWidth() / 7, 2 * getHeight() / 5, 4 * getWidth() / 7 + yellowMario.getWidth(), 2 * getHeight() / 5 + yellowMario.getHeight());
        purpleRect = new Rect(5 * getWidth() / 7, 2 * getHeight() / 5, 5 * getWidth() / 7 + purpleMario.getWidth(), 2 * getHeight() / 5 + purpleMario.getHeight());
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
                //World screen
                if (gameState >= 1 && gameState <= 3) {
                    for (int i = 0; i < e.getPointerCount(); i++) {
                        if ((e.getY(i) >= 3 * getHeight() / 4) && (e.getY(i) <= 3 * getHeight() / 4 + buttons.getButtonLength())) {
                            if ((e.getX(i) >= getWidth() / 12) && (e.getX(i) <= getWidth() / 12 + buttons.getButtonLength())) {
                                //Left button
                                mario.setDirection(-1);
                                mario.setMoveLeftFlag(true);
                                mario.setMoveRightFlag(false);
                            }
                            if ((e.getX(i) >= getWidth() / 12 + buttons.getButtonLength()) && (e.getX(i) <= getWidth() / 12 + 2 * buttons.getButtonLength())) {
                                //Right button
                                mario.setDirection(1);
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
                //Start screen
                if (gameState == 0) {
                    gameState = 0.5f;
                }

                //Character select screen
                if (gameState == 0.5) {
                    if ((e.getY() >= 2 * getHeight() / 5) && (e.getY() <= 2 * getHeight() / 5 + redMario.getHeight())) {
                        if ((e.getX() >= 2 * getWidth() / 7) && (e.getX() <= 2 * getWidth() / 7 + redMario.getWidth())) {
                            color = 0;

                            redTouch++;
                            greenTouch = 0;
                            yellowTouch = 0;
                            purpleTouch = 0;

                            greenMario = colorSelect.get(4);
                            yellowMario = colorSelect.get(8);
                            purpleMario = colorSelect.get(12);
                        } else if ((e.getX() >= 3 * getWidth() / 7) && (e.getX() <= 3 * getWidth() / 7 + redMario.getWidth())) {
                            color = 1;

                            redTouch = 0;
                            greenTouch++;
                            yellowTouch = 0;
                            purpleTouch = 0;

                            redMario = colorSelect.get(0);
                            yellowMario = colorSelect.get(8);
                            purpleMario = colorSelect.get(12);
                        } else if ((e.getX() >= 4 * getWidth() / 7) && (e.getX() <= 4 * getWidth() / 7 + redMario.getWidth())) {
                            color = 2;

                            redTouch = 0;
                            greenTouch = 0;
                            yellowTouch++;
                            purpleTouch = 0;

                            redMario = colorSelect.get(0);
                            greenMario = colorSelect.get(4);
                            purpleMario = colorSelect.get(12);
                        } else if ((e.getX() >= 5 * getWidth() / 7) && (e.getX() <= 5 * getWidth() / 7 + redMario.getWidth())) {
                            color = 3;

                            redTouch = 0;
                            greenTouch = 0;
                            yellowTouch = 0;
                            purpleTouch++;

                            redMario = colorSelect.get(0);
                            greenMario = colorSelect.get(4);
                            yellowMario = colorSelect.get(8);
                        }
                    }
                    if (redTouch == 2 || greenTouch == 2 || yellowTouch == 2 || purpleTouch == 2) {
                        gameState = 0.75f;
                        mario = new Mario(this.getWidth()/4,this.getHeight()/2,w1, this);
                        w1.setMario(mario);
                    }
                    break;
                }

                //Stage select
                if (gameState == 0.75) {
                    if ((e.getY() >= 2 * getHeight() / 5) && (e.getY() <= 2 * getHeight() / 5 + redMario.getHeight())) {
                        if (e.getX() >= 3 * getWidth() / 12 && e.getX() <= 5 * getWidth() / 12 ){
                            world = 1;
                            gameState = 1;
                        } else if (e.getX() > 5 * getWidth() / 12 && e.getX() < 7 * getWidth() / 12 ){
                            world = 2;
                            gameState = 2;
                        } else if (e.getX() >= 7 * getWidth() / 12 && e.getX() <= 9 * getWidth() / 12 ) {
                            world = 3;
                            gameState = 3f;
                        }
                    }
                    break;
                }

                //World screen - reset flags when buttons are released
                else if (gameState >= 1 && gameState <= 3) {
                    mario.setJumpFlag(false);
                    mario.setFireballFlag(false);
                    mario.setMoveLeftFlag(false);
                    mario.setMoveRightFlag(false);
                }

                //Retry screen
                else if (gameState == 4) {
                    //Restart current level
                    if(world == 1) {
                        gameState = 1;
                        mario.revive();
                        mario.setDeathTimer(false);
                        mario.setDy(0);
                        w1.reset();
                    } else if(world == 2) {
                        gameState = 2;
                    } else if(world == 3) {
                        gameState = 3;
                    }
                    time = 10000;
                }

                //Game Over screen
                else if (gameState == 5) {
                    gameState = 0;
                    world = 0;
                    score = 0;
                    lives = 3;
                    time = 10000;
                    color = 0;
                    redTouch = 0;
                    greenTouch = 0;
                    yellowTouch = 0;
                    purpleTouch = 0;
                    redMario = colorSelect.get(0);
                    greenMario = colorSelect.get(4);
                    yellowMario = colorSelect.get(8);
                    purpleMario = colorSelect.get(12);
                    w1.reset();
                    //TODO
                    //w2.reset();
                    //w3.reset();
                }
                break;
        }
        return true;
    }

        @Override
        public void onDraw(Canvas c) {
            super.onDraw(c);
        if (gameState == 0.5) {
            drawColors(c);
        }

        //Draw world based on gamestate
        else if (gameState > 0.5 && gameState < 4) {
            if(gameState == 1) {
                w1.start(c);
            } else if(gameState == 2) {
                //TODO
                //w2.start(c);
            }else if (gameState == 3) {
                //TODO
                //w3.start(c);
            }
            mario.draw(c);
            buttons.draw(c);
        }
    }

    @Override
    public void tick( Canvas c ) {
        Paint paint = new Paint();
        Log.v("TAG", "gameState: " + gameState);

        //Fill background with blue for world background
        if (gameState <= 3) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#6B8CFF"));
            paint.setAntiAlias(true);
            c.drawPaint(paint);
        }

        //Start screen - draw title
        if (gameState == 0) {
            paint = new Paint();
            c.drawBitmap(title, null, dst, paint);
            paint.setTextSize(getWidth() / 16);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            c.drawText("Tap Anywhere to Start", getWidth() / 2, 5 * getHeight() / 6, paint);
        }

        //Character select screen - draw text, mario sprites, animate
        else if (gameState == 0.5) {
            paint = new Paint();
            paint.setTextSize(getWidth() / 16);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            c.drawText("Select Your Character", getWidth() / 2, 5 * getHeight() / 6, paint);
            drawColors(c);
            doAnim();
        }

        //Stage select screen - draw text
        else if (gameState == 0.75) {
            paint = new Paint();
            paint.setTextSize(getWidth() / 16);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            c.drawText("Select Your Stage", getWidth() / 2, 5 * getHeight() / 6, paint);
            c.drawText("1", 2 * getWidth() / 6, 8 * getHeight() / 15, paint);
            c.drawText("2", 3 * getWidth() / 6, 8 * getHeight() / 15, paint);
            c.drawText("3", 4 * getWidth() / 6, 8 * getHeight() / 15, paint);
        }


        //World screen - decrement time
        else if (gameState >= 1 && gameState <= 3) {
            if (!mario.isDead()) {
                time--;
                time--;
            }
            if (gameState == 1) {
                w1.start(c);
            } else if(gameState == 2) {
                //TODO
                //w2.start(c);
            } else if (gameState == 3) {
                //TODO
                //w3.start(c);
            }
        }

        //Fill background with black on death & game over
        else if (gameState >= 4) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            c.drawPaint(paint);
            if (gameState == 4) {
                paint = new Paint();
                paint.setTextSize(getWidth() / 16);
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                c.drawText("Tap Anywhere to Retry", getWidth() / 2, getHeight() / 2, paint);
            } else if (gameState == 5) {
                paint = new Paint();
                paint.setTextSize(getWidth() / 16);
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                c.drawText("Game Over", getWidth() / 2, getHeight() / 2, paint);
            }
        }

        if (gameState > 0.75) {
            mario.tick(c);
            buttons.draw(c);
            drawScore(c);
            drawLives(c);
            drawTime(c);
        }
    }

    //In-game HUD
    protected void drawScore( Canvas c ) {
        //Score
        Paint paint = new Paint() ;
        paint.setTextSize(getWidth() / 36);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        if (color == 0) {
            c.drawText("Mario", getWidth() / 12, getHeight() / 20, paint);
        } else if (color == 1) {
            c.drawText("Luigi", getWidth() / 12, getHeight() / 20, paint);
        } else if (color == 2) {
            c.drawText("Wario", getWidth() / 12, getHeight() / 20, paint);
        } else if (color == 3) {
            c.drawText("Waluigi", getWidth() / 12, getHeight() / 20, paint);
        }
        c.drawText(Integer.toString(score), getWidth() / 12, getHeight() / 9, paint);
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
        c.drawText(Integer.toString(time / 100), 11 * getWidth() / 12, getHeight()/9, paint);
    }

    public void loadImages(MarioSurfaceView view) {
        Bitmap redMario1, redMario2, redMario3, redMario4, greenMario1, greenMario2, greenMario3, greenMario4, yellowMario1, yellowMario2, yellowMario3, yellowMario4, purpleMario1, purpleMario2, purpleMario3, purpleMario4;
        colorSelect = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();
        redMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario1, options);
        redMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario2, options);
        redMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario3, options);
        redMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigredmario4, options);
        greenMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.biggreenmario1, options);
        greenMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.biggreenmario2, options);
        greenMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.biggreenmario3, options);
        greenMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.biggreenmario4, options);
        yellowMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigyellowmario1, options);
        yellowMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigyellowmario2, options);
        yellowMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigyellowmario3, options);
        yellowMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigyellowmario4, options);
        purpleMario1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigpurplemario1, options);
        purpleMario2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigpurplemario2, options);
        purpleMario3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigpurplemario3, options);
        purpleMario4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigpurplemario4, options);

        colorSelect.add(redMario1);     //0
        colorSelect.add(redMario2);     //1
        colorSelect.add(redMario3);     //2
        colorSelect.add(redMario4);     //3
        colorSelect.add(greenMario1);   //4
        colorSelect.add(greenMario2);   //5
        colorSelect.add(greenMario3);   //6
        colorSelect.add(greenMario4);   //7
        colorSelect.add(yellowMario1);  //8
        colorSelect.add(yellowMario2);  //9
        colorSelect.add(yellowMario3);  //10
        colorSelect.add(yellowMario4);  //11
        colorSelect.add(purpleMario1);  //12
        colorSelect.add(purpleMario2);  //13
        colorSelect.add(purpleMario3);  //14
        colorSelect.add(purpleMario4);  //15
    }

    public void doAnim(){
        if(timer <= 5){
            if (color == 0) {
                redMario = colorSelect.get(1);
            } else if (color == 1) {
                greenMario = colorSelect.get(5);
            } else if (color == 2) {
                yellowMario = colorSelect.get(9);
            } else if (color == 3) {
                purpleMario = colorSelect.get(13);
            }
            timer++;
        } else if(timer <= 10){
            if (color == 0) {
                redMario = colorSelect.get(2);
            } else if (color == 1) {
                greenMario = colorSelect.get(6);
            } else if (color == 2) {
                yellowMario = colorSelect.get(10);
            } else if (color == 3) {
                purpleMario = colorSelect.get(14);
            }
            timer++;
        } else if(timer <= 15){
            if (color == 0) {
                redMario = colorSelect.get(3);
            } else if (color == 1) {
                greenMario = colorSelect.get(7);
            } else if (color == 2) {
                yellowMario = colorSelect.get(11);
            } else if (color == 3) {
                purpleMario = colorSelect.get(15);
            }
            timer++;
        } else if(timer <= 20){
            if (color == 0) {
                redMario = colorSelect.get(2);
            } else if (color == 1) {
                greenMario = colorSelect.get(6);
            } else if (color == 2) {
                yellowMario = colorSelect.get(10);
            } else if (color == 3) {
                purpleMario = colorSelect.get(14);
            }
            timer++;
        } else if(timer <= 30) {
            timer = 0;
        }
    }

    public void drawColors(Canvas c){
        Paint paint = new Paint();
        c.drawBitmap(redMario, null, redRect, paint);
        c.drawBitmap(greenMario, null, greenRect, paint);
        c.drawBitmap(yellowMario, null, yellowRect, paint);
        c.drawBitmap(purpleMario, null, purpleRect, paint);
    }
}
