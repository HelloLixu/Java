package dao;

import java.sql.*;
import java.util.ArrayList;
import com.mysql.jdbc.PreparedStatement;
import model.*;

public class ClientDAO extends MySQLConnection implements IDAO {

	private int recordNum = 0;
	private int pageNum = 0;
	
	public ClientDAO() {
		recordNum = 0;
		pageNum = 0;
	}

	@Override
	public boolean AddEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		Client client = (Client)entity;
		if(super.ConnectMySQL()) {
			try {
				String sql = "insert into client values(null,?,?,?,?,?,?,?,0)";
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setString(1, client.GetName());
				preStmt.setString(2, client.GetAddress());
				preStmt.setString(3, client.GetFax());
				preStmt.setString(4, client.GetTel());
				preStmt.setString(5, client.GetPostCode());
				preStmt.setString(6, client.GetBankName());
				preStmt.setString(7, client.GetBankAccount());
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
		Client client = (Client)entity;
		if(client.GetId()==0) {
			return false;
		}
		if(super.ConnectMySQL()){
			String sql="update client set del=1 where del=0 and id=?";
			try {
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, client.GetId());
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
		Client client = (Client)entity;
		if(super.ConnectMySQL()){
			String sql="update client set ";
			try {
				if(client.GetId()==0) {
					succ = false;
					return succ;
				}
				if(client.GetName()!=null) {
					sql+="name='"+client.GetName()+"',";
				}
				if(client.GetAddress()!=null) {
					sql+="address='"+client.GetAddress()+"',";
				}
				if(client.GetFax()!=null) {
					sql+="fax='"+client.GetFax()+"',";
				}
				if(client.GetTel()!=null) {
					sql+="tel='"+client.GetTel()+"',";
				}
				if(client.GetPostCode()!=null) {
					sql+="postcode='"+client.GetPostCode()+"',";
				}
				if(client.GetBankName()!=null) {
					sql+="bankname='"+client.GetBankName()+"',";
				}
				if(client.GetBankAccount()!=null) {
					sql+="bankaccount='"+client.GetBankAccount()+"',";
				}
				
				
				//去掉最后的“,”
				sql=sql.substring(0, sql.length()-1);
				
				sql+=" where del=0 and id="+client.GetId();
				
				
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
		Client client = null;
		if(id>0){
			if(super.ConnectMySQL()) {
				String sql="select * from client where del=0 and id=?";
				try {
					preStmt=(PreparedStatement) con.prepareStatement(sql);
					preStmt.setInt(1, id);
					ResultSet res=preStmt.executeQuery();
					if(res.next()) {
						client = new Client(id,
								res.getString("name"),
								res.getString("address"),
								res.getString("fax"),
								res.getString("tel"),
								res.getString("postcode"),
								res.getString("bankname"),
								res.getString("bankaccount")
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
		
		return client;
	}

	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo,
			int pageRecordNum) {
		// TODO Auto-generated method stub
		ArrayList<IEntity> arr=null;
		Client client = (Client)entity;
		if(super.ConnectMySQL()) {
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql=" from client where del=0 and ";
			if(client.GetId()!=0) {
				sql+="id="+client.GetId()+" and ";
			}
			if(client.GetName()!=null) {
				sql+="name like '%"+client.GetName()+"%' and ";
			}
			if(client.GetAddress()!=null) {
				sql+="address='"+client.GetAddress()+"' and ";
			}
			if(client.GetFax()!=null) {
				sql+="fax='"+client.GetFax()+"' and ";
			}
			if(client.GetTel()!=null) {
				sql+="tel='"+client.GetTel()+"' and ";
			}
			if(client.GetPostCode()!=null) {
				sql+="postcode='"+client.GetPostCode()+"' and ";
			}
			if(client.GetBankName()!=null) {
				sql+="bankname='"+client.GetBankName()+"' and ";
			}
			if(client.GetBankAccount()!=null) {
				sql+="bankaccount='"+client.GetBankAccount()+"' and ";
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
				if(recordNum%pageRecordNum!=0) {
					pageNum++;
				}
				
				if(pageNo<=pageNum){
					//设置limit值
					int startNo=(pageNo-1)*pageRecordNum;
					sqlGetEntitySet+=sql+" limit "+startNo+","+pageRecordNum;
					
					arr=new ArrayList<IEntity>();
					res = stmt.executeQuery(sqlGetEntitySet);
					while(res.next()){
						client = new Client(res.getInt("id"),
								res.getString("name"),
								res.getString("address"),
								res.getString("fax"),
								res.getString("tel"),
								res.getString("postcode"),
								res.getString("bankname"),
								res.getString("bankaccount"));
		
						arr.add(client);
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
	public ArrayList<IEntity> GetEntitySet(IEntity entity) {
		// TODO Auto-generated method stub
		ArrayList<IEntity> arr=null;
		Client client = (Client)entity;
		if(super.ConnectMySQL()) {
			String sql="select * from client where del=0 and ";
			if(client.GetId()!=0) {
				sql+="id="+client.GetId()+" and ";
			}
			if(client.GetName()!=null) {
				sql+="name='"+client.GetName()+"' and ";
			}
			if(client.GetAddress()!=null) {
				sql+="address='"+client.GetAddress()+"' and ";
			}
			if(client.GetFax()!=null) {
				sql+="fax='"+client.GetFax()+"' and ";
			}
			if(client.GetTel()!=null) {
				sql+="tel='"+client.GetTel()+"' and ";
			}
			if(client.GetPostCode()!=null) {
				sql+="postcode='"+client.GetPostCode()+"' and ";
			}
			if(client.GetBankName()!=null) {
				sql+="bankname='"+client.GetBankName()+"' and ";
			}
			if(client.GetName()!=null) {
				sql+="bankaccount='"+client.GetBankAccount()+"' and ";
			}
			
			//去掉最后的“and”
			sql = sql.substring(0, sql.length()-4);
			
			ResultSet res;
			try {
				arr=new ArrayList<IEntity>();
				res = stmt.executeQuery(sql);
				while(res.next()){
					client = new Client(res.getInt("id"),
							res.getString("name"),
							res.getString("address"),
							res.getString("fax"),
							res.getString("tel"),
							res.getString("postcode"),
							res.getString("bankname"),
							res.getString("bankaccount"));
	
					arr.add(client);
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
	public IEntity GetOneEntity(IEntity entity) {
		// TODO Auto-generated method stub
		Client client = (Client)entity;
		Client result=null;
		if(super.ConnectMySQL()) {
			String sql="select * from client where del=0 and ";
			if(client.GetId()!=0) {
				sql+="id="+client.GetId()+" and ";
			}
			if(client.GetName()!=null) {
				sql+="name='"+client.GetName()+"' and ";
			}
			if(client.GetAddress()!=null) {
				sql+="address='"+client.GetAddress()+"' and ";
			}
			if(client.GetFax()!=null) {
				sql+="fax='"+client.GetFax()+"' and ";
			}
			if(client.GetTel()!=null) {
				sql+="tel='"+client.GetTel()+"' and ";
			}
			if(client.GetPostCode()!=null) {
				sql+="postcode='"+client.GetPostCode()+"' and ";
			}
			if(client.GetBankName()!=null) {
				sql+="bankname='"+client.GetBankName()+"' and ";
			}
			if(client.GetBankAccount()!=null) {
				sql+="bankaccount='"+client.GetBankAccount()+"' and ";
			}
			
			//去掉最后的“and”
			sql = sql.substring(0, sql.length()-4);
			
			ResultSet res;
			try {
				res = stmt.executeQuery(sql);
				while(res.next()){
					result = new Client(res.getInt("id"),
							res.getString("name"),
							res.getString("address"),
							res.getString("fax"),
							res.getString("tel"),
							res.getString("postcode"),
							res.getString("bankname"),
							res.getString("bankaccount"));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				super.CloseMySQL();
			}
			
		}
		return result;
	}

}
