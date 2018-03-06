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
					<c:choose>
						<c:when test="${information.id != null}">
							<a href="#" class="current">编辑信息</a>
						</c:when>
						<c:otherwise>
							<a href="#" class="current">信息发布</a>
						</c:otherwise>
					</c:choose>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<div class="container-fluid">
			<form action="${webctx}/information/saveInformation" method="post"
				class="form-horizontal" id="form">
				<input type="hidden" name="id" value="${information.id }">
				<div class="control-group">
					<label class="control-label" id="titleLabel">标题</label>
					<div class="controls">
						<input type="text" class="span8" placeholder="" name="title"
							value="${information.title }" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">社区</label>
					<div class="controls" >
						<select name="community">
							<shiro:hasRole name="ROLE_ADMIN">
								<option value="street" selected="selected"
									<c:if test="${information.community eq 'street' }">selected="selected"</c:if>
								>街道</option>
							</shiro:hasRole>
							<c:if test="${fn:contains(community, 'penglang') }">
								<option value="penglang" selected="selected"
									<c:if test="${information.community eq 'penglang' }">selected="selected"</c:if>
								>蓬朗社区</option>
							</c:if>
							<c:if test="${fn:contains(community, 'pengxii') }">
							<option value="pengxii"
								<c:if test="${information.community eq 'pengxii' }">selected="selected"</c:if>
							>蓬曦社区</option>
							</c:if>
							<c:if test="${fn:contains(community, 'pengxin') }">
							<option value="pengxin"
								<c:if test="${information.community eq 'pengxin' }">selected="selected"</c:if>
							>蓬欣社区</option>
							</c:if>
							<c:if test="${fn:contains(community, 'pengchen') }">
							<option value="pengchen"
								<c:if test="${information.community eq 'pengchen' }">selected="selected"</c:if>
							>蓬晨社区</option>
							</c:if>
							<c:if test="${fn:contains(community, 'pengyuan') }">
							<option value="pengyuan"
								<c:if test="${information.community eq 'pengyuan' }">selected="selected"</c:if>
							>蓬苑社区</option>
							</c:if>
							<c:if test="${fn:contains(community, 'penglai') }">
							<option value="penglai"
								<c:if test="${information.community eq 'penglai' }">selected="selected"</c:if>
							>蓬莱社区</option>
							</c:if>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">栏目</label>
					<div class="controls" >
						<select name="type" class="select">
							<shiro:hasRole name="ROLE_ADMIN">
								<option value="notice" selected="selected"
									<c:if test="${information.type eq 'notice' }">selected="selected"</c:if>
								>公告</option>
							</shiro:hasRole>
							<option value="communityaffairs" selected="selected"
								<c:if test="${information.type eq 'communityaffairs' }">selected="selected"</c:if>
							>小区管理</option>
							<option value="party" selected="selected"
								<c:if test="${information.type eq 'party' }">selected="selected"</c:if>
							>党建服务</option>
							<option value="civilaffairs"
								<c:if test="${information.type eq 'civilaffairs' }">selected="selected"</c:if>
							>民政民生</option>
							<option value="comprehensives"
								<c:if test="${information.type eq 'comprehensives' }">selected="selected"</c:if>
							>综合治理</option>
							<option value="otheraffairs"
								<c:if test="${information.type eq 'otheraffairs' }">selected="selected"</c:if>
							>其他</option>
						</select>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" id="contentLabel">内容</label>
					<div class="controls">
						<textarea name="content" id="editor1" rows="10" cols="80">
	                	${information.content }
			            </textarea>
					</div>
				</div>
				<div class="control-group">
					<div class="controls col-sm-offset-2">
						<button type="submit" class="btn btn-primary">提交</button>
						<button type="button" class="btn btn-success" onclick="back();">返回</button>
					</div>
					
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
	<script src="${webctx}/static/js/ckeditor/ckeditor.js"></script>
	<script src="${webctx}/static/js/common.js"></script>
	<script type="text/javascript">
		$("#sidebar li").each(function(i, n) {
			$(this).removeClass("active");
		});
		$("#info").addClass("active");
		CKEDITOR.config.toolbarGroups = [
		    { name: 'document',    groups: [ 'mode', 'doctools' ] },
		    { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		    { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		    { name: 'forms' },
		    '/',
		    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		    { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align'] },
		    { name: 'links' },
		    { name: 'insert' },
		    { name: 'styles' },
		    { name: 'colors' },
		    { name: 'tools' }
		];
		var editor = CKEDITOR.replace('editor1', {
			filebrowserLinkUploadUrl : "${webctx}/ckeditor/fileUpload",
			filebrowserImageUploadUrl : "${webctx}/ckeditor/imgUpload",
			filebrowserFlashUploadUrl : "",
			filebrowserFlvplayerUploadUrl : "${webctx}/ckeditor/vedioUpload",
			height : 500
		});

		editor.on("instanceReady", function(evt) {
			editor.addCommand("save", {
				modes : {
					wysiwyg : 1,
					source : 1
				},
				exec : function(editor) {
					$("#form").submit();
				}
			});
		});

		$("#form").validate({
				rules : {
					'title' : {
						required : true,
						strLength : 200
					},
					
					'content' : {
						required : function() {
							var content = CKEDITOR.instances.editor1
									.getData();
							return content.isNotEmpty();
						}
					}
				},
				messages : {
					'title' : {
						required : "请输入项目标题",
						strLength : "标题不能超过100个汉字"
					},
					'content' : {
						required : "请输入项目信息"
					}
				},
				errorClass : "help-inline",
				errorElement : "span",
				highlight : function(element, errorClass,
						validClass) {
					$(element).parents('.control-group').addClass(
							'error');
				},
				unhighlight : function(element, errorClass,
						validClass) {
					$(element).parents('.control-group')
							.removeClass('error');
					$(element).parents('.control-group').addClass(
							'success');
				},
				submitHandler : function(form) {
					var content = CKEDITOR.instances.editor1.getData();
					if(content.isNotEmpty()){
						$("#editor1").val(content);
						$(form).ajaxSubmit({
							success : function(data) {
								if (data.success) {
									alert("信息发布成功！");
									setTimeout(function() {
										to("${webctx}/information/manageNotices");
									}, 3000);
									
								} else {
									alert("信息发布失败。原因：" + data.message);
								}
							}
						})
					}
				}
			});
	</script>