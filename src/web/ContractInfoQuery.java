package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;
import dao.*;

/**
 * Servlet implementation class ContractInfoQuery
 */
@WebServlet("/ContractInfoQuery")
public class ContractInfoQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractInfoQuery() {
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
			//从数据库读取所有合同,默认显示第一页
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
			request.getRequestDispatcher("ContractInfoQuery.jsp").forward(request, response);
		}else if(type.equals("detail")){
			//获取合同id，从数据库读取合同详细信息
			Integer id=Integer.parseInt(request.getParameter("id"));
			if(id!=null){
				ContractDAO contractDao=new ContractDAO();
				Contract contract=(Contract)contractDao.GetOneEntity(id);
				String client=((Client)new ClientDAO().GetOneEntity(contract.GetClientNo())).GetName();
				//得到当前合同的状态码
				Status status=new Status();
				status.SetcontractNo(id);
				Integer statusCode=((Status)new StatusDAO().GetOneEntity(status)).GetcontractStatus();
				//得到起草人姓名
				String drafter=((SystemUser)new SystemUserDAO().GetOneEntity(contract.GetDrafterNo())).GetNickname();
				//得到分配人姓名
				//...
				
				
				OperateFlow oper=new OperateFlow();
				oper.setContractNo(id);
				//得到所有会签人姓名
				oper.setOperateType(StatusCode.OPERATETYPE_COUNTERSIGN);
				ArrayList<IEntity> countsigners=new OperateFlowDAO().GetEntitySet(oper);
				ArrayList<String> countsignerNames=new ArrayList<String>();
				if(countsigners!=null){
					for(int i=0;i<countsigners.size();i++){
						int operatorNo=((OperateFlow)countsigners.get(i)).getOperatorNo();
						countsignerNames.add(((SystemUser)new SystemUserDAO().GetOneEntity(operatorNo)).GetNickname());
					}
					request.setAttribute("countsigners",countsignerNames);
				}
				//得到所有审核人姓名
				oper.setOperateType(StatusCode.OPERATETYPE_APPROVE);
				ArrayList<IEntity> approvers=new OperateFlowDAO().GetEntitySet(oper);
				ArrayList<String> approverNames=new ArrayList<String>();
				if(approvers!=null){
					for(int i=0;i<approvers.size();i++){
						int operatorNo=((OperateFlow)approvers.get(i)).getOperatorNo();
						approverNames.add(((SystemUser)new SystemUserDAO().GetOneEntity(operatorNo)).GetNickname());
					}
					request.setAttribute("approvers",approverNames);
				}
				//得到所有签订人姓名
				oper.setOperateType(StatusCode.OPERATETYPE_SIGN);
				ArrayList<IEntity> signers=new OperateFlowDAO().GetEntitySet(oper);
				ArrayList<String> signerNames=new ArrayList<String>();
				if(signers!=null){
					for(int i=0;i<signers.size();i++){
						int operatorNo=((OperateFlow)signers.get(i)).getOperatorNo();
						signerNames.add(((SystemUser)new SystemUserDAO().GetOneEntity(operatorNo)).GetNickname());
					}
					request.setAttribute("signers",signerNames);
				}
				
				request.setAttribute("client",client);
				request.setAttribute("statusCode",statusCode);
				request.setAttribute("drafter",drafter);
				request.setAttribute("contract",contract);
				request.getRequestDispatcher("ContractInfoQueryDetail.jsp").forward(request, response);
			}
		}
		
		
	}

}
