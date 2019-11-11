package model;

public class SystemUser implements IEntity {
	private int id;
	private String username;
	private int roleNo;
	private String nickname;
	private String password;
	private String email;
	private String tel;
	
	public SystemUser() {
		id = 0;
		username = null;
		roleNo = 0;    //0代表非法值，当插入一个新用户时，必须赋值为1
		nickname = null;
		password = null;
		email = null;
		tel = null;
		
	}
	
	public SystemUser(int id, String username, int roleNo, String nickname, String password, String email, String tel) {
		this.id = id;
		this.username = username;
		this.roleNo = roleNo;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.tel = tel;
		
	}
	
	public int GetId() {
		return id;
	}
	
	public String GetUsername() {
		return username;
	}
	
	public int GetRoleNo() {
		return roleNo;
	}
	
	public String GetNickname() {
		return nickname;
	}
	
	public String GetPassword() {
		return password;
	}
	
	public String GetEmail() {
		return email;
	}
	
	public String GetTel() {
		return tel;
	}
	
	public void SetId(int id) {
		this.id = id;
	}
	
	public void SetUsername(String username) {
		this.username = username;
	}
	
	public void SetRoleNo(int roleNo) {
		this.roleNo = roleNo;
	}
	
	public void SetNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void SetPassword(String password) {
		this.password = password;
	}
	
	
	public void SetEmail(String email) {
		this.email = email;
	}
	
	public void SetTel(String tel) {
		this.tel = tel;
	}
	
}
