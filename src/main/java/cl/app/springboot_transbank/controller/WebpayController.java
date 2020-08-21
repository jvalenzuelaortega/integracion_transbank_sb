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

@Controller
public class WebpayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebpayController.class);

	@Autowired
	WebpayDataProperties webpayDataProperties;
	
	@Autowired
	WebpayNormalService webpayNormalService;
	
	@PostMapping("/webpay")
	public ModelAndView getIndex(@ModelAttribute Purchase purchase) {
		ModelAndView mav = new ModelAndView("webpay");
		
		double amount = purchase.getAmount();
		String session = purchase.getSessionId();
		String buyOrder = purchase.getBuyOrder();
		
		LOGGER.info("Iniciando transaccion en el servicio de Transbank");
		
		WsInitTransactionOutput initResult = webpayNormalService.initResult(amount, session, buyOrder, 
				webpayDataProperties.getUrlReturn(),webpayDataProperties.getUrlFinal());
		
		String urlFormAction = initResult.getUrl();
		String tokeWs = initResult.getToken();

		LOGGER.info("Se ha extraido la url y el token de Transbank");
		
		//Valores que se enviaran a la vista inicial
		mav.addObject("formAction", urlFormAction);
		mav.addObject("tokenWs", tokeWs);
		mav.addObject("amopunt", amount);
		mav.addObject("buyOrder", buyOrder);

		LOGGER.info("Retornando vista de pago");
		
		return mav;
	}

	@PostMapping("/return")
	public ModelAndView getReturn(@RequestParam(name = "token_ws") String tokenWs) {
		ModelAndView mav = new ModelAndView("return");

		LOGGER.info("Iniciando compra en el servicio de Transbank");
		TransactionResultOutput result = webpayNormalService.result(tokenWs);
		WsTransactionDetailOutput output = webpayNormalService.output(result);
		
		LOGGER.info("Validando respuesta de transbank...");
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

	@PostMapping("/final")
	public ModelAndView getFinal() {
		ModelAndView mav = new ModelAndView("final");
		
		LOGGER.info("Retornando vista final");
		
		return mav;
	}

}
