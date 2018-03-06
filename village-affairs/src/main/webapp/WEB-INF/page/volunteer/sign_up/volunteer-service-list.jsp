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
					class="icon-home"></i>主页</a><a href="${webctx}/volunteer/list">志愿者服务</a> <a href="#" class="current">服务明细</a>
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
								<p class="form-control-static">${volunteer.name }</p>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label">身份证号</label>
							<div class="controls">
								<p class="form-control-static">${volunteer.idCard }</p>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<a href="${webctx }/volunteer/addServicePage/${volunteer.id}" class="btn btn-success">新增服务</a>
				<table class="table table-bordered table-responsive table-striped  table-hover">
					<thead>
						<tr>
							<th>姓名</th>
							<th>身份证号</th>
							<th>服务项目</th>
							<th>服务时间</th>
							<th>服务时长</th>
							<th>服务积分</th>
							<th>确认人</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty pageView.records}">
								<c:forEach var="info" items="${pageView.records }">
									<tr>
										<td>${info.volunteer.name }</td>
										<td>${info.volunteer.idCard }</td>
										<td>${info.serviceName }</td>
										<td><fmt:formatDate value="${info.serviceDate }"
												pattern="yyyy/MM/dd" /></td>
										<td>${info.serviceTime }</td>
										<td>${info.point }</td>
										<td>${info.confirmer }</td>
										<td>
											<a href="${webctx }/volunteer/editService/${info.id}" class="btn btn-success btn-mini" data-toggle="tooltip" title="编辑">
												<i class="icon-edit"> </i>
											</a>
											<a href="###" onclick="removeItem('${info.id}')" class="btn btn-danger btn-mini" data-toggle="tooltip" title="删除">
												<i class="icon-trash"> </i>
											</a>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="8">
										<i class="icon-info-sign"></i>&nbsp;&nbsp;没有查询结果！
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						
					</tbody>
				</table>
				
				
				<!-- 分页 -->
				<c:set var="pageUrl" value="${webctx }/volunteer/serviceList/${volunteer.id }"></c:set>
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
	<script>
		$("#sidebar li").each(function(i, n) {
			$(this).removeClass("active");
		});
		$("#volunteer").addClass("active").parents("li").addClass("open");
		function removeItem(infoId) {
			showConfirm("警告!!", "删除后就不能恢复，确定要删除吗？");
			$("#confirm_btn").click(function() {
				$.post("${webctx}/volunteer/delService/"+infoId, function(data) {
					if (data.success) {
						alert("删除成功！");
						setTimeout(function() {
							window.location.reload();
						}, 3300)

					} else {
						alert("删除失败。原因：" + data.message);
					}
				}, "json")
			})
		}
	</script>
</body>
</html>
