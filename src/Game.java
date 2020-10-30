import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.*;

import java.net.*;
import java.io.*;

@SuppressWarnings("serial")
public class Game extends JPanel {

    Ball ball = new Ball(this);
    Bloque[][] bloquesL = new Bloque[8][14];
    Racket racket = new Racket(this);
    Integer lifes = 3;

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
        Integer x = 5;
        Integer y = 20;
        for (Integer i = 0; i < 8; i++) {
            for (Integer j = 0; j < 14; j++) {
                bloquesL[i][j] = new Bloque(this, x, y);
                x += 85;
            }
            x = 5;
            y += 35;
        }
    }

    protected void colls(Bloque bloq) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 14; j++) {
                if (bloquesL[i][j] == bloq) {
                    int power = bloquesL[i][j].power;

                    switch (power) {
                        case 1:
                            lifes++;
                            break;
                        case 2:
                             // Nueva bola
                             break;
                        case 3:
                            racket.set2W();
                            break;
                        case 4:
                            racket.sethalfW();
                            break;
                        case 5:
                            //Aumentar velocidad 2fix
                            ball.vel = 4;
                            break;
                        case 6:
                            //Disminuir velocidad 2fix
                            ball.vel = 1;
                            break;
                            
                    }
                    //Client(i,j);
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
        g.drawString("Vidas: " + lifes, 10, 13);
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

    public void Client(int i, int j){
        try {
            Socket sock = new Socket("127.0.0.1", 25558);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            out.println(i+"c"+j);
            out.flush();
            
            out.close();
            sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         
    }

    public void Server(){
        try (ServerSocket serverSocket = new ServerSocket(25557)){
            System.out.println("Server is listening");
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String text;
                text = reader.readLine();

                char ch1 = text.charAt(0);
                if(ch1 == '1'){
                    //Logica de establecer el mismo puntaje para todas las columnas de la fila dada
                    Character rowch = text.charAt(2);
                    Integer row = Character.getNumericValue(rowch); //Fila
                    String puntajStr = text.substring(text.lastIndexOf("p")+1);
                    Integer puntaje = Integer.parseInt(puntajStr.trim()); //Columna

                    for(Integer i = 0; i<14; i++){
                        bloquesL[row][i].puntaje = puntaje;
                    }
                }
                if(ch1 == '2'){
                    System.out.println("Entra al 2");
                    //Insertar el power up al ladrillo en la fila y columna dada
                    Character rowch = text.charAt(2);
                    Integer row = Character.getNumericValue(rowch); //Fila

                    String colch = text.substring(text.indexOf("c")+1);
                    colch = colch.substring(0, colch.indexOf("p"));

                    Integer col = Integer.parseInt(colch.trim()); //Columna

                    String poderStr = text.substring(text.lastIndexOf("p")+1);
                    Integer poder = Integer.parseInt(poderStr.trim()); //Poder

                    bloquesL[row][col].power = poder;
                }

                System.out.println(text);
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
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