<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">

<head>
<title>蓬朗街道社区绩效监察平台--登录</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${webctx}/static/css/bootstrap.min.css" />
<link rel="stylesheet" href="${webctx}/static/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${webctx}/static/font-awesome/css/font-awesome.css"/>
<link rel="stylesheet" href="${webctx}/static/css/matrix-login.css" />
<style type="text/css">

	body{
		
	}
</style>
</head>
<body>
	<div id="loginbox">
		<div class="control-group normal_text">
			<h3>蓬朗街道社区绩效监察平台</h3>
		</div>
		<form id="loginform" class="form-vertical" action="${webctx}/sysUser/login ">
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_lg"><i class="icon-user"></i></span><input
							type="text" placeholder="用户名" name="userName" id="username"
							data-toggle="tooltip" title="请输入用户名" data-placement="top" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly"><i class="icon-lock"></i></span><input
							type="password" placeholder="密码" name="password" id="password"
							data-toggle="tooltip" title="请输入密码" data-placement="top" />
					</div>
				</div>
			</div>
			<div class="form-actions">
				<span class="pull-left"><a href="#"
					class="flip-link btn btn-info" id="to-recover">忘记密码？</a></span> <span
					class="pull-right"><a type="submit" href="##"
					class="btn btn-success" onclick="login()"> 登录</a></span>
			</div>
		</form>
		<form id="recoverform" action="#" class="form-vertical">
			<p class="normal_text">输入你的邮件地址</p>
			<div class="controls">
				<div class="main_input_box">
					<span class="add-on bg_lo"><i class="icon-envelope"></i></span><input
						type="text" placeholder="邮件地址" />
				</div>
			</div>

			<div class="form-actions">
				<span class="pull-left"><a href="#"
					class="flip-link btn btn-success" id="to-login">&laquo; 返回</a></span> <span
					class="pull-right"><a class="btn btn-info">找回</a></span>
			</div>
		</form>

		<jsp:include page="/WEB-INF/page/common/modal_dailog.jsp"></jsp:include>
	</div>

	<script src="${webctx}/static/js/jquery.min.js"></script>
	<script src="${webctx}/static/js/jquery.validate.js"></script>
	<script src="${webctx}/static/js/jquery.uniform.js"></script>
	<script src="${webctx}/static/js/jquery.form.min.js"></script>
	<script src="${webctx}/static/js/bootstrap.min.js"></script>
	<script src="${webctx}/static/js/matrix.login.js"></script>
	<script src="${webctx}/static/js/messagebox/messagebox.js"></script>
	<script src="${webctx}/static/js/select2.min.js"></script>
	<script src="${webctx}/static/js/matrix.form_common.js"></script>
	<script src="${webctx}/static/js/common.js"></script>
	<script src="${webctx}/static/js/sha256.js"></script>

	<script type="text/javascript">
		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			var keyCode = e.keyCode ? e.keyCode : e.charCode ? e.charCode : 0;
			if (keyCode == 27) { // 按 Esc 
				//要做的事情
			}
			if (keyCode == 113) { // 按 F2 
				//要做的事情
			}
			if (keyCode == 13) { // enter 键
				//要做的事情
				login();
			}
		};

		function login() {
			var username = $("#username").val();
			if (username.isEmpty()) {
				$("#username").tooltip("show");
				return;
			}
			var password = $("#password").val();
			if (password.isEmpty()) {
				$("#password").tooltip("show");
				return;
			}
			$("#password").val(sha256_digest(password));
			$("#loginform").ajaxSubmit({
				success : function(data) {
					if (data.success) {
						if(data.message != null){
							to(data.message);
						}else{
							to("${webctx}/home");
						}
					} else {
						alert(data.message);
					}
				}
			});
		}
	</script>
</body>

</html>
