package kylelam.mario;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Kyle on 5/24/2015.
 */
public class ScoreScreen implements TimeConscious {
    // class variables
    MarioSurfaceView view;
    public int score;

    private int x;
    private int y;

    public Rect scoreRect;

    // constructor
    public ScoreScreen (MarioSurfaceView view, int score) {
        this.view = view;
        this.score = score;
        this.x = view.getWidth();
        this.y = view.getHeight();
    }

    // tick
    public void tick (Canvas c) {
        this.draw(c);
    }

    // draw
    public void draw (Canvas c) {
        //draws score
        Paint scorePaint = new Paint();
        Paint scoreText = new Paint();
        scoreText.setTextSize(65);
        scorePaint.setTextSize(65);
        String scoreString = Integer.toString(score);
        c.drawText(scoreString, (float) (x - 810), y / 3, scorePaint);
        c.drawText(new String("Score: "), (float)(x - 1000), y / 3, scoreText);

        // instructions
        Paint paint = new Paint();
        paint.setAlpha(255);
        paint.setTextSize(50);
        c.drawText("Click to return to menu", x / 3, y - 400, paint);
        scoreRect = new Rect(0, 0, x, y);
    }
}
