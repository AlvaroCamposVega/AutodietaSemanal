package daosImplementaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import daos.HoraDAO;
import entidades.Hora;

public class HoraDAOImpl implements HoraDAO {

	private Connection conexion;
	
	public HoraDAOImpl() {
		
	}
	
	@Override
	public Hora buscaPorId(int id) throws SQLException {

		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsHora = s.executeQuery("exec HObuscaPorId " + id);
		
		Hora hora = null;
		
		while (rsHora.next()) { // Recogemos los datos de la hora
			
			int idHora = Integer.parseInt(rsHora.getString("Id"));
			String nomHora = rsHora.getString("Hora");
			
			hora = new Hora(idHora, nomHora); // Creamos el objeto de la hora
		}
		
		conexion.close();
		return hora;
	}

	@Override
	public List<Hora> buscaTodas() throws SQLException {

		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		ResultSet rsHora = s.executeQuery("exec HObuscaTodas");
		
		List<Hora> horas = new ArrayList<Hora>();
		
		while (rsHora.next()) { // Recogemos los datos de las horas
			
			int idHora = Integer.parseInt(rsHora.getString("Id"));
			String nomHora = rsHora.getString("Hora");
			
			Hora hora = new Hora(idHora, nomHora); // Creamos el objeto de la hora
			horas.add(hora); // Añadimos las horas a la lista de horas
		}
		
		conexion.close();
		return horas;
	}
}
