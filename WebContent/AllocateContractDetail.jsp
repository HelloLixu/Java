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
	   Contract contract=(Contract)request.getAttribute("contracts");
       ArrayList<String> hqname=(ArrayList<String>)request.getAttribute("hqname");
       ArrayList<String> spname=(ArrayList<String>)request.getAttribute("spname");
       ArrayList<String> qdname=(ArrayList<String>)request.getAttribute("qdname");
    
    %>
    <script type="text/javascript">
    var hqnames=[];
    var spnames=[];
    var qdnames=[];
    
    function ajaxsubmit(){
    	
    	if(hqnames.length==0||spnames.length==0||qdnames.length==0){
    		alert("分配人员不全！");
    		return false;
    	}
    	
    	
    	  var submit=null;
          if(window.XMLHttpRequest){
  		     submit=new XMLHttpRequest();
  		  }else if(window.ActiveXObject){
  		     submit=new ActiveXObject("Microsoft.XMLHttp");
  	      }
  			
  			if(submit){
  				var pre="AllocateContract?type=allocateOper&id="+document.getElementById("id").value+"&submit=";
  				var str="";
  				
  				for(var i=0;hqnames[i]!=null;i++)
  				{
  					str+=hqnames[i];
  					
  					if(hqnames[i+1]!=null)
  					   str+=",";
  				}
  				str+=":";
  				for(var i=0;spnames[i]!=null;i++)
  				{
  					str+=spnames[i];
  					
  					if(spnames[i+1]!=null)
  					   str+=",";
  				}
  				str+=":";
  				for(var i=0;qdnames[i]!=null;i++)
  				{
  					str+=qdnames[i];
  					
  					if(qdnames[i+1]!=null)
  					    str+=",";
  				}
  				//对含姓名的中文进行utf-8编码
  				str=encodeURI(encodeURI(str));
  				pre+=str;
  				submit.open("get",pre,true);
  				submit.onreadystatechange=function(){
				if(submit.readyState==4&&submit.status==200){
					//判断ajax返回结果
					alert(submit.responseText);
					document.location = "AllocateContract";
				}
  				};
  				submit.send();
  			}
      }
    
    
    
    
    </script>
    
