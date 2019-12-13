package gui.util.calculos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gui.conf.Configuracoes;
import gui.util.Alerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class CaixaAnterior {
	Configuracoes conf = new Configuracoes();
	
	public void gerarListaDeCaixas(ListView<String> listaDeCaixas) {
		File file = new File(conf.getLocalArmazenamentoDeCaixas());
		
		listaDeCaixas.getItems().addAll(file.list());
	}
	
	public List<Object> editar(String caixaSelecionado) {
		List<Object> lista = new ArrayList<>();
		lista.add(lerTxt(conf.getLocalArmazenamentoDeCaixas(), caixaSelecionado));
		lista.add(lerTxt(conf.getLocalArmazenamentoTotalDoDia(), caixaSelecionado.replace(".txt", "")));
		lista.add(caixaSelecionado.replace(".txt", "")); //essa será a nova data informada no datePicker
		return lista;
	}
	
	private String lerTxt(String local, String caixaSelecionado) {
		String str = "";

		if(caixaSelecionado == null) {			
			Alerts.showAlert("Atenção", "Selecione um caixa primeiro", null, Alert.AlertType.WARNING);
		} else {
			try (BufferedReader br = new BufferedReader(
					new FileReader(local + caixaSelecionado))) {
				
				String auxiliar = br.readLine();
				
				while (auxiliar != null) {
					str += auxiliar + "\n";
					auxiliar = br.readLine();
				}
			} catch (NullPointerException e) {
				Alerts.showAlert("Erro ao ler Arquivo", "NullPointerException", "O caixa selecionado está corrompido\n" + e.getMessage(), Alert.AlertType.ERROR);
			} catch (IOException e) {
				Alerts.showAlert("Erro inesperado", "IOException", e.getMessage(), Alert.AlertType.ERROR);
			}
		}
		return str;
	}
}
