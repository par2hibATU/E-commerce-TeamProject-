package ie.atu.week3.ecomerceproject.Service;

import ie.atu.week3.ecomerceproject.DTO.Product;
import ie.atu.week3.ecomerceproject.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Product addProduct(Product product){
        return productRepo.save(product);
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

    public Product addproduct(Product product){
        return productRepo.save(product);
    }

    //Updates by ID
    public Product updateProduct(String id, Product product){
        Optional<Product> QueryProd = productRepo.findById(id);
        if(!QueryProd.isPresent()){
            throw new RuntimeException(("Product ID not Found...."));
        }
        Product existingProduct = QueryProd.get();
        existingProduct.setName(product.getName());
        existingProduct.setId(product.getId());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setReleaseDate(product.getReleaseDate());
        existingProduct.setAvailable(product.getAvailable());
        existingProduct.setQuantity(product.getQuantity());
        return productRepo.save(existingProduct);
    }
    //updates by Name
    public List<Product> updateProductByName(String name, Product product){
        List<Product> QueryProd = productRepo.findByName(name);
        if(!QueryProd.isEmpty()){
            throw new RuntimeException(("Product ID not Found...."));
        }
        for( Product existingProduct : QueryProd ){
            existingProduct.setName(product.getName());
            existingProduct.setId(product.getId());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setReleaseDate(product.getReleaseDate());
            existingProduct.setAvailable(product.getAvailable());
            existingProduct.setQuantity(product.getQuantity());
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
