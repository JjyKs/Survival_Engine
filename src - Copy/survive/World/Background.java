package survive.World;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import survive.GameHolder;
import survive.environment.Rain;
import survive.environment.Temperature;

/**
 *
 * @author Jyri Saukkonen
 */
public class Background {

    int texture = 1;
    public float snowLayer1 = 0;
    public float snowLayer2 = 0;
    public float snowLayer3 = 0;

    public void drawAndTickBackground() {
        Color.white.bind();
        GameHolder.textureHandler.textures.get(texture).bind();
        float time = GameHolder.environmentHolder.timeOfDay.time;
        Temperature temperature = GameHolder.environmentHolder.temperature;
        Rain rain = GameHolder.environmentHolder.rain;

        //Jos pakkasta ja lunta sataa
        
        if (temperature.temperature <= 0 && rain.density > 0) {

            if (snowLayer1 < 1 && rain.density > 0.3f) {
               
                snowLayer1 += 0.00125f * rain.density;
            }
            if (snowLayer1 > 0.9f && rain.density > 0.5f && snowLayer2 <= 1) {
                snowLayer2 += 0.00125f * rain.density;
            }
            if (snowLayer2 > 0.9f && rain.density > 0.8f && snowLayer3 <= 1) {
                snowLayer3 += 0.00250f * rain.density;
            }
        }
        

        //Jos yli nollan niin sulatetaan lunta.
        if (temperature.temperature > 0) {
            if (snowLayer1 > 0 && snowLayer2 < 0.3f) {
                snowLayer1 -= 0.00075f * temperature.temperature;
            }
            if (snowLayer2 > 0f && snowLayer3 < 0.3f) {
                snowLayer2 -= 0.00075f * temperature.temperature;
            }
            if (snowLayer3 > 0f) {
                snowLayer3 -= 0.00150f * temperature.temperature;
            }
        }

        GL11.glColor4f(1, 1, 1, 1f);
        GL11.glBegin(GL11.GL_QUADS);
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 11; x++) {
                GL11.glColor4f(1, 1, 1, 1);
                GL11.glTexCoord2f(0.0f, 0);
                GL11.glVertex2f(x * 128, y * 128);
                GL11.glTexCoord2f(0.25f, 0);
                GL11.glVertex2f(x * 128 + 128, y * 128);
                GL11.glTexCoord2f(0.25f, 1);
                GL11.glVertex2f(x * 128 + 128, y * 128 + 128);
                GL11.glTexCoord2f(0.0f, 1);
                GL11.glVertex2f(x * 128, y * 128 + 128);

                GL11.glColor4f(1, 1, 1, snowLayer1);
                GL11.glTexCoord2f(0.25f, 0);
                GL11.glVertex2f(x * 128, y * 128);
                GL11.glTexCoord2f(0.5f, 0);
                GL11.glVertex2f(x * 128 + 128, y * 128);
                GL11.glTexCoord2f(0.5f, 1);
                GL11.glVertex2f(x * 128 + 128, y * 128 + 128);
                GL11.glTexCoord2f(0.25f, 1);
                GL11.glVertex2f(x * 128, y * 128 + 128);

                GL11.glColor4f(1, 1, 1, snowLayer2);
                GL11.glTexCoord2f(0.5f, 0);
                GL11.glVertex2f(x * 128, y * 128);
                GL11.glTexCoord2f(0.75f, 0);
                GL11.glVertex2f(x * 128 + 128, y * 128);
                GL11.glTexCoord2f(0.75f, 1);
                GL11.glVertex2f(x * 128 + 128, y * 128 + 128);
                GL11.glTexCoord2f(0.5f, 1);
                GL11.glVertex2f(x * 128, y * 128 + 128);

                GL11.glColor4f(1, 1, 1, snowLayer3);
                GL11.glTexCoord2f(0.75f, 0);
                GL11.glVertex2f(x * 128, y * 128);
                GL11.glTexCoord2f(1f, 0);
                GL11.glVertex2f(x * 128 + 128, y * 128);
                GL11.glTexCoord2f(1f, 1);
                GL11.glVertex2f(x * 128 + 128, y * 128 + 128);
                GL11.glTexCoord2f(0.75f, 1);
                GL11.glVertex2f(x * 128, y * 128 + 128);

            }
        }
        GL11.glEnd();
    }
}
