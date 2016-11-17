package auxiliar;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.LineArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.SceneBase;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

/*
 * Uses Java3D to show a solid.
 * Based on Universidad del Pais Vasco (UPV/EHU) version 
 * programmed by Carlos Pedrinaci Godoy (xenicp@yahoo.es).
 * @version 1.0
 * @author edumart@um.es, jmjuarez@um.es, mdvaldes@um.es
 */

public class J3DViewer extends Applet { 

	private static final long serialVersionUID = 1L;
	private static final double SCALE = 1.0;
	private SimpleUniverse u = null;	
	private FacetList facets = null;
	private Canvas3D canvas = null;
	private float lineAxisLength = 1.0f;
	private float lineAxisColorIntensity = 0.8f;
	private float lineAxisShininess = 10.0f;
	private float coneWidth = 0.01f;
	private float coneWidthScale = 0.01f;
	private float coneLength = 0.05f;
	private float coneLengthScale = 0.05f;
	private float nameScale = 0.02f;
	private float namePadding = 0.05f;
	
	/**
	 * Method that creates one of 3d axis 
  	 * @param String name of the axis "X", "Y" or "Z"
	 * @return BranchGroup with three axis
	 */
	private BranchGroup generateSingleAxis(String name) {
		BranchGroup axis = new BranchGroup();
		
		// Line specified with 2 points
		LineArray lineArray = new LineArray(2, LineArray.COORDINATES);		
		Point3f linePoint0 = new Point3f(); // Default point at 0,0,0
		Point3f linePoint1 = new Point3f(); // Default point at 0,0,0
		// Appearance
		Appearance app = new Appearance();      
		// Material
		Material mat = new Material();
		// Color
		ColoringAttributes color = new ColoringAttributes();
		// Transform group for cone at negative end of axis
		TransformGroup tg_cone_pos0 = new TransformGroup();
		// Transform translation of cone 0
		Transform3D t_cone_trans0 = new Transform3D();
		// Transform rotation of cone 0
		Transform3D t_cone_rot0 = new Transform3D();
		// Transform group for cone at positive end of axis
		TransformGroup tg_cone_pos1 = new TransformGroup();
		// Transform translation of cone 1
		Transform3D t_cone_trans1 = new Transform3D();
		// Transform rotation of cone 1
		Transform3D t_cone_rot1 = new Transform3D();
		// Axis name font
        Font3D f3d = new Font3D(new Font("Arial", Font.PLAIN, 2),new FontExtrusion());
        // Transform group for axis name at positive end 
        TransformGroup name_pos = new TransformGroup();
        // Name translation transform
        Transform3D t_name_trans = new Transform3D();
        // Name translation vector
        Vector3f v_name_trans = new Vector3f();
        // Transform group for axis name rotation
        TransformGroup name_rot = new TransformGroup();
       
        // Axis name
        Text3D t3d = new Text3D(f3d, name);
       	Shape3D name_shape = new Shape3D();       	
       	name_shape.setGeometry(t3d);
       	
       	// namePadding adjustment to lineAxisLength
       	namePadding = lineAxisLength*nameScale*2;
       
       	if (name.equals("X")) {
			linePoint0.x = -lineAxisLength;
			linePoint1.x = lineAxisLength;
			mat.setAmbientColor(new Color3f(lineAxisColorIntensity, 0.0f, 0.0f));
			mat.setEmissiveColor(new Color3f(0.0f, 0.0f, 0.0f));
			mat.setDiffuseColor(new Color3f(lineAxisColorIntensity, 0.0f, 0.0f));
			mat.setSpecularColor(new Color3f(lineAxisColorIntensity, 0.0f, 0.0f));
			mat.setShininess(lineAxisShininess);
			color.setColor(new Color3f(Color.red));
	        // Combine translation and rotation to locate cone in negative end
			t_cone_trans0.setTranslation(new Vector3f(-lineAxisLength,0.0f,0.0f));	        
	        t_cone_rot0.rotZ(Math.PI/2.0d);
	        t_cone_trans0.mul(t_cone_rot0);	        
	        tg_cone_pos0.setTransform(t_cone_trans0);
	        // Combine translation and rotation to locate cone in positive end
	        t_cone_trans1.setTranslation(new Vector3f(lineAxisLength,0.0f,0.0f));
	        t_cone_rot1.rotZ(-Math.PI/2.0d);
	        t_cone_trans1.mul(t_cone_rot1);	        
	        tg_cone_pos1.setTransform(t_cone_trans1);	        
	        // Name translation vector
	        v_name_trans = new Vector3f(lineAxisLength+namePadding,0.0f,0.0f);
		}
		else if (name.equals("Y")) { 
			linePoint0.y = -lineAxisLength;
			linePoint1.y = lineAxisLength;
			mat.setAmbientColor(new Color3f(0.0f, lineAxisColorIntensity, 0.0f));
			mat.setEmissiveColor(new Color3f(0.0f, 0.0f, 0.0f));
			mat.setDiffuseColor(new Color3f(0.0f, lineAxisColorIntensity, 0.0f));
			mat.setSpecularColor(new Color3f(0.0f, lineAxisColorIntensity, 0.0f));
			mat.setShininess(lineAxisShininess);	
			color.setColor(new Color3f(Color.green));
			// Combine translation and rotation to locate cone in negative end
			t_cone_trans0.setTranslation(new Vector3f(0.0f, -lineAxisLength, 0.0f));
			t_cone_rot0.rotZ(Math.PI);
			t_cone_trans0.mul(t_cone_rot0);
			tg_cone_pos0.setTransform(t_cone_trans0);
			// Combine translation and rotation to locate cone in positive end
			t_cone_trans1.setTranslation(new Vector3f(0.0f, lineAxisLength, 0.0f));
			tg_cone_pos1.setTransform(t_cone_trans1);
			// Name translation	vector        
			v_name_trans = new Vector3f(0.0f,lineAxisLength+namePadding,0.0f);
		}
		else if (name.equals("Z")) { 
			linePoint0.z = -lineAxisLength;
			linePoint1.z = lineAxisLength;
			mat.setAmbientColor(new Color3f(0.0f, 0.0f, lineAxisColorIntensity));
			mat.setEmissiveColor(new Color3f(0.0f, 0.0f, 0.0f));
			mat.setDiffuseColor(new Color3f(0.0f, 0.0f, lineAxisColorIntensity));
			mat.setSpecularColor(new Color3f(0.0f, 0.0f, lineAxisColorIntensity));
			mat.setShininess(lineAxisShininess);
			color.setColor(new Color3f(Color.blue));
			// Combine translation and rotation to locate cone in negative end
			t_cone_trans0.setTranslation(new Vector3f(0.0f, 0.0f, -lineAxisLength));
			t_cone_rot0.rotX(-Math.PI / 2.0d);
			t_cone_trans0.mul(t_cone_rot0);
			tg_cone_pos0.setTransform(t_cone_trans0);
			// Combine translation and rotation to locate cone in positive end
			t_cone_trans1.setTranslation(new Vector3f(0.0f, 0.0f, lineAxisLength));
			t_cone_rot1.rotX(Math.PI / 2.0d);
			t_cone_trans1.mul(t_cone_rot1);
			tg_cone_pos1.setTransform(t_cone_trans1);
			// Name translation	vector        
			v_name_trans = new Vector3f(0.0f,0.0f,lineAxisLength+namePadding);
		}		
		lineArray.setCoordinate(0, linePoint0);
		lineArray.setCoordinate(1, linePoint1);
       	app.setMaterial(mat);
       	app.setColoringAttributes(color);
       	
       	Shape3D line = new Shape3D(lineArray, app);
       	axis.addChild(line);  
       	
       	// Put two cones at the axis ends
       	coneWidth = lineAxisLength*coneWidthScale;
       	coneLength = lineAxisLength*coneLengthScale;
       	Cone cone0 = new Cone(coneWidth,coneLength,com.sun.j3d.utils.geometry.Primitive.GENERATE_NORMALS,app);
        tg_cone_pos0.addChild(cone0);       	
        axis.addChild(tg_cone_pos0);
       	Cone cone1 = new Cone(coneWidth,coneLength,com.sun.j3d.utils.geometry.Primitive.GENERATE_NORMALS,app);
        tg_cone_pos1.addChild(cone1);       	
        axis.addChild(tg_cone_pos1);

        // Name appearance
       	name_shape.setAppearance(app);
       	
       	// Scale
        Transform3D t_name = new Transform3D();        
        t_name.setScale(lineAxisLength*nameScale);
       	TransformGroup tg_name = new TransformGroup();
        tg_name.setTransform(t_name);
        tg_name.addChild(name_shape);
        name_rot.addChild(tg_name); 
        
        // Adjust name_pos transform to fix Y displacement
        Point3d name_center = new Point3d();
    	BoundingSphere b = new BoundingSphere(name_rot.getBounds());
    	b.getCenter(name_center);
    	v_name_trans.y -= (float)name_center.y;
        t_name_trans.setTranslation(v_name_trans);
        name_pos.setTransform(t_name_trans);	        

        // Translation & rotation of axis name   
        Transform3D t_name_rot1 = new Transform3D();
        t_name_rot1.rotZ(Math.PI/2);
        Transform3D t_name_rot2 = new Transform3D();
        t_name_rot2.rotX(Math.PI/2);
        t_name_rot1.mul(t_name_rot2);
        name_rot.setTransform(t_name_rot1);            
        name_pos.addChild(name_rot);
       	axis.addChild(name_pos);
        
       	return axis;
	}
	
