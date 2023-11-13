package libraries;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import config.Preferencias;

public class Reports {

	static Preferencias preferencias = Preferencias.PREFERENCIAS();
	private static String RUTA_REPORTE = preferencias.obtenerAtributo("rutaReporte");

	public Reports() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

	}

	private static ExtentReports extent;

	static {
		ExtentSparkReporter spark = new ExtentSparkReporter(RUTA_REPORTE);
		extent = new ExtentReports();

		extent.attachReporter(spark);
	}

	public static ExtentReports getExtentInstance() {
		return extent;
	}

	public static ExtentTest createTest(String testName) {
		return extent.createTest(testName);
	}

	public static void logInfo(ExtentTest test, String message) {
		test.log(Status.INFO, message);
	}

	public static void logPass(ExtentTest test, String message) {
		test.log(Status.PASS, message);
	}

	public static void logFail(ExtentTest test, String message) {
		test.log(Status.FAIL, message);
	}

	public static void logWarning(ExtentTest test, String message) {
		test.log(Status.WARNING, message);
	}

	public static void assignAuthor(ExtentTest test, String autor) {
		test.assignAuthor(autor);
	}

	public static void assignCategory(ExtentTest test, String category) {
		test.assignAuthor(category);
	}

	public static void assign(ExtentTest test, String autor, String category, String device) {
		test.assignAuthor(autor).assignCategory(category).assignDevice(device);
	}

	public static void cerrarTest() {
		extent.flush();
	}
}