import java.io.FileInputStream;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mollusca.common.util.JsonHandler;
import mollusca.scenario.IScenario;
import mollusca.scenario.ScenarioBuilders;
import mollusca.service.AbstractService;
import mollusca.service.listener.IScenarioListener;


public class TestingService extends AbstractService implements
		IScenarioListener {
	
	public static final String VERSION = "v0.0.1";
	
	private static final String FIREBASE_CREDENTIALS = "trader-530e1-firebase-adminsdk-xcw2w-863e57c040.json";
	private static final String DATABASE_URL = "https://trader-530e1.firebaseio.com";
	
	private static final int MYTHREADS = 100000;
	private FirebaseOptions options;
	private FirebaseDatabase database;
	private DatabaseReference initialReference;
	private DatabaseReference testDataRef;
	private long execCount;
	private long execTime;
	
	public long getExecCount() {
		return execCount;
	}

	public void incrExecCount() {
		this.execCount += 1;
	}

	public long getExecTime() {
		return execTime;
	}

	public void addExecTime(long execTime) {
		this.execTime += execTime;
	}

	public TestingService() {
		setScenarioListener(this);

		setVersion(VERSION);
	}
	
	@Override
	protected void prepare(JsonHandler config) throws Exception {
		super.prepare(config);
		
		options = new FirebaseOptions.Builder()
				  .setServiceAccount(new FileInputStream(FIREBASE_CREDENTIALS))
				  .setDatabaseUrl(DATABASE_URL)
				  .build();
		FirebaseApp.initializeApp(options);
		
		// Get a reference to our users
		database = FirebaseDatabase.getInstance();
		setInitialReference(database.getReference());	
	}

	public void onScenarioReady(IScenario scenario) {
		if (scenario instanceof AbstractTestingScenario) {
			((AbstractTestingScenario) scenario).setService(this);
		}

	}

	@Override
	public ScenarioBuilders createBuilders() {
		ScenarioBuilders builders = new ScenarioBuilders();
		builders.put("insert_firebase", new InsertFirebaseScenarioBuilder());
		builders.put("insert_rethink", new InsertRethinkScenarioBuilder());
		
		return builders;
	}

	public DatabaseReference getInitialReference() {
		return initialReference;
	}

	public void setInitialReference(DatabaseReference initialReference) {
		this.initialReference = initialReference;
	}

}
