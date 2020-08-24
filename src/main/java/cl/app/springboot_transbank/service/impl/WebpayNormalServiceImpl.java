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

// TODO: Auto-generated Javadoc
/**
 * The Class WebpayNormalServiceImpl.
 */
@Service
public class WebpayNormalServiceImpl implements WebpayNormalService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(WebpayNormalServiceImpl.class);

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

			LOGGER.info("Se ha iniciado la transaccion");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error al iniciar la transaccion");
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
			LOGGER.info("Se ha resuelto la transaccion");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("No se ha resuelto la transaccion");
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
			LOGGER.info("Se ha extraido el detalle la transaccion");
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.info("No se ha extraido el detalle la transaccion");
		}
		return output;
	}
}
