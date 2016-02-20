package br.com.projetoseleniumevidence.relatorio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import br.com.projetoseleniumevidence.evidence.RelatorioEvidencia;
import br.com.projetoseleniumevidence.evidence.SeleniumEvidence;
import br.com.projetoseleniumevidence.evidence.TipoRelatorio;
import br.com.projetoseleniumevidence.utils.SeleniumEvidenceUtils;

public class GeradorRelatorioEvidencia {

	public static void geradorEvidencia(RelatorioEvidencia relatorioEvidencia, TipoRelatorio tiposRelatorio) throws IOException {
		List<SeleniumEvidence> data = relatorioEvidencia.getEvidenciaList();

		Properties properties = SeleniumEvidenceUtils.carregarPropriedades();
		String diretorioEvidencia = System.getProperty("user.dir")
				+ System.getProperty("file.separator")
				+ properties.getProperty("evidence.dir")
				+ System.getProperty("file.separator");

		criarDiretorioEvidencia(diretorioEvidencia);

		try {

			String companyImage = properties.getProperty("image.company.path");
			String customerImage = properties
					.getProperty("image.customer.path");

			BufferedImage imageCompany;
			BufferedImage imageClient;

			if (companyImage == null || companyImage.equals("null")) {
				imageCompany = null;
			} else {
				imageCompany = ImageIO.read(new File(companyImage));
			}

			if (customerImage == null || customerImage.equals("null")) {
				imageClient = null;
			} else {
				imageClient = ImageIO.read(new File(customerImage));
			}

			String reportName = relatorioEvidencia.getNomeRelatorio();
			String tester = relatorioEvidencia.getNomeTester();
			String project = relatorioEvidencia.getNomeProjeto();

			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("SEL_COMPANY_LOGO", imageCompany);
			parameters.put("SEL_CUSTOMER_LOGO", imageClient);
			parameters.put("SEL_PROJECT", project);
			parameters.put("SEL_TESTER", tester);

			parameters.put("SEL_LABEL_EVINDENCE_TITLE",
					properties.getProperty("label.evidenceReport"));
			parameters.put("SEL_LABEL_PROJECT",
					properties.getProperty("label.projetc"));
			parameters.put("SEL_LABEL_TESTER",
					properties.getProperty("label.tester"));
			parameters.put("SEL_LABEL_STATUS",
					properties.getProperty("label.status"));
			parameters.put("SEL_LABEL_PASS",
					properties.getProperty("label.status.pass"));
			parameters.put("SEL_LABEL_FAILED",
					properties.getProperty("label.status.failed"));
			parameters.put("SEL_LABEL_DATE",
					properties.getProperty("label.date"));
			parameters.put("SEL_LABEL_FOOTER",
					properties.getProperty("label.footer"));
			parameters.put("SEL_LABEL_PAGE",
					properties.getProperty("label.page"));
			parameters.put("SEL_LABEL_COMPANY_NAME",
					properties.getProperty("label.company.name"));

			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(data, false);

			JasperPrint jasperPrint = JasperFillManager.fillReport(properties.getProperty("evidence.file"), parameters, datasource);

			switch (tiposRelatorio) {
			case PDF:
				JasperExportManager.exportReportToPdfFile(jasperPrint,
						diretorioEvidencia + reportName + ".pdf");
				break;

			case DOC:
				JRDocxExporter exporter = new JRDocxExporter();

				File arquivo = new File(diretorioEvidencia + reportName + ".doc");
				FileOutputStream os = new FileOutputStream(arquivo);

				exporter.setParameter(JRDocxExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(
						JRDocxExporterParameter.CHARACTER_ENCODING, "UTF-8");
				exporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, os);

				exporter.exportReport();

				os.close();
				break;

			case HTML:
				JasperExportManager.exportReportToHtmlFile(jasperPrint,
						diretorioEvidencia + reportName + ".html");
				break;

			default:
				break;
			}

		} catch (SecurityException ex) {
			ex.printStackTrace();
		} catch (JRException jre) {
			jre.printStackTrace();
		}
	}

	private static boolean criarDiretorioEvidencia(String diretorioEvidencia) {
		boolean diretorioExiste = false;

		try {
			File dir = new File(diretorioEvidencia);
			boolean exists = dir.exists();

			if (!exists) {
				dir.mkdir();
				diretorioExiste = false;
			} else {
				diretorioExiste = true;
			}
		} catch (SecurityException se) {
			se.printStackTrace();
		}
		return diretorioExiste;
	}

}
