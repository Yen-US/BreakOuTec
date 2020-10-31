import java.awt.*;
import java.lang.*;

public class Ball {

    private static final Integer DIAMETER = 20;
    Integer x = 400;
    Integer y = 400;
    private Game game;
    Integer xa = 1;
    Integer ya = 1;

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
            xa = game.vel;
        if (x + xa > game.getWidth() - DIAMETER)
            xa = -game.vel;
        if (y + ya < 0)
            ya = game.vel;
        if (y + ya > game.getHeight() - DIAMETER)
            if (game.lifes>0){
                game.lifes-=1;
                x=400;
                y=400;
                xa=game.vel;
                ya=game.vel;
            }else{
                game.gameOver();
            }

        if (collisionRac()){
            ya = -game.vel;
            y = game.racket.getTopY() - DIAMETER;
        }

        x = x + xa;
        y = y + ya;
    }

    public void paint(Graphics2D g) {
        g.fillOval(x, y, 20, 20);
    }
}
