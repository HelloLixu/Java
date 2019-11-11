package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.PreparedStatement;
import model.IEntity;
import model.Status;

public class StatusDAO extends MySQLConnection implements IDAO {

	private int recordNum=0;
	private int pageNum=0;
	
	public StatusDAO(){
		recordNum=0;
		pageNum=0;
	}
	/**
	 * ����һ��ʵ�����
	 * @param entity IEntity
	 * @return boolean
	 */
	@Override
	public boolean AddEntity(IEntity entity) {
		// TODO Auto-generated method stub
				boolean succ=true;
				Status status=(Status)entity;
				if(super.ConnectMySQL()){
					try {
						//stmt.executeUpdate("");
						String sql="insert into status values(null,?,?,?,0)";
						preStmt=(PreparedStatement) con.prepareStatement(sql);
						preStmt.setInt(1, status.GetcontractNo());
						preStmt.setInt(2, status.GetcontractStatus());
						preStmt.setString(3, status.GetfinishTime());

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
	 * ɾ��һ��ʵ�����
	 * @param entity IEntity
	 * @return boolean
	 */
	@Override
	public boolean DeleteEntity(IEntity entity) {
		// TODO Auto-generated method stub
		boolean succ=true;
		Status status=(Status)entity;
		
		if(status.GetcontractNo()==0){
			return false;
		}
		
		if(super.ConnectMySQL()){
			String sql="update status set del=1 where del=0 and contractno=?";
			try {
				preStmt=(PreparedStatement) con.prepareStatement(sql);
				preStmt.setInt(1, status.GetcontractNo());
				if(preStmt.executeUpdate()==0){
					succ=false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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

	/**
	 * ����һ��ʵ�����
	 * @param entity IEntity
	 * @return boolean
	 */
	@Override
	public boolean UpdateEntity(IEntity entity) {
		// TODO Auto-generated method stub
		
		boolean succ=true;
		Status status=(Status)entity;
		if(super.ConnectMySQL()){
			String sql="update status set ";
			try {
//						preStmt=(PreparedStatement) con.prepareStatement(sql);
//						preStmt.setInt(1, status.GetId());
				if(status.GetcontractNo()==0){
					return false;
				}
				
//						if(status.GetcontractNo()!=0){
//							sql+="contractNo='"+status.GetcontractNo()+"',";
//						}
				if(status.GetcontractStatus()!=0){
					sql+="contractStatus="+status.GetcontractStatus()+",";
				}
				if(status.GetfinishTime()!=null){
					sql+="finishTime='"+status.GetfinishTime()+"',";
				}
				
				
				
				//ȥ�����ġ�,��
				sql = sql.substring(0, sql.length()-1);
				
				sql+=" where del=0 and contractno="+status.GetcontractNo();
				
				
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
	 * ���һ��ʵ�����
	 * @param id int
	 * @return IEntity
	 */
	@Override
	public IEntity GetOneEntity(int id) {
		// TODO Auto-generated method stub
		
				Status status=null;
				//id����Ϸ�,��Ȼ����nullֵ
				if(id>0){
					if(super.ConnectMySQL()){
						
						String sql="select * from status where del=0 and id=?";
						try {
							preStmt=(PreparedStatement) con.prepareStatement(sql);
							preStmt.setInt(1, id);
							ResultSet res=preStmt.executeQuery();
							if(res.next()){
								status=new Status(id,
											res.getInt("contractNo"),
											res.getInt("contractStatus"),
											res.getString("finishTime"));
												
							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							super.CloseMySQL();
						}
						
					}
				}
				
				
				return status;
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
		Status status=(Status)entity;
		if(super.ConnectMySQL()){
			String sqlGetNum="select COUNT(id)";	//��ȡʵ��������sql���ǰ׺
			String sqlGetEntitySet="select *";		//��ȡʵ�弯�ϵ�sql���ǰ׺
			String sql=" from status where del=0 and ";
			if(status.GetId()!=0){
				sql+="id="+status.GetId()+" and ";
			}
			if(status.GetcontractNo()!=0){
				sql+="contractNo='"+status.GetcontractNo()+"' and ";
			}
			if(status.GetcontractStatus()!=0){
				sql+="contractStatus="+status.GetcontractStatus()+" and ";
			}
			if(status.GetfinishTime()!=null){
				sql+="finishTime='"+status.GetfinishTime()+"' and ";
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
						status=new Status(res.getInt("id"),
									res.getInt("contractNo"),
									res.getInt("contractStatus"),
									res.getString("finishTime"));
						
						arr.add(status);
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
	 * ���ؼ�¼������
	 * @param 
	 * @return int
	 */
	@Override
	public int getRecordNum() {
		// TODO Auto-generated method stub
		return this.recordNum;
	}
	/**
	 * ����ҳ��
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
		return null;
	}
	@Override
	public IEntity GetOneEntity(IEntity entity) {
		// TODO Auto-generated method stub
		Status status=(Status)entity;
		if(super.ConnectMySQL()){
			String sql="select * from status where del=0 and ";
			if(status.GetId()!=0){
				sql+="id="+status.GetId()+" and ";
			}
			if(status.GetcontractNo()!=0){
				sql+="contractNo='"+status.GetcontractNo()+"' and ";
			}
			if(status.GetcontractStatus()!=0){
				sql+="contractStatus="+status.GetcontractStatus()+" and ";
			}
			if(status.GetfinishTime()!=null){
				sql+="finishTime='"+status.GetfinishTime()+"' and ";
			}
			
			//ȥ�����ġ�and��
			sql=sql.substring(0, sql.length()-4);
			
			ResultSet res;
			try {
				res=stmt.executeQuery(sql);
				if(res.next()){
					status=new Status(res.getInt("id"),
								res.getInt("contractNo"),
								res.getInt("contractStatus"),
								res.getString("finishTime"));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				super.CloseMySQL();
			}
			
		}
		
		return status;
	}
}
