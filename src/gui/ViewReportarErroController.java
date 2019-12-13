package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import gui.conf.Configuracoes;
import gui.util.Alerts;
import gui.util.LogicaDeTelas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewReportarErroController implements Initializable {
	Configuracoes conf = new Configuracoes();
	ObservableList<String> obsList = FXCollections.observableList(Arrays.asList("Sugestão", "Aviso", "Erro crítico"));

	@FXML
	ComboBox<String> comboBox;
	@FXML
	TextField assunto;
	@FXML
	TextArea descricao;
	
	@FXML
	private void onReportarErroAction(Event evento) {
		if(descricao.getText().isEmpty()) {
			Alerts.showAlert("Aviso", "Não foi possível concluir essa ação", "O campo descrição não pode estar vazio", AlertType.WARNING);
		} else {
			int nome = new File(conf.getLocalReportarErro()).listFiles().length;
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(conf.getLocalReportarErro() + nome + ".txt"))){
				bw.write(comboBox.getValue());
				bw.newLine();
				bw.write(assunto.getText());
				bw.newLine();
				bw.write(descricao.getText());
			} catch(IOException e) {
				Alerts.showAlert("Erro inesperado", "IOException", e.getMessage(), AlertType.ERROR);
			}
			Alerts.showAlert("Informação", "Ação realizada com sucesso", null, AlertType.INFORMATION);
			LogicaDeTelas.fecharCaixaDeDialogo(evento);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		comboBox.getItems().setAll(obsList);
		comboBox.setValue("Sugestão");		
	}

}
