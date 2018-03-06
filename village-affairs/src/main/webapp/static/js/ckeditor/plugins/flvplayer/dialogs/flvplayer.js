CKEDITOR.dialog.add('flvplayer',　function(a){
    var b = a.config;
    var　escape　=　function(value){
　　　　　　　　return　value;
　　　　};
    function UpdatePreview() {
        document.getElementById("_video_preview").innerHTML = ReturnPlayer()
    }
    function ReturnPlayer() {
        var src = CKEDITOR.dialog.getCurrent().getContentElement('info', 'src').getValue();
        var JWEmbem = "";
        JWEmbem = "<video  controls=\"controls\" preload=\"auto\" style=\"width: 300px; height: 200px;\">"+
        	"<source src=\""+ src +"\"></source>" +
        +"</video>";
        return JWEmbem
    }
　　　　return　{

　　　　　　　　title:　'视频',
　　　　　　　　resizable:　CKEDITOR.DIALOG_RESIZE_BOTH,
　　　　　　　　minWidth: 300,
          minHeight: 250,
　　　　　　　　contents:　[{
　　　　　　　　　　id: 'info',
                    label: '常规',
                    accessKey: 'P',
                    elements:[
                        {
                        type: 'hbox',
                        widths : [ '10%' ],
                        children:[{
                                id: 'src',
                                type: 'text',
                                label: '视频文件网址',
                                onChange : UpdatePreview
                            }]
                        },
                        
                        {
                        type: 'hbox',
                        widths : [ '50%', '50%' ],
                        children:[{
                            type:　'text',
　　　　　　　　　　　　　　label:　'视频宽度',
　　　　　　　　　　　　　　id:　'mywidth',
　　　　　　　　　　　　　　'default':　'600px',
　　　　　　　　　　　　　　style:　'width:50px'
                        },{
                            type:　'text',
　　　　　　　　　　　　　　label:　'视频高度',
　　　　　　　　　　　　　　id:　'myheight',
　　　　　　　　　　　　　　'default':　'480px',
　　　　　　　　　　　　　　style:　'width:50px'
                        }]// children finish
                        },{
                        type : 'vbox',
                        widths : ['100%'],
                        align : 'left',
                        children : [{
                            type : 'html',
                            id : 'preview',
                            html:'<div id="_video_preview" ><video  controls="controls" preload="auto" width="300" height="200"></video></div>'
                        }]
                    }]
                    }, {
                        id: 'Upload',
                        hidden: 0,
                        filebrowser: 'uploadButton',
                        label: '上传',
                        elements: [{
                            type: 'file',
                            id: 'upload',
                            label: '上传',
                            size: 38
                        },
                        {
                            type: 'fileButton',
                            id: 'uploadButton',
                            label: '发送到服务器',
                            filebrowser: 'info:src',
                            'for': ['Upload', 'upload']
                        }]
　　　　　　　　}],
　　　　　　　　onOk:　function(){
　　　　　　　　　　　　mywidth　=　this.getValueOf('info',　'mywidth');
　　　　　　　　　　　　myheight　=　this.getValueOf('info',　'myheight');
　　　　　　　　　　　　mysrc　=　this.getValueOf('info',　'src');
　　　　　　　　　　　　html　=　''　+　escape(mysrc)　+　'';
　　　　　　　　　　　　// editor.insertHtml("<pre class=\"brush:" + lang + ";\">" + html +
			// "</pre>");

				a.insertHtml('<video  controls="controls" preload="auto" width="'+mywidth+'" height="'+myheight+'"><source src="'+html+'" type="video/mp4"></video>');
　　　　　　　　},
　　　　　　　　onLoad:　function(){
　　　　　　　　}
　　　　};
});