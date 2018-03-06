//扩展js中String对象的方法
String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.isEmpty= function(){
	var regu = "^\s+$";
	var reg = new RegExp(regu);
	return reg.test(this) || this == "";
};
String.prototype.isNotEmpty = function(){
	return !this.isEmpty();
};
String.prototype.length = function(){
	var w = 0;
	for(var i=0; i<this.length; i++){
		var c = this.charCodeAt(i);
		//单字节加1
		if((c >= 0x0001 && c <=0x007e) || (0xff60 <= c && c<= 0xff9f)){
			w++;
		}else{
			w += 2;
		}
	}
	return w;
};

/**
 * 只能输入数字，backspace,tab和enter,禁止粘贴
 * keyCode:
 * 48~57: 0~9
 * 96~105：数字键盘的0~9
 * 8: backspace
 * 9: tab
 * enter:13	
 * @return {}
 */
$.fn.integer = function() {
	return this.bind('paste',function(e){e.preventDefault();}).keypress(function(e) {
		var keyCode = e.keyCode ? e.keyCode : e.charCode ? e.charCode : 0;
		if (keyCode && (keyCode < 48 || keyCode > 57) && keyCode != 8 && keyCode != 9 && keyCode != 13) {
			e.preventDefault();
		}
	});
};


/*
 * 全局的ajax访问，处理ajax清求时sesion超时  
 */
$.ajaxSetup({
	type : "post",
	dataType : "json",
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	cache : false,
	statusCode: {
		404: function() {
		    alert('page not found');
		  }
	},
	beforeSend : function(xhr) {
		MessageBox.loading.show("系统正在处理中，请稍等...");
	},
	complete : function(xhr, textStatus) {
		MessageBox.loading.hide();
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


//禁用Enter键表单自动提交  
document.onkeydown = function(event) {  
    var target, code, tag;  
    if (!event) {  
        event = window.event; //针对ie浏览器  
        target = event.srcElement;  
        code = event.keyCode;  
        if (code == 13) {  
            tag = target.tagName;  
            if (tag == "TEXTAREA") { return true; }  
            else { return false; }  
        }  
    }  
    else {  
        target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
        code = event.keyCode;  
        if (code == 13) {  
            tag = target.tagName;  
            if (tag == "INPUT") { return false; }  
            else { return true; }  
        }  
    }  
};  

$.validator.addMethod("strLength",function(value,element,len){
	var w = 0;
	for(var i=0; i<value.length; i++){
		var c = value.charCodeAt(i);
		//单字节加1
		if((c >= 0x0001 && c <=0x007e) || (0xff60 <= c && c<= 0xff9f)){
			w++;
		}else{
			w += 2;
		}
	}
	if(w > len){
		return false;
	}
	return true;
},"长度超过{0}个字符");




/**
 * 分页
 */
function goToPage(){
	document.getElementById('form').submit();
}
function nextPage(){
	var pageNo = parseInt(document.getElementById("currentPageNo").value) + 1;
	document.getElementById("pageNo").value= pageNo;
	goToPage();
}
function previousPage(){
	var pageNo = parseInt(document.getElementById("currentPageNo").value) - 1;
	document.getElementById("pageNo").value= pageNo;
	goToPage();
}
function homePage(){
	document.getElementById("pageNo").value=1;
	goToPage();
}
function lastPage(){
	var pageNo = document.getElementById("totalPageCount").value;
	document.getElementById("pageNo").value = pageNo;
	goToPage();
}
function reload(){
	window.location.reload();
}
function help(msg){
    if(msg.length == 0) {
		alert('欢迎使用!');
    } else {
        alert(msg);
    }
}

function to(url){
	window.location.href=url;
}
function back(){
	history.go(-1);
}

function setCurTime(oid){
	var now=new Date();
	var year=now.getFullYear();
	var month=now.getUTCMonth()+1;
	var day=now.getDate();
	var hours=now.getHours();
	var minutes=now.getMinutes();
	var seconds=now.getSeconds();
	var timeString = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
	var oCtl = document.getElementById(oid);
	oCtl.value = timeString;
}

function openModalDialog(url, iHeight, iWidth){
	var iTop = (window.screen.availHeight - 20 - iHeight) / 2;  
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;  
	return showModalDialog(url,window,"dialogWidth:"+iWidth+"px;dialogHeight:"+iHeight+"px;dialogTop:"+iTop+"px;dialogLeft:"+iLeft+"px;edge: Raised; center: Yes; help: No; resizable: Yes; status: No;location:no;toolbar:no");
}

function disableButtons(state) {
    var btns = document.getElementsByTagName("button");
    for (var index = 0; index < btns.length; index++) {
        btns[index].disabled = state;
    }
}
    
function limitLenght(obj, limit) {
    var id = obj.id;
    var value = document.getElementById(id).value;
    if(!strWidthCheck(value, limit)) {
        value = value.substring(0, 6);
        document.getElementById(id).value = value;
        showDetail();
    }
}
    
function focusInput(obj, value){
    if(obj.value==value){obj.value='';}
}

function blurInput(obj, value){
    if(obj.value==''){obj.value=value;}
}



function checkfile(fileInput) 
{ 
  var filename=fileInput.value;
  var fileText=filename.substring(filename.lastIndexOf("."),filename.length); //获取文件扩展名
  fileText=fileText.toLowerCase();
  if (fileText=='.jpeg' || fileText=='.png' || fileText=='.jpg' || fileText=='.gif' || fileText=='.bmp')
  {}else
  {
	  alert('只能上传图片格式的文件！');
	  $(fileInput).val('');
  }
  
  if(window.ActiveXObject)//判断条件也可以改为navigator.userAgent.indexOf("MSIE")!=-1
  {
	//IE浏览器
	var image = $(fileInput).next(".image")[0];
	image.dynsrc=fileInput.value;
	if(image.fileSize>5242880){
		alert('文件大小不能超过5M！（您上传的文件大小为'+Math.round((image.fileSize/1048576)*100)/100+'MB）');
		$(fileInput).focus();
		$(fileInput).val('');
	}
  }else {
	if(fileInput.files[0].size>5242880){
		alert('文件大小不能超过5M！（您上传的文件大小为'+Math.round((fileInput.files[0].size/1048576)*100)/100+'MB）');
		$(fileInput).focus();
		$(fileInput).val('');
	}
  }
}