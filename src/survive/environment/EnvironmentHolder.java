
package survive.environment;

/**
 *
 * @author Jyri Saukkonen
 */
public class EnvironmentHolder {
    public Rain rain = new Rain();
    public Temperature temperature = new Temperature();
    public TimeOfDay timeOfDay = new TimeOfDay();
    public TimeOfTheYear timeOfYear = new TimeOfTheYear();
    public Wind wind = new Wind();
    public LightMap lightMap = new LightMap();
   
    
    public void tick(){  
        timeOfDay.tick();
        timeOfYear.tick();
        temperature.tick();
        wind.tick();
        rain.drawAndTickRain();   
        
        lightMap.tick();
        lightMap.draw();
    }
}
