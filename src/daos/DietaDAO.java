package daos;

import java.sql.SQLException;
import java.util.List;

import entidades.Dieta;

/**
 * Interfaz que define la forma de obtener los datos de las dietas existentes en la base de datos.
 * @see daos.RecetaDAO RecetaDAO
 * @see daos.RolDAO RolDAO
 * @see daos.HoraDAO HoraDAO
 * @see daos.UsuarioDAO UsuarioDAO
 */
public interface DietaDAO {

	/**
	 * Devuelve una lista de dietas existentes en la base de datos dada la Id
	 * del usuario al que pertenecen.
	 * 
	 * @param id La {@link entidades.Usuario#id id} del usuario.
	 * @return Una lista de {@link entidades.Dieta dietas}.
	 * @throws SQLException
	 */
	public List<Dieta> buscaPorIdUsuario(int id) throws SQLException;
	
	/**
	 * Salva una dieta completa en la base de datos.
	 * 
	 * @param dietas La lista de {@link entidades.Dieta dietas} que se va a salvar.
	 * @return Devuelve <b>true</b> si el salvado es exitoso, <b>false</b> si no.
	 * @throws SQLException
	 */
	public boolean salva(List<Dieta> dietas) throws SQLException;
	
	/**
	 * Elimina una dieta completa existente en la base de datos.
	 * 
	 * @param idUsuario La {@link entidades.Usuario#id id} del {@link entidades.Usuario usuario}
	 * al que pertenece la dieta.
	 * @return Devuelve <b>true</b> si la eliminación es exitosa, <b>false</b> si no.
	 * @throws SQLException
	 */
	public boolean elimina(int id) throws SQLException;
	
	/**
	 * Actualiza una dieta completa existente en la base de datos.
	 * 
	 * @param id La {@link entidades.Usuario#id id} del {@link entidades.Usuario usuario}
	 * al que pertenece la dieta.
	 * @return Devuelve <b>true</b> si la actualización es exitosa, <b>false</b> si no.
	 * @throws SQLException
	 */
	public boolean actualiza(int id) throws SQLException, ClassNotFoundException;
}
