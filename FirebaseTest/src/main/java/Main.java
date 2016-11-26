import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main {
	
	private static final String FIREBASE_CREDENTIALS = "trader-530e1-firebase-adminsdk-xcw2w-863e57c040.json";
	private static final String DATABASE_URL = "https://trader-530e1.firebaseio.com";
	
//	private static final int MYTHREADS = 1;
	private static FirebaseOptions options;
	private static FirebaseDatabase database;
	private static DatabaseReference initialReference;
	private static DatabaseReference testDataRef;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		options = new FirebaseOptions.Builder()
		  .setServiceAccount(new FileInputStream(FIREBASE_CREDENTIALS))
		  .setDatabaseUrl(DATABASE_URL)
		  .build();
		FirebaseApp.initializeApp(options);
		
		// Get a reference to our users
		database = FirebaseDatabase.getInstance();
		initialReference = database.getReference();	
		testDataRef = initialReference.child("testData");
		
		FirebaseTest firebaseTest = new FirebaseTest(testDataRef);
//		firebaseTest.testDeleteSingleKey("0");
//		firebaseTest.testDeleteRangedKey("0", "2");
//		firebaseTest.testUpdateSingleKey("0");
		firebaseTest.testReadRangedKey("0","-KX0KIc3CHKJuLyVdfTn");
		
//		firebaseTest.testInputWithSet(1);
		
//		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		
//		long start = System.currentTimeMillis();
//		
//		
////		for (int i = 0; i < 1000; i++) {
//// 
////			Runnable worker = new MyRunnable(testDataRef, i);
////			executor.execute(worker);
////		}
////		executor.shutdown();
////		// Wait until all threads are finish
////		while (!executor.isTerminated()) {
//// 
////		}
//		long end = System.currentTimeMillis();
//		System.out.println("\nFinished all threads");
//		System.out.println(end - start + " ms");
//		
//		GoogleCredential googleCred = GoogleCredential.fromStream(new FileInputStream("trader-530e1-firebase-adminsdk-xcw2w-863e57c040.json"));
//		GoogleCredential scoped = googleCred.createScoped(
//		    Arrays.asList(
//		      "https://www.googleapis.com/auth/firebase.database",
//		      "https://www.googleapis.com/auth/userinfo.email"
//		    )
//		);
//		scoped.refreshToken();
//		String token = scoped.getAccessToken();
//		System.out.println(token);
		
//			FirebaseTest firebaseTest = new FirebaseTest();
//			int n = 2;
//			firebaseTest.testInputWithSet(1);
//			firebaseTest.testInputWithSet(n);
//			firebaseTest.testInputWithPush(n);
			
			
			
					
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
