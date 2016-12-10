import java.io.IOException;

public class Main {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		FirebaseListener firebaseListener = new FirebaseListener();
		System.out.println("Check Listener Working:");
		firebaseListener.getOffSet();
		long offset = (long) firebaseListener.getOffset();
		firebaseListener.initializeListener(offset);
		
	}
}