	/**
	 * Method that creates the set of 3d axis  
	 * @return BranchGroup with three axis
	 */
	private BranchGroup generateAxis() {
		BranchGroup axisGroup = new BranchGroup();
		BranchGroup axisX = generateSingleAxis("X");
		BranchGroup axisY = generateSingleAxis("Y");
		BranchGroup axisZ = generateSingleAxis("Z");
		axisGroup.addChild(axisX);
		axisGroup.addChild(axisY);
		axisGroup.addChild(axisZ);
		return axisGroup;
	}
	
	/**
	 * Method that creates the SceneBase with a FacetList
	 * @param FacetList Facets of the solid to show
	 * @return SceneBase The scene with the solid in it
	 */
	public SceneBase makeScene(FacetList facets) {
		
		// Create Scene to pass back
		SceneBase scene = new SceneBase();
		BranchGroup group = new BranchGroup();
		scene.setSceneGroup(group);

		// Store the scene info on a GeometryInfo
		GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_STRIP_ARRAY);
		// Generate arrays with vertex and stripCounts to pass to Java3D
		Point3f[] vertexArray = facets.getPoints();
		// stripCounts indicates how many vertices has each facet.
		int[] stripCounts = new int[vertexArray.length/3];		
		for (int i=0; i < vertexArray.length/3; i++) {
			stripCounts[i] = 3;
		}
		// Generate arrays with normals to pass to Java3D
		Vector3f[] normalArray = facets.getNormals();
		
