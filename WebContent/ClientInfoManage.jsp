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
    	ArrayList<Client> clients=(ArrayList<Client>)request.getAttribute("clients");
    	String keyName=(String)request.getAttribute("keyName");
	    Integer pageNo=(Integer)request.getAttribute("pageNo");
	    Integer pageNum=(Integer)request.getAttribute("pageNum");
	    Integer pageRecordNum=(Integer)request.getAttribute("pageRecordNum");
	    Integer totalRecordNum=(Integer)request.getAttribute("totalRecordNum");
    %>
	
	<script type="text/javascript">
		function ChangeHref(id){
			
			var newHref=document.getElementById(id).getAttribute("href");
			if(newHref.match("keyName")==null&&newHref.match("#")==null){
				newHref+="&keyName="+document.getElementById("keyName").value;
			}
			document.getElementById(id).setAttribute("href",newHref);
		}
		
		function DeleteClient(id){
			if(confirm("删除后不会删除其他合同信息，确定要删除此客户吗？")){
				var deleteRequest=null;
				if(window.XMLHttpRequest){
					deleteRequest=new XMLHttpRequest();
				}else if(window.ActiveXObject){
					deleteRequest=new ActiveXObject("Microsoft.XMLHttp");
				}
				
				if(deleteRequest){
					deleteRequest.open("get","ClientInfoManage?type=delete&id="+id,true);
					deleteRequest.onreadystatechange=function(){
						if(deleteRequest.readyState==4&&deleteRequest.status==200){
							alert(deleteRequest.responseText);
							document.location = "ClientInfoManage";
						}
					};
					deleteRequest.send();
				}
			}
		}
		
		function  ModifyClient(){
			if(document.getElementById("NewTel").value==""||document.getElementById("NewClientName").value==""
					||document.getElementById("NewFax").value==""){
				alert("输入格式错误！！");				
				return false;
			}else{
				
				 var modify=null;
		          if(window.XMLHttpRequest){
		        	 modify=new XMLHttpRequest();
		  		  }else if(window.ActiveXObject){
		  			 modify=new ActiveXObject("Microsoft.XMLHttp");
		  	      }
		  			
		  			if(modify){
		  				var pre="ClientInfoManage?type=modify_submit&id="+document.getElementById("MID").value+
		  						"&NewClientName="+document.getElementById("NewClientName").value+
		  						"&NewTel="+document.getElementById("NewTel").value+
		  						"&NewFax="+document.getElementById("NewFax").value+
		  						"&NewPostCode="+document.getElementById("NewPostCode").value+
		  						"&NewBankName="+document.getElementById("NewBankName").value
		  						+"&NewBankAccount="+document.getElementById("NewBankAccount").value;
		  				
		  				modify.open("get",pre,true);
		  				modify.send();
		  				modify.onreadystatechange=function(){
							if(modify.readyState==4&&modify.status==200){
								//判断ajax返回结果
								alert(modify.responseText);
								document.location = "ClientInfoManage";
							}else{
								alert("ReadyStatus: "+modify.readyState+"  Status: "+modify.status);
							}
		  				};
		  				
		  			}
			}
		}
		
		function AddClient(){
			var a=document.getElementById("NClientName").value;
			var b=document.getElementById("NTel").value;
			var c=document.getElementById("NFax").value;
			var d=document.getElementById("NPostCode").value;
			var e=document.getElementById("NBankName").value;
			var f=document.getElementById("NBankAccount").value;
			var g=document.getElementById("NAddress").value;
			if(a==""||b==""||c==""||g==""){
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
			  				var pre="ClientInfoManage?type=add&NewClientName="+a+
			  						"&NewTel="+b+"&NewFax="+c+
			  						"&NewPostCode="+d+"&NewBankName="+e+
			  						"&NewBankAccount="+f+"&NewAddress="+g;
			  				
			  				submit.open("get",pre,true);
			  				submit.onreadystatechange=function(){
								if(submit.readyState==4&&submit.status==200){
									//判断ajax返回结果
									alert(submit.responseText);
									document.location = "ClientInfoManage";
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
		document.getElementById("khxxgl").setAttribute("class","list-group-item bg active");
      </script>
      
      <div class="panel panel-default col-sm-10 bg"><!--panel start-->
          <div class="panel-heading">
            <h3 class="panel-title">客户列表</h3>
          </div>
          <div class="panel-body"><!--panel body start-->
            <form class="form-horizontal" role="form" action="ClientInfoManage" method="get">            
              <div class="form-group">
                <label for="客户姓名" class="col-sm-2 control-label">客户姓名：</label>
                  <div class="input-group col-sm-4">
                    <input id="keyName" name="keyName" type="text" class="form-control" value="<%if(keyName!=null) out.print(keyName); %>">
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-default">搜索</button>
                    </span>
                  </div><!-- /input-group -->
              </div>
            </form>
            <table class="table table table-hover table-condensed table-condensed table-white">
                <thead>
                	<th>序号</th>
                    <th>客户姓名</th>
                    <th>电话</th>
                    <th>传真</th>
                    <th>邮编</th>
                    <th>操作</th>
                </thead>
                <tbody>
                  <% if(clients!=null){ %>
                  <% for(int i=0;i<clients.size();i++){%>
                    <tr>
                        <td><%=((Client)clients.get(i)).GetId()%></td>
                        <td><%=((Client)clients.get(i)).GetName()%></td>
                        <td><%=((Client)clients.get(i)).GetTel()%></td>
                        <td><%=((Client)clients.get(i)).GetFax()%></td>
                        <td><%=((Client)clients.get(i)).GetPostCode()%></td>
                        <td>
                          <a onclick="document.getElementById('address').value='<%=((Client)clients.get(i)).GetAddress()%>';
                          			  document.getElementById('bankName').value='<%=clients.get(i).GetBankName()%>';
                          			  document.getElementById('bankAccount').value='<%=clients.get(i).GetBankAccount()%>';
                          			 " data-toggle="modal" data-target="#myModal">更多信息</a>&nbsp;&nbsp;
                          <a onclick="document.getElementById('MID').value='<%=clients.get(i).GetId()%>';
                          			  document.getElementById('NewClientName').value='<%=clients.get(i).GetName()%>';
                          			  document.getElementById('NewTel').value='<%=clients.get(i).GetTel()%>';
                          			  document.getElementById('NewFax').value='<%=clients.get(i).GetFax()%>';
                          			  document.getElementById('NewPostCode').value='<%=clients.get(i).GetPostCode()%>';
                          			  document.getElementById('NewBankName').value='<%=clients.get(i).GetBankName()%>';
                          			  document.getElementById('NewBankAccount').value='<%=clients.get(i).GetBankAccount()%>';
                          			 " data-toggle="modal" data-target="#myModal2">修改</a>&nbsp;&nbsp;
                          <a onclick="DeleteClient('<%=((Client)clients.get(i)).GetId()%>')">删除</a>
                        </td>
                    </tr>
                   <%} %>
                   <%} %>
                </tbody>
            </table>
            <form class="form-horizontal" role="form">
	            <div class="form-group">
		          <div class="col-sm-7 col-sm-offset-4">
	                <a id="pageone" onmouseover="ChangeHref('pageone')" class="col-sm-1 col-sm-offset-1" href="<%if(pageNo!=1){out.print("ClientInfoManage?pageNo=1");}else{out.print("#");}%>"><span class="glyphicon glyphicon-fast-backward myclickstyle"></span></a>
		      	    <a id="pagebefore" onmouseover="ChangeHref('pagebefore')" class="col-sm-1" href="<% if(pageNo>=2){out.print("ClientInfoManage?pageNo="+(pageNo-1));}else{out.print("#");} %>"><span class="glyphicon glyphicon-arrow-left myclickstyle"></span></a>
		      	    <span class="col-sm-3"><%=pageNo %>/<%=pageNum %>(共 <%=totalRecordNum%> 条)</span>
		      	    <a id="pagenext" onmouseover="ChangeHref('pagenext')" class="col-sm-1" href="<% if(pageNo+1<=pageNum){out.print("ClientInfoManage?pageNo="+(pageNo+1));}else{out.print("#");} %>"><span class="glyphicon glyphicon-arrow-right myclickstyle"></span></a>
		      	    <a id="pagefinal" onmouseover="ChangeHref('pagefinal')" class="col-sm-1" href="<%if(pageNo!=pageNum){out.print("ClientInfoManage?pageNo="+pageNum);}else{out.print("#");}%>"><span class="glyphicon glyphicon-fast-forward myclickstyle"></span></a>
		      	    <!--<span>每页<input id="pageRecordNum" type="text" size="3" value="">条</span>-->
		          </div><!--display table page-->
		        </div>
                <div class="form-group">
                  <div class="col-sm-2 col-sm-offset-10">
                    <a class="btn btn-info btn-block" data-toggle="modal" data-target="#myModal3">增加客户</a>                    
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
                <h4 class="modal-title" id="myModalLabel">更多信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" >
                	<div class="form-group">
                        <label class="col-sm-3 control-label">详细地址：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="address" type="text" class="form-control" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">银行名称：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="bankName" type="text" class="form-control" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">银行账户：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="bankAccount" type="text" class="form-control" disabled>
                        </div>
                    </div>                                          
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>                   
                    </div>
                   </form>
                  </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
              </div>
         </div><!-- /.modal -->
      
       <!-- Modal2 -->
      <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">                
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">修改客户信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                	<input  id="MID" name="MID" type="text" style="display:none;">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">客户名称：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NewClientName" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">电话：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NewTel" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">传真：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input  id="NewFax" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">邮编：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input  id="NewPostCode" type="text" class="form-control" >
                        </div>
                    </div> 
                    <div class="form-group">
                        <label class="col-sm-3 control-label">银行名称：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NewBankName" type="text" class="form-control" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">银行账户：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NewBankAccount" type="text" class="form-control" >
                        </div>
                    </div>                                                                                
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-info" onclick="return ModifyClient()">修改</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>                   
                    </div>
                   </form>
                  </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
              </div>
         </div><!-- /.modal -->   
         
       <!-- Modal3 -->
      <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">                
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">增加客户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" action="" method="post">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">客户名称：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NClientName" type="text" class="form-control">
                        </div>
                        <!-- <label for="用户名" class="col-sm-2 control-label" style="color:#FF0000;text-align:left;">已存在</label> -->
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">电话：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NTel" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">地址：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NAddress" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">传真：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NFax" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">邮编：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NPostCode" type="text" class="form-control" >
                        </div>
                    </div> 
                    <div class="form-group">
                        <label class="col-sm-3 control-label">银行名称：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NBankName" type="text" class="form-control" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">银行账户：</label>
                        <div class="col-xs-3 col-sm-6">
                          <input id="NBankAccount" type="text" class="form-control" >
                        </div>
                    </div>                                                                                
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-info" onclick="return AddClient()">增加</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>                   
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