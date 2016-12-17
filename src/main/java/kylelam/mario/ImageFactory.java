package kylelam.mario;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class ImageFactory {
    // class variables
    protected final MarioSurfaceView view;

    // for storing bitmaps
    protected static ArrayList<Bitmap> smallMario;
    protected static ArrayList<Bitmap> bigMario;
    protected static ArrayList<Bitmap> fireMario;
    protected static ArrayList<Bitmap> objects;
    protected static ArrayList<Bitmap> enemies;
    protected ArrayList<Bitmap> terrain;

    // map backgrounds
    protected ArrayList<Bitmap> backgrounds;

    // constructor
    public ImageFactory (MarioSurfaceView view) {
        this.view = view;

        // initialize ArrayLists
        smallMario = new ArrayList<>();
        bigMario = new ArrayList<>();
        fireMario = new ArrayList<>();
        objects = new ArrayList<>();
        enemies = new ArrayList<>();
        terrain = new ArrayList<>();
        backgrounds = new ArrayList<>();

        // import bitmaps
        BitmapFactory.Options options = new BitmapFactory.Options();
        // small Mario
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.smalljumpleft, options);
        smallMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.smalljumpright, options);
        smallMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.smalllookleft, options);
        smallMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.smalllookright, options);
        smallMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallmoveleft, options);
        smallMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.smallmoveright, options);
        smallMario.add(bitmap);

        // big Mario
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigjumpleft, options);
        bigMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigjumpright, options);
        bigMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.biglookleft, options);
        bigMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.biglookright, options);
        bigMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigmoveleft1, options);
        bigMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigmoveright1, options);
        bigMario.add(bitmap);

        //fire Mario
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.firejumpleft, options);
        fireMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.firejumpright, options);
        fireMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.firelookleft, options);
        fireMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.firelookright, options);
        fireMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemoveleft1, options);
        fireMario.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.firemoveright1, options);
        fireMario.add(bitmap);

        // objects
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.question1, options);
        objects.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.answer, options);
        objects.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.breakblock, options);
        objects.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.pipetop, options);
        objects.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.flower1, options);
        objects.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.shroom, options);
        objects.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.fireball, options);
        objects.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.gate, options);
        objects.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bigcoin, options);
        objects.add(bitmap);

        // enemies
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.goomba1left, options);
        enemies.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.goomba2left, options);
        enemies.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bulletbill, options);
        enemies.add(bitmap);

        // terrain
        // map 1
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.ggrass, options);
        terrain.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.gdirt, options);
        terrain.add(bitmap);

        // map 2
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.mgrass, options);
        terrain.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.mdirt, options);
        terrain.add(bitmap);

        // map 3
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.skydirt, options);
        terrain.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.skyup, options);
        terrain.add(bitmap);

        // backgrounds
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.sky, options);
        backgrounds.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.map1, options);
        backgrounds.add(bitmap);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.map2, options);
        backgrounds.add(bitmap);
    }
}
