package kylelam.mario;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by Kyle on 5/14/2015.
 */
public class BitmapInfo {
    // class variables
    public Rect rect;
    public Bitmap bitmap;

    // is end gate or not?
    public boolean endVar;

    // type of block
    public BlockType type;

    // constructor
    public BitmapInfo (Bitmap bitmap, Rect rect) {
        this.bitmap = bitmap;
        this.rect = rect;
        this.endVar = false;
    }
}
