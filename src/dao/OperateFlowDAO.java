package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import model.IEntity;
import model.OperateFlow;

public class OperateFlowDAO extends MySQLConnection implements IDAO {
	
	private int recordNum=0;
	private int pageNum=0;
	
	public OperateFlowDAO(){
		recordNum=0;
		pageNum=0;
	}

	@Override
	public boolean AddEntity(IEntity entity) {
		
		boolean succ=true;
		OperateFlow operateflow=(OperateFlow)entity;
		if(super.ConnectMySQL()){
			try {
				
				String sql="insert into operateflow values(null,?,?,?,?,?,?,0)";
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, operateflow.getContractNo());
				preStmt.setInt(2, operateflow.getOperatorNo());
				preStmt.setInt(3, operateflow.getOperateType());
				preStmt.setInt(4, operateflow.getOperateStatus());
				preStmt.setString(5, operateflow.getContent());
				preStmt.setString(6, operateflow.getOperateDate());
				
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
		boolean succ=true;
		OperateFlow operateflow=(OperateFlow)entity;
		boolean notNull=operateflow.getContractNo()!=0||operateflow.getOperatorNo()!=0;
		if(notNull&&super.ConnectMySQL()){
			String sql="update operateflow set del=1 where del=0 and  ";
			if(operateflow.getContractNo()!=0){
				sql+="contractno='"+operateflow.getContractNo()+"',";
			}
			if(operateflow.getOperatorNo()!=0){
				sql+="operatorno='"+operateflow.getOperatorNo()+"',";
			}
			//去掉最后的“,”
			sql=sql.substring(0, sql.length()-1);
			try {
				if(stmt.executeUpdate(sql)==0){
					succ=false;
				}
				
			} catch (SQLException e) {
				succ=false;
				e.printStackTrace();
			} finally {
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
		OperateFlow operateflow=(OperateFlow)entity;
		if(super.ConnectMySQL()){
			String sql="update operateflow set ";
			try {
//				preStmt=(PreparedStatement) con.prepareStatement(sql);
//				preStmt.setInt(1, contract.GetId());
				if(operateflow.GetId()==0){
					return false;
				}
				
				if(operateflow.getContractNo()!=0){
					sql+="contractno='"+operateflow.getContractNo()+"',";
				}
				if(operateflow.getOperatorNo()!=0){
					sql+="operatorno='"+operateflow.getOperatorNo()+"',";
				}
				if(operateflow.getOperateType()!=0){
					sql+="operatetype='"+operateflow.getOperateType()+"',";
				}
				if(operateflow.getOperateStatus()!=-2){
					sql+="operatestatus='"+operateflow.getOperateStatus()+"',";
				}
				if(operateflow.getContent()!=null){
					sql+="content='"+operateflow.getContent()+"',";
				}
				if(operateflow.getOperateDate()!=null){
					sql+="operateTime='"+operateflow.getOperateDate()+"',";
				}
				
				
				//去掉最后的“,”
				sql=sql.substring(0, sql.length()-1);
				
				sql+=" where del=0 and id="+operateflow.GetId();
				
				
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
		OperateFlow operateflow=null;
		//id必须合法,不然返回null值
		if(id>0){
			if(super.ConnectMySQL()){
				
				String sql="select *from operateflow where del=0 and id=?";
				try {
					preStmt=(PreparedStatement) con.prepareStatement(sql);
					preStmt.setInt(1, id);
					ResultSet res=preStmt.executeQuery();
					if(res.next()){
						operateflow=new OperateFlow(id,
											res.getInt("contractno"),
											res.getInt("operatorno"),
											res.getInt("operatetype"),
											res.getInt("operatestatus"),
											res.getString("content"),
											res.getString("operateTime"));
					}
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
				
			}
		}
		
		
		return operateflow;
		
	}

	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo,
			int pageRecordNum) {
		ArrayList<IEntity> arr=null;
		OperateFlow operateflow=(OperateFlow)entity;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql=" from operateflow where del=0 and ";
			if(operateflow.GetId()!=0){
				sql+="id="+operateflow.GetId()+" and ";
			}
			if(operateflow.getContractNo()!=0){
				sql+="contractno='"+operateflow.getContractNo()+"' and ";
			}
			if(operateflow.getOperatorNo()!=0){
				sql+="operatorno="+operateflow.getOperatorNo()+" and ";
			}
			if(operateflow.getOperateType()!=0){
				sql+="operatetype='"+operateflow.getOperateType()+"' and ";
			}
			if(operateflow.getOperateStatus()!=-2){
				sql+="operatestatus='"+operateflow.getOperateStatus()+"' and ";
			}
			if(operateflow.getContent()!=null){
				sql+="content='"+operateflow.getContent()+"' and ";
			}
			if(operateflow.getOperateDate()!=null){
				sql+="operateTime='"+operateflow.getOperateDate()+"' and ";
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
						operateflow=new OperateFlow(
								res.getInt("id"),
								res.getInt("contractno"),
								res.getInt("operatorno"),
								res.getInt("operatetype"),
								res.getInt("operatestatus"),
								res.getString("content"),
								res.getString("operateTime"));
						
						arr.add(operateflow);
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
		
		return this.recordNum;
	}

	@Override
	public int GetPageNum() {
		
		return this.pageNum;
	}

	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity) {
		// TODO Auto-generated method stub
		ArrayList<IEntity> arr=null;
		OperateFlow operateflow=(OperateFlow)entity;
		if(super.ConnectMySQL()){
			String sql="select * from operateflow where del=0 and ";
			if(operateflow.GetId()!=0){
				sql+="id="+operateflow.GetId()+" and ";
			}
			if(operateflow.getContractNo()!=0){
				sql+="contractno='"+operateflow.getContractNo()+"' and ";
			}
			if(operateflow.getOperatorNo()!=0){
				sql+="operatorno="+operateflow.getOperatorNo()+" and ";
			}
			if(operateflow.getOperateType()!=0){
				sql+="operatetype='"+operateflow.getOperateType()+"' and ";
			}
			if(operateflow.getOperateStatus()!=-2){
				sql+="operatestatus='"+operateflow.getOperateStatus()+"' and ";
			}
			if(operateflow.getContent()!=null){
				sql+="content='"+operateflow.getContent()+"' and ";
			}
			if(operateflow.getOperateDate()!=null){
				sql+="operateTime='"+operateflow.getOperateDate()+"' and ";
			}
			
			//去掉最后的“and”
			sql=sql.substring(0, sql.length()-4);
			
			ResultSet res;
			try {
				arr=new ArrayList<IEntity>();
				res = stmt.executeQuery(sql);
				while(res.next()){
					operateflow=new OperateFlow(
							res.getInt("id"),
							res.getInt("contractno"),
							res.getInt("operatorno"),
							res.getInt("operatetype"),
							res.getInt("operatestatus"),
							res.getString("content"),
							res.getString("operateTime"));
					
					arr.add(operateflow);
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
		OperateFlow operateflow=(OperateFlow) entity;
		if(super.ConnectMySQL()){

			String sql="select *from operateflow where del=0 and ";
			if(operateflow.GetId()!=0){
				sql+="id="+operateflow.GetId()+" and ";
			}
			if(operateflow.getContractNo()!=0){
				sql+="contractno='"+operateflow.getContractNo()+"' and ";
			}
			if(operateflow.getOperatorNo()!=0){
				sql+="operatorno="+operateflow.getOperatorNo()+" and ";
			}
			if(operateflow.getOperateType()!=0){
				sql+="operatetype='"+operateflow.getOperateType()+"' and ";
			}
			if(operateflow.getOperateStatus()!=-2){
				sql+="operatestatus='"+operateflow.getOperateStatus()+"' and ";
			}
			if(operateflow.getContent()!=null){
				sql+="content='"+operateflow.getContent()+"' and ";
			}
			if(operateflow.getOperateDate()!=null){
				sql+="operateTime='"+operateflow.getOperateDate()+"' and ";
			}
			
			sql=sql.substring(0, sql.length()-4);
			
			try {
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				ResultSet res=preStmt.executeQuery();
				if(res.next()){
					operateflow=new OperateFlow(res.getInt("id"),
										res.getInt("contractno"),
										res.getInt("operatorno"),
										res.getInt("operatetype"),
										res.getInt("operatestatus"),
										res.getString("content"),
										res.getString("operateTime"));
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
				super.CloseMySQL();
			}
		}
		
		return operateflow;
	}

}
