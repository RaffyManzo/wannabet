package is.project.wannabet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WannabetApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WannabetApplication.class);
		app.run(args);
	}
}

