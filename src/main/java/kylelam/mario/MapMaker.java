package kylelam.mario;



import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Kyle on 5/13/2015.
 */
public class MapMaker extends ImageFactory {
    // class variables
    Map map;

    // screen res
    private int x;
    private int y;

    // block size
    private int side;

    // array for map with every object instance
    private ArrayList<BitmapInfo> mapObjects;
    private ArrayList<BitmapInfo> goombaObjects;
    private ArrayList<BitmapInfo> bulletObjects;
    private ArrayList<BitmapInfo> coinObjects;
    private ArrayList<Goomba> goombaList;
    private ArrayList<BulletBill> bulletList;

    private Bitmap background;

    // rect array
    private ArrayList<Rect> Grid;

    // bitmap Rect coordinates for BitmapInfo
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    private Rect marioDest;

    // counter
    private int counter;

    // constructor
    public MapMaker (MarioSurfaceView view) {
        super(view);
        this.mapObjects = new ArrayList<>();
        this.goombaObjects = new ArrayList<>();
        this.bulletObjects = new ArrayList<>();
        this.coinObjects = new ArrayList<>();
        this.goombaList = new ArrayList<>();
        this.bulletList = new ArrayList<>();
        this.Grid = new ArrayList<>();
        this.x = view.getWidth();
        this.y = view.getHeight();
        this.side = this.x / 32;
        this.x1 = 0;
        this.x2 = this.side;
        this.y1 = this.y - this.side;
        this.y2 = this.y;
        this.counter = 0;
    }

    // turn bitmaps into BitmapInfo
    public Map make (int mapSelect) {
        // Mario bitmap to put into all maps
        Bitmap bitMario = smallMario.get(3);

        // generate rect array
        int i = 0;
        while (counter < 20) {
            Rect dest = new Rect (x1, y1, x2, y2);
            this.Grid.add(dest);

            this.x1 += this.side;
            this.x2 += this.side;

            i++;
            if (i > 159) {
                this.y1 -= this.side;
                this.y2 -= this.side;
                this.x1 = 0;
                this.x2 = this.side;
                counter++;
                i = 0;
            }
        }

        int row1 = 160 - 1;
        int row2 = 2 * 160 - 1;
        int row3 = 3 * 160 - 1;
        int row4 = 4 * 160 - 1;
        int row5 = 5 * 160 - 1;
        int row6 = 6 * 160 - 1;
        int row7 = 7 * 160 - 1;
        int row8 = 8 * 160 - 1;
        int row9 = 9 * 160 - 1;
        int row10 = 10 * 160 -1;

        int screen1 = 31;
        int screen2 = 2 * 32 - 1;
        int screen3 = 3 * 32 - 1;
        int screen4 = 4 * 32 - 1;
        int screen5 = 5 * 32 - 1;

        // case to construct map depending on map flag
        switch (mapSelect) {
            // for map 1
            case 1:
                // empty object array
                this.resetAll();
                // for dirt (2 rows)
                int k = 0;
                while (k < 320) {
                    Rect dest = this.Grid.get(k);
                    BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(1), dest);
                    this.mapObjects.add(bitmapinfo);

                    k++;
                }
                // for grass (1 row)
                int l = 320;
                while (l < 480) {
                    Rect dest = this.Grid.get(l);
                    BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(0), dest);
                    this.mapObjects.add(bitmapinfo);

                    l++;
                }

                i = 0;
                int numBlocks = 5;
                int nextLine = 480;

                this.y1 = this.y - 4 * this.side;
                this.y2 = this.y - 3 * this.side;

                // random block of stuff
                while (i < 5) {
                    Rect dest = this.Grid.get(39 + i + nextLine);
                    BitmapInfo bitmapinfo = new BitmapInfo(objects.get(2), dest);
                    this.mapObjects.add(bitmapinfo);

                    for (int j = 1; j < numBlocks; j++) {
                        dest = this.Grid.get(39 + i + j + nextLine);
                        bitmapinfo = new BitmapInfo(objects.get(2), dest);
                        this.mapObjects.add(bitmapinfo);
                    }

                    nextLine += 160;
                    numBlocks--;
                    i++;
                }

