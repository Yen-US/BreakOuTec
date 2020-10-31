import java.awt.*;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.*;

import java.net.*;
import java.io.*;

@SuppressWarnings("serial")
public class Game extends JPanel {
    /**
     * Variables globales en las que se incluye, las listas de objetos tales como la de bloques y bolas, ademas de la variable de vidas, velocidad,
     * puntuacion y la creacion del objeto raqueta
     */

    Bloque[][] bloquesL = new Bloque[8][14];
    Ball[] bolasL = new Ball[4];
    Racket racket = new Racket(this);
    Integer lifes = 3;
    Integer vel = 1;
    Integer punt = 0;
    Boolean ini = true;


    /**
     * Constructor de la clase game, en la que se crea el key listener y se asigna a la racketa
     */
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

    /**
     * Metodo bolas() la cual se instancia la primera bola y luego las tres siguientes son nulas, ya que al inicio solo se inicia con una bola
     */
    private void bolas() {
        bolasL[0] = new Ball(this);
        bolasL[1] = null;
        bolasL[2] = null;
        bolasL[3] = null;
    }

    /**
     * Metodo bloques() la cual se crean los bloques, funciona para crear las listas de objetos de los bloques para asi luego ser renderizados
     * ademas en los mismos se le asigna de una vez el color que cada uno va a poseer
     */
    private void bloques() {
        if (ini) {
            bolas();
            ini = false;
        }

        Integer x = 5;
        Integer y = 20;
        for (Integer i = 0; i < 8; i++) {
            for (Integer j = 0; j < 14; j++) {
                switch (i) {
                    case 2:
                    case 3:
                        bloquesL[i][j] = new Bloque(this, x, y, 1);
                        break;
                    case 4:
                    case 5:
                        bloquesL[i][j] = new Bloque(this, x, y, 2);
                        break;
                    case 6:
                    case 7:
                        bloquesL[i][j] = new Bloque(this, x, y, 3);
                        break;
                    default:
                        bloquesL[i][j] = new Bloque(this, x, y, 0);
                        break;
                }
                x += 85;
            }
            x = 5;
            y += 35;
        }
    }

    /**
     * Metodo colls, el cual recibe el bloque el cual ha colisionado con la bola y le los procedimientos necesarios para poder ser elmiminado del grid
     * además de aplicar los poderes al juagadore que ese bloque poseia
     */
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
                            for (Integer a = 0; a < 3; a++) {
                                if (bolasL[i] == null) {
                                    bolasL[i] = new Ball(this);
                                    break;
                                }
                            }
                            break;
                        case 3:
                            racket.set2W();
                            break;
                        case 4:
                            racket.sethalfW();
                            break;
                        case 5:
                            //Aumentar velocidad 2fix
                            vel = 2;
                            break;
                        case 6:
                            //Disminuir velocidad 2fix
                            vel = 1;
                            break;

                    }
                    //Client(i,j);
                    bloquesL[i][j] = null;
                }
            }
        }
    }

    /**
     * Metodo move() el cual permite luego ser llamdo en el thread del juego para poder ser ejecutado cada cierta cantidad de tiempo y de esta manera
     * permitir el moviomiento de los objetos en el grid
     */
    private void move() {
        for (Integer i = 0; i < 3; i++) {
            if (!(bolasL[i] == null)) {
                bolasL[i].move();
            }
        }
        racket.move();
    }

/** Metodo paint el cual permite dibujar los elementos en pantalla, por ejemplo se recorre todas las listas de objetos para asi si no son nulas
 * dibujarlas en pantalla, ademas tambien permite resetear eel grid cuando se acabe*/
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("Vidas: " + lifes, 10, 13);
        g.drawString("Puntuación: " + punt, 80, 13);
        g.setColor(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


        for(Integer i=0; i<3;i++){
            if( !(bolasL[i]==null)){
                bolasL[i].paint(g2d);
            }
        }
        for(Integer i=0; i<8;i++){
            for(Integer j=0; j<14;j++) {
                if( !(bloquesL[i][j]==null)){
                    bloquesL[i][j].paint(g2d);
                }
            }
        }
        Boolean empty=true;
        for(Integer i=0; i<8;i++){
            for(Integer j=0; j<14;j++) {
                if( !(bloquesL[i][j]==null)){
                    empty=false;
                    break;
                }
            }
        }
        if(empty){
            bloques();
            for(Integer i=0; i<3;i++){
                if( !(bolasL[i]==null)){
                    bolasL[i].x=400;
                    bolasL[i].y=400;
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
            e.printStackTrace();
        }
    }
    /** Metodo GameOver() el cual permite presenrtar en pantalla el mensaje en caso de que el jugador pierda sus vidas */
    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.ERROR_MESSAGE);
        System.exit(ABORT);
    }

    /** Main donde se crea el Jframe, ademas donde se crea el thread para poder ejecutar el movimiento */
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("BreakOuTEC"); 
        Game game = new Game();
        frame.add(game);
        frame.setSize(1195, 720);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);

        Thread t1 = new Thread();
        while (true) {
            game.move();
            game.repaint();
            t1.sleep(4);


        }

    }
}