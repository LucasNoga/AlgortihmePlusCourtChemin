package Algorithme.Simplexe.algo_court_chemin;

import java.util.Scanner;

public class BellmanFord {
	public int distances[];
	public int nombrePoints;
	public static final int MAXIMAL = 999;
	
	public BellmanFord(){}

	public BellmanFord(int nombreDeSommets){
		this.nombrePoints = nombreDeSommets;
		distances = new int[nombreDeSommets + 1];
	}

	public void algoBellmanFord(int source, int matriceAdjaence[][]){
		for (int node = 1; node <= nombrePoints; node++){
			distances[node] = MAXIMAL;
		}
		distances[source] = 0;

		for (int noeud = 1; noeud <= nombrePoints - 1; noeud++){
			for (int nOrigine = 1; nOrigine <= nombrePoints; nOrigine++){
				for (int nDestination = 1; nDestination <= nombrePoints; nDestination++){
					if (matriceAdjaence[nOrigine][nDestination] != MAXIMAL){
						if (distances[nDestination] > distances[nOrigine] + matriceAdjaence[nOrigine][nDestination])
							distances[nDestination] = distances[nOrigine]+ matriceAdjaence[nOrigine][nDestination];
					}
				}
			}
		}
		for (int sourcenode = 1; sourcenode <= nombrePoints; sourcenode++){
			for (int destinationnode = 1; destinationnode <= nombrePoints; destinationnode++){
				if (matriceAdjaence[sourcenode][destinationnode] != MAXIMAL){
					if (distances[destinationnode] > distances[sourcenode]+ matriceAdjaence[sourcenode][destinationnode])
						System.out.println("le graphe possede un cycle absorbant");
				}
			}
		}

		for (int sommet = 1; sommet <= nombrePoints; sommet++){
			System.out.println("distance de l'origine  " + source + " à "+ sommet + " est " + distances[sommet]);
		}
	}

public static void main(String[] arg){
		int nombreDeSommet = 0;
		int source;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Entrez le nombre de sommets");
		nombreDeSommet = scanner.nextInt();
		int adjacencymatrix[][] = new int[nombreDeSommet + 1][nombreDeSommet + 1];
		System.out.println("Entrer ");
		for (int sourcenode = 1; sourcenode <= nombreDeSommet; sourcenode++)
		{
			for (int destinationnode = 1; destinationnode <= nombreDeSommet; destinationnode++)
			{
				adjacencymatrix[sourcenode][destinationnode] = scanner.nextInt();
				if (sourcenode == destinationnode){
					adjacencymatrix[sourcenode][destinationnode] = 0;
					continue;
				}
				if (adjacencymatrix[sourcenode][destinationnode] == 0){
					adjacencymatrix[sourcenode][destinationnode] = MAXIMAL;
				}
			}
		}
		System.out.println("Entrez le sommet a tester");
		source = scanner.nextInt();
		BellmanFord bellmanford = new BellmanFord(nombreDeSommet);
		
		long start = System.currentTimeMillis();
		bellmanford.algoBellmanFord(source, adjacencymatrix);
		
		//mettre le graphe initiale
		//trouvez de mettre le temps
		long end = System.currentTimeMillis();
		System.out.println(start);
		System.out.println(end);
		System.out.println(end-start+"ms");
		scanner.close();	
	}
}
