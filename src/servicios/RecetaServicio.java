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
import entidades.Hora;
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
	
	/**
	 * Devuelve una lista con todas las horas disponibles para una receta con la id especificada.
	 * Por ejemplo, la receta con nombre "pan con aceite" puede consumirse tanto en el desayuno
	 * como en la merienda.
	 * 
	 * @param id La {@link entidades.Receta#id id} de la {@link entidades.Receta receta}.
	 * @return Una lista con todas las horas disponibles.
	 * @throws SQLException
	 */
	public List<Hora> buscaHorasPorIdReceta(int id) throws SQLException {
		
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsHora = s.executeQuery("exec HRbuscaPorIdReceta " + id);

		HoraServicio horaServicio = new HoraServicio();
		
		List<Hora> horas = new ArrayList<Hora>();
		
		while (rsHora.next()) { // Recogemos los datos de las dietas

			Hora hora = horaServicio.buscaPorId(Integer.parseInt(rsHora.getString("IdHora"))); // Creamos el objeto de la hora

			horas.add(hora); // Añadimos las horas a la lista de horas
		}
		
		conexion.close();
		return horas;
	}

}
