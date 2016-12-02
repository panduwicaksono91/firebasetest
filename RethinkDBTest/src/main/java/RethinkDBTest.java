import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class RethinkDBTest {
	
	// Constant
	private final static RethinkDB r = RethinkDB.r;
	private final static Connection conn = r.connection().hostname("localhost").port(28015).connect();
	
	public RethinkDBTest(){
		// Create the table, only run once
//		r.db("test").tableCreate("testData").run(conn);
	}
	
	public void testCreateWithPush(int n){
		System.out.println("===============================");
		System.out.println("Test RethinkDB Create Using Push Method");
		System.out.println("Total number of data: " + n);
		
		ArrayList<TestData> testDataList = TestDataGenerator.generateData(n);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		for (int ii = 0; ii < n; ii++){
			r.table("testData").insert(
					r.hashMap("testName",testDataList.get(ii).testName)
					.with("testTime",r.now().toEpochTime())
			).run(conn);
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ n + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		
		// close the connection
		conn.close();
	}
	
	public void testCreateWithSet(int n){
		// pass an array into RethinkDB
		System.out.println("===============================");
		System.out.println("Test RethinkDB Create Using Set Method");
		System.out.println("Total number of data: " + n);
		
		ArrayList<TestData> testDataList = TestDataGenerator.generateData(n);
		
		// initialize the data
		MapObject[] mo = new MapObject[n];
		for(int ii = 0; ii < n; ii++) {
			mo[ii] = r.hashMap("id",ii)
			.with("testName", testDataList.get(ii).testName)
			.with("testTime", r.now().toEpochTime());
		}
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		// insert the data into database
		r.table("testData").insert(mo).run(conn);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ n + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		
		// close the connection
		conn.close();
	}
	
	public void testReadSingleKey(int key){
		System.out.println("===============================");
		System.out.println("Test RethinkDB Read Single Key");
		System.out.println("Read data: " + key);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		// read the data from database
		System.out.println(r.table("testData").get(key).run(conn).toString() + "," + System.currentTimeMillis());
				
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		
		// close the connection
		conn.close();
		
	}
	
	public void testReadRangedKey(int key1, int key2){
		System.out.println("===============================");
		System.out.println("Test RethinkDB Read Ranged Key");
		System.out.println("Read data: " + key1 + " - " + key2);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		// read the data from database
		Cursor cursor = r.table("testData").between(key1, key2).optArg("right_bound", "closed").run(conn);
		
		for (Object change : cursor) {
			System.out.println(System.currentTimeMillis() + "," + change.toString());
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key1 + " - " + key2 + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		
		// close the connection
		conn.close();
	}
	
	public void testUpdateSingleKey(int key){
		System.out.println("===============================");
		System.out.println("Test RethinkDB Update Single Key");
		System.out.println("Update data: " + key);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		// create new data
		TestData newTestData = new TestData("testData" + (key+1000),
				r.now().toEpochTime());
		
		// update the data into database
		r.table("testData").get(key).update(
				r.hashMap("testName",newTestData.testName)
					.with("testTime",r.now().toEpochTime()))
		.run(conn);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		
		// close the connection
		conn.close();
		
	}
	
	public void testUpdateRangedKey(int key1, int key2){
		System.out.println("===============================");
		System.out.println("Test RethinkDB Update Ranged Key");
		System.out.println("Update data: " + key1 + " - " + key2);
		
		
		
//		ArrayList<TestData> testDataList = TestDataGenerator.generateData(n);
		
		// initialize the data
		int length = key2 - key1 + 1;
		MapObject[] mo = new MapObject[length];
		
		for(int ii = key1, n = 0; ii <= key2; ii++, n++) {
			mo[ii] = r.hashMap("id",ii)
			.with("testName", "testData" + ii + "Updated")
			.with("testTime", r.now().toEpochTime());
		}
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		r.table("testData").update(mo);
		
		// kodingan yang bener yang di bawah
//		ArrayList<TestData> testDataList = TestDataGenerator.generateUpdateData(key1,key2);
//		
//		long startTime = System.currentTimeMillis();
//		System.out.println("Start Time: " + startTime);
//		
//		// update the data one by one
//		for(int ii = key1,n = 0; ii <= key2; ii++, n++){
//			r.table("testData").get(ii).update(
//					r.hashMap("testName",testDataList.get(n).testName)
//						.with("testTime",r.now().toEpochTime()))
//			.run(conn);
//		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time: " + elapsedTime + " ms");
		System.out.println("===============================");
		
		// close the connection
		conn.close();
		
	}
	
	public void testDeleteSingleKey(int key){
		System.out.println("===============================");
		System.out.println("Test RethinkDB Delete Single Key");
		System.out.println("Delete data: " + key);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		// delete the data from database
		r.table("testData").get(key).delete()
				.optArg("return_changes", true).run(conn);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		
		// close the connection
		conn.close();
	}
	
	public void testDeleteRangedKey(int key1, int key2){
		System.out.println("===============================");
		System.out.println("Test RethinkDB Delete Ranged Key");
		System.out.println("Delete data: " + key1 + " - " + key2);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		// delete the data from database
		r.table("testData").between(key1, key2).
			optArg("right_bound", "closed").delete().run(conn);
				
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time for ("+ key1 + " - " + key2 + "): " + elapsedTime + " ms");
		System.out.println("===============================");
		
		// close the connection
		conn.close();
	}
	
}
