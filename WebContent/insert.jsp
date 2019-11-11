<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>插入数据</title>
<%
	Connection con=null;
	Statement stmt=null;
%>
</head>
<body>
	<%
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student_manager", "root", "");
		stmt=con.createStatement();
		
		//String tablename=request.getParameter("");
		
		int succ=stmt.executeUpdate("insert into ");
		if(succ!=0){
			out.println("插入成功！");
		}else{
			out.println("插入失败！");
		}
		
		stmt.close();
		con.close();
	%>
</body>
</html>