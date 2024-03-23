package tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({  
				PolinomioTestesAvalia.class,
				PolinomioTestesDerivada.class,
				PolinomioTestesEhConstante.class,
				PolinomioTestesEhIgual.class,
				PolinomioTestesEhZero.class,
				PolinomioTestesEscalar.class,
				PolinomioTestesGrau.class,
				PolinomioTestesSimetrico.class,
				PolinomioTestesSoma.class,
				PolinomioTestesSubtraccao.class,
				PolinomioTestesToString.class
				})

public class AllTestsPolinomios {

}