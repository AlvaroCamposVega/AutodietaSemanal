package daosImplementaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import daos.RecetaDAO;
import entidades.Receta;

public class RecetaDAOImpl implements RecetaDAO {
	
	private Connection conexion;
	
	public RecetaDAOImpl() {
		
	}

	@Override
	public Receta buscaPorId(int id) throws SQLException {
		
		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsReceta = s.executeQuery("exec REbuscaPorId " + id);
		
		Receta receta = null;
		
		while (rsReceta.next()) { // Recogemos los datos de la receta
			
			int idReceta = Integer.parseInt(rsReceta.getString("Id"));
			String nombre = rsReceta.getString("Nombre");
			String ingredientes = rsReceta.getString("Ingredientes");
			String descripcion = rsReceta.getString("Descripcion");
			
			receta = new Receta(idReceta, nombre, ingredientes, descripcion); // Creamos el objeto de la receta
		}
		
		conexion.close();
		return receta;
	}

	@Override
	public List<Receta> buscaTodas() throws SQLException {

		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsReceta = s.executeQuery("exec REbuscaTodas");
		
		List<Receta> recetas = new ArrayList<Receta>();
		
		while (rsReceta.next()) { // Recogemos los datos de las recetas
			
			int id = Integer.parseInt(rsReceta.getString("Id"));
			String nombre = rsReceta.getString("Nombre");
			String ingredientes = rsReceta.getString("Ingredientes");
			String descripcion = rsReceta.getString("Descripcion");
			
			Receta receta = new Receta(id, nombre, ingredientes, descripcion); // Creamos el objeto de la receta
			recetas.add(receta); // Añadimos las recetas a la lista de recetas
		}
		
		conexion.close();
		return recetas;
	}

	@Override
	public boolean salva(Receta receta) throws SQLException {
		// TODO IMPLEMENTAR
		return false;
	}

	@Override
	public boolean elimina(int id) throws SQLException {
		// TODO IMPLEMENTAR
		return false;
	}

	@Override
	public boolean actualiza(Receta receta) throws SQLException {
		// TODO IMPLEMENTAR
		return false;
	}
}
