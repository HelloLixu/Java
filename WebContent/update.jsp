<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*" %>
<%@ page import="stuInfo.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新</title>
<script type="text/javascript">
function checkClassTab(clsTab){
	//var clsTab=document.getElementsByName("classTab");
 	if(clsTab.classes.value==""||clsTab.faculty.value==""||clsTab.chiefno.value==""||clsTab.classnum.value==""){
		alert("信息不完整！");
		return false;
	}
	
}

</script>

<%
	request.setCharacterEncoding("utf-8");
	String classno=request.getParameter("classno");
	String f=new String(request.getParameter("faculty").getBytes("iso-8859-1"),"utf-8");
	String chiefno=request.getParameter("chief_tno");
	String classnum=request.getParameter("stuNum");
%>
</head>
<body>
	<h1>学生信息管理系统</h1>
	<hr/>
	<h4>更新数据:</h4>
	<form name="claaTab" action="update.do" method="get" onsubmit="return checkClassTab(this)">
		班级：<input type="text" name="classno" value=<%=classno %> size="8"/>
		院系：<input type="text" name="faculty" value=<%=f %> size="20"/>
		班主任工号：<input type="text" name="chief_tno" value=<%=chiefno %> size="8"/>
		班级人数：<input type="text" name="stuNum" value=<%=classnum %> size="4"/>
		<input type="submit" value="更新" />
	</form>
	
	<hr/>
	
</body>
</html>