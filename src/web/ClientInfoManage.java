package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Client;
import model.Contract;
import model.IEntity;
import model.Log;
import model.Status;
import model.StatusCode;
import dao.ClientDAO;
import dao.ContractDAO;
import dao.LogDAO;
import dao.StatusDAO;

/**
 * Servlet implementation class ClientInfoManage
 */
@WebServlet("/ClientInfoManage")
public class ClientInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientInfoManage() {
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
		PrintWriter out=response.getWriter();
		Integer operatorNo=(Integer)request.getSession().getAttribute("id");
		
		String type=request.getParameter("type");
		Integer pageRecordNum=StatusCode.PAGE_RECORDNUM;
		Integer pageNo=1;
		
		if(type==null){
			//从数据库读取所有合同,默认显示第一页
			ClientDAO clientdao=new ClientDAO();
			Client client=new Client();
			if(request.getParameter("keyName")!=null){
				
				client.SetName(new String(request.getParameter("keyName").getBytes("iso-8859-1"),"UTF-8"));
				//contract.SetName(request.getParameter("contractName"));
				request.setAttribute("keyName", client.GetName());
			}
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}
			
			ArrayList<IEntity> clients=clientdao.GetEntitySet(client, pageNo, pageRecordNum);
			
			request.setAttribute("clients", clients);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageRecordNum", pageRecordNum);
			request.setAttribute("pageNum", clientdao.GetPageNum());
			request.setAttribute("totalRecordNum", clientdao.getRecordNum());
			request.getRequestDispatcher("ClientInfoManage.jsp").forward(request, response);
		}else if(type.equals("modify_submit")){
			Integer id=Integer.parseInt(request.getParameter("id"));
			String NewClientName=request.getParameter("NewClientName");
			String NewTel=request.getParameter("NewTel");
			String NewFax=request.getParameter("NewFax");
			String NewPostCode=request.getParameter("NewPostCode");
			String NewBankName=request.getParameter("NewBankName");
			String NewBankAccount=request.getParameter("NewBankAccount");
			if(id!=null){
				ClientDAO clientDao=new ClientDAO();
                Client Client=(Client)clientDao.GetOneEntity(id);

				
				Client.SetName(new String(NewClientName.getBytes("iso-8859-1"),"UTF-8"));
				Client.SetTel(new String(NewTel.getBytes("iso-8859-1"),"UTF-8"));
				Client.SetFax(new String(NewFax.getBytes("iso-8859-1"),"UTF-8"));
				Client.SetPostCode(new String(NewPostCode.getBytes("iso-8859-1"),"UTF-8"));
				Client.SetBankName(new String(NewBankName.getBytes("iso-8859-1"),"UTF-8"));
				Client.SetBankAccount(new String(NewBankAccount.getBytes("iso-8859-1"),"UTF-8"));
				
				
				if(clientDao.UpdateEntity(Client)){
					SimpleDateFormat currTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					new LogDAO().AddEntity(new Log(0,operatorNo,"修改客户信息,客户号："+id,currTime.format(new Date())));
					out.write("修改成功");
				}else
					out.write("修改失败");

			}else
				out.write("修改失败");
		}else if(type.equals("delete")){
			//注意：删除客户时  我们只删除客户本身，不删除其他，跟我们之前说的不太一样
			Integer id=Integer.parseInt(request.getParameter("id"));
			ClientDAO clientDao=new ClientDAO();
			Client client=(Client)clientDao.GetOneEntity(id);
			if(clientDao.DeleteEntity(client)){
				SimpleDateFormat currTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				new LogDAO().AddEntity(new Log(0,operatorNo,"删除客户信息,客户号："+id,currTime.format(new Date())));
				out.write("删除成功!");
			}else{
				out.write("删除失败!");
			}
			
		}else if(type.equals("add")){
			
			String NewClientName=request.getParameter("NewClientName");
			String NewTel=request.getParameter("NewTel");
			String NewFax=request.getParameter("NewFax");
			String NewPostCode=request.getParameter("NewPostCode");
			String NewBankName=request.getParameter("NewBankName");
			String NewBankAccount=request.getParameter("NewBankAccount");
			String NewAddress=request.getParameter("NewAddress");
			
			ClientDAO clientDao=new ClientDAO();
			Client client=new Client();
			client.SetName(new String(NewClientName.getBytes("iso-8859-1"),"UTF-8"));
		
			if(clientDao.GetOneEntity(client)==null){		
				client.SetAddress(new String(NewAddress.getBytes("iso-8859-1"),"UTF-8"));
			    client.SetTel(new String(NewTel.getBytes("iso-8859-1"),"UTF-8"));
			    client.SetFax(new String(NewFax.getBytes("iso-8859-1"),"UTF-8"));
			    client.SetPostCode(new String(NewPostCode.getBytes("iso-8859-1"),"UTF-8"));
			    client.SetBankName(new String(NewBankName.getBytes("iso-8859-1"),"UTF-8"));
			    client.SetBankAccount(new String(NewBankAccount.getBytes("iso-8859-1"),"UTF-8"));
			    
			    if(clientDao.AddEntity(client)){
			    	SimpleDateFormat currTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					new LogDAO().AddEntity(new Log(0,operatorNo,"添加新客户",currTime.format(new Date())));
					out.write("添加客户成功!");
			    }else{
			    	out.write("添加客户失败!");
			    }

		    }else{
		    	out.write("客户已存在！");
	        } 
		}
	}

}
