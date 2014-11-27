/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.client;

import GSILib.BModel.workers.Journalist;
import GSILib.connect.HumanRecGateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 *
 * @author Alvaro
 */
public class RMIClient {
    
    private Registry registry;
    private Remote remote;
   
    /**
     * TODO: JavaDoc
     * @param remoteMachine
     * @param port
     * @param tag 
     */
    public RMIClient(){
        
        // Constructor nulo
    }
    
    /**
     * TODO: JavaDoc
     * @param remoteMachine
     * @param port
     * @param tag
     * @throws RemoteException 
     */
    public RMIClient(String remoteMachine, int port, String tag) throws RemoteException{
        
        this.conect(remoteMachine, port);
        this.lookup(tag);
    }
    /**
     * TODO: JavaDoc
     * @param remoteMachine
     * @param port
     * @return
     * @throws RemoteException 
     */
    public boolean conect(String remoteMachine, int port) throws RemoteException{
        
        try{
            this.registry = LocateRegistry.getRegistry(remoteMachine, port);
            return true;
        } catch (ConnectException ex){
            return false;
        }
    }
    
    /**
     * TODO: JavaDoc
     * @param remoteMachine
     * @return
     * @throws RemoteException 
     */
    public boolean conect(String remoteMachine) throws RemoteException{
        return this.conect(remoteMachine, 1099);
    }
    
    /**
     * TODO: JavaDoc
     * @param tag
     * @return
     * @throws RemoteException 
     */
    public Remote lookup(String tag) throws RemoteException{
        try{
            this.remote = this.registry.lookup(tag);
            return this.remote;
        } catch (NotBoundException ex){
            return null;
        } catch (RemoteException ex){
            return null;
        }
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public Remote getRemoteObject(){
        return this.remote;
    }
}