package daos;

import java.sql.SQLException;
import java.util.List;

import entidades.Receta;

/**
 * Interfaz que define la forma de obtener los datos de las recetas existentes en la base de datos.
 * @see daos.HoraDAO HoraDAO
 * @see daos.RolDAO RolDAO
 * @see daos.UsuarioDAO UsuarioDAO
 * @see daos.DietaDAO DietaDAO
 */
public interface RecetaDAO {

	/**
	 * Devuelve una receta existente en la base de datos dada su Id.
	 * 
	 * @param id La {@link entidades.Receta#id id} de la receta.
	 * @return Objeto de la clase {@link entidades.Receta Receta}.
	 * @throws SQLException
	 */
	public Receta buscaPorId(int id) throws SQLException;
	
	/**
	 * Devuelve una lista con todas las recetas existentes en la base de datos.
	 * 
	 * @return Lista de todas las recetas.
	 * @throws SQLException
	 */
	public List<Receta> buscaTodas() throws SQLException;
	
	/**
	 * Salva una receta nueva en la base de datos.
	 * 
	 * @param receta La {@link entidades.Receta receta} que se va a salvar.
	 * @return Devuelve <b>true</b> si el salvado es exitoso, <b>false</b> si no.
	 * @throws SQLException
	 */
	public boolean salva(Receta receta) throws SQLException;
	
	/**
	 * Elimina una receta existente en la base de datos.
	 * 
	 * @param id La {@link entidades.Receta#id id} de la {@link entidades.Receta receta}.
	 * @return Devuelve <b>true</b> si la eliminación es exitosa, <b>false</b> si no.
	 * @throws SQLException
	 */
	public boolean elimina(int id) throws SQLException;
	
	/**
	 * Actualiza una receta existente en la base de datos.
	 * 
	 * @param receta La {@link entidades.Receta receta} con los nuevos datos.
	 * @return Devuelve <b>true</b> si la actualización es exitosa, <b>false</b> si no.
	 * @throws SQLException
	 */
	public boolean actualiza(Receta receta) throws SQLException;
}
