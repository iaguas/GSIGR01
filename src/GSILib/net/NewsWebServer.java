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
    public boolean run(int port, String domain){
        try{
            // Creamos un nuevo servidor
            ServerThread serverThread = new ServerThread(port, domain, this.bs);
            serverThread.start();

            // Guardamos en la lista de servidores
            this.serverThreads.add(serverThread);
            
            // Devolvemos que todo ha ido bien.
            return true;
        }
        catch (java.net.BindException ex){
            // Si ha habido algún problema, devolvemos que algo no ha ido bien.
            return false;
        }
    }
    
    /**
     * TODO: JAVADOC
     * @param domain
     * @return 
     */
    public boolean stop(String domain){
        // Recoremos la lista de servidores buscando el que nos interesa.
        for(ServerThread serverThread : this.serverThreads){
            // Cuando encontramos el servidor, lo paramos y lo eliminamos.
            if(serverThread.getDomain().equals(domain)){
                serverThread.interrupt(); // TODO: Revisar que no lance exception.
                serverThreads.remove(serverThread);
                return true;
            }
        }
     
        // Si no encontramos ningun servidor en ejecución de ese dominio.
        return false;
    }
    
    /**
     * TODO: JavaDoc
     */
    public void stopAll(){
        // Recoremos la lista de servidores para parar cada uno de ellos.
        for(ServerThread serverThread : this.serverThreads){
            serverThread.interrupt();
        }
        
        // Limpiamos la lista de servidores dejándola vacía.
        this.serverThreads.clear();
    }

    /** 
     * Clase interna ServerThread
     * TODO: JAVADOC
     */
    public static class ServerThread extends Thread{
        // Parámetros de la clase NewsWebServer
        private final int port;
        private final String domain;
        private final BusinessSystem bs;
        
        /**
         * TODO: JavaDoc
         * @param port
         * @param domain
         * @param bs
         * @throws BindException 
         */
        public ServerThread(int port, String domain, BusinessSystem bs) throws BindException{
            // Incorporamos los datos básicos del servidor.
            this.port = port;
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
                // Creamos el socket y lo indicamos por consola.
                ServerSocket serverSocket = new ServerSocket(this.port);
                System.out.println("[" + this.port + "] Servidor iniciado");
                
                // Comenzamos un bucle infinito de atención a los clientes.
                try {
                    while (true && !this.isInterrupted()) { // TODO: Revisar condición.
                        new ClientThread(serverSocket.accept(), this.bs, this.domain).start();
                    }
                    System.out.println("[" + this.port + "]  Servidor cerrado");
                }
                // Siempre que se destruya la instancia, destruiremos el socket.
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

        // Parámetros de la clase NewsWebServer
        private final Socket socket;
        private BufferedReader socketIn;
        private PrintWriter socketOut;
        private final BusinessSystem bs;
        private final String domain;

        /**
         * TODO: JavaDoc
         * @param socket
         * @param bs
         * @param localDir 
         */
        public ClientThread(Socket socket, BusinessSystem bs, String domain) {
            // Asignamos los datos básicos de cliente a atender en el servidor.
            this.socket = socket;
            this.bs = bs;
            this.domain = domain;
        }
        
        /**
         * TODO: JavaDoc
         */
        @Override
        public void run() {
            try {
                // Creamos los objetos socket para mandar y recibir mensajes
                this.socketIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.socketOut = new PrintWriter(this.socket.getOutputStream(), true);

                // Leemos el mensaje del cliente  
                String inputLine;
                StringBuilder requestString = new StringBuilder();
                int lines = 0, contentLength = 0;
                
                try {
                    // Para poder tratar el método POST, hacemos el análisis de 
                    // la entrada
                    while ((inputLine = this.socketIn.readLine()) != null && ! inputLine.equals("") && lines < 100) {
                        //
                        requestString.append(inputLine + "\n");
                        lines++;
                        // Guardamos la longitud del mensaje HTTP cuando se indica.
                        if (inputLine.startsWith("Content-Length: ")) {
                            contentLength = Integer.parseInt(inputLine.substring("Content-Length: ".length()));
                        }
                    }
                    // Guardamos las variables POST cuando existan.
                    if (requestString.toString().startsWith("POST")){
                        // Lectura de las variables POST
                        char[] content = new char[contentLength];
                        this.socketIn.read(content);
                        requestString.append("POST-Data: " + new String(content) + "\n");
                    }
                }
                catch (IOException e) {
                    System.err.println("ERROR: " + e);
                }
                
                // Creamos la petición con el análisis inicial de la entrada.
                Request request = new Request(requestString.toString());
                request.processRequest();
                
                // Respuesta al cliente
                System.out.print(request.getReduced());
                Response response;
                
                if (request.getOrder().equals("GET")){
                    // Tratamiento de las peticiones GET.
                    GetHandler getHandler = new GetHandler(this.bs, this.domain);
                    getHandler.processGetPetition(request);
                    response = new Response(request.getMode(), getHandler.getStatus(), getHandler.getWebPage(), getHandler.getContentType());
                }
                else if (request.getOrder().equals("POST")){
                    // Tratamiento de las peticiones POST.
                    PostHandler postHandler = new PostHandler(this.bs, this.domain);
                    postHandler.processPostPetition(request);
                    response = new Response(request.getMode(), postHandler.getStatus(), postHandler.getWebPage());
                }
                else{
                    // Error
                    response = null;
                }
                // Envío de la respuesta.
                this.socketOut.println(response);
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (JSONException ex) {
                Logger.getLogger(NewsWebServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                // Si este cliente se desconecta, el hilo se cerrará.
                
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
                // Como no disponemos de un archivo de configuración...
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
        
        // Creamos un nuevo servidor maestro.
        NewsWebServer newsWebServer = new NewsWebServer(bs);
        
        // Analizamos la configuración y si es correcta la aplicamos.
        ServerConfig[] serversConfig = configHandler.getServersConfig();
        if (serversConfig != null){
            for(ServerConfig serverConfig : serversConfig){
                if ((new File(serverConfig.getDomain()).isDirectory())){
                    // Creamos un nuevo servidor con las indicaciones de configuración.
                    newsWebServer.run(serverConfig.getPort(), serverConfig.getDomain());
                }
                else{
                    // No se ha especificado un dominio válido
                    System.err.println("[" + serverConfig.getPort() + "] Domain is not a valid directory (" + serverConfig.getDomain() + ")");
                }
            }
        }
    }
}
