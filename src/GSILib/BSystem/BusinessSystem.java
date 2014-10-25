/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BSystem;

/* Aunque ya están en la interface, se introducen también aquí */
import GSILib.BModel.*;
import GSILib.BModel.workers.*;
import GSILib.BModel.documents.*;
import GSILib.BModel.documents.visualNews.*;
/* Estos, en cambio, son solo cosa de la implementación, que no debe conocerse */
import java.math.BigDecimal;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * This is the class BusinessSystem.
 * In this class we implement the system it self with help of all the classes of
 * the model.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class BusinessSystem implements EditorialOffice{
    
    // Colecciones de objetos propias del sistema.
    private HashMap<String, Worker> workers = new HashMap<>(); // Trabajadores (periodistas y fotografos)
    private List<Document> documents = new ArrayList<>(); // Documentos (todos los tipos de noticias)
    private HashMap<String, Picture> pictures = new HashMap<>(); // Imagenes
    private LinkedHashMap<Date, Newspaper> newspapers = new LinkedHashMap<>(); // Periodicos

    // Clase para dar el ID a las noticias (de cualquier tipo) de forma única.
    private AtomicInteger atomicInteger = new AtomicInteger();
    
    @Override
    public boolean addJournalist(Journalist jr){
        // Comprobamos que el periodista no se encuentra ya entre los trabajadores.
        if(! this.workers.containsKey(jr.getId())){
            // Añadimos el periodista si no está
            this.workers.put(jr.getId(), jr);
            // Devolvemos que todo ha ido bien.
            return true;
        }
        else{
            // Devolvemos que algo ha fallado.
            return false;
        }
    }

    @Override
    public boolean existJournalist(Journalist jr) {
        // Comprobamos que existe el periodista y lo devolvemos.
        return this.workers.containsValue(jr);
    }

    @Override
    public boolean removeJournalist(Journalist jr) {
        // Recogemos la ID del periodista.
        String id = jr.getId();
        // Lo borramos del sistema con la ID seleccionada.
        this.workers.remove(id);
        // Devolemos el resultado de la operación.
        return !this.workers.containsKey(id);
    }

    @Override
    public Journalist findJournalist(String ID) {
        // Comprobamos que el periodista existe.
        if(this.workers.containsKey(ID)){
            // Devolvemos el periodista.
            return (Journalist) this.workers.get(ID);
        }
        else{
            // Devolvemos null porque el periodista no existe.
            return null;
        }
    }

    @Override
    public boolean addPhotographer(Photographer pr) {
        // Comprobamos que el fotografo no se encuentra ya entre los trabajadores.
        if(! this.workers.containsKey(pr.getId())){
            // Añadimos el fotografo si no está
            this.workers.put(pr.getId(), pr);
            // Devolvemos que todo va bien.
            return true;
        }
        else{
            // Devolvemos que algo ha fallado.
            return false;
        }
    }

    @Override
    public boolean existPhotographer(Photographer pr) {
        // Comprobamos que el fotografo existe y lo devolvemos.
        return workers.containsKey(pr.getId());
    }

    @Override
    public boolean removePhotographer(Photographer pr) {
        // Recogemos la ID del periodista.
        String id = pr.getId();
        // Lo borramos del sistema con la ID seleccionada.
        this.workers.remove(id);
        // Devolemos el resultado de la operación.
        return !this.workers.containsKey(id);
    }

    @Override
    public Photographer findPhotographer(String ID) {
        // Comprobamos que el fotografo existe.
        if(this.workers.containsKey(ID)){
            // Devolvemos el fotografo.
            return (Photographer) this.workers.get(ID);
        }
        else{
            // Devolvemos null porque el fotografo no existe.
            return null;
        }
    }

    @Override
    public boolean insertNews(WebNews wn) {
        // Asignamos un ID de forma unívoca a la noticia.
        wn.setId(this.atomicInteger.getAndIncrement());
        // Introducimos la noticia en la colección de documentos.
        return this.documents.add(wn);
    }
    
    @Override
    public boolean insertNews(PrintableNews pn) {
        // Asignamos un ID de forma unívoca a la noticia.
        pn.setId(this.atomicInteger.getAndIncrement());
        // Introducimos la noticia en la colección de documentos.
        return this.documents.add(pn);
    }

    @Override
    public boolean insertNews(Teletype tp) {
        // Asignamos un ID de forma unívoca a la noticia.
        tp.setId(this.atomicInteger.getAndIncrement());
        // Introducimos la noticia en la colección de documentos.
        return this.documents.add(tp);
    }

    @Override
    public boolean removeNews(WebNews wn) {
        // Eliminamos la noticia de la colección de documentos.
        return this.documents.remove(wn);
    }
    
    @Override
    public boolean removeNews(PrintableNews pn) {
        // Eliminamos la noticia de la colección de documentos.
        return this.documents.remove(pn);
    }

    @Override
    public boolean removeNews(Teletype tp) {
        // Eliminamos la noticia de la colección de documentos.
        return this.documents.remove(tp);
    }

    @Override
    public boolean addPrize(Document d, String prize) {
        // Añadimos el premio a la colección de premios.
        return d.addPrize(prize);
    }

    @Override
    public boolean removePrize(Document d, String prize) {
        // Eliminamos el premio de la colección de premios.
        return d.removePrize(prize);
    }
    
    @Override
    public String[] listPrizes(Document d){
        // Nota. Para que luego no nos diga que somos cutres hazlo con un iterator
        // tienes ejemplos en listDocuments y listReviewers.
        return d.getPrizes();
    }

    @Override
    public Journalist getAuthor(Document d) {
        // Devolvemos el autor de un documento.
        return d.getAuthor();
    }

    @Override
    public Document[] getDocuments(Journalist j) {
        // Declaramos una lista de documentos que inicializamos.
        ArrayList<Document> documentsOfAJournalist = new ArrayList<>();
        // Recorremos todos los documentos
        for (Document d : this.documents) {
            // Comprobamos cuales coinciden con el autor.
            if (d.getAuthor().equals(j)) {
                // Guardamos los que coinciden en la lista.
                documentsOfAJournalist.add(d);
            }
        }
        // Convertimos la lista a tabla y la devolvemos.
        Document[] docs = documentsOfAJournalist.toArray(new Document[documentsOfAJournalist.size()]);
        return docs;
    }

    @Override
    public boolean addReviewer(PrintableNews pn, Journalist rw) {
        // Añadimos un revisor a la lista de revisores.
        return pn.addReviewer(rw);
    }

    @Override
    public boolean removeReviewer(PrintableNews pn, Journalist rw) {
        // Eliminamos un revisor de la lista de revisores.
        return pn.removeReviewer(rw);
    }

    @Override
    public Journalist[] listReviewers(PrintableNews pn) {
        // Extraemos los revisores de una noticia y los devolvemos.
        return pn.getReviewers();
    }

    @Override
    public boolean addPicture(Picture p) {
        // Comprobamos que la foto no exista ya.
        if(! this.pictures.containsKey(p.getUrl())){
            // Si no existe, la añadimos.
            this.pictures.put(p.getUrl(), p);
            // Devolvemos que todo ha ido bien.
            return true;
        }
        else{
            // Devolvemo que algo ha ido mal.
            return false;
        }
    }

    @Override
    public boolean removePicture(Picture p) {
        // Rescatamos el identificador (URL) de la imagen.
        String url = p.getUrl();
        // Eliminamo la imagen con esa URL.
        this.pictures.remove(url);
        // Devolvemos la comprobación de que se ha eliminado.
        return !this.pictures.containsKey(url);
    }

    @Override
    public Picture[] getPictures(Photographer p) {
        // Declaramos una lista de imagenes vacia.
        ArrayList<Picture> picsOfAPhotographer = new ArrayList<>();
        // Recorremos todas las imagenes en busca de aquellas que haya hecho el fotografo.
        for (Map.Entry e : pictures.entrySet()) {
            picsOfAPhotographer.add((Picture) e.getValue());
        }
        // Convertimos la lista a tabla y la devolvemos.
        Picture[] pics = picsOfAPhotographer.toArray(new Picture[picsOfAPhotographer.size()]);
        return pics;
    }

    @Override
    public WebResource getWebResource(String URL) {
        // Creamos una instancia de webnews.
        WebNews wn = new WebNews("void","void",new Journalist("void", "void", "void", null),"void");
        // Comprobamos que estamos hablando de la foto.
        if(this.pictures.containsKey(URL))
            // Devolvemos la foto solicitada.
            return (WebResource) pictures.get(URL);
        else 
            // Recorremos los documentos buscando las webnews.
            for(Document d: documents)
                // Buscamos solo los documentos de tipo webnews.
                if (d.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                    wn = (WebNews) d;
                    // Si es la webnews que buscamos, la devolvemos.
                    if(wn.getUrl().equals(URL))
                        return (WebResource) wn;
                }        
        // Si no encontramos nada, devolvemos null.
        return null;
    }

    @Override
    public WebNews[] getIndexedNews(String keyword) {
        // Creamos una lista de webnews.
        ArrayList<WebNews> list = new ArrayList<>();
        // Creamos una instancia de webnews.
        WebNews wn = new WebNews("void","void",new Journalist("void", "void", "void", null),"void");
        
        // Recorremos todos los documentos buscando las webnews.
        for(Document d: documents)
            // Buscamos solo los documentos de tipo webnews.
            if (d.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                    wn = (WebNews) d;
                    // Si es la webnews que buscamos, la devolvemos.
                    if(wn.getKeyWords().contains(keyword))
                        list.add(wn);
                }
        
        // Devolvemos la tabla de webnews
        WebNews[] arrayWebNews = list.toArray(new WebNews[list.size()]);
        return arrayWebNews;
    }

    @Override
    public boolean createNewspaper(Date d) {
        // Creamos un periodico vacío.
        Newspaper n = new Newspaper();
        // Creamos un periodico con fecha d.
        n = this.newspapers.put(d, n);
        // Retornamos el resultado de la creación.
        return !this.newspapers.containsKey(d);
    }

    @Override
    public Newspaper getNewspaper(Date d) {
        // Recogemos un periodico en función a su fecha.
        return this.newspapers.get(d);
    }

    @Override
    public boolean deleteNewspaper(Date d) {
        // Eliminamos el periodico de una fecha.
        return newspapers.remove(d) != null;
    }

    @Override
    public boolean addNewsToIssue(Newspaper np, PrintableNews pn) {
        // Añadimos una noticia a un periodico.
        return np.addNews(pn);
    }

    @Override
    public Journalist[] getJournalist(Newspaper np) {
        // Retornamos el nombre de los periodistas que han trabajado en un
        // periodico concreto.
        return np.getAuthors();
    }

    @Override
    public Photographer getAuthor(Picture p) {
        // Retornamos el autor de una imagen.
        return p.getAutor();
    }
    
    // TODO: javadoc.
    public int importTeletypes(File f){
        // Leemos y almacenamos los datos que hay en la hoja.
        Sheet sheet = null;
        try {
            sheet = SpreadSheet.createFromFile(f).getSheet(0);
        } 
        catch (IOException ex) {
            // TODO: Revisar.
            Logger.getLogger(BusinessSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int i = 0;
        // Iteramos sobre todos los elementos de la hoja de cálculo.
        while(! sheet.getCellAt(i,0).isEmpty()){
            // Importamos el ID del periodista del teletipo.
            BigDecimal authorIDnum = (BigDecimal) sheet.getCellAt(0,i).getValue();
            String authorID = authorIDnum.toString();
            // Importamos el titular del teletipo.
            String headline = (String) sheet.getCellAt(1,i).getValue();
            // Importamos el cuerpo del teletipo.
            String body = (String) sheet.getCellAt(2,i).getValue();
            Teletype tt = new Teletype(headline, body, this.findJournalist(authorID));
            // Para importar los premios hacemos otro bucle.
            int j = 3;
            while(! sheet.getCellAt(j,i).isEmpty()){
                // Importamos los premios conforme los leemos.
                tt.addPrize((String) sheet.getCellAt(j,i).getValue());
                j++;
            }
            // Guardamos el teletipo en el sistema.
            this.insertNews(tt);
            // Avanzamos a la siguiente fila.
            i++;
        }
        
        return 0;
    }
}