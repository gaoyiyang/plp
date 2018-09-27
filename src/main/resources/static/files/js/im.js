function init(user) {
	window._wsobj = new WebSocket("ws://140.143.9.46:8866/ws/" + user.id + "/" + user.username);
	window.sessionStorage.setItem("_wsobj_url", "ws://140.143.9.46:8866/ws/" + user.id + "/" + user.username);
	_wsobj.onopen = function() {
		console.log("连接成功");
	}
	_wsobj.onmessage = function(msg) {
		var obj = JSON.parse(msg.data);
		if(obj.type=="byheart"){
			reconnect();
		}else{
			alert(obj.message);
		}
	}
	_wsobj.onclose = function() {
		reconnect();
	}
	if(window._wsHeart!=null)
		clearInterval(window._wsHeart);
	window._wsHeart = setInterval(function() {
		var obj = new Object();
		obj.type = "heart";
		obj.time = new Date().getTime();
		window._wsobj.send(JSON.stringify(obj));
		reconnect();
	}, 10000);
}

function reconnect() {
	if ((window._wsobj == null || window._wsobj.readyState==3) && window.sessionStorage.getItem("_wsobj_url") != null) {
		window._wsobj = new WebSocket(window.sessionStorage.getItem("_wsobj_url"));
		_wsobj.onopen = function() {
			console.log("连接成功");
		}
		_wsobj.onmessage = function(msg) {
			var obj = JSON.parse(msg.data);
			if(obj.type=="byheart"){
				reconnect();
			}else{
				alert(obj.message);
			}
		}
		_wsobj.onclose = function() {
			reconnect();
		}
		if(window._wsHeart!=null)
			clearInterval(window._wsHeart);
		window._wsHeart = setInterval(function() {
			var obj = new Object();
			obj.type = "heart";
			obj.time = new Date().getTime();
			window._wsobj.send(JSON.stringify(obj));
			reconnect();
		}, 10000);
	}
}

function sendMsg(msg,to){
	var obj = new Object();
	obj.type = "send";
	obj.to = to;
	obj.message = msg;
	window._wsobj.send(JSON.stringify(obj));
}

window.onload = function() {
	reconnect();
}