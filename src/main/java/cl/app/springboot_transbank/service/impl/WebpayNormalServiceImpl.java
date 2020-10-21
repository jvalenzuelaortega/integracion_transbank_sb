package cl.app.springboot_transbank.service.impl;

import cl.app.springboot_transbank.controller.WebpayController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetailOutput;

import cl.app.springboot_transbank.service.WebpayNormalService;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayNormal;
import cl.transbank.webpay.configuration.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class WebpayNormalServiceImpl.
 */
@Service
public class WebpayNormalServiceImpl implements WebpayNormalService {

	/** Constante para LOGS*/
	private static final Logger LOG = LogManager.getLogger(WebpayNormalServiceImpl.class);

	/**
	 * Metodo que inicia la transaccion
	 *
	 * @param amount el valor del producto
	 * @param session el id de session
	 * @param buyOrder la orden de compra
	 * @param returnUrl la url de retorno
	 * @param finalUrl la url final
	 * @return la url para iniciar el flujo y el token
	 */
	@Override
	public WsInitTransactionOutput initResult(double amount, String session, String buyOrder, String returnUrl,
			String finalUrl) {
		WsInitTransactionOutput initResult = new WsInitTransactionOutput();
		try {
			WebpayNormal transaction = new Webpay(Configuration.forTestingWebpayPlusNormal()).getNormalTransaction();
			initResult = transaction.initTransaction(amount, session, buyOrder, returnUrl, finalUrl);
			LOG.info("Valores retornados del servicio : " + initResult.getToken() +" | "+ initResult.getUrl());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error al iniciar transaccion :" + e.getMessage());
		}
		return initResult;
	}

	/**
	 * Metodo que muestra el resultado de la operacion
	 *
	 * @param token el token extraido del inicio de la transaccion
	 * @return el resultado de la transaccion
	 */
	@Override
	public TransactionResultOutput result(String token) {
		TransactionResultOutput result = new TransactionResultOutput();
		try {
			WebpayNormal transaction = new Webpay(Configuration.forTestingWebpayPlusNormal()).getNormalTransaction();
			result = transaction.getTransactionResult(token);
			LOG.info("Valores retornados del servicio");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error al retornar transaccion :" + e.getMessage());
		}
		return result;
	}

	/**
	 * Metodo que procesa el resultado de la transaccion exitosa
	 *
	 * @param result el objecto TransactionResultOutput
	 * @return el detalle del resultado
	 */
	@Override
	public WsTransactionDetailOutput output(TransactionResultOutput result) {
		WsTransactionDetailOutput output = new WsTransactionDetailOutput();
		try {
			output = result.getDetailOutput().get(0);
			LOG.info("Valores retornados del servicio : " + output.getResponseCode());
		}catch(Exception e) {
			e.printStackTrace();
			LOG.error("Error al extraer el detalle de la transaccion :" + e.getMessage());
		}
		return output;
	}
}
