$(document).ready(function() {
	var w = $("div.banner-content").css("width");
	var h = $("div.banner-content").css("height");
	$(".banner li *").css({
			"width": w,
			"height": h
		}

	);
	w = parseFloat(w);
	h = parseFloat(h);
	var num = $(".banner-content ul.banner li").length;

	$("ul.banner").css({
			"width": parseFloat(w) * num + "px",
			"height": h
		}

	);
	setBanner();
	function setBanner() {
		var x = $("ul.banner").css("margin-left");
		x = parseFloat(x);
		var id = $(".banner li.active").attr("banner-id") * 1;
		$(".banner li.active").removeClass("active");
		$(".banner li[banner-id='" + (id + 1) + "']").addClass("active");
		$("ul.banner").animate({
				"margin-left": x - w + "px"
			}, 5000, null,
			function() {
				if ($(".banner li.active").attr("banner-id") * 1 == 4) {
					$(".banner li.active").removeClass("active");
					$(".banner li[banner-id='1']").addClass("active");
					$("ul.banner").css("margin-left", 0 - w + "px");
				}
				setBanner();
			}

		);
	}
})
