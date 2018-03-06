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
<link rel='stylesheet' href='${webctx}/static/js/webuploader/webuploader.css' />
<style type="text/css">
	.webuploader-pick{
		display: block;
		padding: 0;
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
					class="icon-home"></i>主页</a> 
					<a href="${webctx }/volunteer/list">志愿者服务</a>
					<a href="${webctx }/volunteer/serviceList/${volunteer.id}">服务明细</a>
					<a href="#" class="current">修改服务</a>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<div class="container-fluid">
			<form action="${webctx}/volunteer/updateService" method="post" id="form" class="form-horizontal">
				<jsp:include page="./service-info.jsp"></jsp:include>
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
	<script src="${webctx}/static/js/bootstrap-datepicker.js"></script>
	<script src="${webctx}/static/js/masked.js"></script>
	<script src="${webctx}/static/js/jquery.uniform.js"></script>
	<script src="${webctx}/static/js/select2.min.js"></script>
	<script src="${webctx}/static/js/matrix.js"></script>
	<script src="${webctx}/static/js/matrix.form_common.js"></script>
	<script src="${webctx}/static/js/messagebox/messagebox.js"></script>
	<script src="${webctx}/static/js/webuploader/webuploader.min.js"></script>
	<script src="${webctx}/static/js/common.js"></script>
	<script>
		$("document").ready(function(){
			$("#sidebar li").each(function(i, n) {
				$(this).removeClass("active");
			});
			$("#volunteer").addClass("active");
			
			$(".datepicker").datepicker({
				format:"yyyy/mm/dd"
			});
			
			var uploader = WebUploader.create({
				auto: false,// 选完文件后，是否自动上传 
			    // swf文件路径
			    swf: '${webctx}/static/js/webuploader/Uploader.swf',
			    // 文件接收服务端。
			    server: '${webctx}/pictures/uploadPic',
			    // 选择文件的按钮。可选。
			    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
			    pick: '#picker',
			    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			    resize: false,
			    accept: {
			        title: 'images',
			        extensions: 'gif,jpg,jpeg,bmp,png',
			        mimeTypes: 'image/*'
			    },
			    method: 'POST'
			});
			uploader.on('fileQueued', function(file) {
			    //将上传的图片放到上传列表中
			  	var $preview = $('<li id="'+file.id+'" class="village_photo_file"></li>');
			    $('#imgList').append($preview);
			    //生成缩略图
			    uploader.makeThumb(file, function( error, src) {
			        $('#'+file.id).css('background-image','url('+src+')');
			    }, 1000, 1000 );
			});
			
			var	$gallery = $("#gallery"),
            $galleryImg = $("#galleryImg"),
            $imgList = $("#imgList");

			//单击图片预览
	        $imgList.on("click", "li", function() {
	            $galleryImg.attr("style", this.getAttribute("style"));
	            $gallery.fadeIn(100);
	        });
	        
	      
	         //隐藏预览
	        $gallery.on("click", function() {
	            $gallery.fadeOut(100);
	        });
	        
	         //删除图片
		    $("#gallery .gallery_del").on("click",function () {
		        var galleryImg = $("#galleryImg").attr("style");
		        $('#imgList li').each(function () {
		            var $this = $(this);
		            if($this.attr("style") == galleryImg){
		                var picId = $this.attr("data");
		                if(picId != undefined){
			                $.ajax({
								url:"${webctx}/pictures/removePic",
								data:{"id":picId},
								success:function(){
									$this.remove();
								}
							});
		                }else{
		                	$this.remove();
		                }
		            }
		        });
		        $("#gallery").fadeOut(100);
		    });
		    
			
			$("#form").validate({
				rules : {
					'serviceName' : {
						required : true
					},
					'serviceDate' : {
						required : true,
					},
					'serviceTime' : {
						required : true,
						number:true
					},
					'point' : {
						required : true,
						number:true
					},
					'confirmer': {
						required : true
					}
				},
				messages : {
					'serviceName' : {
						required : "服务项目必填"
					},
					'serviceDate' : {
						required : "服务时间必填",
					},
					'serviceTime' : {
						required : "服务时长必填",
						number : "请填入数字"
					},
					'point' : {
						required : "服务积分必填",
						number : "请填入数字"
					},
					'confirmer': {
						required : "确认人必填"
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
								var serviceId = $("#serviceId").val();
								var files = uploader.getFiles();
								if(files.length > 0){
									//开始上传图片
									uploader.option("formData",{
										"serviceDetailId": serviceId
									});
									uploader.upload();
								}
								alert("更新成功！");
								setTimeout(function() {
									window.location.href = "${webctx}/volunteer/serviceList/" + $("#id").val();
								}, 3000);
							} else {
								alert("更新失败。原因：" + data.message);
							}
						}
					})
				}
			});
		
		});
		
	</script>
</body>
</html>
