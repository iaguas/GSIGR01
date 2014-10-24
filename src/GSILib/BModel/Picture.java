/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel;

import GSILib.BModel.workers.Photographer;

/**
 * This is the class Picture.
 * It respresent a picture in the system throw it URL.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Picture {
    
    // Atributos de la clase
    private String url; // Identificador único, la URL.
    private Photographer author; // Fotografo autor de la foto.
    
    /**
     * Class constructor in which you have to put the URL and the photographer.
     * @param url An unique identifier of a picture.
     * @param photographer Author of the picture.
     */
    public Picture(String url, Photographer photographer){
        // Introducimos los datos suministrados por el constructor.
        this.url = url;
        this.author = photographer;
    }
    
    /**
     * Retrieves the information on the URL of a Picture. 
     * If the Picture is not stored in the system, the result is null.
     * @return The URL associated with the picture
     */
    public String getUrl(){
        // Devolvemos la URL
        return this.url;
    }
    
    /**
     * Retrieves the information on the author of a Picture. 
     * If the Picture is not stored in the system, the result is null.
     * @return The photographer associated with the picture
     */    
    public Photographer getAutor(){
        // Devolvemos el autor.
        return this.author;
    }
        
    @Override
    public String toString(){
        // Devolvemos un string con los datos de la imagen.
        return "Picture URL: " + this.getUrl();
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param p a picture
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Picture p){
        // Comparamos y devolvemos si son iguales o no.
        return this.getUrl().equals(p.getUrl());
    }
}
