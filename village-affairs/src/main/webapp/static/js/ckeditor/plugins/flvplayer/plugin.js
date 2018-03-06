CKEDITOR.plugins.add('flvplayer',
{
    init: function(editor)    
    {        
        //plugin code goes here
        var pluginName = 'flvplayer';        
        CKEDITOR.dialog.add(pluginName, this.path + 'dialogs/flvplayer.js');
        editor.config.flv_path = editor.config.flv_path || ( this.path);
        editor.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));        
        /*editor.addCommand('flvplayer', new CKEDITOR.dialogCommand('flvplayer'));
        CKEDITOR.dialog.add('flvplayer',
        function(editor) {
            return {
                title: 'Flv',
                minWidth: 350,
                minHeight: 100,
                contents: [{
                    id: 'tab1',
                    label: 'First Tab',
                    title: 'First Tab',
                    elements: [{
                        id: 'pagetitle',
                        type: 'text',
                        label: '请输入下一页文章标题<br />(不输入默认使用当前标题+数字形式)'
                    }]
                }],
                onOk: function() {
                    editor.insertHtml(" [page = "+this.getValueOf( 'tab1', 'pagetitle' )+"]");
                }
            };
        });*/
        editor.ui.addButton('flvplayer',
        {               
            label: '视频',
            command: pluginName,
			icon: this.path + 'flvplayer.gif'
        });
    }
});