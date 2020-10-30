import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.*;

public class Racket {

    private static final Integer y = 660;
    private static Integer w = 200;
    private static final Integer h= 10;
    Integer x = 547;
    Integer xa = 0;
    private Game game;

    public Racket(Game game) {
        this.game= game;
    }

    public void set2W(){
        if(w == 200 || w == 100){
            w = w*2;
        }
    }

    public void sethalfW(){
        if(w== 200||w==400){
            w = w/2;
        }
    }

    public void move() {
        if (x + xa > 0 && x + xa < game.getWidth()-100)
            x = x + xa;
    }

    public void keyReleased(KeyEvent e) {
        xa = 0;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A)
            xa = -2;
        if (e.getKeyCode() == KeyEvent.VK_D)
            xa = 2;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public int getTopY() {
        return y;
    }
    public void paint(Graphics2D g) {
        g.fill3DRect(x, y, w, h, true);
    }
}