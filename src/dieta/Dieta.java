package dieta;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import biblio.BiblioArrayList;
import biblio.BiblioMatematicas;

public class Dieta {

    private String idReceta = "";
    private String nombreReceta = "";
    private String idHora = "";
    private String dia = "";

    public Dieta(int idHora, int dia) {

        this.idHora = String.valueOf(idHora);
        this.dia = devuelveDia(dia);
    }
    
    public Dieta(String idReceta, String receta) {
        
        this.idReceta = idReceta;
        this.nombreReceta = receta;
    }
    
    // DEVUELVE LA DIETA DEL USUARIO EN UNA TABLA (ARRAY BIDIMENSIONAL)
    public static Dieta[][] generaTablaUsuarioDieta(String idUsuario) throws ClassNotFoundException, SQLException {
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta",
                "admin", "nimda12_34$");
        Statement s = conexion.createStatement();
        Statement s2 = conexion.createStatement();
        
        ResultSet listadoDieta = s.executeQuery("SELECT IdReceta, IdHora, Dia FROM dieta WHERE IdUsuario = "
                + idUsuario);
        
        // Creamos array bidimensional para almacenar las recetas y crear así la tabla
        Dieta[][] tabla = new Dieta[4][7];
        
        while (listadoDieta.next()) { // Recorremos la dieta del usuario
            
            ResultSet nombreReceta = s2.executeQuery("SELECT nombre FROM receta WHERE Id = " +
                    listadoDieta.getString("IdReceta"));
            String receta = "";
            
            while (nombreReceta.next()) { // Obtenemos el nombre de la receta
                
                receta = nombreReceta.getString("nombre");
            }
            
            if (listadoDieta.getString("IdHora").equals("1")) { // Añadimos los datos a la tabla en su respectivo orden
                
                /*
                 Usamos devuelveNumDia(String) ya que el número devuelto coincide con la posición
                 en la que debe de ir la receta
                */
                tabla[0][devuelveNumDia(listadoDieta.getString("Dia"))] = new Dieta(listadoDieta.getString("IdReceta"), receta);
                
            } else if (listadoDieta.getString("IdHora").equals("2")) {
                
                tabla[1][devuelveNumDia(listadoDieta.getString("Dia"))] = new Dieta(listadoDieta.getString("IdReceta"), receta);
                
            } else if (listadoDieta.getString("IdHora").equals("3")) {
                
                tabla[2][devuelveNumDia(listadoDieta.getString("Dia"))] = new Dieta(listadoDieta.getString("IdReceta"), receta);
                
            } else if (listadoDieta.getString("IdHora").equals("4")) {
                
                tabla[3][devuelveNumDia(listadoDieta.getString("Dia"))] = new Dieta(listadoDieta.getString("IdReceta"), receta);
            }
        }
        
        conexion.close();
        
