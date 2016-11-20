import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class RethinkDBListener {
	
	// Constant
	private final static RethinkDB r = RethinkDB.r;
	private final static Connection conn = r.connection().hostname("localhost").port(28015).connect();
	
	public void initializeListener(){
		// how to listen
		Cursor changeCursor = r.table("testData").changes().run(conn);
		
		for (Object change : changeCursor) {
			System.out.println(System.currentTimeMillis() + "," + change.toString());
		}
	}
	
}
