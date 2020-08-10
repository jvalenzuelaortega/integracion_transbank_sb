package cl.app.springboot_transbank.service;

import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetailOutput;

public interface WebpayNormalService {

	//Inicia Transaccion
	public WsInitTransactionOutput initResult(double amount, String session, String buyOrder, String returnUrl, String finalUrl);
	
	//Retorna transaccion 
	public TransactionResultOutput result(String token);
	
	//Muestra detalle de transaccion
	public WsTransactionDetailOutput output(TransactionResultOutput result);
}
