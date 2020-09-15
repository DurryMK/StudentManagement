package app.bean;

import java.io.Serializable;

public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2798235145361974160L;

	private Integer id;
	private String name;
	private Integer teacher_id;
	private String teacherName;
	private String course_date;
	private Integer selected_num;
	private Integer max_num;
	private String info;

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

	public Integer getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Integer teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getCourse_date() {
		return course_date;
	}

	public void setCourse_date(String course_date) {
		this.course_date = course_date;
	}

	public Integer getSelected_num() {
		return selected_num;
	}

	public void setSelected_num(Integer select_num) {
		this.selected_num = select_num;
	}

	public Integer getMax_num() {
		return max_num;
	}

	public void setMax_num(Integer max_num) {
		this.max_num = max_num;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", teacher_id=" + teacher_id + ", teacherName=" + teacherName
				+ ", course_date=" + course_date + ", select_num=" + selected_num + ", max_num=" + max_num + ", info="
				+ info + "]";
	}

}
