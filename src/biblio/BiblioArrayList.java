package biblio;

import java.util.ArrayList;

public class BiblioArrayList {
    
    /**
     * Devuelve verdadero si un número es capicúa y falso si no
     * @param Número Número entero que queremos saber si es capicúa
     * @return Boolean True si es capicúa y False si no
     */
    public static ArrayList<String> copiaArrayList(ArrayList<String> l) {
        
        ArrayList<String> a = new ArrayList<String>();
        
        for (String s : l) {
            
            a.add(s);
        }
        
        return a;
    }

}
