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
		    String palabra=consola.nextLine(); //lee la l�nea con el nombre de fichero  desde consola			

		Pattern patNormal = Pattern.compile("-?\\d\\.[\\d]+[[e|E][[-]|[+]][\\d]{1,2}]*");
		
		Matcher matNormal = patNormal.matcher(palabra); //frase "facet normal ..."
	    if (matNormal.matches()) 
	    	System.out.println("Correcto");
	    else
	    	System.out.println("NO");
		}	
		consola.close();
	}

}
