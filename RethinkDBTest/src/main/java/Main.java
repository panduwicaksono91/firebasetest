import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlError;
import com.rethinkdb.gen.exc.ReqlQueryLogicError;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class Main {
	
	public static void main(String[] args){
		
		RethinkDBTest rethinkDBTest = new RethinkDBTest();
//		rethinkDBTest.testCreateWithPush(1);
//		rethinkDBTest.testCreateWithSet(10000);
//		rethinkDBTest.testReadSingleKey(7);
//		rethinkDBTest.testReadRangedKey(3,7);
//		rethinkDBTest.testDeleteSingleKey(4);
//		rethinkDBTest.testDeleteRangedKey(5000,5999);
//		rethinkDBTest.testUpdateSingleKey(0);
//		rethinkDBTest.testUpdateRangedKey(0,0);
		
//		for(int ii = 1; ii < 10; ii++)
//			System.out.println((ii*1000) + "-" + (ii*1000+999));
//			rethinkDBTest.testDeleteSingleKey(ii*10,ii);
	
		
		// test delete 10 - 99
//		for(int ii = 1; ii < 10; ii++)
//			rethinkDBTest.testDeleteRangedKey((ii*10),(ii*10+9));
		
		// test delete 100-999
//		for(int ii = 1; ii < 10; ii++)
//			rethinkDBTest.testDeleteRangedKey((ii*100),(ii*100+99));
		
		// test delete 1000-9999
		for(int ii = 1; ii < 10; ii++)
			rethinkDBTest.testDeleteRangedKey((ii*1000),(ii*1000+999));
	}
	
}
