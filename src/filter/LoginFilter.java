package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//获取请求地址的URI 就是请求地址栏地址
		String uri = request.getRequestURI();
		
		//保证中文不会乱码
		request.setCharacterEncoding("utf-8");
		
		//如果请求地址栏里面包含login || login.jsp 就放行
		//说明是一个登录请求
		if(uri.contains("/login") || uri.contains("/login.jsp")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if(uri.contains("/register.html") || uri.contains("/register") || uri.contains("/checkUsername")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if(uri.contains("/static")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null) {
			//表示他没有登录
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		} else {
			//表示他已经登录过了
			filterChain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
