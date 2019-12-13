package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import gui.conf.Configuracoes;
import gui.util.Constraints;
import gui.util.LogicaDeTelas;
import gui.util.calculos.LogicaDeCalculos;
import gui.util.calculos.Relatorio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entidades.Cartao;

public class ViewLojaController implements Initializable {
	@FXML
	private TabPane tabPane;	
	
	//Essas são as somas das notas
	@FXML
	private Label somacem;
	@FXML
	private Label somacinquenta;
	@FXML
	private Label somavinte;
	@FXML
	private Label somadez;
	@FXML
	private Label somacinco;
	@FXML
	private Label somadois;
	@FXML
	private Label somaum;
	@FXML
	private Label somacinquentaCents;
	@FXML
	private Label somavinteECinco;
	@FXML
	private Label somadezCents;
	@FXML
	private Label somacincoCents;
	
	//essas são as quantidades de notas
	@FXML
	private TextField cem;
	@FXML
	private TextField cinquenta;
	@FXML
	private TextField vinte;
	@FXML
	private TextField dez;
	@FXML
	private TextField cinco;
	@FXML
	private TextField dois;
	@FXML
	private TextField um;
	@FXML
	private TextField cinquentaCents;
	@FXML
	private TextField vinteECinco;
	@FXML
	private TextField dezCents;
	@FXML
	private TextField cincoCents;
	
	//Valores para contagem do dinheiro caixa
	@FXML
	private Label totalEmCaixa;
	@FXML
	private TextField outrosValores;
	@FXML
	private TextField caixaAnterior;
	@FXML
	private Label totalEmDinheiro;

	//Variáveis referentes a área de cartão
	@FXML
	private TextField credito;
	@FXML
	private TextField debito;
	@FXML
	private TextField descontarCreditoAmanha;
	@FXML
	private TextField descontarDebitoAmanha;
	@FXML
	private TextField descontarCreditoHoje;
	@FXML
	private TextField descontarDebitoHoje;
	
	@FXML
	private TableView<Cartao> tableViewCartoes;
	@FXML
	private TableColumn<Cartao, Cartao> tableColumnDescontar;
	@FXML
	private TableColumn<Cartao, Double> tableColumnCredito;
	@FXML
	private TableColumn<Cartao, Double> tableColumnDebito;
	@FXML
	private TableColumn<Cartao, Double> tableColumnTotal;
	@FXML
	private TableColumn<Cartao, Cartao> tableColumnExcluir;
	
	@FXML
	private Label totalCredito;
	@FXML
	private Label totalDebito;
	@FXML
	private Label totalEmCartao;
	
	//variáveis auxiliar para contagens dos totais
	Map<Cartao, Boolean> mapDescontar = new HashMap<>();
	double somaCreditoDescontar;
	double somaDebitoDescontar;
	double somaTotalDescontar;
	double auxCredito;
	double auxDebito;
	double auxCartao;
	
	//é a soma do total em dinheiro mais o total em cartão
	@FXML
	private Label totalDoDia;

//classe responsável por gerenciar a troca e telas e mostrar novoas telas(stages)
	LogicaDeTelas ldt = new LogicaDeTelas();
	
//classe responsável por fazer os cálculos
	LogicaDeCalculos ldc = new LogicaDeCalculos();
	
//casse responsável pelas configurações
	Configuracoes conf = new Configuracoes();
	
// Lista que será utilizada para passar como argumento para métodos
	List<Label> listSomaDasNotas = new ArrayList<>();
	List<TextField> listQuantidadeDeNotas = new ArrayList<>();
	List<Cartao> listaCartoes = new ArrayList<>();
	
	private void atualizarSomaDasNotas(){
		listSomaDasNotas.clear();
		listSomaDasNotas.add(somacem);
		listSomaDasNotas.add(somacinquenta);
		listSomaDasNotas.add(somavinte);
		listSomaDasNotas.add(somadez);
		listSomaDasNotas.add(somacinco);
		listSomaDasNotas.add(somadois);
		listSomaDasNotas.add(somaum);
		listSomaDasNotas.add(somacinquentaCents);
		listSomaDasNotas.add(somavinteECinco);
		listSomaDasNotas.add(somadezCents);
		listSomaDasNotas.add(somacincoCents);
	}
	
	private void atualizarQuantidadeDeNotas(){
		listQuantidadeDeNotas.clear();
		listQuantidadeDeNotas.add(cem);
		listQuantidadeDeNotas.add(cinquenta);
		listQuantidadeDeNotas.add(vinte);
		listQuantidadeDeNotas.add(dez);
		listQuantidadeDeNotas.add(cinco);
		listQuantidadeDeNotas.add(dois);
		listQuantidadeDeNotas.add(um);
		listQuantidadeDeNotas.add(cinquentaCents);
		listQuantidadeDeNotas.add(vinteECinco);
		listQuantidadeDeNotas.add(dezCents);
		listQuantidadeDeNotas.add(cincoCents);
	}