		// Fill geometryInfo with arrays
		gi.setCoordinates(vertexArray);
		gi.setStripCounts(stripCounts);
		
		if (facets.getUseFileNormals()) {
			gi.setNormals(normalArray);
		}
		else {
			// Instead of using normal vectors from file, it would be possible 
			// to calculate them here with this code
			NormalGenerator ng = new NormalGenerator();
			ng.generateNormals(gi);
		}
				
		// Combine adjacent triangles into triangle strips for more efficient rendering
		Stripifier st = new Stripifier();
		st.stripify(gi);
						
		// Put geometry into Shape3d
		Shape3D shape = new Shape3D();
		shape.setGeometry(gi.getGeometryArray());

		// Use default gray material
		Appearance ap = new Appearance();
		ColoringAttributes catt = new ColoringAttributes();
		catt.setShadeModel(ColoringAttributes.NICEST);
		ap.setColoringAttributes(catt);
		Material mat = new Material();
		mat.setLightingEnable(true);
		ap.setMaterial(mat);
		
		// Apply appearance to shape
		shape.setAppearance(ap);
		group.addChild(shape);
		scene.addNamedObject("solid", shape);
		
		return scene;
	}
	
	/**
	 * Creates background, lights and puts solid inside
	 * 
	 * @param FacetList Facets of the solid to show
	 */
	public BranchGroup createSceneGraph(FacetList facets) {
		
		// Create the root of the branch graph
		BranchGroup objRoot = new BranchGroup();

		// Create a TransformGroup to scale object
		TransformGroup tg_scale = new TransformGroup();		
		objRoot.addChild(tg_scale);

		// Generate scene with facets
		Scene s = makeScene(facets);
		tg_scale.addChild(s.getSceneGroup());
		
		// Generate axis
		BranchGroup axis = generateAxis();
		objRoot.addChild(axis);
		
		// Get object bounding box to scale
		BoundingSphere b = new BoundingSphere(s.getSceneGroup().getBounds());
		
		// Scale
		Transform3D t3d_scale = new Transform3D();		
		t3d_scale.setScale(SCALE*1.0f/(b.getRadius()));
		tg_scale.setTransform(t3d_scale);
		
		// Set up the background
		Color3f bgColor = new Color3f(0.55f, 0.55f, 0.75f);
		Background bgNode = new Background(bgColor);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 50);		
		bgNode.setApplicationBounds(bounds);
		objRoot.addChild(bgNode);		

		// Set up the ambient light 
		Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLightNode);

		// Set up four the directional lights
		Color3f light1Color = new Color3f(0.55f, 0.55f, 0.55f);
		Vector3f light1Direction  = new Vector3f(-1.0f, -1.0f, -1.0f);
		Color3f light2Color = new Color3f(0.55f, 0.55f, 0.55f);
		Vector3f light2Direction  = new Vector3f(1.0f, 1.0f, -1.0f);
		Color3f light3Color = new Color3f(0.35f, 0.35f, 0.35f);
		Vector3f light3Direction  = new Vector3f(0.0f, 0.0f, 1.0f);
		Color3f light4Color = new Color3f(0.2f, 0.2f, 0.2f);
		Vector3f light4Direction  = new Vector3f(-1.0f, 0.0f, 0.0f);

		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);

		DirectionalLight light2 = new DirectionalLight(light2Color, light2Direction);
		light2.setInfluencingBounds(bounds);
		objRoot.addChild(light2);
		
		DirectionalLight light3 = new DirectionalLight(light3Color, light3Direction);
		light3.setInfluencingBounds(bounds);
		objRoot.addChild(light3);
		
		DirectionalLight light4 = new DirectionalLight(light4Color, light4Direction);
		light4.setInfluencingBounds(bounds);
		objRoot.addChild(light4);

		return objRoot;
	}
	
	
	/*
	 * Constructor
	 */
	public J3DViewer(FacetList facets) {
		this.facets = facets;
	}
	
	/**
	 * Initialize
	 * Method called when the viewer is started
	 */
	public void init() {	
		
		// Border Layout
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		
		// The window will have a single 3d canvas in the center 
		canvas = new Canvas3D(config);
		add("Center", canvas);
		
		// SimpleUniverse represents the hierarchy of objects shown in the canvas
		u = new SimpleUniverse(canvas);

		// Create a simple scene and attach it to the virtual universe
		BranchGroup scene = createSceneGraph(facets);		
		u.addBranchGraph(scene);
		
		// Add mouse behaviors to the ViewingPlatform
		ViewingPlatform viewingPlatform = u.getViewingPlatform();		
				
		/*
		 * Java3D uses this coordinate system:
		 * 
		 *            y
		 *            |
		 *            |
		 *            ------- x
		 *           /
		 *          /
		 *         /
		 *        z
		 *        
		 * But STL files uses this one:
		 * 
		 *            z
		 *            |
		 *            |
		 *            ------- y
		 *           /
		 *          /
		 *         /
		 *        x
		 * 		 * 
		 * Play with the TransformGroup inside ViewingPlatform to fix this.
		 *  
		 */
		TransformGroup tg_vp = viewingPlatform.getViewPlatformTransform();
		Transform3D t3d_rotX = new Transform3D();
		t3d_rotX.rotX(Math.PI/2.0);	
		Transform3D t3d_rotY = new Transform3D();
		t3d_rotY.rotY(Math.PI/2.0);
		t3d_rotX.mul(t3d_rotY);

		Transform3D t3d_move = new Transform3D();
		float perspective = 2.0f/(float)Math.tan(u.getViewer().getView().getFieldOfView()/2.0);
		Vector3f move = new Vector3f(0.0f,0.0f,perspective);		
		t3d_move.setTranslation(move);
		t3d_rotX.mul(t3d_move);
		tg_vp.setTransform(t3d_rotX);
		
		// Orbit behaviour is used to control the camera with the mouse
		// Reverse_All makes the camera move according to the mouse movement
		// Stop_Zoom avoids to zoom too much inside the object.
		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL | OrbitBehavior.STOP_ZOOM);	
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		orbit.setSchedulingBounds(bounds);
		BoundingSphere bs = new BoundingSphere(scene.getBounds());
		// Fix the minimum distance to the object
		orbit.setMinRadius(bs.getRadius()*2.0);
		// Fix the maximum distance to the object (it disappears when the camera is very very far away)  
		u.getViewer().getView().setBackClipDistance(1000);
	
		// Attache the orbit object to the viewingPlatform
		viewingPlatform.setViewPlatformBehavior(orbit);	
	}
	
	/**
	 * Destroy
	 * Method called when the viewer is closed
	 */
	public void destroy() {
	    u.removeAllLocales(); 
	} 

	/**
	 * Displays an object specified with FacetList in a Java3D window
	 * @param facets Triangle facets that specifies the object
	 * @param width Window width in pixels
	 * @param height Window height in pixels
	 */
	public static void display(FacetList facets, int width, int height) {
		// MainFrame permits to execute this viewer as an application or an applet.
		new MainFrame(new J3DViewer(facets),800,800);		
	}	

	
}
