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

import dao.*;
import model.*;

/**
 * Servlet implementation class ContractInfoManage
 */
@WebServlet("/ContractInfoManage")
public class ContractInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractInfoManage() {
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
		
		String type=request.getParameter("type");
		Integer pageRecordNum=StatusCode.PAGE_RECORDNUM;
		Integer pageNo=1;
		Integer operatorNo=(Integer)request.getSession().getAttribute("id");
		
		if(type==null){
			//�����ݿ��ȡ���к�ͬ,Ĭ����ʾ��һҳ
			ContractDAO contractdao=new ContractDAO();
			Contract contract=new Contract();
			if(request.getParameter("contractName")!=null){
				
				contract.SetName(new String(request.getParameter("contractName").getBytes("iso-8859-1"),"UTF-8"));
				//contract.SetName(request.getParameter("contractName"));
				request.setAttribute("contractName", contract.GetName());
			}
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}
			
			ArrayList<IEntity> contracts=contractdao.GetEntitySet(contract, pageNo, pageRecordNum);
			ArrayList<Integer> statusCode=new ArrayList<Integer>();
			for(int i=0;i<contracts.size();i++){
				Status temp=new Status();
				temp.SetcontractNo(contracts.get(i).GetId());
				statusCode.add(((Status)new StatusDAO().GetOneEntity(temp)).GetcontractStatus());
			}
			
			
			request.setAttribute("contracts", contracts);
			request.setAttribute("statusCode", statusCode);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageRecordNum", pageRecordNum);
			request.setAttribute("pageNum", contractdao.GetPageNum());
			request.setAttribute("totalRecordNum", contractdao.getRecordNum());
			request.getRequestDispatcher("ContractInfoManage.jsp").forward(request, response);
		}else if(type.equals("detail")){
			//��ȡ��ͬid�������ݿ��ȡ��ͬ��ϸ��Ϣ
//			Integer id=Integer.parseInt(request.getParameter("id"));
//			if(id!=null){
//				ContractDAO contractDao=new ContractDAO();
//				Contract contract=(Contract)contractDao.GetOneEntity(id);
//				String client=((Client)new ClientDAO().GetOneEntity(contract.GetClientNo())).GetName();
//				request.setAttribute("client",client);
//				request.setAttribute("contract",contract);
//				request.getRequestDispatcher("ContractInfoQueryDetail.jsp").forward(request, response);
//			}
		}else if(type.equals("modify_submit")){
			Integer id=Integer.parseInt(request.getParameter("id"));
			String NewContractName=request.getParameter("NewContractName");
			String StartTime=request.getParameter("StartTime");
			String FinishTime=request.getParameter("FinishTime");
			String contractContent=request.getParameter("contractContent");
			if(id!=null){
				ContractDAO contractDao=new ContractDAO();
				Contract contract=(Contract)contractDao.GetOneEntity(id);
				Contract s=new Contract();
				s.SetName(new String(NewContractName.getBytes("iso-8859-1"),"UTF-8"));
				Contract tt=(Contract)contractDao.GetOneEntity(s);
				boolean canModify=false;
				if(tt==null){
					canModify=true;
				}else if(tt.GetId()==id){
					canModify=true;
				}
				if(canModify){
					contract.SetName(new String(NewContractName.getBytes("iso-8859-1"),"UTF-8"));
					contract.SetStartTime(new String(StartTime.getBytes("iso-8859-1"),"UTF-8"));
					contract.SetFinishTime(new String(FinishTime.getBytes("iso-8859-1"),"UTF-8"));
					contract.SetContent(new String(contractContent.getBytes("iso-8859-1"),"UTF-8"));
					if(contractDao.UpdateEntity(contract)){
						SimpleDateFormat currTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
						new LogDAO().AddEntity(new Log(0,operatorNo,"�޸ĺ�ͬ��Ϣ����ͬ��ţ�"+id,currTime.format(new Date())));
						out.write("�޸ĳɹ�!");
					}else{
						out.write("�޸�ʧ��!");
					}
				}else{
					out.write("�ú�ͬ�����Ѵ���!");
				}
				
			}
			
			
		}else if(type.equals("delete")){
			
			//ע�⣺�˴�Ӧɾ��������е����ݣ�contract,attachment,operateFlow,status
			//����ע��д����־��Ϣ���ο�Login.java
			Integer id=Integer.parseInt(request.getParameter("id"));
			if(id!=null){
				ContractDAO contractDao=new ContractDAO();
				Contract contract=(Contract)contractDao.GetOneEntity(id);
				contractDao.DeleteEntity(contract);
				
				AttachmentDAO attachmentDao=new AttachmentDAO();
				Attachment attachment=new Attachment();
				attachment.setContractNo(id);
				
				boolean res01=true;
				boolean res02=true;
				boolean res03=true;
				
				if(attachmentDao.GetOneEntity(attachment)!=null){
					res01=attachmentDao.DeleteEntity(attachment);
				}
				
				OperateFlowDAO operateflowDao=new OperateFlowDAO();
				OperateFlow operateflow=new OperateFlow();
				operateflow.setContractNo(id);
				res02=operateflowDao.DeleteEntity(operateflow);
				
				StatusDAO statusDao=new StatusDAO();
				Status status=new Status();
				status.SetcontractNo(id);
				res03=statusDao.DeleteEntity(status);
				
				if(res01&&res02&&res03){
					SimpleDateFormat currTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
					new LogDAO().AddEntity(new Log(0,operatorNo,"ɾ����ͬ�������Ϣ,��ͬ��ţ�"+id,currTime.format(new Date())));
					
					out.write("ɾ���ɹ���");
				}else{
					out.write("ɾ��ʧ�ܣ�");
				}
			
			}

		}
		
	}

}
