
$.fn.formObject = function() {
	var $inputs = $(':input', this);
	var values = {};
	$inputs.each(function() {
		values[this.name] = $(this).val();
	});
	return values;
}
