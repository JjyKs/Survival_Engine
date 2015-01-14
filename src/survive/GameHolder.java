package survive;

import Character.CharacterHolder;
import GameLogic.Camera;
import survive.Tools.TextureHandler;
import survive.World.WorldHolder;
import survive.environment.EnvironmentHolder;

/**
 *
 * @author Jyri Saukkonen
 */
public class GameHolder {

    //Tools
    public static TextureHandler textureHandler = new TextureHandler();
    //Environment
    public static EnvironmentHolder environmentHolder = new EnvironmentHolder();
    //World
    public static WorldHolder worldHolder = new WorldHolder();    
    //Main loop, set at main@Survive.java
    public static Survive survive;    
    //Pelaaja
    public static CharacterHolder player = new CharacterHolder();
    
    //Camera
    public static Camera camera = new Camera();
}
