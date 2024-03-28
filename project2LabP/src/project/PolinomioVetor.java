package project;

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
	
	// TODO METER TUDO NUM SO LOOP E DEIXAR DE SER BURRO
	@Override
	public Polinomio soma(Polinomio p) {
		Complexo[] coefsSoma = new Complexo[Math.max(this.grau(), p.grau())]; // cria um vetor com o tamanho do polinomio com maior grau
		if (p.grau() != this.grau()) { // se os polinomios tiverem graus diferentes
			for (int i = 0; i < Math.min(this.grau(), p.grau()); i++) { // percorre o vetor com o tamanho do polinomio com menor grau
				coefsSoma[i] = this.coef(i).soma(p.coef(i));
			}
			
			if (this.grau() > p.grau()) { // e depois dependendo do polinomio com maior grau
				for (int i = p.grau() + 1; i <= this.grau(); i++) { // coloca os complexos que sobram no vetor
					coefsSoma[i] = this.coef(i);
				}
			} else {
				for (int i = this.grau() + 1; i <= p.grau(); i++) {
					coefsSoma[i] = p.coef(i);
				}
			}
		} else { // se os polinomios tiverem o mesmo grau
			for (int i = 0; i < this.grau(); i++) { // apenas soma cada membro dos polinomios
				coefsSoma[i] = this.coef(i).soma(p.coef(i));
			}
		}
		return new PolinomioVetor(coefsSoma);
	}

	@Override
	public Polinomio subtraccao(Polinomio p) {
		Complexo[] coefsSub = new Complexo[Math.max(this.grau(), p.grau()) + 1]; // cria um vetor com o tamanho do polinomio com maior grau
		if (p.grau() != this.grau()) { // se os polinomios tiverem graus diferentes
			for (int i = 0; i < Math.min(this.grau(), p.grau()); i++) { // percorre o vetor com o tamanho do polinomio com menor grau
				coefsSub[i] = ((ComplexoConcreto) this.coef(i)).subtraccao(p.coef(i));
			}
			
			if (this.grau() > p.grau()) { // e depois dependendo do polinomio com maior grau
				for (int i = p.grau() + 1; i <= this.grau(); i++) { // coloca os complexos que sobram no vetor
					coefsSub[i] = ((ComplexoConcreto) p.coef(i)).simetrico();
				}
			} else {
				for (int i = this.grau() + 1; i <= p.grau(); i++) {
					coefsSub[i] = ((ComplexoConcreto) p.coef(i)).simetrico();
				}
			}
		} else { // se os polinomios tiverem o mesmo grau
			for (int i = 0; i < this.grau(); i++) { // apenas soma cada membro dos polinomios
				coefsSub[i] = ((ComplexoConcreto) this.coef(i)).subtraccao(p.coef(i));
			}
		}
		return new PolinomioVetor(coefsSub);
	}

	@Override
	public Polinomio produto(Polinomio p) {
		Complexo[] coefsProd = new Complexo[this.grau() + p.grau() + 1]; // cria um vetor com o tamanho da soma dos graus dos polinomios
		if (p.grau() != this.grau()) { // se os polinomios tiverem graus diferentes
			for (int i = 0; i < Math.min(this.grau(), p.grau()); i++) { // percorre o vetor com o tamanho do polinomio com menor grau
				coefsProd[i] = this.coef(i).produto(p.coef(i));
			}
			
			if (this.grau() > p.grau()) { // e depois dependendo do polinomio com maior grau
				for (int i = p.grau() + 1; i <= this.grau(); i++) { // coloca os complexos que sobram no vetor
					coefsProd[i] = new ComplexoConcreto(0, 0);
				}
			} else {
				for (int i = this.grau() + 1; i <= p.grau(); i++) {
					coefsProd[i] = new ComplexoConcreto(0, 0);
				}
			}
		} else { // se os polinomios tiverem o mesmo grau
			for (int i = 0; i < this.grau(); i++) { // apenas soma cada membro dos polinomios
				coefsProd[i] = this.coef(i).produto(p.coef(i));
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

}