                // some bricks and a powerup
                nextLine = 6 * 160 - 1;
                Rect dest2 = this.Grid.get(nextLine + 25);
                BitmapInfo bitmapinfo2 = new BitmapInfo(objects.get(2), dest2);
                bitmapinfo2.type = BlockType.BRICK;
                this.mapObjects.add(bitmapinfo2);
                dest2 = this.Grid.get(nextLine + 26);
                bitmapinfo2 = new BitmapInfo(objects.get(0), dest2);
                bitmapinfo2.type = BlockType.QUESTION;
                this.mapObjects.add(bitmapinfo2);
                dest2 = this.Grid.get(nextLine + 27);
                bitmapinfo2 = new BitmapInfo(objects.get(2), dest2);
                bitmapinfo2.type = BlockType.BRICK;
                this.mapObjects.add(bitmapinfo2);

                //another set of bricks and a powerup
                dest2 = this.Grid.get(row8 + screen1 + 19);
                bitmapinfo2 = new BitmapInfo(objects.get(2), dest2);
                bitmapinfo2.type = BlockType.BRICK;
                this.mapObjects.add(bitmapinfo2);
                dest2 = this.Grid.get(row8 + screen1 + 20);
                bitmapinfo2 = new BitmapInfo(objects.get(2), dest2);
                bitmapinfo2.type = BlockType.BRICK;
                this.mapObjects.add(bitmapinfo2);
                dest2 = this.Grid.get(row8 + screen1 + 21);
                bitmapinfo2 = new BitmapInfo(objects.get(2), dest2);
                bitmapinfo2.type = BlockType.BRICK;
                this.mapObjects.add(bitmapinfo2);
                dest2 = this.Grid.get(row8 + screen1 + 22);
                bitmapinfo2 = new BitmapInfo(objects.get(2), dest2);
                bitmapinfo2.type = BlockType.BRICK;
                this.mapObjects.add(bitmapinfo2);
                dest2 = this.Grid.get(row8 + screen1 + 23);
                bitmapinfo2 = new BitmapInfo(objects.get(2), dest2);
                bitmapinfo2.type = BlockType.BRICK;
                this.mapObjects.add(bitmapinfo2);
                dest2 = this.Grid.get(row10 + screen1 + 21 + 160);
                bitmapinfo2 = new BitmapInfo(objects.get(0), dest2);
                bitmapinfo2.type = BlockType.QUESTION;
                this.mapObjects.add(bitmapinfo2);
                //coin
                dest2 = this.Grid.get(row10 + screen1 + 21);
                bitmapinfo2 = new BitmapInfo(objects.get(8), dest2);
                this.coinObjects.add(bitmapinfo2);

                //add pipe
                Rect pipe = new Rect(this.Grid.get(row4 + screen2 + 5).left, this.Grid.get(row4 + screen2 + 5).top, this.Grid.get(row3 + screen2 + 7).right, this.Grid.get(row3 + screen2 + 7).bottom);
                BitmapInfo pipeInfo = new BitmapInfo(objects.get(3), pipe);
                this.mapObjects.add(pipeInfo);

                // make enemies
                Rect enemyRect = this.Grid.get(480 + 36 - 1);
                BitmapInfo enemyInfo = new BitmapInfo(enemies.get(0), enemyRect);
                this.goombaObjects.add(enemyInfo);

                enemyRect = this.Grid.get(nextLine + 160 + 26);
                enemyInfo = new BitmapInfo(enemies.get(0), enemyRect);
                this.goombaObjects.add(enemyInfo);

                enemyRect = this.Grid.get(row5 + screen2 + 7);
                enemyInfo = new BitmapInfo(enemies.get(0), enemyRect);
                this.goombaObjects.add(enemyInfo);

                enemyRect = this.Grid.get(row3 + screen4 - 3);
                enemyInfo = new BitmapInfo(enemies.get(0), enemyRect);
                this.goombaObjects.add(enemyInfo);

