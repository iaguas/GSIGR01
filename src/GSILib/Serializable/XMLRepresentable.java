/* 
 * Práctica 03 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.Serializable;

import java.io.File;

/**
 * This interface embodies the ability of a a class of objects to represent itself
 * 	using XML. 
 * Note that it refers, in no manner, to its ability to create instances from such a representation.
 *
 * @author carlos.lopez
 */
public interface XMLRepresentable {
    
    public String toXML();
    
    public boolean saveToXML(File f);
    
    public boolean saveToXML(String filePath);
    
}