	//Botões responsáveis por mudar a tabPane. Botão Adicionar Cartão > e Botão Voltar
	@FXML
	public void mudarParaTabCartao() {
		somar();
		tabPane.getSelectionModel().select(1);
	}
	@FXML
	public void mudarParaTabDinheiro() {
		contarTotaisCartoes();
		tabPane.getSelectionModel().select(0);
	}
	
	
	@FXML
	public void editarCaixaAnterior() {
		ldt.mostraNovaJanela("/gui/ViewCaixaAnterior.fxml", "Lista de caixas anteriores");
	}
	
	//métodos para realização dos cálculos
	
	/**
	 * somar() realiza a soma para o campo totalEmcaixa, e para o campo totalEmDinheiro
	 */
	@FXML
	private void somar() {
		somacem.setText(ldc.SomarNotas(cem, 100.00));
		somacinquenta.setText(ldc.SomarNotas(cinquenta, 50.00));
		somavinte.setText(ldc.SomarNotas(vinte, 20.00));
		somadez.setText(ldc.SomarNotas(dez, 10.00));
		somacinco.setText(ldc.SomarNotas(cinco, 5.00));
		somadois.setText(ldc.SomarNotas(dois, 2.00));
		somaum.setText(ldc.SomarNotas(um, 1.00));
		somacinquentaCents.setText(ldc.SomarNotas(cinquentaCents, 0.50));
		somavinteECinco.setText(ldc.SomarNotas(vinteECinco, 0.25));
		somadezCents.setText(ldc.SomarNotas(dezCents, 0.10));
		somacincoCents.setText(ldc.SomarNotas(cincoCents, 0.05));
		
		atualizarSomaDasNotas();
		totalEmCaixa.setText(ldc.somarTotalEmCaixa(listSomaDasNotas));
		
		totalEmDinheiro.setText(ldc.somarTotalEmDinheiro(totalEmCaixa, outrosValores, caixaAnterior));
		
		contarTotalDoDia(); //conta o total de dinheiro gerado no dia
	}
	
	@FXML
	private void adicionarCartao() {
		somar(); //soma a quantidade de notas antes de adicionar o cartão
		
		Double doubleCredito = 0.00;
		Double doubleDebito = 0.00;
		if(!credito.getText().isEmpty()) {
			doubleCredito = Double.parseDouble(credito.getText());
		}
		if(!debito.getText().isEmpty()) {
			doubleDebito = Double.parseDouble(debito.getText());
		}
		
		if(doubleCredito != 0 || doubleDebito != 0) {
			listaCartoes.add(new Cartao(doubleCredito, doubleDebito));
		}
		
		atualizarCartoes();
		credito.setText("0");
		debito.setText("0");
	}
	
	private void initRemoverButoes() {
		tableColumnExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnExcluir.setCellFactory(param -> new TableCell<Cartao, Cartao>() {
			private final Button button = new Button(" X ");

			@Override
			protected void updateItem(Cartao cartao, boolean empty) {
				super.updateItem(cartao, empty);
				if (cartao == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removerCartao(cartao));
			}
		});
	}

	private void removerCartao(Cartao cartao) {
		listaCartoes.remove(cartao);
		somarMapDescontar(cartao, false);
		mapDescontar.remove(cartao);
		atualizarCartoes();
	}

	private void atualizarCartoes() {
		initDescontarCheckBox();
		tableColumnCredito.setCellValueFactory(new PropertyValueFactory<Cartao, Double>("Credito"));
		tableColumnDebito.setCellValueFactory(new PropertyValueFactory<Cartao, Double>("Debito"));
		tableColumnTotal.setCellValueFactory(new PropertyValueFactory<Cartao, Double>("Total"));
		initRemoverButoes();

		tableViewCartoes.setItems(FXCollections.observableArrayList(listaCartoes));
		
		contarTotaisCartoes();//método para fazer a contagem dos totais em crédito, débito e em cartão
	}
	
	private void initDescontarCheckBox() {
		tableColumnDescontar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnDescontar.setCellFactory(param -> new TableCell<Cartao, Cartao>() {
			private final CheckBox checkBox= new CheckBox("");

			@Override
			protected void updateItem(Cartao cartao, boolean empty) {
				super.updateItem(cartao, empty);
				if (cartao == null) {
					setGraphic(null);
					return;
				}
				mapDescontar.put(cartao, false);
				setGraphic(checkBox);
				checkBox.setOnAction(event -> somarMapDescontar(cartao, checkBox.isSelected()));
			}
		});
	}
	