</head>
<body>
  <%@ include file="Head.jsp"%>
  <script type="text/javascript">
	document.getElementById("xtgl").setAttribute("class","active");
  </script>
   
  <div class="container-manage">
      <!-- 引用系统管理导航栏    -->
      <%@ include file="SystemManageNav.jsp" %>
      <script type="text/javascript">
		document.getElementById("fpht").setAttribute("class","list-group-item bg active");
      </script>
      
      <div class="panel panel-default col-sm-10 bg"><!--panel start-->
          <div class="panel-heading">
            <h3 class="panel-title">流程配置：<%=contract.GetName() %><input id="id" style="display:none" value="<%=contract.GetId()%>"></h3>
          </div>        
          <div class="panel-body"><!--panel body start-->
          <form class="form-horizontal" role="form" action="" method="post">
            <div class="form-group">
                <div class="form_group  col-sm-10 col-sm-offset-1">
                    <span class="glyphicon glyphicon-record"></span>
                    <span class="glyphicon-class">分配会签人</span>                   
                </div>
                <div class="form-group">
                <script type="text/javascript">
                 function hqleft()
                {
                    var hqleft = document.getElementById("hqleft");
                    var hqright = document.getElementById("hqright");
                    var val = hqleft.options[hqleft.selectedIndex].text;
                    hqleft.remove(hqleft.selectedIndex);
                    hqright.options.add(new Option(val,"value"));
                    hqnames.push(val);
                    //alert(hqnames[0]);
                 }
                function hqright()
               {
                   var hqleft = document.getElementById("hqleft");
                   var hqright = document.getElementById("hqright");
                   var val = hqright.options[hqright.selectedIndex].text;
                   hqright.remove(hqright.selectedIndex);
                   hqleft.options.add(new Option(val,"value"));
                   var arrayindex = hqnames.indexOf(val);
                   if(arrayindex >-1)
    	           hqnames.splice(arrayindex, 1);
                   //alert(hqnames[0]);
                }
              </script>
                    <div class="col-sm-4 col-sm-offset-1">
                        <select multiple class="form-control" id="hqleft">
                        <% for(int i=0;i<hqname.size();i++)
                        {%>      
                        <option><%=hqname.get(i)%></option>
                        <%} 
                        %>
                        </select>                    
                    </div>
                    <div class="col-sm-2 aCenter" align="center">
                        <div class="form-group">
                            <span class="glyphicon glyphicon-arrow-left myclickstyle" onclick="hqright()"></span>                   
                        </div>
                        <div class="form-group">
                            <span class="glyphicon glyphicon-arrow-right myclickstyle" onclick="hqleft()"></span>                       
                        </div>            
                    </div>
                    <div class="col-sm-4" >
                        <select name="hqnames" multiple class="form-control" id="hqright">
                        </select>                    
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="form_group  col-sm-10 col-sm-offset-1">
                    <span class="glyphicon glyphicon-record"></span>
                    <span class="glyphicon-class">分配审批人</span>                   
                </div>
                <div class="form-group">
                <script type="text/javascript">
            function spleft()
            {
               var spleft = document.getElementById("spleft");
               var spright = document.getElementById("spright");
               var val = spleft.options[spleft.selectedIndex].text;
               spleft.remove(spleft.selectedIndex);
               spright.options.add(new Option(val,"value"));
               spnames.push(val);
               //alert(spnames[0]);
               }
               function spright()
               {
               var spleft = document.getElementById("spleft");
               var spright = document.getElementById("spright");
               var val = spright.options[spright.selectedIndex].text;
               spright.remove(spright.selectedIndex);
               spleft.options.add(new Option(val,"value"));
               var arrayindex = spnames.indexOf(val);
               if(arrayindex >-1)
    	           spnames.splice(arrayindex, 1);
               	   //alert(spnames[0]);
               }
             </script>
                    <div class="col-sm-4 col-sm-offset-1">
                        <select multiple class="form-control" id="spleft">
                        <% for(int i=0;i<spname.size();i++)
                        {%>      
                        <option><%=spname.get(i)%></option>
                        <%} 
                        %>
                        </select>                    
                    </div>
                    <div class="col-sm-2 aCenter" align="center">
                        <div class="form-group">
                            <span class="glyphicon glyphicon-arrow-left myclickstyle" onclick="spright()"></span>                       
                        </div>
                        <div class="form-group">
                            <span class="glyphicon glyphicon-arrow-right myclickstyle" onclick="spleft()"></span>                       
                        </div>            
                    </div>
                    <div class="col-sm-4">
                        <select multiple class="form-control" id="spright" >
                        </select>               
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="form_group  col-sm-10 col-sm-offset-1">
                    <span class="glyphicon glyphicon-record"></span>
                    <span class="glyphicon-class">分配签订人</span>                   
                </div>
                <div class="form-group">
                  <script type="text/javascript">
                  function qdleft()
                  {
                   var qdleft = document.getElementById("qdleft");
                   var qdright = document.getElementById("qdright");
                   var val = qdleft.options[qdleft.selectedIndex].text;
                   qdleft.remove(qdleft.selectedIndex);
                   qdright.options.add(new Option(val,"value"));
                   qdnames.push(val);
                   //alert(qdnames[0]);
                  }
                  function qdright()
                  {
                   var qdleft = document.getElementById("qdleft");
                   var qdright = document.getElementById("qdright");
                   var val = qdright.options[qdright.selectedIndex].text;
                   qdright.remove(qdright.selectedIndex);
                   qdleft.options.add(new Option(val,"value"));
                   var arrayindex = qdnames.indexOf(val);
                   if(arrayindex >-1)
                  	 qdnames.splice(arrayindex, 1);
                     //alert(qdnames[0]);
                   
                  }
                  </script>
                    <div class="col-sm-4 col-sm-offset-1">
                        <select multiple class="form-control" id="qdleft"  >
                        <% for(int i=0;i<qdname.size();i++)
                        {%>      
                        <option><%=qdname.get(i)%></option>
                        <%} 
                        %>
                        </select>                    
                    </div>
                    <div class="col-sm-2 aCenter" align="center">
                        <div class="form-group">
                            <span class="glyphicon glyphicon-arrow-left myclickstyle" onclick="qdright()"></span>                      
                        </div>
                        <div class="form-group">
                            <span class="glyphicon glyphicon-arrow-right myclickstyle" onclick="qdleft()"></span>                   
                        </div>            
                    </div>
                    <div class="col-sm-4">
                        <select multiple class="form-control" id="qdright" >
                        </select>              
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 col-sm-offset-4">
                 <button type="button" class="btn btn-info btn-block" id="提交"  onclick="return ajaxsubmit()">确定</button>                    
                </div>
                <div class="col-sm-2">
                  <button onclick="javascript:history.back(-1)" type="button" class="btn btn-info btn-block" id="重置">取消</button>                    
                </div>
            </div>
          </form>
          </div><!--panel body over-->
      </div><!--panel over-->
      
      
      
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