import java.util.List;
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
				final AtomicBoolean flag = new AtomicBoolean(true);
				
				service.getInitialReference().child("testMartina").push().setValue(testDataList.get(ii))
				.addOnCompleteListener(new OnCompleteListener<Void>() {
				      public void onComplete(Task<Void> task) {
					        flag.set(false);
					      }
					    });

				while (flag.get());
			}
			long endTimePush = System.currentTimeMillis();
			execTime = endTimePush - startTimePush;
			break;

		default: // set
			long startTimeSet = System.currentTimeMillis();
			
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			service.getInitialReference().child("testMartina").setValue(testDataList)
			.addOnCompleteListener(new OnCompleteListener<Void>() {
			      public void onComplete(Task<Void> task) {
				        flag.set(false);
				      }
				    });

			while (flag.get());
		
			long endTimeSet = System.currentTimeMillis();
			execTime = endTimeSet - startTimeSet;
			break;
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
