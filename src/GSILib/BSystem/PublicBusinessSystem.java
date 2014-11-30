/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BSystem;
 
import GSILib.BModel.Document;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.connect.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
 
/**
 *
 * @author inigo
 */

public class PublicBusinessSystem extends BusinessSystem implements HumanRecGateway, ValidationGateway{

    @Override
    public Boolean addWorker(Journalist jn) throws RemoteException {
        return this.addJournalist(jn);
    }

    @Override
    public Boolean addWorker(Photographer pg) throws RemoteException {
        return this.addPhotographer(pg);
    }

    @Override
    public Boolean updateWorker(Journalist newJournalist) throws RemoteException {
        
        // Buscamos el Journalist
        
        Journalist journalist = this.findJournalist(newJournalist.getId());
        
        if (journalist != null){
            journalist.copyValuesFrom(newJournalist);
            return true;
        }
        
        return false;
    }

    @Override
    public Boolean updateWorker(Photographer newPhotographer) throws RemoteException {
        
        // Buscamos el Photographer
        
        Photographer photographer = this.findPhotographer(newPhotographer.getId());
        
        if (photographer != null){
            photographer.copyValuesFrom(newPhotographer);
            return true;
        }
        
        return false;
    }

    @Override
    public PrintableNews[] getPendingNews() throws RemoteException {
        
        return this.getPendingNews(super.minReviewers);
    }

    @Override
    public PrintableNews[] getPendingNews(Integer numReviewers) throws RemoteException {
        
        List<PrintableNews> pendingNews = new ArrayList();
        int reviewers;
        
        for(Document document : super.documents){
            if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                
                // Tratamos solo las PrintableNews
                
                PrintableNews printableNews = (PrintableNews) document;
                
                if(printableNews.getReviewers() == null)
                    reviewers = 0;
                else
                    reviewers = printableNews.getReviewers().length;
                
                if(reviewers < numReviewers){
                    pendingNews.add(printableNews);
                }
            }
        }
        
        if (pendingNews.isEmpty())
            return null;
        return pendingNews.toArray(new PrintableNews[pendingNews.size()]);
    }

    @Override
    public boolean correctNews(PrintableNews printableNews) throws RemoteException {
        int i = 0;
        boolean found = false;
        while(i < super.documents.size() && !found){
            if (super.documents.get(i).getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                if (Objects.equals(super.documents.get(i).getId(), printableNews.getId())){
                    ((PrintableNews) super.documents.get(i)).copyValuesFrom(printableNews);
                    found = true;
                }
            }
            i++;
        }
        return found;                
    }

    @Override
    public boolean validateNews(PrintableNews printableNews, Journalist journalist) throws RemoteException {
        int i = 0;
        boolean found = false;
        while(i < super.documents.size() && !found){
            if (super.documents.get(i).getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                if (Objects.equals(super.documents.get(i).getId(), printableNews.getId())){
                    ((PrintableNews) super.documents.get(i)).addReviewer(journalist);
                    found = true;
                }
            }
            i++;
        }
        return found;  
    }
}
