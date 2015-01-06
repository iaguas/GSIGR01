// ******************************** REVISADA **********************************
/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net;

import GSILib.net.Config.ConfigHandler;
import GSILib.BSystem.BusinessSystem;
import GSILib.BSystem.EditorialOffice;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;


// ESTRUCTURA DE CLASES DEL SERVIDOR.
//  NewsWebServer --------------------------- ServerThread ------------------------- ClientThread
//         (1 servidor por cada dominio y puerto)     (1 hilo de atención a cada cliente HTTP)

/**
 * TODO: JavaDoc
 * @author Alvaro
 */
public class NewsWebServer {
    // Parámetros de la clase NewsWebServer
    private BusinessSystem bs = null;
    private final List<ServerThread> serverThreads = new ArrayList();
    
    /**
     * TODO: JavaDoc
     * @param eo 
     */
    public NewsWebServer(EditorialOffice eo){
        this.bs = (BusinessSystem) eo;
    }
    
    /**
     * TODO: JavaDoc
     * @param port
     * @param domain
     * @return 
     */
    public boolean run(int port, String domain, String localDir){
        try{
            
            ServerThread serverThread = new ServerThread(port, domain, localDir, this.bs);
            serverThread.start();
            
            this.serverThreads.add(serverThread);
            
            return true;
        }
        catch (java.net.BindException ex){
            return false;
        }
    }
    
    /**
     * TODO: JAVADOC
     * @param port
     * @return 
     */
    public boolean stop(int port){
        
        for(ServerThread serverThread : this.serverThreads){
            
            if(serverThread.port == port){

                serverThread.interrupt();
                this.serverThreads.remove(serverThread);
                return true;
            }
        }
        
        return false;
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

    /** 
     * Clase interna ServerThread
     * TODO: JAVADOC
     */
    public static class ServerThread extends Thread{
        
        private final int port;
        private final String domain, localDir;
        private final BusinessSystem bs;
        
        /**
         * TODO: JavaDoc
         * @param port
         * @param domain
         * @param bs
         * @throws BindException 
         */
        public ServerThread(int port, String domain, String localDir, BusinessSystem bs) throws BindException{
            
            this.port = port;
            this.localDir = localDir;
            this.domain = domain;
            this.bs = bs;
        }
        
        /** 
         * TODO: JAVADOC
         * @return 
         */
        public String getDomain(){
            // Devolvemos el dominio del servidor de este hilo.
            return domain;
        }
        
        /**
         * TODO: JavaDoc
         */
        @Override
        public void run(){
            try {
                
                ServerSocket serverSocket = new ServerSocket(this.port);
                System.out.println("[" + this.port + "] Servidor iniciado");
                
                try {
                    while (true && !this.isInterrupted()) { // TODO: Revisar condición.
                        new ClientThread(serverSocket.accept(), this.bs, this.domain, this.localDir).start();
                    }
                    System.out.println("[" + this.port + "]  Servidor cerrado");
                }
                
                finally {
                    serverSocket.close();
                }   
            }
            catch(BindException ex){
                System.err.println("[" +  this.port + "] Unable to create socket with this port\n" + ex);
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
        
        private final Socket socket;
        private BufferedReader socketIn;
        private PrintWriter socketOut;
        private final BusinessSystem bs;
        private final String domain, localDir;

        /**
         * TODO: JavaDoc
         * @param socket
         * @param bs
         * @param localDir 
         */
        public ClientThread(Socket socket, BusinessSystem bs, String domain, String localDir) {
            
            this.socket = socket;
            this.bs = bs;
            this.domain = domain;
            this.localDir = localDir;
        }
        
        /**
         * TODO: JavaDoc
         */
        @Override
        public void run() {
            try {
                
                this.socketIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.socketOut = new PrintWriter(this.socket.getOutputStream(), true);
                
                String inputLine;
                StringBuilder requestString = new StringBuilder();
                int lines = 0, contentLength = 0;
                
                try {
                    
                    while ((inputLine = this.socketIn.readLine()) != null && ! inputLine.equals("") && lines < 100) {
                        
                        requestString.append(inputLine + "\n");
                        lines++;
                        
                        if (inputLine.startsWith("Content-Length: ")) {
                            contentLength = Integer.parseInt(inputLine.substring("Content-Length: ".length()));
                        }
                    }
                    
                    if (requestString.toString().startsWith("POST")){
                        
                        char[] content = new char[contentLength];
                        this.socketIn.read(content);
                        requestString.append("POST-Data: " + new String(content) + "\n");
                    }
                }
                catch (IOException e) {
                    System.err.println("ERROR: " + e);
                }
                
                Request request = new Request(requestString.toString());
                request.processRequest();
                
                System.out.print(request.getReduced());
                Response response;
                
                if (request.getOrder().equals("GET")){
                   
                    GetHandler getHandler = new GetHandler(this.bs, this.domain, this.localDir);
                    getHandler.processGetPetition(request);
                    response = new Response(request.getMode(), getHandler.getStatus(), getHandler.getWebPage(), getHandler.getContentType());
                }
                else if (request.getOrder().equals("POST")){
                    
                    PostHandler postHandler = new PostHandler(this.bs, this.localDir);
                    postHandler.processPostPetition(request);
                    response = new Response(request.getMode(), postHandler.getStatus(), postHandler.getWebPage());
                }
                else{
                    
                    // Error
                    
                    response = null;
                }
                
                // Send Response
                
                this.socketOut.println(response);
                
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (JSONException ex) {
                Logger.getLogger(NewsWebServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                
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
    
    /** 
     * TODO: JAVADOC
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        
        // Creamos los manejadores de la configuración y el sistema.
        
        ConfigHandler configHandler = null;
        BusinessSystem bs = null;
        
        if (args.length == 1){

            // Config File
            File file = new File(args[0]);
            if (file.exists()){
                
                // Creamos el manejador de configuración.
                
                configHandler = new ConfigHandler();
                configHandler.setConfig(args[0]);
                
                // Comprobamos que exista el archivo de carga de datos.
                
                if (! configHandler.getLoadDataPath().isEmpty()){
                    
                    // Se ha indicado un XML, creamos el archivo
                    
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
                    
                    // Cargamos el BusinessSystem de prueba, si no hay fichero.
                    
                    bs = EOTester.getTestingBusinessSystem();
                }
                System.out.println("[done] Config loaded");
            }
            else{
                
                // Error, fichero de configuracion no existe
                
                System.err.println("ConfigFile does not exist");
                exit(0);
            }
        }
        else if (args.length == 0){
            
            // Cargamos el BusinessSystem de prueba.
            
            configHandler = new ConfigHandler();
            configHandler.setConfig();
            bs = EOTester.getTestingBusinessSystem();
        }
        else{
            
            System.err.println("Incorrect number of arguments\n");
            System.err.println("use: java_program [config_file]");
            exit(0);
        }
        
        NewsWebServer newsWebServer = new NewsWebServer(bs);
        
        ServerConfig[] serverConfigs = configHandler.getServerConfigs();
        
        if (serverConfigs != null){
            for(ServerConfig serverConfig : serverConfigs){
                if ((new File(serverConfig.getLocalDir()).isDirectory())){
                    
                    // Creamos un nuevo servidor con las indicaciones de configuración.
                    
                    newsWebServer.run(serverConfig.getPort(), serverConfig.getDomain(), serverConfig.getLocalDir());
                }
                else{
                    
                    // No se ha especificado un localDir válido
                    
                    System.err.println("[" + serverConfig.getPort() + "] Domain is not a valid directory (" + serverConfig.getLocalDir()+ ")");
                }
            }
        }
    }
}
