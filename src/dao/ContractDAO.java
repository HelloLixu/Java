/**
 * �ļ�����ContractDAO.java
 * ����������Contractʵ���dao��Data Access Object���࣬����������ݿ��й��ں�ͬ�����ݣ�ʵ����ɾ��Ĳ�����
 * �������ڣ�2014-04-24
 * �����ߣ�������
 */

package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import model.*;

public class ContractDAO extends MySQLConnection implements IDAO {
	
	private int recordNum=0;	//��¼��
	private int pageNum=0;		//ҳ����
	
	/**
	 * ContractDAO���췽������ʼ������
	 */
	public ContractDAO(){
		recordNum=0;
		pageNum=0;
	}
	
	/**
	 * ����һ����ͬ��Ϣ�����ݿ�
	 * @param entity IEntity ������ĺ�ͬ����
	 * @return succ boolean ����ִ�еĽ��
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
	 * �����ݿ�ɾ����ͬ��Ϣ���������������ݿ���ɾ����ֻ���del�ֶ���Ϊ1
	 * @param entity IEntity ��ɾ���ĺ�ͬ����ϵ�����
	 * @return succ boolean ����ִ�еĽ��
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
	 * �����ݿ����һ����ͬ��Ϣ
	 * @param entity IEntity �µĵĶ����滻��ԭ���Ķ���
	 * @return	succ Boolean ����ִ�еĽ��
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
				
				//ȥ�����ġ�,��
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
	 * ����һ����ͬ��Ϣ
	 * @param id int �����ҵĶ����id
	 * @return entity IEntity ����ҵ�������ָ���ĺ�ͬ��Ϣ����û�У�����null
	 */
	@Override
	public IEntity GetOneEntity(int id) {
		// TODO Auto-generated method stub
		
		Contract contract=null;
		//id����Ϸ�,��Ȼ����nullֵ
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
	 * ����һ����ͬ��Ϣ
	 * @param entity IEntity �����ҵĶ����id
	 * @return entity IEntity ����ҵ�������ָ���ĺ�ͬ��Ϣ����û�У�����null
	 */
	public IEntity GetOneEntity(IEntity entity) {
		// TODO Auto-generated method stub
		
		Contract contract=(Contract)entity;
		//id����Ϸ�,��Ȼ����nullֵ
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
				
				//ȥ�����ġ�and��
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
	 * ��ҳ��ʾʱ����ѯָ��ҳ�����к�ͬ�ļ���
	 * @param entity ��ѯ��������
	 * @param pageNo ָ����ѯ��ҳ��
	 * @param pageRecordNum ָ��ÿҳ��ʾ�ļ�¼��
	 * @return arr ArrayList<IEntity> ��ѯ�Ľ�������������ϲ�ѯ�������򷵻�null
	 */
	@Override
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo, int pageRecordNum) {
		// TODO Auto-generated method stub
		
		ArrayList<IEntity> arr=null;
		Contract contract=(Contract)entity;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//��ȡʵ��������sql���ǰ׺
			String sqlGetEntitySet="select *";		//��ȡʵ�弯�ϵ�sql���ǰ׺
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
			
			//ȥ�����ġ�and��
			sql=sql.substring(0, sql.length()-4);
			
			ResultSet res;
			try {
				sqlGetNum+=sql;
				res = stmt.executeQuery(sqlGetNum);
				if(res.next()){
					this.recordNum=res.getInt(1);
				}
				
				//������ж���ҳ
				pageNum=recordNum/pageRecordNum;
				if(recordNum%pageRecordNum!=0){
					pageNum++;
				}
				
				if(pageNo<=pageNum){
					//����limitֵ
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
	 * ���ز�ѯ�����µĺ�ͬ������
	 * @return int ��ѯ�����µĺ�ͬ������
	 */
	@Override
	public int getRecordNum() {
		// TODO Auto-generated method stub
		return this.recordNum;
	}
	
	/**
	 * ���ز�ѯ�����µĺ�ͬҳ��
	 * @return int ��ѯ�����µĺ�ͬҳ��
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
