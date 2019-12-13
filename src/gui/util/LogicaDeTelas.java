package gui.util;

import java.io.IOException;
import java.util.List;

import application.Main;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogicaDeTelas {
	private Stage dialogoStage = new Stage();
	private boolean jaFoiAberto = false;

	/*
	* Ao abrir e fechar caixas de diálogos, é possível que uma janela queira deixar
	* informações para outra, essas variáveis funcionam como uma ponte entre as janelas, 
	* permitindo a troca de infomações.
	*/
	public static Object pdi;
	public static List<Object> listaPdi;
	
	public static void ponteDeInformacao(Object obj) {
		pdi = obj;
	}

	public static void listaPondeDeInformacao(List<Object> listaObj) {
		listaPdi = listaObj;
	}
	
	public Stage getDialogoStage() {
		return dialogoStage;
	}

	public void mostraNovaJanela(String localAbsoluto, String titulo) {
		try {

			Stage stagePrincipal = Main.getStagePrincipal();

			FXMLLoader loader = new FXMLLoader(getClass().getResource(localAbsoluto));
			Pane pane = loader.load();

			dialogoStage.setTitle(titulo);
			dialogoStage.setScene(new Scene(pane));
			dialogoStage.setResizable(false);

			if (jaFoiAberto == false) {
				dialogoStage.initOwner(stagePrincipal);
				dialogoStage.initModality(Modality.WINDOW_MODAL);
			}
			jaFoiAberto = true;

			dialogoStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("Erro inesperado", "IOException", e.getMessage(), Alert.AlertType.ERROR);
		}
	}

	public Pane alterarJanelaPrincipal(String localAbsoluto) {
		HBox raiz = (HBox) Main.getStagePrincipal().getScene().getRoot();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(localAbsoluto));
		Pane newChildren = null;
		try {
			newChildren = loader.load();
		} catch (IOException e) {
			Alerts.showAlert("Erro inesperado", "IOException", e.getMessage(), Alert.AlertType.ERROR);
		}

		VBox menuLateral = (VBox) raiz.getChildren().get(0);
		raiz.getChildren().clear();
		raiz.getChildren().add(menuLateral);
		raiz.getChildren().add(newChildren);
		
		return newChildren;
	}
	
	public void alterarJanelaPrincipal(Pane janelaJaAberta) {
		HBox raiz = (HBox) Main.getStagePrincipal().getScene().getRoot();
		raiz.getChildren().set(1, janelaJaAberta);
	}

	public static void fecharCaixaDeDialogo(Event evento) {
		Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
		stage.close();
	}
}
