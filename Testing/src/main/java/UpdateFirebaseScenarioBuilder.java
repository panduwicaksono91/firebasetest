import mollusca.fault.Fault;
import mollusca.scenario.AbstractScenarioBuilder;
import mollusca.scenario.IScenario;

public class UpdateFirebaseScenarioBuilder extends AbstractScenarioBuilder {

	@Override
	public IScenario build() throws Fault {
		// TODO Auto-generated method stub
		return new UpdateFirebaseScenario();
	}

}
