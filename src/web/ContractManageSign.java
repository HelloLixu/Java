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

import dao.ClientDAO;
import dao.ContractDAO;
import dao.LogDAO;
import dao.OperateFlowDAO;
import dao.StatusDAO;

import model.Client;
import model.Contract;
import model.IEntity;
import model.Log;
import model.OperateFlow;
import model.Status;
import model.StatusCode;

/**
 * Servlet implementation class ContractManageSign
 */
@WebServlet("/ContractManageSign")
public class ContractManageSign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractManageSign() {
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
			//显示待此用户签订的合同列表
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
			operateFlow.setOperateType(StatusCode.OPERATETYPE_SIGN);
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
					
			request.getRequestDispatcher("ContractManageSign.jsp").forward(request, response);
		}else if(type.equals("detail")){
			//显示合同详细信息
			//获取合同ID
			Integer id=Integer.parseInt(request.getParameter("id"));
			if(id!=null){
				ContractDAO contractDao=new ContractDAO();
				Contract contract=(Contract)contractDao.GetOneEntity(id);
				String client=((Client)new ClientDAO().GetOneEntity(contract.GetClientNo())).GetName();
			
				request.setAttribute("client",client);
				request.setAttribute("contract",contract);
				request.getRequestDispatcher("ContractManageSignDetail.jsp").forward(request, response);
			}
		}else if(type.equals("signOper")){
			//获取提交的信息，写入数据库
			HttpSession session = request.getSession(true);
			
			int clientNo = (Integer)session.getAttribute("id");
			int contractNo = Integer.parseInt(request.getParameter("contractNo"));
			String qdxx=request.getParameter("qdxx");
			//修改contract信息
			Contract aContract = new Contract();
			aContract.SetId(contractNo);
			ContractDAO contractDAO = new ContractDAO();
			Contract bContract = (Contract) contractDAO.GetOneEntity(aContract);
			bContract.SetSignerNo(clientNo);
			if(contractDAO.UpdateEntity(bContract)) {
				//修改contract表成功，即操作成功
				//获取当前时间
				SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String currTime = time.format(new Date());
				//修改operate表数据
				OperateFlowDAO operateFlowDAO = new OperateFlowDAO();
				OperateFlow tempOperateFlow = new OperateFlow(0, contractNo, clientNo, StatusCode.OPERATETYPE_SIGN, StatusCode.OPERATESTATUS_NO_FINISH, null, null);
				OperateFlow operateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(tempOperateFlow);
				operateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_FINISH);
				operateFlow.setContent(qdxx);
				operateFlow.setOperateDate(currTime);
				boolean updateOperateFlow = operateFlowDAO.UpdateEntity(operateFlow);
				
				//更改status表数据
				StatusDAO statusDAO = new StatusDAO();
				Status status = new Status();
				status.SetcontractNo(contractNo);
				status.SetcontractStatus(StatusCode.STATUS_FINISH_SIGN);
				status.SetfinishTime(currTime);
				boolean updateStatus = statusDAO.UpdateEntity(status);
				
				boolean addLog=new LogDAO().AddEntity(new Log(0,clientNo,"签订了一份合同，合同编号："+contractNo,currTime));
				//操作成功与否的提示
				if(updateOperateFlow&&updateStatus&&addLog){
					request.setAttribute("result", "操作成功！");   //操作成功
				}else{
					request.setAttribute("result", "操作成功，但操作、状态、日志信息可能不完整！");
				}
				
			} else {
				//操作失败
				request.setAttribute("result", "操作失败！");
			}
			
			//重新读取待签订合同
			OperateFlowDAO operateFlowDAO=new OperateFlowDAO();
			OperateFlow operateFlow=new OperateFlow();
			operateFlow.setOperatorNo(clientNo);
			operateFlow.setOperateType(StatusCode.OPERATETYPE_SIGN);
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
			
			//返回合同签订页面
			request.getRequestDispatcher("ContractManageSign.jsp").forward(request, response);
		}
		
	}

}
