package auxiliar;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Class that stores the three vertexes of a STL facet.
 * 
 * @version 1.0
 * @author edumart@um.es, jmjuarez@um.es, mdvaldes@um.es
 */

public class Facet {

	private Point3f vertex1 = null;
	private Point3f vertex2 = null;
	private Point3f vertex3 = null;
	private Vector3f normal = null;
	
	/*
	 * @param x x-coord of vertex1
	 * @param y y-coord of vertex1
	 * @param z z-coord of vertex1
	 */
	public void setVertex1(float x, float y, float z) {
		vertex1 = new Point3f(x,y,z);
	}

	/*
	 * @param x x-coord of vertex2
	 * @param y y-coord of vertex2
	 * @param z z-coord of vertex2
	 */
	public void setVertex2(float x, float y, float z) {
		vertex2 = new Point3f(x,y,z);
	}
	
	/*
	 * @param x x-coord of vertex3
	 * @param y y-coord of vertex3
	 * @param z z-coord of vertex3
	 */
	public void setVertex3(float x, float y, float z) {
		vertex3 = new Point3f(x,y,z);
	}
	
	/*
	 * @param x x-coord of normal vector
	 * @param y y-coord of normal vector
	 * @param z z-coord of normal vector
	 */
	public void setNormal(float x, float y, float z) {
		normal = new Vector3f(x,y,z);
	}
	
	/*
	 * @return vertex1 in javax.vecmath.Point3f format 
	 */	
	public Point3f getVertex1() {
		return vertex1;
	}
	
	/*
	 * @return vertex2 in javax.vecmath.Point3f format 
	 */	
	public Point3f getVertex2() {
		return vertex2;
	}
	
	/*
	 * @return vertex3 in javax.vecmath.Point3f format 
	 */	
	public Point3f getVertex3() {
		return vertex3;
	}
	
	/*
	 * @return normal in javax.vecmath.Vector3f format 
	 */	
	public Vector3f getNormal() {
		return normal;
	}
}
