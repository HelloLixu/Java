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
			//��ʾ�����û������ĺ�ͬ�б�
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
			//��ʾ��ͬ��ϸ��Ϣ
			Integer id=Integer.parseInt(request.getParameter("id"));
			if(id!=null){
				//�����ݿ��ȡ��ͬ��ϸ��Ϣ
				ContractDAO contractDao=new ContractDAO();
				Contract contract=(Contract)contractDao.GetOneEntity(id);

				request.setAttribute("contract",contract);
				request.getRequestDispatcher("ContractManageApproveDetail.jsp").forward(request, response);
			}
		}else if(type.equals("approveOper")){
			//��ȡ�ύ����Ϣ��д�����ݿ�
			HttpSession session = request.getSession(true);
			int clientNo = (Integer)request.getSession().getAttribute("id");
			
			int contractNo = Integer.parseInt(request.getParameter("id"));
			String spyj = request.getParameter("spyj");
			String option = request.getParameter("optionsRadios");
			
			if(option.equals("option1")) {
				//����ͨ��
				//�޸�contract��״̬
				Contract aContract = new Contract();
				aContract.SetId(contractNo);
				ContractDAO contractDAO = new ContractDAO();
				Contract bContract = (Contract) contractDAO.GetOneEntity(aContract);
				bContract.SetApproverNo(clientNo);
				if(contractDAO.UpdateEntity(bContract)) {
					//��ȡ��ǰʱ��
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
					String currTime = time.format(new Date());
					//�޸�operateflow��״̬
					OperateFlowDAO operateFlowDAO = new OperateFlowDAO();
					OperateFlow aTempOperateFlow = new OperateFlow(0, contractNo, clientNo, StatusCode.OPERATETYPE_APPROVE, StatusCode.OPERATESTATUS_NO_FINISH, null, null);
					OperateFlow aOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(aTempOperateFlow);
					aOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_FINISH);
					aOperateFlow.setContent(spyj);
					aOperateFlow.setOperateDate(currTime);
					boolean aUpdateOperateFlow = operateFlowDAO.UpdateEntity(aOperateFlow);
					//��operateflow����ǩ��״̬��OPERATESTATUS_NO_READY��ΪOPERATESTATUS_NO_FINISH
					OperateFlow bTempOperateFlow = new OperateFlow();
					bTempOperateFlow.setContractNo(contractNo);
					bTempOperateFlow.setOperateType(StatusCode.OPERATETYPE_SIGN);
					bTempOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_READY);
					OperateFlow bOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(bTempOperateFlow);
					bOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
					boolean bUpdateOperateFlow = operateFlowDAO.UpdateEntity(bOperateFlow);
					//�޸�Status��״̬
					StatusDAO statusDAO = new StatusDAO();
					Status aStatus = new Status();
					aStatus.SetcontractNo(contractNo);
					Status status = (Status) statusDAO.GetOneEntity(aStatus);
					status.SetcontractStatus(StatusCode.STATUS_FINISH_APPROVE);
					status.SetfinishTime(currTime);
					boolean updateStatus = statusDAO.UpdateEntity(status);
					//�Ѳ�����Ϣд����־
					boolean addLog=new LogDAO().AddEntity(new Log(0,clientNo,"����ͨ����һ�ݺ�ͬ����ͬ��ţ�"+contractNo,currTime));
					if(aUpdateOperateFlow&&bUpdateOperateFlow&&updateStatus&&addLog){
						request.setAttribute("result", "�����ɹ���");   //��ͬ��ǩ�ɹ�
					}else{
						request.setAttribute("result", "�����ɹ�����������״̬����־��Ϣ���ܲ�������");
					}		
					
				} else {
					//�޸�contract��״̬���ɹ���������ʧ��
					request.setAttribute("result", "����ʧ�ܣ�");
				}
				
			} else {
				//������ͨ��
				//�޸�contract��״̬
				Contract aContract = new Contract();
				aContract.SetId(contractNo);
				ContractDAO contractDAO = new ContractDAO();
				Contract bContract = (Contract) contractDAO.GetOneEntity(aContract);
				bContract.SetApproverNo(clientNo);
				if(contractDAO.UpdateEntity(bContract)) {
					//��ȡ��ǰʱ��
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
					String currTime = time.format(new Date());
					//�޸�operateflow��״̬
					OperateFlowDAO operateFlowDAO = new OperateFlowDAO();
					OperateFlow aTempOperateFlow = new OperateFlow(0, contractNo, clientNo, StatusCode.OPERATETYPE_APPROVE, StatusCode.OPERATESTATUS_NO_FINISH, null, null);
					OperateFlow aOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(aTempOperateFlow);
					aOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_REJECT);
					aOperateFlow.setContent(spyj);
					aOperateFlow.setOperateDate(currTime);
					boolean aUpdateOperateFlow = operateFlowDAO.UpdateEntity(aOperateFlow);
					//��operateflow���ж���״̬��OPERATESTATUS_HAVE_FINISH��ΪOPERATESTATUS_NO_FINISH
					OperateFlow bTempOperateFlow = new OperateFlow();
					bTempOperateFlow.setContractNo(contractNo);
					bTempOperateFlow.setOperateType(StatusCode.OPERATETYPE_FINALIZE);
					bTempOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_HAVE_FINISH);
					OperateFlow bOperateFlow = (OperateFlow) operateFlowDAO.GetOneEntity(bTempOperateFlow);
					bOperateFlow.setOperateStatus(StatusCode.OPERATESTATUS_NO_FINISH);
					boolean bUpdateOperateFlow = operateFlowDAO.UpdateEntity(bOperateFlow);
					//�޸�Status��״̬
					StatusDAO statusDAO = new StatusDAO();
					Status aStatus = new Status();
					aStatus.SetcontractNo(contractNo);
					Status status = (Status) statusDAO.GetOneEntity(aStatus);
					status.SetcontractStatus(StatusCode.STATUS_FINISH_COUNTERSIGN);
					status.SetfinishTime(currTime);
					boolean updateStatus = statusDAO.UpdateEntity(status);
					
					//�Ѳ�����Ϣд����־
					boolean addLog=new LogDAO().AddEntity(new Log(0,clientNo,"�����ܾ���һ�ݺ�ͬ����ͬ��ţ�"+contractNo,currTime));
					if(aUpdateOperateFlow&&bUpdateOperateFlow&&updateStatus&&addLog){
						request.setAttribute("result", "�����ɹ���");   //��ͬ��ǩ�ɹ�
					}else{
						request.setAttribute("result", "�����ɹ�����������״̬����־��Ϣ���ܲ�������");
					}		
					
					
				} else {
					//�޸�contract��״̬���ɹ���������ʧ��
					
					request.setAttribute("result", "����ʧ�ܣ�");	
				}
			}
			//���¶�ȡ����˺�ͬ
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
