package auxiliar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Clase con una colección de métodos estáticos que pueden usarse en otra clase para leer un fichero 
 * línea a línea y  volcar el contenido en un String; también  para escribir un string en un fichero. 
 * Se ilustra  el uso de los métodos de lectura y escritura en fichero de esta clase y 
 * captura  de IOexception.
 *
 */
 
public class LEfichero {
			
    /**
     * Método que lee  un fichero de texto línea a línea y devuelve un String con todo el contenido.
     * @param nombreFichero  Un String con el nombre del fichero  que se quiere leer.
     * @return  El contenido del fichero guardado en String.
     * @throws IOException 
     */	
    public static String leerFichero(String nombreFichero) throws IOException  {
	File fichero = new File (nombreFichero); // Crea un objeto File a partir del nombre del fichero a leer
	FileReader fr = new FileReader(fichero); //Crea un FileReader para recorrer el fichero y recuperar el contenido
	BufferedReader br = new BufferedReader(fr); //Crea un BufferedReader a partir del FileReader para leer por líneas el contenido.
	StringBuffer texto=new StringBuffer(); // Para guardar el texto del fichero a medida que se lee línea a línea; más eficiente que concatenar Strings.
	String linea;
	while ( (linea = br.readLine()) != null ) { //bucle de lectura línea a línea y volcado del fichero en StringBuffer
	    texto.append(linea); //añado la línea leída con readLine al buffer
	    //añadimos  secuencia de newline adecuada según el sistema operativo, para que sea portable.
	    texto.append(System.getProperty("line.separator")); 
	}
	br.close();
	fr.close();
			
	return (texto.toString()); //convierte el StringBuffer a String y lo devuelve.
    }
						
/**
 * Método que guarda  un  String en un fichero. 
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
 * Método con ejemplos de uso de L/E en fichero y captura de excepción.
 */
	   
///////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
public static void main(String[] args) {
    Scanner consola = new Scanner(System.in); //Crea Scanner para leer por consola
		
    System.out.println("Nombre del fichero a leer> ");
    String nombreFichIn=consola.nextLine(); //lee la línea con el nombre de fichero  desde consola						
    String contenido=null; //para guardar el texto del fichero leído
		
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
