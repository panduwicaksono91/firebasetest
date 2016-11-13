import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main {
	
	private static final String FIREBASE_CREDENTIALS = "trader-530e1-firebase-adminsdk-xcw2w-863e57c040.json";
	private static final String DATABASE_URL = "https://trader-530e1.firebaseio.com";
	
	public static void main(String[] args) throws IOException {
		
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
			
			System.out.println("check : " + ref.getRef());
			
			// This section is for Save Data, currently not working
//			Users testing2 = new Users("testing2@gmail.com","testing2","+32123456789","testing2");
//			
//			DatabaseReference newUsersRef = usersRef.push();
//			newUsersRef.setValue(testing2);	
			
			
			// Retrieve Data
			// Attach a listener to read the data at our posts reference
//			usersRef.addValueEventListener(new ValueEventListener() {
//			    @Override
//			    public void onDataChange(DataSnapshot dataSnapshot) {
//			        Users users = dataSnapshot.getValue(Users.class);
//			        System.out.println(users);
//			    }
//
//			    @Override
//			    public void onCancelled(DatabaseError databaseError) {
//			        System.out.println("The read failed: " + databaseError.getCode());
//			    }
//			});
	}

}
