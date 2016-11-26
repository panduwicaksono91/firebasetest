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
		System.out.println(service == null ? "NULL" : "NOT NULL");
		int n = 1;
		System.out.println(". ===============================");
		System.out.println(". Test Firebase Using Set Method");
		System.out.println(". Total number of data: " + n);
		final AtomicBoolean done = new AtomicBoolean(false);
		ArrayList<Data> testDataList = DataGenerator.generateData(n);
	
//		final CountDownLatch sync = new CountDownLatch(1);
		final long[] elapsedTime = {0};
		final long startTime = System.currentTimeMillis();
		System.out.println(". Start Time: " + startTime);
		System.out.println(service.getInitialReference() == null ? "NULL" : "NOT NULL");
		service.getInitialReference().child("testData").push().setValue(testDataList.get(0))
		.addOnCompleteListener(new OnCompleteListener<Void>() {
		      public void onComplete(Task<Void> task) {
		        long endTime = System.currentTimeMillis();
		  		System.out.println(". End Time: " + endTime);
				
		  		elapsedTime[0] = endTime - startTime;
				
				System.out.println(". Elapsed Time for: " + elapsedTime[0] + " ms");
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
