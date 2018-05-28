
function show_loading() {
	$("#loading").show();
}

function jumpurl(url) {
	window.location.href = url;
	show_loading();
}

;(function($) {

	$.fn.warning = function (msg) {
		$(this).addClass("alert-warning");
		$(this).attr("data-content", msg);
	};
	
	$.extend({
		warning : function (entity, msg) {
			$(entity).addClass("alert-warning");
			$(entity).attr("data-content", msg);
		}
	});

	$.extend({
		isUndefined : function (entity) {
			return typeof(entity) == "undefined" || entity == null || entity == undefined;
		}
	});

	$.extend({
		verify : function (id, url) {
			$("#"+ id).attr("src", url + "?v=" + Math.random());
		}
	});
	
	$.extend({
		isEmpty : function (val) {
			return val == null || val.length < 1 || val == "";
		}
	});
	
	$.fn.isEmpty = function () {
		return $(this).val() == null || $(this).length < 1 || $(this).val() == "";
	};

	$.extend({
		isEmail : function (val) {
			var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			return reg.test(val);
		}
	});

	$.fn.isEmail = function () {
		var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		return reg.test($(this).val());
	};

	$.extend({
		isPhone : function (val) {
			var reg = /^(1[0-9][0-9])\d{8}$/;
			return reg.test(val);
		}
	});

	$.fn.isPhone = function () {
		var reg = /^(1[0-9][0-9])\d{8}$/;
		return reg.test($(this).val());
	};
	
	$.extend({
		isReadonly : function (entity) {
			return !($(entity).attr("readonly") == undefined);
		}
	});

	$.fn.isReadonly = function () {
		return !($(this).attr("readonly") == undefined);
	};

	$.extend({
		isDisabled : function (entity) {
			return !($(entity).attr("disabled") == undefined);
		}
	});

	$.fn.isDisabled = function () {
		return !($(this).attr("disabled") == undefined);
	};

	$.extend({
		getStyle : function (url) {
			if(!$.isEmpty($.trim(url))) {
				$("head").append("<link type='text/css' rel='stylesheet' href='" + $.trim(url) + "' />");
			}
		}
	});
	
	$.extend({
		inCollection : function (arr, element) {
			var _retval = false;
			$.each($.trim(arr).split(","), function(i, n) {
				if ($.trim(n) == $.trim(element))
					_retval = true;
			});
			return _retval;
		}
	});
	
	$.extend({
		format : function(str, step, splitor) {

			var prefix = "";
			var suffix = "";

			if (isNaN(str.substr(0, 1)) && str.substr(0, 1) == "-") {
				prefix = str.substr(0, 1);
				str = str.substr(1, str.length);
			}

			if (str.indexOf(".") > -1) {
				suffix = str.substr(str.indexOf("."), str.length);
				str = str.substr(0, str.indexOf("."));
			}
			
			str = str.toString();
			var len = str.length;

			if (len > step) {
				var l1 = len % step,
					l2 = parseInt(len / step),
					arr = [],
					first = str.substr(0, l1);
				if (first != '') {
					arr.push(first);
				}
				for ( var i = 0; i < l2; i++) {
					arr.push(str.substr(l1 + i * step, step));
				}
				str = arr.join(splitor);
			}
			return prefix + str + suffix;
		}
	});

	$.fn.slideLeftHide = function(speed, callback) {
		this.animate({
			width : "hide",
			paddingRight : "hide",
			paddingLeft : "hide",
			marginRight : "hide",
			marginLeft : "hide"
		}, speed, callback);
	};

	$.fn.slideLeftShow = function(speed, callback) {
		this.animate({
			width : "show",
			paddingLeft : "show",
			paddingRight : "show",
			marginLeft : "show",
			marginRight : "show"
		}, speed, callback);
	};

})(jQuery);