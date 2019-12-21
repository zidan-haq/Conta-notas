package application;
	
import java.io.IOException;

import gui.conf.Configuracoes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	private static Stage stagePrincipal;
	
	public static Stage getStagePrincipal() {
		return stagePrincipal;
	}
	
	@Override
	public void start(Stage stage) {
		try {
			Parent parent;
			
			Configuracoes.sairDepoisDeSalvar = true;
			if(new Configuracoes().atualizarEVerificarConfiguracao()) {
				parent = FXMLLoader.load(getClass().getResource("/gui/ViewPrincipal.fxml"));
				Configuracoes.sairDepoisDeSalvar = false;
			} else {
				//se o programa não estiver configurado a tela de configurações será aberta por meio desse código
				parent = FXMLLoader.load(getClass().getResource("/gui/ViewConf.fxml"));
			}
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setTitle("Conta Notas");
			stage.setResizable(false);
			stage.show();
			stagePrincipal = stage;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}