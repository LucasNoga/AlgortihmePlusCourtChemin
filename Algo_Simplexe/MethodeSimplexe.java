
package test;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**Classe qui recupere la matrice courante et applique la méthode du simplexe*/
public class MethodeSimplexe {
	
	private static RoundingMode ROUND_EVEN = RoundingMode.HALF_EVEN;
	private static int ROUND_SCALE = 15;
	
	//Permet de preciser le nombre de decimale
	private static MathContext PRECISION   = new MathContext(5);
	
	//liste les matrices en fonction des etape
	private List<MatriceSimplexe> listeMatrice;

	/**Appelle la methode du simplexe avec les données (fonctioneconomique + matrice contentan les contraintes*/
	public MethodeSimplexe(MatriceSimplexe matrice) {
		this.listeMatrice = new ArrayList<MatriceSimplexe>();
		this.listeMatrice.add(matrice);
	}
	
	//retourne la colonne du pivot
	private int colonnePivot() {
		int colPivot = 0;
		final BigDecimal[] fonctionEco = getCurrentStep().getEcoFunction();

		for (int i = 0; i < fonctionEco.length - 1; i++) {
			if (estPlusPetitQue(fonctionEco[colPivot], fonctionEco[i]))
				colPivot = i;
		}
		return colPivot;
	}

	//recupere la matrice courante
	public MatriceSimplexe getEtape(int step) {
		return listeMatrice.get(step);
	}

	//Permet de tester si la solution est optimale si c'est pas le cas on recherche un nouveau pivot sinon on affiche les solution
	public void testOptimisation() {
		affichageTableau();

		while (!estSolutionOptimale()) {
			etapeSuivante();
			affichageTableau();
		}
		affichageSolution();
	}

	//permet de tester si la solution est optimale
	private boolean estSolutionOptimale() {
		final BigDecimal[] ecoFunction = getCurrentStep().getEcoFunction();

		for (int i = 0; i < ecoFunction.length - 1; i++) {
			if (plusGrandQue(ecoFunction[i], BigDecimal.ZERO))
				return false;
		}
		return true;
	}

	//permet de modifier les lignes de la matrice par rapport a la ligne du pivot
	private void etapeSuivante() {
		//recupere la colonne du pivot
		final int colPivot = colonnePivot();
		//recupere la ligne du pivot
		final int lignePivot = lignePivot(colPivot);

		MatriceSimplexe currentStep = getCurrentStep();
		MatriceSimplexe nextStep = new MatriceSimplexe(getCurrentStep());

		BigDecimal coefficient = BigDecimal.ONE
				.divide(currentStep.getConstraints()[lignePivot][colPivot],
						ROUND_SCALE, ROUND_EVEN);

		for (int i = 0; i < nextStep.getEcoFunction().length; i++) {
			nextStep.getEcoFunction()[i] = nextStep.getEcoFunction()[i].subtract(
					coefficient.multiply(currentStep.getEcoFunction()[colPivot], PRECISION)
					.multiply(currentStep.getConstraints()[lignePivot][i], PRECISION), PRECISION);
		}

		for (int i = 0; i < currentStep.getConstraints().length; i++) {
			for (int j = 0; j < currentStep.getConstraints()[0].length; j++) {
				if (i != lignePivot)
					nextStep.getConstraints()[i][j] = nextStep.getConstraints()[i][j].subtract(
							coefficient.multiply(currentStep.getConstraints()[i][colPivot], PRECISION)
							.multiply(currentStep.getConstraints()[lignePivot][j], PRECISION),
							PRECISION);
				else
					nextStep.getConstraints()[i][j] = nextStep.getConstraints()[i][j].divide(currentStep.getConstraints()[lignePivot][colPivot],ROUND_SCALE, ROUND_EVEN);
			}
		}

		if (colPivot < currentStep.getNbrVariables())
			nextStep.getSolutionValues()[colPivot] = lignePivot;

		//ajoute la nouvelle matrice
		this.listeMatrice.add(nextStep);

	}
	
	/**Recupere le plus petit coefficient pour recuperer le pivot*/
	private int plusPetitCoefficient(BigDecimal[][] contraintes, int colPivot, int lignePivot) {
		final int  nbLines = contraintes.length;
		BigDecimal newRatio, oldRatio;

		for (int i = 0; i < nbLines; i++) {
			if(estDifferentDeZero(contraintes[i][colPivot])) {
				newRatio = getRapport(contraintes, i, colPivot);
				oldRatio = getRapport(contraintes, lignePivot, colPivot);
				if (estPlusPetitQue(newRatio, oldRatio) && plusGrandQue(newRatio, BigDecimal.ZERO))
					lignePivot = i;
			}
		}
		return lignePivot;
	}

	/**Methode pour recuperer la ligne du pivot*/
	private int lignePivot(int colPivot) {
		int lignePivot;
		final BigDecimal[][] contraintes = getCurrentStep().getConstraints();

		lignePivot = initilaliseLignePivot(contraintes, colPivot);

		//appelle la methode pour tester le minimum de chaque rapport entre la colonne et le second membre
		return plusPetitCoefficient(contraintes, colPivot, lignePivot);
	}

	/**Teste si au moins la valeur selectionne est strictement superieur a zero*/
	private int initilaliseLignePivot(BigDecimal[][] contraintes, int colPivot) {
		int lignePivot = 0;
		
		//si le coefficient est inferieur ou egale a 0 on passe au suivant
		while (inferieurOuEgal(contraintes[lignePivot][colPivot], BigDecimal.ZERO))
			lignePivot++;
		return lignePivot;
	}
	
	/**Retourne vrai si variable est inferieru ou egale a variableAComparer*/
	private boolean inferieurOuEgal(BigDecimal variable, BigDecimal variableAComparer) {
		return variable.compareTo(variableAComparer) == -1
				|| variable.compareTo(variableAComparer) == 0;
	}
	
	private boolean plusGrandQue(BigDecimal value, BigDecimal valueToCompare) {
		return value.compareTo(valueToCompare) == 1;
	}

	/**recupere le rapport entre le pivot et le coefficient de la variable*/
	private BigDecimal getRapport(BigDecimal[][] contraintes, int ligne, int colonne) {
		return contraintes[ligne][contraintes[0].length - 1].divide(contraintes[ligne][colonne], ROUND_SCALE, ROUND_EVEN);
	}
	
	/**retourne 1 si la variable est different de 0*/
	private boolean estDifferentDeZero(BigDecimal variable) {
		return variable.compareTo(BigDecimal.ZERO) != 0;
	}
	
	/**retourne 1 si variable est plus petit que variableAComparer*/
	private boolean estPlusPetitQue(BigDecimal variable, BigDecimal variableAComparer) {
		return variable.compareTo(variableAComparer) == -1;
	}

	private MatriceSimplexe getCurrentStep(){
		return listeMatrice.get(listeMatrice.size() - 1);
	}

	//methode pour recuperer la solution pour l'etape en cours
	public String getSolution() {
		return getCurrentStep().getSolution();
	}

	//methode qui affiche le tableau de l'etape
	private void affichageTableau() {
		String str;
		if (etapeCourante() == 0)
			str = "Tableau 0 : ";
		else
			str = "Tableau numéro " + etapeCourante() + " : ";

		System.out.println(str);
		//affiche la matrice courante
		getCurrentStep().affichageTableau();
	}

	private int etapeCourante() {
		return listeMatrice.size() - 1;
	}

	private void affichageSolution() {
		getCurrentStep().affichageSolution();
	}
}
