package servicios;

import java.sql.SQLException;
import java.util.List;

import daos.HoraDAO;
import daosImplementaciones.HoraDAOImpl;
import entidades.Hora;

/**
 * Esta clase es el servicio que usará el cliente
 * para obtener datos de una hora de la base de datos.
 */
public class HoraServicio implements HoraDAO {
	
	private HoraDAOImpl horaDAO = new HoraDAOImpl();

	/**
	 * Genera una instancia del {@link HoraServicio servicio}.
	 */
	public HoraServicio() {

	}

	@Override
	public Hora buscaPorId(int id) throws SQLException {

		return horaDAO.buscaPorId(id);
	}

	@Override
	public List<Hora> buscaTodas() throws SQLException {

		return horaDAO.buscaTodas();
	}

}
