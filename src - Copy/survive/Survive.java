package survive;

import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import survive.Objects.GameObject;

public class Survive {

    long lastFrame;
    int fps;
    long lastFPS;

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(1366, 768));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        initGL(); // init OpenGL
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        int tickTimer = 0;
        while (!Display.isCloseRequested()) {
            int delta = getDelta();

            if (tickTimer == 4){
                tickLogic();
                tickTimer = 0;
            } else {
                tickTimer++;
            }
            update(delta);
            renderGL();

            Display.update();
            Display.sync(60); // cap fps to 60fps
        }

        Display.destroy();
    }

    public void initVariables() {

    }

    public void update(int delta) {
        updateFPS(); // update FPS Counter
    }

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 1366, 0, 768, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    }
    
    public void tickLogic(){
        GameHolder.environmentHolder.tick();
    }

    public void renderGL() {
        ArrayList<Texture> textures = GameHolder.textureHandler.getTexturelist();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glPushMatrix();

        GameHolder.worldHolder.background.drawAndTickBackground();        
        GameHolder.worldHolder.objects.drawAndTickObjects();
        
        GameHolder.environmentHolder.draw();
        GameHolder.player.tick();

    }

    public static void main(String[] argv) {
        Survive survive = new Survive();

        
        survive.start();
    }
}
