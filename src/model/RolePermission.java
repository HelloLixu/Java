package model;

public class RolePermission implements IEntity {

	private int id;
	private int roleNo;
	private int permissionNo;
	
	public RolePermission(){
		this.id=0;
		this.roleNo=0;
		this.permissionNo=0;
	}
	
	public RolePermission(int id, int roleNo,int permissionNo){
		this.id=id;
		this.roleNo=roleNo;
		this.permissionNo=permissionNo;
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

	public int getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(int roleNo) {
		this.roleNo = roleNo;
	}

	public int getPermissionNo() {
		return permissionNo;
	}

	public void setPermissionNo(int permissionNo) {
		this.permissionNo = permissionNo;
	}

}
