package cl.app.springboot_transbank.service.impl;

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

	@Override
	public WsInitTransactionOutput initResult(double amount, String session, String buyOrder, String returnUrl,
			String finalUrl) {
		// TODO Auto-generated method stub
		WsInitTransactionOutput initResult = new WsInitTransactionOutput();
		try {
			WebpayNormal transaction = new Webpay(Configuration.forTestingWebpayPlusNormal()).getNormalTransaction();
			initResult = transaction.initTransaction(amount, session, buyOrder, returnUrl, finalUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			WsTransactionDetailOutput output = result.getDetailOutput().get(0);
			
			if(output.getResponseCode() == 0) {
				return result;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public WsTransactionDetailOutput output(TransactionResultOutput result) {
		// TODO Auto-generated method stub
		WsTransactionDetailOutput output = new WsTransactionDetailOutput();
		try {
			output = result.getDetailOutput().get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return output;
	}
}
