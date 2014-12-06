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
import GSILib.BTesting.UselessDataTesting;
import GSILib.WebServer.Modelers.ContentHandler;
import GSILib.WebServer.Modelers.WebPage;
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
                
                ContentHandler contentHandler = new ContentHandler(request.getPath());
        
                Response response = new Response(request.getMode(), contentHandler.getStatus(), contentHandler.getWebPage().toString());
                
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
