package auxiliar;
import java.util.ArrayList;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/*
 * List of facets in a STL solid
 * @version 1.0
 * @author edumart@um.es, jmjuarez@um.es, mdvaldes@um.es
 */
public class FacetList {
	private ArrayList<Facet> facets;
	private boolean useFileNormals = true;
	
	public FacetList() {
		facets = new ArrayList<Facet>();
	}
	
	public void add(Facet f) {
		facets.add(f);
	}
	
	/**
	 * Method that takes the info from an ArrayList of Point3f and returns a
	 * Point3f[]. Needed for ASCII files as we don't know the number of facets
	 * until the end.
	 *
	 * @return Point3f[] List of points of all the facets in the list.
	 */
	public Point3f[] getPoints() {
		Point3f outList[] = new Point3f[facets.size()*3];
		int j = 0;
		for (int i = 0; i < facets.size(); i++) {
			outList[j++] = facets.get(i).getVertex1();
			outList[j++] = facets.get(i).getVertex2();
			outList[j++] = facets.get(i).getVertex3();
		}
		return outList;
	}
	
	/**
	 * Method that takes the info from an ArrayList of Vector3f and returns a
	 * Vector3f[]. Needed for ASCII files as we don't know the number of facets
	 * until the end.
	 *
	 * @return Vector3f[] List of normals of all the facets in the list.
	 */
	public Vector3f[] getNormals() {
		// Normal vectors are copied three times, one for each vertex
		Vector3f outList[] = new Vector3f[facets.size()*3];
		int j = 0;
		for (int i = 0; i < facets.size(); i++) {
			outList[j++] = facets.get(i).getNormal();
			outList[j++] = facets.get(i).getNormal();
			outList[j++] = facets.get(i).getNormal();
		}
		return outList;
	}
	
	/**
	 * @param flag use or not normal vectors in file
	 */
	public void setUseFileNormals(boolean flag) {
		this.useFileNormals = flag;
	}
	
	/**
	 * @return flag use file normals
	 */
	public boolean getUseFileNormals() {
		return useFileNormals;
	}
	
}
