package Character;

import Items.Clothes.Cloth;
import survive.GameHolder;

/**
 *
 * @author Jyri Saukkonen
 */
public class Clothes {

    float headWetness;
    float upperBodyWetness;
    float legWetness;
    float footWetness;
    float handWetness;

    Cloth hat;
    Cloth jacket;
    Cloth legs;
    Cloth gloves;
    Cloth boots;
    CharacterHolder holder;

    public Clothes(CharacterHolder holder) {
        hat = new Cloth();
        jacket = new Cloth();
        legs = new Cloth();
        gloves = new Cloth();
        boots = new Cloth();
        this.holder = holder;

        headWetness = 0;
        upperBodyWetness = 0;
        legWetness = 0;
        footWetness = 0;
        handWetness = 0;
    }

    public void useCloth(Cloth cloth) {
        int ws = cloth.wearSlot;

        if (ws == 1) {
            hat = cloth;
        } else if (ws == 2) {
            jacket = cloth;
        } else if (ws == 3) {
            legs = cloth;
        } else if (ws == 4) {
            gloves = cloth;
        } else if (ws == 5) {
            boots = cloth;
        } else {
            //TODO, lisävaate (viltit jne), voidaan pukea minne halutaan parantamaan eristystä tai vedenkestävyyttä.
        }
    }

    public void tick() {
        //Jos sataa vettä tai räntää
        //System.out.println("");
        //System.out.println("Wetness " + headWetness);
        if (GameHolder.environmentHolder.rain.density > 0 && GameHolder.environmentHolder.temperature.temperature > -2) {
            if (headWetness < 1) {
                headWetness += 0.01f * (GameHolder.environmentHolder.rain.density * (1 - hat.waterResistance));
            }
            if (upperBodyWetness < 1) {
                upperBodyWetness += 0.01f * (GameHolder.environmentHolder.rain.density * (1 - jacket.waterResistance));
            }
            if (legWetness < 1) {
                legWetness += 0.01f * (GameHolder.environmentHolder.rain.density * (1 - legs.waterResistance));
            }
            if (footWetness < 1) {
                footWetness += 0.01f * (GameHolder.environmentHolder.rain.density * (1 - gloves.waterResistance));
            }
            if (handWetness < 1) {
                handWetness += 0.01f * (GameHolder.environmentHolder.rain.density * (1 - boots.waterResistance));
            }
        } else {
            if (headWetness > 0) {
                headWetness -= 0.01f * Math.max(1, GameHolder.environmentHolder.temperature.temperature);
            }
            if (upperBodyWetness > 0) {
                upperBodyWetness -= 0.01f * Math.max(1, GameHolder.environmentHolder.temperature.temperature);
            }

            if (legWetness > 0) {
                legWetness -= 0.01f * Math.max(1, GameHolder.environmentHolder.temperature.temperature);
            }
            if (footWetness > 0) {
                footWetness -= 0.01f * Math.max(1, GameHolder.environmentHolder.temperature.temperature);
            }
            if (handWetness > 0) {
                handWetness -= 0.01f * Math.max(1, GameHolder.environmentHolder.temperature.temperature);
            }
        }

        if (headWetness < 0) {
            headWetness = 0;
        }
        if (upperBodyWetness < 0) {
            upperBodyWetness = 0;
        }
        if (legWetness < 0) {
            legWetness = 0;
        }
        if (footWetness < 0) {
            footWetness = 0;
        }
        if (handWetness < 0) {
            handWetness = 0;
        }

    }

    public float getOverallInsulation() {
        return 4 * jacket.insulation - 4 * upperBodyWetness
                + 0.5f * hat.insulation - 0.5f * headWetness
                + 3 * legs.insulation - 3 * legWetness
                + 2 * boots.insulation - 2 * footWetness
                + 0.5f * gloves.insulation - 0.5f * handWetness;
    }

}
