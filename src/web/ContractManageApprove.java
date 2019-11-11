package web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Contract;
import model.IEntity;
import model.Log;
import model.OperateFlow;
import model.Status;
import model.StatusCode;
import dao.ContractDAO;
import dao.LogDAO;
import dao.OperateFlowDAO;
import dao.StatusDAO;

/**
 * Servlet implementation class ContractManageApprove
 */
@WebServlet("/ContractManageApprove")
public class ContractManageApprove extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractManageApprove() {
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
		Integer pageRecordNum=5;
		Integer pageNo=1;
		if(type==null){
			//显示待此用户审批的合同列表
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
			operateFlow.setOperateType(StatusCode.OPERATETYPE_APPROVE);
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
			
			request.getRequestDispatcher("ContractManageApprove.jsp").forward(request, response);
		}else if(type.equals("detail")){
			//显示合同详细信息
			Integer id=Integer.parseInt(request.getParameter("id"));
			if(id!=null){
				//从数据库读取合同详细信息
				ContractDAO contractDao=new ContractDAO();
				Contract contract=(Contract)contractDao.GetOneEntity(id);

				request.setAttribute("contract",contract);
				request.getRequestDispatcher("ContractManageApproveDetail.jsp").forward(request, response);
			}
		}else if(type.equals("approveOper")){
			//获取提交的信息，写入数据库
			HttpSession session = request.getSession(true);
			int clientNo = (Integer)request.getSession().getAttribute("id");
			
			int contractNo = Integer.parseInt(request.getParameter("id"));
			String spyj = request.getParameter("spyj");
			String option = request.getParameter("optionsRadios");
			
			if(option.equals("option1")) {
				//审批通过
				//修改contract表状态
				Contract aContract = new Contract();
				aContract.SetId(contractNo);
				ContractDAO contractDAO = new ContractDAO();
				Contract bContract = (Contract) contractDAO.GetOneEntity(aContract);
				bContract.SetApproverNo(clientNo);
				if(contractDAO.UpdateEntity(bContract)) {
					//获取当前时间
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String currTime = time.format(new Date());
					//修改operateflow表状态
					OperateFlowDAO operateFlowDAO = new OperateFlowDAO();
					OperateFlow aTempOperateFlow = new OperateFlow(0, contractNo, clientNo, StatusCode.OPERATETYPE_APPROVE, StatusCode.OPERATESTATUS_NO_FINISH, null, null);
					OperateFlow aOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(aTempOperateFlow);
					aOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_FINISH);
					aOperateFlow.setContent(spyj);
					aOperateFlow.setOperateDate(currTime);
					boolean aUpdateOperateFlow = operateFlowDAO.UpdateEntity(aOperateFlow);
					//将operateflow表中签订状态从OPERATESTATUS_NO_READY改为OPERATESTATUS_NO_FINISH
					OperateFlow bTempOperateFlow = new OperateFlow();
					bTempOperateFlow.setContractNo(contractNo);
					bTempOperateFlow.setOperateType(StatusCode.OPERATETYPE_SIGN);
					bTempOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_READY);
					OperateFlow bOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(bTempOperateFlow);
					bOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
					boolean bUpdateOperateFlow = operateFlowDAO.UpdateEntity(bOperateFlow);
					//修改Status表状态
					StatusDAO statusDAO = new StatusDAO();
					Status aStatus = new Status();
					aStatus.SetcontractNo(contractNo);
					Status status = (Status) statusDAO.GetOneEntity(aStatus);
					status.SetcontractStatus(StatusCode.STATUS_FINISH_APPROVE);
					status.SetfinishTime(currTime);
					boolean updateStatus = statusDAO.UpdateEntity(status);
					//把操作信息写入日志
					boolean addLog=new LogDAO().AddEntity(new Log(0,clientNo,"审批通过了一份合同，合同编号："+contractNo,currTime));
					if(aUpdateOperateFlow&&bUpdateOperateFlow&&updateStatus&&addLog){
						request.setAttribute("result", "操作成功！");   //合同会签成功
					}else{
						request.setAttribute("result", "操作成功，但操作、状态、日志信息可能不完整！");
					}		
					
				} else {
					//修改contract表状态不成功，即操作失败
					request.setAttribute("result", "操作失败！");
				}
				
			} else {
				//审批不通过
				//修改contract表状态
				Contract aContract = new Contract();
				aContract.SetId(contractNo);
				ContractDAO contractDAO = new ContractDAO();
				Contract bContract = (Contract) contractDAO.GetOneEntity(aContract);
				bContract.SetApproverNo(clientNo);
				if(contractDAO.UpdateEntity(bContract)) {
					//获取当前时间
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String currTime = time.format(new Date());
					//修改operateflow表状态
					OperateFlowDAO operateFlowDAO = new OperateFlowDAO();
					OperateFlow aTempOperateFlow = new OperateFlow(0, contractNo, clientNo, StatusCode.OPERATETYPE_APPROVE, StatusCode.OPERATESTATUS_NO_FINISH, null, null);
					OperateFlow aOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(aTempOperateFlow);
					aOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_REJECT);
					aOperateFlow.setContent(spyj);
					aOperateFlow.setOperateDate(currTime);
					boolean aUpdateOperateFlow = operateFlowDAO.UpdateEntity(aOperateFlow);
					//将operateflow表中定稿状态从OPERATESTATUS_HAVE_FINISH改为OPERATESTATUS_NO_FINISH
					OperateFlow bTempOperateFlow = new OperateFlow();
					bTempOperateFlow.setContractNo(contractNo);
					bTempOperateFlow.setOperateType(StatusCode.OPERATETYPE_FINALIZE);
					bTempOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_FINISH);
					OperateFlow bOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(bTempOperateFlow);
					bOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
					boolean bUpdateOperateFlow = operateFlowDAO.UpdateEntity(bOperateFlow);
					//修改Status表状态
					StatusDAO statusDAO = new StatusDAO();
					Status aStatus = new Status();
					aStatus.SetcontractNo(contractNo);
					Status status = (Status) statusDAO.GetOneEntity(aStatus);
					status.SetcontractStatus(StatusCode.STATUS_FINISH_COUNTERSIGN);
					status.SetfinishTime(currTime);
					boolean updateStatus = statusDAO.UpdateEntity(status);
					
					//把操作信息写入日志
					boolean addLog=new LogDAO().AddEntity(new Log(0,clientNo,"审批拒绝了一份合同，合同编号："+contractNo,currTime));
					if(aUpdateOperateFlow&&bUpdateOperateFlow&&updateStatus&&addLog){
						request.setAttribute("result", "操作成功！");   //合同会签成功
					}else{
						request.setAttribute("result", "操作成功，但操作、状态、日志信息可能不完整！");
					}		
					
					
				} else {
					//修改contract表状态不成功，即操作失败
					
					request.setAttribute("result", "操作失败！");	
				}
			}
			//重新读取待审核合同
			OperateFlowDAO operateFlowDAO=new OperateFlowDAO();
			OperateFlow operateFlow=new OperateFlow();
			operateFlow.setOperatorNo(clientNo);
			operateFlow.setOperateType(StatusCode.OPERATETYPE_APPROVE);
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
			request.getRequestDispatcher("ContractManageApprove.jsp").forward(request, response);
			
		}
		
	}

}
