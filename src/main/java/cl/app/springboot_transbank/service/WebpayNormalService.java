package cl.app.springboot_transbank.service;

import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetailOutput;

/**
 * La Interface WebpayNormalService.
 */
public interface WebpayNormalService {

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
	public WsInitTransactionOutput initResult(double amount, String session, String buyOrder, String returnUrl, String finalUrl);
	
	/**
	 * Metodo que muestra el resultado de la operacion
	 *
	 * @param token el token extraido del inicio de la transaccion
	 * @return el resultado de la transaccion
	 */
	public TransactionResultOutput result(String token);
	
	/**
	 * Metodo que procesa el resultado de la transaccion exitosa
	 *
	 * @param result el objecto TransactionResultOutput
	 * @return el detalle del resultado
	 */
	public WsTransactionDetailOutput output(TransactionResultOutput result);
}
