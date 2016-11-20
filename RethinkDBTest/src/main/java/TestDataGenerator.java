import java.util.ArrayList;

public class TestDataGenerator {
	public static ArrayList<TestData> generateData(int n){
		ArrayList<TestData> result = new ArrayList<TestData>();
		
		for(int ii = 0; ii < n; ii++)
			result.add(new TestData(("testData" + ii),System.currentTimeMillis()));
		
		return result;
	}
	
	public static ArrayList<TestData> generateData(int key1, int key2){
		ArrayList<TestData> result = new ArrayList<TestData>();
		
		for(int ii = key1; ii <= key2; ii++)
			result.add(new TestData(("testData" + ii),System.currentTimeMillis()));
		
		return result;
	}
	
	public static ArrayList<TestData> generateUpdateData(int n){
		ArrayList<TestData> result = new ArrayList<TestData>();
		
		for(int ii = 0; ii < n; ii++)
			result.add(new TestData(("testData" + (ii+1000)),System.currentTimeMillis()));
		
		return result;
	}
	
	public static ArrayList<TestData> generateUpdateData(int key1, int key2){
		ArrayList<TestData> result = new ArrayList<TestData>();
		
		for(int ii = key1; ii <= key2; ii++)
			result.add(new TestData(("testData" + (ii+1000)),System.currentTimeMillis()));
		
		return result;
	}
	
}
