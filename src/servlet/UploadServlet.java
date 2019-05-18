package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import dao.UploadDao;
import dao.impl.UploadDaoImpl;
import model.Upload;
import model.User;

@WebServlet(urlPatterns = "/upload")
public class UploadServlet extends HttpServlet{

	private static final long serialVersionUID = -8279791785237277465L;
	
	// 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final long MAX_FILE_SIZE      = 1024 * 1024 * 1024 * 5L; // 5G
    private static final long MAX_REQUEST_SIZE   = 1024 * 1024 * 1024 * 10L; // 10G
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		// 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
 
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8"); 

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
       
         
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // 解析请求的内容提取文件数据
            List<FileItem> formItems = upload.parseRequest(request);
            String[] limit = new String[]{".rar", ".zip", ".7z"};
  	      	SuffixFileFilter filter = new SuffixFileFilter(limit);
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String uuidName = user.getUsername()+"_"+fileName;
                        String filePath = uploadPath + File.separator + uuidName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        
                        //需要把上传文件的时间和用户名存入数据库
                        UploadDao uploadDao = new UploadDaoImpl();
                        List<Upload> uploads = uploadDao.findByFileNameAndUsername(fileName, user.getUsername());
                        Upload u = new Upload();
                        u.setOriginalName(fileName);
                        u.setUsername(user.getUsername());
                        u.setUploadTime(new Timestamp(System.currentTimeMillis()));
                        u.setUuidName(uuidName);
                        u.setAbsolutePath(storeFile.getAbsolutePath());
                        if(uploads.size() > 0) {
                        	uploadDao.update(u);
                        } else {
                            uploadDao.insert(u);
                        }
                        // 保存文件到硬盘
                        boolean accept = filter.accept(storeFile);
                        if(accept) {
                        	item.write(storeFile);
                            item.delete();
                        } else {
                        	break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 上传成功之后重定向到当前页
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    
	}
}
