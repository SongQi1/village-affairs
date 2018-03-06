<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>
<div class="pagination alternate text-center">
	<ul>
		<pg:pager url="${pageUrl}" items="${pageView.totalRecord}"
			maxPageItems="${pageView.maxResult }"
			export="currentPageNumber=pageNumber">
			<pg:param name="pagesize" value="${pageView.maxResult }" />
			<pg:index>
				<pg:first>
					<li><a href="${pageUrl}">首页</a></li>
				</pg:first>
				<pg:prev>
					<li><a href="${pageUrl}">前一页</a></li>
				</pg:prev>
				<pg:pages>
					<c:choose>
						<c:when test="${pageNumber == currentPageNumber}">
							<li class="active"><a href="${pageUrl}">${pageNumber}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${pageUrl}">${pageNumber}</a></li>
						</c:otherwise>
					</c:choose>
				</pg:pages>
				<pg:next>
					<li><a href="${pageUrl}">后页</a></li>
				</pg:next>
				<pg:last>
					<li><a href="${pageUrl}">尾页</a></li>
				</pg:last>
			</pg:index>
		</pg:pager>
	</ul>
</div>