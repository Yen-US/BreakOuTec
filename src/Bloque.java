import java.awt.*;
import java.lang.*;

public class Bloque {

    Integer x = 50;
    Integer y = 50;
    private Game game;

    public Bloque(Game game, int px, int py) {
        this.game= game;
        this.x= px;
        this.y= py;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 80, 30);
    }

    private boolean collision() {
        return game.ball.getBounds().intersects(getBounds());
    }


    public void paint(Graphics2D g) {
        g.fill3DRect(x, y, 80, 30, true);

        if (collision()){
            game.ball.collision();
            game.colls(this);
            g.dispose();
        }

    }
}