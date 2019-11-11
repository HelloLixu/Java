<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*" %>

<div class="list-group col-sm-2">
  <a href="BaseDataManage" class="list-group-item activetitle"><h2>基础信息管理</h2></a>
  
  <%
    ArrayList<String> right=(ArrayList<String>)session.getAttribute("rightCode");
    for(int i=0;i<right.size();i++){
    	if(right.get(i).equals("HTXXGL")){
    		out.println("<a id=\"htxxgl\" href=\"ContractInfoManage\" class=\"list-group-item bg\"><h3>合同信息管理</h3></a>");
    	}
    	if(right.get(i).equals("KHXXGL")){
    		out.println("<a id=\"khxxgl\" href=\"ClientInfoManage\" class=\"list-group-item bg\"><h3>客户信息管理</h3></a>");
    	}
    }
  
  %>
</div>