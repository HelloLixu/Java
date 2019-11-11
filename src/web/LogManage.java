package web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import model.*;

/**
 * Servlet implementation class LogManage
 */
@WebServlet("/LogManage")
public class LogManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogManage() {
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
		
		String type=request.getParameter("type");
		Integer pageRecordNum=StatusCode.PAGE_RECORDNUM;
		Integer pageNo=1;
		
		if(type==null)
		{
			request.setAttribute("pageNo", 1);
			request.setAttribute("pageRecordNum", 1);
			request.setAttribute("pageNum", 1);
			request.setAttribute("totalRecordNum", 1);
			request.getRequestDispatcher("LogManage.jsp").forward(request, response);
		}else if(type.equals("query")){
			
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}
			String startTime = request.getParameter("startTime");
			String finishTime = request.getParameter("finishTime");
			request.setAttribute("startTime", startTime);
			request.setAttribute("finishTime", finishTime);
			
			startTime+=" 00:00:00";
			finishTime+=" 23:59:59";
			
		    LogDAO logDAO = new LogDAO();
			
			ArrayList<IEntity> logs=logDAO.GetEntitySet(pageNo, pageRecordNum,startTime,finishTime);
			ArrayList<String> names=new ArrayList<String>();
			
			if(logs!=null){
				for(int i=0;i<logs.size();i++){
					SystemUser user = new SystemUser();
					user.SetId(((Log)logs.get(i)).getOperatorNo());
					SystemUser tt=((SystemUser)new SystemUserDAO().GetOneEntity(user.GetId()));
					String name="";
				if(tt!=null){
						name=tt.GetNickname();
					}else{
						name+=user.GetId();
					}
					names.add(name);
				}
			}
			
			request.setAttribute("names", names);
			request.setAttribute("logs", logs);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageRecordNum", pageRecordNum);
			request.setAttribute("pageNum", logDAO.GetPageNum());
			request.setAttribute("startTimeDate",startTime);
			request.setAttribute("finishTimeDate",finishTime);
			request.setAttribute("totalRecordNum", logDAO.getRecordNum());
			request.getRequestDispatcher("LogManage.jsp").forward(request, response);
		}else if(type.equals("export")){
			String startTime = request.getParameter("startTimeDate");
			String finishTime = request.getParameter("finishTimeDate");
			
			
		    LogDAO logDAO = new LogDAO();
			
			ArrayList<IEntity> logs=logDAO.GetEntitySet(startTime,finishTime);
			
			
			
			for(int i=0;i<logs.size();i++)
			{
				SystemUser tt=((SystemUser)new SystemUserDAO().GetOneEntity(((Log) logs.get(i)).getOperatorNo()));
				String nickname=""+((Log) logs.get(i)).getOperatorNo();
				if(tt!=null){
					nickname=tt.GetNickname();
				}
				
				String url =  ((Log)logs.get(i)).GetId()+"   "+nickname
						      +"	"+((Log)logs.get(i)).getContent()+"	时间："+((Log)logs.get(i)).getTime();
				
				BufferedWriter fw =null;
				try{
					File file = new File("C:/Users/Tracy/Downloads/log.txt");
					fw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8"));
					fw.append(url);
					fw.newLine();
					fw.flush();
					
					request.setAttribute("expotRes","导出成功，文件保存在C:/Users/Tracy/Downloads/log.txt");
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(fw!=null){
						try{
							fw.close();
						}catch(IOException e){
							e.printStackTrace();
						}
						
						
					}
						
				}
			}
			request.setAttribute("pageNo", 1);
			request.setAttribute("pageRecordNum", 1);
			request.setAttribute("pageNum", 1);
			request.setAttribute("totalRecordNum", 1);
			
			request.getRequestDispatcher("LogManage.jsp").forward(request, response);
			
		}
	}

}
