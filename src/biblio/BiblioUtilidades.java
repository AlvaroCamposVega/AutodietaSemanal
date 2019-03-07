package biblio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import dieta.Dieta;

public class BiblioUtilidades {
    
    // Elimina el usuario y reordena las IDs para que no queden "huecos"
    public static void eliminaUsuarioCompruebaId(String idEliminar, Connection conexion) throws SQLException {
        
        Statement s = conexion.createStatement();
        int mayor = obtenerMayor(conexion);
        Dieta.eliminaDietaUsuario(idEliminar, s);
        
        if (!(Integer.parseInt(idEliminar) == mayor)) { // Si el usuario a eliminar no es el último
            
            String idPrivilegio = "";
            String nombre = "";
            String contrasena = "";
            String expiraDieta = "";
            ResultSet listaAtributosUsuario = s.executeQuery("SELECT IdPrivilegio, Nombre, Contrasena, ExpiraDieta" +
                " FROM usuario WHERE Id = " + mayor);
            
            while (listaAtributosUsuario.next()) { // Obtenemos los atributos del último usuario
                
                idPrivilegio = listaAtributosUsuario.getString("IdPrivilegio");
                nombre = listaAtributosUsuario.getString("Nombre");
                contrasena = listaAtributosUsuario.getString("Contrasena");
                expiraDieta = listaAtributosUsuario.getString("ExpiraDieta");
            }
            
            // Actualizamos el usuario antiguo para que sea el último
            String updateUsuarioAntiguo = "UPDATE usuario SET IdPrivilegio = " + idPrivilegio + ", Nombre = '" +
                nombre + "', Contrasena = '" + contrasena + "', ExpiraDieta = '" + expiraDieta + "' WHERE Id = " + idEliminar;
            s.execute(updateUsuarioAntiguo);
            // Actualizamos la dieta del usuario a su nueva ID
            String updateDieta = "UPDATE dieta SET IdUsuario = " + idEliminar + " WHERE IdUsuario = " + mayor;
            s.execute(updateDieta);
        }
        
        // Eliminamos el último usuario
        String eliminar = "DELETE FROM usuario WHERE Id = " + mayor;
        s.execute(eliminar);
    }
    
    // DEVUELVE LA ÚLTIMA ID (LA MÁS GRANDE)
    private static int obtenerMayor(Connection conexion)
            throws SQLException {
        
        Statement s = conexion.createStatement();
        ResultSet listadoIds = s.executeQuery("SELECT Id FROM usuario");
        String idLista = "";
        int mayor = 0;
        
        while (listadoIds.next()) {
            
            idLista = listadoIds.getString("Id");
            
            if (Integer.parseInt(idLista) > mayor) {
                
                mayor = Integer.parseInt(idLista);
            }
        }
        
        return mayor;
    }
    
    public static boolean compruebaFecha(String fecha) {
        
        // Obtenemos la fecha de hoy
        Calendar cal = Calendar.getInstance();

        int anyo = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        cal.clear();

        cal.set(Calendar.YEAR, anyo);
        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.DATE, dia);

        // Formateamos la fecha para que tenga el mismo formato que en sql
        Date hoy = new Date(cal.getTimeInMillis());
        // Comparamos las fechas
        int comparaFecha = hoy.compareTo(Date.valueOf(fecha));
        
        if (comparaFecha != (-1)) {
            
            return false;
        }
        
        return true;
    }
    
    public static int obtenerNuevaIdUsuario() throws ClassNotFoundException, SQLException {
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta","admin", "nimda12_34$");
        Statement s = conexion.createStatement();
        ResultSet listadoIds = s.executeQuery("SELECT Id FROM usuario");
        
        int idUsuario = 1;
        int idListado;
        
        /*
            Consultamos las Ids almacenadas para averiguar cuál es la última y así configurarle
            una nueva al nuevo usuario
        */
        while (listadoIds.next()) { // Consultamos las Ids de los usuarios almacenados
            
            idListado = Integer.parseInt(listadoIds.getString("ID"));
            
            if (idUsuario == idListado) { // Vamos incrementando la Id para el nuevo usuario
                
                idUsuario++;
            }
        }
        
        return idUsuario;
    }
}