                enemyRect = this.Grid.get(row4 + screen4);
                enemyInfo = new BitmapInfo(enemies.get(2), enemyRect);
                this.bulletObjects.add(enemyInfo);

                enemyRect = this.Grid.get(530);
                enemyInfo = new BitmapInfo(enemies.get(2), enemyRect);
                this.bulletObjects.add(enemyInfo);

                // end of level
                Rect endDest = new Rect(this.Grid.get(1280 + 151 - 1).left, this.Grid.get(1280 + 151 - 1).top, this.Grid.get(480 + 153 - 1).right, this.Grid.get(480 + 153 - 1).bottom);
                BitmapInfo endInfo = new BitmapInfo(objects.get(7), endDest);
                endInfo.endVar = true;
                this.mapObjects.add(endInfo);

                // set Mario's location
                int mx1 = this.side * 2;
                int my1 = this.y - (this.side * 3 + (int) (this.side * 1.3));
                int mx2 = this.side * 3;
                int my2 = this.y - (this.side * 3);
                marioDest = new Rect(mx1, my1, mx2, my2);

                // set background
                background = backgrounds.get(1);

                break;

            // for map 2
            case 2:
                // empty object array
                this.resetAll();
                // for dirt (2 rows)
                k = 0;
                while (k < 320) {
                    Rect dest = this.Grid.get(k);
                    BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(3), dest);
                    this.mapObjects.add(bitmapinfo);

