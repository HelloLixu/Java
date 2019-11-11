package model;


public class Status implements IEntity {
	private int id;
	private int contractNo;
	private int contractStatus;
	private String finishTime;
	
	public Status(){
		this.id=0;
		this.contractNo=0;
		this.contractStatus=0;
		this.finishTime=null;	
	}
	
	public Status(int id,int contractNo,int contractStatus,String finishTime){
		this.id=id;
		this.contractNo=contractNo;
		this.contractStatus=contractStatus;
		this.finishTime=finishTime;		
	}
	public int GetId(){
		   return id;
		}
		public int GetcontractNo(){
			return contractNo;
		}
		public int GetcontractStatus(){
			return contractStatus;
		}
		public String GetfinishTime(){
			return finishTime;
		}
		public void SetId(int id){
			this.id=id;
		}
		public void SetcontractNo(int contractNo){
			this.contractNo=contractNo;
		}
		public void SetcontractStatus(int contractStatus){
			this.contractStatus=contractStatus;
		}
		public void SetfinishTime(String finishTime){
			this.finishTime=finishTime;
		}


}
