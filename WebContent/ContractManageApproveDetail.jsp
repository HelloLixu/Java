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
    Contract contract = (Contract)request.getAttribute("contract");
    %>
	
</head>
<body>
  <!-- 引用外部页眉    -->
  <%@ include file="Head.jsp"%>

  <div class="container-manage">
  	  <!-- 引用合同管理左边导航栏    -->
      <%@ include file="ContractManageNav.jsp"%>
	  <script type="text/javascript">
	  	document.getElementById("spht").setAttribute("class","list-group-item bg active");
	  	function reset() {
	        document.getElementById("spyj").value = "";    
	    }
	  </script>
	  
	  <div class="panel panel-default col-sm-10 bg"><!--panel start-->
          <div class="panel-heading">
            <h3 class="panel-title">审批合同</h3>
          </div>  
          <div class="panel-body">
            <form class="form-horizontal" role="form" action="ContractManageApprove?type=approveOper&id=<%=contract.GetId()%>" method="post">
                <div class="form-group">
                    <label class="col-sm-2 control-label">合同名称：</label>
					<label class="col-sm-6 control-label" style="text-align:left"><%=contract.GetName()%></label>
                </div>
                <div class="form-group">
                    <label  for="合同内容" class="col-sm-2 control-label" >合同内容：</label>
                    <div class="col-sm-9" style="margin-top:10px;">
                      <textarea class="form-control" rows="5" disabled><%=contract.GetContent()%></textarea>
                    </div>              
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">审批意见：</label>
                    <div class="col-sm-9" style="margin-top:10px;">
                      <textarea id="spyj" maxlength="100" name="spyj" class="form-control" rows="4"></textarea>
                    </div>              
                </div>               
                <div class="form-group">
					<div class="radio col-sm-2 col-sm-offset-4">
					  <label>
					    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
					            通过
					  </label>
					</div>
					<div class="radio col-sm-2 col-sm-offset-2">
					  <label>
					    <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
					            拒绝
					  </label>
					</div>              	
                </div>					                                                                        
                <div class="form-group">
                    <div class="col-sm-2 col-sm-offset-4">
                      <button type="submit" class="btn btn-info btn-block">提交</button>
                    </div>
                    <div class="col-sm-2">
                      <button type="reset" class="btn btn-info btn-block" onclick="setTimeout('reset()',100);">重置</button>            
                    </div>
                </div>
            </form>
          </div>
        </div><!--panel over-->
  </div>
  
  
  <div style="margin-top:550px;"><jsp:include page="Footer.jsp"></jsp:include></div>
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="css/bootstrap_3.0.3/js/jquery-2.0.2.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="css/bootstrap_3.0.3/js/bootstrap.js"></script>
  <script src="css/main_js/supersized.3.2.7.min.js"></script>
  <script src="css/main_js/supersized-init.js"></script>
</body>
</html>