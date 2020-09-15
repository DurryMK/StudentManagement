package app.contorller;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.bean.Admin;
import app.bean.Student;
import app.bean.Teacher;

@Controller
public class ValidationContorller {

	@RequestMapping(value = "/admin/validation")
	public @ResponseBody Map<String, Object> validation_a(HttpSession session) {
		Map<String, Object> m = new HashMap<String, Object>();
		if (session.getAttribute("admin") == null) {
			m.put("code", -1);
			return m;
		}
		m.put("code", 0);
		m.put("admin", (Admin) session.getAttribute("admin"));
		return m;
	}

	@RequestMapping(value = "/student/validation")
	public @ResponseBody Map<String, Object> validation_s(HttpSession session) {
		Map<String, Object> m = new HashMap<String, Object>();
		if (session.getAttribute("student") == null) {
			m.put("code", -1);
			return m;
		}
		m.put("code", 0);
		m.put("student", (Student) session.getAttribute("student"));
		return m;
	}

	@RequestMapping(value = "/teacher/validation")
	public @ResponseBody Map<String, Object> validation_t(HttpSession session) {
		Map<String, Object> m = new HashMap<String, Object>();
		if (session.getAttribute("teacher") == null) {
			m.put("code", -1);
			return m;
		}
		m.put("code", 0);
		m.put("teacher", (Teacher) session.getAttribute("teacher"));
		return m;
	}
}
