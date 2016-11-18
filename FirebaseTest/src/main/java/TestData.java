
public class TestData {
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Object getTestTime() {
		return testTime;
	}

	public void setTestTime(Object testTime) {
		this.testTime = testTime;
	}

	public String testName;
	public Object testTime;
	
	public TestData(){
		this.testName = "";
		this.testTime = "";
	}
	
	public TestData(String testName, Object testTime){
		this.testName = testName;
		this.testTime = testTime;
	}
	
}
