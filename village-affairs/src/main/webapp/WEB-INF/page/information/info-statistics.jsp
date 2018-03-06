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
<link rel='stylesheet' href="${webctx}/static/css/opensans.css" />
<link rel="stylesheet" href="${webctx}/static/css/daterangepicker.css" />


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
.daterangepicker .calendar th,.daterangepicker .calendar td{
	min-width:20px;
}
.daterangepicker .ranges{
	width:240px;
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
						<li class="active"><a href="${webctx }/information/statistics">查询统计</a></li>
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
				class="icon-home"></i>主页</a> <a href="#" class="current">查询统计</a>
		</div>
		<!--End-breadcrumbs-->	
		<div class="container-fluid">
			<form action="" method="post" id="form" class="form-horizontal" role="form">
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label">社区</label>
							<div class="controls">
								<select name="communities" multiple class="span11">
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
					
				</div>
				
				<div class="row-fluid">
					
					<div class="span6">
						<label class="control-label">栏目</label>
						<div class="controls" >
							<select name="types" multiple  class="span11">
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
				
				
			</form>
			<div class="row-fluid text-center">
					<button type="submit" class="btn btn-primary btn-large"><i class="icon-search"></i>&nbsp;查询</button>
				</div>
			<hr/>
			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-content">
						<div id="echart" style="height:400px;"></div>
					</div>
				</div>
			</div>
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
	
		  // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echart'));
        // 指定图表的配置项和数据
       var option = {
       		color: ['#C23531', '#2F4554', '#61A0A8', '#5BB75B', '#FCEC1A','#3385FF'],
            title: {
                text: '各社区绩效情况明细',
                subtext:'',
                x:'center'
            },
            tooltip: {},
            legend: {
            	orient: 'vertical',
		        right: 'right',
                data:['蓬朗','蓬莱','蓬晨','蓬曦','蓬欣','蓬苑']
            },
            xAxis: {
                data: []
            },
            yAxis:  [
		        {
		            type: 'value'
		        }
		    ],
            series: [
            {
                name: '蓬朗',
                type: 'bar',
                data: []
            },{
            	name: '蓬莱',
                type: 'bar',
                data: []
            },{
            	name: '蓬晨',
                type: 'bar',
                data: []
            },{
            	name: '蓬曦',
                type: 'bar',
                data: []
            },{
            	name: '蓬欣',
                type: 'bar',
                data: []
            },{
            	name: '蓬苑',
                type: 'bar',
                data: []
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $(".btn").on("click",function(){
        	myChart.showLoading();
        	$("#form").ajaxSubmit({
        		url:"${webctx}/information/getEchart",
        		success:function(data){
        			option.color = data.colors;
        			option.xAxis.data = data.categories;
        			option.series = data.series;
        			option.legend.data = data.legendData;
		        	option.title.text = data.title;
		        	option.title.subtext = data.subTitle;
		        	myChart.hideLoading();
		        	myChart.clear();
		        	myChart.setOption(option);
        		}
        	})
        })
	</script>
</body>

</html>