package app.contorller;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import app.bean.AddBean;
import app.bean.Admin;
import app.bean.Leave;
import app.bean.Score;
import app.bean.Student;
import app.bean.Summary;
import app.bean.Teacher;
import app.biz.InfoBiz;
import app.biz.OperationBiz;
import app.service.Impl.UpdataInfoDao;
import app.service.Impl.UserInfoDaoImpl;

@Controller
public class OperationContorller {
	@Autowired
	private OperationBiz ob;

	@Autowired
	private InfoBiz ib;

	@Autowired
	private UpdataInfoDao uid;

	@Autowired
	private UserInfoDaoImpl uidi;

	@RequestMapping("/op/admin/delete")//管理员删除信息
	public @ResponseBody Map<String, Integer> deleteList(String[] list, int type) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		int success = 0;
		int failure = 0;
		for (String id : list) {
			if (ob.delete(Integer.parseInt((id.replace("]", "")).replace("[", "")), type)) {
				success++;
			} else {
				failure++;
			}
		}
		m.put("success", success);
		m.put("failure", failure);
		return m;
	}

	@RequestMapping(value = "/op/admin/add")//管理员添加用户信息
	public @ResponseBody Map<String, Integer> add(String list, int type) {
		AddBean ab = new Gson().fromJson(list, AddBean.class);
		Map<String, Integer> m = new HashMap<String, Integer>();
		m.put("code", 0);
		if (!ob.add(ab, type)) {
			m.put("code", -1);
		}
		return m;
	}

	@RequestMapping(value = "/op/alterPersonalInfo")//用户修改个人信息
	public @ResponseBody Map<String, Integer> alterPersonalInfo(String content, int type) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		int success = 0;
		int failure = 0;
		if (type == 0) {
			Student s = new Gson().fromJson((content.replace("[", "")).replace("]", ""), Student.class);
			if (uid.alterStudentInfo(s)) {
				success++;
			} else {
				failure++;
			}
		}
		if (type == 1) {
			Teacher t = new Gson().fromJson((content.replace("[", "")).replace("]", ""), Teacher.class);
			if (uid.alterTeacherInfo(t)) {
				success++;
			} else {
				failure++;
			}
		}
		m.put("success", success);
		m.put("failure", failure);
		return m;
	}

	@RequestMapping(value = "op/admin/alterInfo")//管理员修改用户信息
	public @ResponseBody Map<String, Integer> adminalterInfo(String content, int type) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		try {
			if (ob.alterInAdmin(content, type))
				m.put("code", 1);
			else
				m.put("code", -1);
		} catch (Exception e) {
			e.printStackTrace();
			m.put("code", -1);
		}
		return m;
	}

	@RequestMapping(value = "/op/subSelect") // 提交选课结果
	public @ResponseBody Map<String, Integer> subSelect(String willselectList, String willUnselectList, int id) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		int success = 0;
		int failure = 0;

		String[] wsl = ((willselectList.replace("[", "")).replace("]", "")).split(",");// 选择的课程id列表
		String[] wusl = ((willUnselectList.replace("[", "")).replace("]", "")).split(",");// 退选的课程id列表
		// 选课
		for (String s : wsl) {
			try {
				if (ob.select(Integer.parseInt(s), id, 1)) {
					success++;
				} else {
					failure++;
				}
			} catch (Exception e) {
				continue;
			}
		}
		// 退选
		for (String s : wusl) {
			try {
				if (ob.select(Integer.parseInt(s), id, -1)) {
					success++;
				} else {
					failure++;
				}
			} catch (Exception e) {
				continue;
			}
		}
		m.put("success", success);
		m.put("failure", failure);
		return m;
	}

	@RequestMapping(value = "/op/alterPassword") // 修改用户密码
	public @ResponseBody Map<String, Integer> alterPassword(String newpwd, String pwd, int type, HttpSession session) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		try {
			// 修改学生密码
			if (type == 0) {
				Student stu = (Student) session.getAttribute("student");
				String oldpwd = uidi.getUserPwd(type, stu.getId());
				if (!oldpwd.equals(pwd)) {
					m.put("code", -1);
				} else {
					if (uid.alterPwd(newpwd, stu.getId(), "s_student")) {
						m.put("code", 1);
					} else {
						m.put("code", -1);
					}
				}
				return m;
			}
			// 修改教师密码
			if (type == 1) {
				Teacher tea = (Teacher) session.getAttribute("teacher");
				String oldpwd = uidi.getUserPwd(type, tea.getId());
				if (!oldpwd.equals(pwd)) {
					m.put("code", -1);
				} else {
					if (uid.alterPwd(newpwd, tea.getId(), "s_teacher")) {
						m.put("code", 1);
					} else {
						m.put("code", -1);
					}
				}
				return m;
			}

			// 修改管理员密码
			if (type == 2) {
				Admin adm = (Admin) session.getAttribute("admin");
				String oldpwd = uidi.getUserPwd(type, adm.getId());
				if (!oldpwd.equals(pwd)) {
					m.put("code", -1);
				} else {
					if (uid.alterPwd(newpwd, adm.getId(), "s_admin")) {
						m.put("code", 1);
					} else {
						m.put("code", -1);
					}
				}
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("code", -1);
			return m;
		}
		m.put("code", -1);
		return m;
	}

	@RequestMapping(value = "/op/delete/leave")
	public @ResponseBody Map<String, Integer> leave(int lid) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		if (uid.deleteLeave(lid)) {
			m.put("code", 1);
		} else {
			m.put("code", -1);
		}
		return m;
	}

	@RequestMapping(value = "/op/student/addleave") // 学生的请假申请
	public @ResponseBody Map<String, Integer> leave(String info, String time, HttpSession session) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		Student stu = (Student) session.getAttribute("student");
		int success = 0;
		int failure = 0;
		Leave l = new Leave();
		l.setStudent_id(stu.getId());
		l.setInfo(info);
		l.setTime(time);
		System.out.println(stu);
		if (uid.addLeaveInfo(l)) {
			success++;
		} else {
			failure++;
		}
		m.put("success", success);
		m.put("failure", failure);
		return m;
	}

	@RequestMapping(value = "/op/teacher/replay") // 请假批复
	public @ResponseBody Map<String, Integer> replay(int id, String content, int flag) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		int success = 0;
		int failure = 0;
		Leave l = new Leave();
		l.setId(id);
		l.setStatus(flag);
		l.setRemark(content);
		if (uid.replay(l)) {
			success++;
		} else {
			failure++;
		}
		m.put("success", success);
		m.put("failure", failure);
		return m;
	}

	@RequestMapping(value = "/op/student/exportScore") // 学生导出成绩
	public void exportScore(int id, HttpServletResponse response, HttpSession session) {
		try {
			Student stu = (Student) session.getAttribute("student");

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("成绩单");
			String fileName = uidi.getStudentItem("name", stu.getId()) + "的成绩单" + "-" + new Date().getTime();// 设置要导出的文件的名字
			int rowNum = 1;
			String[] headers = { "序号", "姓名", "课程", "成绩", "备注" };
			// headers表示excel表中第一行的表头
			HSSFRow row = sheet.createRow(0);
			// 在excel表中添加表头
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
			int seriz = 1;
			// 在表中存放查询到的数据放入对应的列
			for (Object o : uidi.getScoreInfo("student_id", id)) {
				Score s = (Score) o;
				HSSFRow row1 = sheet.createRow(rowNum);
				row1.createCell(0).setCellValue(seriz);
				row1.createCell(1).setCellValue(s.getName());
				row1.createCell(2).setCellValue(s.getCourse());
				row1.createCell(3).setCellValue(s.getScore());
				row1.createCell(4).setCellValue(s.getRemark());
				rowNum++;
				seriz++;
			}
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/octet-stream");
			response.flushBuffer();
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/op/teacher/exportStudentInfo")//教师导出学生信息
	public void exportStudentInfo(int cid, HttpServletResponse response) {
		try {
			List<Object> lo = ib.getPersonalInfo_Teacher(1, 0, cid);
			List<Student> ls = new ArrayList<Student>();
			for (Object o : lo) {
				Student s = (Student) o;
				ls.add(s);
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("班级花名册");
			String fileName = ls.get(0).getClassName() + "的花名册" + "-" + new Date().getTime();// 设置要导出的文件的名字
			int rowNum = 1;
			String[] headers = { "序号", "学号", "姓名", "性别", "电话", "QQ" };
			// headers表示excel表中第一行的表头
			HSSFRow row = sheet.createRow(0);
			// 在excel表中添加表头
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
			int seriz = 1;
			// 在表中存放查询到的数据放入对应的列
			for (Student s : ls) {
				HSSFRow row1 = sheet.createRow(rowNum);
				row1.createCell(0).setCellValue(seriz);
				row1.createCell(1).setCellValue(s.getSn());
				row1.createCell(2).setCellValue(s.getName());
				row1.createCell(3).setCellValue(s.getSex());
				row1.createCell(4).setCellValue(s.getMobile());
				row1.createCell(5).setCellValue(s.getQq());
				rowNum++;
				seriz++;
			}
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/octet-stream");
			response.flushBuffer();
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/op/teacher/exportStudentScore") // 导出学生成绩
	public void exportStudentScore(HttpSession session, HttpServletResponse response) {
		try {
			int cid = ((Teacher) session.getAttribute("teacher")).getClazz_id();
			List<Object> lo = ib.getPersonalInfo_Teacher(3, 0, cid);
			List<Score> ls = new ArrayList<Score>();
			for (Object o : lo) {
				Score s = (Score) o;
				ls.add(s);
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("班级成绩信息");
			String fileName = uidi.getClazzName(cid) + "的成绩单" + "-" + new Date().getTime();// 设置要导出的文件的名字
			int rowNum = 1;
			String[] headers = { "序号", "学号", "姓名", "课程", "分数", "评价" };
			// headers表示excel表中第一行的表头
			HSSFRow row = sheet.createRow(0);
			// 在excel表中添加表头
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
			int seriz = 1;
			// 在表中存放查询到的数据放入对应的列
			for (Score s : ls) {
				HSSFRow row1 = sheet.createRow(rowNum);
				row1.createCell(0).setCellValue(seriz);
				row1.createCell(1).setCellValue(s.getSn());
				row1.createCell(2).setCellValue(s.getName());
				row1.createCell(3).setCellValue(s.getCourse());
				row1.createCell(4).setCellValue(s.getScore());
				row1.createCell(5).setCellValue(s.getRemark());
				rowNum++;
				seriz++;
			}
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/octet-stream");
			response.flushBuffer();
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/op/teacher/exportSummary") // 导出学生成绩统计
	public void exportSummary(HttpSession session, HttpServletResponse response) {
		try {
			int cid = ((Teacher) session.getAttribute("teacher")).getClazz_id();
			List<Object> lo = ib.getPersonalInfo_Teacher(4, 0, cid);
			List<Summary> ls = new ArrayList<Summary>();
			for (Object o : lo) {
				Summary s = (Summary) o;
				ls.add(s);
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("班级成绩统计");
			String fileName = uidi.getClazzName(cid) + "的成绩统计" + "-" + new Date().getTime();// 设置要导出的文件的名字
			int rowNum = 1;
			String[] headers = { "序号", "课程", "最高分", "最低分", "平均分" };
			// headers表示excel表中第一行的表头
			HSSFRow row = sheet.createRow(0);
			// 在excel表中添加表头
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
			int seriz = 1;
			// 在表中存放查询到的数据放入对应的列
			for (Summary s : ls) {
				HSSFRow row1 = sheet.createRow(rowNum);
				row1.createCell(0).setCellValue(seriz);
				row1.createCell(1).setCellValue(s.getName());
				row1.createCell(2).setCellValue(s.getMax());
				row1.createCell(3).setCellValue(s.getMin());
				row1.createCell(4).setCellValue(s.getAvg());
				rowNum++;
				seriz++;
			}
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/octet-stream");
			response.flushBuffer();
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/op/teacher/importStudentScore")//教师导入学生成绩
	public @ResponseBody Map<String, Integer> importStudentScore(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		int success = 0;
		int failure = 0;
		if (file.isEmpty()) {
			m.put("code", 0);
		}
		try {
			// 根据路径获取这个操作excel的实例
			HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheet = wb.getSheetAt(0);

			// 实体类集合
			List<Score> scores = new ArrayList<>();
			HSSFRow row = null;
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				// 获取每一行数据
				row = sheet.getRow(i);
				Score s = new Score();
				s.setSn(row.getCell(1) + "");
				s.setStudent_id(Integer.parseInt(uidi.getStudentId_withSN(s.getSn())));
				s.setName(row.getCell(2) + "");
				s.setCourse(row.getCell(3) + "");
				s.setCourse_id(uidi.getCourseId(s.getCourse()));
				s.setScore(Double.parseDouble(row.getCell(4) + ""));
				s.setRemark(row.getCell(5) + "");
				// 不重复读入数据库
				List<Map<String, Object>> result = uidi.anySql("select * from s_score where student_id="
						+ s.getStudent_id() + " and course_id=" + s.getCourse_id());
				if (result.size() == 0 || result == null) {
					scores.add(s);
				}
			}
			for (Score s : scores) {
				System.out.println(s);
				AddBean ab = new AddBean();
				ab.setScore(s.getScore()+"");
				ab.setStudent_id(s.getStudent_id()+"");
				ab.setCourse_id(s.getCourse_id()+"");
				ab.setRemark(s.getRemark());
				if (uid.addScore(ab)) {
					success++;
				} else {
					failure++;
				}
			}
			m.put("code", 1);
			m.put("success", success);
			m.put("failure", failure);
		} catch (Exception e) {
			System.out.println("Contorller:");
			e.printStackTrace();
			m.put("code", -1);
		}
		return m;
	}
}
