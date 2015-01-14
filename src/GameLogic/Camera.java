package GameLogic;

/**
 *
 * @author jysky_saukkonen
 */
public class Camera {

    public int x;
    public int y;

    public Camera() {
        x = 0;
        y = 0;
    }

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveCamera(int xMove, int yMove) {
        x += xMove;
        y += yMove;
    }

    public void tick() {
        //x = x - 1;
        //y = y + 1;
    }
}
