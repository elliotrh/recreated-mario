package kylelam.mario;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// todo map 2 + 3
// todo fire flower
// todo mario growth

public class MarioSurfaceView extends SurfaceView implements SurfaceHolder.Callback, TimeConscious {
    //class variables
    private MarioRenderThread thread;
    private Menu menu;
    private MapMaker maker;
    private Map map;
    protected Mario mario;
    private boolean menuGone;
    private Overlay overlay;
    private ScoreScreen sScreen;

    public int mapSelect;

    // screen res
    private int x;
    private int y;

    // screen touch control limit
    private int moveX1;
    private int moveX2;
    private int moveY;
    private int AB;

    // limit
    public int liveCounter;

    // boolean
    public boolean hitFire;
    private boolean scoreScreen;
    private boolean resetFlag;

    //constructor
    public MarioSurfaceView(Context context){
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        // set screen res + touch control screen limits
        x = this.getWidth();
        y = this.getHeight();
        moveX1 = (int) (x * 0.075);
        moveX2 = (int) (x * 0.125);
        moveY = (int) (y * 0.7);
        AB = (int) (x * 0.8);
        hitFire = false;
        scoreScreen = false;

        // menu creation
        menu = new Menu(this);
        menuGone = false;

        // use MapMaker to create map
        maker = new MapMaker(this);

        // map make using mapSelect var
        mapSelect = 1; // default start map 1
        map = maker.make(mapSelect);
        this.mario = map.returnMario();
        mario.movement = Movement.RIGHT;

        // lives
        liveCounter = 2;

        //overlay
        resetFlag = false;
        overlay = new Overlay(this, this.x, this.y, this.liveCounter);
        sScreen = new ScoreScreen(this, this.overlay.score);


        // start thread
        thread = new MarioRenderThread(this);
        thread.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // foo
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // interrupt thread when program end
        thread.interrupt();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int pointerIndex = e.getActionIndex();

        // get current positions of motion event
        int curX1 = (int) e.getX(pointerIndex);
        int curY1 = (int) e.getY(pointerIndex);

        // multitouch action get
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // menu clicks
                if (!menuGone) {
                    // default menu
                    if (menu.selectVar == Menu.Select.MAIN) {
                        // play
                        if (menu.playRect.contains(curX1, curY1)) {
                            menu.selectVar = Menu.Select.PLAY;
                            //map updates
                            maker.resetAll();
                            maker = new MapMaker(this);
                            map = maker.make(mapSelect);
                            this.mario = map.returnMario();
                            mario.movement = Movement.RIGHT;
                            menuGone = true;
                        }
                        // settings
                        else if (menu.setRect.contains(curX1, curY1)) {
                            menu.selectVar = Menu.Select.SETTINGS;
                        }
                        // exit
                        else if (menu.exitRect.contains(curX1, curY1)) {
                            System.exit(0);
                        }
                    }
                    // settings menu
                    else if (menu.selectVar == Menu.Select.SETTINGS) {
                        // back
                        if (menu.backRect.contains(curX1, curY1)) {
                            menu.selectVar = Menu.Select.MAIN;
                        }
                        // adjust lives
                        else if (menu.livesRect.contains(curX1, curY1)) {
                            liveCounter++;
                            if (liveCounter > 5) {
                                liveCounter = 0;
                            }
                            //overlay update
                            overlay = new Overlay(this, this.x, this.y, this.liveCounter);
                        }
                        // change map start
                        else if (menu.mapRect.contains(curX1, curY1)) {
                            mapSelect++;
                            if (mapSelect > 3) {
                                mapSelect = 1;
                            }
                        }
                    }
                }

                else if (menuGone) {
                    if (!scoreScreen) {
                        // left
                        if (curX1 < moveX1) {
                            mario.movingFlag = true;
                            mario.movement = Movement.LEFT;
                        }
                        // right
                        if (curX1 > moveX2 && curX1 < x / 2) {
                            mario.movingFlag = true;
                            mario.movement = Movement.RIGHT;
                        }
                        // jump
                        if (mario.movement == Movement.LEFT && curX1 > AB) {
                            mario.movementPrev = Movement.LEFT;
                            mario.movement = Movement.JUMP;
                        }
                        if (mario.movement == Movement.RIGHT && curX1 > AB) {
                            mario.movementPrev = Movement.RIGHT;
                            mario.movement = Movement.JUMP;
                        }
                        // fireball
                        if (curX1 > x / 2 && curX1 < AB) {
                            hitFire = true;
                        }
                    }
                    else if (scoreScreen && sScreen.scoreRect.contains(curX1, curY1)) {
                        resetFlag = true;
                    }
                }
                break;
            // secondary pointer
            case MotionEvent.ACTION_POINTER_DOWN:
                curX1 = (int) e.getX(pointerIndex);
                curY1 = (int) e.getY(pointerIndex);
                if (menuGone) {
                    // left
                    if (curX1 < moveX1) {
                        mario.movingFlag = true;
                        mario.movement = Movement.LEFT;
                    }
                    // right
                    if (curX1 > moveX2 && curX1 < x / 2) {
                        mario.movingFlag = true;
                        mario.movement = Movement.RIGHT;
                    }
                    // jump
                    if (mario.movement == Movement.LEFT && curX1 > AB) {
                        mario.movementPrev = Movement.LEFT;
                        mario.movement = Movement.JUMP;
                    }
                    if (mario.movement == Movement.RIGHT && curX1 > AB) {
                        mario.movementPrev = Movement.RIGHT;
                        mario.movement = Movement.JUMP;
                    }
                    // fireball
                    if (curX1 > x / 2 && curX1 < AB) {
                        hitFire = true;
                    }
                }
                break;
            // moved positions
            case MotionEvent.ACTION_MOVE:
                if (menuGone) {
                    // left
                    if (curX1 < moveX1) {
                        mario.movingFlag = true;
                        mario.movement = Movement.LEFT;
                    }
                    // right
                    if (curX1 > moveX2 && curX1 < x / 2) {
                        mario.movingFlag = true;
                        mario.movement = Movement.RIGHT;
                    }
                    // jump
                    if (mario.movement == Movement.LEFT && curX1 > AB) {
                        mario.movementPrev = Movement.LEFT;
                        mario.movement = Movement.JUMP;
                    }
                    if (mario.movement == Movement.RIGHT && curX1 > AB) {
                        mario.movementPrev = Movement.RIGHT;
                        mario.movement = Movement.JUMP;
                    }
                    // fireball
                    if (curX1 > x / 2 && curX1 < AB) {
                        hitFire = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (menuGone) {
                    // if moving left, set to none left
                    if (mario.movement == Movement.LEFT) {
                        mario.movingFlag = false;
                    }
                    // if moving right, set to none right
                    if (mario.movement == Movement.RIGHT) {
                        mario.movingFlag = false;
                    }
                    // if jump, reset to previous direction
                    if (mario.movement == Movement.JUMP) {
                        mario.movement = mario.movementPrev;
                    }
                    // if fireball, reset to no fireball
                    if (hitFire) {
                        mario.movingFlag = false;
                        hitFire = false;
                    }
                }
                break;
            // secondary pointer
            case MotionEvent.ACTION_POINTER_UP:
                if (menuGone) {
                    // if moving left, set to none left
                    if (mario.movement == Movement.LEFT) {
                        mario.movingFlag = false;
                    }
                    // if moving right, set to none right
                    if (mario.movement == Movement.RIGHT) {
                        mario.movingFlag = false;
                    }
                    // if jump, reset to previous direction
                    if (mario.movement == Movement.JUMP) {
                        mario.movement = mario.movementPrev;
                    }
                    // if fireball, reset to no fireball
                    if (hitFire) {
                        mario.movingFlag = false;
                        hitFire = false;
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        sScreen.draw(c);
        map.draw(c);
        //draws initial goombas
        for (int j = 0; j < map.goombaList.size(); j++) {
            // check if goombas are on screen
            if ((x - map.goombaList.get(j).goombaInfo.rect.left) > 0 && map.goombaList.get(j).goombaInfo.rect.right > 0) {
                map.goombaList.get(j).draw(c);
            }
        }
        //draws bullet Bills
        for (int h = 0; h < map.bulletList.size(); h++) {
            // check if bullets are on screen
            if ((x - map.bulletList.get(h).bulletInfo.rect.left) > 0 && map.bulletList.get(h).bulletInfo.rect.right > 0) {
                map.bulletList.get(h).draw(c);
            }
        }

        mario.draw(c);
        overlay.draw(c);
        menu.draw(c);
    }

    @Override
    public void tick(Canvas c) {
        // display menu update
        menu.tick(c);

        // if clicked play
        if (menuGone) {
            if (!scoreScreen) {
                // check mario death
                if (mario.checkDeath() || mario.marioDied) {
                    // used all lives
                    if (liveCounter == 0) {
                        this.resetAll();
                    }
                    // keep going
                    else if (liveCounter != 0) {
                        this.resetLevel();
                    }
                }

                // check next level
                if (mario.nextLevel) {
                    if (mapSelect != 3) {
                        this.nextLevel();
                    } else if (mapSelect == 3) {
                        scoreScreen = true;
                    }
                }
                map.tick(c);
                // increment each goomba
                for (int j = 0; j < map.goombaList.size(); j++) {
                    if ((x - map.goombaList.get(j).goombaInfo.rect.left) > 0 && map.goombaList.get(j).goombaInfo.rect.right > 0) {
                        map.goombaList.get(j).tick(c);
                    }
                }
                // increment bullet bills
                for (int h = 0; h < map.bulletList.size(); h++) {
                    if ((x - map.bulletList.get(h).bulletInfo.rect.left) > 0 && map.bulletList.get(h).bulletInfo.rect.right > 0) {
                        map.bulletList.get(h).tick(c);
                    }
                }

                mario.tick(c);
                overlay.tick(c);
            }
            else if (scoreScreen) {
                sScreen.score = overlay.score;
                sScreen.tick(c);
                if (resetFlag) {
                    this.resetAll();
                }
            }
        }
    }

    // reset back to menu
    private void resetAll () {
        // menu creation
        menu = new Menu(this);
        menuGone = false;
        scoreScreen = false;
        resetFlag = false;

        // use MapMaker to create map
        maker.resetAll();
        maker = new MapMaker(this);

        // map make using mapSelect var
        mapSelect = 1;
        map = maker.make(mapSelect);
        Mario.destroyMario(mario);
        this.mario = map.returnMario();
        mario.movement = Movement.RIGHT;
        mario.marioDied = false;

        liveCounter = 2;
        //new overlay
        overlay = new Overlay(this, this.x, this.y, this.liveCounter);
        sScreen = new ScoreScreen(this, this.overlay.score);
    }

    // reset level only
    private void resetLevel () {

        // use MapMaker to create map
        maker.resetAll();
        maker = new MapMaker(this);

        // map make using mapSelect var
        map = maker.make(mapSelect);
        Mario.destroyMario(mario);
        this.mario = map.returnMario();
        mario.movement = Movement.RIGHT;
        mario.marioDied = false;
        mario.nextLevel = false;

        // decrement lives
        this.liveCounter--;
        overlay.lives = liveCounter;
    }

    // next level
    private void nextLevel () {
        // use MapMaker to create map
        maker.resetAll();
        maker = new MapMaker(this);

        // map make using mapSelect var
        mapSelect++;
        map = maker.make(mapSelect);
        Mario.destroyMario(mario);
        this.mario = map.returnMario();
        mario.movement = Movement.RIGHT;
        mario.nextLevel = false;
    }
}

