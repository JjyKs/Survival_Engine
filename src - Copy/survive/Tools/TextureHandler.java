package survive.Tools;

import java.util.ArrayList;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Jyri Saukkonen
 */
public class TextureHandler {

    public ArrayList<Texture> textures;

    public TextureHandler() {
        textures = new ArrayList();
        init();
    }
    public ArrayList<Texture> getTexturelist(){
        return textures;
    }

    public final void init() {
        load("nullobject");//0
        load("testgrass"); //1
        load("rain");      //2  
        load("snow3");     //3
        load("testrock");  //4
        load("night");     //5
        load("testtree");  //6
        load("testcar");   //7
    }

    public void load(String name) {
        try {
            Texture texture;
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + name + ".png"));
            System.out.println("Texture loaded: " + texture);
            System.out.println(">> Texture ID: " + texture.getTextureID());
            textures.add(texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
