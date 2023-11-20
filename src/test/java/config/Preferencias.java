package config;

import java.util.HashMap;
import java.util.Map;

public class Preferencias {

	private static Preferencias instancia;
	private Map<String, String> atributos;

	private Preferencias() {
		String home = System.getProperty("user.home");
		atributos = new HashMap<>();

		atributos.put("home", home);
		atributos.put("rutaExcel", home + "\\Documents\\Automatizacion BEL Web\\Datos.xlsx");
		atributos.put("paginaWeb", "https://www.bienlinea.bi.com.gt");
		atributos.put("bitacora", home + "\\Documents\\Automatizacion BEL Web\\Operacional.xlsx");
		atributos.put("rutaJson", home + "\\Documents\\Automatizacion BEL Web\\Resourses\\ElementsPage.json");
		atributos.put("rutaJsonAmbiente", home + "\\Documents\\Automatizacion BEL Web\\Resourses\\ambientes.json");
		atributos.put("rutaJsonConfig", home + "\\Documents\\Automatizacion BEL Web\\Resourses\\Config.json");
		atributos.put("rutaReporte", home + "\\Documents\\Automatizacion BEL Web\\Reporte.html");
		atributos.put("nivelTest", "1");
		atributos.put("navegadorTipo", "");
		atributos.put("navegadorNombre", "");
	}

	public static synchronized Preferencias getInstance() {
		if (instancia == null) {
			instancia = new Preferencias();
		}

		return instancia;
	}

	/**
	 * 
	 * @param nombre Nombre del atributo
	 * @return Valor del atriburo
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public synchronized String obtenerAtributo(String nombre) {
		if (atributos.containsKey(nombre)) {
			System.out.println("Obteniendo atributo " + nombre + ", valor: "+atributos.get(nombre));
			return atributos.get(nombre);
		} else {
			throw new IllegalArgumentException("Nombre de atributo no vlido: " + nombre);
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
	public synchronized void valorAtributo(String nombre, String valor) {
		System.out.println("Asignando valor " + valor + " al atributo " + nombre);
		atributos.put(nombre, valor);
	}
}
