package app.bean;

import java.io.Serializable;


public class Select implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8321121207787103367L;
	private Integer id;
	private Integer student_id;
	private Integer course_id;
	private String sn;
	private String name;
	private String course;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}
	public Integer getCourse_id() {
		return course_id;
	}
	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	@Override
	public String toString() {
		return "Select [id=" + id + ", student_id=" + student_id + ", course_id=" + course_id + ", sn=" + sn + ", name="
				+ name + ", course=" + course + "]";
	}
	
	
	
	
}
