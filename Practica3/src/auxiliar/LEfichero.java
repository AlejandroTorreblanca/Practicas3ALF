package auxiliar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Clase con una colecci�n de m�todos est�ticos que pueden usarse en otra clase para leer un fichero 
 * l�nea a l�nea y  volcar el contenido en un String; tambi�n  para escribir un string en un fichero. 
 * Se ilustra  el uso de los m�todos de lectura y escritura en fichero de esta clase y 
 * captura  de IOexception.
 *
 */
 
public class LEfichero {
			
    /**
     * M�todo que lee  un fichero de texto l�nea a l�nea y devuelve un String con todo el contenido.
     * @param nombreFichero  Un String con el nombre del fichero  que se quiere leer.
     * @return  El contenido del fichero guardado en String.
     * @throws IOException 
     */	
    public static String leerFichero(String nombreFichero) throws IOException  {
	File fichero = new File (nombreFichero); // Crea un objeto File a partir del nombre del fichero a leer
	FileReader fr = new FileReader(fichero); //Crea un FileReader para recorrer el fichero y recuperar el contenido
	BufferedReader br = new BufferedReader(fr); //Crea un BufferedReader a partir del FileReader para leer por l�neas el contenido.
	StringBuffer texto=new StringBuffer(); // Para guardar el texto del fichero a medida que se lee l�nea a l�nea; m�s eficiente que concatenar Strings.
	String linea;
	while ( (linea = br.readLine()) != null ) { //bucle de lectura l�nea a l�nea y volcado del fichero en StringBuffer
	    texto.append(linea); //a�ado la l�nea le�da con readLine al buffer
	    //a�adimos  secuencia de newline adecuada seg�n el sistema operativo, para que sea portable.
	    texto.append(System.getProperty("line.separator")); 
	}
	br.close();
	fr.close();
			
	return (texto.toString()); //convierte el StringBuffer a String y lo devuelve.
    }
						
/**
 * M�todo que guarda  un  String en un fichero. 
 * @param texto El Sgring con el texto para escribir en el fichero.
 * @param nombreFichero  Un String con el nombre del fichero en que se quiere escribir.
 * @throws FileNotFoundException 
 */
public static void  escribirFichero(String texto,String nombreFichero) throws FileNotFoundException {
    File fichero = new File (nombreFichero); // Crea un objeto File a partir del nombre de fichero a guardar
    PrintWriter escritor = new PrintWriter(fichero);			
    escritor.print(texto); //  escribe el  texto en fichero
    escritor.close(); //cierro el  escritor creado.			
}
	
/**
 * M�todo con ejemplos de uso de L/E en fichero y captura de excepci�n.
 */
	   
///////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
public static void main(String[] args) {
    Scanner consola = new Scanner(System.in); //Crea Scanner para leer por consola
		
    System.out.println("Nombre del fichero a leer> ");
    String nombreFichIn=consola.nextLine(); //lee la l�nea con el nombre de fichero  desde consola						
    String contenido=null; //para guardar el texto del fichero le�do
		
    try {
	contenido = LEfichero.leerFichero(nombreFichIn);	//se intenta leer el contenido de fichero
				  
    } catch (IOException e) {
	System.out.println("***Error de lectura*** "+e); 
    }  
    if (contenido!=null){	
	try {
				 
	    System.out.println("Nombre del fichero para guardar> ");
	    String nombreFichOut=consola.nextLine(); 				
										
	    LEfichero.escribirFichero(contenido,nombreFichOut);
	    System.out.println("+++ El fichero de salida ha sido generado."); 
				  
	} catch (IOException e) {
	    System.out.println("***Error de escritura*** "+e); 
	}  
    }				
		
    consola.close();
} 

} 
