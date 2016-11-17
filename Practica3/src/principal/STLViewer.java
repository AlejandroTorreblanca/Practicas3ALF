package principal;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// consultas sobre matcher:
// http://puntocomnoesunlenguaje.blogspot.com.es/2013/07/ejemplos-expresiones-regulares-java-split.html

public class STLViewer {

	private static int lineaN;
	
	public static int comprobarNombreFichero(String nombreFichero) {
       Pattern pat = Pattern.compile(".stl$");
       Matcher mat = pat.matcher(nombreFichero);
       if (mat.matches()) {
    	   return 1; 	//Caso en el que el fichero tiene extensión correcta
       } else {
    	   return 0;    //Caso en el que el fichero no tiene extensión correcta
       }
	}
	
	public static void comprobarFormatoTriangulo(String[] triangulo)
	{
		Pattern patLoop = Pattern.compile("^outer\\sloop");
		Pattern patEndLoop = Pattern.compile("^endloop");
		Pattern patNormal = Pattern.compile("^facet\\snormal\\s[[\\d]+[.][\\d]+]{3}");
		Pattern patEndNormal = Pattern.compile("^endfacet");
		Pattern patVertice = Pattern.compile("^vertex\\s[[\\d]+[.][\\d]+]{3}");
		
		lineaN++;
		Matcher matNormal = patNormal.matcher(triangulo[0]); //frase "facet normal ..."
	    if (!matNormal.matches()) 
	    	errorDeLectura2();
	    lineaN++;
		Matcher matLoop = patLoop.matcher(triangulo[1]); //frase "outer loop"
		if (!matLoop.matches()) 
	    	errorDeLectura2();
		for (int i = 2; i < 5; i++)
		{
			lineaN++;
			Matcher matVertice = patVertice.matcher(triangulo[2]); //frase "vertex ..."
			if (!matVertice.matches()) 
		    	errorDeLectura2();
		}
		lineaN++;
		Matcher matEndLoop = patEndLoop.matcher(triangulo[5]); //frase "endloop"
		if (!matEndLoop.matches()) 
	    	errorDeLectura2();
		lineaN++;
		Matcher matEndNormal = patEndNormal.matcher(triangulo[6]); //frase "endfacet"
	    if (!matEndNormal.matches()) 
	    	errorDeLectura2();
	}
	
	public static void errorDeLectura1()
	{
		System.out.println("Error en la lectura del fichero, el fichero está incompleto.\nPrograma finalizado.");
		System.exit(0);
	}
	
	public static void errorDeLectura2()
	{
		System.out.println("Error en la lectura del fichero, el formato de la linea "+ lineaN + " no es el adecuado.\nPrograma finalizado.");
		System.exit(0);
	}
	
	public static void leerFichero(String nombreFichero)throws IOException  
	{
		File fichero = new File (nombreFichero); // Crea un objeto File a partir del nombre del fichero a leer
		FileReader fr = new FileReader(fichero); //Crea un FileReader para recorrer el fichero y recuperar el contenido
		BufferedReader br = new BufferedReader(fr); //Crea un BufferedReader a partir del FileReader para leer por líneas el contenido.
		String linea;
		boolean control = true;
		Pattern patName = Pattern.compile("^solid\\s.*");
		Pattern patEndName = Pattern.compile("^endsolid\\s.*");
		
		if( (linea = br.readLine()) != null )	
		{
			lineaN++;
			Matcher matName = patName.matcher(linea); //frase "solid ..."
		       if (!matName.matches()) 
		    	   errorDeLectura2();
		}
		while(control)
		{
			if( (linea = br.readLine()) == null )
		    	errorDeLectura1();
			lineaN++;
			Matcher matEndName = patEndName.matcher(linea); //frase "endsolid ..."
			String[] triangulo= new String[7];
		    if (matEndName.matches()) 
		    	control=false;
		    else
		    {
		    	for (int i = 0; i < 7; i++) {
					triangulo[i]=br.readLine();
					if(triangulo[i]==null)
						errorDeLectura1();
				}
		    	comprobarFormatoTriangulo(triangulo);
		    }
		}
		br.close();
		fr.close();
	}
	
	public static void main(String[] args) {

		Scanner consola = new Scanner(System.in); //Crea Scanner para leer por consola
		boolean control=true;
		lineaN=0;
		while(control)
		{
			System.out.println("Nombre del fichero a leer con extensión .stl> ");
		    String nombreFichIn=consola.nextLine(); //lee la línea con el nombre de fichero  desde consola			
		    int valor=comprobarNombreFichero(nombreFichIn);
			switch (valor) {
			case 0:
				System.out.println("Extensión del fichero incorrecta, asegurese de que tiene extensión .stl.");
				break;
			case 1:
				try {
					leerFichero(nombreFichIn);	//se intenta leer el contenido de fichero
					control=false;			  
				} catch (IOException e) {
					System.out.println("***Error de lectura***\n Asegurese de que el fichero existe."+e); 
					control=true;
				    } 
				break;
			default:
				break;
			}	
		    
		}
		
	    
		consola.close();
	}

}
