package daos;

import java.sql.SQLException;
import java.util.List;

import entidades.Rol;

/**
 * Interfaz que define la forma de obtener los datos de los roles existentes en la base de datos.
 * @see daos.RecetaDAO RecetaDAO
 * @see daos.HoraDAO HoraDAO
 * @see daos.UsuarioDAO UsuarioDAO
 * @see daos.DietaDAO DietaDAO
 */
public interface RolDAO {

	/**
	 * Devuelve un rol existente en la base de datos dada su Id.
	 * 
	 * @param id La {@link entidades.Rol#id id} del rol.
	 * @return Objeto de la clase {@link entidades.Rol Rol}.
	 * @throws SQLException
	 */
	public Rol buscaPorId(int id) throws SQLException;
	
	/**
	 * Devuelve un rol existente en la base de datos dado su rol.
	 * 
	 * @param rol El {@link entidades.Rol#rol rol}.
	 * @return Objeto de la clase {@link entidades.Rol Rol}.
	 * @throws SQLException
	 */
	public Rol buscaPorRol(String rol) throws SQLException;
	
	/**
	 * Devuelve una lista con todos los roles existentes en la base de datos.
	 * 
	 * @return Lista de todos los roles.
	 * @throws SQLException
	 */
	public List<Rol> buscaTodos() throws SQLException;
}
