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
import entidades.Dieta;
import entidades.Hora;
import entidades.Receta;
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
	 * Devuelve una lista con todas las dietas del usuario con la id especificada.
	 * 
	 * @param id La {@link entidades.Usuario#id id} del {@link entidades.Usuario usuario}.
	 * @return Una lista con todas las dietas.
	 * @throws SQLException
	 */
	public List<Dieta> buscaDietaPorIdUsuario(int id) throws SQLException {
		
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsDieta = s.executeQuery("exec DIbuscaPorIdUsuario " + id);
		
		RecetaServicio recetaServicio = new RecetaServicio();
		HoraServicio horaServicio = new HoraServicio();
		
		List<Dieta> dietas = new ArrayList<Dieta>();
		Dieta dieta = null;
		
		while (rsDieta.next()) { // Recogemos los datos de las dietas
			
			Usuario usuario = usuarioDAO.buscaPorId(Integer.parseInt(rsDieta.getString("IdUsuario")));
			Receta receta = recetaServicio.buscaPorId(Integer.parseInt(rsDieta.getString("IdReceta")));
			Hora hora = horaServicio.buscaPorId(Integer.parseInt(rsDieta.getString("IdHora")));
			String dia = rsDieta.getString("Dia");
			
			dieta = new Dieta(usuario, receta, hora, dia); // Creamos el objeto de la dieta
			dietas.add(dieta); // Añadimos las dietas a la lista de dietas
		}
		
		conexion.close();
		return dietas;
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
