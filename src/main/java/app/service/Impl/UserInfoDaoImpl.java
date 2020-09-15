package app.service.Impl;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import app.bean.Admin;
import app.bean.Clazz;
import app.bean.Course;
import app.bean.Leave;
import app.bean.Score;
import app.bean.Select;
import app.bean.Student;
import app.bean.Summary;
import app.bean.Teacher;
import app.bean.User;

@Repository
public class UserInfoDaoImpl {

	private JdbcTemplate jp;

	@Autowired
	public void setjp(DataSource ds) {
		this.jp = new JdbcTemplate(ds);
	}

	public List<Map<String, Object>> anySql(String sql) {
		try {
			return jp.queryForList(sql);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 发生异常时返回空数据
	List<Object> temp = new LinkedList<Object>();

	/** 获取用户id */
	public Integer getUserInfo(User user, String tablename) {
		try {
			return (int) jp.queryForMap("select * from " + tablename + " where name=? and password=?", user.getName(),
					user.getPwd()).get("id");
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/** 获取用户密码 */
	public String getUserPwd(int type, int id) {
		try {
			switch (type) {
			case 0:
				return jp.queryForMap("select password from s_student where id=?", id).get("password") + "";
			case 1:
				return jp.queryForMap("select password from s_teacher where id=?", id).get("password") + "";
			case 2:
				return jp.queryForMap("select password from s_admin where id=?", id).get("password") + "";
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** id对应的管理员信息 */
	public Admin getAdminInfo(int id) {
		Map<String, Object> m = jp.queryForList("select * from s_admin where id=?", id).get(0);
		Admin a = new Admin();
		a.setId(Integer.parseInt(m.get("id") + ""));
		a.setName(m.get("name") + "");
		a.setStatus(Integer.parseInt(m.get("status") + ""));
		return a;
	}

	/**
	 * 传入的key为空时返回所有学生的信息 key为Integer时返回对应id的学生信息 key为String时返回对应姓名/学号的学生信息
	 */
	public List<Object> getStudentInfo(@Nullable Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			if (key == null) {
				l = jp.queryForList("select * from s_student");
			} else if (key.getClass() == Integer.class) {
				l = jp.queryForList("select * from s_student where id=?", key);
			} else if (key.getClass() == String.class) {
				l = jp.queryForList(
						"select * from s_student where sn like '%" + key + "%' or name like '%" + key + "%'");
			} else {
				return temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Student());
	}

	/**
	 * 传入的key为空时返回所有教师的信息 key为Integer时返回对应id的教师信息 key为String时返回对应姓名/工号的教师信息
	 */
	public List<Object> getTeacherInfo(@Nullable Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			if (key == null) {
				l = jp.queryForList("select * from s_teacher");
			} else if (key.getClass() == Integer.class) {
				l = jp.queryForList("select * from s_teacher where id=?", key);
			} else if (key.getClass() == String.class) {
				l = jp.queryForList(
						"select * from s_teacher where sn like '%" + key + "%' or name like '%" + key + "%'");
			} else {
				return temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Teacher());
	}

	/**
	 * 传入的key为空时返回所有班级的信息 key为Integer时返回对应id的班级信息 key为String时返回对应名称的班级信息
	 */
	public List<Object> getClassInfo(@Nullable Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			if (key == null) {
				l = jp.queryForList("select * from s_clazz");
			} else if (key.getClass() == Integer.class) {
				l = jp.queryForList("select * from s_clazz where id=?", key);
			} else if (key.getClass() == String.class) {
				l = jp.queryForList("select * from s_clazz where name like '%" + key + "%'");
			} else {
				return temp;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return temp;
		}
		return parse(l, new Clazz());
	}

	/**
	 * 传入的key为空时返回所有课程的信息 key为Integer时返回对应id的课程信息 key为String时返回对应名称的课程信息
	 */
	public List<Object> getCourseInfo(@Nullable Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			if (key == null) {
				l = jp.queryForList("select * from s_course");
			} else if (key.getClass() == String.class) {
				l = jp.queryForList("select * from s_course where name like '%" + key + "%'");
			} else if (key.getClass() == Integer.class) {
				l = jp.queryForList("select * from s_course where id=?", key);
			} else {
				return temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Course());
	}

	/**
	 * 传入的key为空时返回所有选课的信息 key为Integer时返回对应id的学生的选课信息 key为String时返回对应名字的学生的选课信息
	 */
	public List<Object> getSelectInfo(@Nullable Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			if (key == null) {
				l = jp.queryForList("select * from s_selected_course");
			} else if (key.getClass() == String.class) {
				List<Map<String, Object>> ids = jp
						.queryForList("select * from s_student where name like '%" + key + "%'");
				for (Map<String, Object> id : ids) {
					List<Map<String, Object>> selectList = jp
							.queryForList("select * from s_selected_course where student_id=?", id.get("id"));
					for (Map<String, Object> m : selectList) {
						l.add(m);
					}
				}
			} else if (key.getClass() == Integer.class) {
				l = jp.queryForList("select * from s_selected_course where student_id=?", key);
			} else {
				return temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Select());
	}

	/**
	 * 传入的key为空时返回所有成绩的信息 key为Integer时返回对应id的学生的成绩信息 key为String时返回对应名字的学生的成绩信息
	 */
	public List<Object> getScoreInfo(@Nullable String rowName, @Nullable Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			if (key == null) {
				l = jp.queryForList("select * from s_score");
			} else if (key.getClass() == String.class) {

				l = jp.queryForList(
						"select * from s_score where student_id in (SELECT id from s_student where name LIKE '%" + key
								+ "%') or course_id in (SELECT id from s_course where name LIKE '%" + key + "%')");
			} else if (key.getClass() == Integer.class) {
				if (rowName.equals("student_id")) {
					l = jp.queryForList("select * from s_score where student_id=?", key);
				} else if (rowName.equals("course_id")) {
					l = jp.queryForList("select * from s_score where course_id=?", key);
				} else {
					return temp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}

		return parse(l, new Score());
	}

	/**
	 * 传入的key为空时返回所有请假的信息 key为Integer时返回对应id的学生的请假信息
	 */
	public List<Object> getLeaveInfo(Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			if (key == null) {
				l = jp.queryForList("select * from s_leave");
			} else if (key.getClass() == Integer.class) {
				l = jp.queryForList("select * from s_leave where student_id=?", key);
			} else {
				return temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Leave());
	}

	/**
	 * flag 为true:返回对应id的学生已选课程 ;false : 返回未选课程
	 */
	public List<Object> getCourseInfo_forStudent(boolean flag, Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			if (flag) {
				l = jp.queryForList(
						"Select * from s_course where id in (select course_id  from s_selected_course where student_id=?)",
						key);
			} else {
				l = jp.queryForList(
						"select * from s_course where id NOT IN (Select id from s_course where id in (select course_id  from s_selected_course where student_id=?))",
						key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Course());
	}

	/**
	 * 返回一个班级内的请假信息
	 */
	public List<Object> getLeaveInfo_InClass(Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			l = jp.queryForList("Select * from s_leave where student_id in (select id from s_student where clazz_id=?)",
					key);
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Leave());
	}

	/**
	 * 返回班级内所有学生信息
	 */
	public List<Object> getStudentInfo_InClass(Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			l = jp.queryForList("select * from s_student where clazz_id=?", key);
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Student());
	}

	/**
	 * 根据名字返回班级内学生信息
	 */
	public List<Object> getStudentInfo_InClass_forName(Object name, Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			l = jp.queryForList("select * from s_student where name like '%" + name + "%'  and clazz_id=" + key);
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Student());
	}

	/** classid为null时 所有课程的所有学生的成绩汇总信息 否则获取一个班级内所有学生的每一门课程的成绩汇总信息 */
	public List<Object> getScoreSummary(@Nullable Integer classid) {
		List<Object> result = new ArrayList<Object>();
		try {
			List<Map<String, Object>> allCourseid = new ArrayList<Map<String, Object>>();
			Map<String, Object> resMap = new HashMap<String, Object>();
			if (classid == null) {
				// 获取所有课程列表
				allCourseid = jp.queryForList("select id from s_course");
				for (Map<String, Object> singleid : allCourseid) {
					resMap = jp.queryForMap(
							"select max(score) max,avg(score) avg,min(score) min from s_score where course_id =?",
							singleid.get("id"));
					Summary s = new Summary();
					s.setName(getCourseName(singleid.get("id") + ""));
					s.setMin(resMap.get("min") + "");
					s.setMax(resMap.get("max") + "");
					s.setAvg(resMap.get("avg") + "");
					result.add(s);
				}
			} else {
				// 获取班级内所有学生选择的课程列表
				allCourseid = jp.queryForList(
						"select DISTINCT course_id id from s_selected_course where student_id in (select id from s_student where clazz_id = ?)",
						classid);
				for (Map<String, Object> singleid : allCourseid) {
					resMap = jp.queryForMap(
							"select max(score) max,avg(score) avg,min(score) min from s_score where course_id =? and student_id in (select id from s_student where clazz_id=?)",
							singleid.get("id"), classid);
					Summary s = new Summary();
					s.setName(getCourseName(singleid.get("id") + ""));
					s.setMin(resMap.get("min") + "");
					s.setMax(resMap.get("max") + "");
					s.setAvg(resMap.get("avg") + "");
					result.add(s);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
	}

	public List<Object> parse(List<Map<String, Object>> l, Object T) {
		List<Object> result = new LinkedList<Object>();
		if (T instanceof Student) {
			for (Map<String, Object> m : l) {
				Student t = new Student();
				t.setId(Integer.parseInt(m.get("id") + ""));
				t.setName(m.get("name") + "");
				t.setClazz_id(Integer.parseInt(m.get("clazz_id") + ""));
				t.setMobile(((m.get("mobile") + "").equals("")) ? "暂无信息" : (m.get("mobile") + ""));
				t.setQq(((m.get("qq") + "").equals("")) ? "暂无信息" : (m.get("qq") + ""));
				t.setSex(m.get("sex") + "");
				t.setSn(m.get("sn") + "");
				t.setClassName(getClazzName(t.getClazz_id()));
				result.add(t);
			}
		} else if (T instanceof Teacher) {
			for (Map<String, Object> m : l) {
				Teacher t = new Teacher();
				t.setId(Integer.parseInt(m.get("id") + ""));
				t.setName(m.get("name") + "");
				t.setClazz_id(Integer.parseInt(m.get("clazz_id") + ""));
				t.setMobile(m.get("mobile") + "");
				t.setQq(m.get("qq") + "");
				t.setSex(m.get("sex") + "");
				t.setSn(m.get("sn") + "");
				t.setClassName(getClazzName(t.getClazz_id()));
				result.add(t);
			}
		} else if (T instanceof Clazz) {
			for (Map<String, Object> m : l) {
				Clazz t = new Clazz();
				t.setId(Integer.parseInt(m.get("id") + ""));
				t.setName(m.get("name") + "");
				t.setInfo(m.get("info") + "");
				if (t.getId() != 0)
					result.add(t);
			}
		} else if (T instanceof Course) {
			for (Map<String, Object> m : l) {
				Course t = new Course();
				t.setId(Integer.parseInt(m.get("id") + ""));
				t.setName(m.get("name") + "");
				t.setCourse_date(m.get("course_date") + "");
				t.setTeacher_id(Integer.parseInt(m.get("teacher_id") + ""));
				t.setTeacherName(getTeacherName(t.getTeacher_id()));
				t.setMax_num(Integer.parseInt(m.get("max_num") + ""));
				t.setSelected_num(Integer.parseInt(m.get("selected_num") + ""));
				t.setInfo(m.get("info") + "");
				result.add(t);
			}
		} else if (T instanceof Score) {
			for (Map<String, Object> m : l) {
				Score t = new Score();
				t.setId(Integer.parseInt(m.get("id") + ""));
				t.setStudent_id(Integer.parseInt(m.get("student_id") + ""));
				t.setSn(getStudentItem("sn", t.getStudent_id()));
				t.setCourse_id(Integer.parseInt(m.get("course_id") + ""));
				t.setCourse(getCourseName(t.getCourse_id() + ""));
				t.setName(getStudentItem("name", t.getStudent_id()));
				t.setScore(Double.parseDouble(m.get("score") + ""));
				t.setRemark(((m.get("remark") + "").equals("null")) ? "暂无评价" : (m.get("remark") + ""));
				result.add(t);
			}
		} else if (T instanceof Select) {
			for (Map<String, Object> m : l) {
				Select t = new Select();
				t.setId(Integer.parseInt(m.get("id") + ""));
				t.setStudent_id(Integer.parseInt(m.get("student_id") + ""));
				t.setCourse_id(Integer.parseInt(m.get("course_id") + ""));
				t.setCourse(getCourseName(t.getCourse_id() + ""));
				t.setName(getStudentItem("name", t.getStudent_id()));
				result.add(t);
			}
		} else if (T instanceof Leave) {
			for (Map<String, Object> m : l) {
				Leave t = new Leave();
				t.setStudent_id(Integer.parseInt(m.get("student_id") + ""));
				t.setId(Integer.parseInt(m.get("id") + ""));
				t.setName(getStudentItem("name", t.getStudent_id()));
				t.setTime(((m.get("time") == null)) ? "无" : m.get("time") + "");
				t.setInfo(m.get("info") + "");
				switch (Integer.parseInt(m.get("status") + "")) {
				case 0:
					t.setIsAgree("未审核");
					break;
				case 1:
					t.setIsAgree("审核通过");
					break;
				case -1:
					t.setIsAgree("审核未通过");
					break;
				default:
					break;
				}
				t.setRemark(((m.get("remark") + "").equals("null")) ? "未回复" : (m.get("remark") + ""));
				result.add(t);
			}
		}
		return result;
	}

	/** 根据学生id获取对应信息 */
	public String getStudentItem(String item, Integer student_id) {
		try {
			return jp.queryForMap("select " + item + " from s_student where id=?", student_id).get(item) + "";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/** 根据学生学号获取对应信息 */
	public String getStudentId_withSN(String sn) {
		try {
			return jp.queryForMap("select id from s_student where sn=?", sn).get("id") + "";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 使用教师id获取教师姓名 */
	public String getTeacherName(Integer teacher_id) {
		try {
			return jp.queryForMap("select name from s_teacher where id=?", teacher_id).get("name") + "";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 使用班级id获取班级名称 */
	public String getClazzName(Integer clazz_id) {
		try {
			return jp.queryForMap("select name from s_clazz where id=?", clazz_id).get("name") + "";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 返回一个班级内所有学生的成绩信息 */
	public List<Object> getScoreInfo_InClass(Object key) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			l = jp.queryForList("Select * from s_score where student_id in (select id from s_student where clazz_id=?)",
					key);
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		}
		return parse(l, new Score());
	}

	/** 根据课程名获取对应id */
	public Integer getCourseId(String course) {
		try {
			return (int) jp.queryForMap("select id from s_course where name=?", course).get("id");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 根据id查课程名称 */
	public String getCourseName(String id) {
		return jp.queryForMap("select name from s_course where id=?", id).get("name") + "";
	}

	

}
