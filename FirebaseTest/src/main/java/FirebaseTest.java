import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	}
	

	public void testInputWithPush(int n) throws InterruptedException{
		System.out.println("===============================");
		System.out.println("Test Firebase Create Using Push Method");
		System.out.println("Total number of data: " + n);
		
		ArrayList<TestData> testDataList = TestDataGenerator.generateData(n);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		for (int ii = 0; ii < n; ii++){
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			testDataRef.push().setValue(testDataList.get(ii))
			.addOnCompleteListener(new OnCompleteListener<Void>() {
			      public void onComplete(Task<Void> task) {
				        flag.set(false);
				      }
				    });

			while (flag.get());
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ n + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		
	}
	
	public void testInputWithSet(int n) throws InterruptedException{
		System.out.println("===============================");
		System.out.println("Test Firebase Create Using Set Method");
		System.out.println("Total number of data: " + n);
		
		ArrayList<TestData> testDataList = TestDataGenerator.generateData(n);
	
		final AtomicBoolean flag = new AtomicBoolean(true);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		testDataRef.setValue(testDataList)
		.addOnCompleteListener(new OnCompleteListener<Void>() {
		      public void onComplete(Task<Void> task) {
		        flag.set(false);
		      }
		    });

		while (flag.get());
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ n + "): " + elapsedTime + " ms");
		System.out.println("===============================");
	}
	
	public void testReadSingleKey(String key){
		System.out.println("===============================");
		System.out.println("Test Firebase Read Single Key");
		System.out.println("Read data: " + key);
		
		final AtomicBoolean flag = new AtomicBoolean(true);
		
		ChildEventListener listener = new ChildEventListener() {
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				String testDataKey = dataSnapshot.getKey();
		    	TestData readTestData = dataSnapshot.getValue(TestData.class);
		        System.out.println(System.currentTimeMillis() + ",Read TestData," + testDataKey + "," + readTestData.toString());
		        flag.set(false);
			}

			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

			public void onChildRemoved(DataSnapshot dataSnapshot) {}

			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

			public void onCancelled(DatabaseError databaseError) {}
		};
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		testDataRef.orderByKey().equalTo(key).addChildEventListener(
				listener);
		
		while (flag.get());
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		testDataRef.removeEventListener(listener);
	}
	
	public void testReadRangedKey(String key1, String key2){
		System.out.println("===============================");
		System.out.println("Test Firebase Read Ranged Key");
		System.out.println("Read data: " + key1 + " - " + key2);
		
		final AtomicBoolean flag = new AtomicBoolean(true);
		final String limitKey = key2;	
		
		ChildEventListener listener = new ChildEventListener() {
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				String testDataKey = dataSnapshot.getKey();
		    	TestData readTestData = dataSnapshot.getValue(TestData.class);
		        System.out.println(System.currentTimeMillis() + ",Read TestData," + testDataKey + "," + readTestData.toString());
	        	if (testDataKey.equals(limitKey)) 
	        		flag.set(false);
			}

			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

			public void onChildRemoved(DataSnapshot dataSnapshot) {}

			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

			public void onCancelled(DatabaseError databaseError) {}
		};
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		testDataRef.orderByKey().startAt(key1).endAt(key2).addChildEventListener(
			listener);
		
		while(flag.get());
	
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key1 + " - " + key2 + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		testDataRef.removeEventListener(listener);
	}
	
	public void testUpdateSingleKey(String key) throws InterruptedException {
		System.out.println("===============================");
		System.out.println("Test Firebase Update Single Key");
		System.out.println("Update data: " + key);
		
		String updateKey = "testDataUpdated";
		TestData updateTestData = new TestData(updateKey, ServerValue.TIMESTAMP);
		
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("testName", updateTestData.testName);
		updateMap.put("testTime", updateTestData.testTime);
		
		final AtomicBoolean flag = new AtomicBoolean(true);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		DatabaseReference updatedChildRef = testDataRef.child(key);
		
		updatedChildRef.updateChildren(updateMap).addOnCompleteListener(
				new OnCompleteListener<Void>() {
				      public void onComplete(Task<Void> task) {
					      flag.set(false);
				      }
		    });

		while(flag.get());
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key + "): " + elapsedTime + " ms");
		System.out.println("===============================");
	}
	
	
	public void testUpdateRangedKey(int key1, int key2) {
		System.out.println("===============================");
		System.out.println("Test Firebase Update Single Key");
		System.out.println("Update data (" + key1 + " - " + key2 + ")");
		
		Map<String, Object> updateMap = new HashMap<String, Object>();
		
		for(int ii = key1 ; ii < (key2 + 1); ii++){
			String updateKey = "" + ii;
			String updateName = "testData" + ii + "Updated";
			TestData updateTestData = new TestData(updateName, ServerValue.TIMESTAMP);
			
			updateMap.put(updateKey + "/testName", updateTestData.testName);
			updateMap.put(updateKey + "/testTime", updateTestData.testTime);
		}
		
		final AtomicBoolean flag = new AtomicBoolean(true);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		testDataRef.updateChildren(updateMap).addOnCompleteListener(
				new OnCompleteListener<Void>() {
				      public void onComplete(Task<Void> task) {
					      flag.set(false);
				      }
		    });

		while(flag.get());
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key1 + " - " + key2 + "): " + elapsedTime + " ms");
		System.out.println("===============================");
	}
	
	
	public void testDeleteSingleKey(String key) throws InterruptedException {
		System.out.println("===============================");
		System.out.println("Test Firebase Delete Single Key");
		System.out.println("Delete data: " + key);
		
		final AtomicBoolean flag = new AtomicBoolean(true);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		DatabaseReference updatedChildRef = testDataRef.child(key);
		
		updatedChildRef.removeValue().addOnCompleteListener(
				new OnCompleteListener<Void>() {
				      public void onComplete(Task<Void> task) {
				        flag.set(false);
				      }
		    });

		while(flag.get());
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key + "): " + elapsedTime + " ms");
		System.out.println("===============================");
	}
	
	public void testDeleteRangedKey(String key1, String key2) throws InterruptedException {
		System.out.println("===============================");
		System.out.println("Test Firebase Delete Ranged Key");
		System.out.println("Delete data: " + key1 + " - " + key2);
		
		int key1int = Integer.parseInt(key1);
		int key2int = Integer.parseInt(key2);
		
		Map<String, Object> updateMap = new HashMap<String, Object>();
		
		for(int ii = key1int ; ii < (key2int + 1); ii++){
			String updateKey = "" + ii;
//			String updateName = "testData" + ii + "Updated";
//			TestData updateTestData = new TestData(updateName, ServerValue.TIMESTAMP);
			
			updateMap.put(updateKey, null);
//			updateMap.put(updateKey + "/testTime", updateTestData.testTime);
		}
		
		final AtomicBoolean flag = new AtomicBoolean(true);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		testDataRef.updateChildren(updateMap).addOnCompleteListener(
				new OnCompleteListener<Void>() {
				      public void onComplete(Task<Void> task) {
					      flag.set(false);
				      }
		    });

		while(flag.get());
		
//		int key1int = Integer.parseInt(key1);
//		int key2int = Integer.parseInt(key2);
//		int length = key2int - key1int + 1;
//		String[] deletedKey = new String[length];
//		for(int ii = 0; ii < length; ii++)
//			deletedKey[ii] = "" + (ii + length - 1);
//		
//		long startTime = System.currentTimeMillis();
//		System.out.println("Start Time: " + startTime);
//		
//		for(int ii = 0; ii < length; ii++) {
//			DatabaseReference updatedChildRef = testDataRef.child(deletedKey[ii]);
//		
//			final AtomicBoolean flag = new AtomicBoolean(true);
//			
//			updatedChildRef.removeValue().addOnCompleteListener(
//					new OnCompleteListener<Void>() {
//					      public void onComplete(Task<Void> task) {
//					        flag.set(false);
//					      }
//			    });
//	
//			while(flag.get());
//		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key1 + " - " + key2 + "): " + elapsedTime + " ms");
		System.out.println("===============================");
	}
}
