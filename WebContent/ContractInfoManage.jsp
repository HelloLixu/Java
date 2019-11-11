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
	    ArrayList<Contract> contracts=(ArrayList<Contract>)request.getAttribute("contracts");
    	ArrayList<Integer> statusCode=(ArrayList<Integer>)request.getAttribute("statusCode");
	    Integer pageNo=(Integer)request.getAttribute("pageNo");
	    Integer pageNum=(Integer)request.getAttribute("pageNum");
	    Integer pageRecordNum=(Integer)request.getAttribute("pageRecordNum");
	    Integer totalRecordNum=(Integer)request.getAttribute("totalRecordNum");
	    String contractName=(String)request.getAttribute("contractName");
    %>
	
	<script type="text/javascript">
		function ChangeHref(id){
			
			var newHref=document.getElementById(id).getAttribute("href");
			if(newHref.match("contractName")==null&&newHref.match("#")==null){
				newHref+="&contractName="+document.getElementById("contractName").value;
			}
			document.getElementById(id).setAttribute("href",newHref);
		}
		
		function DeleteContract(id){
			if(confirm("删除后会删除其关联的任何信息，确定要删除此合同吗？")){
				var deleteRequest=null;
				if(window.XMLHttpRequest){
					deleteRequest=new XMLHttpRequest();
				}else if(window.ActiveXObject){
					deleteRequest=new ActiveXObject("Microsoft.XMLHttp");
				}
				
				if(deleteRequest){
					deleteRequest.open("get","ContractInfoManage?type=delete&id="+id,true);
					deleteRequest.onreadystatechange=function(){
						if(deleteRequest.readyState==4&&deleteRequest.status==200){
							alert(deleteRequest.responseText);
							document.location = "ContractInfoManage";
						}
					};
					deleteRequest.send();
				}
			}
		}
		
		
		function  ModifyContract(){

			if(document.getElementById("FinishTime").value==""||document.getElementById("StartTime").value==""
				||document.getElementById("contractContent").value==""||document.getElementById("NewContractName").value==""||document.getElementById("StartTime").value>document.getElementById("FinishTime").value){
				alert("输入格式错误！！");
				return false;
			}else{
				
				 var submit=null;
		          if(window.XMLHttpRequest){
		  		     submit=new XMLHttpRequest();
		  		  }else if(window.ActiveXObject){
		  		     submit=new ActiveXObject("Microsoft.XMLHttp");
		  	      }
		  			
		  			if(submit){
		  				var pre="ContractInfoManage?type=modify_submit&id="+document.getElementById("McontractNo").value
		  						+"&StartTime="+document.getElementById("StartTime").value+
		  						"&FinishTime="+document.getElementById("FinishTime").value+
		  						"&contractContent="+document.getElementById("contractContent").value+
		  						"&NewContractName="+document.getElementById("NewContractName").value;
	  				
		  				submit.open("get",pre,true);
		  				submit.onreadystatechange=function(){
						if(submit.readyState==4&&submit.status==200){
							//判断ajax返回结果
							alert(submit.responseText);
							document.location = "ContractInfoManage";
						}
		  				};
		  				submit.send();
		  			}
			}
		}
		
		
	</script>
