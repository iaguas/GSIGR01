/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

/**
 * TODO: JavaDoc
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class WebServer {

    private static final int PUERTO = 8080;
    private static HashSet<PrintWriter> clients = new HashSet<PrintWriter>();

    public static void main(String[] args) throws Exception {
        System.out.println("[status] Servidor iniciado");

        //------------------------------------------------------------------------------
        //  Creamos el socket
        //------------------------------------------------------------------------------

        ServerSocket socket = new ServerSocket(PUERTO);
        try {
            while (true) {
                new Gestor(socket.accept()).start();
            }
        }
        finally {
            socket.close();
        }
    }
    
    /**
     * TODO: JavaDoc
     */
    private static class Gestor extends Thread {

        //------------------------------------------------------------------------------
        //  Variables
        //------------------------------------------------------------------------------

        private Socket socket;
        private BufferedReader socketIn;
        private PrintWriter socketOut;

        public Gestor(Socket socket) {

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
                
                response.setHTML("<html><body>hola</body></html>");
                
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
