package GSILib.connect.server;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.PublicBusinessSystem;
import GSILib.connect.ValidationGateway;
import java.rmi.RemoteException;
 
/**
 *
 * @author inigo
 */


public class PublicValidationGw extends PublicBusinessSystem implements ValidationGateway {
    
    @Override
    public PrintableNews[] getPendingNews() throws RemoteException{
        return this.getPendingNews();
    }
    
    @Override
    public PrintableNews[] getPendingNews(Integer numReviewers)  throws RemoteException{
        return this.getPendingNews(numReviewers);
    }
   
    @Override
    public boolean correctNews(PrintableNews pn)  throws RemoteException{
        return this.correctNews(pn);
    }
    
    @Override
    public boolean validateNews(PrintableNews pn,Journalist jn)  throws RemoteException{
        return this.validateNews(pn, jn);
    }
}