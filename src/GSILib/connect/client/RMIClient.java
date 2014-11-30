/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.client;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class implements a RMI client as general in order to be able to use with 
 * any registry. It makes it possible returning a remote object.
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class RMIClient {
    
    private Registry registry;
    private Remote remote;
   
    /**
     * Empty class constructor
     */
    public RMIClient(){
        
        // Constructor nulo
    }
    
    /**
     * This constructor creates a RMI session for a client
     * @param remoteMachine Address for the remote machine
     * @param port Connection port
     * @param tag Tag for the desired client stub
     * @throws RemoteException handles errors on RMI session creation
     */
    public RMIClient(String remoteMachine, int port, String tag) throws RemoteException{
        
        this.conect(remoteMachine, port);
        this.lookup(tag);
    }
    /**
     * This method allows to establish a generic RMI session (without tag)
     * @param remoteMachine Address for the remote machine
     * @param port Connection port
     * @return True if connection made correctly, false otherwise
     * @throws RemoteException handles errors associated with RMI
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
     * This method allows to establish a generic RMI session (without tag, using default port)
     * @param remoteMachine Address for the remote machine
     * @return True if connection made correctly, false otherwise
     * @throws RemoteException handles errors associated with RMI
     */
    public boolean conect(String remoteMachine) throws RemoteException{
        return this.conect(remoteMachine, 1099);
    }
    
    /**
     * This method ensures the correct selection of the tag
     * @param tag Tag for the desired client stub
     * @return A new remote object with you can use as a client.
     * @throws RemoteException will handle errors related to bad tag selection
     */
    public Remote lookup(String tag) throws RemoteException{
        try{
            this.remote = this.registry.lookup(tag);
            return this.remote;
        } catch (NotBoundException | RemoteException ex){
            return null;
        }
    }
    
    /**
     * Returns a Remote type object
     * @return Remote type object
     */
    public Remote getRemoteObject(){
        return this.remote;
    }
}