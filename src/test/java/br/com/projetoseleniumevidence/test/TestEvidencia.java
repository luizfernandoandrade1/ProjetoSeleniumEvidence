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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.projetoseleniumevidence.evidence.RelatorioEvidencia;
import br.com.projetoseleniumevidence.evidence.SeleniumEvidence;
import br.com.projetoseleniumevidence.evidence.TipoRelatorio;
import br.com.projetoseleniumevidence.relatorio.GeradorRelatorioEvidencia;


public class TestEvidencia {

	WebDriver driver;
	List<SeleniumEvidence> evidenceList;
	RelatorioEvidencia report;
	String errorMessage;
	WebElement element;
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		evidenceList = new ArrayList<SeleniumEvidence>();
	}

	@Test
	public void testGerarEvidencia() throws IOException {
		try {
			driver.get("http://localhost:8080/ProjetoTesteFuncional/index.jsp");
			evidenceList.add(new SeleniumEvidence("Projeto Teste Funcional", takeScreenshot(driver)));
			
			driver.findElement(By.id("avaliarAluno")).click();
			evidenceList.add(new SeleniumEvidence("Clique em Avaliar Aluno", takeScreenshot(driver)));
			
			element = driver.findElement(By.name("vNome"));
			element.sendKeys("Charleston");
			element = driver.findElement(By.name("vNota1"));
			element.sendKeys("7");
			element = driver.findElement(By.name("vNota2"));
			element.sendKeys("10");
			element = driver.findElement(By.name("vFrequencia"));
			element.sendKeys("13");
			
			evidenceList.add(new SeleniumEvidence("Cadastro do Aluno", takeScreenshot(driver)));
			
			driver.findElement(By.name("Enviar")).click();
			evidenceList.add(new SeleniumEvidence("Avaliação do Aluno", takeScreenshot(driver)));
			
		} catch (Exception e) {
			
		} finally {
			RelatorioEvidencia report = new RelatorioEvidencia(evidenceList, "Relatorio", "Charleston", "ProjectTest", null);
			GeradorRelatorioEvidencia.generareEvidenceReport(report, TipoRelatorio.DOC);
		}
	}
	
	private String takeScreenshot(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	}
}
