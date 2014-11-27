<<<<<<< HEAD
/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.connect.client;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.connect.HumanRecGateway;
import GSILib.connect.ValidationGateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 *
 * @author Iñaki
 */
public class VLClient {
    private static int RMI_PORT=1099;
    
    /**
     * TODO: JavaDoc
     * @param args 
     */
    public static void main(String[] args) throws RemoteException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Leemos por teclado a ip
        
        System.out.print("Introduzca la ip del servidor: ");
        String remoteMachine;
        try {
            remoteMachine = br.readLine();
            if (remoteMachine.equals(""))
                remoteMachine = "localhost";
        } catch (IOException ioe) {
            System.out.println("Exception when reading: " + ioe.getMessage());
            remoteMachine="localhost";
        }
        
        // Leemos por teclado el puerto
        
        System.out.print("Introduzca el puerto de servidor: ");
        int port;
        try{
            port = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe){
            port = 1099;
        } catch (IOException ioe){
            System.out.println("Exception en la lectura: " + ioe.getMessage());
            port = 1099;
        }
        
        // Leemos por teclado el tag
        
        System.out.print("Introduzca el tag del objeto remoto: ");
        String tag;
        try{
            tag = br.readLine();
            if (tag.equals(""))
                tag = "null";
        }
        catch (IOException ioe){
            System.out.println("Exception en la lectura: " + ioe.getMessage());
            tag = "null";
        }
        
        // Creamos un cliente
        
        RMIClient client = new RMIClient(remoteMachine, port, tag);
        
        ValidationGateway validation = (ValidationGateway) client.getRemoteObject();
        
        if (validation == null){
            System.err.println("No se pudo encontrar un objeto remoto (" + remoteMachine + ":" + port + "-" + tag + ")");
            exit(0);
        }
        
        System.out.println("---------------");
        System.out.println("*** Testing ***");
        System.out.println("---------------");
        
        System.out.println("Retrieving list of news...");
        for (PrintableNews printableNews : validation.getPendingNews()){
            System.out.println(printableNews.toXML());
        }
    }
}
=======
/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.client;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.connect.ValidationGateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Alvaro
 */
public class VLClient {
    
    public static void main(String[] args) {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Leemos por teclado a ip
        
        System.out.print("Introduzca la ip del servidor: ");
        String remoteMachine;
        try {
            remoteMachine = br.readLine();
            if (remoteMachine.equals(""))
                remoteMachine = "localhost";
        } catch (IOException ioe) {
            System.out.println("Exception when reading: " + ioe.getMessage());
            remoteMachine="localhost";
        }
        
        // Leemos por teclado el puerto
        
        System.out.print("Introduzca el puerto de servidor: ");
        int port;
        try{
            port = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe){
            port = 1099;
        } catch (IOException ioe){
            System.out.println("Exception en la lectura: " + ioe.getMessage());
            port = 1099;
        }
        
        // Leemos por teclado el tag
        
        System.out.print("Introduzca el tag del objeto remoto: ");
        String tag;
        try{
            tag = br.readLine();
            if (tag.equals(""))
                tag = "null";
        }
        catch (IOException ioe){
            System.out.println("Exception en la lectura: " + ioe.getMessage());
            tag = "null";
        }
        
        System.out.println("---------------");
        System.out.println("***   RMI   ***");
        System.out.println("---------------");
        
        /* Existen las siguientes opciones para el cliente que se conecte 
       mediante este stub:
       1. Corregir una noticia, subiendo una nueva que la sustituya
       2. Asociar un Journalist a un PrintableNews
       3. Mostrar las noticias cumpla alguna condición
           3.1. Que no alcance el mínimo número de reviewers
           3.2. Que tenga, a lo sumo, un cierto número de reviewers pasado por parámetro

       */
        MenuVLClient.display();

        
        // Nueva noticia para actualizar noticia antigua
        PrintableNews pn_vieja = new PrintableNews("old_headline","Esto es una noticia vieja de prueba",null);
        
        // RMI stuff
        
        try {
            // Nos conectamos
            
            Registry registry = LocateRegistry.getRegistry(remoteMachine, port);
            
            // Enlazamos el objeto remoto como local
           
            ValidationGateway validation = (ValidationGateway) registry.lookup(tag);
            
            // Done! start coding
            
            System.out.println("Añadiendo Journalist... " + validation.correctNews(pn_vieja));
        } catch (ConnectException ex){
            System.err.println("No se pudo encontrar el servidor: " + remoteMachine + ":" + port);
        } catch (RemoteException ex) {
            System.err.println("Exception in connection : " + ex.getMessage());
        } catch (NotBoundException ex){
            System.err.println("No se pudo encontrar el tag: " + tag);
        }
    }
}
>>>>>>> FETCH_HEAD
