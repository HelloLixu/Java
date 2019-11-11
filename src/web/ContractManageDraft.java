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
 * Servlet implementation class ContractManageDraft
 */
@WebServlet("/ContractManageDraft")
public class ContractManageDraft extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractManageDraft() {
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
			request.setAttribute("clients", new ClientDAO().GetEntitySet(new Client()));
			request.getRequestDispatcher("ContractManageDraft.jsp").forward(request, response);
		}else if(type.equals("draftOper")){
			//��ȡǰ̨����
			HttpSession session = request.getSession(true);
			String name=request.getParameter("contractName");
			String startTime=request.getParameter("startTime");
			String finishTime=request.getParameter("finishTime");
			String content=request.getParameter("contractContent");
			String clientName=request.getParameter("client");
			int drafterNo=(Integer)session.getAttribute("id");
			//���ݻ�õĿͻ�����ȡ�ͻ�ID
			Client aClient = new Client();
			aClient.SetName(clientName);
			Client client=(Client) new ClientDAO().GetOneEntity(aClient);
			
			//д�����ݿ�
			Contract tempContract=new Contract(0, name, client.GetId(), startTime, finishTime, content, drafterNo);
			ContractDAO contractDAO = new ContractDAO();
			if(contractDAO.AddEntity(tempContract)) {
				//��ȡ��ǰʱ��
				SimpleDateFormat currTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
				String timeStr=currTime.format(new Date());
				
				//д�����ݿ�oprateflow��
				Contract aContract = (Contract) contractDAO.GetOneEntity(tempContract);
				OperateFlow operateFlow = new OperateFlow(0, aContract.GetId(), drafterNo, StatusCode.OPERATETYPE_DRAFT,
															model.StatusCode.OPERATESTATUS_HAVE_FINISH, null, timeStr);
				boolean addOperateFlow=new OperateFlowDAO().AddEntity(operateFlow);
				
				OperateFlow bOperateFlow = new OperateFlow(0, aContract.GetId(), drafterNo, StatusCode.OPERATETYPE_FINALIZE, StatusCode.OPERATESTATUS_NO_READY, null, timeStr);
				boolean is=new OperateFlowDAO().AddEntity(bOperateFlow);
				
				
				
				//д�����ݿ�status��
				Status status=new Status();
				status.SetcontractNo(aContract.GetId());
				status.SetcontractStatus(model.StatusCode.STATUS_FINISH_DRAFT);
				status.SetfinishTime(timeStr);
				boolean addStatus=new StatusDAO().AddEntity(status);
				
				//�Ѳ�����Ϣд����־
				boolean addLog=new LogDAO().AddEntity(new Log(0,drafterNo,"�����һ�ݺ�ͬ����ͬ��ţ�"+aContract.GetId(),currTime.format(new Date())));
				
				//�ϴ�����
				String filename=request.getParameter("textfield");
				if(filename!=null){
					//�ϴ�ָ���ļ�
				}
				
				if(addOperateFlow&&addStatus&&addLog){
					request.setAttribute("result", "��ݳɹ���");   //��ͬ��ݳɹ�
				}else{
					request.setAttribute("result", "��ͬ����ɹ�����������״̬����־��Ϣ���ܲ�������");
				}
			} else {
				//���ʧ��
				request.setAttribute("result", "���ʧ�ܣ�");
			}
			
			request.setAttribute("clients", new ClientDAO().GetEntitySet(new Client()));
			request.getRequestDispatcher("ContractManageDraft.jsp").forward(request, response);
		}
		
		
		
	}

}