        return tabla;
    }
    
    // DEVUELVE EL NÚMERO DEL DÍA DADO SU NOMBRE
    private static int devuelveNumDia(String dia) {
        
        switch (dia) {
        
            case "Lunes":
                return 0;
            case "Martes":
                return 1;        
            case "Miércoles":
                return 2;
            case "Jueves":
                return 3;        
            case "Viernes":
                return 4;
            case "Sábado":
                return 5;
            default:
                return 6;
        }
    }
    
    // DEVUELVE EL DÍA DADO SU NÚMERO DE LA SEMANA (DEL 0 AL 6)
    private static String devuelveDia(int dia) {
        
        switch (dia) {
        
            case 0:
                return "Lunes";
            case 1:
                return "Martes";        
            case 2:
                return "Miércoles";
            case 3:
                return "Jueves";        
            case 4:
                return "Viernes";
            case 5:
                return "Sábado";
            default:
                return "Domingo";
        }
    }
    
    // ACTUALIZA LA DIETA DEL USUARIO
    public static void actualizaDietaUsuario(String idUsuario) throws ClassNotFoundException, SQLException {
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta","admin", "nimda12_34$");
        Statement s = conexion.createStatement();
        
        eliminaDietaUsuario(idUsuario, s);
        
        ResultSet listaIdRecetas = s.executeQuery("SELECT Id FROM receta");
        ArrayList<String> idRecetas = new ArrayList<String>();
    
        while (listaIdRecetas.next()) { // Volcamos todas las recetas disponibles en la lista
            
            idRecetas.add(listaIdRecetas.getString("Id"));
        }
        
        ArrayList<Dieta> dietaUsuario = Dieta.generaDietaUsuario(idRecetas); // Generamos la dieta del usuario
        String dietaUsuarioInsert = "INSERT INTO dieta (IdUsuario, IdReceta, IdHora, Dia) VALUES ";
        
        for (int i = 0; i < 28; i++) { // Creamos la variable para insertar los datos en la BBDD
            
            if (i == 0) {
                
                dietaUsuarioInsert += "('" + idUsuario + "', '" + dietaUsuario.get(i).getIdReceta() + "', '"
                    + dietaUsuario.get(i).getIdHora() + "', '" + dietaUsuario.get(i).getDia() + "')";
                
            } else {
                
                dietaUsuarioInsert += ", ('" + idUsuario + "', '" + dietaUsuario.get(i).getIdReceta() + "', '"
                        + dietaUsuario.get(i).getIdHora() + "', '" + dietaUsuario.get(i).getDia() + "')";
            }
        }
        
        s.execute(dietaUsuarioInsert); // Realizamos la inserción
        
        // Proceso de obtención de la nueva fecha en la que expira la dieta
        Calendar cal = Calendar.getInstance();
        
        // Obtenemos la fecha de hoy
        int anyo = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        cal.clear();
        
        // Actualizamos el calendario con los datos de arriba
        cal.set(Calendar.YEAR, anyo);
        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.DATE, dia);
        
        // Le añadimos 7 días a la fecha ya que la dieta se renueva cada semana
        cal.add(Calendar.DATE, 7);
        
        Date fechaSql = new Date(cal.getTimeInMillis()); // Formateamos la fecha para que se pueda almacenar en sql
        
        String updateFechaExpiraDieta = "UPDATE usuario SET ExpiraDieta = '" + fechaSql + "' WHERE Id = " + idUsuario;
        s.execute(updateFechaExpiraDieta); // Actualizamos la fecha
    }    
    
    // ELIMINA LA DIETA DEL USUARIO INDICADO
    public static void eliminaDietaUsuario(String idUsuario, Statement s) throws SQLException {
        
        String eliminarDietaUsuario = "DELETE FROM dieta WHERE IdUsuario = " + idUsuario;
        s.execute(eliminarDietaUsuario);
    }
    
    // GENERA LA DIETA DEL USUARIO
    public static ArrayList<Dieta> generaDietaUsuario(ArrayList<String> idRecetas)
            throws ClassNotFoundException, SQLException {
        
        Dieta[][] dietas = new Dieta[4][7];
        ArrayList<Dieta> listaDietas = new ArrayList<Dieta>();
        
        for (int hora = 0; hora < 4; hora++) {
            
            for (int dia = 0; dia < 7; dia++) {
                
                dietas[hora][dia] = new Dieta((hora + 1), dia);
            }
        }
        
        seleccionaReceta(dietas);
        
        for (int fila = 0; fila < 4; fila++) {
            
            for (int col = 0; col < 7; col++) {
                
                listaDietas.add(dietas[fila][col]);
            }
        }
        
        return listaDietas;
    }
    
    // SELECCIONA UNA RECETA PARA LAS CELDAS CUMPLIENDO LAS CONDICIONES 
    private static void seleccionaReceta(Dieta[][] dietas)
            throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta",
                "admin", "nimda12_34$");
        Statement s = conexion.createStatement();
        
        Dieta dieta = dietas[0][0];
        
        for (int fila = 0; fila < 4; fila++) {

            ResultSet listadoRecetasDisponibles = s.executeQuery("SELECT IdReceta FROM horareceta WHERE IdHora = " + (fila + 1));
            ArrayList<String> recetasDisponibles = new ArrayList<String>();
            
            while (listadoRecetasDisponibles.next()) { // Recogemos las recetas disponibles para la hora
                
                recetasDisponibles.add(listadoRecetasDisponibles.getString("IdReceta"));
            }
            
            for (int celda = 0; celda < 7; celda++) { // Para cada celda aplicamos las condiciones
                
                dieta = dietas[fila][celda];

                // Si es lunes nos dará igual la receta menos comida libre
                if (dieta.getDia().equals("Lunes")) {
                    
                    // Si estamos de la comida en adelante, hacemos que no se repita la receta del día anterior
                    if (fila > 0) {
                        
                        compruebaCelda(
                                dietas[fila][celda],
                                eliminaRecetaDiaAnterior(dietas[fila - 1][6], BiblioArrayList.copiaArrayList(recetasDisponibles)),
                                s
                        );
                        
                    } else {
                        
                        compruebaCelda(dietas[fila][celda], BiblioArrayList.copiaArrayList(recetasDisponibles), s);
                    }
                
                // Si es sábado o domingo podrá tocar comida libre y tendrá más posibilidades de aparecer
                // en la comida y en la cena. Falta implementar que solo puede aparecer 3 máximo
                } else if (dieta.getDia().equals("Sábado") || dieta.getDia().equals("Domingo")) {
                    
                    compruebaSabadoDomingo(
                            dietas[fila][celda],
                            eliminaRecetaDiaAnterior(dietas[fila][celda - 1], BiblioArrayList.copiaArrayList(recetasDisponibles)),
                            s
                    );
                
                // Si es cualquier otro día se deberá comprobar que no se repita con la del día anterior
                } else {
                    
                    compruebaCelda(
                            dietas[fila][celda],
                            eliminaRecetaDiaAnterior(dietas[fila][celda - 1], BiblioArrayList.copiaArrayList(recetasDisponibles)),
                            s
                    );
                }
            }
        }
    }
    
    // ELIMINA LA RECETA DEL DÍA ANTERIOR PARA QUE ESTA NO SE REPITA
    private static ArrayList<String> eliminaRecetaDiaAnterior(Dieta dieta, ArrayList<String> recetasDisponibles) {
        
        // CREO QUE LISTO, REVISAR
        
        String idReceta = dieta.getIdReceta();
        ArrayList<String> copiaRecetasDisponibles = recetasDisponibles;
        
        copiaRecetasDisponibles.remove(idReceta);
        
        return copiaRecetasDisponibles;
    }
    
    // ELIGE LA RECETA PARA EL SÁBADO Y EL DOMINGO
    private static void compruebaSabadoDomingo(Dieta dieta, ArrayList<String> recetasDisponibles, Statement s)
            throws SQLException {
        
        // FALTA IMPLEMENTAR EL LÍMITE DE 3 COMIDAS LIBRES

        String libre = devuelveIdComidaLibre(s);
        ArrayList<String> copiaRecetasDisponibles = recetasDisponibles;
        
        // Si es la hora de la comida o cena es más probable que toque comida libre
        if (dieta.getIdHora().equals("2") || dieta.getIdHora().equals("4")) {
            
            copiaRecetasDisponibles.add(libre);
            
            dieta.setIdReceta(recetaAleatoria(copiaRecetasDisponibles));
            
        } else {
            
            dieta.setIdReceta(recetaAleatoria(copiaRecetasDisponibles));
        }
    }
    
    // ELIGE LA RECETA PARA EL RESTO DE DÍAS
    private static void compruebaCelda(Dieta dieta, ArrayList<String> recetasDisponibles, Statement s)
            throws SQLException {
        
        String libre = devuelveIdComidaLibre(s);
        ArrayList<String> copiaRecetasDisponibles = recetasDisponibles;
        
        copiaRecetasDisponibles.remove(libre); // La eliminamos de las recetas disponibles

        // Cogemos una receta aleatoria
        dieta.setIdReceta(recetaAleatoria(copiaRecetasDisponibles));
    }
    
    // SELECCIONA UNA RECETA ALEATORIA
    private static String recetaAleatoria(ArrayList<String> copiaRecetasDisponibles) {
        
        return copiaRecetasDisponibles.get(BiblioMatematicas.aleatorio(0, copiaRecetasDisponibles.size() - 1));
    }

    // DEVUELVE LA ID DE LA COMIDA LIBRE
    private static String devuelveIdComidaLibre(Statement s) throws SQLException {
        
        ResultSet listadoIdLibre = s.executeQuery("SELECT Id FROM receta WHERE nombre = '" + "Libre'");
        String idLibre = "";
        
        while (listadoIdLibre.next()) { // Obtenemos la Id de la comida libre
            
            idLibre =listadoIdLibre.getString("Id");
        }
        
        return idLibre;
    }

    public String getIdReceta() {
        
        return idReceta;
    }
    
    public String getNombreReceta() {
        
        return nombreReceta;
    }

    public void setIdReceta(String idReceta) {
        
        this.idReceta = idReceta;
    }
    
    public String getIdHora() {
        
        return idHora;
    }
    
    public String getDia() {
        
        return dia;
    }
}
