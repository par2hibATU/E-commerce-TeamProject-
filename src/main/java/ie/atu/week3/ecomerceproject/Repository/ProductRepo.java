package ie.atu.week3.ecomerceproject.Repository;

import ie.atu.week3.ecomerceproject.DTO.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
    List<Product> findByName(String name);

    List<Product> findByAvailable(boolean available);
}
