package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import gui.util.LogicaDeTelas;
import gui.util.calculos.Relatorio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

public class ViewRelatorioController implements Initializable {
	Relatorio relatorio = new Relatorio();
	LogicaDeTelas ldt = new LogicaDeTelas();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private TextArea quantidadeEmCaixa;
	@FXML
	private TextArea totalDoDia;
	
	@FXML
	private RadioButton imprimirTotalDoDia;
	
	@FXML
	public void gerarRelatorio() {
		quantidadeEmCaixa.setText(Relatorio.quantidadeEmCaixa);
		
		totalDoDia.setText(relatorio.stringTotalDoDia(Relatorio.totalCartao, Relatorio.totalCredito, Relatorio.totalDebito,
				Relatorio.totalDinheiro, Relatorio.totalDoDiaSemOnline, Relatorio.totalDoDiaIfood, Relatorio.onlineIfood,
				Relatorio.dinheiroIfood, Relatorio.cartaoIfood));
	}
	@FXML
	private void editarCaixarAnterior() {
		ldt.mostraNovaJanela("/gui/ViewCaixaAnterior.fxml", "Caixa Anterior");
		if((boolean) LogicaDeTelas.pdi) {
			limparTudo();
			quantidadeEmCaixa.setText((String) LogicaDeTelas.listaPdi.get(0)); 
			totalDoDia.setText((String) LogicaDeTelas.listaPdi.get(1));
			datePicker.setValue(LocalDate.parse((String) LogicaDeTelas.listaPdi.get(2), dtf));
		}
		LogicaDeTelas.listaPondeDeInformacao(null);
		LogicaDeTelas.ponteDeInformacao(null);
	}
	@FXML
	private void limparTudo() {
		ViewPrincipalController.limparTudo();
		datePicker.setValue(LocalDate.now());
		quantidadeEmCaixa.setText("");
		totalDoDia.setText("");
		imprimirTotalDoDia.setSelected(false);
		Relatorio.limparTudo();
	}
	@FXML
	private void imprimir() {
		relatorio.imprimir(quantidadeEmCaixa, totalDoDia, imprimirTotalDoDia);
	}
	@FXML
	private void salvar() {
		relatorio.salvar(quantidadeEmCaixa, totalDoDia, datePicker);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Locale.setDefault(Locale.US);
		datePicker.setValue(LocalDate.now());
	}
}
