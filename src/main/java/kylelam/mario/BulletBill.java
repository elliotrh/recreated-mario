package kylelam.mario;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Elliot Rhee on 5/12/2015.
 */
public class BulletBill extends ImageFactory implements TimeConscious {
    //class variables
    public BitmapInfo bulletInfo;
    private int bulletVel;

    //constructor
    public BulletBill(MarioSurfaceView view, BitmapInfo bulletInfo){
        super(view);
        bulletVel = 15;
        this.bulletInfo = bulletInfo;
    }

    public void tick(Canvas c) {
        this.bulletInfo.rect.left -= bulletVel;
        this.bulletInfo.rect.right -= bulletVel;
        this.draw(c);
    }

    public void draw(Canvas c){
        Paint paint = new Paint();
        paint.setAlpha(255);
        c.drawBitmap(bulletInfo.bitmap, null, bulletInfo.rect, paint);
    }
}
