package prueba;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PruebaLenguajeFormal {

	public static void main(String[] args) {

		Scanner consola = new Scanner(System.in); //Crea Scanner para leer por consola
		boolean control=true;
		while(control)
		{
			System.out.println("Escriba la Palabra:  ");
		    String palabra=consola.nextLine(); //lee la línea con el nombre de fichero  desde consola			
			
		    

		Pattern patLoop = Pattern.compile("^outer\\sloop");
		Pattern patEndLoop = Pattern.compile("^endloop");
		//Pattern patNormal = Pattern.compile("^facet\\snormal\\s  ");
		//Pattern patNormal = Pattern.compile("^[-]*[\\d][.][\\d]+\\s[-]*[\\d]+[.][\\d]+\\s[-]*[\\d]+[.][\\d]+"); 	//funciona, caso: -5.32562 
		
		//Pattern patNormal = Pattern.compile("^[-]*[\\d][.][\\d]+[e|E][[-]|[+]][\\d]{1,2}");  						//funciona, caso:  3.235e-6
		
		
		//Pattern patNormal = Pattern.compile("^facet\\snormal\\s[-]*[\\d][.][\\d]+[[e|E][[-]|[+]][\\d]{1,2}]*\\s[-]*[\\d]+[.][\\d]+[[e|E][[-]|[+]][\\d]{1,2}]*\\s[-]*[\\d]+[.][\\d]+[[e|E][[-]|[+]][\\d]{1,2}]*"); //Esta se supone que funciona
		Pattern patNormal = Pattern.compile("^[-]*[\\d][.][\\d]+[[e|E][[-]|[+]][\\d]{1,2}]*");
		
		//Pattern patEndNormal = Pattern.compile("^endfacet");
		Pattern patVertice = Pattern.compile("^vertex\\s[[\\d]+[.][\\d]+]{3}");
		
		Matcher matNormal = patNormal.matcher(palabra); //frase "facet normal ..."
	    if (matNormal.matches()) 
	    	System.out.println("Es es un patNormal ");
	    Matcher matLoop = patLoop.matcher(palabra); //frase "outer loop"
		if (matLoop.matches()) 
			System.out.println("Es es un patLoop ");
		Matcher matVertice = patVertice.matcher(palabra); //frase "vertex ..."
		if (matVertice.matches()) 
			System.out.println("Es es un patVertice ");
		Matcher matEndLoop = patEndLoop.matcher(palabra); 
		if (matEndLoop.matches()) 
			System.out.println("Es es un patEndLoop ");
		}	
		consola.close();
	}

}
