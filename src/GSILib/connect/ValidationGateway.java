
package GSILib.connect;

import GSILib.BModel.workers.Journalist;
import GSILib.BModel.documents.visualNews.PrintableNews;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This inteface offers an access for listing and validating news in our Editorial System.
 * @author carlos.lopez
 */
public interface ValidationGateway extends Remote{
    
    /**
     * Lists all the printable news in the system which have not been reviewed by
     *  the minimum number of reviewers.
     * @return The list of PrintableNews, in no specific order.
     * @throws RemoteException If something goes wrong in the connection/serialization.
     */
    public PrintableNews[] getPendingNews() throws RemoteException;
    
    /**
     * Lists all the printable news in the system which have been reviewed by,
     *  at most, maxReviewers.
     * @param numReviewers Maximum number of reviewers in the system.
     * @return The list of PrintableNews, in no specific order.
     * @throws RemoteException If something goes wrong in the connection/serialization.
     */
    public PrintableNews[] getPendingNews(Integer numReviewers)  throws RemoteException;
   
    
    /**
     * Uploads a revised version of the news, which replaces the old one. The
     *  list of reviewers is then cleared (set to empty).
     * @param pn    The new version of the news
     * @return  True if and only if the operation could be completed.
     * @throws RemoteException If something goes wrong in the connection/serialization.
     */
    public boolean correctNews(PrintableNews pn)  throws RemoteException;
    
    /**
     * Adds the Journalist jn as reviewer in the PrintableNews pn
     * @param pn    The PrintableNews that has been reviewed by the journalist
     * @param jn    The journalist him/herself
     * @return  True if and only if the operation could be completed, the PrintableNews exists,
     *          it has not been modified and the journalist was not listed as reviewer already.
     * @throws RemoteException If something goes wrong in the connection/serialization.
     */
    public boolean validateNews(PrintableNews pn,Journalist jn)  throws RemoteException;

    
}
