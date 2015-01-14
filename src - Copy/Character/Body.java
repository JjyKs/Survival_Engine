package Character;

/**
 *
 * @author Jyri Saukkonen
 */
public class Body {

    float weight;
    CharacterHolder holder;
    int burnTimer;
    public boolean alive = true;

    public Body(CharacterHolder holder) {
        this.holder = holder;
        weight = 70;
        burnTimer = 0;

    }

    public void tick() {
        //System.out.println("weight: " + weight);
        if (burnTimer > 0) {
            burnTimer--;
        }
    }

    public boolean burnWeightForEnergy() {
        if (burnTimer == 0) {
            burnTimer = 100;
            weight -= 0.1f;
            return true;
        } else {
            return false;
        }
    }
}
