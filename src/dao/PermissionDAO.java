package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import model.*;

public class PermissionDAO extends MySQLConnection implements IDAO {
	
	private int recordNum=0;
	private int pageNum=0;
	
	public PermissionDAO(){
		recordNum=0;
		pageNum=0;
	}

	@Override
	public boolean AddEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		Permission permission=(Permission)entity;
		if(super.ConnectMySQL()){
			try {
				
				String sql="insert into permission values(null,?,?,?,0)";
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setString(1, permission.GetCode());
				preStmt.setString(2, permission.GetName());
				preStmt.setString(3, permission.GetDescription());
				
				if(preStmt.executeUpdate()==0){
					succ=false;
				}
				
				
			} catch (SQLException e) {
				
				succ=false;
				e.printStackTrace();
			}finally{
				super.CloseMySQL();
			}
		}else{
			succ=false;
		}
		
		return succ;
	}

	@Override
	public boolean DeleteEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		Permission permission=(Permission)entity;
		
		if(permission.GetId()==0){
			return false;
		}
		
		if(super.ConnectMySQL()){
			String sql="update permission set del=1 where del=0 and id=?";
			try {
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, permission.GetId());
				if(preStmt.executeUpdate()==0){
					succ=false;
				}
				
			} catch (SQLException e) {
				
				succ=false;
				e.printStackTrace();
			}finally{
				super.CloseMySQL();
			}
			
		}else{
			succ=false;
		} 
		
		return succ;
	}

	@Override
	public boolean UpdateEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		Permission permission=(Permission)entity;
		if(super.ConnectMySQL()){
			String sql="update permission set ";
			try {
				if(permission.GetId()==0){
					return false;
				}
				
				if(permission.GetCode()!=null){
					sql+="code='"+permission.GetCode()+"',";
				}
				if(permission.GetName()!=null){
					sql+="name='"+permission.GetName()+"',";
				}
				if(permission.GetDescription()!=null){
					sql+="description='"+permission.GetDescription()+"',";
				}
				
				
				//去掉最后的“,”
				sql=sql.substring(0, sql.length()-1);
				
				sql+=" where del=0 and id="+permission.GetId();
				
				
				if(stmt.executeUpdate(sql)==0){
					succ=false;
				}
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				succ=false;
				e.printStackTrace();
			}finally{
				super.CloseMySQL();
			}
			
		}else{
			succ=false;
		}
		
		return succ;
	}

	@Override
	public IEntity GetOneEntity(int id) {
		// TODO Auto-generated method stub
		Permission permission=null;
		//id必须合法,不然返回null值
		if(id>0){
			if(super.ConnectMySQL()){
				
				String sql="select *from permission where del=0 and id=?";
				try {
					preStmt=(PreparedStatement) con.prepareStatement(sql);
					preStmt.setInt(1, id);
					ResultSet res=preStmt.executeQuery();
					if(res.next()){
						permission=new Permission(id,
											res.getString("code"),
											res.getString("name"),
											res.getString("description"));
					}
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
				
			}
		}
		
		
		return permission;
	}

	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo,
			int pageRecordNum) {
		// TODO Auto-generated method stub
		ArrayList<IEntity> arr=null;
		Permission permission=(Permission)entity;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql=" from permission where del=0 and ";
			if(permission.GetId()!=0){
				sql+="id="+permission.GetId()+" and ";
			}
			if(permission.GetCode()!=null){
				sql+="code='"+permission.GetCode()+"' and ";
			}
			if(permission.GetName()!=null){
				sql+="name="+permission.GetName()+" and ";
			}
			if(permission.GetDescription()!=null){
				sql+="description='"+permission.GetDescription()+"' and ";
			}
			
			//去掉最后的“and”
			sql=sql.substring(0, sql.length()-4);
			
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
						permission=new Permission(
								res.getInt("id"),
								res.getString("code"),
								res.getString("name"),
								res.getString("description"));
						
						arr.add(permission);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				super.CloseMySQL();
			}
			
		}
		
		return arr;
	}

	@Override
	public int getRecordNum() {
		// TODO Auto-generated method stub
		return this.recordNum;
	}

	@Override
	public int GetPageNum() {
		// TODO Auto-generated method stub
		return this.pageNum;
	}

	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntity GetOneEntity(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
