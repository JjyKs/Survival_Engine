
package survive.environment;

import survive.GameHolder;

/**
 *
 * @author Jyri Saukkonen
 */
public class TimeOfDay {
    public float time = 0;
    boolean increasing = false;
    
    
    public void tick(){
        if (increasing){
            time += 0.005f;
            if (time > 10){
                increasing = false;
            }
        } else {
            time -= 0.005f;
            if (time < 0){
                increasing = true;
                GameHolder.environmentHolder.timeOfYear.tickTimer = 1;
            }
        }
        //System.out.println(time);
    }
}
