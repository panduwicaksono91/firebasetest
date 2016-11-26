import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;

import mollusca.common.util.JsonHandler;
import mollusca.scenario.Deliverable;
import mollusca.scenario.DeliveryMap;

public class InsertFirebaseScenario extends AbstractTestingScenario {
	
	@Override
	public Deliverable run(TestingService service, JsonHandler param) throws Exception {
		int n = 1;
		
		final AtomicBoolean done = new AtomicBoolean(false);
		ArrayList<Data> testDataList = DataGenerator.generateData(n);
	
		final long[] elapsedTime = {0};
		final long startTime = System.currentTimeMillis();
		
		service.getInitialReference().child("testData").push().setValue(testDataList.get(0))
		.addOnCompleteListener(new OnCompleteListener<Void>() {
		      public void onComplete(Task<Void> task) {
		        long endTime = System.currentTimeMillis();				
		  		elapsedTime[0] = endTime - startTime;
		    	  done.set(true);
		      }
		    });
		while (!done.get());
//		try {
////			sync.await();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		service.incrExecCount();
		service.addExecTime(elapsedTime[0]);
		
		
		DeliveryMap result = new DeliveryMap();
		result.put("elapsed_time", elapsedTime[0]);
		result.put("execCount", service.getExecCount());
		result.put("execTime", service.getExecTime());
		return result;
	}

}
