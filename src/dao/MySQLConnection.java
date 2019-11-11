package dao;

import java.sql.*;

import com.mysql.jdbc.PreparedStatement;

public class MySQLConnection {
	
	protected Connection con=null;
	protected PreparedStatement preStmt=null;
	protected Statement stmt=null;
	
	public MySQLConnection(){
		//this.ConnectMySQL("contractmanager", "3306", "root", "");
	}
	
	public boolean ConnectMySQL(){
		
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			return false;
//		}
//		
//		try {
//			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/contractmanager", "root", "");
//			stmt=con.createStatement();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			return false;
//		}
		
		this.ConnectMySQL("contractmanage", "3306", "root", "");
		
		return true;
	}

	public boolean ConnectMySQL(String dbname,String port,String username,String password){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			//172.31.34.50
			con=DriverManager.getConnection("jdbc:mysql://localhost:"+port+"/"+dbname,username,password);
			stmt=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean CloseMySQL(){
		
		try {
			if(preStmt!=null) preStmt.close();
			if(stmt!=null) stmt.close();
			if(con!=null) con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
			
}
