import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;

import mollusca.common.util.JsonHandler;
import mollusca.scenario.Deliverable;
import mollusca.scenario.DeliveryMap;

public class DeleteFirebaseScenario extends AbstractTestingScenario {

	@Override
	public Deliverable run(TestingService service, JsonHandler param) throws Exception {
		
		String startId = null;
		String endId = null;
		String key = null;
		if(param.has("start")) startId = param.getAsString("start");
		if(param.has("end")) endId = param.getAsString("end");
		if(param.has("key")) key = param.getAsString("key");
		
		String root = service.getRootref();
		long execTime = 0;
		
		if (key != null) {
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			long startTime = System.currentTimeMillis();
			
			DatabaseReference updatedChildRef = service.getInitialReference().child(root).child(key);
			
			updatedChildRef.removeValue().addOnCompleteListener(
					new OnCompleteListener<Void>() {
					      public void onComplete(Task<Void> task) {
					        flag.set(false);
					      }
			    });

			while(flag.get());
			
			long endTime = System.currentTimeMillis();
			
			execTime = endTime - startTime;
		} else if (startId != endId) {
			
			int key1int = Integer.parseInt(startId);
			int key2int = Integer.parseInt(endId);
			
			Map<String, Object> updateMap = new HashMap<String, Object>();
			
			for(int ii = key1int ; ii < (key2int + 1); ii++){
				String updateKey = "" + ii;
//				String updateName = "testData" + ii + "Updated";
//				TestData updateTestData = new TestData(updateName, ServerValue.TIMESTAMP);
				
				updateMap.put(updateKey, null);
//				updateMap.put(updateKey + "/testTime", updateTestData.testTime);
			}
			
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			long startTime = System.currentTimeMillis();
			System.out.println("Start Time: " + startTime);
			
			DatabaseReference updatedChildRef = service.getInitialReference().child(root);
			
			updatedChildRef.updateChildren(updateMap).addOnCompleteListener(
					new OnCompleteListener<Void>() {
					      public void onComplete(Task<Void> task) {
						      flag.set(false);
					      }
			    });

			while(flag.get());
			
//			int key1int = Integer.parseInt(startId);
//			int key2int = Integer.parseInt(endId);
//			int length = key2int - key1int + 1;
//			String[] deletedKey = new String[length];
//			for(int ii = 0; ii < length; ii++)
//				deletedKey[ii] = "" + (ii + length - 1);
//			
//			long startTime = System.currentTimeMillis();
//			
//			for(int ii = 0; ii < length; ii++) {
//				DatabaseReference updatedChildRef = service.getInitialReference().child(root).child(deletedKey[ii]);
//			
//				final AtomicBoolean flag = new AtomicBoolean(true);
//				
//				updatedChildRef.removeValue().addOnCompleteListener(
//						new OnCompleteListener<Void>() {
//						      public void onComplete(Task<Void> task) {
//						        flag.set(false);
//						      }
//				    });
//		
//				while(flag.get());
//			}
			
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
