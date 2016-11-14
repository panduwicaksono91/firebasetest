
public class Users {
	public String email;
	public String name;
	public String phone;
	public String username;
	
	public Users(){
		this.email = "";
		this.name = "";
		this.phone = "";
		this.username = "";
	}
	
	public Users(String email, String name, String phone, String username) {
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.username = username;
	}
	
	@Override
	public String toString(){
		return "Email: " + email + ", Name : " + name + ", Phone: " + phone +
				", Username: " + username;
	}

}
