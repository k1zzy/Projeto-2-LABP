package project;

/**
 * Classe que representa um numero complexo na forma algebrica e trignometrica.
 * 
 * @author Rodrigo Afonso (61839)
 * @version 1.0
 */
public final class ComplexoConcreto implements Complexo {
	final double re;
	final double im;
	final double norma;
	final double theta;
	
	public ComplexoConcreto(double re, double im) {
		this.re = re;
		this.im = im;
		this.norma = Math.sqrt(re * re + im * im); // raiz quadrada de re^2 + im^2 = norma
		this.theta = Math.atan2(im, re); // arctan(im / re) entre -pi e pi em radianos
	}
	
	@Override
	public double re() {
		return re;
	}

	@Override
	public double im() {
		return im;
	}

	@Override
	public double rho() {
		return norma;
	}

	@Override
	public double theta() {
		return theta;
	}
	
	@Override
	public double norma() {
		return norma;
	}

	@Override
	public Complexo soma(Complexo outro) {
		// (a + bi) + (c + di) = (a + c) + (b + d)i (so juntar as partes reais e imaginarias respectivamente)
		return new ComplexoConcreto(re + outro.re(), im + outro.im());
	}

	@Override
	public Complexo produto(Complexo outro) {
		// (a + bi) * (c + di) = (ac - bd) + (ad + bc)i (distributiva e i^2 = -1)
		return new ComplexoConcreto(re * outro.re() - im * outro.im(), re * outro.im() + im * outro.re());
	}
	
	// TODO mesma coisa que em baixo
	@Override
	public Complexo potencia(double x) {
		// formula de Moivre: norma^x * (cos(x * theta) + i * sin(x * theta))
		return new ComplexoConcreto(Math.pow(norma, x) * Math.cos(theta * x),
									Math.pow(norma, x) * Math.sin(theta * x));
	}
	
	//TODO pode dar mal na subtracao dos tetas tenho que ver se fica sempre no intervalo certo, mas acho que nao ha problema visto
	// que irao entrar como angulos no sin e cos e depois no novo complexo voltam a estar no intervalo certo
	@Override
	public Complexo quociente(Complexo outro) {
		//formula da divisao de complexos na forma trigonometrica: dividir as normas e subtrair os tetas
		return new ComplexoConcreto((norma / outro.norma()) * Math.cos(theta - outro.theta()),
									(norma / outro.norma()) * Math.sin(theta - outro.theta())); 
	}

	@Override
	public Complexo conjugado() {
		return new ComplexoConcreto(re, -im);
	}

	@Override
	public boolean ehReal() {
		// se a parte imaginaria for menor 0 (entre o erro) e a parte real for maior que 0, o complexo eh real
		return re >= Complexo.getErro() && im <= Complexo.getErro() && im >= -Complexo.getErro();
	}

	@Override
	public boolean ehReal(double erro) {
		// se a parte imaginaria for menor 0 (entre o erro) e a parte real for maior que 0, o complexo eh real
		return re >= erro && im <= erro && im >= -erro;
	}

	@Override
	public boolean ehZero() {
		return re <= Complexo.getErro() && re >= -Complexo.getErro() && // se a parte real e imaginaria forem menores que o erro
			   im <= Complexo.getErro() && im >= -Complexo.getErro();   // o numero eh zero
	}

	@Override
	public boolean ehZero(double erro) {
		return re <= erro && re >= -erro && // se a parte real e imaginaria forem menores que o erro
			   im <= erro && im >= -erro;   // o numero eh zero
	}

	@Override
	public boolean ehIgual(Complexo outro) {
		return re - outro.re() <= Complexo.getErro() && re - outro.re() >= -Complexo.getErro() && // se a parte real e imaginaria forem iguais (dentro do erro)
			   im - outro.im() <= Complexo.getErro() && im - outro.im() >= -Complexo.getErro(); // os numeros sao iguais
	}

	@Override
	public boolean ehIgual(Complexo outro, double erro) {
		return re - outro.re() <= erro && re - outro.re() >= -erro && // se a parte real e imaginaria forem iguais (dentro do erro)
			   im - outro.im() <= erro && im - outro.im() >= -erro; // os numeros sao iguais
	}

	@Override
	public String toString() {		
		if (ehZero()) // quando eh 0
			return "0.0"; // imprime 0.0 pq eh assim que ta nos testes
		else if (ehReal()) // se for real
			return re >= Complexo.getErro() ? Double.toString(re): "- " + -re; // imprime so a parte real
		else if (ehImaginario()) // se for imaginario
			return im >= Complexo.getErro() ? im + "i": "- " + -im; // imprime so a parte imaginaria
		else if (re >= Complexo.getErro()) // se a parte real for positiva
			return im >= Complexo.getErro() ? re + " + " + im + "i" : re + " - " + -im + "i"; // a parte imaginaria ou eh positiva ou negativa
		else // se a parte real for negativa
			return im >= Complexo.getErro() ? "- " + -re + " + " + im + "i" : "- " + -re + " - " + -im + "i" ; // a parte imaginaria sera negativa
	}
	
	@Override
	public String repTrigonometrica() {
		return !ehZero() ? norma + " cis (" + theta + ")" : "0.0"; // norma * (cis theta)
	}

	public Complexo simetrico() {
        return new ComplexoConcreto(-re, -im);
	}
	
	public Complexo subtraccao(Complexo outro) {
		return new ComplexoConcreto(re - outro.re(), im - outro.im());
	}
	
	private boolean ehImaginario() {
		// se a parte real for menor 0 (entre o erro) e a parte imaginaria for maior que 0, o complexo eh imaginario
		return im >= Complexo.getErro() && re <= Complexo.getErro() && re >= -Complexo.getErro();
	}
}
