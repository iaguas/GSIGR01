/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.connect.client;

import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.PublicBusinessSystem;
import GSILib.connect.HumanRecGateway;
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
public class HRClient {
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
        
        HumanRecGateway human = (HumanRecGateway) client.getRemoteObject();
        
        if (human == null){
            System.err.println("No se pudo encontrar un objeto remoto (" + remoteMachine + ":" + port + "-" + tag + ")");
            exit(0);
        }
        
        System.out.println("---------------");
        System.out.println("*** Testing ***");
        System.out.println("---------------");
        
        int option = Menu.getHROption();
        
        System.out.println("Opcion: " + option);
        
        // Nuevo Journalist
        
        ArrayList interestsOfAlvaro = new ArrayList();
        
        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro", "27/12/1993", interestsOfAlvaro);
        
        System.out.println("Añadiendo Journalist [" + human.addWorker(journalistAlvaro) + "]");
        
        journalistAlvaro.setName("Alvaro Octal");
        
        System.out.println("Actualizando Journalist [" + human.updateWorker(journalistAlvaro) + "]");
    }
}
