package model;

public class Client implements IEntity{
	private int id;
	private String name;
	private String address;
	private String fax;
	private String tel;
	private String postCode;
	private String bankName;
	private String bankAccount;
	
	public Client() {
		id = 0;
		name = null;
		address = null;
		fax = null;
		tel = null;
		postCode =null;
		bankName = null;
		bankAccount = null;
	}
	
	public Client(int id, String name, String address, String fax, String tel, String postCode, String bankName, String bankAccount) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.fax = fax;
		this.tel = tel;
		this.postCode =postCode;
		this.bankName = bankName;
		this.bankAccount = bankAccount;
	}
	
	
	public int GetId() {
		return id;
	}
	
	public String GetName() {
		return name;
	}
	
	public String GetAddress() {
		return address;
	}
	
	public String GetFax() {
		return fax;
	}
	
	public String GetTel() {
		return tel;
	}
	
	public String GetPostCode() {
		return postCode;
	}
	
	public String GetBankName() {
		return bankName;
	}
	
	public String GetBankAccount() {
		return bankAccount;
	}
	
	public void SetId(int id) {
		this.id = id;
	}
	
	public void SetName(String name) {
		this.name = name;
	}
	
	public void SetAddress(String address) {
		this.address = address;
	}
	
	public void SetFax(String fax) {
		this.fax = fax;
	}
	
	public void SetTel(String tel) {
		this.tel = tel;
	}
	
	public void SetPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public void SetBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public void SetBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
}
