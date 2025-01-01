package ie.atu.week3.ecomerceproject.Consumer;

import ie.atu.week3.ecomerceproject.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {
    @RabbitListener(queues = RabbitMQConfig.PRODUCT_QUEUE)
    public void listen(String message) {
        System.out.println("Received message from RabbitMQ: " + message);
    }
}
