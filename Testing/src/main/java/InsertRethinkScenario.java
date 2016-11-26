
import java.util.ArrayList;
import java.util.Map;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import mollusca.common.util.JsonHandler;
import mollusca.scenario.Deliverable;
import mollusca.scenario.DeliveryMap;

public class InsertRethinkScenario extends AbstractTestingScenario {

	// Constant
	private final static RethinkDB r = RethinkDB.r;
	private final static Connection conn = r.connection().hostname("localhost").port(28015).connect();
		
		
	@Override
	public Deliverable run(TestingService service, JsonHandler param) throws Exception {
//		int n = param.getAsInt("n");
//		System.out.println("N : " + n);
		int n = 1;
		System.out.println("Test RethinkDB Create Using Push Method");
		System.out.println("Total number of data: " + n);
		
		ArrayList<Data> testDataList = DataGenerator.generateData(n);
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		
		for (int ii = 0; ii < n; ii++){
			Map<String, Object> s = r.table("testData").insert(
					r.hashMap("testName",testDataList.get(ii).getName())
					.with("testTime",r.now().toEpochTime())
			).run(conn);
//			for (Map.Entry<String, Object> entry : s.entrySet()) {
//				System.out.println(entry.getKey());
//				System.out.println(entry.getValue());
//			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("End Time: " + endTime);
		
		long elapsedTime = endTime - startTime;
		
		System.out.println("Elapsed Time ("+ n + "): " + elapsedTime);
		
		// close the connection
//		conn.close();
		
		service.incrExecCount();
		service.addExecTime(elapsedTime);
		
		DeliveryMap result = new DeliveryMap();
		result.put("elapsed_time", elapsedTime + " ms");
		result.put("execCount", service.getExecCount());
		result.put("execTime", service.getExecTime() + " ms" );
		
		
		return result;
	}

}
