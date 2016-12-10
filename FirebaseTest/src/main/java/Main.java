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
	
//	private static final String FIREBASE_CREDENTIALS = "testingjava-a634e-firebase-adminsdk-8w1oc-daf2afd918.json";
//	private static final String DATABASE_URL = "https://testingjava-a634e.firebaseio.com/";
	
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
		
		// Get a reference to test data
		database = FirebaseDatabase.getInstance();
		initialReference = database.getReference();	
		testDataRef = initialReference.child("testData");
		
		FirebaseTest firebaseTest = new FirebaseTest(testDataRef);
		// all test scenario
		int n = 1;
		firebaseTest.testInputWithPush(n);
//		firebaseTest.testInputWithSet(10);
//		firebaseTest.testReadSingleKey("0");
//		firebaseTest.testReadRangedKey("0","-KX0KIc3CHKJuLyVdfTn");
//		firebaseTest.testUpdateSingleKey("0");
//		firebaseTest.testUpdateRangedKey(5, 7);
//		firebaseTest.testDeleteSingleKey("0");
//		firebaseTest.testDeleteRangedKey("0", "2");
		
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
			
			
	}

}
