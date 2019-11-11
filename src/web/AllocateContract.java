package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import model.*;


/**
 * Servlet implementation class AllocateContract
 */
@WebServlet("/AllocateContract")
public class AllocateContract extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllocateContract() {
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
		//request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String type=request.getParameter("type");
		if(type==null){
			//从数据库读取刚起草的合同
			Integer pageNo=1;
			Integer pageRecordNum=StatusCode.PAGE_RECORDNUM;
			
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}
			if(request.getParameter("pageRecordNum")!=null){
				pageRecordNum=Integer.parseInt(request.getParameter("pageRecordNum"));
			}
			
			StatusDAO statusDao=new StatusDAO();
			Status status=new Status();
			status.SetcontractStatus(StatusCode.STATUS_FINISH_DRAFT);
			if(request.getParameter("contractName")!=null){
				String contractName=new String(request.getParameter("contractName").getBytes("iso-8859-1"),"UTF-8");
				Contract tempContract=new Contract();
				tempContract.SetName(contractName);
				int id=((Contract)new ContractDAO().GetOneEntity(tempContract)).GetId();
				status.SetcontractNo(id);
			}
			
			ArrayList<IEntity> arr=statusDao.GetEntitySet(status, pageNo, pageRecordNum);
			ArrayList<Contract> contracts=new ArrayList<Contract>();
			ArrayList<String> drafter=new ArrayList<String>();
			
