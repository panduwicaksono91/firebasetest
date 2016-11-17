import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;

public class Main {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
			FirebaseTest firebaseTest = new FirebaseTest();
			int n = 3;
			firebaseTest.testInputWithPush(n);
			firebaseTest.testInputWithSet(n);
					
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
