import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;

import mollusca.common.util.JsonHandler;
import mollusca.scenario.Deliverable;
import mollusca.scenario.DeliveryMap;

public class UpdateFirebaseScenario extends AbstractTestingScenario {

	@Override
	public Deliverable run(TestingService service, JsonHandler param) throws Exception {

		int startId = 0;
		int endId = 0;
		String key = null;
		if(param.has("start")) startId = param.getAsInt("start");
		if(param.has("end")) endId = param.getAsInt("end");
		if(param.has("key")) key = param.getAsString("key");
		
		String root = service.getRootref();
		
		long execTime = 0;
		
		if (key != null) {
			String updateKey = "testDataUpdated";
			Data updateTestData = new Data(updateKey, ServerValue.TIMESTAMP);
			
			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("testName", updateTestData.getName());
			updateMap.put("testTime", updateTestData.getTime());
			
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			long startTime = System.currentTimeMillis();
			
			DatabaseReference updatedChildRef = service.getInitialReference().child(root).child(key);
			
			updatedChildRef.updateChildren(updateMap).addOnCompleteListener(
					new OnCompleteListener<Void>() {
					      public void onComplete(Task<Void> task) {
						      flag.set(false);
					      }
			    });

			while(flag.get());
			
			long endTime = System.currentTimeMillis();
			
			execTime = endTime - startTime;
		} 
		else if (startId != endId) {
			Map<String, Object> updateMap = new HashMap<String, Object>();
			
			for(int ii = startId ; ii < (endId + 1); ii++){
				String updateKey = "" + ii;
				String updateName = "testData" + ii + "Updated";
				Data updateTestData = new Data(updateName, ServerValue.TIMESTAMP);
				
				updateMap.put(updateKey + "/testName", updateTestData.getName());
				updateMap.put(updateKey + "/testTime", updateTestData.getTime());
			}
			
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			long startTime = System.currentTimeMillis();
			
			service.getInitialReference().child(root).updateChildren(updateMap).addOnCompleteListener(
					new OnCompleteListener<Void>() {
					      public void onComplete(Task<Void> task) {
						      flag.set(false);
					      }
			    });

			while(flag.get());
			
			long endTime = System.currentTimeMillis();
			
			execTime = endTime - startTime;
		}
		
		service.incrExecCount();
		service.addExecTime(execTime);
		
		DeliveryMap result = new DeliveryMap();
		result.put("elapsed_time", execTime);
		result.put("execCount", service.getExecCount());
		result.put("execTime", service.getExecTime());
		
		return result;
	}

}
