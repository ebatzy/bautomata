package helpers;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import config.Mensajes;
import config.Preferencias;
import libraries.Reports;

public class Bi_helper {
	static Preferencias preferencias = Preferencias.PREFERENCIAS();
	static Mensajes mensajes = Mensajes.MENSAJES();

	public Bi_helper() {
		// super();

	}

	/**
	 * Devuelve el valor de un atributo dentro de un archivo .json
	 * 
	 * @param objeto   Nombre del objeto al que se desea acceder
	 * @param atributo Nombre del atributo donde se obtendra el valor
	 * @param ruta     Ruta del archivo .json
	 * @return String, Valor del atributo encontrado
	 * @throws InterruptedException Posibles errores al buscar el atributo u objeto
	 */
	public static String obtenerDato(String objeto, String atributo, String ruta) throws InterruptedException {
		File archivo = new File(ruta);

		String retorno = "";

		if (archivo.exists()) {
			try {

				JsonReader jsonReader = Json.createReader(new FileReader(archivo));
				JsonObject jsonObject = jsonReader.readObject();
				jsonReader.close();

				if (jsonObject.containsKey(objeto)) {

					JsonObject formObjeto = jsonObject.getJsonObject(objeto);

					if (formObjeto.containsKey(atributo)) {
						retorno = formObjeto.getString(atributo);
					} else {
						throw new InterruptedException("No existe el atributo " + atributo);
					}
				} else {
					setErrores(2, Map.of("{objeto}", objeto, "{ruta}", ruta));
					throw new InterruptedException(mensajes.getMensaje());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			setErrores(3, Map.of("{ruta}", ruta));
			throw new InterruptedException("El archivo " + ruta + " no existe");
		}

		return retorno;
	}

	/**
	 * Devuelve la fecha actual, el formato depende del parametro tipo
	 * 
	 * @param tipo int
	 * @return String
	 */
	public static String Hoy(int tipo) {
		SimpleDateFormat formato = null;

		switch (tipo) {
			case 1:
				formato = new SimpleDateFormat("dd-MM-yy");
				break;
			case 2:
				formato = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
				break;
			default:
				break;
		}

		Date fecha = new Date();
		return formato.format(fecha);
	}

	public static String screenShot(String cuentaDebito, String cuentaCredito, String tipoTransaccion, WebDriver driver,
			boolean completada) {
		try {
			Robot robot = new Robot();
			String fileName = "";
			String documents = System.getProperty("user.home") + "/Documents/Automatizacion BEL Web/";
			String documentsPath = documents + "Capturas Automatizacion " + Hoy(1) + "/" +
					tipoTransaccion;

			if (tipoTransaccion.equals("Login")) {
				fileName = documentsPath + "/Login "+ Hoy(2) + ".png";
				System.out.println(fileName);
			} else {
				if (completada) {
					documentsPath = documentsPath + "/Completadas/";
				} else {
					documentsPath = documentsPath + "/No_Completadas/";
				}

				fileName = documentsPath + "BEL WEB " + tipoTransaccion + " de CTA " + cuentaDebito + " a "
						+ cuentaCredito + " " + Hoy(2) + ".png";
			}

			File folder = new File(documentsPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			ImageIO.write(screenFullImage, "png", new File(fileName));
			return fileName.substring(documents.length());

		} catch (AWTException |

				IOException ex) {
		}
		return null;
		
	}

	public static String obtenerTipoCuenta(String cadena) {
		String valor = "";
		if (cadena.length() == 10) {
			if (cadena.substring(0, 2).equals("00"))
				valor = "Monetaria $";
			else
				valor = "Monetaria Q";
		} else {
			switch (cadena.substring(0, 1)) {
				case "0":
					valor = "Ahorro Corriente";
					break;
				case "3":
					if (cadena.substring(0, 3).equals("334"))
						valor = "Ahorro Seguro Dolar";
					else if (cadena.substring(0, 2).equals("33"))
						valor = "Ahorro Dolar";
					else if (cadena.substring(0, 2).equals("34"))
						valor = "Ahorro Dolar";
					else if (cadena.substring(0, 2).equals("35"))
						valor = "Ahorro Super Cuenta";
					else if (cadena.substring(0, 2).equals("38"))
						valor = "Ahorro Cuenta Chica";
					else
						valor = "Ahorro";
					break;
				case "4":
					if (cadena.substring(0, 3).equals("411"))
						valor = "Ahorro Seguro Q";
					else if (cadena.substring(0, 2).equals("41"))
						valor = "Ahorro con Sorteo";
					else if (cadena.substring(0, 3).equals("413"))
						valor = "Ahorro SOS";
					else
						valor = "Ahorro";
					break;
				case "5":
					valor = "Ahorro SOS";
					break;
				case "7":
					valor = "Ahorro 5 Estrellas";
					break;
				case "8":
					valor = "Ahorro 5 Estrellas";
					break;
				case "9":
					valor = "Ahorro 5 Estrellas";
					break;
				default:
					valor = "Ahorro";
					break;
			}
			return valor;
		}
		return valor;
	}

	public static void setErrores(Integer codigo, Map<String, String> datos) {
		String temp = "";

		if (mensajes.errores.containsKey(codigo)) {
			if (datos != null && !datos.isEmpty()) {
				temp = mensajes.errores.get(codigo);

				for (Map.Entry<String, String> entry : datos.entrySet()) {
					temp = temp.replace(entry.getKey(), entry.getValue());
				}
			} else {
				temp = codigo.toString() + " " + mensajes.errores.get(codigo);
			}
		} else {
			temp = "-1 Error desconocido";
		}

		mensajes.agregarMensaje(temp);
	}
}
