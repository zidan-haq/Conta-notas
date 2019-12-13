package gui.util.calculos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import gui.conf.Configuracoes;
import gui.util.Alerts;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LogicaDeCalculos {

	Calendar cal = Calendar.getInstance(); // está sendo usado principalmente para retornar o dia atual
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // formatação padrão para ser usada em nome de arquivos e
																// datas geradas pelo programa

	double totalEmCaixa; // auxilia na soma do total em caixa

	Configuracoes conf = new Configuracoes(); // acessa as configurações
	
//métodos para realização dos cálculos
	public String SomarNotas(TextField quantidadeDeNotas, double valorDaNota) {
		Locale.setDefault(Locale.US);
		String somaDasNotas = null;

		// esse if irá impedir que os campos fiquem vazios, adicionando 0 se isso
		// acontecer
		if (quantidadeDeNotas.getText().length() < 1) {
			quantidadeDeNotas.setText("0");
		}

		somaDasNotas = String.format("%.2f", Integer.parseInt(quantidadeDeNotas.getText()) * valorDaNota);
		return somaDasNotas;
	}

	public String somarTotalEmCaixa(List<Label> listSomaDasNotas) {		
		totalEmCaixa = 0.0;
		listSomaDasNotas.forEach(x -> totalEmCaixa += Double.parseDouble(x.getText()));

		return String.format("%.2f", totalEmCaixa);
	}
	
	/**
	 * soma o total em dinheiro circulado no caixa, ou seja,
	 * total de notas mais outros valores menos o que já havia no caixa
	 */
	public String somarTotalEmDinheiro(Label totalEmCaixa, TextField outrosValores, TextField caixaAnterior) {
		Double totalEmDinheiro;
		
		if(outrosValores.getText().isEmpty()) {
			outrosValores.setText("0");
		}
		if(caixaAnterior.getText().isEmpty()) {
			caixaAnterior.setText("0");
		}
		
		totalEmDinheiro = Double.parseDouble(totalEmCaixa.getText()) + Double.parseDouble(outrosValores.getText()) - Double.parseDouble(caixaAnterior.getText());
		
		return String.format("%.2f", totalEmDinheiro);
	}

//	Método para retornar caixa anterior criado pelo método salvar()
	public void procurarCaixaAnterior(TextField caixaAnterior) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DATE, -1);
		int diaAnterior = calendario.get(Calendar.DAY_OF_MONTH);
		
		if (conf.atualizarEVerificarConfiguracao()) {
			
			File anteriores[] = new File(conf.getLocalArmazenamentoDeCaixas()).listFiles();
			String totalDiaAnterior = "";
			for (File anterior : anteriores) {
				if (Integer.parseInt(anterior.getName().substring(0,
						2)) == diaAnterior) {
					try (BufferedReader br = new BufferedReader(new FileReader(anterior))) {
						String proximaLinha = br.readLine();
						while (proximaLinha != null) {
							if(proximaLinha.split(" ")[0].toLowerCase().equals("total")) {
								totalDiaAnterior = proximaLinha;
							}
							proximaLinha = br.readLine();
						}
						caixaAnterior.setText(totalDiaAnterior.split(" ")[totalDiaAnterior.split(" ").length - 1]);
						break;
					} catch (IOException e) {
						e.getMessage();
					}
				}
			}
			if (totalDiaAnterior == "") {
				Alerts.showAlert("Nenhum caixa encontrado", null, "Os valores do caixa anterior não foram encontrados",
						Alert.AlertType.WARNING);
			}
		}
	}
	
	public void procurarDescontarHoje(TextField descontarCreditoHoje, TextField descontarDebitoHoje) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DATE, -1);
		int diaAnterior = calendario.get(Calendar.DAY_OF_MONTH);
		
		if (conf.atualizarEVerificarConfiguracao()) {
			File anteriores[] = new File(conf.getLocalArmazenamentoDeCaixas()).listFiles();
			String credito = "";
			String debito = "";
			for (File anterior : anteriores) {
				if (Integer.parseInt(anterior.getName().substring(0,
						2)) == diaAnterior) {
					try (BufferedReader br = new BufferedReader(new FileReader(anterior))) {
						String proximaLinha = br.readLine();
						
						while (proximaLinha != null) {							
							if(proximaLinha.split(" ")[0].toLowerCase().equals("crédito")) {
								credito = proximaLinha;
							}
							if(proximaLinha.split(" ")[0].toLowerCase().equals("débito")) {
								debito = proximaLinha;
							}
							proximaLinha = br.readLine();
						}
						descontarCreditoHoje.setText(credito.split(" ")[credito.split(" ").length - 1]);
						descontarDebitoHoje.setText(debito.split(" ")[credito.split(" ").length - 1]);
						break;
					} catch (IOException e) {
						e.getMessage();
					}
				}
			}
			if (credito == "" && debito == "") {
				Alerts.showAlert("Nenhum valor encontrado", null, "Os valores não foram encontrados",
						Alert.AlertType.WARNING);
			}
		}
	}
}
