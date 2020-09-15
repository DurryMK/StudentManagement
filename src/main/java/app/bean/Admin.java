package app.bean;

import java.io.Serializable;

public class Admin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Integer id;
	private String name;
	private String pwd;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", name=" + name + ", pwd=" + pwd + ", status=" + status + "]";
	}

}
