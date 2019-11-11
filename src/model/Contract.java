package model;


public class Contract implements IEntity {
	
	private int id;
	private String name;
	private int clientNo;
	private String startTime;
	private String finishTime;	
	private String content;
	private int drafterNo;
	private int counterSignerNo;
	private int approverNo;
	private int signerNo;
	
	
	public Contract() {
		id = 0;
		name = null;
		clientNo = 0;
		startTime = null;
		finishTime = null;
		content = null;
		drafterNo = 0;
	}
	
	public Contract(int id, String name, int clientNo, String startTime, String finishTime, String content, int drafterNo) {
		this.id = id;
		this.name = name;
		this.clientNo = clientNo;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.content = content;
		this.drafterNo = drafterNo;
		
	}
	
	public int GetId() {
		return id;
	}

	public String GetName() {
		return name;
	}
	
	public int GetClientNo() {
		return clientNo;
	}
	
	public String GetStartTime() {
		return startTime;
	}
	
	public String GetFinishTime() {
		return finishTime;
	}
	
	public String GetContent() {
		return content;
	}
	
	public int GetDrafterNo() {
		return drafterNo;
	}
	
	public int GetCounterSignerNo() {
		return counterSignerNo;
	}
	
	public int GetApproverNo() {
		return approverNo;
	}
	
	public int GetSignerNo() {
		return signerNo;
	}
	
	public void SetId(int id) {
		this.id = id;
	}
	
	public void SetName(String name) {
		this.name = name;
	}
	
	public void SetClientNo(int clientNo) {
		this.clientNo = clientNo;
	}
	
	public void SetStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public void SetFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	
	public void SetContent(String content) {
		this.content = content;
	}
	
	public void SetDrafterNo(int drafterNo) {
		this.drafterNo = drafterNo;
	}
	
	public void SetCounterSignerNo(int counterSignerNo) {
		this.counterSignerNo = counterSignerNo;
	}
	
	public void SetApproverNo(int approverNo) {
		this.approverNo = approverNo;
	}
	
	public void SetSignerNo(int signerNo) {
		this.signerNo = signerNo;
	}
	
}
