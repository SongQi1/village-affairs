<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>


<input type="hidden" name="id" value="${sysUser.id }">
<div class="control-group">
	<label class="control-label">用户名</label>
	<div class="controls">
		<c:if test="${sysUser.id ne null }">
			<p class="form-control-static">${sysUser.userName }</p>
		</c:if>
		<c:if test="${sysUser.id eq null }">
			<input type="text" placeholder="" name="userName"
			value="${sysUser.userName }" />
		</c:if>
	</div>
</div>
<shiro:hasRole name="ROLE_ADMIN">
	<c:if test="${sysUser.id eq null }">
	<div class="control-group">
		<label class="control-label">密码</label>
		<div class="controls">
			<input type="password" placeholder="" name="password"
				value="${sysUser.password }" />
		</div>
	</div>
	</c:if>
</shiro:hasRole>
<div class="control-group">
	<label class="control-label">姓名</label>
	<div class="controls">
		<input type="text" placeholder="" name="chineseName"
			value="${sysUser.chineseName }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">联系电话</label>
	<div class="controls">
		<input type="text" placeholder="" name="mobileNo"
			value="${sysUser.mobileNo }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">邮件</label>
	<div class="controls">
		<input type="text" placeholder="" name="email"
			value="${sysUser.email }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">QQ</label>
	<div class="controls">
		<input type="text" placeholder="" name="qq" value="${sysUser.qq }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">微信</label>
	<div class="controls">
		<input type="text" placeholder="" name="weixin"
			value="${sysUser.weixin }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">学历</label>
	<div class="controls">
		<input type="text" placeholder="" name="degree"
			value="${sysUser.degree }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">职称</label>
	<div class="controls">
		<input type="text" placeholder="" name="jobTitle"
			value="${sysUser.jobTitle }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">职务</label>
	<div class="controls">
		<input type="text" placeholder="" name="jobPosition"
			value="${sysUser.jobPosition }" />
	</div>
</div>
<shiro:hasRole name="ROLE_ADMIN">
	<div class="control-group">
		<label class="control-label">负责社区</label>
		<div class="controls">
			<label> <input type="radio" name="community"
				value="penglang"
				<c:if test="${fn:contains(sysUser.community,'penglang') }">checked="checked"</c:if> />
				蓬朗社区
			</label> <label> <input type="radio" name="community"
				value="pengxii"
				<c:if test="${fn:contains(sysUser.community,'pengxii') }">checked="checked"</c:if> />
				蓬曦社区
			</label> <label> <input type="radio" name="community"
				value="pengxin"
				<c:if test="${fn:contains(sysUser.community,'pengxin') }">checked="checked"</c:if> />
				蓬欣社区
			</label> <label> <input type="radio" name="community"
				value="pengchen"
				<c:if test="${fn:contains(sysUser.community,'pengchen') }">checked="checked"</c:if> />
				蓬晨社区
			</label> <label> <input type="radio" name="community"
				value="pengyuan"
				<c:if test="${fn:contains(sysUser.community,'pengyuan') }">checked="checked"</c:if> />
				蓬苑社区
			</label> <label> <input type="radio" name="community"
				value="penglai"
				<c:if test="${fn:contains(sysUser.community,'penglai') }">checked="checked"</c:if> />
				蓬莱社区
			</label>
		</div>
	</div>
</shiro:hasRole>