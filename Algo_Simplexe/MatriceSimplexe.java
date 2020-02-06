package test;


import java.math.BigDecimal;
import java.math.RoundingMode;

/**Classe qui affiche les matrices a chaque etape du simplexe*/
public class MatriceSimplexe {

  //Ensemble des solutions
  private int[] solutions;
  
  //tableau des coefficients des variables de la fonction economique
  private BigDecimal[] fonctionEconomique;
  
  //matrice des coefficients des variables des contraintes
  private BigDecimal[][] contraintes;
  
  //Recupere le nombre de variables totales pour la matrice
  private int nombreVariables;

  //permet de recreer une matrice avec une fonction eco, des contraintes et le nombre de variables de base
  public MatriceSimplexe(BigDecimal[] fonctionEconomique, BigDecimal[][] contraintes, int nombreVariables) {
    this.nombreVariables = nombreVariables;
    contraintes(contraintes);
    fonctionEconomique(fonctionEconomique);
    initialisationSolution();
  }
  
  /**Constructeur pour recreer la matrice de l'etpae suivantes*/
  public MatriceSimplexe(MatriceSimplexe matrice) {
	    this.fonctionEconomique = matrice.getEcoFunction().clone();
	    this.solutions = matrice.getSolutionValues().clone();
	    this.nombreVariables = matrice.getNbrVariables();
	    this.contraintes = new BigDecimal[matrice.getConstraints().length][];

	    for (int i = 0; i < this.contraintes.length; i++)
	      this.contraintes[i] = matrice.getConstraints()[i].clone();
	  }

  //permet de remtre les solutions dans la matrice
  private void initialisationSolution() {
    solutions = new int[nombreVariables];
    for (int i = 0; i < nombreVariables; i++)
      solutions[i] = -1;
  }

  //Permet de creer la matrice contenant les coefficients saisies par l'utilisateur
  private void contraintes(BigDecimal[][] matriceContraintes) {
    final int nombreContraintes = matriceContraintes.length;
    final int tailleContraintes = matriceContraintes[0].length + nombreContraintes;
    final int nombreVariables = matriceContraintes[0].length - 1;
    contraintes = new BigDecimal[nombreContraintes][tailleContraintes];

    for (int i = 0; i < nombreContraintes; i++) {
      for (int j = 0; j < tailleContraintes; j++) {
        if (j >= nombreVariables)
          contraintes[i][j] = isInDiagonal(nombreVariables, i, j) ? BigDecimal.ONE : BigDecimal.ZERO;
        else
          contraintes[i][j] = matriceContraintes[i][j];
      }
      contraintes[i][tailleContraintes() - 1] =
          matriceContraintes[i][matriceContraintes[i].length - 1];
    }
  }

  //Methode pour les variables dans la base ei
  private boolean isInDiagonal(int nbrVariables, int currentLine, int currentColumn) {
    return currentColumn - nbrVariables == currentLine;
  }

  //permet de recupere la colonne des valeurs des contraintes
  private int tailleContraintes() {
	  return contraintes[0].length;
	  }

  //Retourne la valeur de la contrainte
  public BigDecimal getValeur(int line) {
    return contraintes[line][tailleContraintes() - 1];
  }


  private void fonctionEconomique(BigDecimal[] tableauEcoFonction) {
    this.fonctionEconomique = new BigDecimal[tailleContraintes()];

    for (int i = 0; i < fonctionEconomique.length; i++)
      fonctionEconomique[i] = i < tableauEcoFonction.length - 1 ? tableauEcoFonction[i] : BigDecimal.ZERO;

    fonctionEconomique[fonctionEconomique.length - 1] = tableauEcoFonction[tableauEcoFonction.length - 1];
  }

  public BigDecimal[][] getConstraints() {
    return contraintes;
  }

  public void setConstraints(BigDecimal[][] constraints) {
    this.contraintes = constraints;
  }

  public BigDecimal[] getEcoFunction() {
    return fonctionEconomique;
  }

  public void setEcoFunction(BigDecimal[] ecoFunction) {
    this.fonctionEconomique = ecoFunction;
  }

  public int getNbrVariables() {
    return nombreVariables;
  }

  public int[] getSolutionValues() {
    return solutions;
  }

  public void affichageTableau() {
    System.out.println(this.toString());
  }

  /**Afficher les variables (xi, ei) chaine*/
  private String nombreVariablesEnString() {
	  String str = "";
	  for (int i = 0; i < nombreVariables; i++)
		  str += "x" + (i + 1) + "\t\t";
	  for (int i = nombreVariables; i < fonctionEconomique.length - 1; i++)
		  str += "e" + (i - nombreVariables + 1) + "\t\t";
	  str += "Z\n";
	  return str;
  }

  /**Afficher la fonction eco*/
  private String fonctionEcoEnString() {
	  String str = "";
	  for (BigDecimal variable : fonctionEconomique)
		  str += variable.setScale(2, RoundingMode.HALF_EVEN).toPlainString() + "\t\t";
	  str += "\n";
	  return str;
  }

  /**Afficher les contraintes*/
  private String contraintesEnString() {
	  String str = "";
	  for (BigDecimal[] constraint : contraintes) {
		  for (BigDecimal variable : constraint)
			  str += variable.setScale(2, RoundingMode.HALF_EVEN).toPlainString() + "\t\t";
		  str += "\n";
	  }
	  str += "\n";
	  return str;
  }

  /**methode toString qui appelle les 3 fonctions au dessus pour formes le tableau*/
  public String toString() {
	  String str = "";

    str += nombreVariablesEnString();
    str += fonctionEcoEnString();
    str += contraintesEnString();

    return str;
  }

  /**Affiche les solutions finales*/
  public void affichageSolution() {
    System.out.println(getSolution());
  }

  /**Recupere les solutions*/
  public String getSolution() {
	  String str = "";
	  int i = 0;
	  BigDecimal z = fonctionEconomique[fonctionEconomique.length - 1];
	  str += "\nSolution : \n";
	  str += "Z = " + z.negate().setScale(2, RoundingMode.HALF_EVEN).toPlainString() + "\n";

	  for (int solutionValueIndex : solutions) {
		  str += "x" + (++i) + " = ";
		  str += solutionValueIndex != -1 ?
				  getValeur(solutionValueIndex).setScale(2, RoundingMode.HALF_EVEN).toPlainString() :
					  "0";
				  str += "\n";
	  }
	  return str;
  }
}
