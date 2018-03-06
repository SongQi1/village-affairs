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
<link rel="stylesheet" href="${webctx}/static/css/uniform.css" />
<link rel="stylesheet" href="${webctx}/static/css/select2.css" />
<link rel="stylesheet" href="${webctx}/static/css/matrix-style.css" />
<link rel="stylesheet" href="${webctx}/static/font-awesome/css/font-awesome.css"  />
<link href='${webctx}/static/css/opensans.css' rel='stylesheet' type='text/css'>


</head>
<style>
body {
	margin-top: 0px;
	padding-left: 20px;
	padding-right: 20px;
}
.widget-content{
		height: auto;
	}
@media ( min-width : 979px) {
	.content {
		position: relative;
		padding-top: 50px;
	}
}
</style>

<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar collapsed"
					data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="#">蓬朗街道社区绩效监察平台</a>
				<div class="nav-collapse collapse" style="height: 0px;">
					<ul class="nav">
						<li class=""><a href="${webctx }">首页</a></li>
						<li class=""><a href="${webctx }/information/partyInfoList">党建服务</a></li>
						<li class=""><a href="${webctx }/information/civilAffairsList">民政民生</a></li>
						<li class=""><a href="${webctx }/information/comprehensivesInfoList">综合治理</a></li>
						<li class=""><a href="${webctx }/information/communityAffairsList">小区管理</a></li>
						<li class=""><a href="${webctx }/information/list">科室信息</a></li>
						<li class=""><a href="${webctx }/information/statistics">查询统计</a></li>
						<li class="active"><a href="${webctx }/volunteer/list">志愿者服务</a></li>
						<li class=""><a href="${webctx }/information/otherAffairsList">其他</a></li>
					</ul>
					<div class="pull-right">
						<shiro:authenticated>
							<ul  class="nav">
								<li><a href="${webctx }/sysUser/logout"><i class="icon-user"></i>&nbsp;安全退出</a></li>
							</ul>
						</shiro:authenticated>
						<shiro:notAuthenticated>
							<a href="${webctx }/login.jsp" class="btn btn-success">登录</a>
							<%-- <a href="${webctx }/sysUser/registerPage" class="btn btn-info">注册</a> --%>
						</shiro:notAuthenticated>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container content">
		<!-- 图片轮播 -->
		<jsp:include page="/WEB-INF/page/common/banner.jsp"></jsp:include>
		<div id="breadcrumb">
			<a href="${webctx}" title="主页" class="tip-bottom"><i
				class="icon-home"></i>主页</a> <a href="#" class="current">志愿者服务</a>
		</div>
		<!--End-breadcrumbs-->	
		<div class="container-fluid">
			<form action="${webctx }/volunteer/list" method="post" id="form" role="form">
				<p class="form-inline text-center form-search">
					<input type="text" name="idCard" placeholder="身份证号"  value="${volunteer.idCard }">
					<button type="submit" class="btn btn-primary"><i class="icon-search"></i>&nbsp;查询</button>
				</p>
				<hr>
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
							<th>服务明细</th>
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
										<td><hideId:hideIdCardNo end="4" idCard="${info.idCard }" front="10"></hideId:hideIdCardNo></td>
										<td><hideId:hideIdCardNo end="4" idCard="${info.phoneNo }" front="3"></hideId:hideIdCardNo></td>
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
											<a href="${webctx }/volunteer/serviceList/${info.id}" class="btn btn-warning btn-mini">
												<i class="icon-list-alt"> </i>
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
		<!--end-main-container-part-->
		
		<!--Footer-part-->
		<jsp:include page="/WEB-INF/page/common/foot.jsp"></jsp:include>
		<!--end-Footer-part-->
	</div>

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
	<script src="${webctx}/static/js/echarts.min.js"></script>
	<script src="${webctx}/static/js/messagebox/messagebox.js"></script>
	<script src="${webctx}/static/js/common.js"></script>
	
	<script>
	
	</script>

</body>

</html>