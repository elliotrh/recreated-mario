package kylelam.mario;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;

/**
 * Created by Kyle on 5/15/2015.
 */
public class Mario implements TimeConscious {
    // class variables
    private MarioSurfaceView view;
    private Map map;
    private int alpha;
    public Movement movement;
    public Movement movementPrev;

    // flags
    public boolean movingFlag;
    public boolean marioDied;
    public boolean enemyKilled;
    public boolean nextLevel;
    public boolean gotShroom;
    public boolean gotFlower;
    public boolean gotCoin;
    public boolean hitQuestion;
    public boolean hitQuestion2;
    public boolean Invulnerable;
    public boolean noBreak;
    public boolean alreadyFired;

    // Mario's velocities
    private int lrv = 15;
    private int fireVel = 20;
    private int jump = 0;
    private boolean initJump = true;
    private int counter;

    // small Mario size
    private int marioHeight;
    private int marioBigHeight;

    // screen res
    private int x;
    private int y;

    private int moveLimit;

    // bitmap info
    private BitmapInfo marioInfo;
    private BitmapInfo fireBallInfo;

    // collision
    public BitmapInfo collidedObject;
    private int collidedIndex;
    private int collidedGoomba;
    private int collidedBullet;
    private int collidedShroom;
    private int collidedFlower;
    private int collidedCoin;
    private int fireDestroyIndex;

    // enum for side hit
    private enum sideHit {
        TOP, LEFT, RIGHT, BOTTOM
    }
    private sideHit side;

    // counter
    private int flip;
    private int InvulnerableCounter;
    private int noBreakCounter;
    private int itemsCounter;

    // constructor
    public Mario (MarioSurfaceView view, Map map, int x, int y, BitmapInfo marioInfo) {
        this.view = view;
        this.map = map;
        this.alpha = 255;
        this.movingFlag = false;
        this.marioDied = false;
        this.enemyKilled = false;
        this.nextLevel = false;
        this.gotShroom = false;
        this.gotFlower = false;
        this.gotCoin = false;
        this.hitQuestion = false;
        this.hitQuestion2 = false;
        this.Invulnerable = false;
        this.noBreak = false;
        this.flip = 0;
        this.InvulnerableCounter = 0;
        this.noBreakCounter = 0;
        this.itemsCounter = 0;
        this.x = x;
        this.y = y;
        this.moveLimit =  this.x / 2 - map.returnSide() * 4;
        this.marioInfo = marioInfo;
        this.marioHeight = (int) (map.returnSide() * 1.3);
        this.marioBigHeight = map.returnSide() * 2;
    }

    // tick
    public void tick (Canvas c) {
        // go through physics and increment movement
        this.physics(movement);
        this.draw(c);
    }

    // draw function
    public void draw (Canvas c) {
        if (gotFlower) {
            Paint firePaint = new Paint();
            firePaint.setAlpha(255);
            c.drawBitmap(fireBallInfo.bitmap, null, fireBallInfo.rect, firePaint);
        }
        Paint paint = new Paint();
        if (Invulnerable) {
            alpha = 130;
        }
        else {
            alpha = 255;
        }
        paint.setAlpha(alpha);
        c.drawBitmap(marioInfo.bitmap, null, marioInfo.rect, paint);
    }