</head>
<body>
  <%@ include file="Head.jsp"%>
  <script type="text/javascript">
	document.getElementById("jcsjgl").setAttribute("class","active");
  </script>
   
  <div class="container-manage">
      <!-- 引用系统管理导航栏    -->
      <%@ include file="BaseDataManageNav.jsp" %>
      <script type="text/javascript">
		document.getElementById("htxxgl").setAttribute("class","list-group-item bg active");
      </script>
      
      <div class="panel panel-default col-sm-10 bg"><!--panel start-->
          <div class="panel-heading">
            <h3 class="panel-title">合同列表</h3>
          </div>
          <div class="panel-body"><!--panel body start-->
            <form class="form-horizontal" role="form" action="ContractInfoManage" method="get">            
              <div class="form-group">
                <label for="合同名称" class="col-sm-2 control-label">合同名称：</label>
                <div class="input-group col-sm-4">
                  <input id="contractName" name="contractName" type="text" class="form-control" value="<%if(contractName!=null) out.print(contractName); %>">
                  <span class="input-group-btn">
                      <button type="submit" class="btn btn-default">搜索</button>
                  </span>
                </div><!-- /input-group -->                              
              </div>             
            </form>           
            <table class="table table table-hover table-condensed table-condensed table-white">
                <thead>
                	<th>序号</th>
                    <th>合同名称</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </thead>
                <tbody>
                  <% if(contracts!=null){ %>
                  <% for(int i=0;i<contracts.size();i++){%>
                    <tr>
                        <td><%=contracts.get(i).GetId()%></td>
                        <td><%=contracts.get(i).GetName()%></td>
                        <td><%=contracts.get(i).GetStartTime()%></td>
                        <td><%=contracts.get(i).GetFinishTime()%></td>
                        <td>
                        <%if(statusCode.get(i).equals(StatusCode.STATUS_FINISH_DRAFT)){
                        	out.print("分配中");
                        }else if(statusCode.get(i).equals(StatusCode.STATUS_FINISH_ALLOCATE)){
                        	out.print("会签中");
                        }else if(statusCode.get(i).equals(StatusCode.STATUS_FINISH_COUNTERSIGN)){
                        	out.print("定稿中");
                        }else if(statusCode.get(i).equals(StatusCode.STATUS_FINISH_FINALIZE)){
                        	out.print("审核中");
                        }else if(statusCode.get(i).equals(StatusCode.STATUS_FAIL_APPROVE)){
                        	out.print("审核未通过");
                        }else if(statusCode.get(i).equals(StatusCode.STATUS_FINISH_APPROVE)){
                        	out.print("签订中");
                        }else if(statusCode.get(i).equals(StatusCode.STATUS_FINISH_SIGN)){
                        	out.print("签订完成");
                        }else{
                        	out.print("未知状态");
                        }
                        %>
                        </td>
                        <td>
                          <a href="ContractInfoQuery?type=detail&id=<%=((Contract)contracts.get(i)).GetId()%>">详情</a>&nbsp;&nbsp;
                          <a onclick="document.getElementById('NewContractName').value='<%=((Contract)contracts.get(i)).GetName()%>';
                              		  document.getElementById('McontractNo').value='<%=((Contract)contracts.get(i)).GetId()%>';
              			  			  document.getElementById('StartTime').value='<%=((Contract)contracts.get(i)).GetStartTime()%>';
              			  			  document.getElementById('FinishTime').value='<%=((Contract)contracts.get(i)).GetFinishTime()%>';
              			  			  document.getElementById('contractContent').value='<%=((Contract)contracts.get(i)).GetContent()%>';
                          			  " data-toggle="modal" data-target="#myModal">修改</a>&nbsp;&nbsp;
                          <a onclick="DeleteContract('<%=((Contract)contracts.get(i)).GetId()%>')">删除</a>
                        </td>
                    </tr>
                   <%} %>
                   <%} %>
                </tbody>
            </table>
            <form class="form-horizontal" role="form">
	            <div class="form-group">
		          <div class="col-sm-7 col-sm-offset-3">
	                <a id="pageone" onmouseover="ChangeHref('pageone')" class="col-sm-1 col-sm-offset-1" href="<%if(pageNo!=1){out.print("ContractInfoManage?pageNo=1");}else{out.print("#");}%>"><span class="glyphicon glyphicon-fast-backward myclickstyle"></span></a>
		      	    <a id="pagebefore" onmouseover="ChangeHref('pagebefore')" class="col-sm-1" href="<% if(pageNo>=2){out.print("ContractInfoManage?pageNo="+(pageNo-1));}else{out.print("#");} %>"><span class="glyphicon glyphicon-arrow-left myclickstyle"></span></a>
		      	    <span class="col-sm-3"><%=pageNo %>/<%=pageNum %>(共 <%=totalRecordNum%> 条)</span>
		      	    <a id="pagenext" onmouseover="ChangeHref('pagenext')" class="col-sm-1" href="<% if(pageNo+1<=pageNum){out.print("ContractInfoManage?pageNo="+(pageNo+1));}else{out.print("#");} %>"><span class="glyphicon glyphicon-arrow-right myclickstyle"></span></a>
		      	    <a id="pagefinal" onmouseover="ChangeHref('pagefinal')" class="col-sm-1" href="<%if(pageNo!=pageNum){out.print("ContractInfoManage?pageNo="+pageNum);}else{out.print("#");}%>"><span class="glyphicon glyphicon-fast-forward myclickstyle"></span></a>
		      	    <!--<span>每页<input id="pageRecordNum" type="text" size="3" value="">条</span>-->
		          </div><!--display table page-->
		        </div>
                <div class="form-group">
                  <div class="col-sm-2 col-sm-offset-10">
                    <a href="ContractManageDraft" class="btn btn-info btn-block">增加合同</a>                    
                  </div>                              
                </div>	        
	        </form>
          </div><!--panel body over-->
      </div><!--panel over-->
       <!-- Modal -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">                
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">修改合同信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" >
                	<input  id="McontractNo" name="McontractNo" type="text" style="display:none;">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">合同名称：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input type="text" class="form-control" id="NewContractName" name="NewContractName">
                        </div>
                        <!-- <label for="用户名" class="col-sm-2 control-label" style="color:#FF0000;text-align:left;">已存在</label> -->
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">开始时间：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input type="text" class="form-control" id="StartTime" name="StartTime">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">结束时间：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input type="text" class="form-control" id="FinishTime" name="FinishTime">
                        </div>
                    </div> 
                    <div class="form-group">
                        <label class="col-sm-3 control-label">合同内容：</label>
	                    <div class="col-sm-9">
	                      <textarea maxlength="500" name="contractContent" class="form-control" rows="4" id="contractContent" name="contractContent"></textarea>
	                    </div> 
                    </div>                                 
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-info" onclick="return ModifyContract()">修改</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>                   
                    </div>
                   </form>
                  </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
              </div>
         </div><!-- /.modal -->     
    </div>
   	<div style="margin-top:600px;"><jsp:include page="Footer.jsp"></jsp:include></div>
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="css/bootstrap_3.0.3/js/jquery-2.0.2.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="css/bootstrap_3.0.3/js/bootstrap.js"></script>
  <script src="css/main_js/supersized.3.2.7.min.js"></script>
  <script src="css/main_js/supersized-init.js"></script>
</body>
</html>