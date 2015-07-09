package br.com.projetoseleniumevidence.evidence;

import java.util.List;

public class RelatorioEvidencia {
	
	private List<SeleniumEvidence> evidenciaList = null;
	private String nomeRelatorio = null;
	private String nomeTester = null;
	private String nomeProjeto = null;
	private String excecao = null;
	
	public RelatorioEvidencia(List<SeleniumEvidence> evidenciaList, String nomeRelatorio,
			String nomeTester, String nomeProjeto, String excecao) {
		
		this.evidenciaList = evidenciaList;
		this.nomeRelatorio = nomeRelatorio;
		this.nomeTester = nomeTester;
		this.nomeProjeto = nomeProjeto;
		this.excecao = excecao;
	}

	public List<SeleniumEvidence> getEvidenciaList() {
		return evidenciaList;
	}

	public void setEvidenciaList(List<SeleniumEvidence> evidenciaList) {
		this.evidenciaList = evidenciaList;
	}

	public String getNomeRelatorio() {
		return nomeRelatorio;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public String getNomeTester() {
		return nomeTester;
	}

	public void setNomeTester(String nomeTester) {
		this.nomeTester = nomeTester;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public String getExcecao() {
		return excecao;
	}

	public void setExcecao(String excecao) {
		this.excecao = excecao;
	}

}
