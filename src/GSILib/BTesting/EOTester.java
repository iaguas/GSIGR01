/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILib.BTesting;

import GSILib.BSystem.*;
import GSILib.BModel.*;
import GSILib.BModel.workers.*;
import GSILib.BModel.documents.*;
import GSILib.BModel.documents.visualNews.*;
import java.util.ArrayList;
/**
 *
 * @author inigo.aguas
 */
public class EOTester {
    public void main(String[] args){
        BusinessSystem bsystem = new BusinessSystem();
         
        /* S1: Si introduce a un trabajador, este puede ser localizado luego a
               partir de su ID.
        */
        // Completamos primero la lista de intereses
        ArrayList<String> interest = new ArrayList<>();
        interest.add("lectura");
        interest.add("natacion");
        interest.add("belenismo");
        // Añadimos al nuevo periodísta. Se podría hacer de forma análoga para
        // para los fotógrafos.
        Journalist jr = new Journalist("Jonh Smith", "09/02/1993", interest);
        bsystem.addJournalist(jr);
        // Buscamos el empleado por el ID.
        // TODO: ¿Y de donde narices sacamos el ID?, Habrá que hacer un nuevo método...
         
         
        /* S2: Si busca a un periodista que no existe con findJournalist, el 
               resultado es null.
        */
        jr = bsystem.findJournalist("999999");
        System.out.println("El resultado es " + jr);
         
        /* S3: Si se busca a un periodista con el ID de un fotográfo, el
               resultado es null.
        */
        // Mismo problema, de donde obtenemos el ID?
        jr = bsystem.findJournalist("999999");
        System.out.println("El resultado es " + jr);
         
        /* S4: Si intenta introducir a un peridista con el ID de un fotógrafo, 
               anteriormente introducido, el método addJournalist devuelve un 
               resultado falso.
        */
        // Mismo problema, de donde obtenemos el ID?
         
        /* S5: Si busca un periodista al que ha eliminado (con removeJournalist)
               el resultado de findJournalist es null.
        */
        jr = bsystem.findJournalist("99999");
        bsystem.removeJournalist(jr);
        jr = bsystem.findJournalist("999999");
        System.out.println("El resultado es " + jr);
        
        /* S6: Introduzca varias noticias (de varias clases intanciables) y
               asígnelas a un periodista presente en el sistema. Trate de
               recuperarlas usando getDocuments.
        */
        // Creamos las nuevas noticias.
        PrintableNews pn = new PrintableNews("title1","body1",jr);
        Teletype tt = new Teletype("title2","body2",jr);
        WebNews wn = new WebNews("title3","body3",jr,"http://midominio.com/noticias/id");
        
        // Insertamos las noticias.
        bsystem.insertNews(pn);
        bsystem.insertNews(tt);
        bsystem.insertNews(wn);
                
        // Recuperamos los documentos con el método propuesto.
        Document[] documentList = bsystem.getDocuments(bsystem.findJournalist("99999"));
        
        /* S7: Trate de recuperar las noticas escritas por un periodista que no
               tenga noticias asociadas.
        */
        // Recuperar un periodista por su ID.
        jr = bsystem.findJournalist("99999");
        
        // Recuperamos las noticias del periodista anterior.
        documentList = bsystem.getDocuments(jr);
        
        /* S8: Introduzca varias fotos en el sistema. Más adelante, asocie varias
               fotos a una de las noticias introducidas anteriormente
               (evidenetemente, no puede ser un Teleprinter). Finalmente,
               recupere la noticia usando getDocuments y compruebe que la
               asociación ha sido adecuada.
        */
        // Creamos varias fotos asignadas a un único fotógrafo.
        Photographer pg = new Photograper("Jonh Smith", "30/04/1953","C/ Falsa 123", "C/ Verdadera 567");
        Picture pic1 = new Picture("http://midominio.com/fotos/pic1", pg);
        Picture pic2 = new Picture("http://midominio.com/fotos/pic2", pg);
        Picture pic3 = new Picture("http://midominio.com/fotos/pic3", pg);
        
        // Asociamos las fotos a la noticia.
        bsystem.addPicture(pic1);
        bsystem.addPicture(pic2);
        bsystem.addPicture(pic3);
        
        // Recuperamos la noticia
        Document[] docs = bsystem.getDocuments(jr);
        
        // Buscamos la noticia que nos interesa
        
        // Mostramos que tiene todas las fotos.
        
    }
}
