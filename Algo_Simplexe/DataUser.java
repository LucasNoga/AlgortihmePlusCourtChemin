package test;

import java.math.BigDecimal;
import java.util.Scanner;

/**Classe pour recuperer les donnees saisies par l'utilisateur*/
public class DataUser{

	/*Le nombre de variables présent dans la fonction eco*/
	private int nombreVariables;
	/*La fonction economique*/
	public BigDecimal[] fonctionEconomique;
	/*les contraintes dauns une matrice*/
	public BigDecimal[][] contraintes;

	public DataUser() {}

	/**Lancement du programme pour recuperer les données et les retournes dans un matrice a l'aide de la classe matriceSimplexe*/
	public MatriceSimplexe start(){
		fonctionEconomique = initialisationfonctionEconomique();
		contraintes  = contraintes();
		return new MatriceSimplexe(fonctionEconomique, contraintes, nombreVariables);
	}

	/**Fonction pour saisir la fonction economique*/
	private BigDecimal[] initialisationfonctionEconomique() {
		Scanner sc = new Scanner(System.in);
		
		//permet de savoir la taille du tableau de la fonction economique
		System.out.print("nombre de variables de la fonction : ");
		nombreVariables = sc.nextInt();
		if(nombreVariables <= 0){
			System.out.println("vous n'avez pas saisi le nombre demandé\nArret du programme");
			System.exit(0);
		}	
		fonctionEconomique = new BigDecimal[nombreVariables + 1];

		//permet de saisir les coefficients des variables de la fonction eco
		int i = 0;
		BigDecimal valeur;
		while (i < nombreVariables) {
			System.out.print("x" + (i+1) + " = ");
			valeur = sc.nextBigDecimal();
			fonctionEconomique[i] = valeur;
			i++;
		}
		
		// la valeur au départ de la fonction est zéro
		fonctionEconomique[i] = BigDecimal.ZERO;
		
		return fonctionEconomique;
	}

	private BigDecimal[][] contraintes() {
		Scanner sc = new Scanner(System.in);
		
		//le coefficient de la variable de la contrainte (2 pour x1 = 2)
		BigDecimal variable;
		
		//le nombre de contraintes
		int nombreContraintes;

		//permet de savoir la taille de la matrice
		System.out.print("\nNombre de contraintes : ");
		nombreContraintes = sc.nextInt();
		if(nombreContraintes <= 0){
			System.out.println("vous n'avez pas saisi le nombre demandé\nArret du programme");
			System.exit(0);
		}	
		contraintes = new BigDecimal[nombreContraintes][];

		
		//pour le membre de gauche
		int i, j;
		for (i = 0; i < nombreContraintes; i++) {
			contraintes[i] = new BigDecimal[nombreVariables + 1];
			for (j = 0; j < nombreVariables; j++) {
				System.out.print("x" + (j + 1) + " : ");
				variable = sc.nextBigDecimal();
				contraintes[i][j] = variable;
			}
			
			//pour le membre de droite
			System.out.print("valeur <= ");
			variable = sc.nextBigDecimal();
			contraintes[i][j] = variable;
			System.out.println();
			System.out.println();
		}
		return contraintes;
	}

	public static void main(String[] args) {
		DataUser data = new DataUser();

		MethodeSimplexe simplex = new MethodeSimplexe(data.start());
		simplex.testOptimisation();
	}
}