    // physics
    public void physics (Movement movement) {
        switch (movement) {
            // left movement
            case LEFT:
                // if moving
                if (movingFlag) {
                    // increment
                    lrv = 15;
                    marioInfo.rect.left -= lrv;
                    marioInfo.rect.right -= lrv;

                    // collision
                    if (checkCollision(map.mapObjects, this)) {
                        if (side == sideHit.LEFT) {
                            marioInfo.rect.left += lrv;
                            marioInfo.rect.right += lrv;
                            lrv = 0;
                        }
                    }

                    // if Mario is at left border
                    if (marioInfo.rect.left <= 0) {
                        marioInfo.rect.left = 0;
                        marioInfo.rect.right = map.returnSide();
                    }
                    this.scrollBitmaps(movement, movementPrev);
                }
                // reset sprite
                else {
                    this.scrollBitmaps(movement, movementPrev);
                }
                break;
            // right movement
            case RIGHT:
                // if moving
                if (movingFlag) {
                    // increment, check if mario is at screen limit, if so increment map
                    lrv = 15;
                    if (marioInfo.rect.right < moveLimit || map.mapEnded()) {
                        marioInfo.rect.left += lrv;
                        marioInfo.rect.right += lrv;
                    }
                    else if (marioInfo.rect.right >= moveLimit) {
                        for (int i = 0; i < map.mapObjects.size(); i++) {
                            map.mapObjects.get(i).rect.left -= lrv;
                            map.mapObjects.get(i).rect.right -= lrv;
                        }
                        // compensate movement for other active objects
                        for (int v = 0; v < map.goombaList.size(); v++) {
                            map.goombaList.get(v).goombaInfo.rect.left -= lrv;
                            map.goombaList.get(v).goombaInfo.rect.right -= lrv;
                        }
                        for (int z = 0; z < map.bulletList.size(); z++) {
                            map.bulletList.get(z).bulletInfo.rect.left -= lrv;
                            map.bulletList.get(z).bulletInfo.rect.right -= lrv;
                        }
                        for (int d = 0; d < map.shroomObjects.size(); d++) {
                            map.shroomObjects.get(d).rect.left -= lrv;
                            map.shroomObjects.get(d).rect.right -= lrv;
                        }
                        for (int e = 0; e < map.flowerObjects.size(); e++) {
                            map.flowerObjects.get(e).rect.left -= lrv;
                            map.flowerObjects.get(e).rect.right -= lrv;
                        }
                        for (int i = 0; i < map.coinObjects.size(); i++) {
                            map.coinObjects.get(i).rect.left -= lrv;
                            map.coinObjects.get(i).rect.right -= lrv;
                        }

                    }
                    // collision
                    if (checkCollision(map.mapObjects, this)) {
                        if (side == sideHit.RIGHT) {
                            marioInfo.rect.left -= lrv;
                            marioInfo.rect.right -= lrv;
                            lrv = 0;
                        }
                    }
                    this.scrollBitmaps(movement, movementPrev);
                }
                // reset sprite
                else {
                    this.scrollBitmaps(movement, movementPrev);
                }
                break;
            // jumps (left/right only has bitmap difference)
            case JUMP:
                if (initJump){
                    counter = 0;
                    jump = 22;
                }
                initJump = false;
                this.scrollBitmaps(movement, movementPrev);
                break;
        }
        // apply gravity + check collision
        this.gravityCollide();

        // set fireball flag // todo
        if (gotFlower && !alreadyFired) {
            // fireball
            Rect fireRect = new Rect(marioInfo.rect.left + 17, marioInfo.rect.top + 47, marioInfo.rect.right - 17, marioInfo.rect.bottom - 47);
            this.fireBallInfo = new BitmapInfo(ImageFactory.objects.get(6), fireRect);
            if (map.view.hitFire) {
                alreadyFired = true;
            }
        }

        // enemy collision
        if (!Invulnerable) {
            if (checkEnemyCollision() || checkEnemyCollision2()) {
                // dies if hit mario's side
                if (side == sideHit.RIGHT || side == sideHit.LEFT || side == sideHit.BOTTOM) {
                    if (gotFlower) {
                        gotFlower = false;
                        gotShroom = true;
                        itemsCounter--;
                        Invulnerable = true;
                    }
                    else if (gotShroom) {
                        gotShroom = false;
                        Invulnerable = true;
                    } else if (!gotShroom) {
                        marioDied = true;
                    }
                }
            }
        }
        else if (Invulnerable) {
            InvulnerableCounter++;
            if (InvulnerableCounter > 50) {
                Invulnerable = false;
                InvulnerableCounter = 0;
            }
        }

        // can only break blocks as big Mario
        if (gotShroom || gotFlower) {
            // timer to limit block breaking for map stability
            if (!noBreak) {
                if (collidedObject.type == BlockType.BRICK) {
                    if (side == sideHit.BOTTOM) {
                        // remove brick block
                        map.mapObjects.remove(collidedIndex);
                        noBreak = true;
                    }
                }
            }
            // timer
            else if (noBreak) {
                noBreakCounter++;
                if (noBreakCounter > 30) {
                    noBreak = false;
                    noBreakCounter = 0;
                }
            }
        }

        // item collision
        if (checkShroomCollision()) {
            map.shroomObjects.remove(collidedShroom);
            gotShroom = true;
            hitQuestion = false;
            map.alreadyGotShroom = false;
        }

        //coin collision
        if (checkCoinCollision()) {
            map.coinObjects.remove(collidedCoin);
            gotCoin = true;
        }

        // block type collision
        if (collidedObject.type == BlockType.QUESTION) {
            if (side == sideHit.BOTTOM) {
                map.mapObjects.get(collidedIndex).bitmap = ImageFactory.objects.get(1);
                map.mapObjects.get(collidedIndex).type = null;
                if (itemsCounter == 0) {
                    hitQuestion = true;
                    itemsCounter++;
                }
                else if (itemsCounter == 1) {
                    hitQuestion2 = true;
                }
            }
        }
        // flower collision
        if (checkFlowerCollision()) {
            map.flowerObjects.remove(collidedFlower);
            gotFlower = true;
            gotShroom = false;
            hitQuestion2 = false;
            map.alreadyGotFlower = false;
        }

        // fireball collision
        if (alreadyFired) {
            if (movement == Movement.RIGHT) {
                fireBallInfo.rect.left += fireVel;
                fireBallInfo.rect.right += fireVel;
            }
            else if (movement == Movement.LEFT) {
                fireBallInfo.rect.left -= fireVel;
                fireBallInfo.rect.right -= fireVel;
            }
            if (!checkFireBallCollision() && Math.abs(fireBallInfo.rect.right - marioInfo.rect.right) > 200) {
                fireBallInfo.rect = new Rect(marioInfo.rect.left + 17, marioInfo.rect.top + 47, marioInfo.rect.right - 17, marioInfo.rect.bottom - 47);
                alreadyFired = false;
            }
            else if (checkFireBallCollision()) {
                map.goombaList.remove(fireDestroyIndex);
                fireBallInfo.rect = new Rect(marioInfo.rect.left + 17, marioInfo.rect.top + 47, marioInfo.rect.right - 17, marioInfo.rect.bottom - 47);
                alreadyFired = false;
            }
        }

        // check level end
        else if (collidedObject.endVar) {
            nextLevel = true;
        }
    }