                    k++;
                }
                // for grass (1 row)
                l = 320;
                while (l < 480) {
                    Rect dest = this.Grid.get(l);
                    BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(2), dest);
                    this.mapObjects.add(bitmapinfo);

                    l++;
                }

                //creates row of grass in middle of map
                int m = row10 + screen2 - 15;
                while(m < row10 + 160 - 15){
                    Rect dest = this.Grid.get(m);
                    BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(2), dest);
                    this.mapObjects.add(bitmapinfo);

                    m++;
                }
                i = 0;
                numBlocks = 6;
                nextLine = 480;

                this.y1 = this.y - 4 * this.side;
                this.y2 = this.y - 3 * this.side;

                // random block of stuff
                while (i < 6) {
                    Rect dest = this.Grid.get(39 + i + nextLine);
                    BitmapInfo bitmapinfo = new BitmapInfo(objects.get(2), dest);
                    this.mapObjects.add(bitmapinfo);

                    for (int j = 1; j < numBlocks; j++) {
                        dest = this.Grid.get(39 + i + j + nextLine);
                        bitmapinfo = new BitmapInfo(objects.get(2), dest);
                        this.mapObjects.add(bitmapinfo);
                    }

                    nextLine += 160;
                    numBlocks--;
                    i++;
                }

                //make item blocks
                dest2 = this.Grid.get(row10 + 640 + screen2 - 14);
                bitmapinfo2 = new BitmapInfo(objects.get(0), dest2);
                bitmapinfo2.type = BlockType.QUESTION;
                this.mapObjects.add(bitmapinfo2);

                dest2 = this.Grid.get(row6 + screen2 - 14);
                bitmapinfo2 = new BitmapInfo(objects.get(0), dest2);
                bitmapinfo2.type = BlockType.QUESTION;
                this.mapObjects.add(bitmapinfo2);

                //add pipe
                pipe = new Rect(this.Grid.get(row4 + screen2 + 5).left, this.Grid.get(row4 + screen2 + 5).top, this.Grid.get(row3 + screen2 + 7).right, this.Grid.get(row3 + screen2 + 7).bottom);
                pipeInfo = new BitmapInfo(objects.get(3), pipe);
                this.mapObjects.add(pipeInfo);

                // make enemies
                enemyRect = this.Grid.get(row5 + screen2 + 7);
                enemyInfo = new BitmapInfo(enemies.get(0), enemyRect);
                this.goombaObjects.add(enemyInfo);

                enemyRect = this.Grid.get(row3 + screen4 - 3);
                enemyInfo = new BitmapInfo(enemies.get(0), enemyRect);
                this.goombaObjects.add(enemyInfo);

                enemyRect = this.Grid.get(row10 + 160 + screen4);
                enemyInfo = new BitmapInfo(enemies.get(0), enemyRect);
                this.goombaObjects.add(enemyInfo);

                enemyRect = this.Grid.get(row5 + screen3 + 7);
                enemyInfo = new BitmapInfo(enemies.get(2), enemyRect);
                this.bulletObjects.add(enemyInfo);

                enemyRect = this.Grid.get(row10 + 480 + screen4 + 7);
                enemyInfo = new BitmapInfo(enemies.get(2), enemyRect);
                this.bulletObjects.add(enemyInfo);

                enemyRect = this.Grid.get(row10 + 480 + screen3);
                enemyInfo = new BitmapInfo(enemies.get(2), enemyRect);
                this.bulletObjects.add(enemyInfo);

                //coin
                dest2 = this.Grid.get(row10 + 480 + screen4 + 20);
                bitmapinfo2 = new BitmapInfo(objects.get(8), dest2);
                this.coinObjects.add(bitmapinfo2);


                // end of level
                endDest = new Rect(this.Grid.get(1280 + 151 - 1).left, this.Grid.get(1280 + 151 - 1).top, this.Grid.get(480 + 153 - 1).right, this.Grid.get(480 + 153 - 1).bottom);
                endInfo = new BitmapInfo(objects.get(7), endDest);
                endInfo.endVar = true;
                this.mapObjects.add(endInfo);

                // set Mario's location
                mx1 = this.side * 2;
                my1 = this.y - (this.side * 3 + (int) (this.side * 1.3));
                mx2 = this.side * 3;
                my2 = this.y - (this.side * 3);
                marioDest = new Rect(mx1, my1, mx2, my2);

                // set background
                background = backgrounds.get(2);

                break;

            // for map 3
            case 3:
                // empty object array
                this.resetAll();

                // layer of grass for almost one screen length
                for (l = 320; l < row2 + (screen1 - 10); l++) {
                    Rect dest = this.Grid.get(l);
                    BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(5), dest);
                    this.mapObjects.add(bitmapinfo);
                }

                //one row of blocks with nothing beneath
                for(m = row3 + (screen1 - 7); m < row3 + screen1 - 2; m++){
                    dest2 = this.Grid.get(m);
                    bitmapinfo2 = new BitmapInfo(objects.get(2), dest2);
                    bitmapinfo2.type = BlockType.BRICK;
                    this.mapObjects.add(bitmapinfo2);
                }

                //add pipe on row of blocks
                pipe = new Rect(this.Grid.get(row5 + screen1 - 5).left, this.Grid.get(row5 + screen1 - 5).top, this.Grid.get(row4 + screen1 - 3).right, this.Grid.get(row4 + screen1 - 3).bottom);
                pipeInfo = new BitmapInfo(objects.get(3), pipe);
                this.mapObjects.add(pipeInfo);
                //power up above pipe
                dest2 = this.Grid.get(row9 + screen1 - 4);
                bitmapinfo2 = new BitmapInfo(objects.get(0), dest2);
                bitmapinfo2.type = BlockType.QUESTION;
                this.mapObjects.add(bitmapinfo2);

                //another layer of grass on screen 2
                for(int n = 320 + screen1; n < row2 + screen2 ; n++){
                    Rect dest = this.Grid.get(n);
                    BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(5), dest);
                    this.mapObjects.add(bitmapinfo);
                }

                //goomba
                enemyRect = this.Grid.get(row3 + screen2 - 5);
                enemyInfo = new BitmapInfo(enemies.get(0), enemyRect);
                this.goombaObjects.add(enemyInfo);

                //creates stair of blocks 7 high on screen2
                nextLine = row3;
                numBlocks = 7;
                while (i < 7) {
                    Rect dest = this.Grid.get(nextLine + screen2 + 5 + i);
                    BitmapInfo bitmapinfo = new BitmapInfo(objects.get(2), dest);
                    this.mapObjects.add(bitmapinfo);

                    for (int j = 1; j < numBlocks; j++) {
                        dest = this.Grid.get(i + j + nextLine + screen2 + 5);
                        bitmapinfo = new BitmapInfo(objects.get(2), dest);
                        this.mapObjects.add(bitmapinfo);
                    }
                    nextLine += 160;
                    numBlocks--;
                    i++;
                }

                //layer of grass after stairs 2 rows higher
                for(int n = row5 + screen2 + 15; n < row5 + screen3; n++){
                    Rect dest = this.Grid.get(n);
                    BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(5), dest);
                    this.mapObjects.add(bitmapinfo);
                }

                //stairs of grass
                counter = 0;
                while(counter < 4){

                    for(i = 0; i < 4; i++){
                        Rect dest = this.Grid.get(i + row2 + screen3 + 5 + 320 * counter + 6 * counter);
                        BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(5), dest);
                        this.mapObjects.add(bitmapinfo);
                    }
                    counter += 1;
                    i = 0;
                }

                //coin
                dest2 = this.Grid.get(row10 + 160 + screen4);
                bitmapinfo2 = new BitmapInfo(objects.get(8), dest2);
                this.coinObjects.add(bitmapinfo2);

                //bullet bills for each stair of grass //todo testing memory issues
                for (i = 0; i < 4; i++){
                    int row = i * 320 + 480;
                    enemyRect = this.Grid.get(row + screen3 +7 + 8* i);
                    enemyInfo = new BitmapInfo(enemies.get(2), enemyRect);
                    this.bulletObjects.add(enemyInfo);
                }

                enemyRect = this.Grid.get(row10 + screen4 + 5);
                enemyInfo = new BitmapInfo(enemies.get(2), enemyRect);
                this.bulletObjects.add(enemyInfo);

                //final layer of grass
                for (i = row2 + screen4; i < row2 + screen5; i++) {
                    Rect dest = this.Grid.get(i);
                    BitmapInfo bitmapinfo = new BitmapInfo(terrain.get(5), dest);
                    this.mapObjects.add(bitmapinfo);
                }

                // end of level
                endDest = new Rect(this.Grid.get(1280 + 151 - 1).left, this.Grid.get(1280 + 151 - 1).top, this.Grid.get(480 + 153 - 1).right, this.Grid.get(480 + 153 - 1).bottom);
                endInfo = new BitmapInfo(objects.get(7), endDest);
                endInfo.endVar = true;
                this.mapObjects.add(endInfo);

                // set Mario's location
                mx1 = this.side * 2;
                my1 = this.y - (this.side * 3 + (int) (this.side * 1.3));
                mx2 = this.side * 3;
                my2 = this.y - (this.side * 3);
                marioDest = new Rect(mx1, my1, mx2, my2);

                // set background
                background = backgrounds.get(0);

                break;
        }
        // make Mario BitmapInfo
        BitmapInfo marioInfo = new BitmapInfo(bitMario, marioDest);

        // make Goombas
        for (int a = 0; a < goombaObjects.size(); a++) {
            Goomba goomba = new Goomba(this.view, goombaObjects.get(a), this.mapObjects);
            this.goombaList.add(goomba);
        }

        //make bullet Bills
        for (int b = 0; b < bulletObjects.size(); b++) {
            BulletBill bullet = new BulletBill(this.view, bulletObjects.get(b));
            this.bulletList.add(bullet);
        }

        // make Map and put array of objects into it
        map = new Map(view, this.x, this.y, this.side, this.mapObjects, this.goombaList, this.bulletList, this.coinObjects, background, marioInfo);
        return map;
    }

    // reset all parameters
    public void resetAll () {
        // array for map with every object instance
        mapObjects.clear();
        goombaObjects.clear();
        bulletObjects.clear();
        goombaList.clear();
        bulletList.clear();
    }
}
