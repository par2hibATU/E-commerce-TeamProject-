package ie.atu.week3.ecomerceproject.Service;

import ie.atu.week3.ecomerceproject.DTO.Product;
import ie.atu.week3.ecomerceproject.RabbitMQConfig;
import ie.atu.week3.ecomerceproject.Repository.ProductRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProductService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Product addProduct(Product product){

        Product savedProduct = productRepo.save(product);

        // Publish to RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, product);

        return savedProduct;
    }

    public List<Product> getAllProducts(){

        return productRepo.findAll();
    }

    public List<Product> getProductByName(String name){
        return productRepo.findByName(name);
    }

    public Product getProductById(String id){
        return productRepo.findById(id).get();
    }

    public List<Product> getProductByAvailability(String available){
        return productRepo.findByAvailable(available);
    }



    //Updates by ID
    public Product updateProduct(String id, Product product){
        Optional<Product> QueryProd = productRepo.findById(id);
        if(!QueryProd.isPresent()){
            throw new RuntimeException(("Product ID not Found...."));
        }
        Product existingProduct = QueryProd.get();
        existingProduct.setCustomerName(product.getCustomerName());
        existingProduct.setName(product.getName());
        existingProduct.setId(product.getId());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setReleaseDate(product.getReleaseDate());
        existingProduct.setAvailable(product.getAvailable());
        existingProduct.setQuantity(product.getQuantity());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, product);
        return productRepo.save(existingProduct);
    }
    //updates by Name
    public List<Product> updateProductByName(String name, Product product){
        List<Product> QueryProd = productRepo.findByName(name);
        if(!QueryProd.isEmpty()){
            throw new RuntimeException(("Product ID not Found...."));
        }
        for( Product existingProduct : QueryProd ){
            existingProduct.setCustomerName(product.getCustomerName());
            existingProduct.setName(product.getName());
            existingProduct.setId(product.getId());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setReleaseDate(product.getReleaseDate());
            existingProduct.setAvailable(product.getAvailable());
            existingProduct.setQuantity(product.getQuantity());
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, product);
            productRepo.save(existingProduct);
        }
        return QueryProd;
    }


    public String deleteProduct(String id){
        if(productRepo.findById(id).isPresent()){
            productRepo.deleteById(id);
            return "Product with ID " + id + " is Deleted";
        }
        return "Product with ID " + id + " Not Found";
    }
}
