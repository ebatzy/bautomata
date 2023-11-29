package pages;

import config.Preferencias;
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
import org.testng.SkipException;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class General_page {

    private static WebDriver driver;
    private String NAVEGADOR_TIPO = Preferencias.getInstance().obtenerAtributo("navegadorTipo");

    public General_page() {
        super();
    }

    /**
     * Inicia el driver según la variable NAVEGADOR_TIPO
     */
    public void iniciarNavegador() {
        if (Objects.equals(NAVEGADOR_TIPO, "1")) {
            ChromeOptions opciones = new ChromeOptions();
            opciones.addArguments("--start-maximized");

            driver = new ChromeDriver(opciones);
        } else if (Objects.equals(NAVEGADOR_TIPO, "2")) {
            FirefoxOptions opciones = new FirefoxOptions();
            // opciones.addArguments("--start-maximized"); No cuenta con esta opcion

            driver = new FirefoxDriver(opciones);
        } else if (Objects.equals(NAVEGADOR_TIPO, "3")) {
            EdgeOptions opciones = new EdgeOptions();
            opciones.addArguments("--start-maximized");

            driver = new EdgeDriver(opciones);

        } else if (Objects.equals(NAVEGADOR_TIPO, "4")) {

            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                SafariOptions opciones = new SafariOptions();

                // opciones.addArguments("--start-maximized"); No probado aún
                driver = new SafariDriver(opciones);
            } else {
                throw new SkipException("Sistema operativo no válido para el navegador "
                        + Preferencias.getInstance().obtenerAtributo("navegadorNombre"));
            }
        } else {
            throw new SkipException("Tipo de navegador no valido");
        }

    }

    /**
     * Retorna el driver previamente instanciado
     *
     * @return driver WebDriver
     */
    public static WebDriver obtenerDriver() {
        return driver;
    }

    /**
     * Termina el uso del driver
     */
    public static void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * URL de la pagina a probar
     *
     * @param url String
     */
    public void navegar(String url) {
        driver.get(url);

        // El driver para el navegador Firefox no cuenta con una configuracion inicial
        // para maximizar el navegador, se realiza posterior al cargar la paguna
        if (Objects.equals(NAVEGADOR_TIPO, "2")) {
            driver.manage().window().maximize();
        }
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
     * @param tiempo <pre>Duration.ofMillis(tiempo)</pre>
     */
    public void esperaImplicita(Duration tiempo) {
        driver.manage().timeouts().implicitlyWait(tiempo);
    }

    /**
     * Ejecuta una espera explicita
     *
     * @param objeto Elemento ubicado
     * @param time   <pre>Duration.ofSeconds(time)</pre>
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
                return true;
            }
        }
        return false;
    }
}
