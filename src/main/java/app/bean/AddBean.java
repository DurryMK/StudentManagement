package app.bean;

import java.io.Serializable;

public class AddBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String student_id;
	private String sex;
	private String name;
	private String mobile;
	private String coursename;
	private String course_id;
	private String clazz_id;
	private String clazzname;
	private String teacher_id;
	private String time;
	private String max;
	private String score;
	private String remark;

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getClazz_id() {
		return clazz_id;
	}

	public void setClazz_id(String clazz_id) {
		this.clazz_id = clazz_id;
	}

	public String getClazzname() {
		return clazzname;
	}

	public void setClazzname(String clazzname) {
		this.clazzname = clazzname;
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
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
		return "AddBean [student_id=" + student_id + ", sex=" + sex + ", name=" + name + ", mobile=" + mobile
				+ ", coursename=" + coursename + ", course_id=" + course_id + ", clazz_id=" + clazz_id + ", clazzname="
				+ clazzname + ", teacher_id=" + teacher_id + ", time=" + time + ", max=" + max + ", score=" + score
				+ ", remark=" + remark + "]";
	}
}
