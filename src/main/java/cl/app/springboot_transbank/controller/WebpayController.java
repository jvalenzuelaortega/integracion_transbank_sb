package cl.app.springboot_transbank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetailOutput;

import cl.app.springboot_transbank.model.Purchase;
import cl.app.springboot_transbank.properties.WebpayDataProperties;
import cl.app.springboot_transbank.service.WebpayNormalService;

/**
 * La clase WebpayController.
 */
@Controller
public class WebpayController {

	/** la constante LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(WebpayController.class);

	/** Inyeccion de la dependecia ("Cableado automatico" de la clase)*/
	@Autowired
	WebpayDataProperties webpayDataProperties;
	
	/** Inyeccion de la dependecia ("Cableado automatico" de la clase)*/
	@Autowired
	WebpayNormalService webpayNormalService;
	
	/**
	 * Obtiene la vista webpay.html.
	 *
	 * @param purchase para la clase compra
	 * @return la vista que inicia la compra con transbank
	 */
	@PostMapping("/webpay")
	public ModelAndView getIndex(@ModelAttribute Purchase purchase) {
		ModelAndView mav = new ModelAndView("webpay");
		
		double amount = purchase.getAmount();
		String session = purchase.getSessionId();
		String buyOrder = purchase.getBuyOrder();
		
		LOGGER.info("Iniciando transaccion en el servicio de Transbank");
		
		//Se inicia la transaccion
		WsInitTransactionOutput initResult = webpayNormalService.initResult(amount, session, buyOrder, 
				webpayDataProperties.getUrlReturn(),webpayDataProperties.getUrlFinal());
		
		//Valores retornados del servicio
		String urlFormAction = initResult.getUrl();
		String tokeWs = initResult.getToken();

		LOGGER.info("Se ha extraido la url y el token de Transbank");
		
		//Valores que se enviaran a la vista webpay
		mav.addObject("formAction", urlFormAction);
		mav.addObject("tokenWs", tokeWs);
		mav.addObject("amopunt", amount);
		mav.addObject("buyOrder", buyOrder);

		LOGGER.info("Retornando vista de pago");
		
		return mav;
	}

	/**
	 * Obtiene la vista return.html
	 *
	 * @param envia el token generado en el servicio
	 * @return la vista de retorno
	 */
	@PostMapping("/return")
	public ModelAndView getReturn(@RequestParam(name = "token_ws") String tokenWs) {
		ModelAndView mav = new ModelAndView("return");

		LOGGER.info("Iniciando compra en el servicio de Transbank");
		
		//Se inicia el resultado del servicio
		TransactionResultOutput result = webpayNormalService.result(tokenWs);
		
		//Obtiene el detalle de la compra
		WsTransactionDetailOutput output = webpayNormalService.output(result);
		
		LOGGER.info("Validando respuesta de transbank...");
		//Valida que el codigo de respuesta sea 0 (el valido para continuar)
		if(output.getResponseCode() == 0) {
			String urlRedirection = result.getUrlRedirection();
			String responseCode = String.valueOf(output.getResponseCode());
			String amount = output.getAmount().toString();
			String authorizationCode = output.getAuthorizationCode();
			
			LOGGER.info("Codigo de respuesta de transbank : " + output.getResponseCode() + "... Exitoso");
			//Valores que se enviaran a la vista de retorno
			mav.addObject("urlRedireccion", urlRedirection);
			mav.addObject("responseCode", responseCode);
			mav.addObject("amount", amount);
			mav.addObject("authorizationCode", authorizationCode);
		}

		
		mav.addObject("tokenWs", tokenWs);

		LOGGER.info("Retornando vista de retorno");
		
		return mav;
	}

	/**
	 * Obtiene la vista final
	 *
	 * @return final.html
	 */
	@PostMapping("/final")
	public ModelAndView getFinal() {
		ModelAndView mav = new ModelAndView("final");
		
		LOGGER.info("Retornando vista final");
		
		return mav;
	}

}
