package kylelam.mario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Kyle on 5/19/2015.
 */
public class Menu extends ImageFactory implements TimeConscious {
    // class variables
    Bitmap menuPic;

    // screen res
    private int x;
    private int y;

    // click areas
    public Rect playRect;
    public Rect setRect;
    public Rect exitRect;
    public Rect backRect;
    public Rect livesRect;
    public Rect mapRect;

    // enum for menu selection
    public enum Select {
        MAIN, PLAY, SETTINGS, EXIT
    }
    public Select selectVar;

    // constructor
    public Menu (MarioSurfaceView view) {
        super(view);
        x = view.getWidth();
        y = view.getHeight();
        menuPic = backgrounds.get(0);
        this.selectVar = Select.MAIN;
    }

    public void tick (Canvas c) {
        if (selectVar == Select.MAIN) {
            this.draw(c);
            this.drawDef(c);
        }
        else if (selectVar == Select.SETTINGS) {
            this.draw(c);
            this.drawSet(c);
        }
        else if (selectVar == Select.EXIT) {
            // foo
        }
    }

    public void draw (Canvas c) {
        Paint paint = new Paint();
        paint.setAlpha(255);
        Rect rect = new Rect(0, 0, x, y);
        c.drawBitmap(menuPic, null, rect, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(75);
        c.drawText("Super Copy World", x / 3, 150, paint);

        paint.setTextSize(45);
        c.drawText("By Kyle Lam and Elliot Rhee", x / 3, 200, paint);
    }

    // default menu
    public void drawDef (Canvas c) {
        Paint paint = new Paint();
        paint.setAlpha(255);
        paint.setTextSize(50);
        c.drawText("Play Game", 200 + x / 3, 500, paint);
        playRect = new Rect (0, 450, x, 550);

        c.drawText("Settings", 200 + x / 3, 650, paint);
        setRect = new Rect (0, 600, x, 700);

        c.drawText("Exit", 200 + x / 3, 800, paint);
        exitRect = new Rect (0, 750, x, 850);
    }

    // settings menu
    public void drawSet (Canvas c) {
        Paint paint = new Paint();
        paint.setAlpha(255);
        paint.setTextSize(50);
        String livesString = Integer.toString(view.liveCounter);
        String mapString = Integer.toString(view.mapSelect);
        // back button
        c.drawText("Back", 200 + x / 3, 800, paint);
        backRect = new Rect (0, 750, x, 850);

        // lives button
        c.drawText("Lives (Tap to change): ", x / 3, 650, paint);
        c.drawText(livesString, 2 * x / 3, 650, paint);
        livesRect = new Rect (0, 600, x, 700);

        // map select button
        c.drawText("Level (Tap to change): ", x / 3, 500, paint);
        c.drawText(mapString, 2 * x / 3, 500, paint);
        mapRect = new Rect (0, 450, x, 550);
    }
}
