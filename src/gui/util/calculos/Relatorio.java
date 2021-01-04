package gui.util.calculos;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import gui.conf.Configuracoes;
import gui.util.Alerts;
import gui.util.ConferirPrograma;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Relatorio {
	
	Configuracoes conf = new Configuracoes();
	Calendar cal = Calendar.getInstance();
	DateTimeFormatter dtm = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public static String quantidadeEmCaixa;
	public static double totalCartao;
	public static double totalCredito;
	public static double totalDebito;
	public static double totalDinheiro;
	public static double totalDoDiaSemOnline;
	public static double totalDoDiaIfood;
	public static double onlineIfood;
	public static double dinheiroIfood;
	public static double cartaoIfood;
	
	public static void toStringQuantidadeEmCaixa(List<TextField> listQuantidadeDeNotas, Label totalEmCaixa, TextField descontarCreditoAmanha,
			TextField descontarDebitoAmanha) {
		String string = "";
		
		string += String.format("Descontar no próximo caixa:\nCrédito = %.2f\n", Double.parseDouble(descontarCreditoAmanha.getText()));
		string += String.format("Débito = %.2f\n", Double.parseDouble(descontarDebitoAmanha.getText()));

		string += "------------------------------\n";
		string += "Total em caixa:\n";
		
		String[] tiposDeNotas = { "   100 = ", "\n     50 = ", "\n     20 = ", "\n     10 = ", "\n       5 = ",
				"\n       2 = ", "\n       1 = ", "\n  0.50 = ", "\n  0.25 = ", "\n  0.10 = ", "\n  0.05 = " };

		for (int x = 0; x < listQuantidadeDeNotas.size(); x++) {
			Integer quantidadeDeNota = Integer.parseInt(listQuantidadeDeNotas.get(x).getText());
			if (quantidadeDeNota != 0) {
				string += String.format("%s%d", tiposDeNotas[x], quantidadeDeNota);
			}
		}
		string += ("\nTotal = " + totalEmCaixa.getText());

		quantidadeEmCaixa = string;
		
		ConferirPrograma.mudarJaSalvo(false);
	}

	// Métodos para gerar a Pré-visualização na tela

	public static void valoresLoja(Label totalEmDinheiro, Label totalEmCartao, Label credito, Label debito,
			Label totalDoDia) {
		totalDinheiro = Double.parseDouble(totalEmDinheiro.getText());
		totalCartao = Double.parseDouble(totalEmCartao.getText());
		totalCredito = Double.parseDouble(credito.getText());
		totalDebito = Double.parseDouble(debito.getText());
		totalDoDiaSemOnline = Double.parseDouble(totalDoDia.getText());
	
		ConferirPrograma.mudarJaSalvo(false);
	}

	public static void valoresIfood(TextField online, TextField dinheiro, TextField cartao, Label totalDoDia) {
		onlineIfood = Double.parseDouble(online.getText());
		dinheiroIfood = Double.parseDouble(dinheiro.getText());
		cartaoIfood = Double.parseDouble(cartao.getText());
		totalDoDiaIfood = Double.parseDouble(totalDoDia.getText());
	
		ConferirPrograma.mudarJaSalvo(false);
	}

	public String stringTotalDoDia(double totalCartao, double totalCredito, double totalDebito, double totalDinheiro, double totalDoDiaSemOnline,
			double totalDoDiaIfood, double onlineIfood, double dinheiroIfood, double cartaoIfood) {		
		String str = String.format("LOJA: R$ %.2f\n", totalDoDiaSemOnline - cartaoIfood - dinheiroIfood);
		str += String.format("  * Dinheiro: R$ %.2f\n", totalDinheiro - dinheiroIfood);
		str += String.format("  * Cartão: R$ %.2f\n", totalCartao - cartaoIfood);

		str += String.format("\nIFOOD: R$ %.2f\n", totalDoDiaIfood);
		str += String.format("  * Dinheiro: R$ %.2f\n", dinheiroIfood);
		str += String.format("  * Cartão: R$ %.2f\n", cartaoIfood);
		str += String.format("  * Online: R$ %.2f\n", onlineIfood);

		str += String.format("\n------------------------");
		str += String.format("\nTOTAL: R$ %.2f\n", totalDoDiaSemOnline + onlineIfood);
		str += String.format("  * Dinheiro: R$ %.2f\n", totalDinheiro);
		str += String.format("  * Cartão: R$ %.2f\n", totalCartao);
		str += String.format("      -> Crédito: R$ %.2f\n", totalCredito);
		str += String.format("      -> Débito: R$ %.2f\n", totalDebito);
		str += String.format("  * Online Ifood: R$ %.2f\n", onlineIfood);

		return str;
	}
	
	public void imprimir(TextArea quantidadeEmCaixa, TextArea totalDoDia, RadioButton imprimirTotalDoDia) {
		if (conf.atualizarEVerificarConfiguracao()) {
			String impressao = imprimirTotalDoDia.isSelected() ? quantidadeEmCaixa.getText() + "\n\n\n\n\n" + ("" + (char) 27 + (char) 109)
					+ totalDoDia.getText() + "\n\n\n\n\n" + ("" + (char) 27 + (char) 109) 
					: quantidadeEmCaixa.getText() + "\n\n\n\n\n" + ("" + (char) 27 + (char) 109);
			
			PrintService impressora = null;

			try {
				DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
				PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);

				for (PrintService p : ps) {
					if (p.getName() != null && p.getName().contains(conf.getSelecionarImpressora())) {
						impressora = p;
					}
				}
			} catch (Exception e) {
				Alerts.showAlert("Erro ao imprimir", "PrintException", e.getMessage(), Alert.AlertType.ERROR);
			}
//------------------------------
			if (impressora == null || impressora.getName().isEmpty()) {
				Alerts.showAlert("Erro ao imprimir", "Impressora não encontrada", "Selecione uma impressora em 'Configurações'\nantes de tentar imprimir",
						Alert.AlertType.WARNING);
			} else if(!quantidadeEmCaixa.getText().isEmpty()) {
				try {
					DocPrintJob dpj = impressora.createPrintJob();
					InputStream stream = new ByteArrayInputStream(
							(impressao).getBytes());
					DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
					Doc doc = new SimpleDoc(stream, flavor, null);
					dpj.print(doc, null);

					System.out.println(impressora.getName());

				} catch (PrintException e) {
					Alerts.showAlert("Erro ao imprimir", "PrintException", e.getMessage(), Alert.AlertType.ERROR);
				}
			}
		}
	}

	public void salvar(TextArea quantidadeEmCaixa, TextArea totalDoDia, DatePicker datePicker) {
		boolean continuar = true;
		
		if (conf.atualizarEVerificarConfiguracao()) {
			if (quantidadeEmCaixa.getText().isEmpty() || totalDoDia.getText().isEmpty()) {
				Alerts.showAlert("Erro ao tentar salvar", "Há um ou mais campos vazios",
						"Você deve preencher o campo 'Quantidade em\n caixa' e 'Total do dia' antes de salvar",
						Alert.AlertType.ERROR);
			} else {
				if(estaSobrescrevendo(datePicker)) {
					Optional<ButtonType> resultado = Alerts.showAlert("Confirme", "Já existe um arquivo com está data.", "Deseja sobrescrevê-lo?", AlertType.CONFIRMATION);
					if(resultado.get() != ButtonType.OK) {
						continuar = false;
					}
				}
				
				if(continuar) {
					//este try() irá criar os txt's dos caixas na pasta
					try (BufferedWriter bw = new BufferedWriter(
							new FileWriter(conf.getLocalArmazenamentoDeCaixas() + datePicker.getValue().format(dtm) + ".txt"))) {
						bw.write(quantidadeEmCaixa.getText());
						Alerts.showAlert("Concluído", null, "A operação foi realizada com sucesso",
								Alert.AlertType.INFORMATION);
						
						ConferirPrograma.mudarJaSalvo(true);
					} catch (IOException e) {
						Alerts.showAlert("Falha ao tentar salvar arquivo", "O programa não está configurado corretamente",
								e.getMessage(), Alert.AlertType.ERROR);
					}
					// este segundo try{} salva o total do dia na pasta principal do programa,
					// separado da quantidade do dia
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(conf.getLocalArmazenamentoTotalDoDia() + datePicker.getValue().format(dtm)))) {
						bw.write(totalDoDia.getText());
					} catch (IOException e) {
						e.getMessage();
					}
					// esse comando manterá salvo apenas a quantidade de caixas configuradas para
					// serem salvas.
					if (conf.getQuantidadeDeCaixasSalvos() == null) {
						Alerts.showAlert("Programa não configurado", "O programa não está configurado corretamente", null,
								Alert.AlertType.ERROR);
					} else {
						apagarArqAntigos(conf.getLocalArmazenamentoDeCaixas());
						apagarArqAntigos(conf.getLocalArmazenamentoTotalDoDia());
					}
				}
			}
		}
	}
	
	private boolean estaSobrescrevendo(DatePicker datePicker) {
		boolean resposta = false;
		
		File[] arquivos = new File(conf.getLocalArmazenamentoDeCaixas()).listFiles();
		
		for(File x : arquivos) {
			if(x.getName().equals(datePicker.getValue().format(dtm)+".txt")) {
				resposta = true;
			}
		}
		
		return resposta;
	}
	
	private void apagarArqAntigos(String local) {		
		List<File> arquivos = Arrays.asList(new File(local).listFiles());
		
		ordenarArquivosPorNome(arquivos);
		
		//essa parte do código exclui os dias 30 e 31 quando não satisfazem mais a quantidade de caixas que devem ser mantidos salvos
		arquivos.forEach(x -> {
			if (conf.getQuantidadeDeCaixasSalvos() == 2 && arquivos.indexOf(x) > 1) {
				x.delete();
			} else if (conf.getQuantidadeDeCaixasSalvos() == 3 && arquivos.indexOf(x) > 2) {
				x.delete();
			} else if (conf.getQuantidadeDeCaixasSalvos() == 7 && arquivos.indexOf(x) > 6) {
				x.delete();
			}
		});
	}
	
	private void ordenarArquivosPorNome(List<File> arquivos) {
		arquivos.sort((a1, a2) -> {
			String[] tupla = a1.getName().replaceAll(".txt", "").split("-");
			Integer a1Inteiro = Integer.parseInt(tupla[2] + tupla[1] + tupla[0]);
			tupla = a2.getName().replaceAll(".txt", "").split("-");
			Integer a2Inteiro = Integer.parseInt(tupla[2] + tupla[1] + tupla[0]);
			return a1Inteiro.compareTo(a2Inteiro);
		});
	}
	
	public static void limparTudo() {
		quantidadeEmCaixa = null;
		totalCartao = 0;
		totalCredito = 0;
		totalDebito = 0;
		totalDinheiro = 0;
		totalDoDiaSemOnline = 0;
		totalDoDiaIfood = 0;
		onlineIfood = 0;
		dinheiroIfood = 0;
		cartaoIfood = 0;
		
		ConferirPrograma.mudarJaSalvo(true);
	}
}
