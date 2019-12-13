package gui;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import gui.util.Constraints;
import gui.util.LogicaDeTelas;
import gui.util.calculos.Calculadora;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ViewCalculadoraController implements Initializable {	
	@FXML
	public TextField tela;
	
	@FXML
	public TextField historico1;
	public TextField historico2;
	public TextField historico3;

	@FXML
	public Button terminar;
	
	@FXML
	public Button zero;
	public Button um;
	public Button dois;
	public Button tres;
	public Button quatro;
	public Button cinco;
	public Button seis;
	public Button sete;
	public Button oito;
	public Button nove;
	public Button ponto;
	public Button apagar;
	public Button apagarTudo;
	public Button fechar;
	
	@FXML
	public Button somar;
	public Button subtrair;
	public Button multiplicar;
	public Button dividir;
	public Button igual;

	Calculadora calculadora = new Calculadora();
	
	@FXML
	public void onTerminarAction(Event evento) {
		String resultado = tela.getText();
		if(resultado != null) {
			LogicaDeTelas.ponteDeInformacao(resultado);
		} else {
			LogicaDeTelas.ponteDeInformacao("0");
		}
		LogicaDeTelas.fecharCaixaDeDialogo(evento);
	}
	
	@FXML
	public void onZeroAction() {
		calculadora.pressionarNumero("0");
	}
	@FXML
	public void onUmAction() {
		calculadora.pressionarNumero("1");
	}
	@FXML
	public void onDoisAction() {
		calculadora.pressionarNumero("2");
	}
	@FXML
	public void onTresAction() {
		calculadora.pressionarNumero("3");
	}
	@FXML
	public void onQuatroAction() {
		calculadora.pressionarNumero("4");
	}
	@FXML
	public void onCincoAction() {
		calculadora.pressionarNumero("5");
	}
	@FXML
	public void onSeisAction() {
		calculadora.pressionarNumero("6");
	}
	@FXML
	public void onSeteAction() {
		calculadora.pressionarNumero("7");
	}
	@FXML
	public void onOitoAction() {
		calculadora.pressionarNumero("8");
	}
	@FXML
	public void onNoveAction() {
		calculadora.pressionarNumero("9");
	}
	@FXML
	public void onPontoAction() {
		calculadora.pressionarPonto();
	}
	@FXML
	public void onApagarAction() {
		calculadora.apagar();
	}
	@FXML
	public void onApagarTudoAction() {
		calculadora.apagarTudo();
	}
	@FXML
	public void onFecharAction(Event evento) {
		LogicaDeTelas.fecharCaixaDeDialogo(evento);
	}
	@FXML
	public void onSomarAction() {
		calculadora.somar();
	}
	
	@FXML
	public void onSubtrairAction() {
		calculadora.subtrair();
	}
	
	@FXML
	public void onMultiplicarAction() {
		calculadora.multiplicar();
	}
	
	@FXML
	public void onDividirAction() {
		calculadora.dividir();
	}
	
	@FXML
	public void onIgualAction() {
		calculadora.igual();
	}
	
	@FXML
	public void onHistorico2Action() {
		calculadora.historico2Action();
	}
	
	@FXML
	public void onHistorico3Action() {
		calculadora.historico3Action();
	}
	
	@FXML
	private void eventoDeTeclado(KeyEvent evento) {
		igual.requestFocus();
		if(evento.getCode() == KeyCode.ENTER) {
			onIgualAction();
			evento.consume();
		} else if(evento.getCode() == KeyCode.NUMPAD0 || evento.getCode() == KeyCode.DIGIT0) {
			onZeroAction();
		} else if(evento.getCode() == KeyCode.NUMPAD1 || evento.getCode() == KeyCode.DIGIT1) {
			onUmAction();
		} else if(evento.getCode() == KeyCode.NUMPAD2 || evento.getCode() == KeyCode.DIGIT2) {
			onDoisAction();
		} else if(evento.getCode() == KeyCode.NUMPAD3 || evento.getCode() == KeyCode.DIGIT3) {
			onTresAction();
		} else if(evento.getCode() == KeyCode.NUMPAD4 || evento.getCode() == KeyCode.DIGIT4) {
			onQuatroAction();
		} else if(evento.getCode() == KeyCode.NUMPAD5 || evento.getCode() == KeyCode.DIGIT5) {
			onCincoAction();
		} else if(evento.getCode() == KeyCode.NUMPAD6 || evento.getCode() == KeyCode.DIGIT6) {
			onSeisAction();
		} else if(evento.getCode() == KeyCode.NUMPAD7 || evento.getCode() == KeyCode.DIGIT7) {
			onSeteAction();
		} else if(evento.getCode() == KeyCode.NUMPAD8 || evento.getCode() == KeyCode.DIGIT8) {
			onOitoAction();
		} else if(evento.getCode() == KeyCode.NUMPAD9 || evento.getCode() == KeyCode.DIGIT9) {
			onNoveAction();
		} else if(evento.getCode() == KeyCode.PERIOD || evento.getCode() == KeyCode.SEPARATOR || 
				evento.getCode() == KeyCode.COMMA || evento.getCode() == KeyCode.DECIMAL || evento.getCode() == KeyCode.UNDEFINED) {
			onPontoAction();
		}else if(evento.getCode() == KeyCode.BACK_SPACE) {
			onApagarAction();
		}else if(evento.getCode() == KeyCode.DELETE) {
			onApagarTudoAction();
		}else if(evento.getCode() == KeyCode.ESCAPE) {
			onFecharAction(evento);
		} else if(evento.getCode() == KeyCode.ADD) {
			onSomarAction();
		} else if(evento.getCode() == KeyCode.SUBTRACT) {
			onSubtrairAction();
		} else if(evento.getCode() == KeyCode.MULTIPLY) {
			onMultiplicarAction();
		} else if(evento.getCode() == KeyCode.DIVIDE) {
			onDividirAction();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Locale.setDefault(Locale.US);
		calculadora.setCamposDaCalculadora(tela, historico1, historico2, historico3);
		Constraints.setTextFieldDouble(tela, 16);
	}
}
