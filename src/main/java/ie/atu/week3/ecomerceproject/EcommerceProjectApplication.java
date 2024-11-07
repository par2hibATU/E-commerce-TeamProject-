package ie.atu.week3.ecomerceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EcommerceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceProjectApplication.class, args);
    }

}
