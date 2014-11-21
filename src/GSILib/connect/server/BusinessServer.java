/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.server;

import GSILib.BSystem.BusinessSystem;
import GSILib.BSystem.EditorialOffice;
import GSILib.BSystem.PublicBusinessSystem;
import GSILib.connect.HumanRecGateway;
import GSILib.connect.ValidationGateway;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Alvaro
 */
public class BusinessServer {
    private static int RMI_PORT=1099;
    
    public static void main(String[] args) throws RemoteException  {
        
        // Creamos el security manager
        
        /*  Da problemas
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/
        
        // Creamos el objeto remoto
        
        PublicBusinessSystem pbs = new PublicBusinessSystem();
        
        Remote remote = UnicastRemoteObject.exportObject(pbs,0);
        HumanRecGateway stubHuman = (HumanRecGateway) remote;
        ValidationGateway stubValidation = (ValidationGateway) remote;
        
        // Publicamos los interfaces
        
        try{
            
            // Creamos el registry
            
            System.out.print("Creating registry...");
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            System.out.println(" [done]");
       
            // Lanzamos el registry Human
            
            System.out.print("Launching registry...");
            registry.rebind("Human", stubHuman);
            System.out.println(" [done]");
            
            // Lanzamos el registry Validation
            
            System.out.print("Launching registry...");
            registry.rebind("Validation", stubValidation);
            System.out.println(" [done]");
        }catch(RemoteException re){
            System.out.println("RMI Error in publishing the stub: " + re.getMessage());
        }
    }
}
