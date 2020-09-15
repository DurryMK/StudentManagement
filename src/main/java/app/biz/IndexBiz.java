package app.biz;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.User;
import app.service.Impl.UserInfoDaoImpl;

@Service
public class IndexBiz {

	@Autowired
	private UserInfoDaoImpl uidi;
	
	/**判断用户是否能够登录,并且保存可登录的用户的信息*/
	public Integer isLogin(User user, HttpSession session) {
		Integer userid = null;
		switch (user.getType()) {
		case 0:
			userid = uidi.getUserInfo(user, "s_student");
			session.setAttribute("student", (userid==-1)?null:uidi.getStudentInfo(userid).get(0));
			break;
		case 1:
			userid = uidi.getUserInfo(user, "s_teacher");
			session.setAttribute("teacher", (userid==-1)?null:uidi.getTeacherInfo(userid).get(0));
			break;
		case 2:
			userid = uidi.getUserInfo(user, "s_admin");
			session.setAttribute("admin", (userid==-1)?null:uidi.getAdminInfo(userid));
			break;
		default:
			break;
		}
		return (userid==-1)?userid:1;
	}
}
