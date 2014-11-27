/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.server;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.PublicBusinessSystem;
import GSILib.connect.HumanRecGateway;
import GSILib.connect.ValidationGateway;
import java.io.IOException;
import static java.lang.System.exit;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alvaro
 */
public class BusinessServer {

    private static int RMI_PORT=1099;
    
    private HumanRecGateway stubHuman;
    private ValidationGateway stubValidation;
    private Registry registry;
    
    public BusinessServer(int port, PublicBusinessSystem pbs) throws RemoteException{
        
        // Creamos el security manager
        
        /*  Da problemas
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/
        
        // Creamos el objeto remoto
        
        PublicHumanRecGw phg = new PublicHumanRecGw(pbs);
        PublicValidationGw pvg = new PublicValidationGw(pbs);
        
        Remote remoteHuman = UnicastRemoteObject.exportObject(phg,0);
        Remote remoteValidation = UnicastRemoteObject.exportObject(pvg,0);
        this.stubHuman = (HumanRecGateway) remoteHuman;
        this.stubValidation = (ValidationGateway) remoteValidation;
        
        // Publicamos los interfaces
        
        try{
            
            // Creamos el registry
            
            System.out.print("Creating registry...");
            this.registry = LocateRegistry.createRegistry(port);
            System.out.println(" [done]");
       
            // Lanzamos el registry Human
            
            System.out.print("Launching (Human) registry...");
            this.registry.rebind("HRGateway", this.stubHuman);
            System.out.println(" [done]");
            
            // Lanzamos el registry Validation
            
            System.out.print("Launching (Validation) registry...");
            this.registry.rebind("VLGateway", this.stubValidation);
            System.out.println(" [done]");
        }catch(RemoteException re){
            System.out.println("RMI Error in publishing the stub: " + re.getMessage());
        }
    }
    
    public static void main(String[] args) throws RemoteException, IOException, SAXException  {
        
        PublicBusinessSystem pbs = new PublicBusinessSystem();
        
        // Cargamos el servidor
        
        BusinessServer server = null;
        
        if (args.length == 1){
            
            // Si existe configuracion
            
            ConfigHandler configHandler = new ConfigHandler(args[0]);
            server = new BusinessServer(configHandler.getPort(), pbs);
        }
        else if(args.length == 0){
            
            // Configuracion por defecto
            
            server = new BusinessServer(RMI_PORT, pbs);
        }
        else{
            
            // Error en el numero de parametros
            
            System.err.println("Invalid number of arguments");
            exit(0);
        }
        
        // Nuevo Journalist
        
        /*ArrayList interestsOfAlvaro = new ArrayList();
        
        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro", "27/12/1993", interestsOfAlvaro);
        
        pbs.addJournalist(journalistAlvaro);
        pbs.insertNews(new PrintableNews("255Tbps: World’s fastest network could carry all of the internet’s traffic on a single fiber", "A joint group of researchers from the Netherlands and the US have smashed the world speed record for a fiber network, pushing 255 terabits per second down a single strand of glass fiber. This is equivalent to around 32 terabytes per second — enough to transfer a 1GB movie in 31.25 microseconds (0.03 milliseconds), or alternatively, the entire contents of your 1TB hard drive in about 31 milliseconds.", journalistAlvaro));
        */
    }
}
