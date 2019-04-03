package daosImplementaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import daos.UsuarioDAO;
import entidades.Rol;
import entidades.Usuario;
import servicios.RolServicio;
import servicios.UsuarioServicio;

public class UsuarioDAOImpl implements UsuarioDAO {
	
	private Connection conexion;
	
	public UsuarioDAOImpl() {
		
	}

	@Override
	public Usuario buscaPorId(int id) throws SQLException {
			
		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsUsuario = s.executeQuery("exec USbuscaPorId " + id);
		
		Usuario usuario = null;
		RolServicio rolServicio = new RolServicio();
		
		while (rsUsuario.next()) { // Recogemos los datos del usuario
			
			int idUsuario = Integer.parseInt(rsUsuario.getString("Id"));
			Rol rol = rolServicio.buscaPorId(Integer.parseInt(rsUsuario.getString("IdRol")));
			String nombre = rsUsuario.getString("Nombre");
			String contrasena = rsUsuario.getString("Contrasena");
			String expiraDieta = rsUsuario.getString("ExpiraDieta");
			
			usuario = new Usuario(idUsuario, rol, nombre, contrasena, expiraDieta); // Creamos el objeto del usuario
		}
		
		conexion.close();
		return usuario;
	}

	@Override
	public Usuario buscaPorNombre(String nombre) throws SQLException {
		
		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsUsuario = s.executeQuery("exec USbuscaPorNombre " + nombre);
		
		Usuario usuario = null;
		RolServicio rolServicio = new RolServicio();
		
		while (rsUsuario.next()) { // Recogemos los datos del usuario
			
			int id = Integer.parseInt(rsUsuario.getString("Id"));
			Rol rol = rolServicio.buscaPorId(Integer.parseInt(rsUsuario.getString("IdRol")));
			String nom = rsUsuario.getString("Nombre");
			String contrasena = rsUsuario.getString("Contrasena");
			String expiraDieta = rsUsuario.getString("ExpiraDieta");
			
			usuario = new Usuario(id, rol, nom, contrasena, expiraDieta); // Creamos el objeto del usuario
		}
		
		conexion.close();
		return usuario;
	}

	@Override
	public List<Usuario> buscaTodos() throws SQLException {
		
		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsUsuario = s.executeQuery("exec USbuscaTodos");
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		RolServicio rolServicio = new RolServicio();
		
		while (rsUsuario.next()) { // Recogemos los datos de los usuarios
			
			int id = Integer.parseInt(rsUsuario.getString("Id"));
			Rol rol = rolServicio.buscaPorId(Integer.parseInt(rsUsuario.getString("IdRol")));
			String nombre = rsUsuario.getString("Nombre");
			String contrasena = rsUsuario.getString("Contrasena");
			String expiraDieta = rsUsuario.getString("ExpiraDieta");
			
			Usuario usuario = new Usuario(id, rol, nombre, contrasena, expiraDieta); // Creamos el objeto del usuario
			usuarios.add(usuario); // Añadimos los usuarios a la lista de usuarios
		}
		
		conexion.close();
		return usuarios;
	}

	@Override
	public boolean salva(Usuario usuario) throws SQLException {
		// TODO REVISAR

		UsuarioServicio usuarioServicio = new UsuarioServicio();
		
		Usuario usuarioObj = usuarioServicio.buscaPorId(usuario.getId());
		boolean resultado = false;
		
		if (usuarioObj.equals(null)) {
			
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
			Statement s = conexion.createStatement();
			
			if (s.execute("exec USsalva " + usuario.getId() + ", " + usuario.getNombre())) {
				
				conexion.close();
				resultado = true;
			}
		}
		
		return resultado;
	}

	@Override
	public boolean elimina(int id) throws SQLException {
		// TODO REVISAR

		UsuarioServicio usuarioServicio = new UsuarioServicio();
		
		Usuario usuario = usuarioServicio.buscaPorId(id);
		boolean resultado = false;
		
		if (usuario.equals(null)) {
			
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
			Statement s = conexion.createStatement();
			
			if (s.execute("exec USelimina " + id)) {
				
				conexion.close();
				resultado = true;
			}
		}
		
		return resultado;
	}

	@Override
	public boolean actualiza(Usuario usuario) throws SQLException {
		// TODO IMPLEMENTAR
		return false;
	}
}
