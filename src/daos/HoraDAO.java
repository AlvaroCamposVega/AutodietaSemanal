package daos;

import java.sql.SQLException;
import java.util.List;

import entidades.Hora;

/**
 * Interfaz que define la forma de obtener los datos de las horas existentes en la base de datos.
 * @see daos.RecetaDAO RecetaDAO
 * @see daos.RolDAO RolDAO
 * @see daos.UsuarioDAO UsuarioDAO
 */
public interface HoraDAO {
	
	/**
	 * Devuelve una hora existente en la base de datos dada su Id.
	 * 
	 * @param id La {@link entidades.Hora#id id} de la hora.
	 * @return Objeto de la clase {@link entidades.Hora Hora}.
	 * @throws SQLException
	 */
	public Hora buscaPorId(int id) throws SQLException;
	
	/**
	 * Devuelve una lista con todas las horas existentes en la base de datos.
	 * 
	 * @return Lista de todas las horas.
	 * @throws SQLException
	 */
	public List<Hora> buscaTodas() throws SQLException;
}
