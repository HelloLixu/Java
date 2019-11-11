package model;

//import java.sql.Date;
//import java.sql.Time;

public class Log implements IEntity {

	
	private int id;
	private int operatorNo;
	private String content;
	private String time;
	
	//private Date time;
	
	public Log(){
		this.id=0;
		this.operatorNo=0;
		this.content=null;
		this.time=null;
	}
	
	public Log(int id,int operatorNo,String content,String time){
		this.id=id;
		this.operatorNo=operatorNo;
		this.content=content;
		this.time=time;
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
	

	public int getOperatorNo() {
		return operatorNo;
	}
	

	public void setOperatorNo(int operatorNo) {
		this.operatorNo = operatorNo;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
