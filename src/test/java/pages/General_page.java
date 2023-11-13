package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.Preferencias;

public class General_page {

	private static WebDriver driver;
	static Preferencias preferencias = Preferencias.PREFERENCIAS();
	private static String NAVEGADOR_TIPO = preferencias.obtenerAtributo("navegadorTipo");

	public General_page() throws InterruptedException {
		super();
		// General_page genPage = General_page.genPage();
	}

	/**
	 * Recibe como parametro un valor entero, los cuales son: 1. Edge 2. Chrome 3.
	 * Firefox
	 * 
	 * @param tipo
	 * @return driver
	 * @throws InterruptedException
	 */
	public static WebDriver iniciarNavegador() throws InterruptedException {

		if (NAVEGADOR_TIPO == "1") {
			ChromeOptions opciones = new ChromeOptions();
			opciones.addArguments("--start-maximized");

			driver = new ChromeDriver(opciones);
		} else if (NAVEGADOR_TIPO == "2") {
			FirefoxOptions opciones = new FirefoxOptions();
			opciones.addArguments("--start-maximized");

			driver = new FirefoxDriver(opciones);
		} else if (NAVEGADOR_TIPO == "3") {
			EdgeOptions opciones = new EdgeOptions();
			opciones.addArguments("--start-maximized");

			driver = new EdgeDriver(opciones);

		} else if (NAVEGADOR_TIPO == "4") {
			SafariOptions opciones = new SafariOptions();
			// opciones.addArguments("--start-maximized");

			driver = new SafariDriver(opciones);
		} else {
			throw new InterruptedException("Tipo de navegador no valido");
		}

		return driver;
	}

	public static WebDriver obtenerDriver() {
		return driver;
	}

	public static void cerrarNavegador() {
		driver.quit();
	}

	/**
	 * URL de la pagina a probar
	 * 
	 * @param url String
	 */
	public void navegar(String url) {
		driver.get(url);
	}

	/**
	 * Obtener varios elementos
	 * 
	 * @param objeto By
	 * @return List
	 */
	public List<WebElement> buscarElementos(By objeto) {
		return driver.findElements(objeto);
	}

	/**
	 * Devuelve el texto de un elemento
	 * 
	 * @param objeto By
	 * @return String
	 */
	public String obtenerTexto(By objeto) {
		return driver.findElement(objeto).getText();
	}

	/**
	 * Devuelve el atributo de un elemento
	 * 
	 * @param objeto   By
	 * @param atributo String
	 * @return String
	 */
	public String obtenerAtributo(By objeto, String atributo) {
		return driver.findElement(objeto).getAttribute(atributo);
	}

	/**
	 * Escribe texto en el elemento
	 * 
	 * @param objeto By
	 * @param texto  String
	 */
	public void escribir(By objeto, String texto) {
		driver.findElement(objeto).sendKeys(texto);
	}

	/**
	 * Da click en el elemento ubicado
	 * 
	 * @param objeto By
	 */
	public void click(By objeto) {
		driver.findElement(objeto).click();
	}

	/**
	 * Valida que el elemento sea visible
	 * 
	 * @param objeto By
	 * @return Boolean
	 */
	public Boolean esVisible(By objeto) {
		try {
			return driver.findElement(objeto).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Realiza un scroll hacia el elemento
	 * 
	 * @param objeto By
	 */
	public void moverseA(By objeto) {
		WebElement elemento = driver.findElement(objeto);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elemento);
	}

	/**
	 * Ejecuta una espera implicita. Recibe como parametro el tiempo en milisegundos
	 * 
	 * @param tiempo Duration.ofMillis(tiempo)
	 */
	public void esperaImplicita(Duration tiempo) {
		driver.manage().timeouts().implicitlyWait(tiempo);
	}

	/**
	 * Ejecuta una espera explicita
	 * 
	 * @param objeto Elemento ubicado
	 * @param time   Duration.ofSeconds(time)
	 * @return WebElement
	 */
	public WebElement esperaExplicita(By objeto, Duration time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(objeto));
		return elemento;
	}

	/**
	 * Selecciona la opcion de una lista desplegable
	 * 
	 * @param objeto By
	 * @param valor  String
	 * @return Boolean
	 */

	public Boolean seleccionarLista(By objeto, String valor) {

		WebElement selectElement = driver.findElement(objeto);

		By optionLocator = By.tagName("option");
		List<WebElement> options = selectElement.findElements(optionLocator);

		for (WebElement option : options) {
			if (option.getText().contains(valor)) {
				option.click();
				// System.out.println(option.getText().toString());
				return true;
			}
		}
		return false;
	}
}
