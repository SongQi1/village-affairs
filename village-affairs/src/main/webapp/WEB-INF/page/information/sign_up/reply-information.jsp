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
					class="icon-home"></i>主页</a>
				<a href="${webctx }/information/partyInfos">信息管理</a>
				<a href="###" class="current">回复信息</a>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<div class="container-fluid">
			<input type="hidden" id="id" value="${information.id }">
			<div class="row-fluid">
				<h3 class="text-center text-primary">${information.title }</h3>
			</div>
			<div class="row-fluid">
				<div class="span12 text-center">
					<span>发布人：${information.author }</span> <span class="offset1">时间：<fmt:formatDate
							value="${information.refreshTime }" pattern="yyyy/MM/dd HH:mm:ss" /></span>

				</div>
			</div>
			<div class="row-fluid">
				<div class="span12">${information.content }</div>
			</div>
			
			<c:if test="${not empty information.replyInfoList }">
				<c:forEach var="replyInfo" items="${ information.replyInfoList}">
					<div class="alert">
						<h4 class="alert-heading">${ replyInfo.author} 于 <fmt:formatDate value="${replyInfo.replyTime }" pattern="yyyy/MM/dd HH:mm:ss"/> 回复:</h4>
              			${replyInfo.content }
              		</div>
				</c:forEach>
			</c:if>
			
			<hr>
			<div class="row-fluid">
				<div class="span12">
					<textarea name="editor1" id="editor1" rows="10" cols="80" placeholder="">
               		
            		</textarea>
		           
				</div>
			</div>
			

			<h1></h1>
			<div class="text-center">
				<button class="btn btn-primary btn-large" onclick="reply();">回复</button>
				<button class="btn btn-success btn-large" onclick="back();">返回</button>
			</div>



			<!-- modal-dailog -->
			<jsp:include page="/WEB-INF/page/common/modal_dailog.jsp"></jsp:include>
			<!-- end-modal-dailog -->

			<!-- 进度模态框 -->
			<div id="scoreModal" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button">×</button>
					<h3>进度</h3>
				</div>
				<div class="modal-body">
					<div>
						<label class="control-lable">完成进度</label> <input type="text"
							id="percent" value="" placeholder="<fmt:formatNumber value='${information.completePercent}' pattern='0.00'/>%">
					</div>
				</div>
				<div class="modal-footer">
					<a data-dismiss="modal" class="btn btn-primary" href="#"
						id="score_confirm_btn">确定</a> <a data-dismiss="modal" class="btn"
						href="#">取消</a>
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
	<script src="${webctx}/static/js/ckeditor/ckeditor.js"></script>
	<script src="${webctx}/static/js/common.js"></script>
	<script>
	
		$("#sidebar li").each(function(i, n) {
			$(this).removeClass("active");
		});
		$("#noticemng").addClass("active");
		
		CKEDITOR.config.toolbarGroups=[
		       { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		    { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align'] },
		    { name: 'links' },
		    { name: 'styles' },
		    { name: 'insert' },
		    { name: 'colors' }
		];
		var editor = CKEDITOR.replace('editor1', {
			filebrowserLinkUploadUrl : "${webctx}/ckeditor/fileUpload",
			filebrowserImageUploadUrl : "${webctx}/ckeditor/imgUpload",
			filebrowserFlvplayerUploadUrl : "${webctx}/ckeditor/vedioUpload"
		});
		
		function reply(){
			$("#scoreModal").modal({
				backdrop : "static",
				show : true
			});	
			
			$("#score_confirm_btn").click(function() {
				var content = CKEDITOR.instances.editor1.getData();
				var percent = $("#percent").val();
				$.post("${webctx}/information/reply",
					{"id":$("#id").val(),"content":content,"completePercent": percent}, function(data) {
					if(data.success){
						window.location.reload();
					}else{
						alert(data.message);
					}
				}, "json");
			});
		
		
		/* 	var content = CKEDITOR.instances.editor1.getData();
			$.post("${webctx}/information/reply",
				{"id":$("#id").val(),"content":content},
				function(data){
					if(data.success){
						window.location.reload();
					}
				},"json");	 */
		}
	</script>
</body>
</html>