			if(arr!=null){
				for(int i=0;i<arr.size();i++){
					Contract contract=(Contract)(new ContractDAO().GetOneEntity(((Status)arr.get(i)).GetcontractNo()));
					contracts.add(contract);
					drafter.add(((SystemUser)new SystemUserDAO().GetOneEntity(contract.GetDrafterNo())).GetNickname());
				}
			}
			request.setAttribute("contracts", contracts);
			request.setAttribute("drafter", drafter);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageRecordNum", pageRecordNum);
			request.setAttribute("pageNum", statusDao.GetPageNum());
			request.getRequestDispatcher("AllocateContract.jsp").forward(request, response);
		}else if(type.equals("detail")){
			//获取合同id，从数据库读取人员信息，显示分配界面
			Integer id=Integer.parseInt(request.getParameter("id"));
			if(id!=null){
				ContractDAO contractDAO = new ContractDAO();
				Contract contracts = (Contract)contractDAO.GetOneEntity(id);
				
			
				RolePermissionDAO rolePermissionDAO= new RolePermissionDAO();
				RolePermission rolePermission = new RolePermission();
				rolePermission.setPermissionNo(8);
				//从数据库读取人员信息
				ArrayList<IEntity> hqArr=rolePermissionDAO.GetEntitySet(rolePermission);
				Set<IEntity> hqusers=new HashSet<IEntity>();
				SystemUser hqtempUser=new SystemUser();
				
				if(hqArr!=null)
				{
					for(int i=0;i<hqArr.size();i++)
					{
						
						//SystemUser systemUser=(SystemUser)(new SystemUserDAO().GetOneEntity(((RolePermission)useridArr.get(i)).getRoleNo()));
						hqtempUser.SetRoleNo(((RolePermission)hqArr.get(i)).getRoleNo());
						ArrayList<IEntity> hqtempArr=new SystemUserDAO().GetEntitySet(hqtempUser);
						for (int j = 0; j < hqtempArr.size(); j++) {
							hqusers.add(hqtempArr.get(j));
						}
					}
				}
				
				ArrayList<String> hqname=new ArrayList<String>();
				
				Iterator<IEntity> hqitr=hqusers.iterator();
				while (hqitr.hasNext()){
					hqname.add(((SystemUser)hqitr.next()).GetNickname());
				}
				
				rolePermission.setPermissionNo(10);
				
				ArrayList<IEntity> spArr=rolePermissionDAO.GetEntitySet(rolePermission);
				Set<IEntity> spusers=new HashSet<IEntity>();
				SystemUser sptempUser=new SystemUser();
				
				if(spArr!=null)
				{
					for(int i=0;i<spArr.size();i++)
					{
						sptempUser.SetRoleNo(((RolePermission)spArr.get(i)).getRoleNo());
						ArrayList<IEntity> sptempArr=new SystemUserDAO().GetEntitySet(sptempUser);
						for (int j = 0; j < sptempArr.size(); j++) {
							spusers.add(sptempArr.get(j));
						}
					}
				}
				
				ArrayList<String> spname=new ArrayList<String>();
				
				Iterator<IEntity> spitr=spusers.iterator();
				while (spitr.hasNext()){
					spname.add(((SystemUser)spitr.next()).GetNickname());
				}
				
				rolePermission.setPermissionNo(11);
				
				ArrayList<IEntity> qdArr=rolePermissionDAO.GetEntitySet(rolePermission);
				Set<IEntity> qdusers=new HashSet<IEntity>();
				SystemUser qdtempUser=new SystemUser();
				
				if(qdArr!=null)
				{
					for(int i=0;i<qdArr.size();i++)
					{
						qdtempUser.SetRoleNo(((RolePermission)qdArr.get(i)).getRoleNo());
						ArrayList<IEntity> qdtempArr=new SystemUserDAO().GetEntitySet(qdtempUser);
						for (int j = 0; j < qdtempArr.size(); j++) {
							qdusers.add(qdtempArr.get(j));
						}
					}
				}
				
				ArrayList<String> qdname=new ArrayList<String>();
				
				Iterator<IEntity> qditr=qdusers.iterator();
				while (qditr.hasNext()){
					qdname.add(((SystemUser)qditr.next()).GetNickname());
				}
				
				
				request.setAttribute("hqname",hqname);
				request.setAttribute("spname",spname);
				request.setAttribute("qdname",qdname);
				request.setAttribute("contracts", contracts);
				request.getRequestDispatcher("AllocateContractDetail.jsp").forward(request, response);
			}

		}else if(type.equals("allocateOper")){
			//获取提交的分配配置，写入数据库，返回结果
			String ajax=request.getParameter("submit");
			String names=URLDecoder.decode(ajax,"utf-8");
			
			PrintWriter out=response.getWriter();
			if(names==null){
				out.write("参数为空！");
			}else{
				//ajax格式:n1,n1,n1#n2,n2,n2#n3,n3,n3
				String []str=names.split(":");
				
				String []hqnames=str[0].split(",");
				String []spnames=str[1].split(",");
				String []qdnames=str[2].split(",");
				
				SystemUser user=new SystemUser();
				OperateFlowDAO operdao=new OperateFlowDAO();
				SimpleDateFormat currTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String timeStr=currTime.format(new Date());
				Integer contractNo=Integer.parseInt(request.getParameter("id"));
				
				//写入会签人的任务
				for(int i=0;i<hqnames.length;i++){
					user.SetNickname(hqnames[i]);
					OperateFlow oper=new OperateFlow();
					oper.setContractNo(contractNo);
					oper.setOperatorNo(new SystemUserDAO().GetOneEntity(user).GetId());
					oper.setOperateType(StatusCode.OPERATETYPE_COUNTERSIGN);
					oper.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
					oper.setOperateDate(timeStr);
					//增加操作信息，给指定分配合同的人添加任务
					operdao.AddEntity(oper);
				}
				//写入审批人的任务
				for(int i=0;i<spnames.length;i++){
					user.SetNickname(spnames[i]);
					OperateFlow oper=new OperateFlow();
					oper.setContractNo(contractNo);
					oper.setOperatorNo(new SystemUserDAO().GetOneEntity(user).GetId());
					oper.setOperateType(StatusCode.OPERATETYPE_APPROVE);
					oper.setOperateStatus(StatusCode.OPERATESTATUS_NO_READY);
					oper.setOperateDate(timeStr);
					//增加操作信息，给指定审批合同的人添加任务
					operdao.AddEntity(oper);
				}
				//写入签订人的任务
				for(int i=0;i<qdnames.length;i++){
					user.SetNickname(qdnames[i]);
					OperateFlow oper=new OperateFlow();
					oper.setContractNo(contractNo);
					oper.setOperatorNo(new SystemUserDAO().GetOneEntity(user).GetId());
					oper.setOperateType(StatusCode.OPERATETYPE_SIGN);
					oper.setOperateStatus(StatusCode.OPERATESTATUS_NO_READY);
					oper.setOperateDate(timeStr);
					//增加操作信息，给指定签订合同的人添加任务
					operdao.AddEntity(oper);
				}
				
				//更改状态
				Status status=new Status();
				status.SetcontractNo(contractNo);
				status.SetcontractStatus(StatusCode.STATUS_FINISH_ALLOCATE);
				status.SetfinishTime(timeStr);
				
				if(!new StatusDAO().UpdateEntity(status)){
					out.write("更新状态信息失败！");
					return ;
				}
				
				//写入日志
				if(!new LogDAO().AddEntity(new Log(0,(Integer)request.getSession().getAttribute("id"),"分配了一份合同，合同编号："+contractNo,timeStr))){
					out.write("更新日志失败！");
					return ;
				}else{
					out.write("分配成功！");
				}
				
				
			}
			
			
		}
	}

}
