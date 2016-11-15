import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class Main {
	
	private static final String FIREBASE_CREDENTIALS = "trader-530e1-firebase-adminsdk-xcw2w-863e57c040.json";
	private static final String DATABASE_URL = "https://trader-530e1.firebaseio.com";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
			// Initialize the app with a service account, granting admin privileges
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setServiceAccount(new FileInputStream(FIREBASE_CREDENTIALS))
					  .setDatabaseUrl(DATABASE_URL)
					  .build();
			FirebaseApp.initializeApp(options);
			
			// Get a reference to our users
			final FirebaseDatabase database = FirebaseDatabase.getInstance();
			DatabaseReference ref = database.getReference();
			DatabaseReference testDataRef = ref.child("testData");
			
//			System.out.println("Check Time1: " + ServerValue.TIMESTAMP);
//			System.out.println("Check Time2: " + ServerValue.TIMESTAMP);
//			System.out.println("Check Time3: " + ServerValue.TIMESTAMP);
//			
			// This section is for Save Data, currently working!
			TestData testData1 = new TestData("testData1",ServerValue.TIMESTAMP);
			TestData testData2 = new TestData("testData2",ServerValue.TIMESTAMP);
			TestData testData3 = new TestData("testData3",ServerValue.TIMESTAMP);
			
			
			//DatabaseReference newTestDataRef = testDataRef.push();
			testDataRef.push().setValue(testData1);
			testDataRef.push().setValue(testData2);
			testDataRef.push().setValue(testData3);
			
			Thread.sleep(10000);
			
			
			
			// Retrieve Data
			// Create a listener for any delete event, currently working!
			
//			ref.addChildEventListener(new ChildEventListener() {
//			    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//			    	Users addedUsers = dataSnapshot.getValue(Users.class);
//			        System.out.println("Added Users: \n" + addedUsers.toString());	
//			    }
//
//			    public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
//			    	Users changedUsers = dataSnapshot.getValue(Users.class);
//			        System.out.println("Changed Users: \n" + changedUsers.toString());
//			    }
//
//			    public void onChildRemoved(DataSnapshot dataSnapshot) {
//			        Users removedUsers = dataSnapshot.getValue(Users.class);
//			        System.out.println("Deleted Users: \n" + removedUsers.toString());
//			    }
//
//			    public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}
//
//			    public void onCancelled(DatabaseError databaseError) {}
//			});
//			
//			Thread.sleep(10000);
			
			// Check Value Event Listener
//			usersRef.addValueEventListener(new ValueEventListener() {
//			    public void onDataChange(DataSnapshot dataSnapshot) {
//			        Users users = dataSnapshot.getValue(Users.class);
//			        System.out.println(users);
//			    }
//			    public void onCancelled(DatabaseError databaseError) {
//			        System.out.println("The read failed: " + databaseError.getCode());
//			    }
//			});
//			
//			Thread.sleep(10000);
			
	}

}
