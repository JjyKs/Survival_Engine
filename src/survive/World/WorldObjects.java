package survive.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import survive.Objects.GameObject;
import survive.Objects.PineTree;
import survive.Objects.Rock;

/**
 *
 * @author Jyri Saukkonen
 */
public class WorldObjects {

    public ArrayList<GameObject> objects = new ArrayList();

    public WorldObjects() {
        debugInit();
    }

    public void debugInit() {
        PineTree puu = new PineTree();
        puu.x = 1024;
        puu.y = 0;
        objects.add(puu);
        Rock rock = new Rock();
        rock.x = 300;
        rock.y = 220;
        objects.add(rock);

        Rock rock2 = new Rock();
        rock2.x = 700;
        rock2.y = 400;
        objects.add(rock2);
        PineTree puu2 = new PineTree();
        puu2.x = 350;
        puu2.y = 219;
        objects.add(puu2);

        PineTree puu3 = new PineTree();
        puu3.x = 264;
        puu3.y = 200;
        objects.add(puu3);
        Collections.sort(objects);
    }

    public void drawAndTickObjects() {
        tick();
        for (GameObject o : objects) {
            
            o.drawObject();
        }
    }

    public void tick() {
        for (GameObject o : objects) {
            o.tick();
        }
    }
}
