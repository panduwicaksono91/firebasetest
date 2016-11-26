
import java.util.List;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.model.MapObject;
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

		// Setting parameter
		int typeIdx = 0; // default = set
		int n = 1; // the number of data for each process
		
		if(param.has("type")) {
			if(param.getAsString("type").equals("push")) {
				typeIdx = 1;
			}
		}		
		System.out.println(typeIdx);
		if(param.has("n")) {
			n = param.getAsInt("n");
		}
		
		// Result parameter 
		long execTime = 0;
		
		// Generate Data
		List<Data> testDataList = DataGenerator.generateData(n);
		
		switch (typeIdx) {
		case 1: // push
			long startTimePush = System.currentTimeMillis();
			
			for (int ii = 0; ii < n; ii++){
				r.table("testData").insert(
						r.hashMap("testName",testDataList.get(ii).getName())
						.with("testTime",r.now().toEpochTime())
				).run(conn);
			}
			
			long endTimePush = System.currentTimeMillis();
			execTime = endTimePush - startTimePush;
			break;

		default: // set
			
			// initialize the data
			MapObject[] mo = new MapObject[n];
			for(int ii = 0; ii < n; ii++) {
				mo[ii] = r.hashMap("id",ii)
				.with("testName", testDataList.get(ii).getName())
				.with("testTime", r.now().toEpochTime());
			}
			
			long startTimeSet = System.currentTimeMillis();
			
			// insert the data into database
			r.table("testData").insert(mo).run(conn);
					
			long endTimeSet = System.currentTimeMillis();
			execTime = endTimeSet - startTimeSet;
			break;
		}
		
		service.incrExecCount();
		service.addExecTime(execTime);
		
		DeliveryMap result = new DeliveryMap();
		result.put("elapsed_time", execTime + " ms");
		result.put("execCount", service.getExecCount());
		result.put("execTime", service.getExecTime() + " ms" );
		
		return result;
	}
}
