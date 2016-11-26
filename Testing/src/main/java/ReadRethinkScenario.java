import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import mollusca.common.util.JsonHandler;
import mollusca.scenario.Deliverable;
import mollusca.scenario.DeliveryMap;

public class ReadRethinkScenario extends AbstractTestingScenario {

	private final static RethinkDB r = RethinkDB.r;
	private final static Connection conn = r.connection().hostname("localhost").port(28015).connect();
	
	@Override
	public Deliverable run(TestingService service, JsonHandler param) throws Exception {

		String startId = null;
		String endId = null;
		String key = null;
		if(param.has("start")) startId = param.getAsString("start");
		if(param.has("end")) endId = param.getAsString("end");
		if(param.has("key")) key = param.getAsString("key");
		
//		String root = service.getRootref();
		
		long startTime = System.currentTimeMillis();
//		System.out.println("Start Time: " + startTime);
		
		// read the data from database
		if (startId != null && endId != null) {
			// read the data from database
			Cursor cursor = r.table("testData").between(startId, endId).optArg("right_bound", "closed").run(conn);
			
			for (Object change : cursor) {
//				System.out.println(System.currentTimeMillis() + "," + change.toString());
			}
		} else if (key != null) {
//			System.out.println(r.table("testData").get(key).run(conn).toString() + "," + System.currentTimeMillis());
		}
		
				
		long endTime = System.currentTimeMillis();
//		System.out.println("End Time: " + endTime);
		
		long execTime = endTime - startTime;
		
//		System.out.println("Elapsed Time for ("+ key + "): " + execTime + " ms");
//		System.out.println("===============================");
		
//		conn.close();
		
		service.incrExecCount();
		service.addExecTime(execTime);
		
		DeliveryMap result = new DeliveryMap();
		result.put("elapsed_time", execTime + " ms");
		result.put("execCount", service.getExecCount());
		result.put("execTime", service.getExecTime() + " ms" );
		
		return result;
	}

}
