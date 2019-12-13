package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.LogicaDeTelas;
import gui.util.calculos.CaixaAnterior;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ViewCaixaAnteriorController implements Initializable {
	CaixaAnterior caixaAnterior = new CaixaAnterior();
	
	@FXML
	public ListView<String> listaDeCaixas;
	
	@FXML
	public void onCancelarAction(Event evento) {
		LogicaDeTelas.ponteDeInformacao(false);
		LogicaDeTelas.fecharCaixaDeDialogo(evento);
	}
	public void onEditarAction(Event evento) {
		String caixaSelecionado = listaDeCaixas.getSelectionModel().getSelectedItem();
		LogicaDeTelas.listaPondeDeInformacao(caixaAnterior.editar(caixaSelecionado));
		LogicaDeTelas.ponteDeInformacao(true);
		LogicaDeTelas.fecharCaixaDeDialogo(evento);
	}	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		caixaAnterior.gerarListaDeCaixas(listaDeCaixas);
	}
}
