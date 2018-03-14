<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head></head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员注册</title>
		
		<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
		<script src="${ pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${ pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css" type="text/css"/>

<style>
  body{
   margin-top:20px;
   margin:0 auto;
 }
 .carousel-inner .item img{
	 width:100%;
	 height:300px;
 }
 .container .row div{ 
	 /* position:relative;
	 float:left; */
 }
 
font .regist {
    color: #3164af;
    font-size: 18px;
    font-weight: normal;
    padding: 0 10px;
}
 </style>
 <script type="text/javascript">
 	$(function(){
 		$("#username").blur(function(){
 			var $value = $(this).val();
 			if($value != ""){
 				$.post("${ pageContext.request.contextPath }/UserServlet",{"method":"checkUsername","username":$value},function(data){
 	 				if(data == 1){
 	 					$("#s1").html("<font color='green'>用户名可以使用</font>");
 	 					$("#regBut").attr("disabled",false);
 	 				}else if(data == 2){
 	 					$("#s1").html("<font color='red'>用户名已经被占用</font>");
 	 					$("#regBut").attr("disabled",true);
 	 				}
 	 			});
 			}
 		});
 		
 	});

	$(function(){
		$("#img1").click(function(){
			$(this).prop("src","${ pageContext.request.contextPath }/CheckImgServlet?time="+new Date().getTime());
		});
	});
</script>
</head>
<body>


<%@ include file="menu.jsp" %>

<div class="container" style="width:100%;background:url('${ pageContext.request.contextPath }/image/regist_bg.jpg');">
<div class="row"> 

	<div class="col-md-2"></div>
	
	<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
		<font class="regist">会员注册</font>USER REGISTER<br>
		<font color="red" size="12">${ msg }</font>
		<form class="form-horizontal" style="margin-top:5px;" action="${ pageContext.request.contextPath }/UserServlet" method="post">
			 <input type="hidden" name="method" value="regist">
			 <div class="form-group">
			    <label for="username" class="col-sm-2 control-label">用户名</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
			    </div>
			    <span id="s1"></span>
			  </div>
			   <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="inputPassword3" name="password"  placeholder="请输入密码">
			    </div>
			  </div>
			   <div class="form-group">
			    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="confirmpwd" name="repassword"  placeholder="请输入确认密码">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-6">
			      <input type="email" class="form-control" id="inputEmail3" name="email"  placeholder="Email">
			    </div>
			  </div>
			 <div class="form-group">
			    <label for="usercaption" class="col-sm-2 control-label">姓名</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" id="usercaption" name="name"  placeholder="请输入姓名">
			    </div>
			  </div>
			  <div class="form-group opt">  
			  <label for="inlineRadio1" class="col-sm-2 control-label">性别</label>  
			  <div class="col-sm-6">
			    <label class="radio-inline">
			  <input type="radio" name="sex" id="inlineRadio1" value="男" checked> 男
			</label>
			<label class="radio-inline">
			  <input type="radio" name="sex" id="inlineRadio2" value="女"> 女
			</label>
			</div>
			  </div>	
			 <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">电话</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control"  name="telephone" >		      
			    </div>
			  </div>	
			  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">出生日期</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control"  name="birthday" >		      
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">验证码</label>
			    <div class="col-sm-3">
			    
			     <input type="text" class="form-control" id="inputPassword3" name="code" placeholder="请输入验证码">
			      
			    </div>
			    <div class="col-sm-2">
			     <img id="img1" src="${pageContext.request.contextPath}/CheckImgServlet" />
			    </div>
			    
			  </div>
			 
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <input type="submit"  id="regBut" width="100" value="注册" border="0"
				    style="background: url('${ pageContext.request.contextPath }/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">
			    </div>
			  </div>
			</form>
	</div>
	
	<div class="col-md-2"></div>
  
</div>
</div>

	  
	
	<div style="margin-top:50px;">
			<img src="${ pageContext.request.contextPath }/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>

</body></html>




