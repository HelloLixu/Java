package stuInfo;

import java.sql.*;


public class SqlManager {
	private java.sql.Connection con=null;
	private java.sql.Statement stmt=null;
	
	boolean ConnectMySql(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		try {
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student_manager", "root", "");
			stmt=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	boolean CloseMySql(){
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
/*	boolean ExecuteQuery(String sql){
		try {
			stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}*/
	
	java.sql.Connection GetConnection(){
		return con;
	}
	
	java.sql.Statement GetStatement(){
		return stmt;
	}
	
	
}
