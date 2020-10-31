import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private Thread t;
    Bloque[][] bloquesL = new Bloque[8][14];

    public Server(Bloque[][] bloquesL) {
        this.bloquesL = bloquesL;
    }

    @Override
    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(25557)) {
            System.out.println("Server is listening");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String text;
                text = reader.readLine();

                char ch1 = text.charAt(0);
                if (ch1 == '1') {
                    // Logica de establecer el mismo puntaje para todas las columnas de la fila dada
                    Character rowch = text.charAt(2);
                    Integer row = Character.getNumericValue(rowch); // Fila
                    String puntajStr = text.substring(text.lastIndexOf("p") + 1);
                    Integer puntaje = Integer.parseInt(puntajStr.trim()); // Columna

                    for (Integer i = 0; i < 14; i++) {
                        bloquesL[row][i].puntaje = puntaje;
                    }
                }
                if (ch1 == '2') {
                    System.out.println("Entra al 2");
                    // Insertar el power up al ladrillo en la fila y columna dada
                    Character rowch = text.charAt(2);
                    Integer row = Character.getNumericValue(rowch); // Fila

                    String colch = text.substring(text.indexOf("c") + 1);
                    colch = colch.substring(0, colch.indexOf("p"));

                    Integer col = Integer.parseInt(colch.trim()); // Columna

                    String poderStr = text.substring(text.lastIndexOf("p") + 1);
                    Integer poder = Integer.parseInt(poderStr.trim()); // Poder

                    bloquesL[row][col].power = poder;
                }

                System.out.println(text);
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void start(){
        if(t == null){
            t = new Thread(this, "Server");
            t.start();
        }
    }
    
    
}
