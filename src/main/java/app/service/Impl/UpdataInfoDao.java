package app.service.Impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.bean.AddBean;
import app.bean.Clazz;
import app.bean.Course;
import app.bean.Leave;
import app.bean.Score;
import app.bean.Student;
import app.bean.Teacher;
import app.tools.Tools;

@Repository
public class UpdataInfoDao {
	private JdbcTemplate jp;

	@Autowired
	public void setjp(DataSource ds) {
		this.jp = new JdbcTemplate(ds);
	}

	/** 修改学生信息 */
	public boolean alterStudentInfo(Student s) {
		try {
			jp.update("update s_student set sn=?,name=?,clazz_id=?,sex=?,mobile=?,qq=? where id=?", s.getSn(),
					s.getName(), s.getClazz_id(), s.getSex(), s.getMobile(), s.getQq(), s.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** 在选课表中删除一条数据 */
	public boolean deleteSelectCourse(int i, int id) {
		try {
			jp.update("delete from s_selected_course where student_id=? and course_id=?", id, i);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/** 在选课表中插入一条数据 */
	public boolean addSelectCourse(int i, int id) {
		try {
			jp.update("insert into s_selected_course(student_id,course_id) value(?,?)", id, i);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/** 在请假表中插入一条数据 */
	public boolean addLeaveInfo(Leave l) {
		try {
			jp.update("insert into s_leave(student_id,time,info,status) values(?,?,?,?)", l.getStudent_id(),
					l.getTime(), l.getInfo(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** 在成绩表中插入一条数据 */
	public boolean addScoreInfo(Score s) {
		try {
			jp.update("insert into s_score(student_id,course_id,score,remark) values(?,?,?,?)", s.getStudent_id(),
					s.getCourse_id(), s.getScore(), s.getRemark());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** 修改指定表名的用户的密码 */
	public boolean alterPwd(String pwd, int id, String tablename) {
		try {
			jp.update("update " + tablename + " set password=? where id=?", pwd, id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** 修改教师信息 */
	public boolean alterTeacherInfo(Teacher t) {
		try {
			jp.update("update s_teacher set sn=?,name=?,clazz_id=?,sex=?,mobile=?,qq=? where id=?", t.getSn(),
					t.getName(), t.getClazz_id(), t.getSex(), t.getMobile(), t.getQq(), t.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**保存请假申请的回复*/
	public boolean replay(Leave l) {
		try {
			jp.update("update s_leave set status=?,remark=? where id=?", l.getStatus(), l.getRemark(), l.getId());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	

	/** 修改班级信息 */
	public boolean alterClazz(Clazz c) {
		try {
			jp.update("update s_clazz  set name=? , info=? where id=?", c.getName(), c.getInfo(), c.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** 修改课程信息 */
	public boolean alterCourse(Course c) {
		try {
			jp.update(
					"update from s_clazz set name=? ,teacher_id=?,course_date=?,selected_num=?,max_num=? , info=? where id=?",
					c.getName(), c.getTeacher_id(), c.getCourse_date(), c.getSelected_num(), c.getMax_num(),
					c.getInfo());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/*** 增加学生 */
	public boolean addStudent(AddBean ab) {
		try {
			String sn = "S" + Tools.createNumber();
			jp.update("insert into s_student(sn,name,password,clazz_id,sex,mobile) value(?,?,?,?,?,?)", sn,
					ab.getName(), sn, (ab.getClazz_id().equals("")) ? 0 : ab.getClazz_id(),
					(ab.getSex().equals("0")) ? "女" : "男", ab.getMobile());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*** 增加教师 */
	public boolean addTeacher(AddBean ab) {
		try {
			String sn = "T" + Tools.createNumber();
			jp.update("insert into s_teacher(sn,name,password,clazz_id,sex,mobile) value(?,?,?,?,?,?)", sn,
					ab.getName(), sn, (ab.getClazz_id().equals("")) ? 0 : ab.getClazz_id(),
					(ab.getSex().equals("0")) ? "女" : "男", ab.getMobile());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*** 增加班级 */
	public boolean addClazz(AddBean ab) {
		try {
			jp.update("insert into s_clazz(name,info) value(?,?)", ab.getClazzname(), ab.getRemark());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*** 增加课程 */
	public boolean addCourse(AddBean ab) {
		try {
			jp.update("insert into s_course(name,teacher_id,course_date,selected_num,max_num,info) value(?,?,?,?,?,?)",
					ab.getCoursename(), ab.getTeacher_id(), ab.getTime(), 0, ab.getMax(), ab.getRemark());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**增加选课信息*/
	public boolean addSelect(AddBean ab) {
		try {
			jp.update("insert into s_selected_course(student_id,course_id) value(?,?)", ab.getStudent_id(),
					ab.getCourse_id());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**增加成绩信息*/
	public boolean addScore(AddBean ab) {
		try {
			jp.update("insert into s_score(student_id,course_id,score,remark) value(?,?,?,?)", ab.getStudent_id(),
					ab.getCourse_id(), ab.getScore(), ab.getRemark());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**删除指定表中指定id的数据*/
	public boolean delete(int id, String tablename) {
		try {
			jp.update("delete from " + tablename + " where id=?", id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/** 删除请假表中的一条数据 */
	public boolean deleteLeave(int lid) {
		try {
			jp.update("delete from s_leave where id=?", lid);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
