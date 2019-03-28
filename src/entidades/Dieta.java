package entidades;

/**
 * Esta clase representa a un registro de la entidad Dieta
 * en la base de datos. Una dieta se compone de un {@link #usuario usuario},
 * una {@link #receta receta}, una {@link #hora hora} y un {@link #dia día}.
 */
public class Dieta {

	/**
	 * El {@link Usuario usuario} referenciado al que pertenece la dieta.
	 * 
	 * @see #getUsuario()
	 * @see #setUsuario(Usuario)
	 */
	private Usuario usuario;
	
	/**
	 * La {@link Receta receta} referenciada.
	 * 
	 * @see #getReceta()
	 * @see #setReceta(Receta)
	 */
	private Receta receta;
	
	/**
	 * La {@link Hora hora} referenciada.
	 * 
	 * @see #getHora()
	 * @see #setHora(Hora)
	 */
	private Hora hora;
	
	/**
	 * El día de la semana, por ejemplo: lunes.
	 * 
	 * @see #getDia()
	 * @see #setDia(String)
	 */
	private String dia;
	
	/**
	 * Crea una instancia de la clase {@link Dieta Dieta}.
	 * 
	 * @param usuario El {@link #usuario usuario} al cual pertenece la dieta.
	 * @param receta La {@link #receta receta} incluida en la dieta.
	 * @param hora La {@link #hora hora} de la dieta.
	 * @param dia El {@link #dia día} de la dieta.
	 */
	public Dieta(Usuario usuario, Receta receta, Hora hora, String dia) {

		this.usuario = usuario;
		this.receta = receta;
		this.hora = hora;
		this.dia = dia;
	}

	/**
	 * Devuelve el {@link #usuario usuario} de la dieta
	 * 
	 * @return El usuario de la dieta.
	 */
	public Usuario getUsuario() {
		
		return usuario;
	}

	/**
	 * Actualiza el {@link #usuario usuario} de la dieta.
	 * 
	 * @param usuario El nuevo usuario.
	 */
	public void setUsuario(Usuario usuario) {
		
		this.usuario = usuario;
	}

	/**
	 * Devuelve la {@link #receta receta} de la dieta
	 * 
	 * @return La receta de la dieta.
	 */
	public Receta getReceta() {
		
		return receta;
	}

	/**
	 * Actualiza la {@link #receta receta} de la dieta.
	 * 
	 * @param receta La nueva receta.
	 */
	public void setReceta(Receta receta) {
		
		this.receta = receta;
	}

	/**
	 * Devuelve la {@link #hora hora} de la dieta
	 * 
	 * @return La hora de la dieta.
	 */
	public Hora getHora() {
		
		return hora;
	}

	/**
	 * Actualiza la {@link #hora hora} de la dieta.
	 * 
	 * @param hora La nueva hora.
	 */
	public void setHora(Hora hora) {
		
		this.hora = hora;
	}

	/**
	 * Devuelve el {@link #dia día} de la dieta
	 * 
	 * @return El día de la dieta.
	 */
	public String getDia() {
		
		return dia;
	}

	/**
	 * Actualiza el {@link #dia día} de la dieta.
	 * 
	 * @param dia El nuevo día.
	 */
	public void setDia(String dia) {
		
		this.dia = dia;
	}
}
