/**
 * 在点击位置生成拖动层元素进行拖动
 */
function setVirtualDrag(selector, callback, opactiy){
	var doms = document.querySelectorAll(selector);
	for(i=0;i<doms.length;i++){
		var sr = doms[i];
		sr.onmousedown = function(e){
			var val = this.innerHTML;
			var dom = document.createElement(this.localName);
			var nx = e.pageX;
			var ny = e.pageY;
			dom.setAttribute("id","drag_div");
			dom.innerHTML = val;
			addStyle(dom, this);
			//修改新元素样式用于拖动
			dom.style.position = "absolute";
			dom.style.textAlign = "center";
			dom.style.verticalAlign = "middle";
			dom.style.cursor = "pointer";
			dom.style.opacity = (opactiy+"")||"0.8";
			dom.style.left = nx-this.offsetWidth/2 + "px";
			dom.style.top = ny-this.offsetHeight/2 + "px";
			document.querySelector("body").appendChild(dom);
			__setDrag("#drag_div", callback);
		}
	}
	
}
/**
 * js为拖动层元素添加拖动事件
 */
function __setDrag(selector, callback) {
	window.dragsel = selector;
	document.querySelector(selector).onmouseover = function (e) {
		window.divmoveflag = true;
		window.rx = this.offsetLeft;
		window.ry = this.offsetTop;
	}
	document.onmousemove = function (e) {
		var thisz = document.querySelector(selector);
		if (window.divmoveflag) {
			var mx = e.movementX;
			var my = e.movementY;
			var x = thisz.offsetLeft;
			var y = thisz.offsetTop;
			thisz.style.left = x + mx + "px";
			thisz.style.position = "absolute";
			thisz.style.top = y + my + "px";
		}
	}
	document.onmouseup = function (e) {
		var thisz = document.querySelector(selector);
//		if (callback != null && !callback()) {
////			dragBack();
//		}
		if (callback != null) {
			var ce = {x:e.pageX,y:e.pageY,style:thisz.style,event:e};
			callback(ce);
		}
		if(thisz!=null)
			thisz.parentElement.removeChild(thisz);
		document.onmouseup = function(){};
		document.onmousemove = function(){};
		window.divmoveflag = false;
	}
//	document.querySelector(selector).onmouseup = function (e) {
//		if (callback != null && !callback()) {
//			dragBack();
//		}
//		this.parentElement.removeChild(this);
//		window.divmoveflag = false;
//	}
//	document.querySelector(selector).onmouseout = function (e) {
//		if (callback != null && !callback()) {
//			dragBack();
//		}
//		this.parentElement.removeChild(this);
//		window.divmoveflag = false;
//	}
}

/**
 * js为元素添加拖动事件
 */
function setDrag(selector, callback, outwin) {
	window.dragsel = selector;
	if(outwin != true)
		outwin = false;
		console.log(outwin)
	document.querySelector(selector).onmousedown = function (e) {
		window.divmoveflag = true;
		window.rx = this.offsetLeft;
		window.ry = this.offsetTop;
		var thisz = this;
		document.onmouseup = function (e) {
			var thisz = document.querySelector(selector);
			if (callback != null) {
				var ce = {x:e.pageX,y:e.pageY,style:thisz.style,event:e};
				callback(ce);
			}
			document.onmouseup = function(){};
			window.divmoveflag = false;
		}
		document.onmousemove = function (e) {
			var thisz = document.querySelector(selector);
			if (window.divmoveflag) {
				var mx = e.movementX;
				var my = e.movementY;
				var x = thisz.offsetLeft;
				var y = thisz.offsetTop;
				if(!outwin){
					
					if(x+mx<0)
						mx = -x;
					if(y+my<0)
						my = -y;
				}
				thisz.style.left = x + mx + "px";
				thisz.style.position = "absolute";
				thisz.style.top = y + my + "px";
			}
		}
	}
}

//function dragBack() {
//	var dom = document.querySelector(window.dragsel);
//	dom.style.left = window.rx + "px";
//	dom.style.top = window.ry + "px";
//}
/**
 * 使两个元素样式完全一致
 */
function addStyle(dom,old){
	var liStyle = window.getComputedStyle(old,null);
	for(key in dom.style){
		dom.style[key] = liStyle[key];
	}
	if(old.children.length>0){
		for(i=0;i<old.children.length;i++){
			addStyle(dom.children[i],old.children[i]);
		}
	}
}