    // collision with mapObjects
    private boolean checkCollision (ArrayList<BitmapInfo> objects, Mario player) {
        for (int i = 0; i < objects.size(); i++) {
            if (Rect.intersects(objects.get(i).rect, player.marioInfo.rect)) {
                // determine which side of Mario was hit
                float wy = (player.marioInfo.rect.width() + objects.get(i).rect.width()) * (objects.get(i).rect.exactCenterY() - player.marioInfo.rect.exactCenterY());
                float hx = (player.marioInfo.rect.height() + objects.get(i).rect.height()) * (objects.get(i).rect.exactCenterX() - player.marioInfo.rect.exactCenterX());
                if (wy > hx) {
                    // top
                    if (wy > -hx) {
                        side = sideHit.TOP;
                    }
                    // left
                    else {
                        side = sideHit.LEFT;
                    }
                }
                else {
                    // right
                    if (wy > -hx) {
                        side = sideHit.RIGHT;
                    }
                    // bottom
                    else {
                        side = sideHit.BOTTOM;
                    }
                }
                collidedObject = objects.get(i);
                collidedIndex = i;
                return true;
            }
        }
        // no intersection
        return false;
    }

    // collision with enemy objects
    private boolean checkEnemyCollision () {
        for (int i = 0; i < map.goombaList.size(); i++) {
            // check goombas
            if (Rect.intersects(map.goombaList.get(i).goombaInfo.rect, this.marioInfo.rect)) {
                // determine which side of Mario was hit
                float wy = (this.marioInfo.rect.width() + map.goombaList.get(i).goombaInfo.rect.width()) * (map.goombaList.get(i).goombaInfo.rect.exactCenterY() - this.marioInfo.rect.exactCenterY());
                float hx = (this.marioInfo.rect.height() + map.goombaList.get(i).goombaInfo.rect.height()) * (map.goombaList.get(i).goombaInfo.rect.exactCenterX() - this.marioInfo.rect.exactCenterX());
                if (wy > hx) {
                    // top
                    if (wy > -hx) {
                        side = sideHit.TOP;
                    }
                    // left
                    else {
                        side = sideHit.LEFT;
                    }
                }
                else {
                    // right
                    if (wy > -hx) {
                        side = sideHit.RIGHT;
                    }
                    // bottom
                    else {
                        side = sideHit.BOTTOM;
                    }
                }
                this.getCollidedEnemy(i);
                return true;
            }
        }
        // no intersection
        return false;
    }
    private boolean checkEnemyCollision2 () {
        for (int i = 0; i < map.bulletList.size(); i++) {
            // check goombas
            if (Rect.intersects(map.bulletList.get(i).bulletInfo.rect, this.marioInfo.rect)) {
                // determine which side of Mario was hit
                float wy = (this.marioInfo.rect.width() + map.bulletList.get(i).bulletInfo.rect.width()) * (map.bulletList.get(i).bulletInfo.rect.exactCenterY() - this.marioInfo.rect.exactCenterY());
                float hx = (this.marioInfo.rect.height() + map.bulletList.get(i).bulletInfo.rect.height()) * (map.bulletList.get(i).bulletInfo.rect.exactCenterX() - this.marioInfo.rect.exactCenterX());
                if (wy > hx) {
                    // top
                    if (wy > -hx) {
                        side = sideHit.TOP;
                    }
                    // left
                    else {
                        side = sideHit.LEFT;
                    }
                }
                else {
                    // right
                    if (wy > -hx) {
                        side = sideHit.RIGHT;
                    }
                    // bottom
                    else {
                        side = sideHit.BOTTOM;
                    }
                }
                this.getCollidedEnemy2(i);
                return true;
            }
        }
        // no intersection
        return false;
    }

