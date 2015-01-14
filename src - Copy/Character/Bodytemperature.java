package Character;

import org.lwjgl.input.Keyboard;
import survive.GameHolder;

/**
 *
 * @author Jyri Saukkonen
 */
public class Bodytemperature {

    float energyLeft;
    float baseEnergyConsumption;
    float bodyTemp;
    CharacterHolder holder;

    public Bodytemperature(CharacterHolder holder) {
        this.holder = holder;
        energyLeft = 3000;
        baseEnergyConsumption = 0.01f;
        bodyTemp = 37;
    }

    public void addEnergy(float energy) {
        energyLeft += energy;
    }

    public void tick() {
        float currentEnergyConsumption = baseEnergyConsumption * holder.stressLevel;
        if (bodyTemp < 34.5f){
            holder.body.alive = false;
        }        
        energyLeft -= currentEnergyConsumption;
        if (energyLeft < 0) {
            energyLeft = 0;
            bodyTemp -= currentEnergyConsumption;
        }
        if (bodyTemp < 37 && energyLeft > 10) {
            bodyTemp += currentEnergyConsumption;
            energyLeft -= 5;
        }

        //Bonus burn
        if (GameHolder.environmentHolder.temperature.temperature < 20) {
            float test = GameHolder.environmentHolder.temperature.temperature - 20;
            test = -test;
            //System.out.println("TEST " + test);
            energyLeft -= currentEnergyConsumption * test * (10 - holder.clothHolder.getOverallInsulation());
            //System.out.println("Bonus burn " + currentEnergyConsumption * test * (10 - holder.clothHolder.getOverallInsulation()));
        }
        //System.out.println("Insulation " + holder.clothHolder.getOverallInsulation());

        if (energyLeft < 100) {
            boolean success = holder.body.burnWeightForEnergy();
            if (success) {
                energyLeft += 100;
            }
        }
        //System.out.println("Energy left " + energyLeft);
        //System.out.println("body temp " + bodyTemp);
    }

}
