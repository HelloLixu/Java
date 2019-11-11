package web;

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

import dao.*;
import model.*;

/**
 * Servlet implementation class CMCountersign
 */
@WebServlet("/ContractManageCountersign")
public class ContractManageCountersign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractManageCountersign() {
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
		if(type==null){
			//显示待此用户会签的合同列表
			
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}
			if(request.getParameter("pageRecordNum")!=null){
				pageRecordNum=Integer.parseInt(request.getParameter("pageRecordNum"));
			}
			
			HttpSession session = request.getSession(true);
			int clientNo=(Integer)request.getSession().getAttribute("id");
			
			OperateFlowDAO operateFlowDAO=new OperateFlowDAO();
			OperateFlow operateFlow=new OperateFlow();
			operateFlow.setOperatorNo(clientNo);
			operateFlow.setOperateType(StatusCode.OPERATETYPE_COUNTERSIGN);
			operateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
			
			ArrayList<IEntity> arr=operateFlowDAO.GetEntitySet(operateFlow, pageNo, pageRecordNum);
			ArrayList<Contract> contracts=new ArrayList<Contract>();
			
			if(arr!=null){
				for(int i=0;i<arr.size();i++){
					Contract contract=(Contract)(new ContractDAO().GetOneEntity(((OperateFlow)arr.get(i)).getContractNo()));
					contracts.add(contract);
				}
			}
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}	
			
			request.setAttribute("contracts", contracts);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageRecordNum", pageRecordNum);
			request.setAttribute("pageNum", operateFlowDAO.GetPageNum());
			request.setAttribute("totalRecordNum", operateFlowDAO.getRecordNum());
			
			
			request.getRequestDispatcher("ContractManageCountersign.jsp").forward(request, response);
		}else if(type.equals("detail")){
			//显示合同详细信息
			//获取合同ID
			Integer id=Integer.parseInt(request.getParameter("id"));
			if(id!=null){
				//从数据库读取合同详细信息
				ContractDAO contractDao=new ContractDAO();
				Contract contract=(Contract)contractDao.GetOneEntity(id);

				request.setAttribute("contract",contract);
				request.getRequestDispatcher("ContractManageCountersignDetail.jsp").forward(request, response);
			}
			
		}else if(type.equals("countersignOper")) {
			//获取提交的信息，写入数据库
			HttpSession session = request.getSession(true);
			
			int clientNo = (Integer)session.getAttribute("id");
			int contractNo = Integer.parseInt(request.getParameter("contractNo"));
			String hqyj=request.getParameter("hqyj");
			
			Contract aContract = new Contract();
			aContract.SetId(contractNo);
			ContractDAO contractDAO = new ContractDAO();
			Contract bContract = (Contract) contractDAO.GetOneEntity(aContract);
			bContract.SetCounterSignerNo(clientNo);
			contractDAO.UpdateEntity(bContract);
			
			//获取当前时间
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String currTime = time.format(new Date());
			
			//更新opetateflow表
			OperateFlowDAO operateFlowDAO = new OperateFlowDAO();
			OperateFlow tempOperateFlow = new OperateFlow(0, contractNo, clientNo, StatusCode.OPERATETYPE_COUNTERSIGN, StatusCode.OPERATESTATUS_NO_FINISH, null, null);
			OperateFlow operateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(tempOperateFlow);
			operateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_FINISH);
			operateFlow.setContent(hqyj);
			operateFlow.setOperateDate(currTime);
			boolean addOperateFlow = operateFlowDAO.UpdateEntity(operateFlow);
			
			//如果所有客户都已经完成会签 修改Status表状态
			ArrayList<IEntity> aArr=null;
			ArrayList<IEntity> bArr=null;
			OperateFlow aOperateFlow = new OperateFlow();
			OperateFlow bOperateFlow = new OperateFlow();
			aOperateFlow.setContractNo(contractNo);
			bOperateFlow.setContractNo(contractNo);
			aOperateFlow.setOperateType(StatusCode.OPERATETYPE_COUNTERSIGN);
			bOperateFlow.setOperateType(StatusCode.OPERATETYPE_COUNTERSIGN);
			aOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_FINISH);
			bOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
			aArr = operateFlowDAO.GetEntitySet(aOperateFlow);
			bArr = operateFlowDAO.GetEntitySet(bOperateFlow);
			
			if(bArr.size()==0&&aArr.size()>0) {
				//将operateflow表中定稿状态从OPERATESTATUS_NO_READY改为OPERATESTATUS_NO_FINISH
				OperateFlow tempOperateflow = new OperateFlow();
				tempOperateflow.setContractNo(contractNo);
				tempOperateflow.setOperateType(StatusCode.OPERATETYPE_FINALIZE);
				tempOperateflow.setOperateStatus(StatusCode.OPERATESTATUS_NO_READY);
				OperateFlow newOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(tempOperateflow);
				newOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
				newOperateFlow.setOperateDate(currTime);
				boolean UpdateOperateFlow = operateFlowDAO.UpdateEntity(newOperateFlow);
				
				
				//更改status表数据
				StatusDAO statusDAO = new StatusDAO();
				Status aStatus = new Status();
				aStatus.SetcontractNo(contractNo);
				Status status = (Status) statusDAO.GetOneEntity(aStatus);
				status.SetcontractStatus(StatusCode.STATUS_FINISH_COUNTERSIGN);
				status.SetfinishTime(currTime);
				boolean UpdateStatus = statusDAO.UpdateEntity(status);
				
				//把操作信息写入日志
				boolean addLog=new LogDAO().AddEntity(new Log(0,clientNo,"会签了一份合同，合同编号："+aContract.GetId(),currTime));
				
				if(addOperateFlow&&UpdateOperateFlow&&UpdateStatus&&addLog){
					request.setAttribute("result", "操作成功！");   //合同会签成功
				}else{
					request.setAttribute("result", "会签成功，但操作、状态、日志信息可能不完整！");
				}

			} else {
				//把操作信息写入日志
				boolean addLog=new LogDAO().AddEntity(new Log(0,clientNo,"会签了一份合同，合同编号："+aContract.GetId(),currTime));
				
				if(addOperateFlow&&addLog){
					request.setAttribute("result", "操作成功！");   //合同会签成功
				}else{
					request.setAttribute("result", "会签成功，但操作、状态、日志信息可能不完整！");
				}
			}
			//重新读取显示待此用户会签的合同列表	
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}
			if(request.getParameter("pageRecordNum")!=null){
				pageRecordNum=Integer.parseInt(request.getParameter("pageRecordNum"));
			}
			
			session = request.getSession(true);
			clientNo=(Integer)request.getSession().getAttribute("id");
			
			operateFlow=new OperateFlow();
			operateFlow.setOperatorNo(clientNo);
			operateFlow.setOperateType(StatusCode.OPERATETYPE_COUNTERSIGN);
			operateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
			
			ArrayList<IEntity> arr=operateFlowDAO.GetEntitySet(operateFlow, pageNo, pageRecordNum);
			ArrayList<Contract> contracts=new ArrayList<Contract>();
			
			if(arr!=null){
				for(int i=0;i<arr.size();i++){
					Contract contract=(Contract)(new ContractDAO().GetOneEntity(((OperateFlow)arr.get(i)).getContractNo()));
					contracts.add(contract);
				}
			}
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}	
			
			request.setAttribute("contracts", contracts);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageRecordNum", pageRecordNum);
			request.setAttribute("pageNum", operateFlowDAO.GetPageNum());
			request.setAttribute("totalRecordNum", operateFlowDAO.getRecordNum());
			request.getRequestDispatcher("ContractManageCountersign.jsp").forward(request, response);
			
		}
	}

}
