package sit.int204.actionback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ActionbackApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActionbackApplication.class, args);
    }


}
