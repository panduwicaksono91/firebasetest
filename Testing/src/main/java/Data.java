
public class Data {
	public String name;
	public Object time;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getTime() {
		return time;
	}
	public void setTime(Object time) {
		this.time = time;
	}
	
	public Data(String name, Object time) {
		this.name = name;
		this.time = time;
	}
}
