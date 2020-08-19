package cl.app.springboot_transbank.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:webpay.properties")
public class WebpayDataProperties {
	
	@Value("${urlReturn}")
	private String urlReturn;
	
	@Value("${urlFinal}")
	private String urlFinal;
	
	public String getUrlReturn() {
		return urlReturn;
	}
	public String getUrlFinal() {
		return urlFinal;
	}
	
	

}
