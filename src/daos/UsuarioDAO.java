package daos;

import java.sql.SQLException;
import java.util.List;

import entidades.Usuario;

/**
 * Interfaz que define la forma de obtener los datos de los usuarios existentes en la base de datos.
 * @see daos.RecetaDAO RecetaDAO
 * @see daos.RolDAO RolDAO
 * @see daos.HoraDAO HoraDAO
 */
public interface UsuarioDAO {

	/**
	 * Devuelve un usuario existente en la base de datos dada su Id.
	 * 
	 * @param id La {@link entidades.Usuario#id id} del usuario.
	 * @return Objeto de la clase {@link entidades.Usuario Usuario}.
	 * @throws SQLException
	 */
	public Usuario buscaPorId(int id) throws SQLException;
	
	/**
	 * Devuelve un usuario existente en la base de datos dado su nombre.
	 * 
	 * @param nombre El {@link entidades.Usuario#nombre nombre} del usuario.
	 * @return Objeto de la clase {@link entidades.Usuario Usuario}.
	 * @throws SQLException
	 */
	public Usuario buscaPorNombre(String nombre) throws SQLException;
	
	/**
	 * Devuelve una lista con todos los usuarios existentes en la base de datos.
	 * 
	 * @return Lista de todos los usuarios.
	 * @throws SQLException
	 */
	public List<Usuario> buscaTodos() throws SQLException;
	
	/**
	 * Salva un usuario nuevo en la base de datos.
	 * 
	 * @param usuario El {@link entidades.Usuario usuario} que se va a salvar.
	 * @return Devuelve <b>true</b> si el salvado es exitoso, <b>false</b> si no.
	 * @throws SQLException
	 */
	public boolean salva(Usuario usuario) throws SQLException;
	
	/**
	 * Elimina un usuario existente en la base de datos.
	 * 
	 * @param id La {@link entidades.Usuario#id id} del {@link entidades.Usuario usuario}.
	 * @return Devuelve <b>true</b> si la eliminación es exitosa, <b>false</b> si no.
	 * @throws SQLException
	 */
	public boolean elimina(int id) throws SQLException;
	
	/**
	 * Actualiza un usuario existente en la base de datos.
	 * 
	 * @param usuario El {@link entidades.Usuario usuario} con los nuevos datos.
	 * @return Devuelve <b>true</b> si la actualización es exitosa, <b>false</b> si no.
	 * @throws SQLException
	 */
	public boolean actualiza(Usuario usuario) throws SQLException;
}
