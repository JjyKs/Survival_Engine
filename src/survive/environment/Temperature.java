package survive.environment;

import java.util.Random;
import survive.GameHolder;

/**
 *
 * @author Jyri Saukkonen
 */
public class Temperature {

    public float temperature = -20;
    public float targetTemp = -20;
    int tickTimer = 0;

    public void tick() {
        tickTimer++;

        if (tickTimer > 100) {
            Random rand = new Random();
            targetTemp = rand.nextFloat() * 10 + GameHolder.environmentHolder.timeOfYear.minTemp;
            tickTimer = 0;
        }

        if (targetTemp < temperature) {
            temperature -= 0.06f;
        } else {
            temperature += 0.06f;
        }
        System.out.println("Temperature " + temperature);

    }
}
