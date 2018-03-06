
$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform({
		fileBtnText:"请选择文件",
		fileDefaultText:"没有选择文件"
	});
	$('select').select2({
		minimumResultsForSearch:Infinity
	});
	$("[data-toggle='tooltip']").tooltip();
	$("th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('table').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		});
	});	
});


/**
 * 封装了alert方法
 * @param title
 * @param info
 */
function alert(info){
	$("#alerttitle").html("提示");
	$("#alertmsg").html(info);
	$("#myModal").modal("show");
	setTimeout(function(){
		$("#myModal").modal("hide");
	},3000);
}


function showConfirm(title, info){
	$("#confirmtitle").html(title);
	$("#confirmmsg").html(info);
	$("#confirmModal").modal({
		backdrop:"static",
		show:true
	});
};


function removeProject(projectId){
	showConfirm("警告!!","删除后就不能恢复，确定要删除吗？");
	$("#confirm_btn").click(function(){
		$.post("../deleteProject",{
			"id":projectId
		},function(data){
			if(data.success){
				alert("删除成功！");
				setTimeout(function(){
					window.location.reload();
				},3300)
				
			}else{
				alert("删除失败。原因："+data.message);
			}
		},"json");
	})
}
