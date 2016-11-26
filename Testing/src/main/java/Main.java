
public class Main {
	
	public static void main(String[] args) {
		try {
			TestingService service = new TestingService();
			service.prepare(args);
			service.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
