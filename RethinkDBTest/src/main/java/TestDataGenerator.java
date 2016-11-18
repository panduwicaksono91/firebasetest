import java.util.ArrayList;

public class TestDataGenerator {
	public static ArrayList<TestData> generateData(int n){
		ArrayList<TestData> result = new ArrayList<TestData>();
		
		for(int ii = 0; ii < n; ii++)
			result.add(new TestData(("testData" + ii),System.currentTimeMillis()));
		
		return result;
	}
}
