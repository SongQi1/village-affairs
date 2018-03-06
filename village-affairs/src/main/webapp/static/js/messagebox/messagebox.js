/**
 * js创建一个等待提示框
 * 
 * @type
 */
// 获取artDialog路径
/*_path = window['_artDialog_path'] || (function(script, i, me) {
	for (i in script) {
		// 如果通过第三方脚本加载器加载本文件，请保证文件名含有"artDialog"字符
		if (script[i].src && script[i].src.indexOf('artDialog') !== -1)
			me = script[i];
	};

	_thisScript = me || script[script.length - 1];
	me = _thisScript.src.replace(/\\/g, '/');
	return me.lastIndexOf('/') < 0 ? '.' : me.substring(0, me
					.lastIndexOf('/'));
}(document.getElementsByTagName('script')));

// 无阻塞载入CSS (如"artDialog.js?skin=aero")
_skin = _thisScript.src.split('skin=')[1];
if (_skin) {
	var link = document.createElement('link');
	link.rel = 'stylesheet';
	link.href = _path + '/skins/' + _skin + '.css?' + artDialog.fn.version;
	_thisScript.parentNode.insertBefore(link, _thisScript);
};
//<link rel="stylesheet" href="http://localhost:8080/GatewayNew/javascripts/artDialog4.1.2/skins/blue.css?4.1.2">
 */
_path = (function(script, i, me) {
	for (i in script) {
		// 如果通过第三方脚本加载器加载本文件，请保证文件名含有"artDialog"字符
		if (script[i].src && script[i].src.indexOf('messagebox') !== -1)
			me = script[i];
	};

	_thisScript = me || script[script.length - 1];
	me = _thisScript.src.replace(/\\/g, '/');
	return me.lastIndexOf('/') < 0 ? '.' : me.substring(0, me.lastIndexOf('/'));
}(document.getElementsByTagName('script')));
var link = document.createElement('link');
link.rel = 'stylesheet';
link.href = _path + '/msgbox.css';
_thisScript.parentNode.insertBefore(link, _thisScript);
var MessageBox = {
	loading : {
		bgObj: null,
		main : null,
		show : function(msg) {
			this.main = document.createElement('div');
			this.main.className = 'qz_msgbox_layer_wrap';

			var span = document.createElement('span');
			span.style.cssText = 'z-index:10000;';
			span.className = 'qz_msgbox_layer';

			var span1 = document.createElement('span');
			span1.className = 'gtl_ico_clear';

			var img = document.createElement('img');
			img.src = _path + '/images/loading.gif';

			var span2 = document.createElement('span');
			span2.className = 'gtl_end';
			var textNode = document.createTextNode(msg);
			span.appendChild(span1);
			span.appendChild(img);
			span.appendChild(span2);
			span.appendChild(textNode);
			this.main.appendChild(span);

			var sWidth, sHeight;
			sWidth = document.body.clientWidth;
			sHeight = document.body.clientHeight;

			this.bgObj = document.createElement("div");
			this.bgObj.setAttribute('id', 'bgDiv');
			this.bgObj.style.position = "absolute";
			this.bgObj.style.top = "0";
			this.bgObj.style.background = "#777";
			this.bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
			this.bgObj.style.opacity = "0.6";
			this.bgObj.style.left = "0";
			this.bgObj.style.width = sWidth + "px";
			this.bgObj.style.height = sHeight + "px";
			document.body.appendChild(this.bgObj);
			document.body.appendChild(this.main);
		},
		hide : function() {
			document.body.removeChild(this.main);
			document.body.removeChild(this.bgObj);
		}
	}
};