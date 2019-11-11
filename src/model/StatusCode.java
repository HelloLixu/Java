package model;

public class StatusCode {
	
	static public final Integer PAGE_RECORDNUM=9;	//每页显示的记录数
	
	static public final int DEL_DELETED=1;		//已经删除
	static public final int DEL_NO_DELETE=0;	//未删除
	
	static public final int STATUS_FINISH_DRAFT=11;		//完成起草
	static public final int STATUS_FINISH_ALLOCATE=21;	//完成分配
	static public final int STATUS_FINISH_COUNTERSIGN=31;//完成会签
	static public final int STATUS_FINISH_FINALIZE=41;	//完成定稿
	static public final int STATUS_FAIL_APPROVE=50;		//审核驳回
	static public final int STATUS_FINISH_APPROVE=51;	//完成审核
	static public final int STATUS_FINISH_SIGN=61;		//完成签订
	
	static public final int OPERATETYPE_DRAFT=1;		//操作类型：起草
	static public final int OPERATETYPE_ALLOCATE=2;		//操作类型：分配
	static public final int OPERATETYPE_COUNTERSIGN=3;	//操作类型：会签
	static public final int OPERATETYPE_FINALIZE=4;		//操作类型：定稿
	static public final int OPERATETYPE_APPROVE=5;		//操作类型：审核
	static public final int OPERATETYPE_SIGN=6;			//操作类型：签订
	
	
	static public final int OPERATESTATUS_NO_READY=-1;	//操作状态：分配完成，但是未进行到此阶段
	static public final int OPERATESTATUS_NO_FINISH=0;	//操作状态：未完成
	static public final int OPERATESTATUS_HAVE_FINISH=1;//操作状态：已通过
	static public final int OPERATESTATUS_HAVE_REJECT=2;//操作状态：已否决
	
	
	static public final int ERROR_NOUSERNAME=101;			//错误信息：没有用户名
	static public final int ERROR_WRONGPASSWORD=102;      //错误信息：密码错误
	static public final int ERROR_WRONGIDFCODE=103;      //错误信息：验证码错误
	static public final int ERROR_NORIGHT=104;      	//错误信息：没有权限
	static public final int PROMPT_LOGIN_IN=105;      		//登录状态：已登录
	static public final int PROMPT_LOGIN_OUT=106;      		//登录状态：已下线
	static public final int ERROR_LOGIN_OUT=107;      //错误：用户已下线或者登录超时
	static public final int PROMPT_REGISTER_SUCCESS=108;      //提示：注册成功
	static public final int ERROR_REGISTER_FAILURE=109; //错误：注册失败
}
