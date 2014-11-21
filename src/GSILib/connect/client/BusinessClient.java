/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.connect.client;

import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.EditorialOffice;
import GSILib.connect.HumanRecGateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 *
 * @author Alvaro
 */
public class BusinessClient {
    
    public static void main(String[] args) {
        
        // Step 1- Reading from the keyboard the address of the remote machine
        
        System.out.print("Please input the addess of the machine: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String remoteMachine;
        try {
            remoteMachine = br.readLine();
        } catch (IOException ioe) {
            System.out.println("Exception when reading : "+ioe.getMessage());
            remoteMachine="localhost";
        }
        
        System.out.println("---------------");
        System.out.println("***  Store  ***");
        System.out.println("---------------");
        
        System.out.println("\n+ BModel");
        
        // Nuevo Joournalist
        
        ArrayList interestsOfAlvaro = new ArrayList();
        
        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro Octal", "27/12/1993", interestsOfAlvaro);
        
        try {
            // Step 2-  Connecting to the remote registry
            Registry registry = LocateRegistry.getRegistry(remoteMachine);
            // Step 3- Linking the remote object as if it was a local one
            HumanRecGateway editorial = (HumanRecGateway) registry.lookup("Human");
            // Step 4- Just using the object!
            System.out.println("Añadiendo Journalist... " + editorial.addWorker(journalistAlvaro));
        } catch (RemoteException | NotBoundException ex) {
            System.out.println("Exception in connection : "+ex.getMessage());
        }
    }
}
