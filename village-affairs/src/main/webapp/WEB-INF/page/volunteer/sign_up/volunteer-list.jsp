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
					class="icon-home"></i>主页</a> <a href="#" class="current">志愿者服务</a>
			</div>
		</div>
		<!--End-breadcrumbs-->		
		<div class="container-fluid">
			<form action="${webctx }/volunteer/list" method="post" id="form" class="form-horizontal" role="form">
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label">姓名</label>
							<div class="controls">
								<input class="span11" type="text" name="name" value=${volunteer.name }>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label">身份证号</label>
							<div class="controls">
								<input class="span11" type="text" name="idCard" value="${volunteer.idCard }">
							</div>
						</div>
					</div>
				</div>
				
			
				<div class="row-fluid text-center">
					<button type="submit" class="btn btn-primary btn-large"><i class="icon-search"></i>&nbsp;查询</button>
				</div>
				<hr>
				<a href="${webctx }/volunteer/addVolunteerPage" class="btn btn-success">新增志愿者</a>
				<table class="table table-bordered table-responsive table-striped  table-hover">
					<thead>
						<tr>
							<th>编号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>身份证号</th>
							<th>手机号</th>
							<th>所在社区</th>
							<th>总积分</th>
							<th>总服务时长</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty pageView.records}">
								<c:forEach var="info" items="${pageView.records }">
									<tr>
										<td>${info.no }</td>
										<td>${info.name }</td>
										<td>${info.sex }</td>
										<td>${info.idCard }</td>
										<td>${info.phoneNo }</td>
										<td>
											<c:if test="${info.community eq 'street' }">街道</c:if>
						                  	<c:if test="${info.community eq 'penglang' }">蓬朗</c:if>
						                  	<c:if test="${info.community eq 'pengxii' }">蓬曦</c:if>
						                  	<c:if test="${info.community eq 'pengxin' }">蓬欣</c:if>
						                  	<c:if test="${info.community eq 'pengchen' }">蓬晨</c:if>
						                  	<c:if test="${info.community eq 'pengyuan' }">蓬苑</c:if>
						                  	<c:if test="${info.community eq 'penglai' }">蓬莱</c:if>
										</td>
										<td>${info.totalPoint}</td>
										<td>${info.totalServiceTime }</td>
										<td>
											<a href="${webctx }/volunteer/edit/${info.id}" class="btn btn-success btn-mini" data-toggle="tooltip" title="编辑">
												<i class="icon-edit"> </i>
											</a>
											<a href="${webctx }/volunteer/serviceList/${info.id}" class="btn btn-warning btn-mini" data-toggle="tooltip" title="服务明细">
												<i class="icon-list-alt"> </i>
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
									<td colspan="9">
										<i class="icon-info-sign"></i>&nbsp;&nbsp;没有查询结果！
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						
					</tbody>
				</table>
				
				
				<!-- 分页 -->
				<c:set var="pageUrl" value="${webctx }/volunteer/list"></c:set>
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
		$("#volunteer").addClass("active");
		function removeItem(infoId) {
			showConfirm("警告!!", "删除后就不能恢复，确定要删除吗？");
			$("#confirm_btn").click(function() {
				$.post("${webctx}/volunteer/delete/"+ infoId,  function(data) {
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
