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
<link rel="stylesheet" href="${webctx}/static/font-awesome/css/font-awesome.css"  />
<link rel='stylesheet' href='${webctx}/static/css/opensans.css' />
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
					class="icon-home"></i>主页</a> <a href="${webctx }/volunteer/list">志愿者服务</a><a href="#" class="current">编辑志愿者</a>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<div class="container-fluid">
			<form action="${webctx}/volunteer/updateVolunteer" method="post" id="form" class="form-horizontal">
				<jsp:include page="./volunteer-info.jsp"></jsp:include>
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
		$("#sidebar li").each(function(i, n) {
			$(this).removeClass("active");
		});
		$("#volunteer").addClass("active").parents("li").addClass("open");
		$("#form").validate({
			rules : {
				'no' : {
					required : true,
					remote : {
						url : "${webctx}/volunteer/checkNo",
						data : {
							"no" : function() {
								return $("#no").val();
							},
							"id": function(){
								return $("#id").val();
							}
						}
					}
				},
				'name' : {
					required : true
				},
				'idCard' : {
					required : true,
					remote : {
						url : "${webctx}/volunteer/checkIdCard",
						data : {
							"idCard" : function() {
								return $("#idCard").val();
							},
							"id": function(){
								return $("#id").val();
							}
						}
					}
				},
				'phoneNo' : {
					required : true
				}
			},
			messages : {
				'no':{
					required:"请输入志愿者编号",
					remote:"志愿者编号已存在"
				},
				'name' : {
					required : "姓名必填"
				},
				'idCard' : {
					required : "身份证号必填",
					remote:"身份证号已存在"
				},
				'phoneNo' : {
					required : "手机号必填",
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
				$("#form").ajaxSubmit({
					success : function(data) {
						if (data.success) {
							alert("新增成功！");
							setTimeout(function() {
								window.location.href = "${webctx}/volunteer/list";
							}, 3000);
						} else {
							alert("新增失败。原因：" + data.message);
						}
					}
				})
			}
		});
	</script>
</body>
</html>
