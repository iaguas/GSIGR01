/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
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
        
        // Leemos por teclado a ip
        
        System.out.print("Please input the addess of the machine: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String remoteMachine;
        try {
            remoteMachine = br.readLine();
        } catch (IOException ioe) {
            System.out.println("Exception when reading : " + ioe.getMessage());
            remoteMachine="localhost";
        }
        
        System.out.println("---------------");
        System.out.println("***   RMI   ***");
        System.out.println("---------------");
        
        // Nuevo Joournalist
        
        ArrayList interestsOfAlvaro = new ArrayList();
        
        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro Octal", "27/12/1993", interestsOfAlvaro);
        
        // RMI stuff
        
        try {
            // Nos conectamos
            
            Registry registry = LocateRegistry.getRegistry(remoteMachine);
            
            // Enlazamos el objeto remoto como local
            
            HumanRecGateway human = (HumanRecGateway) registry.lookup("Human");
            
            // Done! start coding
            
            System.out.println("Añadiendo Journalist... " + human.addWorker(journalistAlvaro));
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Exception in connection : " + ex.getMessage());
        }
    }
}
