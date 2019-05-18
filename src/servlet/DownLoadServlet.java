package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebServlet(urlPatterns = "/download")
public class DownLoadServlet extends HttpServlet{

	private static final long serialVersionUID = 5963066657098135976L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		if(user.getUsername().equals("admin")) {
			String filePath = req.getParameter("filePath");
			String fileName = req.getParameter("fileName");
			FileInputStream fis = new FileInputStream(filePath);
			OutputStream out = resp.getOutputStream();
			
			String userAgent = req.getHeader("User-Agent");
			if(userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				fileName = URLEncoder.encode(fileName,"utf-8");
			} else {
				fileName = new String(fileName.getBytes("utf-8"),StandardCharsets.ISO_8859_1);
			}
			
			resp.setContentType("application/octet-stream");
			resp.addHeader("Content-disposition","filename="+fileName);
			
			int c;
			byte[] bytes = new byte[1024*8];
			while((c = fis.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				out.flush();
			}
			fis.close();
			out.close();
		} else {
			resp.setContentType("text/plain;charset=utf-8");
			resp.getWriter().write("您没有权限进行下载！");
		}
	}
}
