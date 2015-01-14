
package Character;

/**
 *
 * @author Jyri Saukkonen
 */
public class CharacterHolder {
    boolean isAI = false;    
    float stressLevel = 1;
    
    
    Clothes clothHolder = new Clothes(this);
    Bodytemperature bodyTemperature = new Bodytemperature(this);
    Body body = new Body(this);
    
    public void tick(){
        clothHolder.tick();
        bodyTemperature.tick();
        body.tick();
    }
    
}
