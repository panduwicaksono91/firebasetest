import mollusca.fault.Fault;
import mollusca.fault.FaultDetail;
import mollusca.fault.FaultManager;


public class TestingFaultManager extends FaultManager {

	// EXCEPTION
	public Fault exception(Exception e, Object source) {
		if (e instanceof Fault) {
			return (Fault) e;
		} else {
			String message = e.getMessage();
			String info = e.getClass().getName();
			StackTraceElement[] stackTrace = e.getStackTrace();

			FaultDetail detail = new FaultDetail(200);
			detail.setErrorCode("#exception");
			detail.setMessage(message);
			detail.setInfo(info);

			Fault fault = createFault(detail, source);
			fault.setStackTrace(stackTrace);
			return fault;
		}
	}
	
	public static class SingletonHolder {
		public static TestingFaultManager INSTANCE = new TestingFaultManager();
	}

	public static TestingFaultManager getInstance() {
		return SingletonHolder.INSTANCE;
	}
}
