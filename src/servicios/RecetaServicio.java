package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import daos.RecetaDAO;
import daosImplementaciones.RecetaDAOImpl;
import entidades.Receta;

/**
 * Esta clase es el servicio que usará el cliente
 * para obtener datos de una receta de la base de datos.
 */
public class RecetaServicio implements RecetaDAO {
	
	private RecetaDAOImpl recetaDAO = new RecetaDAOImpl();

	/**
	 * Genera una instancia del {@link RecetaServicio servicio}.
	 */
	public RecetaServicio() {

	}

	@Override
	public Receta buscaPorId(int id) throws SQLException {

		return recetaDAO.buscaPorId(id);
	}

	@Override
	public List<Receta> buscaTodas() throws SQLException {

		return recetaDAO.buscaTodas();
	}

	@Override
	public boolean salva(Receta receta) throws SQLException {

		return recetaDAO.salva(receta);
	}

	@Override
	public boolean elimina(int id) throws SQLException {

		return recetaDAO.elimina(id);
	}

	@Override
	public boolean actualiza(Receta receta) throws SQLException {

		return recetaDAO.actualiza(receta);
	}
	
//	/**
//	 * Devuelve una lista con todas las horas disponibles para una receta con la id especificada.
//	 * Por ejemplo, la receta con nombre "pan con aceite" puede consumirse tanto en el desayuno
//	 * como en la merienda.
//	 * 
//	 * @param id La {@link entidades.Receta#id id} de la {@link entidades.Receta receta}.
//	 * @return Una lista con todas las horas disponibles.
//	 * @throws SQLException
//	 */
//	public List<Hora> buscaHorasPorIdReceta(int id) throws SQLException {
//		
//		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
//		Statement s = conexion.createStatement();
//		ResultSet rsHora = s.executeQuery("exec HRbuscaPorIdReceta " + id);
//
//		HoraServicio horaServicio = new HoraServicio();
//		
//		List<Hora> horas = new ArrayList<Hora>();
//		
//		while (rsHora.next()) { // Recogemos los datos de las horas
//
//			Hora hora = horaServicio.buscaPorId(Integer.parseInt(rsHora.getString("IdHora"))); // Creamos el objeto de la hora
//
//			horas.add(hora); // Añadimos las horas a la lista de horas
//		}
//		
//		conexion.close();
//		return horas;
//	}
	
	/**
	 * Devuelve una lista con todas las recetas disponibles para una hora
	 * concreta.
	 * 
	 * @param hora La {@link entidades.Hora hora}.
	 * @return Una lista con todas las recetas disponibles.
	 * @throws SQLException
	 */
	public List<Receta> buscaRecetasPorIdHora(int id) throws SQLException {
		
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsReceta = s.executeQuery("exec HRbuscaPorIdHora " + id);

		RecetaServicio recetaServicio = new RecetaServicio();
		
		List<Receta> recetas = new ArrayList<Receta>();
		
		while (rsReceta.next()) { // Recogemos los datos de las recetas

			Receta receta = recetaServicio.buscaPorId(Integer.parseInt(rsReceta.getString("IdReceta"))); // Creamos el objeto de la receta

			recetas.add(receta); // Añadimos las recetas a la lista de recetas
		}
		
		conexion.close();
		return recetas;
	}
	
	/**
	 * Devuelve la receta de la comida libre.
	 * 
	 * @return La receta de la comida libre.
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public Receta buscaRecetaComidaLibre() throws NumberFormatException, SQLException {
		
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsRecetaComidaLibre = s.executeQuery("exec REbuscaComidaLibre");
		
		Receta recetaComidaLibre = null;
		
		while (rsRecetaComidaLibre.next()) { // Recogemos los datos de la receta

			int id = Integer.parseInt(rsRecetaComidaLibre.getString("Id"));
			String nombre = rsRecetaComidaLibre.getString("Nombre");
			String ingredientes = rsRecetaComidaLibre.getString("Ingredientes");
			String descripcion = rsRecetaComidaLibre.getString("Descripcion");
			
			recetaComidaLibre = new Receta(id, nombre, ingredientes, descripcion); // Creamos el objeto de la receta
		}
		
		conexion.close();
		return recetaComidaLibre;
	}
}
