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
<link rel='stylesheet' href='${webctx}/static/css/opensans.css'/>
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
.carousel-indicators li {
	padding: 0px;
}
.widget-content{
		height: auto;
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
						<li class="active"><a href="${webctx }">首页</a></li>
						<li class=""><a href="${webctx }/information/partyInfoList">党建服务</a></li>
						<li class=""><a href="${webctx }/information/civilAffairsList?pagesize=5">民政民生</a></li>
						<li class=""><a href="${webctx }/information/comprehensivesInfoList?pagesize=5">综合治理</a></li>
						<li class=""><a href="${webctx }/information/communityAffairsList?pagesize=5">小区管理</a></li>
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
							<a href="${webctx }/login.jsp" class="btn btn-warning">登录</a>
							<%-- <a href="${webctx }/sysUser/registerPage" class="btn btn-info">注册</a> --%>
						</shiro:notAuthenticated>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div class="container content">
		<jsp:include page="/WEB-INF/page/common/banner.jsp"></jsp:include>

		<div id="breadcrumb">
			<a href="${webctx}" title="主页" class="tip-bottom">
				<i class="icon-home"></i>主页
			</a> 
		</div>
		<!--End-breadcrumbs-->	
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span6">
					<div class="widget-box">
						<div class="widget-content">
							<div id="echart" style="height:400px;"></div>
						</div>
					</div>
				</div>
				<div class="span6">
					<div class="widget-box">
						<div class="widget-content">
							<div id="epie" style="height:400px;"></div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row-fluid">
				<div class="span4">
					<div class="widget-box">
						<div class="widget-title"> 
							<a href="${webctx }/volunteer/list" title="更多"><span class="icon"> <i class="icon-arrow-right"></i> </span></a>
				        	<h5>志愿者积分榜</h5>
				    	</div>
			           	<p class="form-search text-center">
						      <input type="text" class="input-medium search-query" placeholder="输入身份证号查询积分" id="idCard">
						      <button type="submit" class="btn" id="searchBtn">查询</button>
						</p>
				    	<div class="widget-content nopadding">
				            <table class="table table-bordered">
				              <thead>
				                <tr>
				                  <th>姓名</th>
				                  <th>身份证号</th>
				                  <th>服务时长</th>
				                  <th>积分</th>
				                </tr>
				              </thead>
				              <tbody>
				              	<c:choose>
				              		<c:when test="${not empty volunteerList }">
				              			<c:forEach var="volunteer" items="${volunteerList }">
						              		<tr>
							                  <td><a href="${webctx }/volunteer/list?id=${volunteer.id}">${volunteer.name }</a></td>
							                  <td>
							                  	<hideId:hideIdCardNo end="4" idCard="${volunteer.idCard }" front="10"></hideId:hideIdCardNo>
							                  </td>
							                  <td>${volunteer.totalServiceTime }</td>
							                  <td>${volunteer.totalPoint }</td>
							                </tr>
						              	</c:forEach>
				              		</c:when>
				              		<c:otherwise>
				              			<td colspan="4">暂无数据！</td>
				              		</c:otherwise>
				              	</c:choose>
				              </tbody>
				            </table>
						</div>
					</div>
				</div>
				<div class="span8">
					<div class="widget-box">
						<div class="widget-title"> 
							<a href="${webctx }/information/list" title="更多"><span class="icon"> <i class="icon-arrow-right"></i> </span></a>
				        	<h5>最新信息</h5>
				    	</div>
				    	<div class="widget-content nopadding">
				            <table class="table table-bordered">
				              <thead>
				                <tr>
				                  <th>标题</th>
				                  <th>版块</th>
				                  <th>社区</th>
				                  <th>发布时间</th>
				                  <th>发布人</th>
				                  <th>进度</th>
				                </tr>
				              </thead>
				              <tbody>
				              	<c:choose>
				              		<c:when test="${not empty  informationList}">
				              			<c:forEach var="information" items="${informationList }">
						              		<tr 
						              			<c:if test="${information.important }">
						              			style="color:red" 
						              		 	</c:if>
						              		 >
							                  <td title="${information.title }">
							                  	<a href="${webctx }/information/viewInfoDetail/${information.id}"
							                  		<c:if test="${information.important }">
							              			style="color:red" 
							              		 	</c:if>
							                  	>
							                  	<c:if test="${information.important }">
							              			<i class="icon-star"></i>
							              		</c:if>
							              			<omit:omitLongText maxLength="66" text="${information.title }"></omit:omitLongText>
							                  	</a>
							                  </td>
							                  <td>
							                  	<c:if test="${information.type eq 'notice' }">公告</c:if>
							                  	<c:if test="${information.type eq 'communityaffairs' }">小区管理</c:if>
							                  	<c:if test="${information.type eq 'party' }">党建服务</c:if>
							                  	<c:if test="${information.type eq 'civilaffairs' }">民政民生</c:if>
							                  	<c:if test="${information.type eq 'comprehensives' }">综合治理</c:if>
							                  	<c:if test="${information.type eq 'otheraffairs' }">其他</c:if>
							                  </td>
							                  <td>
							                  	<c:if test="${information.community eq 'street' }">街道</c:if>
							                  	<c:if test="${information.community eq 'penglang' }">蓬朗</c:if>
							                  	<c:if test="${information.community eq 'pengxii' }">蓬曦</c:if>
							                  	<c:if test="${information.community eq 'pengxin' }">蓬欣</c:if>
							                  	<c:if test="${information.community eq 'pengchen' }">蓬晨</c:if>
							                  	<c:if test="${information.community eq 'pengyuan' }">蓬苑</c:if>
							                  	<c:if test="${information.community eq 'penglai' }">蓬莱</c:if>
							                  </td>
							                  <td>
							                  	<fmt:formatDate value="${information.refreshTime }" pattern="yyyy/MM/dd"/>
							                  </td>
							                  <td>${information.author }</td>
							                  <td>
							                  	<c:if test="${information.completePercent != null}">
							                  	<fmt:formatNumber value="${information.completePercent }" pattern="0.00"></fmt:formatNumber>%
												</c:if>
							                  </td>
							                </tr>
						              	</c:forEach>
				              		</c:when>
				              		<c:otherwise>
				              			<td colspan="5"><i class="icon-info-sign"></i>&nbsp;&nbsp;暂无公告信息</td>
				              		</c:otherwise>
				              	</c:choose>
				              </tbody>
				            </table>
						</div>
					</div>
				</div>
			</div>

			<!-- modal-dailog -->
			<jsp:include page="/WEB-INF/page/common/modal_dailog.jsp"></jsp:include>
			<!-- end-modal-dailog -->
		</div>
		
	</div>

	<!--Footer-part-->
	<jsp:include page="/WEB-INF/page/common/foot.jsp"></jsp:include>
	<!--end-Footer-part-->
	
	
	<script src="${webctx}/static/js/jquery.min.js"></script>
	<script src="${webctx}/static/js/jquery.form.min.js"></script>
	<script src="${webctx}/static/js/bootstrap.min.js"></script>
	<script src="${webctx}/static/js/echarts.min.js"></script>
	<script>
		$('.carousel').carousel({
			interval : 2000,
			pause : "hover"
		});
		  // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echart'));

        // 指定图表的配置项和数据
        var option = {
        	color: ['#C23531', '#2F4554', '#61A0A8', '#5BB75B', '#FCEC1A','#3385FF'],
            title: {
                text: '各社区绩效情况明细',
                subtext:'xxxx年度',
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
            	name: '蓬曦',
                type: 'bar',
                data: []
            },{
            	name: '蓬欣',
                type: 'bar',
                data: []
            },{
            	name: '蓬晨',
                type: 'bar',
                data: []
            },{
            	name: '蓬苑',
                type: 'bar',
                data: []
            },{
            	name: '蓬莱',
                type: 'bar',
                data: []
            }]
        };
        myChart.setOption(option);
        myChart.showLoading();
        $.getJSON("${webctx}/getEchart", function(data){
        	 // 使用刚指定的配置项和数据显示图表。
        	option.xAxis.data = data.categories;
        	option.series = data.series;
        	option.title.text = data.title;
        	option.title.subtext = data.subTitle;
        	myChart.hideLoading();
        	myChart.clear();
        	myChart.setOption(option);
        });

       
        
        var myPie = echarts.init(document.getElementById('epie'));
        pieOption = {
        	color: ['#C23531', '#2F4554', '#61A0A8', '#5BB75B', '#FCEC1A','#3385FF'],
		    title : {
		        text: '各社区完成工作汇总',
		        subtext: 'xxxx年度',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: ['蓬朗','蓬莱','蓬晨','蓬曦','蓬欣','蓬苑']
		    },
		    series : [
		        {
		            name: '完成总计',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
        
        myPie.setOption(pieOption);
        myPie.showLoading();
        $.getJSON("${webctx}/getEpie", function(data){
        	 // 使用刚指定的配置项和数据显示图表。
        	pieOption.title.text=data.title;
        	pieOption.title.subtext = data.subTitle;
        	pieOption.series[0].data = data.seriesData;
        	myPie.hideLoading();
        	myPie.setOption(pieOption);
        });
        
        
        
        $("#searchBtn").on("click",function(){
        	window.location.href = "${webctx}/volunteer/list?idCard="+$("#idCard").val();
        });
	</script>
</body>

</html>