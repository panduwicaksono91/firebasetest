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
		
		System.out.println("Elapsed Time ("+ n + "): " + elapsedTime);
		
		// close the connection
		conn.close();
	}
	
	public void testCreateWithSet(int n){
		// pass an array into RethinkDB
		
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
		
		System.out.println("Elapsed Time ("+ n + "): " + elapsedTime);
		
		// close the connection
		conn.close();
	}
	
	public void testReadSingleKey(int key){
		System.out.println("Test RethinkDB Read Single Element");
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		// read the data from database
		System.out.println(r.table("testData").get(key).run(conn).toString() + "," + System.currentTimeMillis());
				
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time: " + elapsedTime);
		
		// close the connection
		conn.close();
		
	}
	
	public void testReadRangedKey(int key1, int key2){
		System.out.println("Test RethinkDB Read Ranged Element");
		
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
		
		System.out.println("Elapsed Time: " + elapsedTime);
		
		// close the connection
		conn.close();
	}
	
	public void testDeleteSingleKey(int key){
		System.out.println("Test RethinkDB Delete Single Key");
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		// delete the data from database
		r.table("testData").get(key).delete()
				.optArg("return_changes", true).run(conn);
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time: " + elapsedTime);
		
		// close the connection
		conn.close();
	}
	
	public void testDeleteRangedKey(int key1, int key2){
		System.out.println("Test RethinkDB Delete Ranged Key");
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		// delete the data from database
		r.table("testData").between(key1, key2).
			optArg("right_bound", "closed").delete().run(conn);
				
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time: " + elapsedTime);
		
		// close the connection
		conn.close();
	}
	

}
