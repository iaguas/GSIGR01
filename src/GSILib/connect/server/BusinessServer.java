/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.server;

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
        PublicHumanRecGw phg = new PublicHumanRecGw(pbs);
        PublicValidationGw pvg = new PublicValidationGw(pbs);
        
        Remote remote1 = UnicastRemoteObject.exportObject(phg,0);
        Remote remote2 = UnicastRemoteObject.exportObject(pvg,0);
        HumanRecGateway stubHuman = (HumanRecGateway) remote1;
        ValidationGateway stubValidation = (ValidationGateway) remote2;
        
        // Publicamos los interfaces
        
        try{
            
            // Creamos el registry
            
            System.out.print("Creating registry...");
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            System.out.println(" [done]");
       
            // Lanzamos el registry Human
            
            System.out.print("Launching (Human) registry...");
            registry.rebind("HRGateway", stubHuman);
            System.out.println(" [done]");
            
            // Lanzamos el registry Validation
            
            System.out.print("Launching (Validation) registry...");
            registry.rebind("VLGateway", stubValidation);
            System.out.println(" [done]");
        }catch(RemoteException re){
            System.out.println("RMI Error in publishing the stub: " + re.getMessage());
        }
    }
}
