package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	
	/**
	 * Devuelve una lista de usuarios pertenecientes al rol especificado.
	 * 
	 * @param rol El rol al que pertenecen los {@link entidades.Usuario usuarios}
	 * que se desean obtener.
	 * @return Una lista con los usuarios pertenecientes al
	 * {@link entidades.Rol rol} especificado.
	 * @throws SQLException
	 */
	public List<Usuario> buscaUsuariosPorRol(Rol rol) throws SQLException {
		
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsUsuarios = s.executeQuery("exec USbuscaTodosPorRol " + rol.getId());

		RolServicio rolServicio = new RolServicio();
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		while (rsUsuarios.next()) { // Recogemos los datos de las recetas

			int id = Integer.parseInt(rsUsuarios.getString("Id"));
			Rol rolUs = rolServicio.buscaPorId(Integer.parseInt(rsUsuarios.getString("IdRol")));
			String nombre = rsUsuarios.getString("Nombre");
			String contrasena = rsUsuarios.getString("Contrasena");
			String expiraDieta = rsUsuarios.getString("ExpiraDieta");

			usuarios.add(new Usuario(id, rolUs, nombre, contrasena, expiraDieta)); // Añadimos los usuarios a la lista de usuarios
		}
		
		conexion.close();
		return usuarios;
	}
}
