package model;

public class Attachment implements IEntity {

	private int id;
	private int contractNo;
	private String filename;
	private String filepath;
	private String filetype;
	private String submitTime;
	
	
	public Attachment(){
		this.id=0;
		this.contractNo=0;
		this.filename=null;
		this.filepath=null;
		this.submitTime=null;
	}
	
	public Attachment(int id, int contractNo,String filename,
			String filepath,String filetype,String submitTime){
		this.id=id;
		this.contractNo=contractNo;
		this.filename=filename;
		this.filepath=filepath;
		this.filetype=filetype;
		this.submitTime=submitTime;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

}
