package gui.conf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import gui.util.Alerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Configuracoes {
	public static boolean sairDepoisDeSalvar = false;

	boolean programaSalvo = false; // define se o programa já foi salvo depois de ser editado

	private String localDoPrograma; // caminho onde o programa está instalado
	private String localArmazenamentoDeCaixas; 
	private String localImagens;
	private String localArmazenamentoTotalDoDia;
	private String localReportarErro;
	
	
	private Integer quantidadeDeCaixasSalvos; // quantidade de caixas que serão mantidos salvos
	private String selecionarImpressora; // impressora padrão

	private Map<String, Integer> opcoesQuantidadeDeCaixas = new LinkedHashMap<>(); // fornece as opções para a
																					// quantidade de caixas a ser
																					// mantido salvo

	public Configuracoes() {
		opcoesQuantidadeDeCaixas.put("Caixa atual e anterior", 2);
		opcoesQuantidadeDeCaixas.put("Últimos três caixas", 3);
		opcoesQuantidadeDeCaixas.put("Todos os caixas da semana", 7);

		try (BufferedReader br = new BufferedReader(new FileReader("conf.txt"))) {
			localDoPrograma = br.readLine();
			localArmazenamentoDeCaixas = adicionarBarraNoEndereco(localDoPrograma + "arquivos salvos");
			localImagens = adicionarBarraNoEndereco(localDoPrograma + "imagens");
			localArmazenamentoTotalDoDia = adicionarBarraNoEndereco(adicionarBarraNoEndereco(localDoPrograma + "programa") + "arquivos");
			localReportarErro = adicionarBarraNoEndereco(localDoPrograma + "reportar erro");
			
			quantidadeDeCaixasSalvos = Integer.parseInt(br.readLine());
			selecionarImpressora = br.readLine();
		} catch (IOException e) {
			Alerts.showAlert("Erro inesperado", "IOException error", e.getMessage(), Alert.AlertType.INFORMATION);
		} catch (NumberFormatException e) {
			if(!sairDepoisDeSalvar) {
				Alerts.showAlert("NumberFormatException", "Erro ao tentar converter número",
						"O arquivo de configurações pode estar corrompido.", Alert.AlertType.ERROR);
			}
		}
	}

	public boolean getProgramaSalvo() {
		return programaSalvo;
	}

	public String getLocalDoPrograma() {
		return localDoPrograma;
	}

	public String getLocalArmazenamentoDeCaixas() {
		return localArmazenamentoDeCaixas;
	}
	
	public String getLocalImagens() {
		return localImagens;
	}
	
	public String getLocalArmazenamentoTotalDoDia() {
		return localArmazenamentoTotalDoDia;
	}
	
	public String getLocalReportarErro() {
		return localReportarErro;
	}
	
	public Integer getQuantidadeDeCaixasSalvos() {
		return quantidadeDeCaixasSalvos;
	}

	public String getSelecionarImpressora() {
		return selecionarImpressora;
	}

	public Map<String, Integer> getOpcoesQuantidadeDeCaixas() {
		return opcoesQuantidadeDeCaixas;
	}

//esse método foi feito para permitir ao usuário escolher uma entre as impressoras disponíveis no sistema
	public List<String> listaDeImpressoras() {
		try {
			List<String> listaImpressoras = new ArrayList<>();
			DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
			PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);
			for (PrintService p : ps) {
				listaImpressoras.add(p.getName());
			}
			return listaImpressoras;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//se o programa estiver configurado este método retornará true, se não estiver, retornará false
	public boolean atualizarEVerificarConfiguracao() {
		// atualiza configuracoes
		try (BufferedReader br = new BufferedReader(new FileReader("conf.txt"))) {
			String auxiliar = br.readLine();
			if(auxiliar != null) {
				localDoPrograma = auxiliar;
				try {
					quantidadeDeCaixasSalvos = Integer.parseInt(br.readLine());
				} catch(NumberFormatException e){
					quantidadeDeCaixasSalvos = 2;
				}	
				selecionarImpressora = br.readLine();
			} else {
				localDoPrograma = null;
			}
		} catch (IOException e) {
			Alerts.showAlert("Erro inesperado", "IOException error", e.getMessage(), Alert.AlertType.INFORMATION);
		}
		// verifica configuracoes
		if (localDoPrograma == null || localDoPrograma.replace(" ", "").length() < 2
				|| localDoPrograma.isEmpty() || quantidadeDeCaixasSalvos == null) {
			Alerts.showAlert("Alerta", "Programa não configurado",
					"Para configurar, acesse a guia 'Configurações'", Alert.AlertType.ERROR);
			return false;
		}
		return true;
	}

	public void salvarConfiguracao(TextField localDoPrograma, ComboBox<String> quantidadeDeCaixasSalvos,
			ComboBox<String> selecionarImpressora) {

		String ldp= localDoPrograma.getText();

		Integer qcs = null;
		if (quantidadeDeCaixasSalvos.getValue() != null || quantidadeDeCaixasSalvos.getValue() != "") {
			qcs = opcoesQuantidadeDeCaixas.get((String) quantidadeDeCaixasSalvos.getValue());
		}

		String si = selecionarImpressora.getValue();
		if(si == null) {
			si = "";
		}
		
		// esse if() irá verificar se os campos estão preenchidos corretamente
		if (ldp == null || ldp.replace(" ", "").length() < 2 || ldp.isEmpty() || qcs == null) {
			Alerts.showAlert("Erro", "Há campos vazios ou preenchidos incorretamente", null, Alert.AlertType.ERROR);

		} else {
			adicionarBarraNoEndereco(ldp);

			this.localDoPrograma = ldp;

			this.quantidadeDeCaixasSalvos = qcs;

			this.selecionarImpressora = si;

			try (BufferedWriter bw = new BufferedWriter(new FileWriter("conf.txt"))) {
				bw.write(ldp);
				bw.newLine();
				bw.write(String.format("%d", qcs));
				bw.newLine();
				bw.write(si);

				programaSalvo = true;

				Alerts.showAlert("Atualização concluída", null, "Atualização concluída com sucesso",
						Alert.AlertType.INFORMATION);
				
			} catch (IOException e) {
				Alerts.showAlert("Erro inesperado", "IOException error", e.getMessage(), Alert.AlertType.INFORMATION);
			}
		}
	}

//esse método colocará uma barra ou contra barra (windows ou unix) no final do local de armazenamento passado pelo usuário se ele não tiver a adicionado
	public String adicionarBarraNoEndereco(String localDoPrograma) {
		if (localDoPrograma.charAt(0) == '/') {
			if (localDoPrograma.charAt(localDoPrograma.length() - 1) != '/') {
				localDoPrograma += "/";
			}
		} else {
			if (localDoPrograma.charAt(localDoPrograma.length() - 1) != '\\') {
				localDoPrograma += "\\";
			}
		}		
		return localDoPrograma;
	}
}
