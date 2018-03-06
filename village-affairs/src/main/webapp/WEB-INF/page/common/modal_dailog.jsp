<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>

<div id="confirmModal" class="modal hide">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button">×</button>
		<h3 id="confirmtitle">警告</h3>
	</div>
	<div class="modal-body">
		<p id="confirmmsg">删除后就不能恢复，确定要删除吗？</p>
	</div>
	<div class="modal-footer">
		<a data-dismiss="modal" class="btn btn-primary" href="#"
			id="confirm_btn">确定</a> <a data-dismiss="modal" class="btn" href="#">取消</a>
	</div>
</div>

<div id="myModal" class="modal hide">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button">×</button>
		<h3 id="alerttitle"></h3>
	</div>
	<div class="modal-body">
		<p id="alertmsg"></p>
	</div>
</div>