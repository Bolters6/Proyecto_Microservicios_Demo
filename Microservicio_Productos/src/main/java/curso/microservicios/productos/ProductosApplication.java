package curso.microservicios.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = {"com.example.libreria_commons.models"})
public class ProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductosApplication.class, args);
	}

}
