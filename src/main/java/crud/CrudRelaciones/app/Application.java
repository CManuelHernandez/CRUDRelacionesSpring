package crud.CrudRelaciones.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"crud.CrudRelaciones.content"

})

@EnableJpaRepositories(basePackages = {"crud.CrudRelaciones.content"})

@EntityScan("crud.CrudRelaciones.content")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
