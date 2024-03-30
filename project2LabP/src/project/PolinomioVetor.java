package project;

import java.util.Arrays;

/**
 * Classe que representa um polinomio de numeros complexos num vetor.
 * 
 * @author Rodrigo Afonso (61839)
 * @version 1.0
 */
public final class PolinomioVetor implements Polinomio {
	final int grau;
	final Complexo[] coefs;

	public PolinomioVetor(Complexo[] coefs) {
		this.coefs = coefs;
		this.grau = coefs.length - 1; //o grau do polinomio sera sempre o tamanho do vetor - 1
 	}

	@Override
	public int grau() {
		return grau;
	}

	@Override
	public Complexo coef(int i) {
		return coefs[i]; //retorna o coeficiente de indice i ou seja o coeficiente de grau i
	}

	@Override
	public boolean ehZero() {
		for (Complexo c : coefs) {
			if (!c.ehZero()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean ehZero(double erro) {
		for (Complexo c : coefs) {
			if (!c.ehZero(erro)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean ehConstante() {
		return grau == 0;
	}

	@Override
	public Polinomio escalar(Complexo f) {
		Complexo[] coefsEscalar = new Complexo[this.coefs.length]; //vetor que vai conter os coeficientes do polinomio multiplicados pelo escalar
		for (int i = 0; i < coefs.length; i++) {
			coefsEscalar[i] = this.coefs[i].produto(f); //multiplica cada coeficiente pelo escalar e coloca-lo no vetor coefsEscalar
		}
		return new PolinomioVetor(coefsEscalar); //retorna um novo polinomio com os coeficientes multiplicados pelo escalar
	}

	@Override
	public Polinomio simetrico() {
		Complexo[] coefsSimetrico = new Complexo[this.coefs.length]; // vetor que vai conter os coeficientes do polinomio simetrico
		for (int i = 0; i < coefs.length; i++) {
			coefsSimetrico[i] = ((ComplexoConcreto) this.coefs[i]).simetrico(); // coloca no vetor coefsSimetrico os coeficientes simetricos
		}
		return new PolinomioVetor(coefsSimetrico); // retorna um novo polinomio com os coeficientes simetricos
	}
	
	@Override
	public Polinomio soma(Polinomio p) {
		char grauMaior = grau() >= p.grau() ? 'a' : 'p'; // 'a' = atual, 'p' = p
   		Complexo[] coefsSoma = new Complexo[Math.max(grau(), p.grau()) + 1]; // cria um vetor com o tamanho do polinomio com maior grau
		
		for (int i = 0; i <= Math.max(grau(), p.grau()); i++) { // percorre ate a maior posicao do polinomio com maior grau
			if (i <= grau() && i <= p.grau()) // se o i for igual ou menor que o grau
				coefsSoma[i] = this.coef(i).soma(p.coef(i)); // fazer normalmente a soma
			else { 
				switch (grauMaior) { // caso contrario ir verificar se
				case 'a': // o atual eh o de maior grau
					coefsSoma[i] = this.coef(i); // e colocar esse na posicao i
					break;
				case 'p': // ou se o p eh o de maior grau
					coefsSoma[i] = p.coef(i); // e colocar esse na posicao i
					break;
				}
			}
		}
		return new PolinomioVetor(coefsSoma);
	}
	
	// pode acontecer no caso de se subtrair dois monomios iguais e esse ser o ultimo ficar com o ultimo monomio a 0 o que nao pode acontecer
	@Override
	public Polinomio subtraccao(Polinomio p) {
		char grauMaior = grau() >= p.grau() ? 'a' : 'p'; // 'a' = atual, 'p' = p
   		Complexo[] coefsSub = new Complexo[Math.max(grau(), p.grau()) + 1]; // cria um vetor com o tamanho do polinomio com maior grau
		
		for (int i = 0; i <= Math.max(grau(), p.grau()); i++) { // percorre ate a maior posicao do polinomio com maior grau
			if (i <= grau() && i <= p.grau()) // se o i for igual ou menor que o grau
				coefsSub[i] = ((ComplexoConcreto) this.coef(i)).subtraccao(p.coef(i)); // fazer normalmente a subtraccao
			else { 
				switch (grauMaior) { // caso contrario ir verificar se
				case 'a': // o atual eh o de maior grau
					coefsSub[i] = this.coef(i); // e colocar esse na posicao i
					break;
				case 'p': // ou se o p eh o de maior grau
					coefsSub[i] = ((ComplexoConcreto) p.coef(i)).simetrico(); // e colocar o seu simetrico na posicao i
					break;
				}
			}
		}
		return new PolinomioVetor(coefsSub);
	}
	
	@Override
	public Polinomio produto(Polinomio p) {
   		Complexo[] coefsProd = new Complexo[grau() + p.grau() + 1]; // cria um vetor com tamanho da soma dos graus dos dois polimonios +1
   		boolean ehPrimeiraPos = true; // se estamos na primeira pos do polinomio
   		int skippedPos = 0; // n de vezes que apagamos uma pos da array por ser 0 na primeira pos
		for (int i = 0; i <= this.grau(); i++) { // percorre ate a maior posicao do polinomio atual
			for (int j = 0 ; j <= p.grau(); j++) {
				// se for a primeira vez a passar na array coefsProd estara a null, encher de complexos 0
				if (coefsProd[i + j - skippedPos] == null)
					coefsProd[i + j - skippedPos] = new ComplexoConcreto(0, 0);
				coefsProd[i + j - skippedPos] = coefsProd[i + j - skippedPos].soma(this.coef(i).produto(p.coef(j)));
				// se o produto for 0, se estiver na primeira pos da array e se nao for de grau 0
				if (coefsProd[i + j - skippedPos].ehZero() && ehPrimeiraPos && coefsProd.length != 1) {
					reverseArray(coefsProd); // reverte a array
					coefsProd = Arrays.copyOf(coefsProd, coefsProd.length - 1); // apaga a ultima pos
					skippedPos++; // incrementra o numero de pos apagadas
				} else
					ehPrimeiraPos = false;
			}
		}
		return new PolinomioVetor(coefsProd);
	}

	@Override
	public Complexo avalia(Complexo x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Polinomio derivada() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Polinomio copia() {
		return new PolinomioVetor(this.coefs);
	}

	@Override
	public boolean ehIgual(Polinomio p) {
		for (int i = 0; i < coefs.length; i++) {
			if (!coefs[i].ehIgual(p.coef(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean ehIgual(Polinomio p, double erro) {
		for (int i = 0; i < coefs.length; i++) {
			if (!coefs[i].ehIgual(p.coef(i), erro)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int grau = grau() + 1;
		for (Complexo c : coefs) {
			if (c == null || c.ehZero())
				sb.append(0.0);
			else {
				sb.append(c.repTrigonometrica());
				grau--;
			}
			if (grau != 0)
				sb.append(" x^" + grau + " + ");
		}
		return sb.toString();
	}
	
	private void reverseArray(Complexo[] array) {
	    for (int i = 0; i < array.length / 2; i++) {
	        Complexo temp = array[i];
	        array[i] = array[array.length - 1 - i];
	        array[array.length - 1 - i] = temp;
	    }
	}

}
