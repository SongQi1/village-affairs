<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<title>蓬朗街道社区绩效监察平台</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${webctx}/static/css/bootstrap.min.css" />
<link rel="stylesheet" href="${webctx}/static/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${webctx}/static/css/datepicker.css" />
<link rel="stylesheet" href="${webctx}/static/css/uniform.css" />
<link rel="stylesheet" href="${webctx}/static/css/select2.css" />
<link rel="stylesheet" href="${webctx}/static/css/matrix-style.css" />
<link rel="stylesheet" href="${webctx}/static/css/matrix-media.css" />
<link rel="stylesheet" href="${webctx}/static/font-awesome/css/font-awesome.css"/>
<link rel='stylesheet' href='${webctx}/static/css/opensans.css'/>
</head>
<body>

	<!--Header-part-->
	<div id="header">
		<h1>
			<a href="dashboard.html">Matrix Admin</a>
		</h1>
	</div>
	<!--close-Header-part-->


	<!--top-Header-menu-->
	<jsp:include page="/WEB-INF/page/common/top_header_menu.jsp"></jsp:include>
	<!--close-top-Header-menu-->


	<!--sidebar-menu-->
	<jsp:include page="/WEB-INF/page/common/sidebar_menu.jsp"></jsp:include>
	<!--sidebar-menu-->

	<!--main-container-part-->
	<div id="content">

		<!--breadcrumbs-->
		<div id="content-header">
			<div id="breadcrumb">
				<a href="${webctx}/home" title="主页" class="tip-bottom"><i
					class="icon-home"></i>主页</a><a href="${webctx}/sysUser/getUsers">账号管理</a> <a href="#" class="current">编辑用户</a>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<div class="container-fluid">
			<form action="${webctx}/sysUser/updateProfile" method="post" id="form" class="form-horizontal">
				<jsp:include page="./user-info.jsp"></jsp:include>
				<div class="form-actions">
					<button type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-success" onclick="back();">返回</button>
				</div>
			</form>

			<!-- modal-dailog -->
			<jsp:include page="/WEB-INF/page/common/modal_dailog.jsp"></jsp:include>
			<!-- end-modal-dailog -->
		</div>
	</div>
	<!--end-main-container-part-->

	<!--Footer-part-->
	<jsp:include page="/WEB-INF/page/common/foot.jsp"></jsp:include>
	<!--end-Footer-part-->


	<script src="${webctx}/static/js/jquery.min.js"></script>
	<script src="${webctx}/static/js/jquery.form.min.js"></script>
	<script src="${webctx}/static/js/jquery.validate.js"></script>
	<script src="${webctx}/static/js/jquery.ui.custom.js"></script>
	<script src="${webctx}/static/js/bootstrap.min.js"></script>
	<script src="${webctx}/static/js/masked.js"></script>
	<script src="${webctx}/static/js/jquery.uniform.js"></script>
	<script src="${webctx}/static/js/select2.min.js"></script>
	<script src="${webctx}/static/js/matrix.js"></script>
	<script src="${webctx}/static/js/matrix.form_common.js"></script>
	<script src="${webctx}/static/js/messagebox/messagebox.js"></script>
	<script src="${webctx}/static/js/common.js"></script>
	<script>
		$("#form").validate({
			rules : {
				'chineseName' : {
					required : true,
					strLength:10
				},
				'mobileNo' : {
					number : true,
					minlength:11,
					maxlength:11
				},
				'email':{
					email:true
				},
				'qq':{
					number : true,
					maxlength:15
				}

			},
			messages : {
				'chineseName' : {
					required : "姓名必填",
					maxLength: "姓名不能超过5汉字"
				},
				'mobileNo' : {
					number : "手机号码不正确",
					minlength:"手机号码不正确",
					maxlength:"手机号码不正确"
				},
				'email':{
					email:"email不正确"
				},
				'qq':{
					number : "QQ不正确",
					maxlength:"QQ不正确"
				}

			},
			errorClass : "help-inline",
			errorElement : "span",
			highlight : function(element, errorClass, validClass) {
				$(element).parents('.control-group').addClass('error');
			},
			unhighlight : function(element, errorClass, validClass) {
				$(element).parents('.control-group').removeClass('error');
			},
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function(data) {
						if (data.success) {
							alert("修改成功");
							setTimeout(function() {
								window.location.reload();
							}, 3000);

						} else {
							alert("修改失败。原因：" + data.message);
						}
					}
				})
			}
		});
	</script>
</body>
</html>
