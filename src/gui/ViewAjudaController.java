package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.LogicaDeTelas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class ViewAjudaController implements Initializable{
	LogicaDeTelas ldt = new LogicaDeTelas();
	
	@FXML
	private void manual() {
		File file = new File("Manual Conta Notas.pdf");
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	private void sobre() {
		ldt.mostraNovaJanela("/gui/ViewSobre.fxml", "Sobre");
	}
	@FXML
	private void reportarErro() {
		ldt.mostraNovaJanela("/gui/ViewReportarErro.fxml", "Reportar Erro");
	}
	
	@FXML
	private void linkGitHub() {
		try {
			URI link = new URI("https://github.com/zidan-haq");
			Desktop.getDesktop().browse(link);
		} catch (Exception e) {
			Alerts.showAlert("Erro inesperado", "IOException", e.getMessage(), Alert.AlertType.ERROR);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
