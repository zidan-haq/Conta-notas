package gui.util.calculos;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextField;

public class Calculadora {

	TextField tela; // essa é a tela da calculadora
	TextField historico1; // historico que é mudado conforme as operções matemáticas são feitas
	TextField historico2; // historico que é retornado assim que o botão de igual é pressionado ele também
							// possuí a ação de retornar
							// os cálculos guardados nele;
	TextField historico3; // possui as mesmas funcionalidades do historico2, porém guarda calculos mais
							// antigos

	private List<String> historicoCalculo = new ArrayList<>();
	private String numeroAuxiliar;

	/**
	 * essa variável é mudada para true quando o resultado é gerado, a ideia é
	 * apagar o conteúdo da tela ao pressionar um digito e ficar apenas o número
	 * digitado, pois na tela contem o resultado do último calculo, que deve ser
	 * descartado.
	 **/
	private boolean apagarTelaEEscreverNumero = false;

	public void setCamposDaCalculadora(TextField tela, TextField historico1, TextField historico2,
			TextField historico3) {
		this.tela = tela;
		this.historico1 = historico1;
		this.historico2 = historico2;
		this.historico3 = historico3;
	}

	/*
	 * comportamento dos números ao serem pressionados
	 */
	public void pressionarNumero(String numero) {
		try {
			if (!apagarTelaEEscreverNumero && tela.getText().contains(".")
					|| !apagarTelaEEscreverNumero && Double.parseDouble(tela.getText()) != 0) {
				tela.setText(tela.getText() + numero);
			} else {
				tela.setText(numero);
				apagarTelaEEscreverNumero = false;
			}
		} catch (NumberFormatException e) {
			tela.setText(numero);
			apagarTelaEEscreverNumero = false;
		}
	}

	public void pressionarPonto() {
		if (!tela.getText().isEmpty() && !apagarTelaEEscreverNumero) {
			tela.setText(tela.getText() + ".");
		} else {
			tela.setText("0.");
		}
	}

	/**
	 * Esse método é a ação do botão apagar, ele apaga o último caractére digitado
	 * pelo usuário presente na tela
	 */
	public void apagar() {
		String texto = tela.getText();
		if (!tela.getText().isEmpty()) {
			tela.setText(texto.substring(0, texto.length() - 1));
		}
	}

	/**
	 * Esse método é a ação do botão apagarTudo, ele apaga o conteúdo da tela do
	 * historico1 e do historico de cálculo
	 */
	public void apagarTudo() {
		tela.setText("");
		historico1.setText("");
		historicoCalculo.clear();
	}

	public void somar() {
		if (tela.getText().isEmpty() && historicoCalculo.size() > 0) {
			historicoCalculo.remove(historicoCalculo.size() - 1);
			historicoCalculo.add("+");
		} else {
			if (tela.getText().isEmpty()) {
				numeroAuxiliar = "0";
			} else {
				numeroAuxiliar = tela.getText();
			}
			tela.setText("");

			if (numeroAuxiliar != "") {
				historicoCalculo.add(numeroAuxiliar);
				historicoCalculo.add("+");
			} else {
				historicoCalculo.add("0");
				historicoCalculo.add("+");
			}
		}
		gerarHistorico(false);
	}

	public void subtrair() {
		if (tela.getText().isEmpty() && historicoCalculo.size() > 0) {
			historicoCalculo.remove(historicoCalculo.size() - 1);
			historicoCalculo.add("-");
		} else {
			if (tela.getText().isEmpty()) {
				numeroAuxiliar = "0";
			} else {
				numeroAuxiliar = tela.getText();
			}
			tela.setText("");

			if (numeroAuxiliar != "") {
				historicoCalculo.add(numeroAuxiliar);
				historicoCalculo.add("-");
			} else {
				historicoCalculo.add("0");
				historicoCalculo.add("-");
			}
		}
		gerarHistorico(false);
	}

	public void multiplicar() {
		if (tela.getText().isEmpty() && historicoCalculo.size() > 0) {
			historicoCalculo.remove(historicoCalculo.size() - 1);
			historicoCalculo.add("*");
		} else {
			if (tela.getText().isEmpty()) {
				numeroAuxiliar = "0";
			} else {
				numeroAuxiliar = tela.getText();
			}
			tela.setText("");

			if (numeroAuxiliar != "") {
				historicoCalculo.add(numeroAuxiliar);
				historicoCalculo.add("*");
			} else {
				historicoCalculo.add("0");
				historicoCalculo.add("*");
			}
		}
		gerarHistorico(false);
	}

