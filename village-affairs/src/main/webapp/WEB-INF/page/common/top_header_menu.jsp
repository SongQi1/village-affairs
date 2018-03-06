<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>

<script src="${webctx}/static/js/jquery.min.js"></script>
<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
	<ul class="nav">
		<li class="dropdown" id="profile-messages"><a title="" href="#"
			data-toggle="dropdown" data-target="#profile-messages"
			class="dropdown-toggle"><i class="icon icon-user"></i> <span
				class="text">欢迎 ${current_user.chineseName }</span><b class="caret"></b></a>
			<ul class="dropdown-menu">
				<li><a href="${webctx }/sysUser/profile"><i
						class="icon-user"></i>个人信息</a></li>
				<li class="divider"></li>
				<li onclick="logout();"><a href="#"><i class="icon-key"></i>退出</a></li>
			</ul></li>
		<li class="dropdown" id="menu-messages">
			<a href="${webctx }/message/inbox"><i class="icon icon-envelope"></i> 
				<span class="text">站内信</span> <span class="label label-important" id="count">0</span>
			</a>
		</li>
		<li class=""><a title="" href="${webctx }/sysUser/setting"><i
				class="icon icon-cog"></i> <span class="text">设置</span></a></li>
		<li class="" onclick="logout();"><a title="" href="#"><i
				class="icon icon-share-alt"></i> <span class="text">退出</span></a></li>
	</ul>
</div>


<script>
	function getUnreadCount(){
		$.ajax({
			url:"${webctx}/message/getUnreadCount",
			beforeSend : function(xhr) {
			},
			success:function(data){
				$("#count").html(data);
			},
			complete : function(xhr, textStatus) {
				var sessionstatus = xhr.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
				if (sessionstatus == "timeout") {
					alert("您的会话已超时，请重新登录！");
					to("../login");
				}
				if(sessionstatus == "unauthorized"){
					alert("您没有权限操作");
				}
			}
		});
	}
	function logout() {
		showConfirm("提示", "确定要退出吗？");
		$("#confirm_btn").click(function() {
			to("${webctx}/sysUser/logout");
		});
	}
	
	getUnreadCount();
	setTimeout(getUnreadCount,60000);
	
</script>