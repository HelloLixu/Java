<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*" %>

<div class="list-group col-sm-2">
  <a href="ContractManage" class="list-group-item activetitle"><h2>合同管理</h2></a>
  
  <%
    ArrayList<String> right=(ArrayList<String>)session.getAttribute("rightCode");
    for(int i=0;i<right.size();i++){
    	if(right.get(i).equals("QCHT")){
    		out.println("<a id=\"qcht\" href=\"ContractManageDraft\" class=\"list-group-item bg\"><h3>起草合同</h3></a>");
    	}
    	if(right.get(i).equals("HQHT")){
    		out.println("<a id=\"hqht\" href=\"ContractManageCountersign\" class=\"list-group-item bg\"><h3>会签合同</h3></a>");
    	}
    	if(right.get(i).equals("DGHT")){
    		out.println("<a id=\"dght\" href=\"ContractManageFinalize\" class=\"list-group-item bg\"><h3>定稿合同</h3></a>");
    	}
    	if(right.get(i).equals("SPHT")){
    		out.println("<a id=\"spht\" href=\"ContractManageApprove\" class=\"list-group-item bg\"><h3>审批合同</h3></a>");
    	}
    	if(right.get(i).equals("QDHT")){
    		out.println("<a id=\"qdht\" href=\"ContractManageSign\" class=\"list-group-item bg\"><h3>签订合同</h3></a>");
    	}
    }
  
  %>
</div>