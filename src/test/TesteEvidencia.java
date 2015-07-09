package br.com.projetoseleniumevidence.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.projetoseleniumevidence.evidence.RelatorioEvidencia;
import br.com.projetoseleniumevidence.evidence.SeleniumEvidence;
import br.com.projetoseleniumevidence.relatorio.GeradorRelatorioEvidencia;


public class TesteEvidencia {

	WebDriver driver = null;
	List<SeleniumEvidence> evidenceList = null;
	RelatorioEvidencia report = null;
	String errorMessage = null;
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		evidenceList = new ArrayList<SeleniumEvidence>();
	}

	@Test
	public void generatEvidence() throws IOException {
		try {
			driver.get("http://seleniumhq.org");
			evidenceList.add(new SeleniumEvidence("Selenium page", takeScreenshot(driver)));
			
			driver.findElement(By.linkText("Download")).click();
			evidenceList.add(new SeleniumEvidence("Click in Download link", takeScreenshot(driver)));		
			
		} catch (Exception e) {
			
		} finally {
//			RelatorioEvidencia report = new RelatorioEvidencia(evidenceList, "MyReportOK", "Charleston", "ProjectTest", null);
			GeradorRelatorioEvidencia.generatePDFEvidence(evidenceList, "RelatórioEvidencia", "Charleston", "ProjetoTest", null);
		}
	}
	
	private String takeScreenshot(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	}
}
