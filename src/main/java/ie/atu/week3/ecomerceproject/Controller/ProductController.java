package ie.atu.week3.ecomerceproject.Controller;


import ie.atu.week3.ecomerceproject.DTO.Product;
import ie.atu.week3.ecomerceproject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        Product addProd = productService.getProductById(id);
        return ResponseEntity.ok(addProd);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name){
        List<Product> prod = productService.getProductByName(name);
        return ResponseEntity.ok(prod);
    }

    @GetMapping("/{avail}")
    public ResponseEntity<List<Product>> getProductByAvailability(@PathVariable boolean available){
        List<Product> prod = productService.getProductByAvailability(available);
        return ResponseEntity.ok(prod);
    }

    @PutMapping("/product/id/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable String id, @RequestBody Product product){
        try{
            Product existingProd = productService.updateProduct(id, product);
            return ResponseEntity.ok(existingProd);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/name/{name}")
    public ResponseEntity<List<Product>> updateProductByName(@PathVariable String name, @RequestBody Product product){
        try{
            List<Product> existingProd = productService.updateProductByName(name, product);
            return ResponseEntity.ok(existingProd);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable String id){
        String result = productService.deleteProduct(id);
        if(result.contains("Not Found")){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }



}
