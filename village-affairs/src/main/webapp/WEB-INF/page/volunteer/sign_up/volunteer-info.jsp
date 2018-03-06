<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>


<input type="hidden" name="id" id="id" value="${volunteer.id }">
<div class="control-group">
	<label class="control-label">编号</label>
	<div class="controls">
		<c:if test="${volunteer.id ne null }">
			<p class="form-control-static">${volunteer.no }</p>
		</c:if>
		<c:if test="${volunteer.id eq null }">
			<input type="text" placeholder="" name="no" id="no"
			value="${volunteer.no }" />
		</c:if>
	</div>
</div>
<div class="control-group">
	<label class="control-label">姓名</label>
	<div class="controls">
		<c:if test="${volunteer.id ne null }">
			<p class="form-control-static">${volunteer.name }</p>
		</c:if>
		<c:if test="${volunteer.id eq null }">
			<input type="text" placeholder="" name="name"
			value="${volunteer.name }" />
		</c:if>
	</div>
</div>
<div class="control-group">
	<label class="control-label">性别</label>
	<div class="controls">
		<select name="sex">
			<option value="男" 
				<c:if test="${volunteer.sex eq '男' }">selected="selected"</c:if>
			>男</option>
			<option value="女"
				<c:if test="${volunteer.sex eq '女' }">selected="selected"</c:if>
			>女</option>
		</select>
	</div>
</div>
<div class="control-group">
	<label class="control-label">身份证号</label>
	<div class="controls">
		<input type="text" placeholder="" name="idCard" id="idCard"
			value="${volunteer.idCard }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">手机号</label>
	<div class="controls">
		<input type="text" placeholder="" name="phoneNo"
			value="${volunteer.phoneNo }" />
	</div>
</div>
<shiro:hasRole name="ROLE_ADMIN">
	<div class="control-group">
		<label class="control-label">所在社区</label>
		<div class="controls">
			<label> <input type="radio" name="community"
				value="penglang"
				<c:if test="${volunteer.community eq 'penglang'}">checked="checked"</c:if> />
				蓬朗社区
			</label> <label> <input type="radio" name="community"
				value="pengxii"
				<c:if test="${volunteer.community eq 'pengxii'}">checked="checked"</c:if> />
				蓬曦社区
			</label> <label> <input type="radio" name="community"
				value="pengxin"
				<c:if test="${volunteer.community eq 'pengxin'}">checked="checked"</c:if> />
				蓬欣社区
			</label> <label> <input type="radio" name="community"
				value="pengchen"
				<c:if test="${volunteer.community eq 'pengchen'}">checked="checked"</c:if> />
				蓬晨社区
			</label> <label> <input type="radio" name="community"
				value="pengyuan"
				<c:if test="${volunteer.community eq 'pengyuan'}">checked="checked"</c:if> />
				蓬苑社区
			</label> <label> <input type="radio" name="community"
				value="penglai"
				<c:if test="${volunteer.community eq 'penglai'}">checked="checked"</c:if> />
				蓬莱社区
			</label>
		</div>
	</div>
</shiro:hasRole>
