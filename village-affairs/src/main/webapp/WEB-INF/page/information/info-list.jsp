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
<link rel="stylesheet" href="${webctx}/static/css/select2.css" />
<link rel="stylesheet" href="${webctx}/static/css/matrix-style.css" />
<link rel="stylesheet" href="${webctx}/static/font-awesome/css/font-awesome.css"  />
<link rel="stylesheet" href="${webctx}/static/css/daterangepicker.css" />
<style type="text/css">
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
	.daterangepicker .calendar th,.daterangepicker .calendar td{
		min-width:20px;
	}
	.daterangepicker .ranges{
		width:240px;
	}
</style>

</head>


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
						<li class="active"><a href="${webctx }/information/list">科室信息</a></li>
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
			<a href="${webctx}" title="主页" class="tip-bottom"><i
				class="icon-home"></i>主页</a> <a href="#" class="current">科室信息</a>
		</div>
		<!--End-breadcrumbs-->	
		<div class="container-fluid">
			<form action="" method="post" id="form" class="form-horizontal" role="form">
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label">标题</label>
							<div class="controls">
								<input class="span11" type="text" name="title" value="${information.title }">
							</div>
						</div>
					</div>
					
					<div class="span6">
						<div class="control-group">
							<label class="control-label">社区</label>
							<div class="controls">
								<select name="communities" multiple class="span11">
									<option value="street"
										<c:if test="${fn:contains(information.communities, 'street') }">selected="selected"</c:if>
									>街道</option>
									<option value="penglang" 
										<c:if test="${fn:contains(information.communities, 'penglang') }">selected="selected"</c:if>
									>蓬朗社区</option>
									<option value="pengxii"
										<c:if test="${fn:contains(information.communities, 'pengxii') }">selected="selected"</c:if>
									>蓬曦社区</option>
									<option value="pengxin"
										<c:if test="${fn:contains(information.communities, 'pengxin') }">selected="selected"</c:if>
									>蓬欣社区</option>
									<option value="pengchen"
										<c:if test="${fn:contains(information.communities, 'pengchen') }">selected="selected"</c:if>
									>蓬晨社区</option>
									<option value="pengyuan"
										<c:if test="${fn:contains(information.communities, 'pengyuan') }">selected="selected"</c:if>
									>蓬苑社区</option>
									<option value="penglai"
										<c:if test="${fn:contains(information.communities, 'penglai') }">selected="selected"</c:if>
									>蓬莱社区</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label">发布日期</label>
							<div class="controls">
								<input class="form-control date-picker span11" id="dateTimeRange" value="${information.startDate } 至 ${information.endDate }" type="text">
								<span class="input-group-addon">
									<i class="fa fa-calendar bigger-110"></i>
								</span>
								<input name="startDate" id="beginTime" value="" type="hidden">
								<input name="endDate" id="endTime" value="" type="hidden">
							</div>
						</div>
					</div>
					<div class="span6">
						<label class="control-label">栏目</label>
						<div class="controls" >
							<select name="types" multiple  class="span11">
								<option value="notice"
									<c:if test="${fn:contains(information.types, 'notice') }">selected="selected"</c:if>
								>公告</option>
								<option value="communityaffairs"
									<c:if test="${fn:contains(information.types, 'communityaffairs') }">selected="selected"</c:if>
								>小区管理</option>
								<option value="party"
									<c:if test="${fn:contains(information.types, 'party') }">selected="selected"</c:if>
								>党建服务</option>
								<option value="civilaffairs"
									<c:if test="${fn:contains(information.types, 'civilaffairs') }">selected="selected"</c:if>
								>民政民生</option>
								<option value="comprehensives"
									<c:if test="${fn:contains(information.types, 'comprehensives') }">selected="selected"</c:if>
								>综合治理</option>
								<option value="otheraffairs"
									<c:if test="${fn:contains(information.types, 'otheraffairs') }">selected="selected"</c:if>
								>其他</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label">重要</label>
							<div class="controls">
				                <select name="important" class="span11">
					                <option value="">全部</option>	
									<option value="true"
										<c:if test="${information.important == true}">selected="selected"</c:if>
									>是</option>
									<option value="false"
										<c:if test="${information.important == false }">selected="selected"</c:if>
									>否</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row-fluid text-center">
					<button type="submit" class="btn btn-primary btn-large"><i class="icon-search"></i>&nbsp;查询</button>
				</div>
				<hr>
				<h6>本次查询结果共${pageView.totalPage}页${pageView.totalRecord}条记录</h6>
				<table class="table table-bordered table-responsive table-striped  table-hover">
					<thead>
						<tr>
							<th>标题</th>
							<th>社区</th>
							<th>栏目</th>
							<th>发布日期</th>
							<th>发布人</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty pageView.records}">
								<c:forEach var="information" items="${pageView.records }">
									<tr <c:if test="${information.important }">
				              			style="color:red" 
				              		 	</c:if>>
										<td>
											<c:if test="${information.important }">
						              			<i class="icon-star"></i>
						              		</c:if>	
											<a href="${webctx}/information/viewInfoDetail/${information.id}"
												<c:if test="${information.important }">
							              			style="color:red" 
							              		 </c:if>
											>
												${information.title }
											</a>
										</td>
										<td>
											<c:if test="${information.community eq 'all' }">街道</c:if>
						                  	<c:if test="${information.community eq 'penglang' }">蓬朗</c:if>
						                  	<c:if test="${information.community eq 'pengxii' }">蓬曦</c:if>
						                  	<c:if test="${information.community eq 'pengxin' }">蓬欣</c:if>
						                  	<c:if test="${information.community eq 'pengchen' }">蓬晨</c:if>
						                  	<c:if test="${information.community eq 'pengyuan' }">蓬苑</c:if>
						                  	<c:if test="${information.community eq 'penglai' }">蓬莱</c:if>
										</td>
										<td>
											<c:if test="${information.type eq 'notice' }">公告</c:if>
						                  	<c:if test="${information.type eq 'communityaffairs' }">小区管理</c:if>
						                  	<c:if test="${information.type eq 'party' }">党建服务</c:if>
						                  	<c:if test="${information.type eq 'civilaffairs' }">民政民生</c:if>
						                  	<c:if test="${information.type eq 'comprehensives' }">综合治理</c:if>
						                  	<c:if test="${information.type eq 'otheraffairs' }">其他</c:if>
										</td>
										<td><fmt:formatDate value="${information.refreshTime }" pattern="yyyy/MM/dd"/></td>
										<td>${information.author }</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="5">
										<i class="icon-info-sign"></i>&nbsp;&nbsp;没有查询结果！
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
				
				<!-- 分页 -->
				<c:set var="pageUrl" value="${webctx }/information/list"></c:set>
				<jsp:include page="/WEB-INF/page/common/pager.jsp"></jsp:include>
				
				
				
			</form>
			
			<div class="text-center">
				<button class="btn btn-success btn-large" onclick="back();">返回</button>
			</div>

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
	
	<script src="${webctx}/static/js/date-time/common.js?ver=1"></script>
	<script src="${webctx}/static/js/date-time/moment.js?ver=1"></script>
	<script src="${webctx}/static/js/date-time/daterangepicker.zh-CN.js?ver=1"></script>
	<script src="${webctx}/static/js/date-time/daterangepicker.js?ver=1"></script>
	
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
		$(function() {
			$('#dateTimeRange').daterangepicker({
				applyClass : 'btn-sm btn-success',
				cancelClass : 'btn-sm btn-default',
				locale: {
					applyLabel: '确认',
					cancelLabel: '取消',
					fromLabel : '起始时间',
					toLabel : '结束时间',
					customRangeLabel : '自定义',
					firstDay : 1
				},
				ranges : {
					//'最近1小时': [moment().subtract('hours',1), moment()],
					'今日': [moment().startOf('day'), moment()],
					'昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
					'最近7日': [moment().subtract('days', 6), moment()],
					'最近30日': [moment().subtract('days', 29), moment()],
					'本月': [moment().startOf("month"),moment().endOf("month")],
					'上个月': [moment().subtract(1,"month").startOf("month"),moment().subtract(1,"month").endOf("month")]
				},
				opens : 'right',	// 日期选择框的弹出位置
				separator : ' 至 ',
				showWeekNumbers : true,		// 是否显示第几周
		
		
				//timePicker: true,
				//timePickerIncrement : 10,	// 时间的增量，单位为分钟
				//timePicker12Hour : false,	// 是否使用12小时制来显示时间
		
		
				//maxDate : moment(),			// 最大时间
				format: 'YYYY-MM-DD'
		
			}, function(start, end, label) { // 格式化日期显示框
				$('#beginTime').val(start.format('YYYY-MM-DD'));
				$('#endTime').val(end.format('YYYY-MM-DD'));
			})
			.next().on('click', function(){
				$(this).prev().focus();
			});
		});
		
		
	</script>

</body>

</html>