import java.util.ArrayList;

import com.google.firebase.database.ServerValue;

public class DataGenerator {
	public static ArrayList<Data> generateData(int n){
		ArrayList<Data> result = new ArrayList<Data>();
		
		for(int ii = 0; ii < n; ii++)
			result.add(new Data(("testData" + ii),ServerValue.TIMESTAMP));
		
		return result;
	}
}
