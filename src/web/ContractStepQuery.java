package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Contract;
import model.IEntity;
import model.Status;
import model.StatusCode;
import model.SystemUser;
import dao.ContractDAO;
import dao.StatusDAO;
import dao.SystemUserDAO;

/**
 * Servlet implementation class ContractStepQuery
 */
@WebServlet("/ContractStepQuery")
public class ContractStepQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractStepQuery() {
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
		if(type==null){
			Integer pageNo=1;
			Integer pageRecordNum=StatusCode.PAGE_RECORDNUM;
			Integer statusCode=StatusCode.STATUS_FINISH_DRAFT;
			
			if(request.getParameter("pageNo")!=null){
				pageNo=Integer.parseInt(request.getParameter("pageNo"));
			}
			if(request.getParameter("pageRecordNum")!=null){
				pageRecordNum=Integer.parseInt(request.getParameter("pageRecordNum"));
			}
			String temp = request.getParameter("statusCode");
			if(temp!=null){
				temp=new String(temp.getBytes("iso-8859-1"),"UTF-8");
				if(temp.equals("分配中")){
					statusCode=StatusCode.STATUS_FINISH_DRAFT;
				}else if(temp.equals("会签中")){
					statusCode=StatusCode.STATUS_FINISH_ALLOCATE;
				}else if(temp.equals("定稿中")){
					statusCode=StatusCode.STATUS_FINISH_COUNTERSIGN;
				}else if(temp.equals("审核中")){
					statusCode=StatusCode.STATUS_FINISH_FINALIZE;
				}else if(temp.equals("审核驳回")){
					statusCode=StatusCode.STATUS_FAIL_APPROVE;
				}else if(temp.equals("签订中")){
					statusCode=StatusCode.STATUS_FINISH_APPROVE;
				}else if(temp.equals("签订完成")){
					statusCode=StatusCode.STATUS_FINISH_SIGN;
				}
			}
			
			StatusDAO statusDao=new StatusDAO();
			Status status=new Status();
			status.SetcontractStatus(statusCode);
//			if(request.getParameter("contractName")!=null){
//				String contractName=new String(request.getParameter("contractName").getBytes("iso-8859-1"),"UTF-8");
//				Contract tempContract=new Contract();
//				tempContract.SetName(contractName);
//				int id=((Contract)new ContractDAO().GetOneEntity(tempContract)).GetId();
//				status.SetcontractNo(id);
//			}
			
			ArrayList<IEntity> arr=statusDao.GetEntitySet(status, pageNo, pageRecordNum);
			ArrayList<Contract> contracts=new ArrayList<Contract>();
			ArrayList<String> drafter=new ArrayList<String>();
			ArrayList<Integer> statusArr=new ArrayList<Integer>();
			
			if(arr!=null){
				for(int i=0;i<arr.size();i++){
					Contract contract=(Contract)(new ContractDAO().GetOneEntity(((Status)arr.get(i)).GetcontractNo()));
					contracts.add(contract);
					drafter.add(((SystemUser)new SystemUserDAO().GetOneEntity(contract.GetDrafterNo())).GetUsername());
					statusArr.add(((Status)arr.get(i)).GetcontractStatus());
				}
			}
			
			request.setAttribute("selected", statusCode);
			request.setAttribute("contracts", contracts);
			request.setAttribute("drafter", drafter);
			request.setAttribute("statusCode", statusArr);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageRecordNum", pageRecordNum);
			request.setAttribute("pageNum", statusDao.GetPageNum());
			request.setAttribute("totalRecordNum", statusDao.getRecordNum());
			request.getRequestDispatcher("ContractStepQuery.jsp").forward(request, response);
		}else if(type.equals("detail")){
			
		}
	}

}
