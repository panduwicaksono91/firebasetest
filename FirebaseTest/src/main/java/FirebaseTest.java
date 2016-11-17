import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
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
	
	public FirebaseTest() throws FileNotFoundException {
		// Initialize the app with a service account, granting admin privileges
		this.options = new FirebaseOptions.Builder()
				  .setServiceAccount(new FileInputStream(FIREBASE_CREDENTIALS))
				  .setDatabaseUrl(DATABASE_URL)
				  .build();
		FirebaseApp.initializeApp(options);
		
		// Get a reference to our users
		this.database = FirebaseDatabase.getInstance();
		this.initialReference = database.getReference();	
		this.testDataRef = initialReference.child("testData");
		
	}
	
	public void testInputWithPush(int n) throws InterruptedException{
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
		
		System.out.println("Elapsed Time ("+ n + "): " + elapsedTime);
		
	}
	
	public void testInputWithSet(int n) throws InterruptedException{
		System.out.println("Test Firebase Using Set Method");
		System.out.println("Total number of data: " + n);
		
		ArrayList<TestData> testDataList = TestDataGenerator.generateData(n);
	
		final CountDownLatch sync = new CountDownLatch(1);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		testDataRef.push().setValue(testDataList)
		.addOnCompleteListener(new OnCompleteListener<Void>() {
		      public void onComplete(Task<Void> task) {
		        sync.countDown();
		      }
		    });

		sync.await();
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ n + "): " + elapsedTime);
	
	}
	
}
