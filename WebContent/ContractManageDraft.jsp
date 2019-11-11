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
    <link href="css/main_css/bootstrap-datetimepicker.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" type="text/css" href="css/main_css/main.css">
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
        <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <%
	    ArrayList<IEntity> clients=(ArrayList<IEntity>)request.getAttribute("clients");
    	String result=(String)request.getAttribute("result");
    %>
	
</head>
<script type="text/javascript">
	
	
	function CheckNull(){	
		if(document.getElementById("contractName").value==""||document.getElementById("startTime").value==""||
				document.getElementById("finishTime").value==""||document.getElementById("contractContent").value==""){
			alert("信息不完整，* 为必填项！");
			return false;
		} else {
			return true;
		}
	}
		
	function reset() {
        document.getElementById("contractName").value = "";
        document.getElementById("startTime").value = "";
        document.getElementById("finishTime").value = "";
        document.getElementById("contractContent").value = "";
        document.getElementById("textfield").value = "";
        
    }
  </script>
<body>
  <!-- 引用外部页眉    -->
  <%@ include file="Head.jsp"%>
  <script type="text/javascript">
    document.getElementById("htgl").setAttribute("class","active");
  </script>
      
 
  <div class="container-manage">
      <!-- 引用合同管理左边导航栏    -->
      <%@ include file="ContractManageNav.jsp"%>
	  <script type="text/javascript">
	    document.getElementById("qcht").setAttribute("class","list-group-item bg active");
	  </script>
      
      <div class="panel panel-default col-sm-10 bg"><!--panel start-->
          <div class="panel-body">
            <form class="form-horizontal" role="form" action="ContractManageDraft?type=draftOper" method="post" onsubmit="return CheckNull()">
            	<% if(result!=null){ %>
            		<div class="form-group"><div class="alert alert-success"><%=result %></div></div>
      			<%}%>
                <div class="form-group">
                    <label for="合同名称" class="col-sm-2 control-label">合同名称：</label>
                    <div class="col-sm-6">
                      <input maxlength="50" name="contractName" type="text" class="form-control" id="contractName">
                    </div>
                    <label for="合同名称" class="col-sm-3 control-label" style="color:#ffffff;text-align:left;">*</label>
                </div>
                <div class="form-group">
                    <label for="客户" class="col-sm-2 control-label">客户：</label>
                    <div class="col-sm-6">
                      <select class="form-control" name="client">
						  <option>选择客户</option>
						  <%
						    if(clients!=null){
						    	for(int i=0;i<clients.size();i++){
						    		out.println("<option>"+((Client)clients.get(i)).GetName()+"</option>");
						    	}
						    }
						   %>
					  </select>
                    </div>
                    <div class="col-sm-3">
                      <label>没有客户，<a href="ClientInfoManage?type=add_display" class="btn btn-success btn-xs" id="新增客户">新增客户</a></label>
                    </div>
                </div>
                <div class="form-group">
                <label for="startTime" class="col-md-2 control-label">开始时间: </label>
                <div class="input-group date form_date col-sm-4" data-date="" data-date-format="yyyy-MM-dd" data-link-field="startTime" data-link-format="yyyy-mm-dd" style="padding-left:15px;">
                    <input class="form-control" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
				<input type="hidden" id="startTime" name="startTime" value="" />
			  </div>
			  <div class="form-group">
				<label for="finishTime" class="col-md-2 control-label">结束时间: </label>
                <div class="input-group date form_date col-sm-4" data-date="" data-date-format="yyyy-MM-dd" data-link-field="finishTime" data-link-format="yyyy-mm-dd" style="padding-left:15px;">
                    <input class="form-control" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
				<input type="hidden" id="finishTime" name="finishTime" value="" />
			  </div>
                <div class="form-group">
                    <label for="合同内容" class="col-sm-2 control-label">合同内容：</label>
                    <label  for="合同内容" class="col-sm-10 control-label" style="color:#ffffff;text-align:left;">*</label>
                    <div class="col-sm-9 col-sm-offset-2">
                      <textarea maxlength="500" name="contractContent" class="form-control" rows="4" id="contractContent"></textarea>
                    </div>              
                </div>
                <div class="form-group">
                    <label for="附件" class="col-sm-2 control-label">附件：</label>
                    <div class="file-box col-sm-10"> 
                        <input type="file" name="fileField" class="file" id="fileField" size="24"onchange="document.getElementById('textfield').value=this.value">
                        <input type='button' class='btnnew' value='浏览文件' disabled="disabled"> 
                        
                        <input type='text' name='textfield' id='textfield' class='txt' readonly=""> 
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2 col-sm-offset-4">
                      <button type="submit" class="btn btn-info btn-block" id="提交">提交</button>                    
                    </div>
                    <div class="col-sm-2">
                      <button type="reset" class="btn btn-info btn-block" id="重置" onclick="setTimeout('reset()',100);">重置</button>               
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
  <script type="text/javascript" src="css/main_js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
  <script type="text/javascript" src="css/main_js/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
  <script type="text/javascript">
	$('.form_date').datetimepicker({
        language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
  </script>
</body>
</html>