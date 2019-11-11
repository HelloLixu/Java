<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*" %>
<%@ page import="dao.*" %>
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
    int clientNo = (Integer)session.getAttribute("id");
    ArrayList<Contract> contracts=(ArrayList<Contract>)request.getAttribute("contracts");
    OperateFlowDAO operateFlowDAO=new OperateFlowDAO();
    ArrayList<OperateFlow> operateFlows=new ArrayList<OperateFlow>();
    for(int j=0;j<contracts.size();j++) {
    	OperateFlow tempOperateFlow = new OperateFlow();
    	tempOperateFlow.setContractNo(contracts.get(j).GetId());
    	tempOperateFlow.setOperatorNo(clientNo);
    	tempOperateFlow.setOperateType(StatusCode.OPERATETYPE_APPROVE);
    	operateFlows.add((OperateFlow)operateFlowDAO.GetOneEntity(tempOperateFlow));
    }		
    Integer pageNo=(Integer)request.getAttribute("pageNo");
    Integer pageNum=(Integer)request.getAttribute("pageNum");
    Integer pageRecordNum=(Integer)request.getAttribute("pageRecordNum");
    Integer totalRecordNum=(Integer)request.getAttribute("totalRecordNum");
    String contractName=(String)request.getAttribute("contractName");
    String result=(String)request.getAttribute("result");
    %>
	
</head>
<body>
  <!-- 引用外部页眉    -->
  <%@ include file="Head.jsp"%>
  <script type="text/javascript">
	document.getElementById("htgl").setAttribute("class","active");
	function ChangeHref(id){
		
		var newHref=document.getElementById(id).getAttribute("href");
		if(newHref.match("contractName")==null&&newHref.match("#")==null){
			newHref+="&contractName="+document.getElementById("contractName").value;
		}
		document.getElementById(id).setAttribute("href",newHref);
	}
  </script>
   
  <div class="container-manage">
  	  <!-- 引用合同管理左边导航栏    -->
      <%@ include file="ContractManageNav.jsp"%>
	  <script type="text/javascript">
	  	document.getElementById("dght").setAttribute("class","list-group-item bg active");
	  </script>
      <div class="panel panel-default col-sm-10 bg"><!--panel start-->
      <%if(result!=null){ %>
            		<div class="form-group"><div class="alert alert-success"><%=result %></div></div>
      <%}%> 
          <div class="panel-heading">
            <h3 class="panel-title">待定稿合同列表</h3>
          </div>        
          <div class="panel-body"><!--panel body start-->
            <form class="form-horizontal" role="form" action="ContractManageFinalize" method="get">
            	       
              <div class="form-group">
                <label class="col-sm-2 control-label">待定稿合同名称：</label>
                  <div class="input-group col-sm-4">
                    <input name="contractName" type="text" class="form-control">
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-default">搜索</button>
                    </span>
                  </div><!-- /input-group -->
              </div>
            </form>
            <table class="table table-hover table-condensed table-white" style="color:#ffffff;">
                <thead>
                	<th>序号</th>
                    <th>合同名称</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>操作</th>
                </thead>
                <tbody>
                <%for(int i=0;i<contracts.size();i++){ %>
                	 <tr>
                	 	<td><%=((Contract)contracts.get(i)).GetId()%></td>
						<td><%=((Contract)contracts.get(i)).GetName()%></td>
                		<td><%=((Contract)contracts.get(i)).GetStartTime()%></td>
                		<td><%=((Contract)contracts.get(i)).GetFinishTime()%></td>
                		<%if(operateFlows.get(i).getOperateStatus()==StatusCode.OPERATESTATUS_HAVE_REJECT) {%>
                		<td><a href="ContractManageFinalize?type=detail&flag=twice&id=<%=contracts.get(i).GetId()%>">重新定稿</a></td>
                		<%} else {%>
                		<td><a href="ContractManageFinalize?type=detail&flag=first&id=<%=contracts.get(i).GetId()%>">定稿</a></td>
                		<%}%>
                	</tr>
                <%}%>
                </tbody>
            </table>
            <form class="form-horizontal" role="form" action="" method="post">
            <div class="form-group">
	            <div class="col-sm-4 col-sm-offset-4">
            	<a id="pageone" onmouseover="ChangeHref('pageone')" class="col-sm-1 col-sm-offset-1" href="<%if(pageNo!=1){out.print("ContractManageFinalize?pageNo=1");}else{out.print("#");}%>"><span class="glyphicon glyphicon-fast-backward myclickstyle"></span></a>
	      	    <a id="pagebefore" onmouseover="ChangeHref('pagebefore')" class="col-sm-1" href="<% if(pageNo>=2){out.print("ContractManageFinalize?pageNo="+(pageNo-1));}else{out.print("#");} %>"><span class="glyphicon glyphicon-arrow-left myclickstyle"></span></a>
	      	    <span class="col-sm-3"><%=pageNo %>/<%=pageNum %>(共 <%=totalRecordNum%> 条)</span>
	      	    <a id="pagenext" onmouseover="ChangeHref('pagenext')" class="col-sm-1" href="<% if(pageNo+1<=pageNum){out.print("ContractManageFinalize?pageNo="+(pageNo+1));}else{out.print("#");} %>"><span class="glyphicon glyphicon-arrow-right myclickstyle"></span></a>
	      	    <a id="pagefinal" onmouseover="ChangeHref('pagefinal')" class="col-sm-1" href="<%if(pageNo!=pageNum){out.print("ContractManageFinalize?pageNo="+pageNum);}else{out.print("#");}%>"><span class="glyphicon glyphicon-fast-forward myclickstyle"></span></a>
	      	  <!--<span>每页<input id="pageRecordNum" type="text" size="3" value="">条</span>-->
	        </div><!--display table page-->
	        </div>
	        </form>
          </div><!--panel body over-->
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