package survive.environment;

import java.util.Random;
import org.lwjgl.opengl.GL11;
import survive.GameHolder;

/**
 *
 * @author Jyri Saukkonen
 */
public class Rain {

    public float density = 1;
    public float targetDensity = 1;
    public int rainTexture = 1;
    public int snowTexture = 2;

    public float animX = 0;
    public float animY = 0;
    public float animXSuunta;
    public float animYSuunta;

    float possibility = 0;
    Random rand = new Random();
    int tickTimer = 0;

    public void drawAndTickRain() {

        tickTimer++;
        //System.out.println(tickTimer);
        if (tickTimer > 1500) {
            /*int y = (int) (rand.nextFloat() * 52);
             int x = (int) (rand.nextFloat() * 30);
             GameHolder.environmentHolder.lightMap.lightPoints[x][y] = 1;
             GameHolder.environmentHolder.lightMap.updateLightmap(true);*/
            tickTimer = 0;
            possibility = rand.nextFloat();
            if (possibility > 0.7f) {
                targetDensity = rand.nextFloat() + 0.4f;
            } else {
                targetDensity = 0;
            }
        }
        if (targetDensity > 1) {
            targetDensity = 1;
        }

        if (density < targetDensity) {
            density += 0.01f;
        } else {
            density -= 0.01f;
        }

        if (density > 0) {
            animate();
            draw();
            animXSuunta = GameHolder.environmentHolder.wind.xPower;
            animYSuunta = GameHolder.environmentHolder.wind.yPower;
        }
    }

    public void animate() {
        animY -= animYSuunta;
        animX += animXSuunta;
        if (animY < -512) {
            animY = 0;
        }
        if (animY > 512) {
            animY = 0;
        }
        if (animX > 512) {
            animX = 0;
        }
        if (animX < 0) {
            animX = 512;
        }

    }

    public void draw() {
        float temperature = GameHolder.environmentHolder.temperature.temperature;
        float time = GameHolder.environmentHolder.timeOfDay.time;

        if (temperature <= 2) {
            GameHolder.textureHandler.textures.get(3).bind();

            GL11.glBegin(GL11.GL_QUADS);

            GL11.glColor4f(1, 1, 1, 1);

            for (int y = 0; y < 3; y++) {
                for (int x = -1; x < 3; x++) {

                    if (temperature > 0) {
                        GL11.glColor4f(1, 1, 1, density - 0.2f - (1 * (temperature / 2)));
                    } else {
                        GL11.glColor4f(1, 1, 1, density - 0.2f);
                    }
                    GL11.glTexCoord2f(0, 0);
                    GL11.glVertex2f(-10 + animX + x * 512, animY - 10 + y * 512);

                    GL11.glTexCoord2f(1, 0);
                    GL11.glVertex2f(animX + 512 - 10 + x * 512, animY - 10 + y * 512);

                    GL11.glTexCoord2f(1, 1);
                    GL11.glVertex2f(animX + 512 - 10 + x * 512, animY + 512 - 10 + y * 512);

                    GL11.glTexCoord2f(0, 1);
                    GL11.glVertex2f(animX - 10 + x * 512, animY + 512 - 10 + y * 512);
                }
            }
            GL11.glEnd();
            GL11.glPopMatrix();
        }
        if (temperature >= 0) {
            GameHolder.textureHandler.textures.get(2).bind();

            GL11.glBegin(GL11.GL_QUADS);

            GL11.glColor4f(1, 1, 1, 1);
            for (int y = 0; y < 3; y++) {
                for (int x = -1; x < 3; x++) {

                    if (temperature < 1) {
                        GL11.glColor4f(1, 1, 1, density - 0.2f - (1 - temperature));
                    } else {
                        GL11.glColor4f(1, 1, 1, density - 0.2f);
                    }
                    GL11.glTexCoord2f(0, 0);
                    GL11.glVertex2f(-10 + animX + x * 512, animY - 10 + y * 512);

                    GL11.glTexCoord2f(1, 0);
                    GL11.glVertex2f(animX + 512 - 10 + x * 512, animY - 10 + y * 512);

                    GL11.glTexCoord2f(1, 1);
                    GL11.glVertex2f(animX + 512 - 10 + x * 512, animY + 512 - 10 + y * 512);

                    GL11.glTexCoord2f(0, 1);
                    GL11.glVertex2f(animX - 10 + x * 512, animY + 512 - 10 + y * 512);
                }
            }
            GL11.glEnd();

            GL11.glPopMatrix();
        }
    }
}
