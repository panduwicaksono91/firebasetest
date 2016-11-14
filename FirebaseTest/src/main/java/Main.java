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
			DatabaseReference usersRef = ref.child("users");
			
			// This section is for Save Data, currently working!
			Users testing2 = new Users("testing2@gmail.com","testing2","+32123456789","testing2");
			
			DatabaseReference newUsersRef = usersRef.push();
			newUsersRef.setValue(testing2);	
			
			Thread.sleep(10000);
			
			
			
			// Retrieve Data
			// Create a listener for any delete event, currently working!
			
//			usersRef.addChildEventListener(new ChildEventListener() {
//				
//			    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {}
//
//			    
//			    public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}
//
//			    public void onChildRemoved(DataSnapshot dataSnapshot) {
//			        Users removedUsers = dataSnapshot.getValue(Users.class);
//			        System.out.println("Deleted Users: " + removedUsers.toString());
//			    }
//
//			    public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}
//
//			    public void onCancelled(DatabaseError databaseError) {}
//			});
//			
//			Thread.sleep(10000);
			
	}

}
