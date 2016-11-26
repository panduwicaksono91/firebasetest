import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlError;
import com.rethinkdb.gen.exc.ReqlQueryLogicError;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class Main {
	
	public static void main(String[] args){
		
		RethinkDBTest rethinkDBTest = new RethinkDBTest();
		rethinkDBTest.testCreateWithPush(1);
//		rethinkDBTest.testReadSingleKey(7);
//		rethinkDBTest.testReadRangedKey(3,7);
//		rethinkDBTest.testDeleteSingleKey(1);
//		rethinkDBTest.testDeleteRangedKey(2,4);
//		rethinkDBTest.testUpdateSingleKey(7);
//		rethinkDBTest.testUpdateRangedKey(0,1);
		
	}
	
}
