<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>合同管理系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link rel="shortcut icon" href="img/title.ico">
    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap_3.0.3/css/bootstrap.css">
    <link rel="stylesheet" href="css/main_css/supersized.css">
    <link rel="stylesheet" type="text/css" href="css/main_css/main.css">
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
        <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <%
    	Contract contract=(Contract)request.getAttribute("contract");
    	String clientName=(String)request.getAttribute("client");
    	Integer statusCode=(Integer)request.getAttribute("statusCode");
    	String drafterName=(String)request.getAttribute("drafter");
    	String allocaterName=(String)request.getAttribute("allocater");
    	ArrayList<String> countsignerNames=(ArrayList<String>)request.getAttribute("countsigners");
    	ArrayList<String> approverNames=(ArrayList<String>)request.getAttribute("approvers");
    	ArrayList<String> signerNames=(ArrayList<String>)request.getAttribute("signers");
    %>
	
</head>
<body>
  <%@ include file="Head.jsp"%>
  <script type="text/javascript">
	document.getElementById("cxtj").setAttribute("class","active");
  </script>
   
  <div class="container-manage">
      <!-- 引用系统管理导航栏    -->
      <%@ include file="QueryStatisticsNav.jsp" %>
      <script type="text/javascript">
		document.getElementById("htxxcx").setAttribute("class","list-group-item bg active");
      </script>
      
      <!-- 显示合同的详细信息    -->
      <div class="panel panel-default col-sm-10 bg"><!--panel start-->
          <div class="panel-body">
            <form class="form-horizontal" role="form" >
                <div class="form-group">
                    <label for="合同名称" class="col-sm-2 control-label">合同名称：</label>
                    <div class="col-sm-6">
                      <label class="control-label"><%=contract.GetName() %></label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="客户" class="col-sm-2 control-label">客户：</label>
                    <div class="col-sm-6">
                    	<label class="control-label"><%=clientName %></label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="开始时间" class="col-sm-2 control-label">开始时间：</label>
                    <div class="col-sm-6">
                    	<label class="control-label"><%=contract.GetStartTime() %></label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="结束时间" class="col-sm-2 control-label">结束时间：</label>
                    <div class="col-sm-6">
                      <label class="control-label"><%=contract.GetFinishTime() %></label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="合同内容" class="col-sm-2 control-label">合同内容：</label>
                    <div class="col-sm-9 col-sm-offset-2" style="margin-top:-15px;">
                      <textarea maxlength="400" name="contractContent" class="form-control" rows="6" readonly="readonly" id="contractContent"><%=contract.GetContent() %></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="合同状态" class="col-sm-2 control-label">合同状态：</label>
                    <div class="col-sm-6"> 
                        <label class="control-label">
                            <%if(statusCode.equals(StatusCode.STATUS_FINISH_DRAFT)){
	                        	out.print("分配中");
	                        }else if(statusCode.equals(StatusCode.STATUS_FINISH_ALLOCATE)){
	                        	out.print("会签中");
	                        }else if(statusCode.equals(StatusCode.STATUS_FINISH_COUNTERSIGN)){
	                        	out.print("定稿中");
	                        }else if(statusCode.equals(StatusCode.STATUS_FINISH_FINALIZE)){
	                        	out.print("审核中");
	                        }else if(statusCode.equals(StatusCode.STATUS_FAIL_APPROVE)){
	                        	out.print("审核未通过");
	                        }else if(statusCode.equals(StatusCode.STATUS_FINISH_APPROVE)){
	                        	out.print("签订中");
	                        }else if(statusCode.equals(StatusCode.STATUS_FINISH_SIGN)){
	                        	out.print("签订完成");
	                        }else{
	                        	out.print("未知状态");
	                        }
	                        %>
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="起草人" class="col-sm-2 control-label">起草人：</label>
                    <div class="col-sm-10 control-label" style="text-align: left; "> 
                        <span class="label label-primary"><%=drafterName %></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="会签人" class="col-sm-2 control-label">会签人：</label>
                    <div class="col-sm-10 control-label" style="text-align: left; ">
                      <% if(countsignerNames!=null){ %>
                        <%for(int i=0;i<countsignerNames.size();i++){ %>
                          <span class="label label-primary"><%=countsignerNames.get(i) %></span>
                        <%} %>
                      <%} %>
                    </div>
                </div>
                <div class="form-group">
                    <label for="审批人" class="col-sm-2 control-label">审批人：</label>
                    <div class="col-sm-10 control-label" style="text-align: left; ">
                      <% if(approverNames!=null){ %>
                        <%for(int i=0;i<approverNames.size();i++){ %>
                          <span class="label label-primary"><%=approverNames.get(i) %></span>
                        <%} %>
	                  <%} %>
                    </div>
                </div>
                <div class="form-group">
                    <label for="签订人" class="col-sm-2 control-label">签订人：</label>
                    <div class="col-sm-10 control-label" style="text-align: left; ">
                      <% if(signerNames!=null){ %>
                        <%for(int i=0;i<signerNames.size();i++){ %>
                          <span class="label label-primary"><%=signerNames.get(i) %></span>
                        <%} %>
                      <%} %>
                    </div>
                </div>
                <div class="form-group">
                    <label for="附件" class="col-sm-2 control-label">附件：</label>
                    <div class="col-sm-6 control-label" style="text-align: left; "> 
                        <label>无</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2 col-sm-offset-4">
                      <button type="button" class="btn btn-info btn-block" id="返回" onclick="javascript:history.back(-1)">返回</button>                    
                    </div>
                </div>
            </form>
          </div>
        </div><!--panel over-->
      
    </div>
   	<div style="margin-top:800px;"><jsp:include page="Footer.jsp"></jsp:include></div>
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="css/bootstrap_3.0.3/js/jquery-2.0.2.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="css/bootstrap_3.0.3/js/bootstrap.js"></script>
  <script src="css/main_js/supersized.3.2.7.min.js"></script>
  <script src="css/main_js/supersized-init.js"></script>
</body>
</html>