import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;

public class RethinkDBTest {
	
	// Constant
	private final static RethinkDB r = RethinkDB.r;
	private final static Connection conn = r.connection().hostname("localhost").port(28015).connect();
	
	public RethinkDBTest(){
		// Create the table, only run once
//		r.db("test").tableCreate("testData").run(conn);
	}
	
	public void testInputWithPush(int n){
		System.out.println("Test RethinkDB Using Push Method");
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
	
	public void testInputWithSet(int n){
		// pass an array into RethinkDB
		
		System.out.println("Test RethinkDB Using Set Method");
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
	
}