    // shroom collision
    private boolean checkShroomCollision () {
        for (int i = 0; i < map.shroomObjects.size(); i++) {
            // check shrooms
            if (Rect.intersects(map.shroomObjects.get(i).rect, this.marioInfo.rect)) {
                collidedShroom = i;
                return true;
            }
        }
        // no intersection
        return false;
    }

    //coin collision
    private boolean checkCoinCollision () {
        for (int i = 0; i < map.coinObjects.size(); i++) {
            // check shrooms
            if (Rect.intersects(map.coinObjects.get(i).rect, this.marioInfo.rect)) {
                collidedCoin = i;
                return true;
            }
        }
        // no intersection
        return false;
    }

    // flower collision
    private boolean checkFlowerCollision () {
        for (int i = 0; i < map.flowerObjects.size(); i++) {
            // check shrooms
            if (Rect.intersects(map.flowerObjects.get(i).rect, this.marioInfo.rect)) {
                collidedFlower = i;
                return true;
            }
        }
        // no intersection
        return false;
    }

    // get collided object
    private void getCollidedEnemy (int index) {
        collidedGoomba = index;
    }

    private void getCollidedEnemy2 (int index2){
        collidedBullet = index2;
    }

    // gravity
    private void gravityCollide () {
        // increment
        marioInfo.rect.top -= jump;
        marioInfo.rect.bottom -= jump;

        // check enemy collision
        if (checkEnemyCollision()) {
            // killed enemy
            if (side == sideHit.TOP) {
                map.goombaList.remove(collidedGoomba);
                enemyKilled = true;
            }
        }
        if (checkEnemyCollision2()) {
            // killed enemy
            if (side == sideHit.TOP) {
                map.bulletList.remove(collidedBullet);
                enemyKilled = true;
            }
        }

        // check collision
        if (checkCollision(map.mapObjects, this)) {
            if (side == sideHit.BOTTOM) {
                marioInfo.rect.top += jump;
                marioInfo.rect.bottom += jump;
                jump = 0;
            }
            else {
                marioInfo.rect.top += jump;
                marioInfo.rect.bottom += jump;
                // reset jump variables
                jump = 0;
                initJump = true;
                this.scrollBitmaps(movement, movementPrev);
            }
        }

        // keep falling if no collision
        else if (!checkCollision(map.mapObjects, this)) {
            // slightly longer jump on hold todo causes l/r movement issue
            if (this.movement == Movement.JUMP && counter < 8) {
                jump += 1;
                counter++;
            }
            // normal jump
            int accelerator = 2;
            jump -= accelerator;
        }
    }

    // fireball collision
    private boolean checkFireBallCollision () {
        for (int i = 0; i < map.goombaList.size(); i++) {
            if (Rect.intersects(map.goombaList.get(i).goombaInfo.rect, fireBallInfo.rect)) {
                fireDestroyIndex = i;
                return true;
            }
        }
        return false;
    }

    // check death
    public boolean checkDeath () {
        if (this.marioInfo.rect.top > y) {
            return true;
        }
        return false;
    }

