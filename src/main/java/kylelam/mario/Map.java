package kylelam.mario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Kyle on 5/13/2015.
 */
public class Map implements TimeConscious {
    MarioSurfaceView view;
    Mario mario;

    // array of map object instances
    ArrayList<BitmapInfo> mapObjects;
    ArrayList<BitmapInfo> coinObjects;
    ArrayList<Goomba> goombaList;
    ArrayList<BulletBill> bulletList;
    ArrayList<BitmapInfo> shroomObjects;
    ArrayList<BitmapInfo> flowerObjects;

    // map background
    private Bitmap background;

    // screen res
    protected int x;
    protected int y;

    // one block
    private int side;

    // shroom velocity
    private int shroomVel;
    private int fall;

    // booleans
    public boolean alreadyGotShroom;
    public boolean alreadyGotFlower;

    // constructor
    public Map (MarioSurfaceView view, int x, int y, int side, ArrayList<BitmapInfo> mapObjects, ArrayList<Goomba> goombaList, ArrayList<BulletBill> bulletList, ArrayList<BitmapInfo> coinObjects, Bitmap background, BitmapInfo marioInfo) {
        this.view = view;
        this.x = x;
        this.y = y;
        this.side = side;
        this.mapObjects = mapObjects;
        this.background = background;
        this.shroomObjects = new ArrayList<>();
        this.flowerObjects = new ArrayList<>();
        this.coinObjects = coinObjects;
        shroomVel = 7;
        fall = 0;
        this.alreadyGotShroom = false;
        this.alreadyGotFlower = false;

        this.goombaList = goombaList;
        this.bulletList = bulletList;

        // make Mario according to map data (initial)
        this.mario = new Mario(this.view, this, this.x, this.y, marioInfo);
    }

    // return Mario
    public Mario returnMario () {
        return mario;
    }

    // return side
    public int returnSide () {
        return side;
    }

    // tick
    public void tick (Canvas c) {
        // add shrooms
        // check if question block is hit
        if (view.mario.hitQuestion && !alreadyGotShroom) {
            this.shroomAdd();
            alreadyGotShroom = true;
        }
        this.shroomMove();

        if(view.mario.hitQuestion2 && !alreadyGotFlower){
            this.flowerAdd();
            alreadyGotFlower = true;
        }

        this.draw(c);
    }

    // draw function
    public void draw (Canvas c) {
        // background draw
        Paint backPaint = new Paint();
        int alpha = 255;
        backPaint.setAlpha(alpha);
        Rect backDest = new Rect(0, 0, x, y);
        c.drawBitmap(background, null, backDest, backPaint);

        // object draw
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        for (int i = 0; i < mapObjects.size(); i++) {
            // check if map blocks are on screen
            if ((x - mapObjects.get(i).rect.left) > 0 && mapObjects.get(i).rect.right > 0) {
                c.drawBitmap(mapObjects.get(i).bitmap, null, mapObjects.get(i).rect, paint);
            }
        }

        for (int r = 0; r < coinObjects.size(); r++) {
            // check if map blocks are on screen
            if ((x - coinObjects.get(r).rect.left) > 0 && coinObjects.get(r).rect.right > 0) {
                c.drawBitmap(coinObjects.get(r).bitmap, null, coinObjects.get(r).rect, paint);
            }
        }

        if (alreadyGotShroom) {
            // shroom draw
            for (int k = 0; k < shroomObjects.size(); k++) {
                // check if map blocks are on screen
                if ((x - shroomObjects.get(k).rect.left) > 0 && shroomObjects.get(k).rect.right > 0) {
                    c.drawBitmap(shroomObjects.get(k).bitmap, null, shroomObjects.get(k).rect, paint);
                }
            }
        }

        if (alreadyGotFlower){
            // flower draw
            for (int u = 0; u < flowerObjects.size(); u++) {
                // check if map blocks are on screen
                if ((x - flowerObjects.get(u).rect.left) > 0 && flowerObjects.get(u).rect.right > 0) {
                    c.drawBitmap(flowerObjects.get(u).bitmap, null, flowerObjects.get(u).rect, paint);
                }
            }
        }
    }

    // add shroom
    private void shroomAdd () {
        //actual power up
        Rect dest = new Rect (view.mario.collidedObject.rect.left, view.mario.collidedObject.rect.top - side, view.mario.collidedObject.rect.right, view.mario.collidedObject.rect.bottom - side);
        BitmapInfo bitmapinfo = new BitmapInfo(ImageFactory.objects.get(5), dest);
        bitmapinfo.type = BlockType.SHROOM;
        this.shroomObjects.add(bitmapinfo);
    }

    // add flower
    private void flowerAdd () {
        //actual power up
        Rect dest = new Rect (view.mario.collidedObject.rect.left, view.mario.collidedObject.rect.top - side, view.mario.collidedObject.rect.right, view.mario.collidedObject.rect.bottom - side);
        BitmapInfo bitmapinfo = new BitmapInfo(ImageFactory.objects.get(4), dest);
        bitmapinfo.type = BlockType.FLOWER;
        this.flowerObjects.add(bitmapinfo);
    }

    private void shroomMove () {
        for (int i = 0; i < this.shroomObjects.size(); i++) {
            if (this.shroomObjects.get(i).type == BlockType.SHROOM) {
                this.shroomObjects.get(i).rect.left += shroomVel;
                this.shroomObjects.get(i).rect.right += shroomVel;

                // check collision
                if (checkShroomCollision(this.mapObjects, this.shroomObjects.get(i))) {
                    this.shroomObjects.get(i).rect.left -= shroomVel;
                    this.shroomObjects.get(i).rect.right -= shroomVel;
                    shroomVel = -shroomVel;
                }

                this.shroomGravityCollide(this.shroomObjects.get(i));
            }
        }
    }

    // collision with mapObjects
    public boolean checkShroomCollision (ArrayList<BitmapInfo> objects, BitmapInfo bitmapInfo) {
        for (int i = 0; i < objects.size(); i++) {
            if (Rect.intersects(objects.get(i).rect, bitmapInfo.rect)) {
                return true;
            }
        }
        // no intersection
        return false;
    }

    // gravity
    private void shroomGravityCollide (BitmapInfo shroomInfo) {
        // increment
        shroomInfo.rect.top -= fall;
        shroomInfo.rect.bottom -= fall;

        // check collision
        if (checkShroomCollision(this.mapObjects, shroomInfo)) {
            shroomInfo.rect.top += fall;
            shroomInfo.rect.bottom += fall;
            // reset fall variables
            fall = 0;
        }
        int accelerator = 2;
        fall -= accelerator;
    }

    // check if map is completely incremented (end of level)
    public boolean mapEnded () {
        for (int i = 0; i < mapObjects.size(); i++) {
            if (mapObjects.get(i).rect.right > x) {
                return false;
            }
        }
        return true;
    }
}
