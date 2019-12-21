package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import gui.conf.Configuracoes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ViewConfController implements Initializable {
	
	Configuracoes conf = new Configuracoes();

	private ObservableList<String> obsListCaixas;
	private ObservableList<String> obsListImpressoras;

	@FXML
	private TextField localDoPrograma;

	@FXML
	private ComboBox<String> quantidadeDeCaixasSalvos;

	@FXML
	private ComboBox<String> selecionarImpressora;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	@FXML
	public void onBtSalvarAction(Event evento) {
		conf.salvarConfiguracao(localDoPrograma, quantidadeDeCaixasSalvos, selecionarImpressora);
		if(Configuracoes.sairDepoisDeSalvar) {
			System.exit(0);
		}
	}

	@FXML
	public void onBtCancelarAction(Event evento) {
		localDoProgramaAtual();
		carregarOpcoesQuantidadeDeCaixasSalvos();
		carregarListaDeImpressoras();
		if(Configuracoes.sairDepoisDeSalvar) {
			System.exit(0);
		}
	}

//----------------

	public void localDoProgramaAtual() {
		if (conf.getLocalDoPrograma() != null) {
			localDoPrograma.setText(conf.getLocalDoPrograma());
		}
	}

	public void carregarOpcoesQuantidadeDeCaixasSalvos() {
		List<String> listaAuxiliar = new ArrayList<>(conf.getOpcoesQuantidadeDeCaixas().keySet());
		obsListCaixas = FXCollections.observableList(listaAuxiliar);
		quantidadeDeCaixasSalvos.setItems(obsListCaixas);

		if (conf.getQuantidadeDeCaixasSalvos() != null) {
			Set<String> setAuxiliar = conf.getOpcoesQuantidadeDeCaixas().keySet();
			setAuxiliar.forEach(x -> {
				if (conf.getOpcoesQuantidadeDeCaixas().get(x) == conf.getQuantidadeDeCaixasSalvos()) {
					quantidadeDeCaixasSalvos.setValue(x);
				}
			});
		}
	}

	public void carregarListaDeImpressoras() {
		obsListImpressoras = FXCollections.observableArrayList(conf.listaDeImpressoras());
		selecionarImpressora.setItems(obsListImpressoras);

		if (conf.getSelecionarImpressora() != null) {
			selecionarImpressora.setValue(conf.getSelecionarImpressora());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		localDoProgramaAtual();
		carregarOpcoesQuantidadeDeCaixasSalvos();
		carregarListaDeImpressoras();
	}

}
