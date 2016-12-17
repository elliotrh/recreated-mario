package kylelam.mario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceView;

/**
 * Created by Elliot Rhee on 5/20/2015.
 */
public class Overlay implements TimeConscious {
    //class variables
    public int score;
    private int x;
    private int y;
    private int count;
    public int lives;
    private MarioSurfaceView view;

    //constructor
    public Overlay(MarioSurfaceView view, int x, int y, int lives){
        this.view = view;
        score = 2000;
        count = 0;
        this.x = x;
        this.y = y;
        this.lives = lives;
    }

    public void tick(Canvas c) {
        //decrement score every 10 ticks
        count += 1;
        if(count == 10) {
            if (score >= 1) {
                score -= 1;
            }
            count = 0;
        }
        //if enemy is killed adds 100 to score
        if(view.mario.enemyKilled){
            score += 100;
            view.mario.enemyKilled = false;
        }

        if(view.mario.gotCoin == true){
            score += 500;
            view.mario.gotCoin = false;
        }
        this.draw(c);
    }
    //draw function
    public void draw(Canvas c){
        //draws score
        Paint scorePaint = new Paint();
        Paint scoreText = new Paint();
        scoreText.setTextSize(65);
        scorePaint.setTextSize(65);
        String scoreString = Integer.toString(score);
        c.drawText(scoreString, (float) (x - 200), (float) (75), scorePaint);
        c.drawText(new String("Score: "), (float)(x - 390), (float)(75), scoreText);

        //draw lives counter
        Paint LivesText = new Paint();
        Paint LivesNum = new Paint();
        String livesString = Integer.toString(lives);
        LivesText.setTextSize(55);
        LivesNum.setTextSize(55);
        c.drawText(livesString, (float) (x - 160), (float)(125), LivesNum);
        c.drawText(new String("Lives: "), (float)(x - 350),(float)(125),LivesText);

        //draw move left button
        Path path = new Path();
        path.moveTo((float) (0), (float) 700);
        path.lineTo((float) (x * 0.075), (float) 760);
        path.moveTo((float) (x * 0.075), (float) 760);
        path.lineTo((float) (x * 0.075), (float) 620);
        path.moveTo((float) (x * 0.075), (float) 620);
        path.lineTo((float) (0), (float) 700);
        path.close();

        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.DKGRAY);
        paint.setAlpha(100);
        c.drawPath(path, paint);

        //draw move right
        Path path2 = new Path();
        path2.moveTo((float) (380), (float) 700);
        path2.lineTo((float) (x * 0.125), (float) 760);
        path2.moveTo((float) (x * 0.125), (float) 760);
        path2.lineTo((float) (x * 0.125), (float) 620);
        path2.moveTo((float) (x * 0.125), (float) 620);
        path2.lineTo((float) (380), (float) 700);
        path2.close();

        Paint paint2 = new Paint();
        paint2.setStrokeWidth(4);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setColor(Color.DKGRAY);
        paint2.setAlpha(100);
        c.drawPath(path2, paint2);

        //draw jump button
        Paint paint3 = new Paint();
        paint3.setColor(Color.BLUE);
        paint3.setStyle(Paint.Style.FILL_AND_STROKE);
        paint3.setStrokeWidth((float) 4.5);
        c.drawCircle((float) (x * .85), 660, 60, paint3);

        //draw fireball button
        Paint paint4 = new Paint();
        paint4.setColor(Color.RED);
        paint4.setStyle(Paint.Style.FILL_AND_STROKE);
        paint4.setStrokeWidth((float) 4.5);
        c.drawCircle((float)(x * .75), 660, 60, paint4);

    }

}