	private void somarMapDescontar(Cartao cartao, boolean selecionado) {		
		somaCreditoDescontar = 0;
		somaDebitoDescontar = 0;
		somaTotalDescontar = 0;
		if(mapDescontar.containsKey(cartao)) {
			mapDescontar.replace(cartao, selecionado);
		}
		
		mapDescontar.forEach((chave, valor) -> {
			if(valor) {
				somaCreditoDescontar += chave.getCredito();
				somaDebitoDescontar += chave.getDebito();
				somaTotalDescontar += chave.getTotal();
			}
		});
		descontarCreditoAmanha.setText(String.format("%.2f", somaCreditoDescontar));
		descontarDebitoAmanha.setText(String.format("%.2f", somaDebitoDescontar));
	}

	@FXML
	private void contarTotaisCartoes() {
		if(descontarCreditoHoje.getText().isEmpty()) {
			descontarCreditoHoje.setText("0");
		}
		if(descontarDebitoHoje.getText().isEmpty()) {
			descontarDebitoHoje.setText("0");
		}
		
		auxCredito = 0;
		auxDebito = 0;
		auxCartao = 0;
		listaCartoes.forEach(x -> {
			auxCredito += x.getCredito();
			auxDebito += x.getDebito();
			auxCartao += x.getTotal();
		});
		auxCredito -= Double.parseDouble(descontarCreditoHoje.getText());
		auxDebito -= Double.parseDouble(descontarDebitoHoje.getText());
		auxCartao -= (Double.parseDouble(descontarCreditoHoje.getText()) + Double.parseDouble(descontarDebitoHoje.getText()));
		
		totalCredito.setText(String.format("%.2f", auxCredito));
		totalDebito.setText(String.format("%.2f", auxDebito));
		totalEmCartao.setText(String.format("%.2f", auxCartao));
		
		contarTotalDoDia(); //conta o total de dinheiro gerado no dia
	}
	
	/*
	 * esse método será chamado sempre que o contarTotaisCartoes() ou somar() for acionado
	 * esse método também passará os dados para a classe relatório
	 */
	private void contarTotalDoDia() {
		double total = Double.parseDouble(totalEmDinheiro.getText()) + Double.parseDouble(totalEmCartao.getText()); 
		totalDoDia.setText(String.format("%.2f", total));
		
		//essa parte do método serve para enviar as informações para a classe Relatorio
		atualizarQuantidadeDeNotas();
		if(descontarCreditoAmanha.getText().isEmpty()) {
			descontarCreditoAmanha.setText("0");
		}
		if(descontarDebitoAmanha.getText().isEmpty()) {
			descontarDebitoAmanha.setText("0");
		}
		Relatorio.toStringQuantidadeEmCaixa(listQuantidadeDeNotas, totalEmCaixa, descontarCreditoAmanha, descontarDebitoAmanha);
		Relatorio.valoresLoja(totalEmDinheiro, totalEmCartao, totalCredito, totalDebito, totalDoDia);
	}
	
	@FXML
	public void sair() {
		System.exit(0);
	}
	
	@FXML
	public void mostrarCalculadora() {
		ldt.mostraNovaJanela("/gui/ViewCalculadora.fxml", "Calculadora");
		outrosValores.setText((String) LogicaDeTelas.pdi);
	}
	
	@FXML
	public void procurarCaixaAnterior() {
		ldc.procurarCaixaAnterior(caixaAnterior);
		somar();
	}
	
	@FXML
	public void procurarDescontarHoje() {
		ldc.procurarDescontarHoje(descontarCreditoHoje, descontarDebitoHoje);
		contarTotaisCartoes();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Locale.setDefault(Locale.US);
		Constraints.setTextFieldInteger(cem, 3);
		Constraints.setTextFieldInteger(cinquenta, 3);
		Constraints.setTextFieldInteger(vinte, 3);
		Constraints.setTextFieldInteger(dez, 3);
		Constraints.setTextFieldInteger(cinco, 3);
		Constraints.setTextFieldInteger(dois, 3);
		Constraints.setTextFieldInteger(um, 3);
		Constraints.setTextFieldInteger(cinquentaCents, 3);
		Constraints.setTextFieldInteger(vinteECinco, 3);
		Constraints.setTextFieldInteger(dezCents, 3);
		Constraints.setTextFieldInteger(cincoCents, 3);
		
		Constraints.setTextFieldDouble(outrosValores, 8);
		Constraints.setTextFieldDouble(caixaAnterior, 8);
		
		Constraints.setTextFieldDouble(credito, 8);
		Constraints.setTextFieldDouble(debito, 8);
		Constraints.setTextFieldDouble(descontarCreditoAmanha, 8);
		Constraints.setTextFieldDouble(descontarDebitoAmanha, 8);
		Constraints.setTextFieldDouble(descontarCreditoHoje, 8);
		Constraints.setTextFieldDouble(descontarDebitoHoje, 8);
	}
}
