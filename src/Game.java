import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.*;

@SuppressWarnings("serial")
public class Game extends JPanel {

    Ball ball = new Ball(this);
    Bloque[][] bloquesL= new Bloque[8][14];
    Racket racket= new Racket(this);

    public Game() {
        bloques();
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                racket.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                racket.keyPressed(e);
            }
        });
        setFocusable(true);
    }

    private void bloques() {
        Integer x=5;
        Integer y=20;
        for(Integer i=0; i<8;i++){
            for(Integer j=0; j<14;j++) {
                bloquesL[i][j]= new Bloque(this, x, y);
                x+=85;
            }
            x=5;
            y+=35;
        }
    }

    protected void colls(Bloque bloq){
        for(int i=0; i<8;i++){
            for(int j=0; j<14;j++) {
                if(bloquesL[i][j]==bloq){
                    bloquesL[i][j]=null;
                }
            }
        }
    }

    private void move() {
        ball.move();
        racket.move();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ball.paint(g2d);

        for(int i=0; i<8;i++){
            for(int j=0; j<14;j++) {
                if( !(bloquesL[i][j]==null)){
                    bloquesL[i][j].paint(g2d);
                }
            }
        }
        racket.paint(g2d);
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.ERROR_MESSAGE);
        System.exit(ABORT);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("BreakOuTEC");
        Game game = new Game();
        frame.add(game);
        frame.setSize(1195, 720);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Thread t1 = new Thread();

        while (true) {
            game.move();
            game.repaint();
            t1.setPriority(Thread.MAX_PRIORITY);
            t1.sleep(4);
        }
    }
}