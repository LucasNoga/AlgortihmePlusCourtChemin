package simplex;

public class Principal {
	
	public static String[][] sep(String line){
		String[] tamp = new String[line.length()];
		int index = 0;
		
		// slpit les espace
		for (String retval: line.split(" ")){
	        tamp [index] = retval;
	        index++;
		}
		
		// Compte le nombre d'élément non null dans tamp
		for(index = 0; tamp[index] != null; index++);
		
        // Suppression des case null
		String[] retval = new String[index];
		for(int i = 0; i != index; i++)
			retval[i] = tamp[i];
		
        // On extrait les coeff dans un nouveau tableau
        String[] retval2 = new String[index];
        for(int i = 0; i < index-2; i++)
            for (String tes: retval[i].split("x"))
                retval2[i] = tes;
        
        // Suppresion des coeff dans le premier tableau
        String[] retval3 = new String[index];
        for(int i = 0; i != index-2; i++){
            String tamp2 = "";
            for(int j = 0; retval[i].charAt(j) != 'x'; j++)
                tamp2 += retval[i].charAt(j);
            retval3[i] = tamp2;
        }
        retval3[index-2] = retval[index-2];
        retval3[index-1] = retval[index-1];

        // Tableau définitif
        String[][] result = new String[index][2];
        for(int i = 0; i < index; i++){
            result[i][0] = retval3[i];
            result[i][1] = retval2[i];
        }
        
		return result;
	}
	
	public static void main(String[] args) {
		
        String test = "21x1 +9x2 -2x3 <= 0";
        
        
        String[][] result = sep(test);
        
        for(int i = 0; i < result.length; i++)
            System.out.print(result[i][0] + "|");
        System.out.println();
        
        for(int i = 0; i < result.length; i++)
            System.out.print(result[i][1] + "|");
        System.out.println();

        
	}

}
