 package GSILib.BSystem;
 
import GSILib.BModel.Document;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.connect.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
 
/**
 *
 * @author inigo
 */

public class PublicBusinessSystem extends BusinessSystem implements HumanRecGateway, ValidationGateway{

    @Override
    public Boolean addWorker(Journalist jn) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean addWorker(Photographer pg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean updateWorker(Journalist jn) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean updateWorker(Photographer pg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PrintableNews[] getPendingNews() throws RemoteException {
        
        return this.getPendingNews(super.minReviewers);
    }

    @Override
    public PrintableNews[] getPendingNews(Integer numReviewers) throws RemoteException {
        
        List<PrintableNews> pendingNews = new ArrayList();
        
        for(Document document : super.documents){
            if (document.getClass().getName().equals("GSILib.BModel.documents.PrintableNews")){
                PrintableNews printableNews = (PrintableNews) document;
                
                if(printableNews.getReviewers().length < super.minReviewers){
                    pendingNews.add(printableNews);
                }
            }
        }
        
        return pendingNews.toArray(new PrintableNews[pendingNews.size()]);
    }

    @Override
    public boolean correctNews(PrintableNews printableNews) throws RemoteException {
        printableNews.getId();
        int i = -1;
        boolean found = false;
        while(i < super.documents.size() && !found){
            i++;
            if (super.documents.get(i).getId() == printableNews.getId())
                found = true;
        }
        if (found){
            super.documents.set(i, printableNews);
            return true;
        }
        return false;                
    }

    @Override
    public boolean validateNews(PrintableNews printableNews, Journalist journalist) throws RemoteException {
        return printableNews.addReviewer(journalist);
    }
    
}