    // animation
    private void scrollBitmaps (Movement movement, Movement movementPrev) {
        switch (movement) {
            case LEFT:
                if (movingFlag) {
                    if (initJump) {
                        if (!gotShroom && !gotFlower) {
                            marioInfo.rect.top = marioInfo.rect.bottom - marioHeight;
                            if (flip % 2 == 0) {
                                marioInfo.bitmap = ImageFactory.smallMario.get(4);
                                flip++;
                            } else if (flip % 2 != 0) {
                                marioInfo.bitmap = ImageFactory.smallMario.get(2);
                                flip--;
                            }
                        }
                        else if (gotShroom) {
                            marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                            if (flip % 2 == 0) {
                                marioInfo.bitmap = ImageFactory.bigMario.get(4);
                                flip++;
                            } else if (flip % 2 != 0) {
                                marioInfo.bitmap = ImageFactory.bigMario.get(2);
                                flip--;
                            }
                        }
                        else if (gotFlower) {
                            marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                            if (flip % 2 == 0) {
                                marioInfo.bitmap = ImageFactory.fireMario.get(4);
                                flip++;
                            } else if (flip % 2 != 0) {
                                marioInfo.bitmap = ImageFactory.fireMario.get(2);
                                flip--;
                            }
                        }
                    }
                }
                else {
                    if (!gotShroom && !gotFlower) {
                        marioInfo.rect.top = marioInfo.rect.bottom - marioHeight;
                        marioInfo.bitmap = ImageFactory.smallMario.get(2);
                    }
                    else if (gotShroom) {
                        marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                        marioInfo.bitmap = ImageFactory.bigMario.get(2);
                    }
                    else if (gotFlower) {
                        marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                        marioInfo.bitmap = ImageFactory.fireMario.get(2);
                    }
                }
                break;
            case RIGHT:
                if (movingFlag) {
                    if (initJump) {
                        if (!gotShroom && !gotFlower) {
                            marioInfo.rect.top = marioInfo.rect.bottom - marioHeight;
                            if (flip % 2 == 0) {
                                marioInfo.bitmap = ImageFactory.smallMario.get(5);
                                flip++;
                            } else if (flip % 2 != 0) {
                                marioInfo.bitmap = ImageFactory.smallMario.get(3);
                                flip--;
                            }
                        }
                        else if (gotShroom) {
                            marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                            if (flip % 2 == 0) {
                                marioInfo.bitmap = ImageFactory.bigMario.get(5);
                                flip++;
                            } else if (flip % 2 != 0) {
                                marioInfo.bitmap = ImageFactory.bigMario.get(3);
                                flip--;
                            }
                        }
                        else if (gotFlower) {
                            marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                            if (flip % 2 == 0) {
                                marioInfo.bitmap = ImageFactory.fireMario.get(5);
                                flip++;
                            } else if (flip % 2 != 0) {
                                marioInfo.bitmap = ImageFactory.fireMario.get(3);
                                flip--;
                            }
                        }
                    }
                }
                else {
                    if (!gotShroom && !gotFlower) {
                        marioInfo.rect.top = marioInfo.rect.bottom - marioHeight;
                        marioInfo.bitmap = ImageFactory.smallMario.get(3);
                    }
                    else if (gotShroom) {
                        marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                        marioInfo.bitmap = ImageFactory.bigMario.get(3);
                    }
                    else if (gotFlower) {
                        marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                        marioInfo.bitmap = ImageFactory.fireMario.get(3);
                    }
                }
                break;
            case JUMP:
                if (!initJump) {
                    if (!gotShroom && !gotFlower) {
                        marioInfo.rect.top = marioInfo.rect.bottom - marioHeight;
                        if (movementPrev == Movement.LEFT) {
                            marioInfo.bitmap = ImageFactory.smallMario.get(0);
                        } else if (movementPrev == Movement.RIGHT) {
                            marioInfo.bitmap = ImageFactory.smallMario.get(1);
                        }
                    }
                    else if (gotShroom) {
                        marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                        if (movementPrev == Movement.LEFT) {
                            marioInfo.bitmap = ImageFactory.bigMario.get(0);
                        } else if (movementPrev == Movement.RIGHT) {
                            marioInfo.bitmap = ImageFactory.bigMario.get(1);
                        }
                    }
                    else if (gotFlower) {
                        marioInfo.rect.top = marioInfo.rect.bottom - marioBigHeight;
                        if (movementPrev == Movement.LEFT) {
                            marioInfo.bitmap = ImageFactory.fireMario.get(0);
                        } else if (movementPrev == Movement.RIGHT) {
                            marioInfo.bitmap = ImageFactory.fireMario.get(1);
                        }
                    }
                }
                break;
        }
    }

    public static void destroyMario (Mario mario) {
        mario = null;
    }
}
