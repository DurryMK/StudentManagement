package app.bean;

import java.io.Serializable;

public class Score implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1343097442468152690L;
	private Integer id;
	private Integer student_id;
	private Integer course_id;
	private Double score;
	private String remark;
	
	private String sn;
	private String name;
	private String course;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer studentid) {
		this.student_id = studentid;
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

	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer courseid) {
		this.course_id = courseid;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Score [id=" + id + ", sn=" + sn + ", studentid=" + student_id + ", name=" + name + ", course=" + course
				+ ", courseid=" + course_id + ", score=" + score + ", remark=" + remark + "]";
	}

}
