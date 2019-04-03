package daosImplementaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import daos.DietaDAO;
import entidades.Dieta;
import entidades.Hora;
import entidades.Receta;
import entidades.Usuario;
import servicios.DietaServicio;
import servicios.HoraServicio;
import servicios.RecetaServicio;
import servicios.UsuarioServicio;

public class DietaDAOImpl implements DietaDAO {

	private Connection conexion;

	public DietaDAOImpl() {

	}

	@Override
	public List<Dieta> buscaPorIdUsuario(int id) throws SQLException {
		
		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsDieta = s.executeQuery("exec DIbuscaPorIdUsuario " + id);
		
		RecetaServicio recetaServicio = new RecetaServicio();
		HoraServicio horaServicio = new HoraServicio();
		UsuarioServicio usuarioServicio = new UsuarioServicio();
		
		List<Dieta> dietas = new ArrayList<Dieta>();
		Dieta dieta = null;
		
		while (rsDieta.next()) { // Recogemos los datos de las dietas
			
			Usuario usuario = usuarioServicio.buscaPorId(Integer.parseInt(rsDieta.getString("IdUsuario")));
			Receta receta = recetaServicio.buscaPorId(Integer.parseInt(rsDieta.getString("IdReceta")));
			Hora hora = horaServicio.buscaPorId(Integer.parseInt(rsDieta.getString("IdHora")));
			String dia = rsDieta.getString("Dia");
			
			dieta = new Dieta(usuario, receta, hora, dia); // Creamos el objeto de la dieta
			dietas.add(dieta); // Añadimos las dietas a la lista de dietas
		}
		
		conexion.close();
		return dietas;
	}

	@Override
	public boolean salva(List<Dieta> dietas) throws SQLException {

		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
        String insertarDieta = "INSERT INTO dieta (IdUsuario, IdReceta, IdHora, Dia) VALUES ";
        
        int id = dietas.get(0).getUsuario().getId();
        
        for (Dieta dieta : dietas) { // Recorremos la lista de dietas para configurar la variable de inserción
        	
        	// VARIABLES
    		Receta receta = dieta.getReceta();
			Hora hora = dieta.getHora();
			String dia = dieta.getDia();
			
        	if (dietas.get(0).equals(dieta)) { // El primer registro no tiene una coma al principio
        		
        		insertarDieta += "('" + id + "', '" + receta.getId() + "', '"
        			+ hora.getId() + "', '" + dia + "')";
        		
        	} else { // Los demás sí
        		
        		insertarDieta += ", ('" + id + "', '" + receta.getId() + "', '"
	                + hora.getId() + "', '" + dia + "')";
        	}
		}

		boolean resultado = false;
        
        if (s.execute(insertarDieta)) {
        	
        	conexion.close();
        	resultado = true;
        }
		
        conexion.close();
		return resultado;
	}

	@Override
	public boolean elimina(int id) throws SQLException {
		
		DietaServicio dietaServicio = new DietaServicio();
		
		List<Dieta> dietas = dietaServicio.buscaPorIdUsuario(id);
		boolean resultado = false;
		
		if (!(dietas.size() == 0)) {

			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
			Statement s = conexion.createStatement();
			
			if (s.execute("exec DIelimina " + id)) {
				
				conexion.close();
				resultado = true;
			}
		}
        
		return resultado;
	}

	@Override
	public boolean actualiza(int id) throws SQLException, ClassNotFoundException {

		DietaServicio dietaServicio = new DietaServicio();
		
		boolean resultado = false;
		
		if (dietaServicio.elimina(id)) {
			
			resultado = true;
			
		} else {
			
			resultado = false;
		}
		
		if (dietaServicio.salva(dietaServicio.generaDieta(id))) {
			
			resultado = true;
		}
		
		return resultado;
	}
}
