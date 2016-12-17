package kylelam.mario;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class MarioRenderThread extends Thread {
    private final MarioSurfaceView view;
    private static final int FRAME_PERIOD = 5;
    public MarioRenderThread(MarioSurfaceView view){
        this.view = view;
    }
    public void run() {
        SurfaceHolder sh = view.getHolder();
        // Main game loop
        while (!Thread.interrupted()) {
            Canvas c = sh.lockCanvas(null);
            try {
                synchronized(sh) {
                    view.tick(c);
                }
            } catch(Exception e) {
            } finally {
                if (c != null) {
                    sh.unlockCanvasAndPost(c);
                }
            }
            try {
                Thread.sleep(FRAME_PERIOD);
            } catch(InterruptedException e) {
                return;
            }
        }
    }
}


