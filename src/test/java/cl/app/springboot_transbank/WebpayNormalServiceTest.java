package cl.app.springboot_transbank;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;

import cl.app.springboot_transbank.service.WebpayNormalService;

@SpringBootTest
public class WebpayNormalServiceTest {

	@Autowired
	WebpayNormalService webpayNormalService;
	
	@Test
	public void initTransaction() {
		double amount = 1000;
		String sessionId = "mi-id-session";
		String buyOrder = String.valueOf(Math.abs(new Random().nextLong()));
		String returnUrl = "https://callback/resultado/de/transaccion";
		String finalUrl = "https://callback/final/post/comprobante/webpay";
		
		WsInitTransactionOutput initResult = webpayNormalService.initResult(amount, sessionId, buyOrder, returnUrl, finalUrl);
		
		assertNotNull(initResult.getUrl());
		assertNotNull(initResult.getToken());

	}
	
}
