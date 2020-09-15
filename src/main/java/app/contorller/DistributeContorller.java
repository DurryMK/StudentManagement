package app.contorller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.bean.User;
import app.biz.IndexBiz;

@Controller
public class DistributeContorller {
	@Autowired
	private IndexBiz ib;

	@RequestMapping("/index/test")
	public String index() {
		return "/login.html";
	}

	/**
	 * 分发页面
	 * type:0学生;1教师;2管理员;
	 * 
	 */
	@RequestMapping(value = "/index/login")
	public @ResponseBody Map<String, Object> login(String username, String pwd, int type, HttpSession session) {
		User user = new User(username, pwd, type);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("code", ib.isLogin(user, session));
		switch (user.getType()) {
		case 0:
			m.put("html", "/student.html");
			break;
		case 1:
			m.put("html", "/teacher.html");
			break;
		case 2:
			m.put("html", "/admin.html");
			break;
		default:
			break;
		}
		return m;
	}

	/**
	 * 退出
	 * */
	@RequestMapping(value = "/exit")
	public @ResponseBody int adminExit(HttpSession session) {
		if (session.getAttribute("admin") != null) {
			session.removeAttribute("admin");
		}
		if (session.getAttribute("student") != null) {
			session.removeAttribute("student");
		}
		if (session.getAttribute("teacher") != null) {
			session.removeAttribute("teacher");
		}
		return 0;
	}
}
