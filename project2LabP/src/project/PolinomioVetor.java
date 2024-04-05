package project;

import java.util.Arrays;

/**
 * Classe que representa um polinomio de numeros complexos num vetor.
 * 
 * @author Rodrigo Afonso (61839)
 * @version 1.0
 */
public final class PolinomioVetor implements Polinomio {
	private final int grau;
	private final Complexo[] coefs;

	/**
	 * Construtor de um polinomio apartir de vetores.
	 * O(n)
	 * @param coefs vetor de coeficientes do tipo Complexo
	 */
	public PolinomioVetor(Complexo[] coefs) {
		this.coefs = Arrays.copyOf(coefs, coefs.length);
		this.grau = coefs.length - 1; //o grau do polinomio sera sempre o tamanho do vetor - 1
 	}

	/**
	 * O(1)
	 */
	@Override
	public int grau() {
		return grau;
	}

	/**
	 * O(1)
	 */
	@Override
	public Complexo coef(int i) {
		return coefs[i]; //retorna o coeficiente de indice i ou seja o coeficiente de grau i
	}

	/**
	 * O(1)
	 */
	@Override
	public boolean ehZero() {
		for (Complexo c : coefs) {
			if (!c.ehZero()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * O(n)
	 */
	@Override
	public boolean ehZero(double erro) {
		for (Complexo c : coefs) {
			if (!c.ehZero(erro)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * O(1)
	 */
	@Override
	public boolean ehConstante() {
		return grau == 0;
	}

	/**
	 * O(n)
	 */
	@Override
	public Polinomio escalar(Complexo f) {
		Complexo[] coefsEscalar = new Complexo[coefs.length]; //vetor que vai conter os coeficientes do polinomio multiplicados pelo escalar
		for (int i = 0; i < coefs.length; i++) {
			coefsEscalar[i] = this.coef(i).produto(f); //multiplica cada coeficiente pelo escalar e coloca-lo no vetor coefsEscalar
		}
		return new PolinomioVetor(coefsEscalar); //retorna um novo polinomio com os coeficientes multiplicados pelo escalar
	}

	/**
	 * O(n)
	 */
	@Override
	public Polinomio simetrico() {
		Complexo[] coefsSimetrico = new Complexo[this.coefs.length]; // vetor que vai conter os coeficientes do polinomio simetrico
		for (int i = 0; i < coefs.length; i++) {
			coefsSimetrico[i] = ((ComplexoConcreto) this.coefs[i]).simetrico(); // coloca no vetor coefsSimetrico os coeficientes simetricos
		}
		return new PolinomioVetor(coefsSimetrico); // retorna um novo polinomio com os coeficientes simetricos
	}
	
	/**
	 * O(n)
	 */
	@Override
	public Polinomio soma(Polinomio p) {
		char grauMaior = grau() >= p.grau() ? 'a' : 'p'; // 'a' = atual, 'p' = p
   		Complexo[] coefsSoma = new Complexo[Math.max(grau(), p.grau()) + 1]; // cria um vetor com o tamanho do polinomio com maior grau
   		boolean ehPrimeiraPos = true; // se estamos na primeira pos do polinomio
   		int skippedPos = 0; // n de vezes que apagamos uma pos da array por ser 0 na primeira pos
   		
		for (int i = Math.max(grau(), p.grau()); i >= 0; i--) { // percorre ate a maior posicao do polinomio com maior grau
			if (i <= grau() && i <= p.grau()) { // se o i for igual ou menor que o grau
				coefsSoma[i] = this.coef(i).soma(p.coef(i)); // fazer normalmente a soma
				if((coefsSoma[i].ehZero() || coefsSoma[i] == null)  && ehPrimeiraPos)
	                skippedPos++; // incrementra o numero de pos apagadas
	            else
	                ehPrimeiraPos = false;
			} else { 
				switch (grauMaior) { // caso contrario ir verificar se
				case 'a': // o atual eh o de maior grau
					coefsSoma[i] = this.coef(i); // e colocar esse na posicao i
					if((coefsSoma[i].ehZero() || coefsSoma[i] == null)  && ehPrimeiraPos)
		                skippedPos++; // incrementra o numero de pos apagadas
		            else
		                ehPrimeiraPos = false;
					break;
				case 'p': // ou se o p eh o de maior grau
					coefsSoma[i] = p.coef(i); // e colocar esse na posicao i
					if((coefsSoma[i].ehZero() || coefsSoma[i] == null)  && ehPrimeiraPos)
		                skippedPos++; // incrementra o numero de pos apagadas
		            else
		                ehPrimeiraPos = false;
					break;
				}
			}
		}
		if (skippedPos > 0)
			coefsSoma = Arrays.copyOf(coefsSoma, coefsSoma.length-skippedPos+1); // apagar as primeiras pos da array que sao 0
		return new PolinomioVetor(coefsSoma);
	}
	
	// TODO pode acontecer no caso de se subtrair dois monomios iguais e esse ser o ultimo ficar com o ultimo monomio a 0 o que nao pode acontecer
	// corrigido (provavelmente horivel mas funciona)
	/**
	 * O(n)
	 */
	@Override
	public Polinomio subtraccao(Polinomio p) {
		char grauMaior = grau() >= p.grau() ? 'a' : 'p'; // 'a' = atual, 'p' = p
   		Complexo[] coefsSub = new Complexo[Math.max(grau(), p.grau()) + 1]; // cria um vetor com o tamanho do polinomio com maior grau
   		boolean ehPrimeiraPos = true; // se estamos na primeira pos do polinomio
   		int skippedPos = 0; // n de vezes que apagamos uma pos da array por ser 0 na primeira pos
   		
		for (int i = Math.max(grau(), p.grau()); i >= 0; i--) { // percorre desde o maior grau ate 0
			if (i <= grau() && i <= p.grau()) { // se o i for igual ou menor que os dois graus
				coefsSub[i] = ((ComplexoConcreto) this.coef(i)).subtraccao(p.coef(i)); // fazer normalmente a subtraccao
				// se a sub for 0 e se estiver na primeira pos da array
				if((coefsSub[i].ehZero() || coefsSub[i] == null)  && ehPrimeiraPos)
	                skippedPos++; // incrementra o numero de pos apagadas
	            else
	                ehPrimeiraPos = false;
			} else { 
				switch (grauMaior) { // caso contrario ir verificar se
				case 'a': // o atual eh o de maior grau
					coefsSub[i] = this.coef(i); // e colocar esse na posicao i
					if((coefsSub[i].ehZero() || coefsSub[i] == null)  && ehPrimeiraPos)
		                skippedPos++; // incrementra o numero de pos apagadas
		            else
		                ehPrimeiraPos = false;
					break;
				case 'p': // ou se o p eh o de maior grau
					coefsSub[i] = ((ComplexoConcreto) p.coef(i)).simetrico(); // e colocar o seu simetrico na posicao i
					if((coefsSub[i].ehZero() || coefsSub[i] == null)  && ehPrimeiraPos)
		                skippedPos++; // incrementra o numero de pos apagadas
					else
		                ehPrimeiraPos = false;
					break;
				}
			}
		}
		if (skippedPos > 0)
			coefsSub = Arrays.copyOf(coefsSub, coefsSub.length-skippedPos+1); // apagar as primeiras pos da array que sao 0
		return new PolinomioVetor(coefsSub);
	}
	
	/**
	 * O(n^2)
	 */
	@Override
	public Polinomio produto(Polinomio p) {
   		Complexo[] coefsProd = new Complexo[grau() + p.grau() + 1]; // cria um vetor com tamanho da soma dos graus dos dois polimonios +1
   		boolean ehPrimeiraPos = true; // se estamos na primeira pos do polinomio
   		int skippedPos = 0; // n de vezes que apagamos uma pos da array por ser 0 na primeira pos
		for (int i = this.grau(); i >= 0; i--) { // percorre ate a maior posicao do polinomio atual
			for (int j = p.grau() ; j >= 0; j--) {
				// se for a primeira vez a passar na array coefsProd estara a null, encher de complexos 0
				if (coefsProd[i + j] == null)
					coefsProd[i + j] = new ComplexoConcreto(0, 0);
				coefsProd[i + j] = coefsProd[i + j].soma(this.coef(i).produto(p.coef(j)));
				// se o produto for 0 e se estiver na primeira pos da array
				if ((coefsProd[i + j].ehZero() || coefsProd[i + j] == null)  && ehPrimeiraPos) {
					skippedPos++; // incrementra o numero de pos apagadas
				} else
					ehPrimeiraPos = false;
			}
		}
		if (skippedPos > 0)
			coefsProd = Arrays.copyOf(coefsProd, coefsProd.length-skippedPos+1); // apagar as primeiras pos da array que sao 0
		return new PolinomioVetor(coefsProd);
	}

	/**
	 * O(n)
	 */
	@Override
	public Complexo avalia(Complexo x) {
		Complexo sol = new ComplexoConcreto(0, 0);;
		for (int i = this.grau(); i >= 0; i--) {
			// o que esta la dentro + o produto do coef com o X^grau correspondente)
			sol = sol.soma(this.coef(i).produto(x.potencia(i)));
		}
		return sol;
	}

	/**
	 * O(n)
	 */
	@Override
	public Polinomio derivada() {
		Complexo[] der = Arrays.copyOf(coefs, coefs.length); // copia do vetor de coeficientes
		der[0] = new ComplexoConcreto(0, 0); // derivada de uma constante = 0
		
		if (der.length == 1)
			return new PolinomioVetor(der);
		der = arrayDelFirstPos(der); // apagar a primeira pos
		
		for(int i = this.grau() - 1; i >= 0; i--) {
			der[i] = der[i].produto(new ComplexoConcreto(i + 1, 0)); // derivada de x^n = n*x^(n-1)
		}
		return new PolinomioVetor(der);
	}

	/**
	 * O(1)
	 */
	@Override
	public Polinomio copia() {
		return new PolinomioVetor(this.coefs);
	}

	/**
	 * O(n)
	 */
	@Override
	public boolean ehIgual(Polinomio p) {
		for (int i = 0; i < coefs.length; i++) {
			if (!coefs[i].ehIgual(p.coef(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * O(n)
	 */	
	@Override
	public boolean ehIgual(Polinomio p, double erro) {
		for (int i = 0; i < coefs.length; i++) {
			if (!coefs[i].ehIgual(p.coef(i), erro)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * O(n)
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int grau = grau() + 1;
		if  ((grau() == 0 && coef(0).ehZero()) || coefs == null) // se o polinomio for 0 ou null
            return "0.0"; // retorna 0.0
		for (int i = grau() ; i >= 0; i--) {
			if (!coef(i).ehZero()) { // se o coeficiente for diferente de 0
				sb.append(coef(i).repTrigonometrica()); 
				grau--;
				if (grau > 1) // se o grau for maior que 1
					sb.append(" x^" + grau + " + ");
				else if (grau == 1)
					sb.append(" x + ");
			} else
				grau--;
		}
		return sb.toString();
	}

	/**
	 * Apaga a primeira posicao de um vetor de complexos.
	 * O(n)
	 * @param array vetor de complexos
	 * @requires array != null
	 * @return vetor sem a primeira posicao
	 */
	// 
	private Complexo[] arrayDelFirstPos(Complexo[] array) {
		Complexo[] delArray = new Complexo[array.length - 1];
		for (int i = 0; i < array.length-1; i++) {
			delArray[i] = array[i+1];
		}
		return delArray;
	}
}
