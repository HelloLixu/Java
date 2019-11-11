

package dao;

import java.sql.*;
import java.util.ArrayList;
import com.mysql.jdbc.PreparedStatement;
import model.*;


public class AttachmentDAO extends MySQLConnection implements IDAO {

	private int recordNum = 0;
	private int pageNum = 0;
	
	public  AttachmentDAO() {
		recordNum = 0;
		pageNum = 0;
	}
	
	@Override
	public boolean AddEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ = true;
		Attachment attachment = (Attachment)entity;
		if(super.ConnectMySQL()) {
			try {
				String sql="insert into attachment values(null,?,?,?,?,?,0)";
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, attachment.getContractNo());
				preStmt.setString(2, attachment.getFilename());
				preStmt.setString(3, attachment.getFilepath());
				preStmt.setString(4, attachment.getFiletype());
				preStmt.setString(5, attachment.getSubmitTime());
				
				if(preStmt.executeUpdate()==0){
					succ = false;
				}

				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
	public boolean DeleteEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ = true;
		Attachment attachment = (Attachment)entity;
		
		if(attachment.getContractNo()==0) {
			return false;
		}
		
		if(super.ConnectMySQL()) {
			String sql="update attachment set del=1 where del=0 and contractno=?";
			try {
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, attachment.getContractNo());
				if(preStmt.executeUpdate()==0) {
					succ = false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
		boolean succ = true;
		Attachment attachment = (Attachment)entity;
		if(super.ConnectMySQL()) {
			String sql = "update attachment set ";
			try {
				if(attachment.GetId()==0) {
					return false;
				}
				
				if(attachment.getContractNo()!=0) {
					sql += "contractno="+attachment.getContractNo()+",";
				}
				if(attachment.getFilename()!=null) {
					sql += "filename='"+attachment.getFilename()+"',";
				}
				if(attachment.getFilepath()!=null) {
					sql += "filepath='"+attachment.getFilepath()+"',";
				}
				if(attachment.getFiletype()!=null) {
					sql += "filetype='"+attachment.getFiletype()+"',";
				}
				if(attachment.getSubmitTime()!=null) {
					sql += "submitTime='"+attachment.getSubmitTime()+"',";
				}
				
				sql = sql.substring(0, sql.length()-1); //去掉最后的逗号
				
				sql+=" where del=0 and id="+attachment.GetId();
				
				if(stmt.executeUpdate(sql)==0) {
					succ = false;
				}
							
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
	public IEntity GetOneEntity(int id) {
		// TODO Auto-generated method stub
		Attachment attachment = null;
		
		if(id > 0) {
			if(super.ConnectMySQL()) {
				String sql="select * from attachment where del=0 and id=?";
				try {
					preStmt=(PreparedStatement) con.prepareStatement(sql);
					preStmt.setInt(1, id);
					ResultSet res=preStmt.executeQuery();
					if(res.next()){
						attachment = new Attachment(id,
								res.getInt("contractno"),
								res.getString("filename"),
								res.getString("filepath"),
								res.getString("filetype"),
								res.getString("submitTime"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
			}
		}
			
		return attachment;
	}
	
	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo,
			int pageRecordNum) {
		// TODO Auto-generated method stub
		
		ArrayList<IEntity> arr=null;
		Attachment attachment = (Attachment)entity;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql=" from attachment where del=0 and ";
			if(attachment.GetId()!=0) {
				sql += "id="+attachment.GetId()+" and ";
			}
			if(attachment.getContractNo()!=0) {
				sql += "contractno="+attachment.getContractNo()+" and ";
			}
			if(attachment.getFilename()!=null) {
				sql += "filename='"+attachment.getFilename()+"' and ";
			}
			if(attachment.getFilepath()!=null) {
				sql += "filepath='"+attachment.getFilepath()+"' and ";
			}
			if(attachment.getFiletype()!=null) {
				sql += "filetype='"+attachment.getFilepath()+"' and ";
			}
			if(attachment.getSubmitTime()!=null) {
				sql += "submitTime='"+attachment.getSubmitTime()+"' and ";
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
					while(res.next()) {
						attachment = new Attachment(
								res.getInt("id"),
								res.getInt("contractno"),
								res.getString("filename"),
								res.getString("filepath"),
								res.getString("filetype"),
								res.getString("submitTime")
								);
						
						arr.add(attachment);
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
		Attachment attachment=(Attachment)entity;
		Attachment result=null;
		//id必须合法,不然返回null值
		if(attachment!=null){
			if(super.ConnectMySQL()){
				
				String sql="select *from Attachment where del=0 and ";
				
				if(attachment.GetId()!=0){
					sql+="id="+attachment.GetId()+" and ";
				}
				if(attachment.getFiletype()!=null){
					sql+="filetype='"+attachment.getFiletype()+"' and ";
				}
				if(attachment.getContractNo()!=0){
					sql+="contractno="+attachment.getContractNo()+" and ";
				}
				if(attachment.getFilename()!=null){
					sql+="filename='"+attachment.getFilename()+"' and ";
				}
				if(attachment.getFilepath()!=null){
					sql+="filepath='"+attachment.getFilepath()+"' and ";
				}
				if(attachment.getSubmitTime()!=null){
					sql+="submitTime='"+attachment.getSubmitTime()+"' and ";
				}
				
				//去掉最后的“and”
				sql=sql.substring(0, sql.length()-4);
				
				try {
					ResultSet res=stmt.executeQuery(sql);
					if(res.next()){
						result=new Attachment(res.getInt("id"),
											res.getInt("contractno"),
											res.getString("filename"),
											res.getString("filepath"),
											res.getString("filetype"),
											res.getString("submitTime"));
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

}
