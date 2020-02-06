/*
 * Graphe.java
 * SAUNIER DEBES Brice, 
 * TRINQUAND Thibault, 
 * NOGA Lucas
 */

package algo_court_chemin;

/**Permet de generer une matrice d'ajacence aleatoire, et 
 * d'appliquer les algorithmes de Moore-Djikstra et de Ford-Bellman*/
public class Graphe {

	/**pas de chemin entre deux sommets*/
	private static final int MAXIMAL = 999;

	/**matrice d'adjacence*/
	int[][] matrice;
	
	
	/**tableau des plus courtes distances*/ 
	int[] distances;
	
	/**nombre de sommets du graphes*/
	int sommets = 0;


	public Graphe() {} 
	//generer des poids de 1 à 100
	public int [][] generationAleatoireMatrice(int nbSommets){
		sommets = nbSommets;
		distances = new int[sommets];
		matrice = new int [sommets][sommets];

		//nombre random pour donner 999 a une position
		int c = (int) (Math.random() * 5) + 1;
		for(int i = 0; i < sommets; i++){
			for(int j = 0; j < sommets; j++){
				if (j == i)
					matrice[i][j] = 0;
				else{
					c = (int) (Math.random() * 5) +1;
					if(c == 5)
						matrice[i][j] = 999;
					else{
						int random = (int) (Math.random() * 100) +1;
						matrice[i][j] = random;
						distances[j] = random;
					}
				}
			}  
		}
		return matrice;  
	}

	public void affichageMatrice(){
		for(int i = 0; i < sommets ; i++){
			System.out.println();
			for(int j = 0; j < sommets; j++){
				System.out.print(matrice[i][j]+" ");
			}
		}
	}
	
	public int[] MooreDijkstra(int origine) {
		//enregistre les informations sur les plus courts chemins entre le sommet de départ et autres sommets
		String[] path = new String[sommets];  
		for(int i=0;i<matrice.length;i++)
			path[i]=new String(origine+"-->"+i);
		int iterateur = 0;

		//tableau des sommets déja parcouru
		int[] parcouru = new int[sommets];  

		//initialisation, le sommet de départ est considéré comme déjà parcouru
		distances[origine] = 0;
		parcouru[origine] = 1;
		
		for(int cpt = 1; cpt < matrice.length; cpt++) {
			int VI = -1;  
			int dmin = Integer.MAX_VALUE;
			for(int i = 0; i < sommets; i++) {
				if(parcouru[i] == 0 && matrice[origine][i] < dmin) {
					dmin = matrice[origine][i];
					VI = i;
					iterateur++;
				}
			}
			distances[VI] = dmin;
			parcouru[VI] = 1;
			for(int i = 0; i < matrice.length; i++) {
				if(parcouru[i] == 0 && matrice[origine][VI] + matrice[VI][i] < matrice[origine][i]) {
					matrice[origine][i] = matrice[origine][VI] + matrice[VI][i];
					path[i] = path[VI] + "-->" + i;
					iterateur++;
				}
			}
		}
		System.out.println("\n\nles plus courts chemins sont :");
		for(int i = 0; i < matrice.length; i++) {
			System.out.println(" de "+origine+" à "+i+" le plus court chemin "+path[i]);
		}
		System.out.println("\n\nAlgorithme de Moore-Djikstra");
		for(int i = 0;i < matrice.length;i++)
			System.out.println(" de "+ origine +" à "+ i +" la distance minimale "+ distances[i]);
		System.out.println("nombre d'itération " + iterateur); 
		return distances;
	}

	
	public void BellmanFord(int origine){
		int d =0;;
		System.out.println("\n\nAlgorithme de Bellman-Ford");
		for (int node = 1; node < distances.length; node++){
			distances[node] = MAXIMAL;
		}
		distances[origine] = 0;

		for (int noeud = 0; noeud < matrice.length; noeud++){
			for (int noeudOrigine = 0; noeudOrigine < matrice.length; noeudOrigine++){
				for (int noeudDestination = 1; noeudDestination < matrice.length; noeudDestination++){
					d++;
					if (matrice[noeudOrigine][noeudDestination] != MAXIMAL){
						if (distances[noeudDestination] > distances[noeudOrigine] + matrice[noeudOrigine][noeudDestination]){
							distances[noeudDestination] = distances[noeudOrigine]+ matrice[noeudOrigine][noeudDestination];
						}
					}
				}
			}
		}
		for (int noeudSource = 0; noeudSource < matrice.length; noeudSource++){
			for (int noeudDestination = 0; noeudDestination < matrice.length; noeudDestination++){
				if (matrice[noeudSource][noeudDestination] != MAXIMAL){
					if (distances[noeudDestination] > distances[noeudSource]+ matrice[noeudSource][noeudDestination]){
						System.out.println("le graphe possede un cycle absorbant");
					}
				}
			}
		}

		for (int sommet = 0; sommet < matrice.length; sommet++){
			System.out.println(" de "+ origine +" à "+ sommet +" la distance minimale est "+ distances[sommet]);
		}
		System.out.println("nombre d'itération "+ d);
	}

	//Generation de la matrice et application des algorithmes
	public static void main(String[] args){
		Graphe d = new Graphe();
		
		//il faut changer le nombre pour changer le nombre de sommet du graphe 
		d.generationAleatoireMatrice(1000);
		System.out.println("la matrice: ");
		d.affichageMatrice();
		
		//Moore-Djikstra
		long start = System.currentTimeMillis();
		d.MooreDijkstra(1);
		long end = System.currentTimeMillis();
		
		//Bellman-Ford	
		long start2 = System.currentTimeMillis();
		d.BellmanFord(1);
		long end2 = System.currentTimeMillis();
		
		//Calcul des temps d'execution
		long total = end-start;
		long total2 = end2-start2;
		System.out.println("comparatif entre le temps d'execution de Moore-Djikstra et Ford-Bellman");
		System.out.println("Moore-Djikstra: " + total  + " ms"+ "\nFord-Bellman: " + total2 +" ms");
	}
}
