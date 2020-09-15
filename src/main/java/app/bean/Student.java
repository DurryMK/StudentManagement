package app.bean;

import java.io.Serializable;

public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -906585928388819052L;
	private Integer id;
	private String name;
	private String pwd;
	private String sn;
	private Integer clazz_id;
	private String className;
	private String sex;
	private String mobile;
	private String qq;

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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getClazz_id() {
		return clazz_id;
	}
	public void setClazz_id(Integer classid) {
		this.clazz_id = classid;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", pwd=" + pwd + ", sn=" + sn + ", classid=" + clazz_id
				+ ", className=" + className + ", sex=" + sex + ", mobile=" + mobile + ", qq=" + qq + "]";
	}

}
