package survive.environment;

import java.util.Random;

/**
 *
 * @author Jyri Saukkonen
 */
public class Wind {

    public float xPower = 1;
    public float yPower = 2;
    float targetX = 1;
    float targetY = 2;
    int tickTimer = 0;

    public void tick() {
        tickTimer++;
        if (tickTimer > 100) {
            Random rand = new Random();
            targetX = rand.nextFloat() * 20 - 10;
            targetY = rand.nextFloat() * 9 + 0.2f;
            tickTimer = 0;
        }

        if (targetX > xPower) {
            xPower += 0.01f;
        }
        if (targetX < xPower) {
            xPower -= 0.01f;
        }
        if (targetY > yPower) {
            yPower += 0.01f;
        }
        if (targetY < yPower) {
            yPower -= 0.01f;
        }

    }
}
