package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import model.*;

public class LogDAO extends MySQLConnection implements IDAO {
	
	private int recordNum=0;
	private int pageNum=0;
	
	public LogDAO(){
		recordNum=0;
		pageNum=0;
	}

	@Override
	public boolean AddEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		Log log=(Log)entity;
		if(super.ConnectMySQL()){
			try {
				
				String sql="insert into log values(null,?,?,?)";
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, log.getOperatorNo());
				preStmt.setString(2, log.getContent());
				preStmt.setString(3, log.getTime());
				
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
		Log log=(Log)entity;
		
		if(log.GetId()==0){
			return false;
		}
		
		if(super.ConnectMySQL()){
			String sql="delete from log where id=?";
			try {
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, log.GetId());
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
		Log log=(Log)entity;
		if(super.ConnectMySQL()){
			String sql="update log set ";
			try {
				
				if(log.GetId()==0){
					return false;
				}
				
				if(log.getOperatorNo()!=0){
					sql+="operator='"+log.getOperatorNo()+"',";
				}
				if(log.getContent()!=null){
					sql+="content='"+log.getContent()+"',";
				}
				if(log.getTime()!=null){
					sql+="time='"+log.getTime()+"',";
				}

				
				
				//去掉最后的“,”
				sql=sql.substring(0, sql.length()-1);
				
				sql+=" where id="+log.GetId();
				
				
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
		Log log=null;
		//id必须合法,不然返回null值
		if(id>0){
			if(super.ConnectMySQL()){
				
				String sql="select *from log where  id=?";
				try {
					preStmt=(PreparedStatement) con.prepareStatement(sql);
					preStmt.setInt(1, id);
					ResultSet res=preStmt.executeQuery();
					if(res.next()){
						log=new Log(id,
								res.getInt("operator"),
								res.getString("content"),
								res.getString("time"));
					}
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
				
			}
		}
		
		
		return log;
	}

	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo,
			int pageRecordNum) {
		// TODO Auto-generated method stub
		ArrayList<IEntity> arr=null;
		Log log=(Log)entity;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql=" from log where 1 and  ";	//where 1是防止查询条件全为null时出现sql语法错误
			if(log.GetId()!=0){
				sql+="id="+log.GetId()+" and ";
			}
			if(log.getOperatorNo()!=0){
				sql+="operator='"+log.getOperatorNo()+"' and ";
			}
			if(log.getContent()!=null){
				sql+="content="+log.getContent()+" and ";
			}
			if(log.getTime()!=null){
				sql+="time='"+log.getTime()+"' and ";
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
						log=new Log(res.getInt("id"),
								res.getInt("operator"),
								res.getString("content"),
								res.getString("time"));
						
						arr.add(log);
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
	 * 根据起止时间查询日志记录
	 * @param pageNo	指定的页号
	 * @param pageRecordNum  指定每页显示的记录数
	 * @param startTime		 指定开始时间
	 * @param endTime		 指定结束时间
	 * @return				返回符合条件的结果集
	 */
	public ArrayList<IEntity> GetEntitySet(int pageNo,
			int pageRecordNum,String startTime,String endTime) {
		// TODO Auto-generated method stub
		ArrayList<IEntity> arr=null;
		Log log=null;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql="";							//
			
			if(startTime!=null||endTime!=null){
				sql+=" from log where time > '"+startTime+"' and time < '"+endTime+"'";
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
							log=new Log(res.getInt("id"),
									res.getInt("operator"),
									res.getString("content"),
									res.getString("time"));
							
							arr.add(log);
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					super.CloseMySQL();
				}
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
	
	public ArrayList<IEntity> GetEntitySet(String startTime,String endTime){
		
		ArrayList<IEntity> arr=null;
		Log log=null;
		if(super.ConnectMySQL()){
			String sql="";
			if(startTime!=null||endTime!=null){
				sql+="select * from log where time > '"+startTime+"' and time < '"+endTime+"'";
				ResultSet res;
				try {
						
					arr=new ArrayList<IEntity>();
					res = stmt.executeQuery(sql);
					while(res.next()){
						log=new Log(res.getInt("id"),
								res.getInt("operator"),
								res.getString("content"),
								res.getString("time"));
						
						arr.add(log);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					super.CloseMySQL();
				}
			}
			
		}
		
		return arr;
	}
}
