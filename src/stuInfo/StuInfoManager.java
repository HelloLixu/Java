package stuInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StuInfoManager
 */
@WebServlet("/insertToClass")
public class StuInfoManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public StuInfoManager() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("utf-8"); //处理乱码问题
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Connection con=null;
		//Statement stmt=null;
	    String info="";
	    SqlManager sql=new SqlManager();
		try {
			//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student_manager", "root", "");
			//stmt=con.createStatement();
			//stmt.executeUpdate("");
			
			sql.ConnectMySql();
			
			int succ=sql.GetStatement().executeUpdate("insert into class values('"+request.getParameter("classes")
					+"','"+request.getParameter("faculty")+"','"+request.getParameter("chief_tno")
					+"','"+request.getParameter("stuNum")+"')");
			if(succ==1){
				info+="插入成功!";
			}else{
				info+="插入失败!";
			}
			out.println(info);
/*			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		    //response.sendRedirect("index.jsp");
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//info+=e.getMessage();
			out.println(e.getMessage());
		}
		sql.CloseMySql();
	    
	    
	    
	}

}
