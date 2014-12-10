/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net;

import GSILib.net.Config.ConfigHandler;
import GSILib.BSystem.BusinessSystem;
import GSILib.BTesting.EOTester;
import GSILib.net.Config.ServerConfig;
import GSILib.net.Message.Request;
import GSILib.net.Message.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: JavaDoc
 * @author Alvaro
 */
public class NewsWebServer {
    
    private BusinessSystem bs = null;
    private List<ServerThread> serverThreads = new ArrayList();
    
    /**
     * TODO: JavaDoc
     * @param bs 
     */
    public NewsWebServer(BusinessSystem bs){
        this.bs = bs;
    }
    
    /**
     * TODO: JavaDoc
     * @param port
     * @param localDir
     * @return 
     */
    public boolean run(int port, String localDir){
        
        try{
            // Creamos un nuevo servidor

            ServerThread serverThread = new ServerThread(port, localDir, this.bs);
            serverThread.start();

            // Guardamos en la lista de servidores

            this.serverThreads.add(serverThread);
            
            return true;
        }
        catch (java.net.BindException ex){
            return false;
        }
    }
    
    /**
     * TODO: JavaDoc
     */
    public void stopAll(){
        for(ServerThread serverThread : this.serverThreads){
            serverThread.interrupt();
        }
        this.serverThreads.clear();
    }

    public static class ServerThread extends Thread{
        
        private int port;
        private String localDir;
        private BusinessSystem bs;
        
        /**
         * TODO: JavaDoc
         * @param port
         * @param localDir
         * @param bs
         * @throws BindException 
         */
        public ServerThread(int port, String localDir, BusinessSystem bs) throws BindException{
            this.port = port;
            this.localDir = localDir;
            this.bs = bs;
        }
        
        /**
         * TODO: JavaDoc
         */
        public void run(){
            try {

                //------------------------------------------------------------------------------
                //  Creamos el socket
                //------------------------------------------------------------------------------

                ServerSocket serverSocket = new ServerSocket(this.port);
                System.out.println("[" + this.port + "] Servidor iniciado");
                try {
                    while (true && !this.isInterrupted()) {
                        new ClientThread(serverSocket.accept(), this.bs, this.localDir).start();
                    }
                    System.out.println("[" + this.port + "]  Servidor cerrado");
                }
                finally {
                    serverSocket.close();
                }
                
            }
            catch(BindException ex){
                System.err.println("[" +  this.port + "] Unable to create socket with this port");
            }
            catch(IOException ex) {
                System.out.println(ex);
            }
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

        /**
         * TODO: JavaDoc
         * @param socket
         * @param bs
         * @param localDir 
         */
        public ClientThread(Socket socket, BusinessSystem bs, String localDir) {

            //------------------------------------------------------------------------------
            //  Constructor del 
            //------------------------------------------------------------------------------

            this.socket = socket;
            this.bs = bs;
            this.localDir = localDir;
        }
        
        /**
         * TODO: JavaDoc
         */
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
                
                String inputLine;
                StringBuilder requestString = new StringBuilder();
                int lines = 0;
                
                try {
                    while ((inputLine = this.socketIn.readLine()) != null && inputLine.length() > 0 && lines < 100) {
                        requestString.append(inputLine + "\n");
                        lines++;
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
                
                if (request.getOrder().equals("GET")){
                    
                    // GET Request
                    
                    System.out.print(request.getReduced());
                    
                    GetHandler getHandler = new GetHandler(request, this.bs, this.localDir);
                    Response response = new Response(request.getMode(), getHandler.getStatus(), getHandler.getWebPage(), getHandler.getContentType());
                    this.socketOut.println(response);
                }
                else if (request.getOrder().equals("POST")){
                    
                    // POST Request
                    
                    PostHandler postHandler = new PostHandler(request, this.bs);
                }
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
                System.out.println(" [done]");
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
                
        //------------------------------------------------------------------------------
        //  Config (configPath)
        //------------------------------------------------------------------------------
        
        ConfigHandler configHandler = null;
        BusinessSystem bs = null;
        
        if (args.length == 1){

            // Config File

            File file = new File(args[0]);
            if (file.exists()){
                
                configHandler = new ConfigHandler(args[0]);
                
                if (! configHandler.getLoadDataPath().isEmpty()){
                    
                    // Se ha indicado un XML
                    
                    file = new File(configHandler.getLoadDataPath());
                    if (file.exists()){
                    
                        // Carga un BS desde el XML

                        bs = BusinessSystem.loadFromFileXML(file); 
                    }
                    else{
                        
                        // No existe el archivo XML

                        System.err.println("LoadData File does not exists");
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

            configHandler = new ConfigHandler();
            bs = EOTester.getTestingBusinessSystem();
        }
        else{
            System.err.println("Incorrect number of arguments");
            exit(0);
        }
        
        //------------------------------------------------------------------------------
        //  Create server threads
        //------------------------------------------------------------------------------
        
        NewsWebServer newsWebServer = new NewsWebServer(bs);
        
        ServerConfig[] serverConfigs = configHandler.getServerConfigs();
        if (serverConfigs != null){
            for(ServerConfig serverConfig : serverConfigs){
                if ((new File(serverConfig.getLocalDir()).isDirectory())){
                    
                    // Creamos un nuevo Thread de servidor
                    
                    newsWebServer.run(serverConfig.getPort(), serverConfig.getLocalDir());
                }
                else{
                    
                    // No se ha especificado un localDir válido

                    System.err.println("[" + serverConfig.getPort() + "] LocalDir is not a valid directory (" + serverConfig.getLocalDir() + ")");
                }
            }
        }
    }
}
