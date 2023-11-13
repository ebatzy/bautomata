package models;

import java.io.IOException;

import com.aventstack.extentreports.ExtentTest;

import config.Preferencias;
import libraries.ReadExcelFile;
import libraries.Reports;
import libraries.WriteExcelFile;
import pages.Login_page;
import pages.Principal_page;

public class Login_model {
	Login_page loginPage = new Login_page();
	ReadExcelFile readFile = new ReadExcelFile();
	WriteExcelFile writeFile = new WriteExcelFile();

	static Preferencias preferencias = Preferencias.PREFERENCIAS();
	private static String RUTA_EXCEL = preferencias.obtenerAtributo("rutaExcel");
	private static String PAGINA_WEB = preferencias.obtenerAtributo("paginaWeb");

	public Login_model() throws InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		super();
	}

	public void procesar(ExtentTest test) {
		try {
			iniciar(test);
			writeCodigo(test);
			writeUsuario(test);
			writeContrasenia(test);
			clickIngreso(test);
			String log = validarInicio(test);
			loginLog(log);

		} catch (Exception e) {
			e.printStackTrace();
			Reports.logFail(test, "Error en el inicio de sesion");
		}

	}

	public Boolean iniciar(ExtentTest test) throws InterruptedException {
		try {
			loginPage.iniciarNavegador();
			loginPage.navegar(PAGINA_WEB);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public Boolean writeCodigo(ExtentTest test) throws IOException {
		String codigo = readFile.getCellValue(RUTA_EXCEL, "Login", 1, 1);
		try {
			loginPage.ingresarCodigo(codigo);
			Reports.logPass(test, "Se ingreso correctamente el codigo: " + codigo);
			return true;
		} catch (Exception e) {
			Reports.logFail(test, "Fallo el ingreso del codigo: " + codigo);
			return false;
		}
	}

	public Boolean writeUsuario(ExtentTest test) throws IOException {
		String usuario = readFile.getCellValue(RUTA_EXCEL, "Login", 2, 1);
		try {
			loginPage.ingresarUsuario(usuario);
			Reports.logPass(test, "Ingreso correcto del usuario: " + usuario);
			return true;
		} catch (Exception e) {
			Reports.logFail(test, "Fallo el ingreso del usuario: " + usuario);
			return false;
		}
	}

	public Boolean writeContrasenia(ExtentTest test) {
		try {
			loginPage.ingresarContrasenia(readFile.getCellValue(RUTA_EXCEL, "Login", 3, 1));
			Reports.logPass(test, "Ingreso correcto de la Contraseña: " + "Prueba123..");
			return true;
		} catch (Exception e) {
			Reports.logFail(test, "Fallo al ingresar la Contraseña: " + "Prueba123..");
			return false;
		}
	}

	public Boolean clickIngreso(ExtentTest test) {
		try {
			loginPage.btnInicio();
			Reports.logPass(test, "Clic en boton para iniciar sesion correcto");
			return true;
		} catch (Exception e) {
			Reports.logFail(test, "Fallo el clic en boton para iniciar sesion");
			return false;
		}
	}

	public String validarInicio(ExtentTest test) {
		try {
			Principal_page principal = new Principal_page();
			if (principal.existeCerrarSesion()) {
				Reports.logPass(test, "Inicio de sesion correcto");
				return "Inicio de sesion correcto";
			} else if (loginPage.existeErrorCodigo()) {
				Reports.logFail(test, loginPage.obtenerErrorCodigo().toString());
				return loginPage.obtenerErrorCodigo();
			} else if (loginPage.existeErrorUsuario()) {
				Reports.logFail(test, loginPage.obtenerErrorUsuario().toString());
				return loginPage.obtenerErrorUsuario();
			} else if (loginPage.existeErrorContrasenia()) {
				Reports.logFail(test, loginPage.obtenerErrorContrasenia().toString());
				return loginPage.obtenerErrorContrasenia();
			} else if (loginPage.existeMensajeError()) {
				Reports.logFail(test, loginPage.obtenerMensajeError().toString());
				return loginPage.obtenerMensajeError();
			} else {
				Reports.logFail(test, "Error inicio de sesion");
				return "Error inicio de sesion";
			}

		} catch (Exception e) {
			return null;
		}

	}

	public void loginLog(String log) {
		try {
			writeFile.writeCellValue(RUTA_EXCEL, "Login", 4, 1, log);
		} catch (IOException e) {
		}
	}
}