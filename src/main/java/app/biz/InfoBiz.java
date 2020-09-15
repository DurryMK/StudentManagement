package app.biz;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import app.service.Impl.UserInfoDaoImpl;

@Service
public class InfoBiz {

	@Autowired
	private UserInfoDaoImpl uidi;

	public List<Object> getSingleTableInfo(int type, @Nullable Object key) {

		List<Object> l = new LinkedList<Object>();

		switch (type) {
		case 0:
			l = uidi.getStudentInfo(key);
			break;
		case 1:
			l = uidi.getTeacherInfo(key);
			break;
		case 2:
			l = uidi.getClassInfo(key);
			break;
		case 3:
			l = uidi.getCourseInfo(key);
			break;
		case 4:
			l = uidi.getSelectInfo(key);
			break;
		case 5:
			l = uidi.getScoreInfo(null, key);
			break;
		case 6:
			l = uidi.getLeaveInfo(key);
			break;
		default:
			break;
		}

		return l;
	}

	public List<Object> getPersonalInfo_Student(int type, int id) {
		// 处理后的结果
		List<Object> result = new ArrayList<Object>();
		switch (type) {
		case 0:// 学生个人信息
			result = uidi.getStudentInfo(id);
			break;
		case 1:// 所有班级信息
			result = uidi.getClassInfo(null);
			break;
		case 2:// 我的选课信息
			result = uidi.getCourseInfo_forStudent(true, id);
			break;
		// 返回id对应的学生未选课程的信息
		case 5:
			result = uidi.getCourseInfo_forStudent(false, id);
			break;
		// 成绩信息
		case 4:
			result = uidi.getScoreInfo("student_id", id);
			break;
		// 请假信息
		case 3:
			result = uidi.getLeaveInfo(id);
			break;
		default:
			break;
		}
		return result;
	}

	public List<Object> getPersonalInfo_Teacher(int type, int tid, int cid) {
		List<Object> result = new ArrayList<Object>();
		switch (type) {
		// 教师个人信息
		case 0:
			result = uidi.getTeacherInfo(tid);
			break;
		// 班内学生信息
		case 1:
			result = uidi.getStudentInfo_InClass(cid);
			break;
		case 2:// 获取班内请假信息
			result = uidi.getLeaveInfo_InClass(cid);
			break;
		case 3:// 班内学生成绩
			result = uidi.getScoreInfo_InClass(cid);
			break;
		case 4:// 获取班内每门课程的成绩汇总信息
			result = uidi.getScoreSummary(cid);
			break;
		default:
			break;
		}
		return result;
	}

}
