package app.contorller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.bean.Leave;
import app.bean.Score;
import app.bean.Student;
import app.bean.Teacher;
import app.biz.InfoBiz;
import app.service.Impl.UserInfoDaoImpl;

@Controller
public class InfoContorller {

	@Autowired
	private InfoBiz ib;

	@Autowired
	private UserInfoDaoImpl uidi;

	@RequestMapping("/info/singleTableInfo")
	public @ResponseBody List<Object> singleTableInfo(int type) {
		List<Object> l = ib.getSingleTableInfo(type, null);
		return l;
	}

	/****************** 获取用户个人信息 **********************/

	// *****学生******/
	@RequestMapping("/info/getPersonalInfo_Student")
	public @ResponseBody List<Object> getPersonalInfo(int type, HttpSession session) {
		try {
			Student stu = (Student) session.getAttribute("student");
			return ib.getPersonalInfo_Student(type, stu.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// *******教师******/
	@RequestMapping("/info/getPersonalInfo_Teacher")//个人信息
	public @ResponseBody List<Object> getPersonalInfo_Teacher(int type, HttpSession session) {
		try {
			Teacher tea = (Teacher) session.getAttribute("teacher");
			return ib.getPersonalInfo_Teacher(type, tea.getId(), tea.getClazz_id());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/info/teacher/leaveInClassNum")//请假数
	public @ResponseBody int leaveInfoInClassNum(HttpSession session) {
		List<Object> leaveInClass = uidi
				.getLeaveInfo_InClass(((Teacher) session.getAttribute("teacher")).getClazz_id());
		int num = 0;
		for (Object o : leaveInClass) {
			if (((Leave) o).getIsAgree().equals("未审核"))
				num++;
		}
		return num;
	}

	// ********************搜索信息*************************/
	@RequestMapping(value = "/search/clazz") // 搜索班级信息
	public @ResponseBody List<Object> searchclazz(String key) {
		try {
			if (key.trim().equals("")) {
				return uidi.getClassInfo(null);
			} else {
				return uidi.getClassInfo(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/search/select_course") // 搜索选课信息
	public @ResponseBody List<Object> searchselect_course(String key) {
		try {
			if (key.trim().equals("")) {
				return uidi.getCourseInfo(null);
			} else {
				return uidi.getCourseInfo(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/search/student/score_of_student") // 在某学生的成绩信息中查找某一条
	public @ResponseBody List<Object> score_of_student(String key, HttpSession session) {
		try {
			Student stu = (Student) session.getAttribute("student");
			String sql = "select * from s_score where course_id in (select id from s_course where name LIKE '%" + key
					+ "%') and student_id =" + stu.getId();
			if (key.trim().equals("")) {
				return uidi.getScoreInfo("student_id", stu.getId());
			} else {
				return uidi.parse(uidi.anySql(sql), new Score());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value = "/search/teacher/studentInClass") // 查找班级内学生信息
	public @ResponseBody List<Object> searchstudentInClass(String key, HttpSession session) {
		try {
			int cid = ((Teacher)session.getAttribute("teacher")).getClazz_id();
			if (key.trim().equals("")) {
				return uidi.getStudentInfo_InClass(cid);
			} else {
				return uidi.getStudentInfo_InClass_forName(key, cid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value = "/info/admin/search") //管理员端搜索数据
	public @ResponseBody List<Object> adminsearch(String key,int type) {
		try {
			if (key.trim().equals("")) {
				return ib.getSingleTableInfo(type, null);
			} else {
				return ib.getSingleTableInfo(type ,key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/info/admin/getDeleteList")//管理员端获取可被删除的数据
	public @ResponseBody List<Object> getDeleteList(int type) {
		return ib.getSingleTableInfo(type, null);
	}
}
