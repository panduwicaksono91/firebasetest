import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;

public class FirebaseTest {
	
	// Constant
	private static final String FIREBASE_CREDENTIALS = "trader-530e1-firebase-adminsdk-xcw2w-863e57c040.json";
	private static final String DATABASE_URL = "https://trader-530e1.firebaseio.com";
	
	// Variables
	private static FirebaseOptions options;
	private static FirebaseDatabase database;
	private static DatabaseReference initialReference;
	private static DatabaseReference testDataRef;
	
	public FirebaseTest(DatabaseReference testDataRef) throws FileNotFoundException {
		this.testDataRef = testDataRef;
		// Initialize the app with a service account, granting admin privileges
//		this.options = new FirebaseOptions.Builder()
//				  .setServiceAccount(new FileInputStream(FIREBASE_CREDENTIALS))
//				  .setDatabaseUrl(DATABASE_URL)
//				  .build();
//		FirebaseApp.initializeApp(options);
//		
//		// Get a reference to our users
//		this.database = FirebaseDatabase.getInstance();
//		this.initialReference = database.getReference();	
//		this.testDataRef = initialReference.child("testData");
		
	}
	

	public void testInputWithPush(int n) throws InterruptedException{
		System.out.println("===============================");
		System.out.println("Test Firebase Using Push Method");
		System.out.println("Total number of data: " + n);
		
		ArrayList<TestData> testDataList = TestDataGenerator.generateData(n);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		for (int ii = 0; ii < n; ii++){
			final CountDownLatch sync = new CountDownLatch(1);
			
			testDataRef.push().setValue(testDataList.get(ii))
			.addOnCompleteListener(new OnCompleteListener<Void>() {
			      public void onComplete(Task<Void> task) {
				        sync.countDown();
				      }
				    });

			sync.await();
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time ("+ n + "): " + elapsedTime + " ms");
		
	}
	
	public void testInputWithSet(int n) throws InterruptedException{
		System.out.println("===============================");
		System.out.println("Test Firebase Using Set Method");
		System.out.println("Total number of data: " + n);
		
		ArrayList<TestData> testDataList = TestDataGenerator.generateData(n);
	
		final CountDownLatch sync = new CountDownLatch(1);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		testDataRef.setValue(testDataList)
		.addOnCompleteListener(new OnCompleteListener<Void>() {
		      public void onComplete(Task<Void> task) {
		        sync.countDown();
		      }
		    });

		sync.await();
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ n + "): " + elapsedTime + " ms");
	
	}
	
	public void testReadSingleKey(int key){
		System.out.println("===============================");
		System.out.println("Test Firebase Read Single Key");
		System.out.println("Read data: " + key);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		Flag.flag = true;
		// final AtomicBoolean done = new AtomicBoolean(false);
		
		testDataRef.orderByKey().equalTo(key).addChildEventListener(
				new ChildEventListener() {
					public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
						String testDataKey = dataSnapshot.getKey();
				    	TestData readTestData = dataSnapshot.getValue(TestData.class);
				        System.out.println(System.currentTimeMillis() + ",Read TestData," + testDataKey + "," + readTestData.toString());
				        Flag.flag = false;
				        //done.set(true);
					}

					public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

					public void onChildRemoved(DataSnapshot dataSnapshot) {}

					public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

					public void onCancelled(DatabaseError databaseError) {}
		});
		
		while(Flag.flag){}
		// while (!done.get());
	
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key + "): " + elapsedTime + " ms");
	}
	
	public void testReadRangedKey(int key1, int key2){
		System.out.println("===============================");
		System.out.println("Test Firebase Read Ranged Key");
		System.out.println("Read data: " + key1 + "-" + key2);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		Flag.flag = true;
		
		testDataRef.orderByKey().startAt(key1).endAt(key2).addChildEventListener(
				new ChildEventListener() {
					public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
						String testDataKey = dataSnapshot.getKey();
				    	TestData readTestData = dataSnapshot.getValue(TestData.class);
				        System.out.println(System.currentTimeMillis() + ",Read TestData," + testDataKey + "," + readTestData.toString());
				        Flag.flag = false;
					}

					public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

					public void onChildRemoved(DataSnapshot dataSnapshot) {}

					public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

					public void onCancelled(DatabaseError databaseError) {}
		});
		
		while(Flag.flag){}
	
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key1 + "-" + key2 + "): " + elapsedTime + " ms");
	}
	
	public void testUpdateSingleKey(int key) {}
	
	public void testUpdateRangedKey(int key1, int key2) {}
	
	public void testDeleteSingleKey(int key) {}
	
	public void testDeleteRangedKey(int key1, int key2) {}

}
