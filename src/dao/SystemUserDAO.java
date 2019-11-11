package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.mysql.jdbc.PreparedStatement;

import model.IEntity;
import model.SystemUser;

public class SystemUserDAO extends MySQLConnection implements IDAO {

	private int recordNum=0;
	private int pageNum=0;
	
	public SystemUserDAO(){
		recordNum=0;
		pageNum=0;
	}
	
	/**
	 * 
	 * @param entity IEntity 
	 * @return boolean 
	 */
	@Override
	public boolean AddEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		SystemUser systemUser=(SystemUser)entity;
		if(super.ConnectMySQL()){
			try {
				//stmt.executeUpdate("");
				String sql="insert into system_user values(null,?,?,?,?,?,?,0)";
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setString(1, systemUser.GetUsername());
				preStmt.setInt(2, systemUser.GetRoleNo());
				preStmt.setString(3, systemUser.GetNickname());
				preStmt.setString(4, systemUser.GetPassword());
				preStmt.setString(5, systemUser.GetEmail());
				preStmt.setString(6, systemUser.GetTel());
				if(preStmt.executeUpdate()==0){
					succ=false;
				}
				
				super.CloseMySQL();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				succ=false;
				e.printStackTrace();
				
			}
		}else{
			succ=false;
		}
		
		return succ;
	}
	
	/**
	 * 
	 * @param entity IEntity 
	 * @return boolean 
	 */
	@Override
	public boolean DeleteEntity(IEntity entity) {
		// TODO Auto-generated method stub
				boolean succ=true;
				SystemUser systemUser=(SystemUser)entity;
				
				if(systemUser.GetId()==0){
					return false;
				}
				
				if(super.ConnectMySQL()){
					String sql="update system_user set del=1 where del=0 and id=?";
					try {
						preStmt=(PreparedStatement) con.prepareStatement(sql);
						preStmt.setInt(1, systemUser.GetId());
						if(preStmt.executeUpdate()==0){
							succ=false;
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						succ=false;
						e.printStackTrace();
					}
					super.CloseMySQL();
				}else{
					succ=false;
				}
				
				return succ;
	}
	/**
	 * 
	 * @param entity IEntity 
	 * @return boolean 
	 */
	@Override
	public boolean UpdateEntity(IEntity entity) {
		// TODO Auto-generated method stub
		
				boolean succ=true;
				SystemUser systemUser=(SystemUser)entity;
				if(super.ConnectMySQL()){
					String sql="update system_user set ";
					try {
//						preStmt=(PreparedStatement) con.prepareStatement(sql);
//						preStmt.setInt(1, systemUser.GetId());
						if(systemUser.GetId()==0){
							return false;
						}
						
						if(systemUser.GetUsername()!=null){
							sql+="username='"+systemUser.GetUsername()+"',";
						}
						if(systemUser.GetRoleNo()!=0){
							sql+="role="+systemUser.GetRoleNo()+",";
						}
						if(systemUser.GetNickname()!=null){
							sql+="nickname='"+systemUser.GetNickname()+"',";
						}
						if(systemUser.GetPassword()!=null){
							sql+="password='"+systemUser.GetPassword()+"',";
						}
						if(systemUser.GetEmail()!=null){
							sql+="email='"+systemUser.GetEmail()+"',";
						}
						if(systemUser.GetTel()!=null){
							sql+="tel='"+systemUser.GetTel()+"',";
						}
						
						
						//去掉最后的“,”
						sql = sql.substring(0, sql.length()-1);
						
						sql+=" where del=0 and id="+systemUser.GetId();
						
						
						if(stmt.executeUpdate(sql)==0){
							succ=false;
						}
						
						super.CloseMySQL();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						succ=false;
						e.printStackTrace();
					}
					
				}else{
					succ=false;
				}
				
				return succ;
	}
	/**
	 * 
	 * @param id int
	 * @return IEntity
	 */
	@Override
	public IEntity GetOneEntity(int id) {
		// TODO Auto-generated method stub
		
				SystemUser systemUser=null;
				//id必须合法,不然返回null值
				if(id>0){
					if(super.ConnectMySQL()){
						
						String sql="select * from system_user where del=0 and id=?";
						try {
							preStmt=(PreparedStatement) con.prepareStatement(sql);
							preStmt.setInt(1, id);
							ResultSet res=preStmt.executeQuery();
							if(res.next()){
								systemUser=new SystemUser(id,
													res.getString("username"),
													res.getInt("role"),
													res.getString("nickname"),
													res.getString("password"),
													res.getString("email"),
													res.getString("tel"));
								
							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							super.CloseMySQL();
						}
						
					}
				}
				
				
				return systemUser;
	}
	
	/**
	 * 
	 * @param username String
	 * @return IEntity
	 */
	public IEntity GetOneEntity(String username) {
		// TODO Auto-generated method stub
		
		SystemUser systemUser=null;
		//id必须合法,不然返回null值
		if(username!=null){
			if(super.ConnectMySQL()){
				
				String sql="select * from system_user where del=0 and username=?";
				try {
					preStmt=(PreparedStatement) con.prepareStatement(sql);
					preStmt.setString(1, username);
					ResultSet res=preStmt.executeQuery();
					if(res.next()){
						systemUser=new SystemUser(res.getInt("id"),
											username,
											res.getInt("role"),
											res.getString("nickname"),
											res.getString("password"),
											res.getString("email"),
											res.getString("tel"));
						

					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
				
			}
		}
		return systemUser;
	}
	
	/**
	 * 
	 * @param entity IEntity 
	 * @return ArrayList<IEntity>
	 */
	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity) {
		// TODO Auto-generated method stub
		
		ArrayList<IEntity> arr=null;
		SystemUser systemUser=(SystemUser)entity;
		if(super.ConnectMySQL()){
			String sql="select * from system_user where del=0 and ";
			if(systemUser.GetId()!=0){
				sql+="id="+systemUser.GetId()+" and ";
			}
			if(systemUser.GetUsername()!=null){
				sql+="username='"+systemUser.GetUsername()+"' and ";
			}
			if(systemUser.GetRoleNo()!=0){
				sql+="role="+systemUser.GetRoleNo()+" and ";
			}
			if(systemUser.GetNickname()!=null){
				sql+="nickname='"+systemUser.GetNickname()+"' and ";
			}
			if(systemUser.GetPassword()!=null){
				sql+="password='"+systemUser.GetPassword()+"' and ";
			}
			if(systemUser.GetEmail()!=null){
				sql+="email='"+systemUser.GetEmail()+"' and ";
			}
			if(systemUser.GetTel()!=null){
				sql+="tel="+systemUser.GetTel()+" and ";
			}
			
			//去掉最后的“and”
			sql=sql.substring(0, sql.length()-4);
			
			ResultSet res;
			try {
				arr=new ArrayList<IEntity>();
				res = stmt.executeQuery(sql);
				while(res.next()){
					systemUser=new SystemUser(res.getInt("id"),
							res.getString("username"),
							res.getInt("role"),
							res.getString("nickname"),
							res.getString("password"),
							res.getString("email"),
							res.getString("tel"));
					
					arr.add(systemUser);
				
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
	
	
	/**
	 * 
	 * @param entity IEntity 
	 * @param pageNo int
	 * @param pageRecordNum int
	 * @return ArrayList<IEntity>
	 */
	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo, int pageRecordNum) {
		// TODO Auto-generated method stub
		
		ArrayList<IEntity> arr=null;
		SystemUser systemUser=(SystemUser)entity;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql=" from system_user where del=0 and ";
			if(systemUser.GetId()!=0){
				sql+="id="+systemUser.GetId()+" and ";
			}
			if(systemUser.GetUsername()!=null){
				sql+="username='"+systemUser.GetUsername()+"' and ";
			}
			if(systemUser.GetRoleNo()!=0){
				sql+="role="+systemUser.GetRoleNo()+" and ";
			}
			if(systemUser.GetNickname()!=null){
				sql+="nickname='"+systemUser.GetNickname()+"' and ";
			}
			if(systemUser.GetPassword()!=null){
				sql+="password='"+systemUser.GetPassword()+"' and ";
			}
			if(systemUser.GetEmail()!=null){
				sql+="email='"+systemUser.GetEmail()+"' and ";
			}
			if(systemUser.GetTel()!=null){
				sql+="tel="+systemUser.GetTel()+" and ";
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
						systemUser=new SystemUser(res.getInt("id"),
								res.getString("username"),
								res.getInt("role"),
								res.getString("nickname"),
								res.getString("password"),
								res.getString("email"),
								res.getString("tel"));
						
						arr.add(systemUser);
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
	/**
	 * 返回记录的条数
	 * @param 
	 * @return int
	 */
	@Override
	public int getRecordNum() {
		// TODO Auto-generated method stub
		return this.recordNum;
	}
	/**
	 * 返回页码
	 * @param 
	 * @return int
	 */
	@Override
	public int GetPageNum() {
		// TODO Auto-generated method stub
		return this.pageNum;
	}
	@Override
	public IEntity GetOneEntity(IEntity entity) {
		// TODO Auto-generated method stub
		SystemUser systemUser=(SystemUser)entity;
		SystemUser resUser=null;
		if(super.ConnectMySQL()){
			String sql="select * from system_user where del=0 and ";
			if(systemUser.GetId()!=0){
				sql+="id="+systemUser.GetId()+" and ";
			}
			if(systemUser.GetUsername()!=null){
				sql+="username='"+systemUser.GetUsername()+"' and ";
			}
			if(systemUser.GetRoleNo()!=0){
				sql+="role="+systemUser.GetRoleNo()+" and ";
			}
			if(systemUser.GetNickname()!=null){
				sql+="nickname='"+systemUser.GetNickname()+"' and ";
			}
			if(systemUser.GetPassword()!=null){
				sql+="password='"+systemUser.GetPassword()+"' and ";
			}
			if(systemUser.GetEmail()!=null){
				sql+="email='"+systemUser.GetEmail()+"' and ";
			}
			if(systemUser.GetTel()!=null){
				sql+="tel="+systemUser.GetTel()+" and ";
			}
			
			//去掉最后的“and”
			sql=sql.substring(0, sql.length()-4);
			
			ResultSet res;
			try {
				res = stmt.executeQuery(sql);
				if(res.next()){
					resUser=new SystemUser(res.getInt("id"),
							res.getString("username"),
							res.getInt("role"),
							res.getString("nickname"),
							res.getString("password"),
							res.getString("email"),
							res.getString("tel"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				super.CloseMySQL();
			}
			
		}
		
		return resUser;
	}


}
