package ie.atu.week3.ecomerceproject.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.atu.week3.ecomerceproject.DTO.Product;
import ie.atu.week3.ecomerceproject.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "consumer.enabled", havingValue = "true")
public class ProductConsumer {
    @RabbitListener(queues = "productQueue")
    public void processProductMessage(Product product) {
        System.out.println("Received message: " + product.toString());
    }
}