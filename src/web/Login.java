package web;

import dao.*;
import model.*;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		SystemUserDAO userDao=new SystemUserDAO();
		SystemUser user=(SystemUser)userDao.GetOneEntity(request.getParameter("userName"));
		if(user!=null){
			if(user.GetPassword().equals(request.getParameter("password"))){
				
				RolePermissionDAO rolePermissionDao=new RolePermissionDAO();
				RolePermission rolePermission=new RolePermission();
				rolePermission.setRoleNo(user.GetRoleNo());
				ArrayList<IEntity> arrEntity=rolePermissionDao.GetEntitySet(rolePermission);
				if(arrEntity!=null){
					if(arrEntity.size()==0){
						request.setAttribute("result", StatusCode.ERROR_NORIGHT);
						request.getRequestDispatcher("Login.jsp").forward(request, response);
					}else{
						ArrayList<String> rightCode=new ArrayList<String>();
						
						for(int i=0;i<arrEntity.size();i++){
							rightCode.add(((Permission)new PermissionDAO().GetOneEntity(((RolePermission)arrEntity.get(i)).getPermissionNo())).GetCode());
						}
						
						//登录信息写入日志
						SimpleDateFormat currTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						new LogDAO().AddEntity(new Log(0,user.GetId(),"登录系统",currTime.format(new Date())));
						
						
						HttpSession session=request.getSession(true);
						session.setAttribute("id", user.GetId());
						session.setAttribute("username", user.GetUsername());
						session.setAttribute("nickname", user.GetNickname());
						session.setAttribute("rightCode", rightCode);
						request.getRequestDispatcher("HomePage").forward(request, response);
					}
				}
			}else{
				request.setAttribute("result", StatusCode.ERROR_WRONGPASSWORD);
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
		}else{
			request.setAttribute("result", StatusCode.ERROR_NOUSERNAME);
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
		
	}

}
