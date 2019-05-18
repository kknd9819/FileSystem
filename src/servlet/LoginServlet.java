package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = -154176561953216931L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		//往数据库查
		UserDao userDao = new UserDaoImpl();
		User user = userDao.findByUsernameAndPassword(username, password);
		
		if(user == null) {
			req.setAttribute("error_msg", "用户名或密码错误");
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			resp.sendRedirect(req.getContextPath()+"/index.jsp");
		}
	}
}