	public void dividir() {
		if (tela.getText().isEmpty() && historicoCalculo.size() > 0) {
			historicoCalculo.remove(historicoCalculo.size() - 1);
			historicoCalculo.add("/");
		} else {
			if (tela.getText().isEmpty()) {
				numeroAuxiliar = "0";
			} else {
				numeroAuxiliar = tela.getText();
			}
			tela.setText("");

			if (numeroAuxiliar != "") {
				historicoCalculo.add(numeroAuxiliar);
				historicoCalculo.add("/");
			} else {
				historicoCalculo.add("0");
				historicoCalculo.add("/");
			}
		}
		gerarHistorico(false);
	}

	public void igual() {
		double resultado = 0;
		String primeiroNumero;
		Double segundoNumero = null;
		double auxiliarContagem = 0;
		boolean divisaoPorZero = false;

		if (!tela.getText().isEmpty()) {
			historicoCalculo.add(tela.getText());
		} else {
			historicoCalculo.add("0");
		}

		for (int x = 0; x < historicoCalculo.size(); x++) {
			primeiroNumero = historicoCalculo.get(x);
			if (!primeiroNumero.equals("+") && !primeiroNumero.equals("-") && !primeiroNumero.equals("*")
					&& !primeiroNumero.equals("/")) {
				if (auxiliarContagem == 0) {
					resultado = Double.parseDouble(primeiroNumero);
					auxiliarContagem++;
				}

				if (x < historicoCalculo.size() - 2 && x % 2 == 0) {
					segundoNumero = Double.parseDouble(historicoCalculo.get(x + 2));
				}

				// Soma
			} else if (primeiroNumero.equals("+") && segundoNumero != null) {
				resultado = resultado + segundoNumero;
				// Subtração
			} else if (primeiroNumero.equals("-") && segundoNumero != null) {
				resultado = resultado - segundoNumero;
				// Multiplicação
			} else if (primeiroNumero.equals("*") && segundoNumero != null) {
				resultado = resultado * segundoNumero;
				// Divisão
			} else if (primeiroNumero.equals("/") && segundoNumero != null) {
				if (segundoNumero == 0) {
					divisaoPorZero = true;
					break;
				} else {
					resultado = resultado / segundoNumero;
				}
			}
		}
		if (divisaoPorZero) {
			divisaoPorZero = false;
			tela.setText("");
			historico1.setText("Não é possível dividir por zero");
			historicoCalculo.clear();
		} else {
			tratamentoDeResultado(resultado);
		}
	}

	private void tratamentoDeResultado(double resultado) {
		// esse próximo if impede que números como "1.00" sejam escritos na tela,
		// escrevendo "1" sem casas decimais em vez disso

		if (resultado == Math.rint(resultado)) {
			if (resultado < 1.0E16) {
				tela.setText(String.valueOf((long) resultado));
				
				apagarTelaEEscreverNumero = true;
				gerarHistorico(true);
				historicoCalculo.clear();
			} else {
				tela.setText("");
				historico1.setText("Número com mais de 16 digitos");
				historicoCalculo.clear();
			}
		} else {
			// essa parte do código faz com que apareça na tela apenas números com 16caractéres e
			// tambem arredonda números reais que possuem zeros a direita de pois do . (ponto)

			tela.setText("");
			boolean ponto = false;
			int tamanho = 0;
			for(String x : String.valueOf(resultado).split("")) {
				if(x.equals(".")) {
					ponto = true;
				}
				if(ponto && x.equals("0")) {
					break;
				}
				tela.setText(tela.getText() + x);
				tamanho++;
				if(tamanho > 16) {
					break;
				}
			}
			apagarTelaEEscreverNumero = true;
			gerarHistorico(true);
			historicoCalculo.clear();
		}
	}

	/**
	 * Esses métodos contém as ações que serão realizadas pelos histórico2/3 quando
	 * pressionados A ação deles consiste em realizar o cálculo que está presente em
	 * seu corpo e mostrar o resultado na tela
	 */
	public void historico2Action() {
		apagarTudo();
		for (String x : historico2.getText().split(" ")) {
			historicoCalculo.add(x);
		}
		tela.setText(historicoCalculo.remove(historicoCalculo.size() - 1));
		igual();
	}

	public void historico3Action() {
		apagarTudo();
		for (String x : historico3.getText().split(" ")) {
			historicoCalculo.add(x);
		}
		tela.setText(historicoCalculo.remove(historicoCalculo.size() - 1));
		igual();
	}

	/**
	 * @param novoHistorico Quando passado true para esse método, ele salvará no
	 *                      historico3 os calculos do historico2 e no historico2 os
	 *                      calculos do historico1 Quando passado false para esse
	 *                      método, ele apenas adicionará os calculos da tela no
	 *                      historico1
	 */
	private void gerarHistorico(boolean novoHistorico) {
		String historico = historicoCalculo.toString().replace(",", "").replace("[", "").replace("]", "");

		historico1.setText(historico);

		if (novoHistorico) {
			historico3.setText(historico2.getText());
			historico2.setText(historico1.getText());
		}
	}
}
