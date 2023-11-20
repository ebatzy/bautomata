package tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import config.Preferencias;
import models.Login_model;
import libraries.Reports;

public class Login_test {
	private ExtentTest test;

	@Test
	public void LoginTest() {

		test = Reports.createTest("Login");
		Reports.assign(test, "EB", "TEST", Preferencias.getInstance().obtenerAtributo("navegadorNombre"));
		try {
			Login_model loginModel = new Login_model();

			loginModel.procesar(test);

		} catch (Exception e) {
			e.printStackTrace();
			Reports.logFail(test, "Error en el inicio de sesion");
		}

		Reports.cerrarTest();
	}
}
