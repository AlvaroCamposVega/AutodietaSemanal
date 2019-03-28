package daosImplementaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import daos.RolDAO;
import entidades.Rol;

public class RolDAOImpl implements RolDAO {

	private Connection conexion;
	
	public RolDAOImpl() {
		
	}
	
	@Override
	public Rol buscaPorId(int id) throws SQLException {
		
		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsRol = s.executeQuery("exec RObuscaPorId " + id);
		
		Rol rol = null;
		
		while (rsRol.next()) { // Recogemos los datos del rol
			
			int idRol = Integer.parseInt(rsRol.getString("Id"));
			String nomRol = rsRol.getString("Rol");
			
			rol = new Rol(idRol, nomRol); // Creamos el objeto del rol
		}
		
		conexion.close();
		return rol;
	}

	@Override
	public Rol buscaPorRol(String rol) throws SQLException {
		
		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsRol = s.executeQuery("exec RObuscaPorRol " + rol);
		
		Rol rolObj = null;
		
		while (rsRol.next()) { // Recogemos los datos del rol
			
			int idRol = Integer.parseInt(rsRol.getString("Id"));
			String nomRol = rsRol.getString("Rol");
			
			rolObj = new Rol(idRol, nomRol); // Creamos el objeto del rol
		}
		
		conexion.close();
		return rolObj;
	}

	@Override
	public List<Rol> buscaTodos() throws SQLException {
		
		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsRol = s.executeQuery("exec RObuscaTodos");
		
		List<Rol> roles = new ArrayList<Rol>();
		
		while (rsRol.next()) { // Recogemos los datos de los roles
			
			int idRol = Integer.parseInt(rsRol.getString("Id"));
			String nomRol = rsRol.getString("Rol");
			
			Rol rol = new Rol(idRol, nomRol); // Creamos el objeto del rol
			roles.add(rol); // Añadimos los roles a la lista de roles
		}
		
		conexion.close();
		return roles;
	}
}
