<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>
<div id="banner" class="carousel slide">
	<ol class="carousel-indicators">
		<li data-target="#banner" data-slide-to="0" class="active"></li>
		<li data-target="#banner" data-slide-to="1"></li>
		<li data-target="#banner" data-slide-to="2"></li>
		<li data-target="#banner" data-slide-to="3"></li>
		<li data-target="#banner" data-slide-to="4"></li>
		<li data-target="#banner" data-slide-to="5"></li>
	</ol>
	<div class="carousel-inner">
		<div class="item active">
			<img src="${webctx}/static/img/gallery/pengchen.jpg" alt="" />
		</div>
		<div class="item">
			<img src="${webctx}/static/img/gallery/penglai.jpg" alt="" />
		</div>
		<div class="item">
			<img src="${webctx}/static/img/gallery/penglang.jpg" alt="" />
		</div>
		<div class="item">
			<img src="${webctx}/static/img/gallery/pengxin.jpg" alt="" />
		</div>
		<div class="item">
			<img src="${webctx}/static/img/gallery/pengyuan.jpg" alt="" />
		</div>
		<div class="item">
			<img src="${webctx}/static/img/gallery/pengxii.jpg" alt="" />
		</div>
	</div>
	<a class="left carousel-control" href="#banner" data-slide="prev">&lsaquo;</a>
	<a class="right carousel-control" href="#banner" data-slide="next">&rsaquo;</a>
</div>