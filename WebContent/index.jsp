<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生信息管理系统</title>

<script type="text/javascript">
function checkClassTab(clsTab){
	//var clsTab=document.getElementsByName("classTab");
 	if(clsTab.classes.value==""||clsTab.faculty.value==""||clsTab.chiefno.value==""||clsTab.classnum.value==""){
		alert("信息不完整！");
		return false;
	}
	
}
</script>
</head>
<body>
	<h1>学生信息管理系统</h1>
	<hr>
	<h3>显示所有表：</h3>
	
	<%
		Class.forName("com.mysql.jdbc.Driver");
	    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student_manager", "root", "");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("show tables");
		
		out.println("<table border=\"1px\" cellspacing=\"0px\"><tr><td>表名</td><td>操作</td></tr>");
		
		while(rs.next())
		{
			out.println("<tr>");
			out.println("<td>"+rs.getString(1)+"</td>");
			out.println("<td><a href=\"index.jsp?name="+rs.getString(1)+"\">显示</a></td>");
			out.println("</tr>");
		}
		out.println("</table>");
		
		String name;
		if((name=request.getParameter("name"))!=null){
			
			out.println("<hr>");
			out.println("表"+name+":<br>");
			
			rs=stmt.executeQuery("select *from "+name);
			ResultSetMetaData stru=rs.getMetaData();
			
			out.println("<table border='2px' cellspacing=\"0px\">");
			out.println("<tr>");
			for(int i=1;i<=stru.getColumnCount();i++){
				out.println("<td>"+stru.getColumnName(i)+"</td>");
			}
			out.println("<td>操作</td>");
			out.println("</tr>");
			
			while(rs.next())
			{
				out.println("<tr>");
				for(int i=1;i<=stru.getColumnCount();i++){
					out.println("<td>"+rs.getString(i)+"</td>");
				}
				String ope="<td><a href=\"del.do?key="+rs.getString(1)+"&keyname="+stru.getColumnName(1)+"&table="+name+"\" >删除</a>&nbsp;";
				ope+="<a href=\"update.jsp?";
				for(int j=1;j<=stru.getColumnCount();j++){
					ope+=stru.getColumnName(j)+"="+rs.getString(j);
					if(j!=stru.getColumnCount())
						ope+="&";
				}
				ope+="\" >更新</a></td>";
				out.println(ope);
				out.println("</tr>");
			}
			out.println("</table>");
		}
		
		rs.close();
		stmt.close();
		con.close();
	%>
	<h4>插入数据:</h4>
	<form name="Tab" action="insertToClass" method="post" onsubmit="return checkClassTab(this)">
		班级：<input type="text" name="classes" size="8"/>
		院系：<input type="text" name="faculty" size="20"/>
		班主任工号：<input type="text" name="chief_tno" size="8"/>
		班级人数：<input type="text" name="stuNum" size="4"/>
		<input type="submit" value="插入" />
	</form>
	
	
	<hr>
</body>
</html>