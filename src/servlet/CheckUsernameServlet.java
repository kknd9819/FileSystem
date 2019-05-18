package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;

@WebServlet(urlPatterns = "/checkUsername")
public class CheckUsernameServlet extends HttpServlet{
	private static final long serialVersionUID = 604791361161277461L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		username = username.trim();
		username = username.replaceAll(" ", "");
		UserDao userDao = new UserDaoImpl();
		User user = userDao.findByUsername(username);
		resp.setContentType("application/json;charset=utf-8");
		Map<String,String> map = new HashMap<>();
		
		if(user == null) {
			map.put("msg", "SUCCESS");
		}
		
		resp.getWriter().write(JSON.toJSONString(map));
	}
}
