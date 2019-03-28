package entidades;

/**
 * Esta clase representa a un registro de la entidad Rol
 * en la base de datos. Un rol se compone de una {@link #id id} y un {@link #rol rol}.
 */
public class Rol {
	
	/**
	 * La Id del rol.
	 * 
	 * @see #getId()
	 * @see #setId(int)
	 */
	private int id;
	
	/**
	 * El rol.
	 * 
	 * @see #getRol()
	 * @see #setRol(String)
	 */
	private String rol;
	
	/**
	 * Crea una instancia de la clase {@link Rol Rol}.
	 * 
	 * @param id La {@link #id id} del rol.
	 * @param rol El rol.
	 */
	public Rol(int id, String rol) {

		this.id = id;
		this.rol = rol;
	}

	/**
	 * Devuelve la {@link #id id} del {@link Rol Rol}.
	 * 
	 * @return La id del Rol.
	 */
	public int getId() {
		
		return id;
	}

	/**
	 * Actualiza la {@link #id id} del {@link Rol Rol}.
	 * 
	 * @param id La nueva id que va a adquirir el Rol.
	 */
	public void setId(int id) {
		
		this.id = id;
	}

	/**
	 * Devuelve el {@link #rol rol}.
	 * 
	 * @return El rol.
	 */
	public String getRol() {
		
		return rol;
	}

	/**
	 * Actualiza el {@link #rol rol}.
	 * 
	 * @param rol El nuevo rol.
	 */
	public void setRol(String rol) {
		
		this.rol = rol;
	}
}
