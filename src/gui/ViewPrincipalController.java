package gui;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.conf.Configuracoes;
import gui.util.Alerts;
import gui.util.ConferirPrograma;
import gui.util.LogicaDeTelas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class ViewPrincipalController implements Initializable {
	
	@FXML
	HBox hbox;

	@FXML
	Pane paneLoja;
	@FXML
	Pane paneIfood;
	@FXML
	Pane paneRelatorio;
	@FXML
	Pane paneAjuda;
	@FXML
	Pane paneConfiguracao;
	@FXML
	Pane paneSair;
	
	@FXML
	ImageView loja;
	@FXML
	ImageView ifood;
	@FXML
	ImageView relatorio;
	@FXML
	ImageView ajuda;
	@FXML
	ImageView configuracao;	
	@FXML
	ImageView sair;
	
	//classes auxiliares
	LogicaDeTelas ldt = new LogicaDeTelas();
	Configuracoes conf = new Configuracoes();

	/*
	 * essas variáveis serão usados para navegar entre os menus laterais, sem que o programa apague as informações
	 * já digitadas em uma janela anterior
	*/
	private static Pane lojaAberto;
	private static Pane ifoodAberto;
	private static Pane relatorioAberto;	
	
	@FXML
	public void onLojaAction() {
		if(lojaAberto == null) {
			lojaAberto = ldt.alterarJanelaPrincipal("/gui/ViewLoja.fxml");
			selecionarPane(paneLoja);
		} else {
			ldt.alterarJanelaPrincipal(lojaAberto);
			selecionarPane(paneLoja);
		}
	}
	public void onIfoodAction() {
		if(ifoodAberto == null) {
			ifoodAberto = ldt.alterarJanelaPrincipal("/gui/ViewIfood.fxml");
			selecionarPane(paneIfood);
		} else {
			ldt.alterarJanelaPrincipal(ifoodAberto);
			selecionarPane(paneIfood);
		}
	}
	public void onRelatorioAction() {
		if(relatorioAberto == null) {
			relatorioAberto = ldt.alterarJanelaPrincipal("/gui/ViewRelatorio.fxml");
			selecionarPane(paneRelatorio);
		} else {
			ldt.alterarJanelaPrincipal(relatorioAberto);
			selecionarPane(paneRelatorio);
		}
	}
	public void onAjudaAction() {
		ldt.alterarJanelaPrincipal("/gui/ViewAjuda.fxml");
		selecionarPane(paneAjuda);
	}
	public void onConfiguracaoAction() {
		ldt.alterarJanelaPrincipal("/gui/ViewConf.fxml");
		selecionarPane(paneConfiguracao);
	}
	public void onSairAction() {
		if(ConferirPrograma.jaSalvo) {
			System.exit(0);
		} else {
			Optional<ButtonType> resultado = Alerts.showAlert("Confirme", "O programa ainda não foi salvo.", "Sair sem salvar?", AlertType.CONFIRMATION);
			if(resultado.get() == ButtonType.OK) {
				System.exit(0);
			}
		}
	}
	
	
	//todas as imagens retornadas por este método devem estar na pasta configurada pela classe gui.conf.Configuracoes
	private Image gerarImage(String nomeDaImagem) {
		String caminho = new File(conf.getLocalImagens() + nomeDaImagem).toURI().toString();
		Image imagem = new Image(caminho);
		return imagem;
	}
	
	private void selecionarPane(Pane pane) {
		paneLoja.setBackground(new Background(new BackgroundFill(Paint.valueOf("000000"), null, null)));
		paneIfood.setBackground(new Background(new BackgroundFill(Paint.valueOf("000000"), null, null)));
		paneRelatorio.setBackground(new Background(new BackgroundFill(Paint.valueOf("000000"), null, null)));
		paneAjuda.setBackground(new Background(new BackgroundFill(Paint.valueOf("000000"), null, null)));
		paneConfiguracao.setBackground(new Background(new BackgroundFill(Paint.valueOf("000000"), null, null)));
		paneSair.setBackground(new Background(new BackgroundFill(Paint.valueOf("000000"), null, null)));
		
		if(pane != null) {
			pane.setBackground(null);
		}
	}
	
	public static void limparTudo() {
		lojaAberto = null;
		ifoodAberto = null;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		hbox.setBackground(new Background(new BackgroundImage(gerarImage("background.jpg"), null, null, null, null)));
		
		selecionarPane(null);

		loja.setImage(gerarImage("loja.png"));
		ifood.setImage(gerarImage("ifood.png"));
		relatorio.setImage(gerarImage("relatorio.png"));
		ajuda.setImage(gerarImage("ajuda.png"));
		configuracao.setImage(gerarImage("configuracao.png"));
		sair.setImage(gerarImage("sair.png"));
	}
}
