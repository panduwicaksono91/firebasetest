import mollusca.common.util.JsonHandler;
import mollusca.fault.Fault;
import mollusca.scenario.Deliverable;
import mollusca.scenario.IScenario;



public abstract class AbstractTestingScenario implements IScenario {

	private TestingService service;
	
	// SET SERVICE
	public void setService(TestingService service) {
		this.service = service;
	}
	
	public TestingService getService() {
		return service;
	}
		
	public Deliverable run(JsonHandler param) throws Fault {
		
		try {
			return run(service, param);
		} catch (Exception e) {
			throw TestingFaultManager.getInstance().exception(e, this);
		}
	}
	
	public abstract Deliverable run(TestingService service,
			 JsonHandler param) throws Exception;

}
