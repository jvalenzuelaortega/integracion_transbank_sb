//JS principal
$(document).ready(function () {
	var amountDefault = 1000;
	var mySession = "mi-id-de-sesion";
	var buyOrder = Math.floor((Math.random() * 1000000000) + 1);
	
	$('#sessionIdInpt').val(mySession);
	$('#buyOrderInpt').val(buyOrder);
	$('#montoInpt').val(amountDefault);
	
	$('#comprarBtn').prop('disabled', true);
	
	$('#checkBuy').click(function() {
		if($("#checkBuy").is(':checked')){
		    $('#comprarBtn').prop('disabled', false);
		}else{
		    $('#comprarBtn').prop('disabled', true);
		}
	});
	
	
})



