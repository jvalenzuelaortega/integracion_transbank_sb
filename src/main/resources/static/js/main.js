//JS de la vista principal
$(document).ready(function () {
	var amountDefault = 1000;
	var mySession = "mi-id-de-sesion";
	var buyOrder = Math.floor((Math.random() * 1000000000) + 1);
	
	//Se deja un id de session por defecto
	$('#sessionIdInpt').val(mySession);
	
	//Se genera un numero random para la orden de compra
	$('#buyOrderInpt').val(buyOrder);
	
	//Se deja un valor inicial por defecto. Se puede modificar en la vista
	$('#montoInpt').val(amountDefault);
	
	//Se desabilita el boton de compra
	$('#comprarBtn').prop('disabled', true);
	
	//Logica que permite habilitar el boton de compra, si el boton checked es presionado
	$('#checkBuy').click(function() {
		if($("#checkBuy").is(':checked')){
		    $('#comprarBtn').prop('disabled', false);
		}else{
		    $('#comprarBtn').prop('disabled', true);
		}
	});
	
})



