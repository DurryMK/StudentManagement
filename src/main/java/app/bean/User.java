package app.bean;

public class User {
	private String name;
	private String pwd;
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", pwd=" + pwd + ", type=" + type + "]";
	}

	public User(String name, String pwd, Integer type) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.type = type;
	}

	public User() {
	}

}
