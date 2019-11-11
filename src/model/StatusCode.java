package model;

public class StatusCode {
	
	static public final Integer PAGE_RECORDNUM=9;	//ÿҳ��ʾ�ļ�¼��
	
	static public final int DEL_DELETED=1;		//�Ѿ�ɾ��
	static public final int DEL_NO_DELETE=0;	//δɾ��
	
	static public final int STATUS_FINISH_DRAFT=11;		//������
	static public final int STATUS_FINISH_ALLOCATE=21;	//��ɷ���
	static public final int STATUS_FINISH_COUNTERSIGN=31;//��ɻ�ǩ
	static public final int STATUS_FINISH_FINALIZE=41;	//��ɶ���
	static public final int STATUS_FAIL_APPROVE=50;		//��˲���
	static public final int STATUS_FINISH_APPROVE=51;	//������
	static public final int STATUS_FINISH_SIGN=61;		//���ǩ��
	
	static public final int OPERATETYPE_DRAFT=1;		//�������ͣ����
	static public final int OPERATETYPE_ALLOCATE=2;		//�������ͣ�����
	static public final int OPERATETYPE_COUNTERSIGN=3;	//�������ͣ���ǩ
	static public final int OPERATETYPE_FINALIZE=4;		//�������ͣ�����
	static public final int OPERATETYPE_APPROVE=5;		//�������ͣ����
	static public final int OPERATETYPE_SIGN=6;			//�������ͣ�ǩ��
	
	
	static public final int OPERATESTATUS_NO_READY=-1;	//����״̬��������ɣ�����δ���е��˽׶�
	static public final int OPERATESTATUS_NO_FINISH=0;	//����״̬��δ���
	static public final int OPERATESTATUS_HAVE_FINISH=1;//����״̬����ͨ��
	static public final int OPERATESTATUS_HAVE_REJECT=2;//����״̬���ѷ��
	
	
	static public final int ERROR_NOUSERNAME=101;			//������Ϣ��û���û���
	static public final int ERROR_WRONGPASSWORD=102;      //������Ϣ���������
	static public final int ERROR_WRONGIDFCODE=103;      //������Ϣ����֤�����
	static public final int ERROR_NORIGHT=104;      	//������Ϣ��û��Ȩ��
	static public final int PROMPT_LOGIN_IN=105;      		//��¼״̬���ѵ�¼
	static public final int PROMPT_LOGIN_OUT=106;      		//��¼״̬��������
	static public final int ERROR_LOGIN_OUT=107;      //�����û������߻��ߵ�¼��ʱ
	static public final int PROMPT_REGISTER_SUCCESS=108;      //��ʾ��ע��ɹ�
	static public final int ERROR_REGISTER_FAILURE=109; //����ע��ʧ��
}
