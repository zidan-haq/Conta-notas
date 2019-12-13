package model.entidades;

public class Cartao {
	private double credito;
	private double debito;
	private double total;
	
	public Cartao(double credito, double debito) {
		this.credito = credito;
		this.debito = debito;
		total = credito + debito;
	}
	
	public double getCredito() {
		return credito;
	}

	public double getDebito() {
		return debito;
	}
	
	public double getTotal() {
		return total;
	}
	
	@Override
	public String toString() {
		return "Crédito = " + credito + "\nDébito = " + debito + "\nTotal = " + total;
	}
}
