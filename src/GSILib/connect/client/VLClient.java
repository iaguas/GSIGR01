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
    public static void main(String[] args) throws RemoteException, IOException {
        
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
        
        int option = Menu.getVLOption();
        
        System.out.println("Opcion: " + option);
        
        System.out.println("Retrieving list of news...");
        for (PrintableNews printableNews : validation.getPendingNews()){
            System.out.println(printableNews.toXML());
        }
    }
}