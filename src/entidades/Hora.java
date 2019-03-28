package entidades;

/**
 * Esta clase representa a un registro de la entidad Hora
 * en la base de datos. Una hora se compone de una {@link #id id} y una {@link #hora hora}.
 */
public class Hora {

	/**
	 * La Id de la hora.
	 * 
	 * @see #getId()
	 * @see #setId(int)
	 */
	private int id;
	
	/**
	 * La hora, por ejemplo: comida.
	 * 
	 * @see #getHora()
	 * @see #setHora(int)
	 */
	private String hora;
	
	/**
	 * Crea una instancia de la clase {@link Hora Hora}.
	 * 
	 * @param id La {@link #id id} de la Hora.
	 * @param hora La {@link #hora hora}.
	 */
	public Hora(int id, String hora) {

		this.id = id;
		this.hora = hora;
	}

	/**
	 * Devuelve la {@link #id id} de la {@link Hora Hora}.
	 * 
	 * @return La id de la Hora.
	 */
	public int getId() {
		
		return id;
	}

	/**
	 * Actualiza la {@link #id id} de la {@link Hora Hora}.
	 * 
	 * @param id La nueva id que va a adquirir la Hora.
	 */
	public void setId(int id) {
		
		this.id = id;
	}

	/**
	 * Devuelve la {@link #hora hora}.
	 * 
	 * @return La hora.
	 */
	public String getHora() {
		
		return hora;
	}

	/**
	 * Actualiza la {@link #hora hora}.
	 * 
	 * @param hora La nueva hora que va a adquirir la {@link Hora Hora}.
	 */
	public void setHora(String hora) {
		
		this.hora = hora;
	}
}
