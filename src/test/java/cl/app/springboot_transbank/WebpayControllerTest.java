package cl.app.springboot_transbank;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cl.app.springboot_transbank.controller.MainController;
import cl.app.springboot_transbank.controller.WebpayController;

@SpringBootTest
public class WebpayControllerTest {
	
	@Autowired
	private WebpayController webpayController;
	
	@Autowired
	private MainController mainController;
	
	@Test
	public void contexLoads() throws Exception{
		assertThat(webpayController).isNotNull();
		assertThat(mainController).isNotNull();
	}
}
