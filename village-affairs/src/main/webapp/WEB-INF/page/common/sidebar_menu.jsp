<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>

<!--sidebar-menu-->
<div id="sidebar">
	<a href="#" class="visible-phone"><i class="icon icon-home"></i> 导航</a>
	<ul>
		<li id="info"><a href="${webctx}/information/ckeditor"><i
				class="icon icon-globe"></i> <span>信息发布</span></a></li>
		<li id="noticemng"><a href="${webctx}/information/manageNotices"><i
				class="icon icon-volume-up"></i> <span>信息管理</span></a></li>
		<li id="volunteer">
			<a href="${webctx }/volunteer/list"><i class="icon icon-heart"></i><span>志愿者服务</span></a>
		</li>
		<shiro:hasRole name="ROLE_ADMIN">
			<li id="usermng">
				<a href="${webctx }/sysUser/getUsers">
					<i class="icon icon-group"></i><span>账号管理</span>
				</a>
			</li>
		</shiro:hasRole>
	</ul>
</div>
<!--sidebar-menu-->