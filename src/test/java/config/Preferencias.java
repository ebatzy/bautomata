package config;

import java.util.HashMap;
import java.util.Map;

public class Preferencias {

	/*
	 * private String home = System.getProperty("user.home"); private String
	 * rutaExcel = home + "\\Documents\\Automatizacion BEL Web\\Datos.xlsx"; //
	 * private static String paginaWeb = ""; private String paginaWeb =
	 * "https://www.bienlinea.bi.com.gt"; private String bitacora = home +
	 * "\\Documents\\Automatizacion BEL Web\\Operacional.xlsx"; private String
	 * rutaJson = home +
	 * "\\Documents\\Automatizacion BEL Web\\Resourses\\ElementsPage.json"; private
	 * String rutaJsonConfig = home +
	 * "\\Documents\\Automatizacion BEL Web\\Resourses\\Config.json"; private String
	 * rutaReporte = home + "\\Documentos\\Automatizacion BEL Web\\Reporte.html";
	 */

	private Map<String, Object> atributos = new HashMap<>();

	private static Preferencias PREFERENCIAS;

	private Preferencias() {
		String home = System.getProperty("user.home");
		atributos.put("home", home);
		atributos.put("rutaExcel", home + "\\Documents\\Automatizacion BEL Web\\Datos.xlsx");
		atributos.put("paginaWeb", "https://www.bienlinea.bi.com.gt");
		atributos.put("bitacora", home + "\\Documents\\Automatizacion BEL Web\\Operacional.xlsx");
		atributos.put("rutaJson", home + "\\Documents\\Automatizacion BEL Web\\Resourses\\ElementsPage.json");
		atributos.put("rutaJsonAmbiente", home + "\\Documents\\Automatizacion BEL Web\\Resourses\\ambientes.json");
		atributos.put("rutaJsonConfig", home + "\\Documents\\Automatizacion BEL Web\\Resourses\\Config.json");
		atributos.put("rutaReporte", home + "\\Documents\\Automatizacion BEL Web\\Reporte.html");
		atributos.put("nivelTest", 1);
		atributos.put("navegadorTipo", "");
		atributos.put("navegadorNombre", "");

	}

	public static Preferencias PREFERENCIAS() {
		if (PREFERENCIAS == null) {
			PREFERENCIAS = new Preferencias();
		}

		return PREFERENCIAS;
	}

	/*
	 * 
	 * 
	 * public static Preferencias PREFERENCIAS() { if (PREFERENCIAS == null) {
	 * PREFERENCIAS = new Preferencias(); }
	 * 
	 * return PREFERENCIAS; }
	 */

	/**
	 * 
	 * @param nombre Nombre del atributo
	 * @return Valor del atriburo
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public String obtenerAtributo(String nombre) {
		/*
		 * Class<?> clase = this.getClass(); Field campo =
		 * clase.getDeclaredField(nombre);
		 * 
		 * campo.setAccessible(true);
		 * 
		 * return campo.get(this);
		 */
		// return atributos.get(nombre);
		if (atributos.containsKey(nombre)) {
			return String.valueOf(atributos.get(nombre));
		} else {
			throw new IllegalArgumentException("Nombre de atributo no válido: " + nombre);
		}
	}

	/**
	 * 
	 * @param nombre Nombre del atriburo
	 * @param valor  Nuevo valor que se le asigna al atributo
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void valorAtributo(String nombre, Object valor) {
		/*
		 * Class<?> clase = this.getClass(); Field campo =
		 * clase.getDeclaredField(nombre);
		 * 
		 * campo.setAccessible(true);
		 * 
		 * campo.set(campo, valor);
		 */
		atributos.put(nombre, valor);
	}
}
