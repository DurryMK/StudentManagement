package app.biz;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import app.bean.AddBean;
import app.bean.Clazz;
import app.bean.Student;
import app.bean.Teacher;
import app.service.Impl.UpdataInfoDao;

@Service
public class OperationBiz {

	@Autowired
	private UpdataInfoDao uid;

	public boolean delete(int id, int type) {

		switch (type) {
		case 0:
			return uid.delete(id, "s_student");
		case 1:
			return uid.delete(id, "s_teacher");
		case 2:
			return uid.delete(id, "s_clazz");
		case 3:
			return uid.delete(id, "s_course");
		case 4:
			return uid.delete(id, "s_selected_course");
		case 5:
			return uid.delete(id, "s_score");
		default:
			break;
		}

		return false;
	}

	public boolean add(AddBean ab, int type) {
		switch (type) {
		case 0:
			if (ab.getName().equals("") || ab.getSex().equals("") || ab.getClazz_id().equals(""))
				return false;
			return uid.addStudent(ab);
		case 1:
			if (ab.getName().equals("") || ab.getSex().equals(""))
				return false;
			return uid.addTeacher(ab);
		case 2:
			if (ab.getClazzname().equals(""))
				return false;
			return uid.addClazz(ab);
		case 3:
			if (ab.getCoursename().equals("") || ab.getTeacher_id().equals("") || ab.getMax().equals("")
					|| ab.getTime().equals(""))
				return false;
			return uid.addCourse(ab);
		case 4:
			if (ab.getStudent_id().equals("") || ab.getCourse_id().equals(""))
				return false;
			return uid.addSelect(ab);
		case 5:
			if (ab.getStudent_id().equals("") || ab.getCourse_id().equals("") || ab.getScore().equals(""))
				return false;
			return uid.addScore(ab);
		default:
			break;
		}
		return false;
	}

	public boolean select(int courseid, int id, int type) {
		// 为对应id的学生选择课程号为courseid的课程
		if (type == 1) {
			return uid.addSelectCourse(courseid, id);
		}
		// 为对应id的学生退选课程号为courseid的课程
		if (type == -1) {
			return uid.deleteSelectCourse(courseid, id);
		}
		return false;
	}

	public boolean alterInAdmin(String content, int type) {
		switch (type) {
		case 0:
			Student s = new Gson().fromJson(content, Student.class);
			if (!(s.getSex().equals("男") || s.getSex().equals("女")))
				return false;
			return uid.alterStudentInfo(s);
		case 1:
			Teacher t = new Gson().fromJson(content, Teacher.class);
			if (!(t.getSex().equals("男") || t.getSex().equals("女")))
				return false;
			return uid.alterTeacherInfo(t);
		case 2:
			Clazz c = new Gson().fromJson(content, Clazz.class);
			return uid.alterClazz(c);
		default:
			break;
		}
		return false;
	}
}
