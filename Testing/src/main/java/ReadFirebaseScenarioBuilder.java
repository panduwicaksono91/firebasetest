import mollusca.fault.Fault;
import mollusca.scenario.AbstractScenarioBuilder;
import mollusca.scenario.IScenario;

public class ReadFirebaseScenarioBuilder extends AbstractScenarioBuilder {

	@Override
	public IScenario build() throws Fault {
		return new ReadFirebaseScenario();
	}

}
