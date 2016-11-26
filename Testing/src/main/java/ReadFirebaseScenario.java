import java.util.concurrent.atomic.AtomicBoolean;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import mollusca.common.util.JsonHandler;
import mollusca.scenario.Deliverable;
import mollusca.scenario.DeliveryMap;

public class ReadFirebaseScenario extends AbstractTestingScenario {

	@Override
	public Deliverable run(TestingService service, JsonHandler param) throws Exception {
//		System.out.println("===============================");
//		System.out.println("Test Firebase Read Single Key");
//		System.out.println("Read data: " + key);
		
		String startId = null;
		String endId = null;
		String key = null;
		if(param.has("start")) startId = param.getAsString("start");
		if(param.has("end")) endId = param.getAsString("end");
		if(param.has("key")) key = param.getAsString("key");
		
		String root = service.getRootref();
		
		final AtomicBoolean flag = new AtomicBoolean(true);
		
		ChildEventListener listener = new ChildEventListener() {
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				String testDataKey = dataSnapshot.getKey();
		    	Data readTestData = dataSnapshot.getValue(Data.class);
		        System.out.println(System.currentTimeMillis() + ",Read TestData," + testDataKey + "," + readTestData.toString());
		        flag.set(false);
			}

			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

			public void onChildRemoved(DataSnapshot dataSnapshot) {}

			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

			public void onCancelled(DatabaseError databaseError) {}
		};
		
		long startTime = System.currentTimeMillis();
		
		if (startId != null && endId != null) {
			service.getInitialReference().child(root).orderByKey().startAt(startId).endAt(endId).addChildEventListener(
					listener);
		} else if (key != null) {
			service.getInitialReference().child(root).orderByKey().equalTo(key).addChildEventListener(
					listener);
		}
		
		
		while (flag.get());
		
		long endTime = System.currentTimeMillis();
		
		long execTime = endTime - startTime;
		
//		System.out.println("Elapsed Time for ("+ key + "): " + execTime + " ms");
//		System.out.println("===============================");
		service.getInitialReference().child(root).removeEventListener(listener);
		
		service.incrExecCount();
		service.addExecTime(execTime);
		
		DeliveryMap result = new DeliveryMap();
		result.put("elapsed_time", execTime);
		result.put("execCount", service.getExecCount());
		result.put("execTime", service.getExecTime());
		
		return result;
	}

}
