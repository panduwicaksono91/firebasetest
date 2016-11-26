import java.io.IOException;

public class Main {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		FirebaseListener firebaseListener = new FirebaseListener();
		firebaseListener.initializeListener();
		
		System.out.println("Check Listener Working");
	}
}
