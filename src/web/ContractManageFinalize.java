package web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.*;
import model.*;

/**
 * Servlet implementation class ContractManageFinalize
 */
@WebServlet("/ContractManageFinalize")
public class ContractManageFinalize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractManageFinalize() {
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
		
		HttpSession session = request.getSession(true);
		int clientNo=(Integer)session.getAttribute("id");
		String type=request.getParameter("type");
		Integer pageRecordNum=StatusCode.PAGE_RECORDNUM;
		Integer pageNo=1;
		if(type==null){
			//显示待此用户定稿的合同列表
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}
			if(request.getParameter("pageRecordNum")!=null){
				pageRecordNum=Integer.parseInt(request.getParameter("pageRecordNum"));
			}
			
			
			
			OperateFlowDAO operateFlowDAO=new OperateFlowDAO();
			OperateFlow operateFlow=new OperateFlow();
			operateFlow.setOperatorNo(clientNo);
			operateFlow.setOperateType(StatusCode.OPERATETYPE_FINALIZE);
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
			
			
			request.getRequestDispatcher("ContractManageFinalize.jsp").forward(request, response);
		}else if(type.equals("detail")){
			//显示合同详细信息
			//获取合同ID
			Integer id=Integer.parseInt(request.getParameter("id"));
			String flag=request.getParameter("flag");
			
			if(id!=null){
				//从数据库读取合同详细信息
				ContractDAO contractDao=new ContractDAO();
				Contract contract=(Contract)contractDao.GetOneEntity(id);
				String client=((Client)new ClientDAO().GetOneEntity(contract.GetClientNo())).GetName();
				
				OperateFlowDAO operateFlowDAO = new OperateFlowDAO();
				OperateFlow tempOperateFlow = new OperateFlow();
				tempOperateFlow.setContractNo(id);
				tempOperateFlow.setOperateType(StatusCode.OPERATETYPE_COUNTERSIGN);
				tempOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_FINISH);
				
				ArrayList<IEntity> operateFlows =  operateFlowDAO.GetEntitySet(tempOperateFlow);
				
				if(flag.equals("twice")) {
					OperateFlow aOperateFlow = new OperateFlow();
					aOperateFlow.setContractNo(id);
					aOperateFlow.setOperateType(StatusCode.OPERATETYPE_APPROVE);
					OperateFlow operateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(aOperateFlow);
					request.setAttribute("operateFlow", operateFlow);
				}
	
				request.setAttribute("flag", flag);
				request.setAttribute("operateFlows", operateFlows);
				request.setAttribute("client",client);
				request.setAttribute("contract",contract);
				request.getRequestDispatcher("ContractManageFinalizeDetail.jsp").forward(request, response);
			}
		}else if(type.equals("finalizeOper")){
			//获取提交的信息，写入数据库
			Integer contractNo=Integer.parseInt(request.getParameter("id"));
			String htnr = (String)request.getParameter("htnr");
			ContractDAO contractDAO = new ContractDAO();
			Contract newContract = (Contract) contractDAO.GetOneEntity(contractNo);
			newContract.SetContent(htnr);
			if(contractDAO.UpdateEntity(newContract)) {
				//更新合同内容成功
				//获取当前时间
				Calendar date = Calendar.getInstance();
				int day = date.get(Calendar.DAY_OF_MONTH);
				int month = date.get(Calendar.MONTH) + 1;
				int year = date.get(Calendar.YEAR);
				int hour = date.get(Calendar.HOUR_OF_DAY);
				int minute = date.get(Calendar.MINUTE);
				int second = date.get(Calendar.SECOND);
				String currentTime = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
				//更改operateflow表中数据
				OperateFlowDAO operateFlowDAO = new OperateFlowDAO();
				//将合同定稿的状态从OPERATESTATUS_NO_FINISH改为OPERATESTATUS_HAVE_FINISH
				OperateFlow aOperateFlow = new OperateFlow();
				aOperateFlow.setContractNo(contractNo);
				aOperateFlow.setOperateType(StatusCode.OPERATETYPE_FINALIZE);
				aOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
				OperateFlow aNewOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(aOperateFlow);
				aNewOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_FINISH);
				aNewOperateFlow.setOperateDate(currentTime);
				boolean aUpdateOperateFlow=operateFlowDAO.UpdateEntity(aNewOperateFlow);
				//将合同审批的状态从OPERATESTATUS_NO_REDY改为OPERATESTATUS_NO_FINISH
				OperateFlow bOperateFlow = new OperateFlow();
				bOperateFlow.setContractNo(contractNo);
				bOperateFlow.setOperateType(StatusCode.OPERATETYPE_APPROVE);
				OperateFlow bNewOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(bOperateFlow);
				bNewOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
				boolean bUpdateOperateFlow = operateFlowDAO.UpdateEntity(bNewOperateFlow);
				
				//更改status表数据
				StatusDAO statusDAO = new StatusDAO();
				Status aStatus = new Status();
				aStatus.SetcontractNo(contractNo);
				Status status = (Status) statusDAO.GetOneEntity(aStatus);
				status.SetcontractStatus(StatusCode.STATUS_FINISH_FINALIZE);
				status.SetfinishTime(currentTime);
				statusDAO.UpdateEntity(status);
				boolean UpdateStatus = statusDAO.UpdateEntity(status);
				
				boolean addLog;
				//把操作信息写入日志
				String flag = request.getParameter("flag");
				if(flag.equals("twice")) {
					addLog=new LogDAO().AddEntity(new Log(0,clientNo,"重新定稿了一份合同，合同编号："+contractNo,currentTime));
				} else {
					addLog=new LogDAO().AddEntity(new Log(0,clientNo,"定稿了一份合同，合同编号："+contractNo,currentTime));
				}
				
				
				//操作成功与否的提示
				if(aUpdateOperateFlow&&bUpdateOperateFlow&&UpdateStatus&&addLog){
					request.setAttribute("result", "操作成功！");   //操作成功
				}else{
					request.setAttribute("result", "操作成功，但操作、状态、日志信息可能不完整！");
				}
				
			} else {
				//定稿失败

				request.setAttribute("result", "操作失败！");

			}
			
			//重新读取待定稿合同
			OperateFlowDAO operateFlowDAO = new OperateFlowDAO();
			OperateFlow operateFlow=new OperateFlow();
			operateFlow.setOperatorNo(clientNo);
			operateFlow.setOperateType(StatusCode.OPERATETYPE_FINALIZE);
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
			//返回合同定稿页面
			request.getRequestDispatcher("ContractManageFinalize.jsp").forward(request, response);
		}
		
	}

}
