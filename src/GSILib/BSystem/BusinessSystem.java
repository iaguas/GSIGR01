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
import GSILib.persistence.*;
/* Estos, en cambio, son solo cosa de la implementación, que no debe conocerse */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * This is the class BusinessSystem.
 * In this class we implement the system it self with help of all the classes of
 * the model.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class BusinessSystem implements EditorialOffice, ODSPersistent{
    
    // Colecciones de objetos propias del sistema.
    private final HashMap<String, Worker> workers = new HashMap<>(); // Trabajadores (periodistas y fotografos)
    private final List<Document> documents = new ArrayList<>(); // Documentos (todos los tipos de noticias)
    private final HashMap<String, Picture> pictures = new HashMap<>(); // Imagenes
    private final LinkedHashMap<Date, Newspaper> newspapers = new LinkedHashMap<>(); // Periodicos

    // Clase para dar el ID a las noticias (de cualquier tipo) de forma única.
    private final AtomicInteger atomicInteger = new AtomicInteger();
    
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
 
    /**
     * Reads the Teletypes from an .ods file and imports them into the system
     * @param f The input file (.ods, Open Office spreadsheet) to read from
     * @return the number of Teletypes correctly added to the system
     */
    public int importTeletypes(File f){
        // Leemos y almacenamos los datos que hay en la hoja correspondiente.
        // Teletypes (hoja 0)
        Sheet sheet = null;
        try {
            sheet = SpreadSheet.createFromFile(f).getSheet(0);
        } 
        catch (IOException ex) {
            System.err.printf("No se encontró el archivo.\n");
        }
        
        int i = 0;
        // Iteramos sobre todos los elementos de la hoja de cálculo.
        while(sheet.getRowCount()>i && (! sheet.getCellAt(i,0).isEmpty())){
            // Importamos el ID del periodista del teletipo.
            String authorID = sheet.getCellAt(0,i).getTextValue();
            // Importamos el titular del teletipo.
            String headline = sheet.getCellAt(1,i).getTextValue();
            // Importamos el cuerpo del teletipo.
            String body = sheet.getCellAt(2,i).getTextValue();
            // Buscamos al periodista en el sistema.
            Teletype tt = new Teletype(headline, body, this.findJournalist(authorID));
            // Para importar los premios hacemos otro bucle.
            int j = 3;
            String str = sheet.getCellAt(j,i).getTextValue();
            while(sheet.getColumnCount()>j && (! sheet.getCellAt(j,i).getTextValue().equals("")) ){
                // Importamos los premios conforme los leemos.
                tt.addPrize((String) sheet.getCellAt(j,i).getValue());
                j++;
                //Cell c =sheet.getCellAt(j,i);
                //str = c.getTextValue();
            }
            // Guardamos el teletipo en el sistema.
            this.insertNews(tt);
            // Avanzamos a la siguiente fila.
            i++;
        }
        return i;
    }
    
    /**
     * Reads the PrintableNews from an .ods file and imports them into the system
     * @param f The input file (.ods, Open Office spreadsheet) to read from
     * @return the number of PrintableNews correctly added to the system
     */
    public int importPrintableNews(File f){
        // Leemos y almacenamos los datos que hay en la hoja correspondiente.
        // PrintableNews (hoja 1)
        Sheet sheet = null;
        try {
            sheet = SpreadSheet.createFromFile(f).getSheet(0);
        } 
        catch (IOException ex) {
            System.err.printf("No se encontró el archivo.\n");
        }
        
        int i = 0;
        // Iteramos sobre todos los elementos de la hoja de cálculo.
        while(sheet.getRowCount()>i && (! sheet.getCellAt(i,0).isEmpty())){
            // Importamos el ID del periodista revisor de la noticia imprimible.
            String authorID = sheet.getCellAt(0,i).getTextValue();
            // Importamos el titular de la noticia imprimible.
            String headline = sheet.getCellAt(1,i).getTextValue();
            // Importamos el cuerpo de la noticia imprimible.
            String body = sheet.getCellAt(2,i).getTextValue();
            // Buscamos al periodista en el sistema.
            PrintableNews pn = new PrintableNews(headline, body, this.findJournalist(authorID));
            
            // Para importar los premios hacemos otro bucle.
            int j = 3;
            while(sheet.getColumnCount()>j && !(sheet.getCellAt(j,i).getTextValue()).equals("*") && (! sheet.getCellAt(j,i).isEmpty())){
                // Importamos los premios conforme los leemos.
                pn.addPrize(sheet.getCellAt(j,i).getTextValue());
                j++;
            }
            // Avanzamos las columnas para coger el siguiente valor, los revisores.
            j++; 
            // Para importar los revisores, hacemos otro bucle más
            while(sheet.getColumnCount()>j && (! sheet.getCellAt(j,i).isEmpty())){
                // Importamos los premios conforme los leemos.
                addReviewer(pn, findJournalist(sheet.getCellAt(j,i).getTextValue()));
                j++;
            }
            // Guardamos el la noticia imprimible en el sistema.
            this.insertNews(pn);
            // Avanzamos a la siguiente fila.
            i++;
        }
        return i;
    }

    @Override
    public boolean loadFromFile(File f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveToFile(File file) {
        // Creamos una nueva hoja de cálculo.
        SpreadSheet mySpreadSheet = SpreadSheet.create(7,1000,1000);
        // Rescatamos la hoja dentro de la hoja de cálculo
        final Sheet sheetJournalists = mySpreadSheet.getSheet(0);
        sheetJournalists.setName("Journalists");
        final Sheet sheetPhotographers = mySpreadSheet.getSheet(1);
        sheetPhotographers.setName("Photographers");
        
        final Sheet sheetTeletypes = mySpreadSheet.getSheet(2);
        sheetTeletypes.setName("Teletypes");
        final Sheet sheetPrintableNews = mySpreadSheet.getSheet(3);
        sheetPrintableNews.setName("PrintableNews");
        final Sheet sheetWebNews = mySpreadSheet.getSheet(4);
        sheetWebNews.setName("WebNews");
        
        final Sheet sheetPictures = mySpreadSheet.getSheet(5);
        sheetPictures.setName("Pictures");
        final Sheet sheetNewspapers = mySpreadSheet.getSheet(6);
        sheetNewspapers.setName("Newspapers");
        
        // Recorremos la lista de Workers
        
        int numJournalist = 0, numPhotographer = 0;
        Iterator iteratorWorkers = this.workers.entrySet().iterator();
        while (iteratorWorkers.hasNext()) {
            // Cargamos ese par
            Map.Entry pair = (Map.Entry)iteratorWorkers.next();
            // Guardo Journalists
            if (pair.getValue().getClass().getName().equals("GSILib.BModel.workers.Journalist")){
                // Especificamos que es un Journalist con un casting
                Journalist journalist = (Journalist) pair.getValue();
                // Guardamos los datos del Journalist
                sheetJournalists.setValueAt(pair.getKey(), 0, numJournalist);
                sheetJournalists.setValueAt(journalist.getName(), 1, numJournalist);
                sheetJournalists.setValueAt(journalist.getBirthDate(), 2, numJournalist);
                String[] interests = journalist.getInterests();
                if (interests != null){
                    for (int i=0; i<interests.length; i++){
                        sheetJournalists.setValueAt(interests[i], i+3, numJournalist);
                    }
                }
                
                numJournalist++;
            }
            // Guardo Photographers
            else if (pair.getValue().getClass().getName().equals("GSILib.BModel.workers.Photographer")){
                // Especificamos que es un Photographer con un casting
                Photographer photographer = (Photographer) pair.getValue();
                // Guardamos los datos del Photographer
                sheetPhotographers.setValueAt(pair.getKey(), 0, numPhotographer);
                sheetPhotographers.setValueAt(photographer.getName(), 1, numPhotographer);
                sheetPhotographers.setValueAt(photographer.getBirthDate(), 2, numPhotographer);
                sheetPhotographers.setValueAt(photographer.getRegularResidence(), 3, numPhotographer);
                sheetPhotographers.setValueAt(photographer.getHolidayResidence(), 4, numPhotographer);

                numPhotographer++;
            }
            // Error
            else{
                System.err.printf("Worker no pertenece a subclase.\n");
            }  
        }
        
        // Recorremos la lista de Documents
        
        int numTeletype = 0, numPrintableNews = 0, numWebNews = 0;
        int colsTeletype, colsPrintableNews, colsWebNews; 
        for(Document document: this.documents){
            // Guardo los datos de los Teletypes.
            if (document.getClass().getName().equals("GSILib.BModel.documents.Teletype")){
                colsTeletype = 3;
                // Especificamos que es un Teletype con un casting
                Teletype teletype = (Teletype) document;
                // Guardamos los datos del Journalist
                sheetTeletypes.setValueAt(document.getAuthor().getId(), 0, numTeletype);
                sheetTeletypes.setValueAt(document.getHeadline(), 1, numTeletype);
                sheetTeletypes.setValueAt(document.getBody(), 2, numTeletype);
                // Campos multivaluados
                String[] teletypePrizes = this.listPrizes(document);
                if (teletypePrizes != null){
                    for (int i=0; i<teletypePrizes.length; i++){
                        sheetTeletypes.setValueAt(teletypePrizes[i], i + colsTeletype, numTeletype);
                    }
                }
                
                numTeletype++;
            }
            
            // Guardo los datos de las PrintableNews.
            if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                colsPrintableNews = 3;
                // Especificamos que es un PrintableNew con un casting
                PrintableNews printableNews = (PrintableNews) document;
                // Guardamos los datos del PrintableNew
                sheetPrintableNews.setValueAt(document.getId(), 0, numPrintableNews);
                sheetPrintableNews.setValueAt(document.getHeadline(), 1, numPrintableNews);
                sheetPrintableNews.setValueAt(document.getBody(), 2, numPrintableNews);

                if(! printableNews.getPictures().isEmpty()){
                    sheetWebNews.setValueAt(printableNews.getPictures().get(0), colsPrintableNews, numPrintableNews);
                    colsPrintableNews++;
                }
                if(printableNews.getReviewers() != null){
                    sheetWebNews.setValueAt(printableNews.getReviewers()[0].getId(), colsPrintableNews, numPrintableNews);
                    colsPrintableNews++;
                }
                String[] printableNewsPrizes = this.listPrizes(document);
                if (printableNewsPrizes != null){
                    for (int i=0; i<printableNewsPrizes.length; i++){
                        sheetPrintableNews.setValueAt(printableNewsPrizes[i], colsPrintableNews, numPrintableNews);
                        colsPrintableNews++;
                    }
                }
                numPrintableNews++;
            }
            
            // Guardo los datos de las WebNews.
            if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                colsWebNews = 4;
                // Especificamos que es un WebNews con un casting
                WebNews webNews = (WebNews) document;
                // Guardamos los datos del WebNews
                sheetWebNews.setValueAt(document.getAuthor().getId(), 0, numWebNews);
                sheetWebNews.setValueAt(document.getHeadline(), 1, numWebNews);
                sheetWebNews.setValueAt(document.getBody(), 2, numWebNews);
                sheetWebNews.setValueAt(webNews.getUrl(), 3, numWebNews);
                if(! webNews.getPictures().isEmpty()){
                    sheetWebNews.setValueAt(webNews.getPictures().get(0), colsWebNews, numWebNews);
                    colsWebNews++;
                }
                if(! webNews.getKeyWords().isEmpty()){
                    sheetWebNews.setValueAt(webNews.getKeyWords().get(0), colsWebNews, numWebNews);
                    colsWebNews++;
                }
                String[] webNewsPrizes = this.listPrizes(document);
                if (webNewsPrizes != null){
                    for (int i=0; i<webNewsPrizes.length; i++){
                        sheetWebNews.setValueAt(webNewsPrizes[i], colsWebNews, numWebNews);
                        colsWebNews++;
                    }
                }
                
                numWebNews++;
            }
        }
        
        // Recorremos la lista de Pictures
        
        int numPicture = 0;
        Iterator iteratorPictures = this.pictures.entrySet().iterator();
        while (iteratorPictures.hasNext()) {
            // Cargamos ese par
            Map.Entry pair = (Map.Entry)iteratorPictures.next();
            // Cargamos el valor de ese par como Picture
            Picture picture = (Picture) pair.getValue();
            // Rellenar la tabla
            sheetPictures.setValueAt(pair.getKey(), 0, numPicture);
            sheetPictures.setValueAt(picture.getAutor(), 1, numPicture);
            sheetPictures.setValueAt(picture.getUrl(), 2, numPicture);
            
            numPicture++;  
        }
        
        // Recorremos la lista de Newspapers
        
        int numNewspaper = 0;
        Iterator iteratorNewspapers = this.newspapers.entrySet().iterator();
        while (iteratorNewspapers.hasNext()) {
            // Cargamos ese par
            Map.Entry pair = (Map.Entry)iteratorNewspapers.next();
            // Cargamos el valor de ese par como Newspaper
            Newspaper newspaper = (Newspaper) pair.getValue();
            // Rellenar la tabla
            sheetNewspapers.setValueAt(pair.getKey(), 0, numNewspaper);
            // Rellenar la tabla - Campos multivaluados
            PrintableNews[] printableNews = newspaper.getPrintableNews();
            if (printableNews != null){
                for (int i=0; i<printableNews.length; i++){
                    sheetNewspapers.setValueAt(printableNews[i], i+1, numNewspaper);
                }
            }
            
            numNewspaper++;  
        }
        
        // Guardamos la hoja de cálculo con todos los datos.
        try {
            OOUtils.open(mySpreadSheet.saveAs(file));
        } 
        catch (IOException ex) {
            System.err.printf("No se pudo guardar el archivo.\n");
        }
        return true;
    }   
}
