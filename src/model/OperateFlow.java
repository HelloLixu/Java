package model;

public class OperateFlow implements IEntity {

	
	private int id;
	private int contractNo;
	private int operatorNo;
	private int operateType;
	private int operateStatus;
	private String content;
	private String operateDate;
	
	public OperateFlow(){
		this.id=0;
		this.contractNo=0;
		this.operatorNo=0;
		this.operateType=0;
		this.operateStatus=-2;
		this.content=null;
		this.operateDate=null;
		
	}
	
	public OperateFlow(int id,int contractNo,int operatorNo,int operateType,
			int operateStatus,String content,String operateDate){
		this.id=id;
		this.contractNo=contractNo;
		this.operatorNo=operatorNo;
		this.operateType=operateType;
		this.operateStatus=operateStatus;
		this.content=content;
		this.operateDate=operateDate;
	}
	
	@Override
	public int GetId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void SetId(int id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	public int getContractNo() {
		return contractNo;
	}

	public void setContractNo(int contractNo) {
		this.contractNo = contractNo;
	}

	public int getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(int operatorNo) {
		this.operatorNo = operatorNo;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public int getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(int operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

}
