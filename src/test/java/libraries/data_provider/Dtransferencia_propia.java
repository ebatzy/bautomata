package libraries.data_provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import config.Preferencias;
import libraries.ReadExcelFile;

public class Dtransferencia_propia extends ReadExcelFile {

	static Preferencias preferencias = Preferencias.PREFERENCIAS();
	private static String RUTA_EXCEL = preferencias.obtenerAtributo("rutaExcel");

	public static class Operacion {
		public String cuentaDebito;
		public String cuentaCredito;
		public String monto;
		public int linea;

		public Operacion(String cuentaDebito, String cuentaCredito, String monto, int linea) {
			this.cuentaDebito = cuentaDebito;
			this.cuentaCredito = cuentaCredito;
			this.monto = monto;
			this.linea = linea;
		}
	}

	@DataProvider(name = "Operacion")

	public Object[][] getDatos() throws IOException {
		List<Operacion> transacciones = new ArrayList<>();

		for (int i = 1; i <= countRows(RUTA_EXCEL, "TransferenciasPropias"); i++) {

			transacciones.add(new Operacion(getCellValue(RUTA_EXCEL, "TransferenciasPropias", i, 0),
					getCellValue(RUTA_EXCEL, "TransferenciasPropias", i, 1),
					getCellValue(RUTA_EXCEL, "TransferenciasPropias", i, 2), i));
		}
		return transacciones.stream().map(transaccion -> new Object[] { transaccion.cuentaDebito,
				transaccion.cuentaCredito, transaccion.monto, transaccion.linea }).toArray(Object[][]::new);
	}
}
