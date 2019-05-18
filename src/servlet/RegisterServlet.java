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

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet{

	private static final long serialVersionUID = 4651920540358014074L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		Map<String,String> map = new HashMap<>();
		
		String username = req.getParameter("username");
		username = username.replaceAll("\\s*", "");
		String password = req.getParameter("password");
		int count = 0;
		
		if(!username.equals("") || !password.equals("")) {
			UserDao userDao = new UserDaoImpl();
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
		    count = userDao.insert(user);
		}
		
		if(count != 0) {
			map.put("msg", "SUCCESS");
		}
		resp.getWriter().write(JSON.toJSONString(map));
	}

}
