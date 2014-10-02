/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSI.Misc;

import java.io.File;

/**
 *
 * @author Alvaro
 */

public class SSTest01 {
    private int[][] subTabla;
    
    public void main(String[] args){
        subTabla = new int[4][6];

        TableModel model = new DefaultTableModel(subTabla);  

        // Save the data to an ODS file and open it.
        
        final File file = new File("P2E2.ods");
        SpreadSheet.createEmpty(model).saveAs(file);

        OOUtils.open(file);
    }
}
