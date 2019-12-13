package gui.util;

import javafx.scene.control.TextField;

public class Constraints {
	public static void setTextFieldInteger(TextField txt, int tamanho) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue == null || !newValue.matches("\\d*") || newValue.length() > tamanho) {
				txt.setText(oldValue);
			}
		});
	}
	
	public static void setTextFieldDouble(TextField txt, int tamanho) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue == null || !newValue.matches("([\\-])?\\d*([\\.]\\d*)?") || newValue.length() > tamanho) {
				if(newValue.contains(",") && !newValue.contains(".") && newValue.length() <= tamanho) {
					txt.setText(newValue.replace(",", "."));
				} else {
					txt.setText(oldValue);
				}
			}
		});
	}
}
