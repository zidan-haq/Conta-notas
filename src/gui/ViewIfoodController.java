package gui;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import gui.util.Constraints;
import gui.util.calculos.Relatorio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ViewIfoodController implements Initializable{	
	@FXML
	private TextField online;
	@FXML
	private TextField dinheiro;
	@FXML
	private TextField cartao;
	@FXML
	private Label totalDoDia;
	
	/*
	 * conta o total do dia e passa informações para a classe relatório
	 */
	@FXML
	private void contarTotalDoDia() {
		double auxOnline = 0;
		double auxDinheiro = 0;
		double auxCartao = 0;
		if(!online.getText().isEmpty()) {
			auxOnline = Double.parseDouble(online.getText());
		} else {
			online.setText("0");
		}
		if(!dinheiro.getText().isEmpty()) {
			auxDinheiro = Double.parseDouble(dinheiro.getText());
		} else {
			dinheiro.setText("0");
		}
		if(!cartao.getText().isEmpty()) {
			auxCartao = Double.parseDouble(cartao.getText());
		} else {
			cartao.setText("0");
		}
		totalDoDia.setText(String.format("%.2f", auxOnline + auxDinheiro + auxCartao));
		
		//essa parte do código e responsável para enviar informações para a classe relatório
		Relatorio.valoresIfood(online, dinheiro, cartao, totalDoDia);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Locale.setDefault(Locale.US);
		
		Constraints.setTextFieldDouble(cartao, 12);
		Constraints.setTextFieldDouble(online, 12);
		Constraints.setTextFieldDouble(dinheiro, 12);
	}
}
