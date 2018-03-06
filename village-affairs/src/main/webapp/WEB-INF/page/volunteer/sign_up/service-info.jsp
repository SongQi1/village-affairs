<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>

 <!--图片预览-->
<div class="gallery" id="gallery">
	<span class="gallery_img" id="galleryImg"
		style="background-image:url(./images/timg1.jpg)">
	</span>
	<div class="gallery_opr">
        <a href="javascript:" class="gallery_del">
            <i class="icon-trash"></i>
        </a>
    </div>
</div>
<input type="hidden" name="volunteerId" value="${volunteer.id }" id="id"/>
<input type="hidden" name="id" value="${serviceDetail.id }" id="serviceId"/>
<div class="control-group">
	<label class="control-label">姓名</label>
	<div class="controls">
		<p class="form-control-static">${volunteer.name }</p>
	</div>
</div>
<div class="control-group">
	<label class="control-label">身份证号</label>
	<div class="controls">
		<p class="form-control-static">${volunteer.idCard }</p>
	</div>
</div>
<div class="control-group">
	<label class="control-label">服务项目</label>
	<div class="controls">
		<input type="text" placeholder="" name="serviceName"
			value="${serviceDetail.serviceName }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">服务时间</label>
	<div class="controls">
		<input type="text" placeholder="" name="serviceDate" class="datepicker" 
			value="<fmt:formatDate value="${serviceDetail.serviceDate }" pattern="yyyy/MM/dd"/>" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">服务时长</label>
	<div class="controls">
		<input type="text" placeholder="" name="serviceTime"
			value="${serviceDetail.serviceTime }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">服务积分</label>
	<div class="controls">
		<input type="text" placeholder="" name="point"
			value="${serviceDetail.point }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">备注</label>
	<div class="controls">
		<input type="text" placeholder="" name="remark"
			value="${serviceDetail.remark }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">确认人</label>
	<div class="controls">
		<input type="text" placeholder="" name="confirmer"
			value="${serviceDetail.confirmer }" />
	</div>
</div>
<div class="control-group">
	<label class="control-label">图片</label>
	<div class="controls">
		  <div class="village_photo">
	            <ul id="imgList">
	            	<c:forEach var="picture" items="${serviceDetail.pictures }">
	            		<li class="village_photo_file" data="${picture.id }" style="background-image: url('${webctx}/pictures/viewimage/${picture.id }')"></li>
	            	</c:forEach>
	            	<c:if test="${not empty serviceDetail.pictures }">
	            		
	            	</c:if>
	            
	            </ul>
	            <div class="village_photo_file_add" id="picker">
	            	<i class="icon-plus"></i>
	            </div>
	      </div>
	</div>
</div>
