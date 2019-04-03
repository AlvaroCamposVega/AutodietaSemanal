package servicios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import biblio.BiblioMatematicas;
import biblio.BiblioUtilidades;
import daos.DietaDAO;
import daosImplementaciones.DietaDAOImpl;
import entidades.Dieta;
import entidades.Receta;
import entidades.Usuario;

/**
 * Esta clase es el servicio que usará el cliente
 * para obtener datos de una dieta de la base de datos.
 */
public class DietaServicio implements DietaDAO {
	
	private DietaDAOImpl dietaDAO = new DietaDAOImpl();

	/**
	 * Genera una instancia del {@link DietaServicio servicio}.
	 */
	public DietaServicio() {

	}

	@Override
	public List<Dieta> buscaPorIdUsuario(int id) throws SQLException {

		return dietaDAO.buscaPorIdUsuario(id);
	}

	@Override
	public boolean salva(List<Dieta> dietas) throws SQLException {

		return dietaDAO.salva(dietas);
	}

	@Override
	public boolean elimina(int id) throws SQLException {

		return dietaDAO.elimina(id);
	}

	@Override
	public boolean actualiza(int id) throws SQLException, ClassNotFoundException {

		return dietaDAO.actualiza(id);
	}

	/**
	 * Devuelve la dieta del usuario en forma de tabla (Array bidimensional).
	 * 
	 * @param id La id del {@link entidades.Usuario usuario} al cual pertenece la dieta.
	 * @return Un array bidimensional que representa una tabla con la
	 * {@link entidades.Dieta dieta} del usuario.
	 * @throws SQLException
	 */
	public Dieta[][] generaTablaDietaUsuario(int id) throws SQLException {
		
		DietaServicio dietaServicio = new DietaServicio();
		
		List<Dieta> dietas = dietaServicio.buscaPorIdUsuario(id);
        
        // Creamos array bidimensional para almacenar las recetas y crear así la tabla
        Dieta[][] tabla = new Dieta[4][7];
        
        for (Dieta dieta : dietas) { // Recorremos la dieta del usuario
        	
        	if (dieta.getHora().getHora().equals("desayuno")) { // Añadimos los datos a la tabla en su respectivo orden
        		
        		tabla[0][DietaServicio.devuelveNumDia(dieta.getDia())] = dieta;
        		
        	} else if (dieta.getHora().getHora().equals("comida")) {
        		
        		tabla[1][DietaServicio.devuelveNumDia(dieta.getDia())] = dieta;
        		
        	} else if (dieta.getHora().getHora().equals("merienda")) {
        		
        		tabla[2][DietaServicio.devuelveNumDia(dieta.getDia())] = dieta;
        		
        	} else if (dieta.getHora().getHora().equals("cena")) {
        		
        		tabla[3][DietaServicio.devuelveNumDia(dieta.getDia())] = dieta;
        	}
		}
        
        return tabla;
    }
    
    /**
     * Genera una dieta aleatoria para el usuario con la id especificada.
     * 
     * @param id la id del {@link entidades.Usuario usuario}.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Dieta> generaDieta(int id) throws SQLException, ClassNotFoundException {
        
    	// SERVICIOS
    	HoraServicio horaServicio = new HoraServicio();
    	UsuarioServicio usuarioServicio = new UsuarioServicio();

    	// VARIABLES
        Usuario usuario = usuarioServicio.buscaPorId(id);
        Dieta[][] dietas = new Dieta[4][7];
        String[] dias = new String[] {
    			
        	"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
        };
        
        for (int hora = 0; hora < 4; hora++) { // Configuración del array bidimensional y sus dietas
            
            for (int dia = 0; dia < 7; dia++) {
                
            	// Todas las dietas pertenecen al mismo usuario y se les configura la hora y el día
                dietas[hora][dia] = new Dieta(usuario, null, horaServicio.buscaPorId(hora + 1), dias[dia]);
            }
        }
        
        DietaServicio.seleccionaRecetas(dietas); // Configuración de las recetas para cada una de las dietas

        return BiblioUtilidades.toList(dietas);
    }
	
	/**
	 * Devuelve el número del día dado su nombre.
	 * @param dia El día del cual queremos su valor numérico.
	 * @return Un número que representa el día pasado por parámetros.
	 */
    private static int devuelveNumDia(String dia) {
        
        switch (dia) {
        
            case "Lunes":
                return 0;
            case "Martes":
                return 1;        
            case "Miércoles":
                return 2;
            case "Jueves":
                return 3;        
            case "Viernes":
                return 4;
            case "Sábado":
                return 5;
            default:
                return 6;
        }
    }
    
