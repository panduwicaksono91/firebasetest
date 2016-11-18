import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlError;
import com.rethinkdb.gen.exc.ReqlQueryLogicError;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class Main {
	
	public static void main(String[] args){
		
		RethinkDBTest rethinkDBTest = new RethinkDBTest();
		rethinkDBTest.testInputWithPush(10);

		// how to listen
//		Cursor changeCursor = r.table("tv_shows").changes().run(conn);
//		long time = System.currentTimeMillis();
//		
//		for (Object change : changeCursor) {
//			time = System.currentTimeMillis();
//			System.out.println("Time: " + time);
//			System.out.println(change);
//		}
		
	}
	
}
