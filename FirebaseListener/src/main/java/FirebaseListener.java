import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseListener {
	// Constant
	private static final String FIREBASE_CREDENTIALS = "trader-530e1-firebase-adminsdk-xcw2w-863e57c040.json";
	private static final String DATABASE_URL = "https://trader-530e1.firebaseio.com";
	
	// Variables
	private static FirebaseOptions options;
	private static FirebaseDatabase database;
	private static DatabaseReference initialReference;
	private static DatabaseReference testDataRef;
	private static DatabaseReference offsetReference;
	private double offset = 0;
	
	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	private AtomicBoolean isOffsetSet = new AtomicBoolean(false);
	
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
		this.testDataRef = initialReference.child("testData");
		this.offsetReference = initialReference.child(".info/serverTimeOffset");
		
	}
	
	public void getOffSet() {
		
		offsetReference.addValueEventListener(new ValueEventListener() {

			public void onCancelled(DatabaseError arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onDataChange(DataSnapshot arg0) {
				// TODO Auto-generated method stub
				offset = arg0.getValue(Double.class);
				isOffsetSet.set(true);
				System.out.println(offset);
			}
		  
		});
		while(!isOffsetSet.get()){}
		System.out.println("DONE");
	}
	
	public void initializeListener(long off){
		// Create a listener for any delete event, currently working!
		final long offset = off;
		testDataRef.addChildEventListener(new ChildEventListener() {
		    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
		    	String testDataKey = dataSnapshot.getKey();
		    	TestData addedTestData = dataSnapshot.getValue(TestData.class);
		        
		    	System.out.println(addedTestData.toString());
		    	
//		    	long offset = this.offset;
		    	System.out.println("Offset: " + offset);
		    	
		    	long estimatedTimeServer = System.currentTimeMillis() + offset;
		    	System.out.println("Estimated Time Server when Broadcast is Received: " + estimatedTimeServer);
		    	
		    	String addedTestDataString[] = addedTestData.toString().split(",");
		    	System.out.println(addedTestData);
		    	long receivedTime = Long.parseLong(addedTestDataString[1]);
		    	System.out.println("Received Time: " + receivedTime);
		    	long latency = estimatedTimeServer - receivedTime;
		    	System.out.println("Added TestData," + addedTestDataString[0] + ",latency," + latency);
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
