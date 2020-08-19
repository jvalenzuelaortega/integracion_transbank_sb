package cl.app.springboot_transbank;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:webpay.properties")
public class WebpayDataPropertiesTest {

	@Value("${urlReturn}")
    private String urlReturn;

    @Value("${urlFinal}")
    private String urlFinal;

    @Test
    public void loadPropertiesTest(){
        assertThat(urlReturn).isEqualTo("http://localhost:8080/return");
        assertThat(urlFinal).isEqualTo("http://localhost:8080/final");
    }
}
