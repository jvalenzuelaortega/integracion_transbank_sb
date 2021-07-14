package cl.example.integration.controller;

import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayNormal;
import cl.transbank.webpay.configuration.Configuration;
import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetailOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView getInit(){
        ModelAndView mav = new ModelAndView("index");

        double amount = 1000;
        String session = "mySession";
        String buyOrder = String.valueOf(Math.abs(new Random().nextLong()));
        String urlReturn = "http://localhost:8080/return";
        String urlFinal = "http://localhost:8080/final";

        WsInitTransactionOutput initResult = new WsInitTransactionOutput();

        try {
            WebpayNormal transaction = new Webpay(Configuration.forTestingWebpayPlusNormal()).getNormalTransaction();
            initResult = transaction.initTransaction(amount, session, buyOrder, urlReturn, urlFinal);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = initResult.getUrl();
        String token = initResult.getToken();

        mav.addObject("formAction", url);
        mav.addObject("tokenWs", token);
        mav.addObject("amount", amount);
        mav.addObject("buyOrder", buyOrder);

        return mav;
    }

    @PostMapping("/return")
    public ModelAndView getReturn(@RequestParam(name = "token_ws") String tokenWs){
        ModelAndView mav = new ModelAndView("return");
        TransactionResultOutput result = new TransactionResultOutput();
        try {
            WebpayNormal transaction = new Webpay(Configuration.forTestingWebpayPlusNormal()).getNormalTransaction();
            result = transaction.getTransactionResult(tokenWs);
            WsTransactionDetailOutput output = result.getDetailOutput().get(0);

            if(output.getResponseCode() == 0) {
                String urlRedirection = result.getUrlRedirection();
                String responseCode = String.valueOf(output.getResponseCode());
                String amount = output.getAmount().toString();
                String authorizationCode = output.getAuthorizationCode();

                mav.addObject("urlRedireccion", urlRedirection);
                mav.addObject("responseCode", responseCode);
                mav.addObject("amount", amount);
                mav.addObject("authorizationCode", authorizationCode);
                mav.addObject("tokenWs", tokenWs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mav;
    }

    @PostMapping("/final")
    public ModelAndView getFinal(){
        ModelAndView mav = new ModelAndView("final");
        return mav;
    }

}
