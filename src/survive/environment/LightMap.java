package survive.environment;

import org.lwjgl.opengl.GL11;
import survive.GameHolder;

/**
 *
 * @author Jyri Saukkonen
 */
public class LightMap {

    public int scale = 24;
    int texture = 5;
    float[][][] lightValues = new float[32][58][8];
    public float[][] lightPoints = new float[32][58];
    boolean rising = false;

    public LightMap() {
        debugInit();
    }

    public void tick() {
        updateLightmap(true);
    }

    public void updateLightmap(boolean firstRun) {
        boolean reRun = false;
        if (firstRun) {
            for (int u = 0; u < 8; u++) {
                for (int i = 0; i < 32; i++) {
                    for (int o = 0; o < 58; o++) {
                        lightValues[i][o][u] = 0;
                    }
                }
            }
        }

        int valo = 0;
        for (int i = 0; i < 32; i++) {
            for (int o = 0; o < 57; o++) {
                if (lightPoints[i][o] > 0) {
                    if (valo < 8) {
                        lightValues[i][o][valo] = lightPoints[i][o];

                        valo++;
                    } else {
                        lightPoints[i][o] = 0;
                    }
                }
            }
        }
        for (int u = 0; u < 8; u++) {
            for (int i = 0; i < 32; i++) {
                for (int o = 0; o < 57; o++) {

                    if (lightValues[i][o][u] > 0.0005f) {
                        if (i > 0) {
                            if (lightValues[i - 1][o][u] + 0.01f < lightValues[i][o][u] * 0.9f) {
                                reRun = true;
                                lightValues[i - 1][o][u] = lightValues[i][o][u] * 0.9f;
                            }
                        }
                        if (i < 31) {
                            if (lightValues[i + 1][o][u] + 0.01f < lightValues[i][o][u] * 0.9f) {
                                reRun = true;
                                lightValues[i + 1][o][u] = lightValues[i][o][u] * 0.9f;
                            }
                        }

                        if (o > 0) {
                            if (lightValues[i][o - 1][u] + 0.01f < lightValues[i][o][u] * 0.9f) {
                                reRun = true;
                                lightValues[i][o - 1][u] = lightValues[i][o][u] * 0.9f;
                            }
                            if (i > 0) {
                                if (lightValues[i - 1][o - 1][u] + 0.01f < lightValues[i][o][u] * 0.87f) {
                                    reRun = true;
                                    lightValues[i - 1][o - 1][u] = lightValues[i][o][u] * 0.87f;
                                }
                            }
                            if (i < 31) {
                                if (lightValues[i + 1][o - 1][u] + 0.01f < lightValues[i][o][u] * 0.87f) {
                                    reRun = true;
                                    lightValues[i + 1][o - 1][u] = lightValues[i][o][u] * 0.87f;
                                }
                            }
                        }
                        if (o < 58) {
                            if (lightValues[i][o + 1][u] + 0.01f < lightValues[i][o][u] * 0.9f) {
                                reRun = true;
                                lightValues[i][o + 1][u] = lightValues[i][o][u] * 0.9f;
                            }
                            if (i > 0) {
                                if (lightValues[i - 1][o + 1][u] + 0.01f < lightValues[i][o][u] * 0.87f) {
                                    reRun = true;
                                    lightValues[i - 1][o + 1][u] = lightValues[i][o][u] * 0.87f;
                                }
                            }
                            if (i < 31) {
                                if (lightValues[i + 1][o + 1][u] + 0.01f < lightValues[i][o][u] * 0.87f) {
                                    reRun = true;
                                    lightValues[i + 1][o + 1][u] = lightValues[i][o][u] * 0.87f;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (reRun) {
            updateLightmap(false);
        } else {
            for (int u = 1; u < 8; u++) {
                for (int i = 0; i < 32; i++) {
                    for (int o = 0; o < 57; o++) {
                        if (lightValues[i][o][u] > 0) {
                            lightValues[i][o][0] = Math.max(lightValues[i][o][0], lightValues[i][o][u]) + (Math.min(lightValues[i][o][0], lightValues[i][o][u]) * 0.4f);
                        }
                    }
                }
            }
        }
    }

    public void debugInit() {
        for (int i = 0; i < 32; i++) {
            for (int o = 0; o < 58; o++) {
                lightPoints[i][o] = 0;
            }
        }

        lightPoints[18][40] = 1f;
        lightPoints[28][5] = 1f;
        lightPoints[24][5] = 1f;
        lightPoints[14][5] = 1f;
        lightPoints[8][5] = 1f;

        updateLightmap(true);

    }

    //Rewritaa joskus..
    public void draw() {

        int xC = GameHolder.camera.x;
        int yC = GameHolder.camera.y;
        while (xC > scale + 8) {
            xC -= scale;
        }
        while (xC < scale + 8) {
            xC += scale;
        }

        GameHolder.textureHandler.textures.get(texture).bind();

        GL11.glBegin(GL11.GL_QUADS);
        float fallOff = 0.01f;
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 58; x++) {
                float compareLightValue = 0;

                compareLightValue = lightValues[y][x][0];
                if (y > 0 && x > 0) {
                    compareLightValue = lightValues[y - 1][x - 1][0];
                }
                float lightValue = Math.max(lightValues[y][x][0], compareLightValue);

                GL11.glColor4f(0, 0, 0, Math.min(0.9f, 1 - 0.1f * GameHolder.environmentHolder.timeOfDay.time) - lightValue * 1.5f);
                GL11.glTexCoord2f(0f, 0);
                GL11.glVertex2f(x * scale + xC, y * scale);

                compareLightValue = lightValues[y][x][0];
                if (y > 0 && x < 57) {
                    compareLightValue = lightValues[y - 1][x + 1][0];
                }
                lightValue = Math.max(lightValues[y][x][0], compareLightValue);

                GL11.glColor4f(0, 0, 0, Math.min(0.9f, 1 - 0.1f * GameHolder.environmentHolder.timeOfDay.time) - lightValue * 1.5f);
                GL11.glTexCoord2f(1f, 0);
                GL11.glVertex2f(x * scale + scale + xC, y * scale);

                compareLightValue = lightValues[y][x][0];
                if (y < 31 && x < 57) {
                    compareLightValue = lightValues[y + 1][x + 1][0];
                }
                lightValue = Math.max(lightValues[y][x][0], compareLightValue);

                GL11.glColor4f(0, 0, 0, Math.min(0.9f, 1 - 0.1f * GameHolder.environmentHolder.timeOfDay.time) - lightValue * 1.5f);
                GL11.glTexCoord2f(1f, 1f);
                GL11.glVertex2f(x * scale + scale + xC, y * scale + scale);

                compareLightValue = lightValues[y][x][0];
                if (y > 31 && x > 0) {
                    compareLightValue = lightValues[y + 1][x - 1][0];
                }
                lightValue = Math.max(lightValues[y][x][0], compareLightValue);

                GL11.glColor4f(0, 0, 0, Math.min(0.9f, 1 - 0.1f * GameHolder.environmentHolder.timeOfDay.time) - lightValue * 1.5f);
                GL11.glTexCoord2f(0f, 1f);
                GL11.glVertex2f(x * scale + xC, y * scale + scale);

            }
        }
        GL11.glEnd();

    }

}
