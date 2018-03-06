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
<link rel="stylesheet" href="${webctx}/static/font-awesome/css/font-awesome.css"/>
<link rel="stylesheet" href="${webctx}/static/css/opensans.css"/>
<link rel="stylesheet" href="${webctx}/static/css/matrix-style.css" />
</head>
<style>
body {
	margin-top: 0px;
	padding-left: 20px;
	padding-right: 20px;
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
						<li class="active"><a href="${webctx }/information/comprehensivesInfoList">综合治理</a></li>
						<li class=""><a href="${webctx }/information/communityAffairsList">小区管理</a></li>
						<li class=""><a href="${webctx }/information/list">科室信息</a></li>
						<li class=""><a href="${webctx }/information/statistics">查询统计</a></li>
						<li class=""><a href="${webctx }/volunteer/list">志愿者服务</a></li>
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
			<a href="${webctx}" title="主页" class="tip-bottom">
				<i class="icon-home"></i>主页
			</a> 
			<a href="${webctx }/information/comprehensivesInfoList" >综合治理</a>
			<a href="#" class="current">
				<c:if test="${community eq 'penglang' }">蓬朗社区</c:if>
				<c:if test="${community eq 'pengxii' }">蓬曦社区</c:if>
				<c:if test="${community eq 'pengxin' }">蓬欣社区</c:if>
				<c:if test="${community eq 'pengchen' }">蓬晨社区</c:if>
				<c:if test="${community eq 'pengyuan' }">蓬苑社区</c:if>
				<c:if test="${community eq 'penglai' }">蓬莱社区</c:if>
			</a>
		</div>

		<div class="row-fluid">
			<ul class="activity-list">
				<c:if test="${not empty pageView.records }">
					<c:forEach var="info" items="${pageView.records }">
						<li>
							<a href="${webctx}/information/viewInfoDetail/${info.id }">
								<strong>${info.title }</strong>	
								<span class="pull-right">
								<fmt:formatDate value="${info.refreshTime }"
										pattern="yyyy/MM/dd" />
								</span>	
							</a>
							
						</li>
					</c:forEach>
				</c:if>
			</ul>
		</div>



		<c:set var="pageUrl" value="${webctx }/information/communityComprehensivesInfo?community=${community }"></c:set>
		<!-- 分页 -->
		<jsp:include page="/WEB-INF/page/common/pager.jsp"></jsp:include>
		
		<!--end-main-container-part-->
		<!--Footer-part-->
		<jsp:include page="/WEB-INF/page/common/foot.jsp"></jsp:include>
		<!--end-Footer-part-->
	</div>

	<script src="${webctx}/static/js/jquery.min.js"></script>
	<script src="${webctx}/static/js/jquery.form.min.js"></script>
	<script src="${webctx}/static/js/bootstrap.min.js"></script>
	<script>
		$('.carousel').carousel({
			interval : 2000,
			pause : "hover"
		});
	</script>

</body>

</html>