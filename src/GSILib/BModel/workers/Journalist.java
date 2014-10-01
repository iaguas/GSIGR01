/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.BModel.workers;

import GSILib.BModel.Worker;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class Journalist.
 * He represent a worker who writes Document into de system. He has ID, name,
 * birthDate and, especialy, interest.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Journalist extends Worker {
    
    // Atributo de la clase
    private List<String> interests = new ArrayList<>(); // Lista de intereses.
    
    /**
     * Class constructor
     * @param id This is an unique ID of the worker.
     * @param name The name of the worker.
     * @param birthDate The birth dathe of the worker.
     * @param interests Is a list of a keyword of interest of a Journalist.
     */
    public Journalist(String id, String name, String birthDate, ArrayList interests){
        // Utilizamos el constructor de la superclase
        super(id, name, birthDate);
        // Introducimos los intereses en esta clase, que es quien los contiene.
        this.interests = interests;
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del periodista.
        return "Journalist ID: " + this.getId() + "\n  Name: " + this.getName()
                + "\n  BirthDate: " + this.getBirthDate() + "\n  Interests: " +
                this.interests;
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param jr a journalist
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Journalist jr){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(jr.getId());
    }
}
