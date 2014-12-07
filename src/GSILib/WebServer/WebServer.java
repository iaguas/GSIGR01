/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.WebServer;

import GSILib.BSystem.BusinessSystem;
import GSILib.BTesting.EOTester;
import GSILib.WebServer.Message.Request;
import GSILib.WebServer.Message.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO: JavaDoc
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class WebServer {

    private static int port = 8080;
    private static String localDir = "web/";

    public static void main(String[] args) throws Exception {
        
        //------------------------------------------------------------------------------
        //  Config (configPath, xmlLoad)
        //------------------------------------------------------------------------------

        ConfigHandler configHandler;
        BusinessSystem bs = null;
        
        if (args.length == 1){
            
            // Config File
            
            File file = new File(args[0]);
            if (file.exists()){
                configHandler = new ConfigHandler(args[0]);
                
                if (configHandler.getPort() != 0){
                    
                    // Load Port
                    
                    port = configHandler.getPort();
                }  
                if (configHandler.getLocalDir() != null){
                    
                    // Local Directory
                    
                    localDir = configHandler.getLocalDir();
                }
                if (configHandler.getLoadDataPath() != null){
                    
                    // Load Data
                    
                    file = new File(configHandler.getLoadDataPath());
                    if (file.exists()){
                        
                        // Carga un BS desde el XML
                        
                        bs = BusinessSystem.loadFromFileXML(file);
                
                        System.out.println("[done] Data loaded");
                    }
                    else{
                        System.err.println("LoadData file doesn't exists");
                        exit(0);
                    }
                }
                else{
                    
                    // Load BusinessSystem from testing class
            
                    bs = EOTester.getTestingBusinessSystem();
                }
                
                System.out.println("[done] Config loaded");
            }
            else{
                System.err.println("ConfigFile does not exist");
                exit(0);
            }
        }
        else if (args.length == 0){
            
            // Load BusinessSystem from testing class
            
            bs = EOTester.getTestingBusinessSystem();
        }
        else{
            System.err.println("Incorrect number of arguments");
            exit(0);
        }
        
        //------------------------------------------------------------------------------
        //  Creamos el socket
        //------------------------------------------------------------------------------

        ServerSocket socket = new ServerSocket(port);
        System.out.println("[status] Servidor iniciado");
        try {
            while (true) {
                new ClientThread(socket.accept(), bs, localDir).start();
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
        
        private BusinessSystem bs;
        private String localDir;

        public ClientThread(Socket socket, BusinessSystem bs, String localDir) {

            //------------------------------------------------------------------------------
            //  Constructor del socket
            //------------------------------------------------------------------------------

            this.socket = socket;
            this.bs = bs;
            this.localDir = localDir;
        }
        public void run() {
            try {
                
                System.out.println("[status] Cliente conectado");

                //------------------------------------------------------------------------------
                //  Creamos los objetos para mandar y recibir mensajes
                //------------------------------------------------------------------------------

                this.socketIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.socketOut = new PrintWriter(this.socket.getOutputStream(), true);

                //------------------------------------------------------------------------------
                //  Leemos el mensaje del cliente
                //------------------------------------------------------------------------------
                
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
                
                ContentHandler contentHandler = new ContentHandler(request.getPath(), this.bs, this.localDir);
        
                Response response = new Response(request.getMode(), contentHandler.getStatus(), contentHandler.getWebPage(), contentHandler.getContentType());
                
                this.socketOut.println(response);
            }
            catch(IOException ex) {
                System.out.println(ex);
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
                System.out.println("[status] Cliente desconectado");
            }
        }
    }
}
