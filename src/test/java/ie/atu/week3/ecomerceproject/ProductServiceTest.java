package ie.atu.week3.ecomerceproject;

import ie.atu.week3.ecomerceproject.DTO.Product;
import ie.atu.week3.ecomerceproject.Repository.ProductRepo;
import ie.atu.week3.ecomerceproject.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepo productRepo;

    @Test
    public void testGetAllProducts() {
        Product prod1 = new Product("1L", "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);
        Product prod2 = new Product("2L", "Banana", "Red Banana", 8.00, "Fruits", "2019-08-20", "true", 80);

        when(productRepo.findAll()).thenReturn(Arrays.asList(prod1, prod2));
        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());
        assertEquals(prod1.getName(), products.get(0).getName());
    }

    @Test
    public void testAddProduct(){
        Product prod = new Product(null, "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);
        Product saveProd = new Product("1L", "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);

        when(productRepo.save(prod)).thenReturn(saveProd);
        Product result = productService.addProduct(prod);
        assertNotNull(result.getId());
        assertEquals("apple", result.getName());
    }

    @Test
    public void testGetProductById(){
        Product mockProduct = new Product("1L", "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);
        when(productRepo.findById("1L")).thenReturn(Optional.of(mockProduct));

        Product result = productService.getProductById("1L");
        assertNotNull(result);
        assertEquals("apple", result.getName());
    }

    @Test
    public void testUpdateProduct(){
        Product existingProduct = new Product("1L", "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);
        Product updatedProduct = new Product("1L", "Banana", "Red Banana", 8.00, "Fruits", "2019-08-20", "true", 80);

        when(productRepo.findById("1L")).thenReturn(Optional.of(existingProduct));
        when(productRepo.save(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProduct("1L", updatedProduct);
        assertNotNull(result);
        assertEquals("Banana", result.getName());
    }

    @Test
    public void testDeleteProduct(){
        when(productRepo.findById("1L")).thenReturn(Optional.empty());
        String result = productService.deleteProduct("1L");
        assertEquals("Product with ID 1L Not Found", result);
        verify(productRepo, times(0)).deleteById("1L");
    }
}
