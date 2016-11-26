import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseListener {
	// Constant
	private static final String FIREBASE_CREDENTIALS = "trader-530e1-firebase-adminsdk-xcw2w-863e57c040.json";
	private static final String DATABASE_URL = "https://trader-530e1.firebaseio.com";
	
	// Variables
	private static FirebaseOptions options;
	private static FirebaseDatabase database;
	private static DatabaseReference initialReference;
	private static DatabaseReference testDataRef;
	
	public FirebaseListener() throws FileNotFoundException {
		// Initialize the app with a service account, granting admin privileges
		this.options = new FirebaseOptions.Builder()
				  .setServiceAccount(new FileInputStream(FIREBASE_CREDENTIALS))
				  .setDatabaseUrl(DATABASE_URL)
				  .build();
		FirebaseApp.initializeApp(options);
		
		// Get a reference to our test data
		this.database = FirebaseDatabase.getInstance();
		this.initialReference = database.getReference();	
		this.testDataRef = initialReference.child("testMartina");
		
	}
	
	public void initializeListener(){
		// Create a listener for any delete event, currently working!
		
		testDataRef.addChildEventListener(new ChildEventListener() {
		    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
		    	String testDataKey = dataSnapshot.getKey();
		    	TestData addedTestData = dataSnapshot.getValue(TestData.class);
		        System.out.println(System.currentTimeMillis() + ",Added TestData," + testDataKey + "," + addedTestData.toString());	
		    }

		    public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
		    	String testDataKey = dataSnapshot.getKey();
		    	TestData changedTestData = dataSnapshot.getValue(TestData.class);
		        System.out.println(System.currentTimeMillis() + "Changed TestData," + testDataKey + "," + changedTestData.toString());
		    }

		    public void onChildRemoved(DataSnapshot dataSnapshot) {
		    	String testDataKey = dataSnapshot.getKey();
		    	TestData removedTestData = dataSnapshot.getValue(TestData.class);
		        System.out.println(System.currentTimeMillis() + "Deleted TestData," + testDataKey + "," + removedTestData.toString());
		    }

		    public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

		    public void onCancelled(DatabaseError databaseError) {}
		});
		
		while(true){}
	}
}
