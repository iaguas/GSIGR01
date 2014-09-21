/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BSystem;

import GSILib.BModel.Document;
import GSILib.BModel.Newspaper;
import GSILib.BModel.Picture;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BModel.Worker;
import GSILib.BModel.documents.Teletype;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Alvaro
 */
public class BusinessSystem implements EditorialOffice{
    
    private List<Worker> workers = new ArrayList<Worker>();
    private List<Document> documents = new ArrayList<Document>();
    private List<Picture> pictures = new ArrayList<Picture>();
    private LinkedHashMap<Date, Newspaper> newspapers = new LinkedHashMap<Date, Newspaper>();
    
    private AtomicInteger atomicInteger = new AtomicInteger();
    
    @Override
    public boolean addJournalist(Journalist jr){
        jr.setId(this.atomicInteger.getAndIncrement());
        return this.workers.add(jr);
    }

    @Override
    public boolean existJournalist(Journalist jr) {
        return this.workers.contains(jr);
    }

    @Override
    public boolean removeJournalist(Journalist jr) {
        return this.workers.remove(jr);
    }

    @Override
    public Journalist findJournalist(String ID) {
        int workersLength = workers.size();
        for (int i = 0; i < workersLength; i++) {
            if (workers.get(i).getId() == Integer.parseInt(ID)) {
                return (Journalist) workers.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean addPhotographer(Photographer pr) {
        return workers.add(pr);
    }

    @Override
    public boolean existPhotographer(Photographer pr) {
        return workers.contains(pr);
    }

    @Override
    public boolean removePhotographer(Photographer pr) {
        return workers.remove(pr);
    }

    @Override
    public Photographer findPhotographer(String ID) {
        int workersLength = workers.size();
        for (int i = 0; i < workersLength; i++) {
            if (workers.get(i).getId().equals(ID)) {
                return (Photographer) workers.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean insertNews(WebNews wn) {
        return this.documents.add(wn);
    }
    
    @Override
    public boolean insertNews(PrintableNews pn) {
        return this.documents.add(pn);
    }

    @Override
    public boolean insertNews(Teletype tp) {
        return this.documents.add(tp);
    }

    @Override
    public boolean removeNews(WebNews wn) {
        return this.documents.remove(wn);
    }
    
    @Override
    public boolean removeNews(PrintableNews pn) {
        return this.documents.remove(pn);
    }

    @Override
    public boolean removeNews(Teletype tp) {
        return this.documents.remove(tp);
    }

    @Override
    public boolean addPrize(Document d, String prize) {
        return d.addPrize(prize);
    }

    @Override
    public boolean removePrize(Document d, String prize) {
        return d.removePrize(prize);
    }

    @Override
    public Journalist getAuthor(Document d) {
        return d.getAuthor();
    }

    @Override
    public Document[] getDocuments(Journalist j) {
        Document[] documentsOfAJournalist = null;
        int nextIndex = 0;
        
        for (int i = 0; i < this.documents.size(); i++){
            if (this.documents.get(i).getAuthor() == j){
                documentsOfAJournalist[nextIndex] = this.documents.get(i);
                nextIndex++;
            }
        }
        return documentsOfAJournalist;
    }

    @Override
    public boolean addReviewer(PrintableNews pn, Journalist rw) {
        return pn.addReviewer(rw);
    }

    @Override
    public boolean removeReviewer(PrintableNews pn, Journalist rw) {
        return pn.removeReviewer(rw);
    }

    @Override
    public Journalist[] listReviewers(PrintableNews pn) {
        return pn.getReviewers();
    }

    @Override
    public boolean addPicture(Picture p) {
        return this.pictures.add(p);
    }

    @Override
    public boolean removePicture(Picture p) {
        return this.pictures.remove(p);
    }

    @Override
    public Picture[] getPictures(Photographer p) {
        Picture[] picturesOfAPhotographer = null;
        int nextIndex = 0;
        
        for (int i = 0; i < this.pictures.size(); i++){
            if (this.pictures.get(i).getAutor() == p){
                picturesOfAPhotographer[nextIndex] = this.pictures.get(i);
                nextIndex++;
            }
        }
        return picturesOfAPhotographer;
    }

    @Override
    public WebResource getWebResource(String URL) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WebNews[] getIndexedNews(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createNewspaper(Date d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Newspaper getNewspaper(Date d) {
        return this.newspapers.get(d);
    }

    @Override
    public boolean deleteNewspaper(Date d) {
        return newspapers.remove(d) != null;
    }

    @Override
    public boolean addNewsToIssue(Newspaper np, PrintableNews pn) {
        return np.addNews(pn);
    }

    @Override
    public Journalist[] getJournalist(Newspaper np) {
        return np.getAuthors();
    }

    @Override
    public Photographer getAuthor(Picture p) {
        return p.getAutor();
    }
}