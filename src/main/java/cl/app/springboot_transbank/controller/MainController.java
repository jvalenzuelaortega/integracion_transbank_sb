package cl.app.springboot_transbank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cl.app.springboot_transbank.service.impl.WebpayNormalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetailOutput;

import cl.app.springboot_transbank.service.WebpayNormalService;

@Controller
public class MainController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	WebpayNormalService webpayNormalService;

	@GetMapping("/")
	public ModelAndView getIndex() {
		ModelAndView mav = new ModelAndView("index");

		double amount = 1000;
		String session = "mySession";
		String buyOrder = String.valueOf(Math.abs(new Random().nextLong()));

		String urlReturn = "http://localhost:8080/return";
		String urlFinal = "http://localhost:8080/final";

		List<String> getDataFromWebpay = getDataInitWebpay(100, session, buyOrder, urlReturn, urlFinal);
		
		String urlFormAction = getDataFromWebpay.get(0);
		String tokeWs = getDataFromWebpay.get(1);

		mav.addObject("formAction", urlFormAction);
		mav.addObject("tokenWs", tokeWs);
		mav.addObject("amopunt", amount);
		mav.addObject("buyOrder", buyOrder);

		return mav;
	}

	@PostMapping("/return")
	public ModelAndView getReturn(@RequestParam(name = "token_ws") String tokenWs) {
		ModelAndView mav = new ModelAndView("return");

		List<String> getDataReturnFromWebpay = getDataReturnWebPay(tokenWs);
		
		String urlRedirection = getDataReturnFromWebpay.get(0);
		String responseCode = getDataReturnFromWebpay.get(1);
		String amount = getDataReturnFromWebpay.get(2);
		String authorizationCode = getDataReturnFromWebpay.get(3);
		
		
		mav.addObject("urlRedireccion", urlRedirection);
		mav.addObject("responseCode", responseCode);
		mav.addObject("amount", amount);
		mav.addObject("authorizationCode", authorizationCode);
		
		mav.addObject("tokenWs", tokenWs);

		return mav;
	}

	@PostMapping("/final")
	public ModelAndView getFinal() {
		ModelAndView mav = new ModelAndView("final");

		return mav;
	}

	/*
	 * Metodos necesarios para retornar los valores a la vista
	 */

	// Metodo que extrae la URL y token para cargar Webpay
	private List<String> getDataInitWebpay(double amount, String session, String buyOrder, String returnUrl,
			String finalUrl) {

		List<String> initDataArray = new ArrayList<>();

		try {
			WsInitTransactionOutput initResult = webpayNormalService.initResult(amount, session, buyOrder, returnUrl,
					finalUrl);
			String url = initResult.getUrl();
			String token = initResult.getToken();

			initDataArray.add(url);
			initDataArray.add(token);

			LOGGER.info("Se han guardado los datos en el arreglo :" +initDataArray.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("No se han guardado los datos en el arreglo");
		}

		return initDataArray;
	}
	
	private List<String> getDataReturnWebPay(String token){
		List<String> returnDataArray = new ArrayList<>();
		
		try {
			TransactionResultOutput result = webpayNormalService.result(token);
			WsTransactionDetailOutput output = webpayNormalService.output(result);
			
			if(output.getResponseCode() == 0) {
				String urlRedirection = result.getUrlRedirection();
				String responseCode = String.valueOf(output.getResponseCode());
				String amount = output.getAmount().toString();
				String authorizationCode = output.getAuthorizationCode();
				
				returnDataArray.add(urlRedirection);
				returnDataArray.add(responseCode);
				returnDataArray.add(amount);
				returnDataArray.add(authorizationCode);

				LOGGER.info("Se han guardado los datos en el arreglo :" +returnDataArray.toString());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.info("No se han guardado los datos en el arreglo");
		}

		return returnDataArray;
	}

}
