package curso.microservicios.items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@RibbonClient("servicio-productos")
@EnableEurekaClient
@EnableFeignClients
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class ItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemsApplication.class, args);
	}

}
