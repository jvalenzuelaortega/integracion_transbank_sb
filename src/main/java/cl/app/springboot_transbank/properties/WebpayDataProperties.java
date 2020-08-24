package cl.app.springboot_transbank.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * La clase WebpayDataProperties.
 */
@Configuration
@PropertySource("classpath:webpay.properties")
public class WebpayDataProperties {
	
	/** La url de retorno extraida por properties. */
	@Value("${urlReturn}")
	private String urlReturn;
	
	/** La url final extraida por properties. */
	@Value("${urlFinal}")
	private String urlFinal;
	
	/**
	 * Gets la url de retorno.
	 *
	 * @return la url de retorno
	 */
	public String getUrlReturn() {
		return urlReturn;
	}
	
	/**
	 * Gets la url final.
	 *
	 * @return la url final
	 */
	public String getUrlFinal() {
		return urlFinal;
	}
	
	

}
