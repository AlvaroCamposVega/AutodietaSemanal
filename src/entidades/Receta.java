package entidades;

/**
 * Esta clase representa a un registro de la entidad Receta
 * en la base de datos. Una receta se compone de una {@link #id id},
 * un {@link #nombre nombre}, unos {@link #ingredientes ingredientes} y
 * una {@link #descripcion descripción}.
 */
public class Receta {

	/**
	 * La Id de la receta.
	 * 
	 * @see #getId()
	 * @see #setId(int)
	 */
	private int id;
	
	/**
	 * El nombre de la receta.
	 * 
	 * @see #getNombre()
	 * @see #setNombre(String)
	 */
	private String nombre;
	
	/**
	 * Los ingredientes de la receta.
	 * 
	 * @see #getIngredientes()
	 * @see #setIngredientes(String)
	 */
	private String ingredientes;
	
	/**
	 * La decripción de la receta.
	 * 
	 * @see #getDescripcion()
	 * @see #setDescripcion(String)
	 */
	private String descripcion;
	
	/**
	 * Crea una instancia de la clase {@link Receta Receta}.
	 * 
	 * @param id La {@link #id id} de la receta.
	 * @param nombre El {@link #nombre nombre} de la receta.
	 * @param ingredientes Los {@link #ingredientes ingredientes} de la receta.
	 * @param descripcion La {@link #descripcion descripción} de la receta.
	 */
	public Receta(int id, String nombre, String ingredientes, String descripcion) {

		this.id = id;
		this.nombre = nombre;
		this.ingredientes = ingredientes;
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve la {@link #id id} de la {@link Receta Receta}.
	 * 
	 * @return La id de la Receta.
	 */
	public int getId() {
		
		return id;
	}

	/**
	 * Actualiza la {@link #id id} de la {@link Receta Receta}.
	 * 
	 * @param id La nueva id que va a adquirir la Receta.
	 */
	public void setId(int id) {
		
		this.id = id;
	}

	/**
	 * Devuelve el {@link #nombre nombre} de la {@link Receta Receta}.
	 * 
	 * @return El nombre de la Receta.
	 */
	public String getNombre() {
		
		return nombre;
	}

	/**
	 * Actualiza el {@link #nombre nombre} de la {@link Receta Receta}.
	 * 
	 * @param nombre El nuevo nombre que va a adquirir la Receta.
	 */
	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}

	/**
	 * Devuelve los {@link #ingredientes ingredientes} de la {@link Receta Receta}.
	 * 
	 * @return Los ingredientes de la Receta.
	 */
	public String getIngredientes() {
		
		return ingredientes;
	}

	/**
	 * Actualiza los {@link #ingredientes ingredientes} de la {@link Receta Receta}.
	 * 
	 * @param ingredientes Los nuevos ingredientes que va a adquirir la Receta.
	 */
	public void setIngredientes(String ingredientes) {
		
		this.ingredientes = ingredientes;
	}

	/**
	 * Devuelve la {@link #descripcion descripción} de la {@link Receta Receta}.
	 * 
	 * @return La descripción de la Receta.
	 */
	public String getDescripcion() {
		
		return descripcion;
	}

	/**
	 * Actualiza la {@link #descripcion descripción} de la {@link Receta Receta}.
	 * 
	 * @param descripción La nueva descripción que va a adquirir la Receta.
	 */
	public void setDescripcion(String descripcion) {
		
		this.descripcion = descripcion;
	}
}
