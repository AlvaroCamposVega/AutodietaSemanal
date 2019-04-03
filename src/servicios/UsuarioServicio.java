package servicios;

import java.sql.SQLException;
import java.util.List;

import daos.UsuarioDAO;
import daosImplementaciones.UsuarioDAOImpl;
import entidades.Rol;
import entidades.Usuario;

/**
 * Esta clase es el servicio que usará el cliente
 * para obtener datos de un usuario de la base de datos.
 */
public class UsuarioServicio implements UsuarioDAO {

	private UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
	
	/**
	 * Genera una instancia del {@link UsuarioServicio servicio}.
	 */
	public UsuarioServicio() {

	}

	@Override
	public Usuario buscaPorId(int id) throws SQLException {

		return usuarioDAO.buscaPorId(id);
	}

	@Override
	public Usuario buscaPorNombre(String nombre) throws SQLException {

		return usuarioDAO.buscaPorNombre(nombre);
	}

	public List<Usuario> buscaPorRol(Rol rol) throws SQLException {
		
		return usuarioDAO.buscaPorRol(rol);
	}

	@Override
	public List<Usuario> buscaTodos() throws SQLException {

		return usuarioDAO.buscaTodos();
	}

	@Override
	public boolean salva(Usuario usuario) throws SQLException {

		return usuarioDAO.salva(usuario);
	}

	@Override
	public boolean elimina(int id) throws SQLException {

		return usuarioDAO.elimina(id);
	}

	@Override
	public boolean actualiza(Usuario usuario) throws SQLException {

		return usuarioDAO.actualiza(usuario);
	}
}
