package tests.transferencia;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import config.Preferencias;
import libraries.data_provider.Dtransferencia_propia;
import models.transterencia.Transferencia_propia_model;
import libraries.Reports;

import org.testng.annotations.AfterClass;

public class Transferencia_propia_test {

	Transferencia_propia_model transferenciaModel = null;
	Preferencias preferencias = Preferencias.PREFERENCIAS();

	@Test(dataProvider = "Operacion", dataProviderClass = Dtransferencia_propia.class)
	public void TransferenciaPropiaTest(String cuentaDebitar, String cuentaAcreditar, String monto, int linea)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InterruptedException {
		transferenciaModel = new Transferencia_propia_model();

		ExtentTest test = Reports
				.createTest("Transferencia Propia de cuenta " + cuentaDebitar + " a " + cuentaAcreditar);
		Reports.assign(test, "EB", "TEST", preferencias.obtenerAtributo("navegadorNombre"));

		transferenciaModel.transferencias(test, cuentaDebitar, cuentaAcreditar, monto, linea);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		transferenciaModel.returnHome();
		transferenciaModel.cerrarNavegador();
		Reports.cerrarTest();
	}

}
