package dao;

import java.sql.*;
import java.util.ArrayList;
import com.mysql.jdbc.PreparedStatement;
import model.*;

public class RolePermissionDAO extends MySQLConnection implements IDAO {

	private int recordNum = 0;
	private int pageNum = 0;
	
	public RolePermissionDAO() {
		recordNum = 0;
		pageNum = 0;
	}

	@Override
	public boolean AddEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		RolePermission rolePermission = (RolePermission)entity;
		if(super.ConnectMySQL()) {
			try {
				String sql = "insert into rolepermission values(null,?,?,0)";
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, rolePermission.getRoleNo());
				preStmt.setInt(2, rolePermission.getPermissionNo());
				if(preStmt.executeUpdate()==0){
					succ=false;
				}
			} catch(SQLException e) {
				succ=false;
				e.printStackTrace();
			} finally {
				super.CloseMySQL();
			}
		} else {
			succ =false;
		}
		
		return succ;
	}

	@Override
	public boolean DeleteEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		RolePermission rolePermission = (RolePermission)entity;
		if(rolePermission.GetId()==0) {
			succ = false;
			return succ;
		}
		
		if(super.ConnectMySQL()){
			String sql="update rolepermission set del=1 where del=0 and id=?";
			try {
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, rolePermission.GetId());
				if(preStmt.executeUpdate()==0) {
					succ = false;
				}
			} catch(SQLException e) {
				succ = false;
				e.printStackTrace();
			} finally {
				super.CloseMySQL();
			}
		} else {
			succ = false;
		}
		
		return succ;
	}

	@Override
	public boolean UpdateEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		RolePermission rolePermission = (RolePermission)entity;
		if(super.ConnectMySQL()){
			String sql="update rolepermission set ";
			try {
				if(rolePermission.GetId()==0) {
					succ = false;
					return succ;
				}
				if(rolePermission.getRoleNo()!=0) {
					sql+="roleno="+rolePermission.getRoleNo()+",";
				}
				if(rolePermission.getPermissionNo()!=0) {
					sql+="permissionno="+rolePermission.getPermissionNo()+",";
				}
			
				
				
				//去掉最后的“,”
				sql = sql.substring(0, sql.length()-1);
				
				sql+=" where del=0 and id="+rolePermission.GetId();
				
				
				if(stmt.executeUpdate(sql)==0) {
					succ=false;
				}
			} catch (SQLException e) {
				succ = false;
				e.printStackTrace();
			} finally {
				super.CloseMySQL();
			}
		} else {
			succ =false;
		}
		
		return succ;
	}

	@Override
	public IEntity GetOneEntity(int id) {
		// TODO Auto-generated method stub
		RolePermission rolePermisson = null;
		if(id>0){
			if(super.ConnectMySQL()) {
				String sql="select * from rolepermission where del=0 and id=?";
				try {
					preStmt=(PreparedStatement) con.prepareStatement(sql);
					preStmt.setInt(1, id);
					ResultSet res=preStmt.executeQuery();
					if(res.next()) {
						rolePermisson = new RolePermission(id,
								res.getInt("roleno"),
								res.getInt("permissionno")
								);
					}
					
				} catch(SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
			}
		}
		
		return rolePermisson;
	}

	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity) {
		// TODO Auto-generated method stub
		ArrayList<IEntity> arr=null;
		RolePermission rolePermisson = (RolePermission)entity;
		if(super.ConnectMySQL()) {
			String sql="select * from rolepermission where del=0 and ";
			if(rolePermisson.GetId()!=0) {
				sql+="id="+rolePermisson.GetId()+" and ";
			}
			if(rolePermisson.getPermissionNo()!=0) {
				sql+="permissionno="+rolePermisson.getPermissionNo()+" and ";
			}
			if(rolePermisson.getRoleNo()!=0) {
				sql+="roleno="+rolePermisson.getRoleNo()+" and ";
			}
			//去掉最后的“and”
			sql = sql.substring(0, sql.length()-4);
			
			ResultSet res;
			
			try {
				arr=new ArrayList<IEntity>();
				res = stmt.executeQuery(sql);
				while(res.next()){
					rolePermisson = new RolePermission(res.getInt("id"),
												res.getInt("roleno"),
												res.getInt("permissionno"));
	
					arr.add(rolePermisson);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				super.CloseMySQL();
			}
			
		}
		return arr;
	}
	
	
	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo,int pageRecordNum) {
		// TODO Auto-generated method stub
		ArrayList<IEntity> arr=null;
		RolePermission rolePermisson = (RolePermission)entity;
		if(super.ConnectMySQL()) {
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql=" from rolepermission where del=0 and ";
			if(rolePermisson.GetId()!=0) {
				sql+="id="+rolePermisson.GetId()+" and ";
			}
			if(rolePermisson.getPermissionNo()!=0) {
				sql+="permissionno="+rolePermisson.getPermissionNo()+" and ";
			}
			if(rolePermisson.getRoleNo()!=0) {
				sql+="roleno="+rolePermisson.getRoleNo()+" and ";
			}
			//去掉最后的“and”
			sql = sql.substring(0, sql.length()-4);
			
			ResultSet res;
			
			
			try {
				sqlGetNum+=sql;
				res = stmt.executeQuery(sqlGetNum);
				if(res.next()){
					this.recordNum=res.getInt(1);
				}
				
				//算出共有多少页
				pageNum=recordNum/pageRecordNum;
				if(recordNum%pageRecordNum!=0){
					pageNum++;
				}
				
				if(pageNo<=pageNum){
					//设置limit值
					int startNo=(pageNo-1)*pageRecordNum;
					sqlGetEntitySet+=sql+" limit "+startNo+","+pageRecordNum;
					
					arr=new ArrayList<IEntity>();
					res = stmt.executeQuery(sqlGetEntitySet);
					while(res.next()){
						rolePermisson = new RolePermission(res.getInt("id"),
								res.getInt("roleno"),
								res.getInt("permissionno"));
		
						arr.add(rolePermisson);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				super.CloseMySQL();
			}
			
		}
		
		return arr;
	}

	@Override
	public int getRecordNum() {
		// TODO Auto-generated method stub
		return recordNum;
	}

	@Override
	public int GetPageNum() {
		// TODO Auto-generated method stub
		return pageNum;
	}

	@Override
	public IEntity GetOneEntity(IEntity entity) {
		// TODO Auto-generated method stub
		RolePermission RP=(RolePermission)entity;
		RolePermission resrp=null;
		if(super.ConnectMySQL()){
			String sql="select * from rolepermission where del=0 and ";
			if(RP.GetId()!=0){
				sql+="id="+RP.GetId()+" and ";
			}
			if(RP.getRoleNo()!=0){
				sql+="roleno="+RP.getRoleNo()+" and ";
			}
			if(RP.getPermissionNo()!=0){
				sql+="permissionno="+RP.getPermissionNo()+" and ";
			}
			
			//去掉最后的“and”
			sql=sql.substring(0, sql.length()-4);
			
			ResultSet res;
			try {
				res = stmt.executeQuery(sql);
				if(res.next()){
					resrp=new RolePermission(res.getInt("id"),
							res.getInt("roleno"),
							res.getInt("permissionno"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				super.CloseMySQL();
			}
			
		}
		
		return resrp;
	}

	
	public Boolean ChangeDel(int role,int permission,int del)
	{
		boolean succ=true;
		if(super.ConnectMySQL()){
			String sql="update rolepermission set ";
			try {
					sql+="del="+del;
					
				
				//去掉最后的“,”
				
				sql+=" where roleno="+role+" and permissionno="+permission;
				
				
				if(stmt.executeUpdate(sql)==0) {
					succ=false;
				}
			} catch (SQLException e) {
				succ = false;
				e.printStackTrace();
			} finally {
				super.CloseMySQL();
			}
		} else {
			succ =false;
		}
		
		return succ;
	}

	

}
