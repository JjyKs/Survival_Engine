package survive.environment;

/**
 *
 * @author Jyri Saukkonen
 */
public class TimeOfTheYear {

    public float maxTemp = -20;
    public float minTemp = -30;
    public int tickTimer = 0; //Kutsutaan TimeOfDaystä
    boolean increasing = true;

    public void tick() {        
        if (tickTimer == 1) {
            if (increasing) {
                maxTemp++;
                minTemp++;
                if (maxTemp == 30) {
                    increasing = false;
                }
            } else {
                maxTemp--;
                minTemp--;
                if (maxTemp == -20) {
                    increasing = true;
                }
            }
            tickTimer = 0;
        }

    }
}
