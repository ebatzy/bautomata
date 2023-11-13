package models.transterencia;

import java.io.IOException;

import com.aventstack.extentreports.ExtentTest;

import config.Preferencias;
import helpers.Bi_helper;
import libraries.ReadExcelFile;
import libraries.Reports;
import libraries.WriteExcelFile;
import pages.Login_page;
import pages.Principal_page;
import pages.transferencia.Transferencia_propia_page;

public class Transferencia_propia_model {

	Login_page loginPage = new Login_page();
	Principal_page principalPage = new Principal_page();
	Transferencia_propia_page transferenciaPropiaPage = new Transferencia_propia_page();
	ReadExcelFile readFile = new ReadExcelFile();
	WriteExcelFile writeFile = new WriteExcelFile();

	static Preferencias preferencias = Preferencias.PREFERENCIAS();
	private static String RUTA_EXCEL = preferencias.obtenerAtributo("rutaExcel");
	private static String RUTA_BITACORA = preferencias.obtenerAtributo("bitacora");
	private static String DATOS_CONFIG = preferencias.obtenerAtributo("rutaJsonConfig");

	public Transferencia_propia_model() throws InterruptedException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		super();
	}

	public void transferencias(ExtentTest test, String cuentaDebitar, String cuentaAcreditar, String monto, int linea) {

		try {
			String errorTransferencia = "", montoCredito = "", tipoCambio = "", tipoCuentaDebitar = "",
					tipoCuentaAcreditar = "";

			boolean monedaCruzada = false;

			tipoCuentaDebitar = Bi_helper.obtenerTipoCuenta(cuentaDebitar);
			tipoCuentaAcreditar = Bi_helper.obtenerTipoCuenta(cuentaAcreditar);

			accederTransferenciasPropias(test);
			CuentaDebitar(cuentaDebitar, test);
			CuentaAcreditar(cuentaAcreditar, test);

			tipoCambio = validarMonedaCruzada(test, monto);

			if (tipoCambio == "N/A")
				montoCredito = monto;
			else
				monedaCruzada = true;

			typeComentario(test);
			clickContinuar(test);

			if (clickConfirmar(test)) {
				terminaTransaccionMonedaCruzada(test, monedaCruzada, cuentaDebitar, cuentaAcreditar, monto,
						tipoCuentaDebitar, tipoCuentaAcreditar, tipoCambio);
			}

			errorTransferencia = terminaTransaccion(test, tipoCuentaDebitar, tipoCuentaAcreditar);

			writeLog(test, linea, errorTransferencia);

		} catch (Exception e) {
			e.printStackTrace();
			Reports.logFail(test, "Error en el inicio de sesion");
		}

	}

	public void accederTransferenciasPropias(ExtentTest test) {
		try {
			principalPage.clickMenuTransferencias();
			transferenciaPropiaPage.clicktransferenciaMonetariasYAhorros();
			Reports.logPass(test, "Acceso a transferencias propias correcto");
		} catch (Exception e) {
			Reports.logFail(test, "Acceso a transferencias propias incorrecto");
		}
	}

	public void CuentaDebitar(String cuenta, ExtentTest test) {
		try {
			transferenciaPropiaPage.clickseleccionarListaDebitar();
			if (transferenciaPropiaPage.seleccionarListaDebitar(cuenta))
				Reports.logPass(test, "Cuenta a debitar No." + cuenta + " seleccionada exitosamente");
			else
				Reports.logFail(test, "Cuenta a debitar No." + cuenta + " no encontrada");
		} catch (Exception e) {
			Reports.logFail(test, "Fallo al seleccionar la cuenta a debitar No." + cuenta);
		}
	}

	public void CuentaAcreditar(String cuenta, ExtentTest test) {
		try {
			transferenciaPropiaPage.clickseleccionarListaAcreditar();
			if (transferenciaPropiaPage.seleccionarListaAcreditar(cuenta))
				Reports.logPass(test, "Cuenta a acreditar No." + cuenta + " seleccionada exitosamente");
			else
				Reports.logFail(test, "Cuenta a acreditar No." + cuenta + " no encontrada");
		} catch (Exception e) {
			Reports.logFail(test, "Fallo al seleccionar la cuenta a acreditar No." + cuenta);
		}
	}

	public String validarMonedaCruzada(ExtentTest test, String monto) {
		try {
			if (transferenciaPropiaPage.esVisibleLblTasaDeCambioVenta()) {
				transferenciaPropiaPage.typeFormMontoDebitoQV(monto);
				Reports.logPass(test, "Transaccion de Q a $");
				return transferenciaPropiaPage.obtenerAtributoTxtTasaVenta();
				// $ a Q
			} else if (transferenciaPropiaPage.esVisibleLblTasaDeCambioCompra()) {
				transferenciaPropiaPage.typeFormMontoDebito$C(monto);
				Reports.logPass(test, "Transaccion de $ a Q");
				return transferenciaPropiaPage.obtenerAtributoTxtTasaCompra();
			} else {
				Reports.logPass(test, "Transaccion de la misma moneda");
				transferenciaPropiaPage.typeFormMontoDebito(monto);
				return "N/A";
			}
		} catch (Exception e) {
			Reports.logFail(test, "Fallo la validacion de moneda cruzada");
		}
		return null;
	}

	// TODO
	public Boolean typeComentario(ExtentTest test) throws IOException, InterruptedException {
		String comentario = Bi_helper.obtenerDato("comentarioTransferencias", "comentario", DATOS_CONFIG);
		try {
			transferenciaPropiaPage.typeFormComentario(comentario);
			Reports.logPass(test, "Ingreso correctamente el comentario: " + comentario);
			return true;
		} catch (Exception e) {
			Reports.logFail(test, "Fallo el ingreso el comentario: " + comentario);
			return false;
		}
	}

	public Boolean clickContinuar(ExtentTest test) {
		try {
			transferenciaPropiaPage.clickBtnContinuar();
			Reports.logPass(test, "Clic en boton continuar correcto");
			return true;
		} catch (Exception e) {
			Reports.logFail(test, "Fallo el clic en boton continuar");
			return false;
		}
	}

	public Boolean clickConfirmar(ExtentTest test) {
		try {
			if (transferenciaPropiaPage.esVisibleBtnConfirmar()) {
				transferenciaPropiaPage.clickBtnConfirmar();
				Reports.logPass(test, "Clic en boton confirmar correcto");
				Thread.sleep(3000);
				return true;
			} else
				Reports.logFail(test, "Fallo el clic en boton confirmar");
		} catch (Exception e) {
			Reports.logFail(test, "Fallo el clic en boton confirmar");
			return false;
		}
		return false;
	}

	public void terminaTransaccionMonedaCruzada(ExtentTest test, Boolean monedaCruzada, String cuentaDebito,
			String CuentaCredito, String monto, String tipoCuentaDebitar, String tipoCuentaAcreditar,
			String tipoCambio) {
		try {
			String montoCredito = "";
			if (transferenciaPropiaPage.esVisibleLblAutorizacion()) {
				if (monedaCruzada) {
					montoCredito = transferenciaPropiaPage.obtenerTextolblMontoAcreditado$();
				} else
					montoCredito = monto;
				writeFile.writeBitacora2(RUTA_BITACORA, ReadExcelFile.search(RUTA_BITACORA) + 2,
						transferenciaPropiaPage.obtenerTextoLblAutorizacion(),
						transferenciaPropiaPage.obtenerTextoLblCuentaDebitada(), tipoCuentaDebitar, cuentaDebito, monto,
						transferenciaPropiaPage.obtenerTextoLblCuentaAcreditadaLocation(), tipoCuentaAcreditar,
						CuentaCredito, montoCredito, tipoCambio);
				Reports.logPass(test, "Transaccion terminada con exito");
			}
		} catch (Exception e) {
			Reports.logFail(test, "Fallo al terminar la transaccion");
		}
	}

	public String terminaTransaccion(ExtentTest test, String tipoCuentaDebitar, String tipoCuentaAcreditar) {
		String errorTransferencia = "";
		try {
			if (transferenciaPropiaPage.esVisibleLblAutorizacion()) {
				errorTransferencia = transferenciaPropiaPage.obtenerTextoLblTransferenciaExitosa();
				Bi_helper.screenShot(tipoCuentaDebitar, tipoCuentaAcreditar, "Transferencias Propias",
						transferenciaPropiaPage.enviarDriver(), true);
				// Reports.logPass(test, "Transaccion terminada con exito");
			} else if (transferenciaPropiaPage.isDisplayLblTransferenciaErronea()) {
				errorTransferencia = transferenciaPropiaPage.obtenerTextoLblTransferenciaErronea();
				Bi_helper.screenShot(tipoCuentaDebitar, tipoCuentaAcreditar, "Transferencias Propias",
						transferenciaPropiaPage.enviarDriver(), false);
				Reports.logFail(test, errorTransferencia);
			} else {
				errorTransferencia = "Error en transferencia";
				Bi_helper.screenShot(tipoCuentaDebitar, tipoCuentaAcreditar, "Transferencias Propias",
						transferenciaPropiaPage.enviarDriver(), false);
				Reports.logFail(test, errorTransferencia);
			}

			return errorTransferencia;

		} catch (Exception e) {
			Reports.logFail(test, "Error al terminar la transaccion");
			return errorTransferencia;
		}
	}

	public void writeLog(ExtentTest test, int linea, String errorTransferencia) {

		try {
			writeFile.writeCellValue(RUTA_EXCEL, "TransferenciasPropias", linea, 3, errorTransferencia);
		} catch (Exception e) {

		}
	}

	public void returnHome() {
		try {
			principalPage.clickHome();
		} catch (Exception e) {

		}
	}

	public void cerrarNavegador() {
		transferenciaPropiaPage.cerrarDriver();
	}
}
