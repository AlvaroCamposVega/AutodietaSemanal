package servicios;

import java.sql.SQLException;
import java.util.List;

import daos.RolDAO;
import daosImplementaciones.RolDAOImpl;
import entidades.Rol;

/**
 * Esta clase es el servicio que usará el cliente
 * para obtener datos de un rol de la base de datos.
 */
public class RolServicio implements RolDAO {
	
	private RolDAOImpl rolDAO = new RolDAOImpl();

	/**
	 * Genera una instancia del {@link RolServicio servicio}.
	 */
	public RolServicio() {

	}

	@Override
	public Rol buscaPorId(int id) throws SQLException {

		return rolDAO.buscaPorId(id);
	}

	@Override
	public Rol buscaPorRol(String rol) throws SQLException {

		return rolDAO.buscaPorRol(rol);
	}

	@Override
	public List<Rol> buscaTodos() throws SQLException {

		return rolDAO.buscaTodos();
	}
	
	/**
	 * Devuelve el Rol de un usuario dada la id de este.
	 * 
	 * @param id La {@link entidades.Usuario#id id} del {@link entidades.Usuario usuario}.
	 * @return El {@link entidades.Rol Rol} del usuario.
	 * @throws SQLException
	 */
	public Rol buscaRolPorIdUsuario(int id) throws SQLException {
		
		UsuarioServicio usuarioServicio = new UsuarioServicio();
		
		// Creamos el objeto del rol obteniendo el rol referenciado en el usuario
		return usuarioServicio.buscaPorId(id).getRol();
	}
}