    /**
     * Selecciona una receta para las dietas cumpliendo las siguientes condiciones:
     * <ul>
     * <li>Las comidas libres solo podrán estar presente los sábados y domingos y
     * tendrán más posibilidades de aparecer en la comida y en la cena.
     * <li>No puede repetirse la misma receta dos días seguidos.
     * </ul>
     * @param dietas El array bidimensional de {@link entidades.Dieta dietas} que se
     * va a iterar para configurar sus recetas.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private static void seleccionaRecetas(Dieta[][] dietas) throws SQLException {
        
        for (int fila = 0; fila < 4; fila++) { // Iteración del array de dietas

        	//SERVICIOS
        	RecetaServicio recetaServicio = new RecetaServicio();
        	
        	//VARIABLES
        	List<Receta> recetasDisponibles = recetaServicio.buscaRecetasPorIdHora(fila + 1);
            
            for (int celda = 0; celda < 7; celda++) { // Para cada dieta aplicamos las condiciones
                
                Dieta dieta = dietas[fila][celda];

                // Si es lunes debemos comprobar que no se repita la receta del domingo
                // pasado y además tenemos que eliminar la comida libre
                if (dieta.getDia().equals("Lunes")) {
                    
                    // Si estamos de la comida en adelante, hacemos que no se repita la receta
                	// del día anterior (domingo)
                    if (fila > 0) {
                        
                    	DietaServicio.seleccionaReceta(
                                dieta,
                                DietaServicio.eliminaRecetaDiaAnterior(dietas[fila - 1][6], recetasDisponibles)
                        );
                        
                    } else {
                        
                    	DietaServicio.seleccionaReceta(dieta, recetasDisponibles);
                    }
                
                // Si es cualquier otro día se deberá comprobar que no se repita con la del día anterior
                } else {
                    
                	DietaServicio.seleccionaReceta(
                            dieta,
                            DietaServicio.eliminaRecetaDiaAnterior(dietas[fila][celda - 1], recetasDisponibles)
                    );
                }
            }
        }
    }
    
    /**
     * Selecciona la receta para la dieta especificada entre las recetas disponibles.
     * 
     * @param dieta La dieta para la que se va a seleccionar la receta.
     * @param recetasDisponibles La lista que almacena las recetas disponibles.
     * @throws SQLException
     */
    private static void seleccionaReceta(Dieta dieta, List<Receta> recetasDisponibles)
            throws SQLException {

    	// SERVICIOS
    	RecetaServicio recetaServicio = new RecetaServicio();
        
    	// VARIABLES
        Receta recetaComidaLibre = recetaServicio.buscaRecetaComidaLibre();
        List<Receta> copiaRecetasDisponibles = new ArrayList<Receta>(recetasDisponibles);
    	
        // Si es sábado o domingo podrá tocar comida libre y tendrá más posibilidades de aparecer
        // en la comida y en la cena. Falta implementar que solo puede aparecer 3 máximo
    	if (dieta.getDia().equals("Sábado") || dieta.getDia().equals("Domingo")) {
    		
    		DietaServicio.seleccionaRecetaSabadoDomingo(dieta, copiaRecetasDisponibles, recetaServicio, recetaComidaLibre);
    		
    	} else {
    		
            copiaRecetasDisponibles.remove(recetaComidaLibre); // La eliminamos de las recetas disponibles

            // Cogemos una receta aleatoria
            dieta.setReceta(DietaServicio.recetaAleatoria(copiaRecetasDisponibles));
    	}
    }
    
    /**
     * Selecciona la receta para la dieta especificada si esta tiene como día
     * un sábado o un domingo.
     * 
     * @param dieta La dieta para la que se va a seleccionar la receta.
     * @param recetasDisponibles La lista que almacena las recetas disponibles.
     * @param recetaServicio El servicio de la receta.
     * @param recetaComidaLibre La receta de la comida libre.
     * @throws SQLException
     */
    private static void seleccionaRecetaSabadoDomingo(
    		Dieta dieta, List<Receta> recetasDisponibles,
    		RecetaServicio recetaServicio, Receta recetaComidaLibre
    ) {
        
        // FALTA IMPLEMENTAR EL LÃ�MITE DE 3 COMIDAS LIBRES
        
        // Si es la hora de la comida o cena es más probable que toque comida libre
        if (dieta.getHora().getHora().equals("comida") || dieta.getHora().getHora().equals("cena")) {
        	
        	recetasDisponibles.add(recetaComidaLibre);

            dieta.setReceta(DietaServicio.recetaAleatoria(recetasDisponibles));
            
        } else {
        	
        	dieta.setReceta(DietaServicio.recetaAleatoria(recetasDisponibles));
        }
    }
    
    /**
     * Devuelve una lista con la receta del día anterior eliminada.
     * @param dieta La dieta del día anterior.
     * @param recetasDisponibles La lista de recetas disponibles de la cual eliminaremos la receta.
     * @return Una lista con la receta del día anterior eliminada.
     */
    private static List<Receta> eliminaRecetaDiaAnterior(Dieta dieta, List<Receta> recetasDisponibles) {

        Receta receta = dieta.getReceta();
        List<Receta> copiaRecetasDisponibles = new ArrayList<Receta>(recetasDisponibles);
        
        copiaRecetasDisponibles.remove(receta);
        
        return copiaRecetasDisponibles;
    }
    
    /**
     * Devuelve una receta aleatoria de la lista especificada.
     * 
     * @param recetasDisponibles La lista de la que se va a obtener la receta aleatoria.
     * @return Una receta aleatoria de la lista.
     */
    private static Receta recetaAleatoria(List<Receta> recetasDisponibles) {

        return recetasDisponibles.get(BiblioMatematicas.aleatorio(0, recetasDisponibles.size() - 1));
    }
}
