package cl.app.springboot_transbank.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetailOutput;

import cl.app.springboot_transbank.service.WebpayNormalService;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayNormal;
import cl.transbank.webpay.configuration.Configuration;

@Service
public class WebpayNormalServiceImpl implements WebpayNormalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebpayNormalServiceImpl.class);

	@Override
	public WsInitTransactionOutput initResult(double amount, String session, String buyOrder, String returnUrl,
			String finalUrl) {
		// TODO Auto-generated method stub
		WsInitTransactionOutput initResult = new WsInitTransactionOutput();
		try {
			WebpayNormal transaction = new Webpay(Configuration.forTestingWebpayPlusNormal()).getNormalTransaction();
			initResult = transaction.initTransaction(amount, session, buyOrder, returnUrl, finalUrl);

			LOGGER.info("Se ha iniciado la transaccion");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.info("Error al iniciar la transaccion");
		}
		return initResult;
	}

	@Override
	public TransactionResultOutput result(String token) {
		// TODO Auto-generated method stub
		TransactionResultOutput result = new TransactionResultOutput();
		try {
			WebpayNormal transaction = new Webpay(Configuration.forTestingWebpayPlusNormal()).getNormalTransaction();
			result = transaction.getTransactionResult(token);
			LOGGER.info("Se ha resuelto la transaccion");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.info("No se ha resuelto la transaccion");
		}
		return result;
	}

	@Override
	public WsTransactionDetailOutput output(TransactionResultOutput result) {
		// TODO Auto-generated method stub
		WsTransactionDetailOutput output = new WsTransactionDetailOutput();
		try {
			output = result.getDetailOutput().get(0);
			LOGGER.info("Se ha extraido el detalle la transaccion");
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.info("No se ha extraido el detalle la transaccion");
		}
		return output;
	}
}
