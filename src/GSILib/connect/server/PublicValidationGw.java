package GSILib.connect.server;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.PublicBusinessSystem;
import GSILib.connect.ValidationGateway;
import java.rmi.RemoteException;
 
/**
 * This class implements the methods of ValidationGateway interface.
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */


public class PublicValidationGw implements ValidationGateway {
    
    PublicBusinessSystem pbs = new PublicBusinessSystem();
    
    public PublicValidationGw(PublicBusinessSystem pbs){
        this.pbs = pbs;
    }
    
    @Override
    public PrintableNews[] getPendingNews() throws RemoteException{
        return pbs.getPendingNews();
    }
    
    @Override
    public PrintableNews[] getPendingNews(Integer numReviewers)  throws RemoteException{
        return pbs.getPendingNews(numReviewers);
    }
   
    @Override
    public boolean correctNews(PrintableNews pn)  throws RemoteException{
        return pbs.correctNews(pn);
    }
    
    @Override
    public boolean validateNews(PrintableNews pn,Journalist jn)  throws RemoteException{
        return pbs.validateNews(pn, jn);
    }
}