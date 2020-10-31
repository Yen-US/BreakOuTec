import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import java.net.*;
import java.io.*;

@SuppressWarnings("serial")
public class ClienteObs extends JPanel {

    Bloque[][] bloquesL = new Bloque[8][14];
    static ClienteObs game;

    public ClienteObs() {
        bloques();
    }

    private void bloques() {
        Integer x = 5;
        Integer y = 20;
        for (Integer i = 0; i < 8; i++) {
            for (Integer j = 0; j < 14; j++) {
                switch (i){
                    case 2:
                    case 3:
                        bloquesL[i][j] = new Bloque(null, x, y,1);
                        break;
                    case 4:
                    case 5:
                        bloquesL[i][j] = new Bloque(null, x, y,2);
                        break;
                    case 6:
                    case 7:
                        bloquesL[i][j] = new Bloque(null, x, y,3);
                        break;
                    default:
                        bloquesL[i][j] = new Bloque(null, x, y,0);
                        break;
                }

                x += 85;
            }
            x = 5;
            y += 35;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        

        for(int i=0; i<8;i++){
            for(int j=0; j<14;j++) {
                if( !(bloquesL[i][j]==null)){
                    bloquesL[i][j].paint2(g2d);
                }
            }
        }
    }

    public void Server(){
        try (ServerSocket serverSocket = new ServerSocket(25556)){
            System.out.println("Server is listening");
            while(true){

                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String text;
                text = reader.readLine();

                char ch1 = text.charAt(0);
                Integer row = Character.getNumericValue(ch1); //Fila

                String colstr = text.substring(text.lastIndexOf("c")+1);
                Integer col = Integer.parseInt(colstr.trim()); //Columna

                bloquesL[row][col] = null;


                System.out.println(text);
                game.repaint();
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("BreakOuTEC Observador"); 
        game = new ClienteObs();
        frame.add(game);
        frame.setSize(1195, 720);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        game.Server();
        
}
}


    
