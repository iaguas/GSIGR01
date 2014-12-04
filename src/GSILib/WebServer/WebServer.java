/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.WebServer;

import GSILib.BModel.Newspaper;
import GSILib.BModel.Picture;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BSystem.BusinessSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

/**
 * TODO: JavaDoc
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class WebServer {

    private static final int PUERTO = 8080;

    public static void main(String[] args) throws Exception {
        System.out.println("[status] Servidor iniciado");

        //------------------------------------------------------------------------------
        //  Creamos el socket
        //------------------------------------------------------------------------------

        ServerSocket socket = new ServerSocket(PUERTO);
        try {
            while (true) {
                new ClientThread(socket.accept()).start();
            }
        }
        finally {
            socket.close();
        }
    }
    
    /**
     * TODO: JavaDoc
     */
    private static class ClientThread extends Thread {

        //------------------------------------------------------------------------------
        //  Variables
        //------------------------------------------------------------------------------

        private Socket socket;
        private BufferedReader socketIn;
        private PrintWriter socketOut;

        public ClientThread(Socket socket) {

            //------------------------------------------------------------------------------
            //  Constructor del socket
            //------------------------------------------------------------------------------

            this.socket = socket;
        }
        public void run() {
            try {

                //------------------------------------------------------------------------------
                //  Creamos los objetos para mandar y recibir mensajes
                //------------------------------------------------------------------------------

                this.socketIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.socketOut = new PrintWriter(this.socket.getOutputStream(), true);

                //------------------------------------------------------------------------------
                //  Leemos el mensaje del cliente
                //------------------------------------------------------------------------------

                System.out.println("---------------");
                System.out.println("*** Request ***");
                System.out.println("---------------");
                
                String inputLine;
                StringBuilder requestString = new StringBuilder();
                
                try {
                    while ((inputLine = this.socketIn.readLine()) != null && inputLine.length() > 0) {
                        requestString.append(inputLine + "\n");
                    }
                }
                catch (IOException e) {
                    System.err.println("Error: " + e);
                }
                
                System.out.println(requestString);
                
                //------------------------------------------------------------------------------
                //  Tratamos el protocolo
                //------------------------------------------------------------------------------

                Request request = new Request(requestString.toString());
                
                //------------------------------------------------------------------------------
                //  Respondemos al cliente
                //------------------------------------------------------------------------------
                
                System.out.println("----------------");
                System.out.println("*** Response ***");
                System.out.println("----------------");
        
                Response response = new Response(request.getMode(), 200);
                
                //------------------------------------------------------------------------------
                //  Testing Data
                //------------------------------------------------------------------------------
                
                // Nuevo Joournalist
        
                ArrayList interestsOfAlvaro = new ArrayList();

                interestsOfAlvaro.add("Discutir");
                interestsOfAlvaro.add("Tocar las narices");
                interestsOfAlvaro.add("Jugar al CS");

                Journalist journalistAlvaro = new Journalist("8", "Alvaro Octal", "27/12/1993", interestsOfAlvaro);

                // Nuevo Journalist

                ArrayList interestsOfPirata = new ArrayList();

                interestsOfPirata.add("Comer pasta");
                interestsOfPirata.add("beber cerveza");
                interestsOfPirata.add("Cantar canciones");
                interestsOfPirata.add("trifulcas de bar");

                Journalist journalistPirata = new Journalist("2", "Pirata", "01/01/01", interestsOfPirata);
                
                Photographer photographer = new Photographer("12", "Arguitxu Arzcarrena", "01/01/1990", "Bilbao", "Carpa");
        
                // Nueva Picture
        
                Picture pictureRed = new Picture("http://images.example.com/testRed.png", photographer);
                Picture pictureBlue = new Picture("http://images.example.com/testBlue.png", photographer);

                // Nueva PrintableNews

                PrintableNews printableNews = new PrintableNews("255Tbps: World’s fastest network could carry all of the internet’s traffic on a single fiber", "A joint group of researchers from the Netherlands and the US have smashed the world speed record for a fiber network, pushing 255 terabits per second down a single strand of glass fiber. This is equivalent to around 32 terabytes per second — enough to transfer a 1GB movie in 31.25 microseconds (0.03 milliseconds), or alternatively, the entire contents of your 1TB hard drive in about 31 milliseconds.", journalistAlvaro);
                printableNews.addReviewer(journalistPirata);
                printableNews.addPicture(pictureRed);
                printableNews.addPicture(pictureBlue);
                
                // Nuevo BusinessSystem
                
                BusinessSystem bs = new BusinessSystem();
                Date currentDate = new Date();
                bs.createNewspaper(currentDate);
                bs.addNewsToIssue(bs.getNewspaper(currentDate), printableNews);
                
                //------------------------------------------------------------------------------
                //  End Testing Data
                //------------------------------------------------------------------------------
                
                response.setHTML(bs.getNewspaper(currentDate).getHTMLPage());
                
                System.out.println(response);
                
                this.socketOut.println(response);
            }
            catch(IOException ex) {
                System.out.println("IOException capturada en run()");
            }
            finally {

                //------------------------------------------------------------------------------
                //  Si este cliente se desconecta
                //------------------------------------------------------------------------------

                try {
                    this.socket.close();
                }
                catch(IOException exc) {
                    System.out.println("Error al cerrar el socket");
                }
                System.out.println("cliente desconectado");
            }
        }
    }
}
