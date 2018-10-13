function BrowseImage(options){
    var opts = $.extend({
        node:null,
        callback:null
    },options);
    this.node = opts.node;
    this.input = this.node.find("[type='file']");
    this.callback = opts.callback;
};
BrowseImage.prototype = {
    constructor:BrowseImage,
    trigger:function(){
        var _this = this;
        if(!_this.node || !_this.node.length || !_this.input.length){return}
        //绑定事件
        _this.bindEve();
    },
    //change事件
    handleChange:function(){
        var _this = this;
        return function(){
            var self = this;
            if(self.value == '' || !/(gif|jpg|jpeg|png)/.test(self.value)){return}
            var path = _this.getLocalPath(self);
            //检测图片
            _this.loadImage(path);
        }
    },
    //检测图片
    loadImage:function(src){
		var _this = this;
        var img = new Image();
		img.src = src;
		if(img.complete){
            _this.handleCallback(img);
		}else{
			img.onload = function(){
                _this.handleCallback(img);
			};	
		} 
		img.onerror = function(){
			alert('图片加载失败');
			return false;	
		}
    },
    //回调
    handleCallback:function(img){
        var _this = this;
        if(typeof _this.callback == 'function'){
            _this.callback(img);
        }
    },
    //获取本地路径
    getLocalPath : function(t){
		var _src = '';
		if(window.URL&&window.URL.createObjectURL){
			_src = window.URL.createObjectURL(t.files[0]);
		}else{
			_src = t.value;
		}	
		return _src;	
	},
    //绑定事件
    bindEve:function(){
        var _this = this;
        _this.input.on('change',_this.handleChange());
    }
};

//******针对jquery拓展******
$.fn.browseImage = function(fn){
    if(!$(this).length || !$(this).find("[type='file']").length){return}
    var $this = $(this);
    //循环实例化
    $this.each(function(){
        new BrowseImage({
            node:$(this),
            callback:fn
        }).trigger();
    });
};