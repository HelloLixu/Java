/**
 * 文件名：ContractDAO.java
 * 描述：这是Contract实体的dao（Data Access Object）类，负责访问数据库中关于合同的数据，实现增删查改操作。
 * 创建日期：2014-04-24
 * 创建者：李鹏翔
 */

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import model.*;

public class ContractDAO extends MySQLConnection implements IDAO {
	
	private int recordNum=0;	//记录数
	private int pageNum=0;		//页面数
	
	/**
	 * ContractDAO构造方法，初始化操作
	 */
	public ContractDAO(){
		recordNum=0;
		pageNum=0;
	}
	
	/**
	 * 插入一条合同信息到数据库
	 * @param entity IEntity 被插入的合同对象
	 * @return succ boolean 函数执行的结果
	 */
	@Override
	public boolean AddEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		Contract contract=(Contract)entity;
		if(super.ConnectMySQL()){
			try {
				//stmt.executeUpdate("");
				String sql="insert into contract values(null,?,?,?,?,?,?,null,null,null,0)";
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setString(1, contract.GetName());
				preStmt.setInt(2, contract.GetClientNo());
				preStmt.setString(3, contract.GetStartTime());
				preStmt.setString(4, contract.GetFinishTime());
				preStmt.setString(5, contract.GetContent());
				preStmt.setInt(6, contract.GetDrafterNo());
				if(preStmt.executeUpdate()==0){
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

	/**
	 * 从数据库删除合同信息，并非真正在数据库中删除，只需把del字段置为1
	 * @param entity IEntity 被删除的合同需符合的条件
	 * @return succ boolean 函数执行的结果
	 */
	@Override
	public boolean DeleteEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		Contract contract=(Contract)entity;
		
		if(contract.GetId()==0){
			return false;
		}
		
		if(super.ConnectMySQL()){
			String sql="update contract set del=1 where del=0 and id=?";
			try {
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, contract.GetId());
				if(preStmt.executeUpdate()==0){
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

	/**
	 * 从数据库更新一条合同信息
	 * @param entity IEntity 新的的对象，替换掉原来的对象
	 * @return	succ Boolean 函数执行的结果
	 */
	@Override
	public boolean UpdateEntity(IEntity entity) {
		// TODO Auto-generated method stub
		
		boolean succ=true;
		Contract contract=(Contract)entity;
		if(super.ConnectMySQL()){
			String sql="update contract set ";
			try {
//				preStmt=(PreparedStatement) con.prepareStatement(sql);
//				preStmt.setInt(1, contract.GetId());
				if(contract.GetId()==0){
					return false;
				}
				
				if(contract.GetName()!=null){
					sql+="name='"+contract.GetName()+"',";
				}
				if(contract.GetClientNo()!=0){
					sql+="client="+contract.GetClientNo()+",";
				}
				if(contract.GetStartTime()!=null){
					sql+="startTime='"+contract.GetStartTime()+"',";
				}
				if(contract.GetFinishTime()!=null){
					sql+="finishTime='"+contract.GetFinishTime()+"',";
				}
				if(contract.GetContent()!=null){
					sql+="content='"+contract.GetContent()+"',";
				}
				if(contract.GetDrafterNo()!=0){
					sql+="drafter="+contract.GetDrafterNo()+",";
				}
				if(contract.GetCounterSignerNo()!=0){
					sql+="countersigner="+contract.GetCounterSignerNo()+",";
				}
				if(contract.GetApproverNo()!=0){
					sql+="approver="+contract.GetApproverNo()+",";
				}
				if(contract.GetSignerNo()!=0){
					sql+="signer="+contract.GetSignerNo()+",";
				}
				
				//去掉最后的“,”
				sql=sql.substring(0, sql.length()-1);
				
				sql+=" where del=0 and id="+contract.GetId();
				
				
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

	/**
	 * 查找一条合同信息
	 * @param id int 被查找的对象的id
	 * @return entity IEntity 如果找到，返回指定的合同信息，若没有，返回null
	 */
	@Override
	public IEntity GetOneEntity(int id) {
		// TODO Auto-generated method stub
		
		Contract contract=null;
		//id必须合法,不然返回null值
		if(id>0){
			if(super.ConnectMySQL()){
				
				String sql="select *from contract where del=0 and id=?";
				try {
					preStmt=(PreparedStatement) con.prepareStatement(sql);
					preStmt.setInt(1, id);
					ResultSet res=preStmt.executeQuery();
					if(res.next()){
						contract=new Contract(id,
											res.getString("name"),
											res.getInt("client"),
											res.getString("startTime"),
											res.getString("finishTime"),
											res.getString("content"),
											res.getInt("drafter"));
						
						contract.SetCounterSignerNo(res.getInt("countersigner"));
						contract.SetApproverNo(res.getInt("approver"));
						contract.SetSignerNo(res.getInt("signer"));
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
				
			}
		}
		
		
		return contract;
	}
	
	/**
	 * 查找一条合同信息
	 * @param entity IEntity 被查找的对象的id
	 * @return entity IEntity 如果找到，返回指定的合同信息，若没有，返回null
	 */
	public IEntity GetOneEntity(IEntity entity) {
		// TODO Auto-generated method stub
		
		Contract contract=(Contract)entity;
		//id必须合法,不然返回null值
		if(contract!=null){
			if(super.ConnectMySQL()){
				
				String sql="select *from contract where del=0 and ";
				
				if(contract.GetId()!=0){
					sql+="id="+contract.GetId()+" and ";
				}
				if(contract.GetName()!=null){
					sql+="name='"+contract.GetName()+"' and ";
				}
				if(contract.GetClientNo()!=0){
					sql+="client="+contract.GetClientNo()+" and ";
				}
				if(contract.GetStartTime()!=null){
					sql+="startTime='"+contract.GetStartTime()+"' and ";
				}
				if(contract.GetFinishTime()!=null){
					sql+="finishTime='"+contract.GetFinishTime()+"' and ";
				}
				if(contract.GetContent()!=null){
					sql+="content='"+contract.GetContent()+"' and ";
				}
				if(contract.GetDrafterNo()!=0){
					sql+="drafter="+contract.GetDrafterNo()+" and ";
				}
				if(contract.GetCounterSignerNo()!=0){
					sql+="countersigner="+contract.GetCounterSignerNo()+" and ";
				}
				if(contract.GetApproverNo()!=0){
					sql+="approver="+contract.GetApproverNo()+" and ";
				}
				if(contract.GetSignerNo()!=0){
					sql+="signer="+contract.GetSignerNo()+" and ";
				}
				
				//去掉最后的“and”
				sql=sql.substring(0, sql.length()-4);
				
				try {
					ResultSet res=stmt.executeQuery(sql);
					if(res.next()){
						contract=new Contract(res.getInt("id"),
											res.getString("name"),
											res.getInt("client"),
											res.getString("startTime"),
											res.getString("finishTime"),
											res.getString("content"),
											res.getInt("drafter"));
						
						contract.SetCounterSignerNo(res.getInt("countersigner"));
						contract.SetApproverNo(res.getInt("approver"));
						contract.SetSignerNo(res.getInt("signer"));
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					super.CloseMySQL();
				}
				
			}
		}
		return contract;
	}
	
	/**
	 * 分页显示时，查询指定页的所有合同的集合
	 * @param entity 查询条件对象
	 * @param pageNo 指定查询的页号
	 * @param pageRecordNum 指定每页显示的记录数
	 * @return arr ArrayList<IEntity> 查询的结果集，若不符合查询条件，则返回null
	 */
	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo, int pageRecordNum) {
		// TODO Auto-generated method stub
		
		ArrayList<IEntity> arr=null;
		Contract contract=(Contract)entity;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//获取实体数量的sql语句前缀
			String sqlGetEntitySet="select *";		//获取实体集合的sql语句前缀
			String sql=" from contract where del=0 and ";
			if(contract.GetId()!=0){
				sql+="id="+contract.GetId()+" and ";
			}
			if(contract.GetName()!=null){
				sql+="name like '%"+contract.GetName()+"%' and ";
			}
			if(contract.GetClientNo()!=0){
				sql+="client="+contract.GetClientNo()+" and ";
			}
			if(contract.GetStartTime()!=null){
				sql+="startTime='"+contract.GetStartTime()+"' and ";
			}
			if(contract.GetFinishTime()!=null){
				sql+="finishTime='"+contract.GetFinishTime()+"' and ";
			}
			if(contract.GetContent()!=null){
				sql+="content='"+contract.GetContent()+"' and ";
			}
			if(contract.GetDrafterNo()!=0){
				sql+="drafter="+contract.GetDrafterNo()+" and ";
			}
			if(contract.GetCounterSignerNo()!=0){
				sql+="countersigner="+contract.GetCounterSignerNo()+" and ";
			}
			if(contract.GetApproverNo()!=0){
				sql+="approver="+contract.GetApproverNo()+" and ";
			}
			if(contract.GetSignerNo()!=0){
				sql+="signer="+contract.GetSignerNo()+" and ";
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
						contract=new Contract(res.getInt("id"),
								res.getString("name"),
								res.getInt("client"),
								res.getString("startTime"),
								res.getString("finishTime"),
								res.getString("content"),
								res.getInt("drafter"));
						contract.SetCounterSignerNo(res.getInt("countersigner"));
						contract.SetApproverNo(res.getInt("approver"));
						contract.SetSignerNo(res.getInt("signer"));
						
						arr.add(contract);
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
	 * 返回查询条件下的合同总条数
	 * @return int 查询条件下的合同总条数
	 */
	@Override
	public int getRecordNum() {
		// TODO Auto-generated method stub
		return this.recordNum;
	}
	
	/**
	 * 返回查询条件下的合同页数
	 * @return int 查询条件下的合同页数
	 */
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

}
