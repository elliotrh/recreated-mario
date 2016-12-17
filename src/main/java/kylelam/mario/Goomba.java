package kylelam.mario;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Elliot Rhee on 5/12/2015.
 */
public class Goomba extends ImageFactory implements TimeConscious{
    //class variables
    public BitmapInfo goombaInfo;
    private int goombaVel;
    private ArrayList<BitmapInfo> mapObjects;

    // bitmap scroll
    private int flip = 0;

    // enum for side hit
    private enum sideHit {
        TOP, LEFT, RIGHT, BOTTOM
    }
    private sideHit side;

    // gravity
    private int fall = 0;

    //constructor
    public Goomba(MarioSurfaceView view, BitmapInfo goombaInfo, ArrayList<BitmapInfo> mapObjects){
        super(view);
        this.goombaInfo = goombaInfo;
        goombaVel = 5;
        this.mapObjects = mapObjects;

    }

    public void tick(Canvas c) {
        this.goombaInfo.rect.left -= goombaVel;
        this.goombaInfo.rect.right -= goombaVel;

        if(checkCollision(mapObjects, this)){
            if(side == sideHit.LEFT || side == sideHit.RIGHT) {
                this.goombaInfo.rect.left += goombaVel;
                this.goombaInfo.rect.right += goombaVel;
                goombaVel = -goombaVel;
            }
        }

        this.scrollBitmaps();
        this.gravityCollide();
        this.draw(c);
    }

    public void draw(Canvas c){
        Paint paint = new Paint();
        paint.setAlpha(255);
        c.drawBitmap(goombaInfo.bitmap, null, goombaInfo.rect, paint);
    }

    // collision with mapObjects
    public boolean checkCollision (ArrayList<BitmapInfo> objects, Goomba goomba) {
        for (int i = 0; i < objects.size(); i++) {
            if (Rect.intersects(objects.get(i).rect, goomba.goombaInfo.rect)) {
                // determine which side of Goomba was hit
                float wy = (goomba.goombaInfo.rect.width() + objects.get(i).rect.width()) * (objects.get(i).rect.exactCenterY() - goomba.goombaInfo.rect.exactCenterY());
                float hx = (goomba.goombaInfo.rect.height() + objects.get(i).rect.height()) * (objects.get(i).rect.exactCenterX() - goomba.goombaInfo.rect.exactCenterX());
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
                return true;
            }
        }
        // no intersection
        return false;
    }

    // gravity
    private void gravityCollide () {
        // increment
        goombaInfo.rect.top -= fall;
        goombaInfo.rect.bottom -= fall;

        // check collision
        if (checkCollision(mapObjects, this)) {
            goombaInfo.rect.top += fall;
            goombaInfo.rect.bottom += fall;
            // reset fall variables
            fall = 0;
        }
        int accelerator = 2;
        fall -= accelerator;
    }

    private void scrollBitmaps () {
        if (flip % 6 == 0) {
            this.goombaInfo.bitmap = ImageFactory.enemies.get(0);
            flip++;
        } else if (flip % 6 == 3) {
            this.goombaInfo.bitmap = ImageFactory.enemies.get(1);
            flip++;
        }
        else {
            flip++;
        }
    }
}
