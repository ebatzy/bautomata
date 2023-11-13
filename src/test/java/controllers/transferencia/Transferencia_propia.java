package controllers.transferencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class Transferencia_propia {
	public static void main(String[] args) {
		TestNG testng = new TestNG();
		XmlSuite suite = new XmlSuite();
		suite.setName("My Suite");
		XmlTest test = new XmlTest(suite);
		test.setName("My Test");
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("tests.Login_test"));
		classes.add(new XmlClass("tests.transferencia.Transferencia_propia_test"));
		test.setXmlClasses(classes);
		testng.setXmlSuites(Arrays.asList(new XmlSuite[] { suite }));
		testng.run();
	}
}