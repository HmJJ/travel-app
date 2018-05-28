
/**
 * Init
 */
$(document).ready(function() {

	$(":text").each(function() {
		var control = $(this);
		var params  = $(this).attr("validation");
		if (!$.isUndefined(params)) {
			$.each($.trim(params).replace(/\s+/g, " ").split(" "), function(i, n) {
				if ($.trim(n) == "number") {
					$(control).blur(function() {
						control.val($.trim(control.val()));
						if (!$.isEmpty(control.val())) {
							var _number = control.val().replace(/\,/g, '');
							if(!$.isEmpty(_number) && !isNaN(_number)) {
								_number = Number(_number).toFixed(1);
								$(control).val($.format($.trim(_number), 3, ','));
							}
						}
					});

					$(control).focus(function() {
						control.val($.trim(control.val()));
						if (!$.isEmpty(control.val())) {
							control.val(control.val().replace(/\,/g, ''));
						}
					});
				}
				
				if ($.trim(n) == "integer") {
					$(control).blur(function() {
						control.val($.trim(control.val()));
						if (!$.isEmpty(control.val())) {
							var _number = control.val().replace(/\,/g, '');
							if(!$.isEmpty(_number) && !isNaN(_number)) {
								_number = Number(_number).toFixed(0);
								$(control).val($.format($.trim(_number), 3, ','));
							}
						}
					});

					$(control).focus(function() {
						control.val($.trim(control.val()));
						if (!$.isEmpty(control.val())) {
							control.val(control.val().replace(/\,/g, ''));
						}
					});
				}
			});
		}
	});
	
});

/**
 * 
 * @returns {Form Id}
 */
function validation(formId) {
	
	// default return value TRUE
	var _retval = true;

	// disabled all button
	$(":button, :submit").attr("disabled", "disabled");

	// trim all input
	$("input, textarea").each(function() {
		
		// readonly and disabled
		if ($.isReadonly(this) || $.isDisabled(this)) {
			return ;
		}
		
		$(this).val($.trim($(this).val()));
	});
	
	// readonly all input
	$(":input").attr("readonly", "readonly");
	
	// hide all tip
	$("[data-toggle=popover]").popover('hide');
	
	var controls;
	if (formId != null && formId != '') {
		controls = $("#" + formId).find(":input");
	} else {
		controls = $(":input");
	}
	
	/**
	 * check input\textarea\select
	 */
	controls.each(function() {
		
		// readonly and disabled
		if (/*$.isReadonly(this) || */$.isDisabled(this)) {
			return ;
		}
		
		var control = $(this);
		var params  = $(this).attr("validation");
		if (!$.isUndefined(params)) {

			// remove all alert class
			$(this).removeClass("alert-info");
			$(this).removeClass("alert-warning");
			$(this).removeClass("alert-danger");

			// remove tips
			$(this).popover("destroy");

			// default: success, content: ""
			$(this).addClass("alert-success");
			$(this).attr("data-toggle", "popover");
			$(this).attr("data-content", "");

			$.each($.trim(params).replace(/\s+/g, " ").split(" "), function(i, n) {

				var _arr = new Array([$.trim(n), $(control).attr("type")]);
				if ($.inCollection(_arr, "required") && !required(control)) {
					_retval = false;
				}
				if ($.inCollection(_arr, "number")   && !number(control)  ) {
					_retval = false;
				}
				if ($.inCollection(_arr, "integer")  && !integer(control) ) {
					_retval = false;
				}
				if ($.inCollection(_arr, 'email')    && !email(control)   ) {
					_retval = false;
				}
				if ($.inCollection(_arr, 'phone')    && !phone(control)   ) {
					_retval = false;
				}
				if (!_retval && $(control).attr("type") == 'text') {
					if ($.trim(n) == "number") {
						control.val(control.val().replace(/\,/g, ''));
						control.val($.format($.trim(control.val()), 3, ','));	
					}
				}
			});
		}
	});
	
	return _retval;
}

function required(object) {
	
	if ($(object).is('input')) {
		if ($.inCollection(new Array(["text", "password", "number", "email", "file"]), $(object).attr("type"))) {
			if ($.isEmpty($(object).val())) {
				$(object).addClass("alert-warning");
				$(object).attr("data-content", $(object).attr("data-content") + " required");
				return false;
			}
		}
		if ($(object).attr("type") == 'radio') {
			if ($.isEmpty($("input:radio[name='"+$(object).attr("name")+"']:checked").val())) {
				$(object).addClass("alert-warning");
				$(object).attr("data-content", " required");
				return false;
			}
		}
		if ($(object).attr("type") == 'checkbox') {
			if ($.isEmpty($("input:checkbox[name='"+$(object).attr("name")+"']:checked").val())) {
				$(object).addClass("alert-warning");
				$(object).attr("data-content", " required");
				return false;
			}
		}
	}
	
	if ($(object).is('textarea')) {
		if ($.isEmpty($(object).val())) {
			$(object).addClass("alert-warning");
			$(object).attr("data-content", $(object).attr("data-content") + " required");
			return false;
		}
	}
	
	if ($(object).is('select')) {
		if ($.isEmpty($(object).find("option:selected").val())) {
			$(object).addClass("alert-warning");
			$(object).attr("data-content", $(object).attr("data-content") + " required");
			return false;
		}
	}
	
	return true;
}

function number(object) {
	if ($(object).is('input')) {
		if ($.inCollection(new Array(["text", "number"]), $(object).attr("type"))) {
			$(object).val($(object).val().replace(/\,/g, ''));
			if (!$.isEmpty($(object).val()) && isNaN($(object).val())) {
				$(object).addClass("alert-warning");
				$(object).attr("data-content", $(object).attr("data-content") + " number");
				return false;
			}
		}
	}
	return true;
}

function integer(object) {
	if ($(object).is('input')) {
		if ($.inCollection(new Array(["text", "integer"]), $(object).attr("type"))) {
			$(object).val($(object).val().replace(/\,/g, ''));
			if (!$.isEmpty($(object).val()) && isNaN($(object).val())) {
				$(object).addClass("alert-warning");
				$(object).attr("data-content", $(object).attr("data-content") + " integer");
				return false;
			}
		}
	}
	return true;
}

function email(object) {
	if ($(object).is('input')) {
		if ($.inCollection(new Array(["text", "email"]), $(object).attr("type"))) {
			if (!$.isEmpty($(object).val()) && !$.isEmail($(object).val())) {
				$(object).addClass("alert-warning");
				$(object).attr("data-content", $(object).attr("data-content") + " email");
				return false;
			}
		}
	}
	return true;
}

function phone(object) {
	if ($(object).is('input')) {
		if ($.inCollection(new Array(["text", "phone"]), $(object).attr("type"))) {
			if (!$.isEmpty($(object).val()) && !$.isPhone($(object).val())) {
				$(object).addClass("alert-warning");
				$(object).attr("data-content", $(object).attr("data-content") + " phone");
				return false;
			}
		}
	}
	return true;
}