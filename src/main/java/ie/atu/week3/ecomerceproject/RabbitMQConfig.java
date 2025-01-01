package ie.atu.week3.ecomerceproject;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String PRODUCT_QUEUE = "product.queue";
    public static final String PRODUCT_EXCHANGE = "product.exchange";
    public static final String PRODUCT_ROUTING_KEY = "product.routing.key";

    @Bean
    public Queue productQueue() {
        return new Queue(PRODUCT_QUEUE, true);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(PRODUCT_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue productQueue, TopicExchange exchange) {
        return BindingBuilder.bind(productQueue).to(exchange).with(PRODUCT_ROUTING_KEY);
    }
}
