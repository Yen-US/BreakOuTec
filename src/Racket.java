import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.*;

public class Racket {
/** Variobles globales donde creo la posicion y la velocidad de desplazamiento de la raqueta*/
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
/** Metodo move() la cual es utilizada para permitir el movimiento de la racketa */
    public void move() {
        if (x + xa > 0 && x + xa < game.getWidth()-100)
            x = x + xa;
    }

    /** Metodo keyReleased utilizado para frenar la raqueta eel usuario suelte la tecla*/
    public void keyReleased(KeyEvent e) {
        xa = 0;
    }
/** Metodo keyPressed utilizado para detectar la tecla presionada y asi cambiar la velocidad de movimiento */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A)
            xa = -2;
        if (e.getKeyCode() == KeyEvent.VK_D)
            xa = 2;
    }
/** Metodo getBounds utilizado para retornar el hit de la racketa */
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }
/** Metodo getTopY utilizdo para obtener la cordenada superior de la raqueta */
    public int getTopY() {
        return y;
    }

    /** Metodo paint utilizado para dibujar la raqueta */
    public void paint(Graphics2D g) {
        g.fill3DRect(x, y, w, h, true);
    }
}