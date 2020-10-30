import java.awt.*;
import java.lang.*;

public class Ball {

    private static final Integer DIAMETER = 20;
    Integer x = 400;
    Integer y = 400;
    Integer vel = 1;
    Integer xa = vel;
    Integer ya = vel;
    private Game game;

    public Ball(Game game) {
        this.game= game;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }

    private boolean collisionRac() {
        return game.racket.getBounds().intersects(getBounds());
    }

    void collision(){
        ya = -ya;
    }

    void move() {
        if (x + xa < 0)
            xa = vel;
        if (x + xa > game.getWidth() - DIAMETER)
            xa = -xa;
        if (y + ya < 0)
            ya = vel;
        if (y + ya > game.getHeight() - DIAMETER)
            game.gameOver();
        if (collisionRac()){
            ya = -ya;
            y = game.racket.getTopY() - DIAMETER;
        }

        x = x + xa;
        y = y + ya;
    }

    public void paint(Graphics2D g) {
        g.fillOval(x, y, 20, 20);
    }
}
