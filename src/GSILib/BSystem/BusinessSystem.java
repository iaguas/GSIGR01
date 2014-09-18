/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BSystem;

import GSILib.BModel.Document;
import GSILib.BModel.Picture;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BModel.Worker;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class BusinessSystem implements EditorialOffice{
    
    private List<Worker> workers = new ArrayList<Worker>();
    private List<Document> documents = new ArrayList<Document>();
    private List<Picture> pictures = new ArrayList<Picture>();
    
    @Override
    public boolean addJournalist(Journalist jr){
        return workers.add(jr);
    }

    @Override
    public boolean existJournalist(Journalist jr) {
        return workers.contains(jr);
    }

    @Override
    public boolean removeJournalist(Journalist jr) {
        return workers.remove(jr);
    }

    @Override
    public Journalist findJournalist(String ID) {
        int workersLength = workers.size();
        for (int i = 0; i < workersLength; i++) {
            if (workers.get(i).getId().equals(ID)) {
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
    public void insertNews(WebNews wn) {
        this.documents.add(wn);
    }

    @Override
    public boolean removeNews(WebNews wn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPrize(Document d, String prize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removePrize(Document d, String prize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Journalist getAuthor(Document d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return pn.getReviwers();
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
    public JournalIssue getNewspaper(Date d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteNewspaper(Date d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addNewsToIssue(JournalIssue ji, PrintableNews pn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Journalist getJournalist(JournalIssue ji) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Photographer getAuthor(Picture p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insertNews(WebNews wn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeNews(WebNews wn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPrize(Document d, String prize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removePrize(Document d, String prize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Journalist getAuthor(Document d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addNewsToIssue(JournalIssue ji, PrintableNews pn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
