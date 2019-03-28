package entidades;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Esta clase representa a un registro de la entidad Usuario
 * en la base de datos. Un usuario se compone de una {@link #id id},
 * un {@link #rol rol}, un {@link #nombre nombre}, una
 * {@link #contrasena contraseña} y una {@link #expiraDieta fecha de expiración de su dieta}.
 */
public class Usuario {

	/**
	 * La Id del usuario.
	 * 
	 * @see #getId()
	 * @see #setId(int)
	 */
	private int id;
	
	/**
	 * El {@link Rol rol} referenciado.
	 * 
	 * @see #getRol()
	 * @see #setRol(Rol)
	 */
	private Rol rol;
	
	/**
	 * El nombre del usuario.
	 * 
	 * @see #getNombre()
	 * @see #setNombre(String)
	 */
	private String nombre;
	
	/**
	 * La contraseña del usuario.
	 * 
	 * @see #getContrasena()
	 * @see #setContrasena(String)
	 */
	private String contrasena;
	
	/**
	 * La fecha en la que expira la dieta del usuario.
	 * 
	 * @see #getExpiraDieta()
	 * @see #setExpiraDieta(String)
	 */
	private String expiraDieta;

	/**
	 * Crea una instancia de la clase {@link Usuario Usuario}.
	 * 
	 * @param id La {@link #id id} del usuario.
	 * @param rol El {@link #rol rol} del usuario.
	 * @param nombre El {@link #nombre nombre} del usuario.
	 * @param contrasena La {@link #contrasena contraseña} del usuario.
	 * @param expiraDieta La {@link #expiraDieta fecha de expiración de la dieta} del usuario.
	 */
	public Usuario(int id, Rol rol, String nombre, String contrasena, String expiraDieta) {

		this.id = id;
		this.rol = rol;
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.expiraDieta = expiraDieta;
	}

	/**
	 * Devuelve la {@link #id id} del {@link Usuario Usuario}.
	 * 
	 * @return La id del Usuario.
	 */
	public int getId() {
		
		return id;
	}

	/**
	 * Actualiza la {@link #id id} del {@link Usuario Usuario}.
	 * 
	 * @param id La nueva id que va a adquirir el Usuario.
	 */
	public void setId(int id) {
		
		this.id = id;
	}

	/**
	 * Devuelve el {@link #rol rol} del {@link Usuario Usuario}.
	 * 
	 * @return El rol del Usuario.
	 */
	public Rol getRol() {
		
		return rol;
	}

	/**
	 * Actualiza el {@link #rol rol} del {@link Usuario Usuario}.
	 * 
	 * @param rol El nuevo rol que va a adquirir el Usuario.
	 */
	public void setRol(Rol rol) {
		
		this.rol = rol;
	}

	/**
	 * Devuelve el {@link #nombre nombre} del {@link Usuario Usuario}.
	 * 
	 * @return El nombre del Usuario.
	 */
	public String getNombre() {
		
		return nombre;
	}

	/**
	 * Actualiza el {@link #nombre nombre} del {@link Usuario Usuario}.
	 * 
	 * @param nombre El nuevo nombre que va a adquirir el Usuario.
	 */
	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}

	/**
	 * Devuelve la {@link #contrasena contraseña} del {@link Usuario Usuario}.
	 * 
	 * @return La contraseña del Usuario.
	 */
	public String getContrasena() {
		
		return contrasena;
	}

	/**
	 * Actualiza la {@link #contrasena contraseña} del {@link Usuario Usuario}.
	 * 
	 * @param contrasena La nueva contraseña que va a adquirir el Usuario.
	 */
	public void setContrasena(String contrasena) { // La contraseña va encriptada
		
		this.contrasena = DigestUtils.sha256Hex(contrasena);
	}

	/**
	 * Devuelve la {@link #expiraDieta fecha de expiración de la dieta} del {@link Usuario Usuario}.
	 * 
	 * @return La fecha de expiración de la dieta del Usuario.
	 */
	public String getExpiraDieta() {
		
		return expiraDieta;
	}

	/**
	 * Actualiza la {@link #expiraDieta fecha de expiración de la dieta} del {@link Usuario Usuario}.
	 * 
	 * @param expiraDieta La nueva fecha de expiración de la dieta que va a adquirir el Usuario.
	 */
	public void setExpiraDieta(String expiraDieta) {
		
		this.expiraDieta = expiraDieta;
	}
}
