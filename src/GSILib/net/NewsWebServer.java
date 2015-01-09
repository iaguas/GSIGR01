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
 * Main class for the web service to be instanciated in ServerThreads, and which also instanciates ClientThreads  
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class NewsWebServer {
    // Parámetros de la clase NewsWebServer
    private BusinessSystem bs = null;
    private final List<ServerThread> serverThreads = new ArrayList();
    
    /**
     * Class constructor with EditorialOffice parameter (converted to BusinessSystem)
     * @param eo Instance of interface EditorialOffice
     */
    public NewsWebServer(EditorialOffice eo){
        this.bs = (BusinessSystem) eo;
    }
    
    /**
     * ServerThread run method
     * @param port integer port number
     * @param domain domain server string
     * @param localDir local directory of files
     * @return true if and only server is run correctly
     */
    public boolean run(int port, String domain, String localDir){
        try{
            // Creamos un nuevo servidor e inicializamos.
            ServerThread serverThread = new ServerThread(port, domain, localDir, this.bs);
            serverThread.start();

            // Guardamos en la lista de servidores
            this.serverThreads.add(serverThread);

            // Devolvemos que ha ido bien.
            return true;
        }
        catch (java.net.BindException ex){
            // Si ha habido algún problema, devolvemos que algo no ha ido bien.
            return false;
        }
    }
    
    /**
     * Stops a ServerThread by a given port
     * @param port associated to a ServerThread
     * @return true if and only if serverThread is stopped and removed correctly
     */
    public boolean stop(int port){
        // Recoremos la lista de servidores buscando el que nos interesa.
        for(ServerThread serverThread : this.serverThreads){
            // Cuando encontramos el servidor, lo paramos y lo eliminamos.
            if(serverThread.port == port){
                serverThread.interrupt();
                this.serverThreads.remove(serverThread);
                return true;
            }
        }
        // Si no encontramos ningun servidor en ejecución de ese puerto.
        return false;
    }
    
    /**
     * Stops all ServerThreads.
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
     * Class ServerThread. Makes differents threads for each sever.
     */
    public static class ServerThread extends Thread{
        // Parámetros de la clase ServerThread
        private final int port;
        private final String domain, localDir;
        private final BusinessSystem bs;
        
        /**
         * ServerThread constructor, and element of the list ServerThreads in NewsWebServer
         * @param port integer port number.
         * @param domain domain server string.
         * @param localDir local directory of files.
         * @param bs business system asociate to server.
         * @throws BindException exception derived from socket (the port is not disponible).
         */
        public ServerThread(int port, String domain, String localDir, BusinessSystem bs) throws BindException{
            // Incorporamos los datos básicos del servidor.
            this.port = port;
            this.localDir = localDir;
            this.domain = domain;
            this.bs = bs;
        }
        
        /** 
         * Gets the domain of a ServerThread
         * @return domain
         */
        public String getDomain(){
            // Devolvemos el dominio del servidor de este hilo.
            return domain;
        }
        
        @Override
        public void run(){
            try {
                // Creamos el socket y lo indicamos por consola.
                ServerSocket serverSocket = new ServerSocket(this.port);
                System.out.println("[" + this.port + "] Servidor iniciado");

                // Comenzamos un bucle infinito de atención a los clientes.
                try {
                    while (true && !this.isInterrupted()) { // TODO: Revisar condición.
                        new ClientThread(serverSocket.accept(), this.bs, this.domain, this.localDir).start();
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
     * Class ClientThread. It creates a thread for each client that ask something
     * to any server.
     */
    private static class ClientThread extends Thread {
        // Parámetros de la clase ClientThread
        private final Socket socket;
        private BufferedReader socketIn;
        private PrintWriter socketOut;
        private final BusinessSystem bs;
        private final String domain, localDir;

        /**
         * Class constructor with socket, bs, and localDir parameters
         * @param socket where connect the client.
         * @param bs business system asociated with server
         * @param localDir local directory of files
         */
        public ClientThread(Socket socket, BusinessSystem bs, String domain, String localDir) {
            // Asignamos los datos básicos de cliente a atender en el servidor.
            this.socket = socket;
            this.bs = bs;
            this.domain = domain;
            this.localDir = localDir;
        }

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
                        // Añadimos formato al archivo.
                        requestString.append(inputLine + "\n");
                        lines++;
                        // Guardamos la longitud del mensaje HTTP cuando se indica.
                        if (inputLine.startsWith("Content-Length: ")) {
                            contentLength = Integer.parseInt(inputLine.substring("Content-Length: ".length()));
                        }
                    }
                    // Guardamos las variables POST cuando existan.
                    if (requestString.toString().startsWith("POST")){
                        // Lectura de las variables POST.
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
                
                // Respuesta al cliente.
                System.out.print(request.getReduced());
                Response response;
                
                if (request.getOrder().equals("GET")){
                    // Tratamiento de las peticiones GET.
                    GetHandler getHandler = new GetHandler(this.bs, this.domain, this.localDir);
                    getHandler.processGetPetition(request);
                    response = new Response(request.getMode(), getHandler.getStatus(), getHandler.getWebPage(), getHandler.getContentType());
                }
                else if (request.getOrder().equals("POST")){
                    // Tratamiento de las peticiones POST.
                    PostHandler postHandler = new PostHandler(this.bs, this.localDir);
                    postHandler.processPostPetition(request);
                    response = new Response(request.getMode(), postHandler.getStatus(), postHandler.getWebPage());
                }
                else{
                    // Error
                    response = null;
                }
            
                // Envio de la respuesta    
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
     * Main method
     * @param args arguments stack of args of main method.
     * @throws Exception An exception.
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
        
        // Creamos el servidor maestro.
        NewsWebServer newsWebServer = new NewsWebServer(bs);
        
        // Analizamos la configuración y si es correcta la aplicamos.
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
