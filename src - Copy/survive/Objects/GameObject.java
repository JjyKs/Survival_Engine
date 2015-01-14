package survive.Objects;

import org.lwjgl.opengl.GL11;
import survive.GameHolder;
import survive.environment.Rain;
import survive.environment.Temperature;
import survive.environment.Wind;

/**
 *
 * @author Jyri Saukkonen
 */
public class GameObject implements Comparable<GameObject> {

    public float snowLayer1;
    float snowLayer2;
    float snowLayer3;
    int texture;
    Temperature temperature;
    Rain rain;
    public int x;
    public int y;
    public int spriteSize = 128;
    boolean windRemovesSnow = false;

    public GameObject() {
        snowLayer1 = 0f;
        snowLayer2 = 0f;
        snowLayer3 = 0f;
        texture = 0;
        x = 0;
        y = 0;
        //TODO if talvi -> snowLayer1..3 = 1f;

        temperature = GameHolder.environmentHolder.temperature;
        rain = GameHolder.environmentHolder.rain;
    }

    public void drawObject() {
        GameHolder.textureHandler.textures.get(texture).bind();
        float time = GameHolder.environmentHolder.timeOfDay.time;

        GL11.glColor4f(1, 1, 1, 1f);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glColor4f(1, 1, 1, 1);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.25f, 1);
        GL11.glVertex2f(x + spriteSize, y);
        GL11.glTexCoord2f(0.25f, 0);
        GL11.glVertex2f(x + spriteSize, y + spriteSize);
        GL11.glTexCoord2f(0.0f, 0);
        GL11.glVertex2f(x, y + spriteSize);

        GL11.glColor4f(1, 1, 1, snowLayer1);
        GL11.glTexCoord2f(0.25f, 1);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.5f, 1);
        GL11.glVertex2f(x + spriteSize, y);
        GL11.glTexCoord2f(0.5f, 0);
        GL11.glVertex2f(x + spriteSize, y + spriteSize);
        GL11.glTexCoord2f(0.25f, 0);
        GL11.glVertex2f(x, y + spriteSize);

        GL11.glColor4f(1, 1, 1, snowLayer2);
        GL11.glTexCoord2f(0.5f, 1);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.75f, 1);
        GL11.glVertex2f(x + spriteSize, y);
        GL11.glTexCoord2f(0.75f, 0);
        GL11.glVertex2f(x + spriteSize, y + spriteSize);
        GL11.glTexCoord2f(0.5f, 0);
        GL11.glVertex2f(x, y + spriteSize);

        GL11.glColor4f(1, 1, 1, snowLayer3);
        GL11.glTexCoord2f(0.75f, 1);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(1f, 1);
        GL11.glVertex2f(x + spriteSize, y);
        GL11.glTexCoord2f(1f, 0);
        GL11.glVertex2f(x + spriteSize, y + spriteSize);
        GL11.glTexCoord2f(0.75f, 0);
        GL11.glVertex2f(x, y + spriteSize);
        GL11.glEnd();
    }

    public void tick() {
        tickSnowLayer();
    }

    public void tickSnowLayer() {
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

        //Jos yli nollan.
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

        //Jos objekti reagoi tuuleen
        Wind wind = GameHolder.environmentHolder.wind;
        if (windRemovesSnow) {
            if (temperature.temperature < 0 && wind.xPower + wind.yPower > 5 && rain.density < 0.1f) {

                if (snowLayer2 > 0.4f && snowLayer3 < 0.3f) {
                    snowLayer2 -= 0.00075f * (wind.xPower + wind.yPower) / 20;
                }
                if (snowLayer3 > 0.2f) {
                    snowLayer3 -= 0.00150f * (wind.xPower + wind.yPower) / 20;
                }
            }
        }
    }

    @Override
    public int compareTo(GameObject o) {
        return o.y - y;
    }

}
