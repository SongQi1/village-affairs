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
					class="icon-home"></i>主页</a> <a href="#" class="current">账号管理</a>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<div class="container-fluid">
			<form action="" method="post" id="form" class="form-horizontal" role="form">
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label">姓名</label>
							<div class="controls">
								<input class="span11" type="text" name="">
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label">用户名</label>
							<div class="controls">
								<input class="span11" type="text" name="">
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label">电话</label>
							<div class="controls">
								<input class="span11" type="text" name="">
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label">邮件</label>
							<div class="controls">
								<input class="span11" type="text" name="">
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid text-center">
					<button type="submit" class="btn btn-primary btn-large"><i class="icon-search"></i>&nbsp;查询</button>
				</div>
				<hr/>
				<a href="${webctx }/sysUser/addUserPage" class="btn btn-success">新增</a>
				<table class="table table-bordered table-responsive table-striped  table-hover with-check">
					<thead>
						<tr>
							<th><input type="checkbox" id="title-table-checkbox"
								name="title-table-checkbox" /></th>
							<th>用户名</th>
							<th>姓名</th>
							<th>电话</th>
							<th>邮件</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sysUser" items="${pageView.records }">
							<tr>
								<td><input type="checkbox" /></td>
								<td>${sysUser.userName }</td>
								<td>${sysUser.chineseName }</td>
								<td>${sysUser.mobileNo }</td>
								<td>${sysUser.email }</td>
								<td>
									<a href="${webctx }/sysUser/view/${sysUser.id}" class="btn btn-info btn-mini" data-toggle="tooltip" title="查看用户信息">
										<i class="icon-eye-open"> </i>
									</a> 
									<a href="${webctx }/sysUser/edit/${sysUser.id}" class="btn btn-success btn-mini" data-toggle="tooltip" title="编辑用户信息">
										<i class="icon-edit"> </i>
									</a>
									<a href="###" onclick="initpassword('${sysUser.id}')" class="btn btn-primary btn-mini" data-toggle="tooltip" title="重置密码">
										<i class="icon-refresh"> </i>
									</a>
									<a href="###" onclick="del('${sysUser.id}')" class="btn btn-danger btn-mini" data-toggle="tooltip" title="删除">
										<i class="icon-trash"> </i>
									</a>
									<c:if test="${sysUser.userStatus.status eq 'NORMAL' }">
									<a href="###" onclick="lock('${sysUser.id}')" class="btn btn-warning btn-mini" data-toggle="tooltip" title="锁定">
										<i class="icon-lock"> </i>
									</a>
									</c:if>
									<c:if test="${sysUser.userStatus.status eq 'LOCKED' }">
									<a href="###" onclick="unlock('${sysUser.id}')" class="btn btn-danger btn-mini" data-toggle="tooltip" title="解锁">
										<i class="icon-unlock"> </i>
									</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				
				<!-- 分页 -->
				<c:set var="pageUrl" value="${webctx }/sysUser/getUsers"></c:set>
				<jsp:include page="/WEB-INF/page/common/pager.jsp"></jsp:include>
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
	<script type="text/javascript">
		$("#sidebar li").each(function(i, n) {
			$(this).removeClass("active");
		});
		$("#usermng").addClass("active");
		
		function lock(id){
			$.post("${webctx}/sysUser/lock",{"id":id},function(data){
				if(data.success){
					alert("操作成功！");
					setTimeout(function(){
						window.location.reload();
					},3000);
				}else{
					alert("操作失败。原因："+data.message);
				}
			},"json");
		}
		
		function unlock(id){
			$.post("${webctx}/sysUser/unlock",{"id":id},function(data){
				if(data.success){
					alert("操作成功！");
					setTimeout(function(){
						window.location.reload();
					},3000);
				}else{
					alert("操作失败。原因："+data.message);
				}
			},"json");
		}
		function initpassword(id){
			$.post("${webctx}/sysUser/initPassword",{"id":id},function(data){
				if(data.success){
					alert("操作成功！");
					setTimeout(function(){
						window.location.reload();
					},3000);
				}else{
					alert("操作失败。原因："+data.message);
				}
			},"json");
		}
		
		function del(id){
			showConfirm("警告!!", "删除后就不能恢复，确定要删除吗？");
			$("#confirm_btn").click(function() {
				$.post("${webctx}/sysUser/delete/"+id,function(data){
				if(data.success){
					alert("删除成功！");
					setTimeout(function(){
						window.location.reload();
					},3000);
				}else{
					alert("操作失败。原因："+data.message);
				}
			},"json");
			})
		}
	</script>