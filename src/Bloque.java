import java.awt.*;
import java.lang.*;

public class Bloque {

    Integer x = 50;
    Integer y = 50;
    private Game game;
    Integer puntaje = 0;
    Integer power;
    Integer tipo;
    /** Constructor Bloque utilizado para colocar los bloques en el grid y asignarlo a las variables*/
    public Bloque(Game game, Integer px, Integer py, Integer tip) {
        this.game= game;
        this.x= px;
        this.y= py;
        this.power = 0;
        this.tipo= tip;

    }
    
/** Metodo getbounds utilizado para obtener el hitbox del bloque */
    public Rectangle getBounds() {
        return new Rectangle(x, y, 80, 30);
    }
/** Metodo collision, utilizado para detectar las colisiones */
    private boolean collision() {
        for (Integer i = 0; i < 3; i++) {
            if (!(game.bolasL[i] == null)) {
                return game.bolasL[i].getBounds().intersects(getBounds());

            }
        }return game.bolasL[0].getBounds().intersects(getBounds());
    }

    public void paint2(Graphics2D g){
        switch (tipo){
            case 0:
                g.setColor(Color.RED);
                break;
            case 1:
                g.setColor(Color.ORANGE);
                break;
            case 2:
                g.setColor(Color.YELLOW);
                break;
            case 3:
                g.setColor(Color.GREEN);
                break;

        }
        g.fill3DRect(x, y, 80, 30, true);
    }

/** metodo paint utilizado para cambiar el color de los bloques, ademas se detectan las colisiones */
    public void paint(Graphics2D g) {
        switch (tipo){
            case 0:
                g.setColor(Color.RED);
                break;
            case 1:
                g.setColor(Color.ORANGE);
                break;
            case 2:
                g.setColor(Color.YELLOW);
                break;
            case 3:
                g.setColor(Color.GREEN);
                break;

        }
        g.fill3DRect(x, y, 80, 30, true);



        if (collision()){
            for (Integer i = 0; i < 3; i++) {
                if (!(game.bolasL[i] == null)) {
                    game.bolasL[i].collision();
                }
            }
            game.colls(this);
            g.dispose();
        }

    }
}
