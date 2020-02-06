package algo_court_chemin;

public class MooreDjikstra {

	public static void main(String[] args) {
		//pas de chemin entre deux sommets
		final int MAXIMAL = 999;

		//matrice d’adjacence (ou matrice d’incidence sommets-sommets)
		int[][] weight1 = {
				{0,3,2000,7,MAXIMAL},
				{3,0,4,2,MAXIMAL},
				{MAXIMAL,4,0,5,4},
				{7,2,5,0,6},   
				{MAXIMAL,MAXIMAL,4,6,0}
		};

		int[][] weight2 = {
				{0,10,MAXIMAL,30,100},
				{MAXIMAL,0,50,MAXIMAL,MAXIMAL},
				{MAXIMAL,MAXIMAL,0,MAXIMAL,10},
				{MAXIMAL,MAXIMAL,20,0,60},
				{MAXIMAL,MAXIMAL,MAXIMAL,MAXIMAL,0}
		};

		int[][] weight3 = {
				{0,1,1,4,MAXIMAL,2,5,MAXIMAL},
				{1,0,MAXIMAL,MAXIMAL,MAXIMAL,2,MAXIMAL,4},
				{1,MAXIMAL,0,MAXIMAL,MAXIMAL,MAXIMAL,3,MAXIMAL},
				{4,MAXIMAL,MAXIMAL,0,1,MAXIMAL,MAXIMAL,MAXIMAL},
				{MAXIMAL,MAXIMAL,MAXIMAL,1,0,1,MAXIMAL,MAXIMAL},
				{2,2,MAXIMAL,MAXIMAL,1,0,MAXIMAL,MAXIMAL},
				{5,MAXIMAL,3,MAXIMAL,MAXIMAL,MAXIMAL,0,1},
				{MAXIMAL,4,MAXIMAL,MAXIMAL,MAXIMAL,MAXIMAL,1,0}      	 
		};

		int[][] weight4 = {
				{0,3,1,MAXIMAL,MAXIMAL,MAXIMAL},
				{3,0,4,MAXIMAL,MAXIMAL,5},
				{1,4,0,7,2,MAXIMAL},
				{MAXIMAL,MAXIMAL,7,0,MAXIMAL,MAXIMAL},
				{MAXIMAL,MAXIMAL,2,MAXIMAL,0,MAXIMAL},
				{MAXIMAL,5,MAXIMAL,MAXIMAL,MAXIMAL,0}
		};

		int start=0;
		int[] shortPath = dijkstra(weight4,start);

		for(int i = 0;i < shortPath.length;i++)
			System.out.println(" de "+start+" à "+i+" la distance minimale "+shortPath[i]);
	}

	/**retourne un tableau, qui contient la longueur des chemins plus courts entre
	 * le sommet de départ et autres sommets*/
	
	public static int[] dijkstra(int[][] weight, int start) {
		int n = weight.length;  	// nombre des sommets

		// enregistre les longueurs des plus courts chemins entre le sommet de départ et autres sommets    
		int[] shortPath = new int[n];  

		// enregistre les informations sur les plus courts chemins entre le sommet de départ et autres sommets
		String[] path = new String[n];  
		for(int i=0;i<n;i++)
			path[i]=new String(start+"-->"+i);

		// si le sommet courant est déjà parcouru, la valeur est égale à un
		int[] visited = new int[n];  

		// initialisation, le sommet de départ est considéré comme déjà parcouru
		shortPath[start] = 0;
		visited[start] = 1;

		for(int count = 1; count < n; count++) {   // faut ajouter n-1 sommets
			int k = -1;    	// sélectionne un sommet qui est le plus proche du sommet de départ
			int dmin = Integer.MAX_VALUE;
			for(int i = 0; i < n; i++) {
				if(visited[i] == 0 && weight[start][i] < dmin) {
					dmin = weight[start][i];
					k = i;
				}
			}

			shortPath[k] = dmin;
			visited[k] = 1;

			for(int i = 0; i < n; i++) {
				if(visited[i] == 0 && weight[start][k] + weight[k][i] < weight[start][i]) {
					weight[start][i] = weight[start][k] + weight[k][i];
					path[i] = path[k] + "-->" + i;
				}
			}
		}
		for(int i = 0; i < n; i++) {
			System.out.println(" de "+start+" à "+i+" le plus court chemin "+path[i]);
		}
		System.out.println("=====================================");
		return shortPath;
	}
}
