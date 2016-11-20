import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;


public class MyRunnable implements Runnable {

	
//	private final String url;
	private DatabaseReference dbRef;
	private int index;
	 
	MyRunnable(DatabaseReference dbRef, int idx){
		this.dbRef = dbRef;
		this.index = idx;
	}
	
//	public void run() {
//		// TODO Auto-generated method stub
//		String result = "";
//		int code = 200;
//		try {
//			URL siteURL = new URL(url);
//			HttpURLConnection connection = (HttpURLConnection) siteURL
//					.openConnection();
//			connection.setRequestMethod("GET");
//			connection.connect();
//
//			code = connection.getResponseCode();
//			if (code == 200) {
//				result = "Green\t";
//			}
//		} catch (Exception e) {
//			result = "->Red<-\t";
//		}
//		System.out.println(url + "\t\tStatus:" + result);
//	}
//	
	public void run(){
		int n = 1;
		System.out.println(index + ". ===============================");
		System.out.println(index + ". Test Firebase Using Set Method");
		System.out.println(index + ". Total number of data: " + n);
		final AtomicBoolean done = new AtomicBoolean(false);
		ArrayList<TestData> testDataList = TestDataGenerator.generateData(n);
	
//		final CountDownLatch sync = new CountDownLatch(1);
		
		final long startTime = System.currentTimeMillis();
		System.out.println(index + ". Start Time: " + startTime);
		dbRef.push().setValue(testDataList.get(0))
		.addOnCompleteListener(new OnCompleteListener<Void>() {
		      public void onComplete(Task<Void> task) {
		        long endTime = System.currentTimeMillis();
		  		System.out.println(index + ". End Time: " + endTime);
				
				long elapsedTime = endTime - startTime;
				
				System.out.println(index + ". Elapsed Time for: " + elapsedTime + " ms");
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
		
		
	
	}

}
