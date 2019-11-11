package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import model.IEntity;
import model.Role;



public class RoleDAO extends MySQLConnection implements IDAO {

	private int recordNum=0;
	private int pageNum=0;
	
	public RoleDAO(){
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
		Role role=(Role)entity;
		if(super.ConnectMySQL()){
			try {
				//stmt.executeUpdate("");
				String sql="insert into role values(null,?,?,?,0)";
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setString(1, role.GetCode());
				preStmt.setString(2, role.GetName());
				preStmt.setString(3, role.GetDescription());

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
				Role role=(Role)entity;
				
				if(role.GetId()==0){
					return false;
				}
				
				if(super.ConnectMySQL()){
					String sql="update role set del=1 where del=0 and id=?";
					try {
						preStmt=(PreparedStatement) con.prepareStatement(sql);
						preStmt.setInt(1, role.GetId());
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
				Role role=(Role)entity;
				if(super.ConnectMySQL()){
					String sql="update role set ";
					try {
//						preStmt=(PreparedStatement) con.prepareStatement(sql);
//						preStmt.setString(1, role.GetId());
						if(role.GetId()==0){
							return false;
						}
						
						if(role.GetCode()!=null){
							sql+="code='"+role.GetCode()+"',";
						}
						if(role.GetName()!=null){
							sql+="name='"+role.GetName()+"',";
						}
						if(role.GetDescription()!=null){
							sql+="description='"+role.GetDescription()+"',";
						}
						
						
						
						//去掉最后的“,”
						sql = sql.substring(0, sql.length()-1);
						
						sql+=" where del=0 and id="+role.GetId();
						
						
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
	 * 获得一个实体对象
	 * @param id int
	 * @return IEntity 
	 */
	@Override
	public IEntity GetOneEntity(int id) {
		// TODO Auto-generated method stub
		
				Role role=null;
				//id必须合法,不然返回null值
				if(id>0){
					if(super.ConnectMySQL()){
						
						String sql="select * from role where del=0 and id=?";
						try {
							preStmt=(PreparedStatement) con.prepareStatement(sql);
							preStmt.setInt(1, id);
							ResultSet res=preStmt.executeQuery();
							if(res.next()){
								role=new Role(id,
											res.getString("name"),
											res.getString("code"),
											res.getString("description"));
												
								

							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							super.CloseMySQL();
						}
						
					}
				}
				
				
				return role;
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
		Role role=(Role)entity;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql=" from role where del=0 and ";
			if(role.GetId()!=0){
				sql+="id="+role.GetId()+" and ";
			}
			if(role.GetCode()!=null){
				sql+="code='"+role.GetCode()+"' and ";
			}
			if(role.GetName()!=null){
				sql+="name like '%"+role.GetName()+"%' and ";
			}
			if(role.GetDescription()!=null){
				sql+="description='"+role.GetDescription()+"' and ";
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
						role=new Role(res.getInt("id"),
									res.getString("name"),
									res.getString("code"),
									res.getString("description"));
						
						arr.add(role);
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
	public ArrayList<IEntity> GetEntitySet(IEntity entity) {
		// TODO Auto-generated method stub
		ArrayList<IEntity> arr=null;
		Role role=(Role)entity;
		if(super.ConnectMySQL()){
			String sql="select * from role where del=0 and ";
			if(role.GetId()!=0){
				sql+="id="+role.GetId()+" and ";
			}
			if(role.GetCode()!=null){
				sql+="code='"+role.GetCode()+"' and ";
			}
			if(role.GetName()!=null){
				sql+="name like '%"+role.GetName()+"%' and ";
			}
			if(role.GetDescription()!=null){
				sql+="description='"+role.GetDescription()+"' and ";
			}
			
			//去掉最后的“and”
			sql = sql.substring(0, sql.length()-4);
			
			ResultSet res;
			try {
				arr=new ArrayList<IEntity>();
				res = stmt.executeQuery(sql);
				while(res.next()){
					role=new Role(res.getInt("id"),
								res.getString("name"),
								res.getString("code"),
								res.getString("description"));
					
					arr.add(role);
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
	public IEntity GetOneEntity(IEntity entity) {
		// TODO Auto-generated method stub
		Role role=(Role)entity;
		Role result=null;
		//id必须合法,不然返回null值
		if(role!=null){
			if(super.ConnectMySQL()){
				
				String sql="select * from role where del=0 and ";
				
				if(role.GetId()!=0){
					sql+="id="+role.GetId()+" and ";
				}
				if(role.GetCode()!=null){
					sql+="code='"+role.GetCode()+"' and ";
				}
				if(role.GetName()!=null){
					sql+="name='"+role.GetName()+"' and ";
				}
				if(role.GetDescription()!=null){
					sql+="description="+role.GetDescription()+" and ";
				}
				
				//去掉最后的“and”
				sql=sql.substring(0, sql.length()-4);
				
				try {
					ResultSet res=stmt.executeQuery(sql);
					if(res.next()){
						result=new Role(res.getInt("id"),
									  res.getString("name"),
									  res.getString("code"),
									  res.getString("description"));
						
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
				
			}
		}
		return result;
	}
	
	public IEntity GetOneEntity(String rolename){
		// TODO Auto-generated method stub
		
		Role role=null;
		//id必须合法,不然返回null值
		if(rolename!=null){
			if(super.ConnectMySQL()){
				
				String sql="select * from role where del=0 and name=?";
				try {
					preStmt=(PreparedStatement) con.prepareStatement(sql);
					preStmt.setString(1, rolename);
					ResultSet res=preStmt.executeQuery();
					if(res.next()){
						role=new Role(res.getInt("id"),rolename,
											res.getString("code"),
											res.getString("description"));
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
				
			}
		}
		return role;
	}
	
	
}


