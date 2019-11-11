package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SystemUser;

import dao.SystemUserDAO;

/**
 * Servlet implementation class VerifyLogin
 */
@WebServlet("/VerifyLogin")
public class VerifyLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//Îª±ÜÃâÒ»Ð©²»¿ÉÔ¤ÖªµÄÂé·³£¬½ûÖ¹ä¯ÀÀÆ÷»º´æ
		response.setContentType("text/html");  
		response.setHeader("Cache-Control", "no-store");  
		response.setHeader("Pragma", "no-cache");  
		response.setDateHeader("Expires", 0);  
		
		PrintWriter out=response.getWriter();
		
		String username=(String)request.getParameter("username");
		String password=(String)request.getParameter("password");
		if(username!=null){
			SystemUserDAO sysDao=new SystemUserDAO();
			SystemUser user=(SystemUser)sysDao.GetOneEntity(username);
			if(user!=null){
				if(user.GetPassword().equals(password))
					out.write("YES");
				else
					out.write("WRONG_PASSWORD");
			}else{
				out.write("WRONG_USERNAME");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
