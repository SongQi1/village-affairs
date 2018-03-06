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
<link rel="stylesheet" href="${webctx}/static/css/matrix-media.css" />
<link rel="stylesheet" href="${webctx}/static/font-awesome/css/font-awesome.css"/>
<link rel='stylesheet' href='${webctx}/static/css/opensans.css'/>
	
	<style type="text/css">
	.todo li{
		padding:  0px;
	}
	.message-unread{
		font-weight: bold;
	}
</style>
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
					class="icon-home"></i>主页</a> <a href="#" class="current">收件箱</a>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<div class="container-fluid">


			<div class="widget-content">
				<div class="todo">
					<ul>
						<li class="clearfix message-unread">
							<div class="row-fluid">
								<div class="span2">
									<input type="checkbox">
								发信人
								</div>
								<div class="span10">
									<div class="txt">
										<a href="###">主题 </a>
									</div>
									<div class="pull-right">
										发送时间
									</div>
								</div>
							</div>
						</li>
						<c:if test="${not empty pageView.records }">
						
							<c:forEach var="msg" items="${pageView.records }">
								<li class="clearfix <c:if test="${msg.read == false}">message-unread</c:if>">
									<div class="row-fluid">
										<div class="span2">
											<input type="checkbox">
										${msg.sendUser.chineseName }
										</div>
										<div class="span10">
											<div class="txt">
												<a href="###" onclick="showMsg('${msg.id}')">${msg.title } </a>
											</div>
											<div class="pull-right">
												<fmt:formatDate value="${msg.sendTime }" pattern="yyyy/MM/dd HH:mm"/>
											</div>
										</div>
									</div>
								</li>
							</c:forEach>
						</c:if>
					</ul>
				</div>
				
				<c:set var="pageUrl" value="${webctx }/message/inbox"></c:set>
				<!-- 分页 -->
				<jsp:include page="/WEB-INF/page/common/pager.jsp"></jsp:include>
			</div>

			<!-- modal-dailog -->
			<jsp:include page="/WEB-INF/page/common/modal_dailog.jsp"></jsp:include>
			<!-- end-modal-dailog -->
			
			<div id="msg" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button">×</button>
					<h3 id="msgtitle"></h3>
				</div>
				<div class="modal-body">
					<p id="msgContent">
					</p>
				</div>
				<div class="modal-footer">
					<a data-dismiss="modal" class="btn btn-primary" href="#"
						id="confirm_btn">
						<i class="icon  icon-ok"></i>
						确定</a> <a data-dismiss="modal" id="delete_btn" class="btn btn-danger" href="#"><i class="icon icon-trash"></i>  删除</a>
				</div>
			</div>
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
	 function showMsg(id){
		 $.post("${webctx}/message/getMessgeContent",{"id":id},function(data){
			 if(data.success){
				var msg = data.data;
				$("#msgtitle").html(msg.title);
				$("#msgContent").html(msg.content);
				$("#msg").modal({
						backdrop:"static",
						show:true
				});
			 }
		 },"json");
		 
		 
		 $("#delete_btn").click(function(){
		 	$.post("${webctx}/message/delete",{"id":id},function(data){
		 		if(data.success){
		 			alert("删除成功");
		 			setTimeout(function(){
		 				window.location.reload();
		 			},3000);
		 		}else{
		 			alert("删除失败。原因："+ data.message);
		 		}
		 	},"json");
		 });
	 }
	</script>
</body>
</html>
