package ie.atu.week3.ecomerceproject;

import ie.atu.week3.ecomerceproject.DTO.Product;
import ie.atu.week3.ecomerceproject.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProducts() throws Exception{
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddProduct() throws Exception{
        Product mockProduct = new Product("1L", "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);
        when(productService.addProduct(new Product(null, "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50))).thenReturn(mockProduct);

        String productJson = "{\"name\":\"apple\", \"description\":\"Riped Apple\", \"price\":2.00, \"category\": \"Fruits\", \"releaseDate\":\"2010-12-05\", \"available\": \"true\", \"quantity\": 50}";
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("apple"))
                .andExpect(jsonPath("$.description").value("Riped Apple"))
                .andExpect(jsonPath("$.price").value(2.00))
                .andExpect(jsonPath("$.category").value("Fruits"))
                .andExpect(jsonPath("$.releaseDate").value("2010-12-05"))
                .andExpect(jsonPath("$.available").value("true"))
                .andExpect(jsonPath("$.quantity").value(50));
    }

    @Test
    public void testGetProductById() throws Exception{
        Product mockProduct = new Product("1L", "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);
        when(productService.getProductById("1L")).thenReturn(mockProduct);
        mockMvc.perform(get("/products/product/1L"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("apple"))
                .andExpect(jsonPath("$.description").value("Riped Apple"))
                .andExpect(jsonPath("$.price").value(2.00))
                .andExpect(jsonPath("$.category").value("Fruits"))
                .andExpect(jsonPath("$.releaseDate").value("2010-12-05"))
                .andExpect(jsonPath("$.available").value("true"))
                .andExpect(jsonPath("$.quantity").value(50));
    }

    @Test
    public void testUpdateProductById() throws Exception{
        Product existingProduct = new Product("1L", "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);
        Product updatedProduct = new Product("1L", "Banana", "Red Banana", 8.00, "Fruits", "2019-08-20", "true", 80);


        when(productService.updateProduct("1L", updatedProduct)).thenReturn(updatedProduct);
        String productJson = "{\"id\":\"1L\", \"name\":\"Banana\", \"description\":\"Red Banana\", \"price\":8.00, \"category\": \"Fruits\", \"releaseDate\":\"2019-08-20\", \"available\": \"true\", \"quantity\": 80}";
        mockMvc.perform(put("/products/product/id/1L")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.description").value("Red Banana"))
                .andExpect(jsonPath("$.price").value(8.00))
                .andExpect(jsonPath("$.category").value("Fruits"))
                .andExpect(jsonPath("$.releaseDate").value("2019-08-20"))
                .andExpect(jsonPath("$.available").value("true"))
                .andExpect(jsonPath("$.quantity").value(80));

    }

    @Test
    public void testDeleteProductById() throws Exception{
        when(productService.deleteProduct("1L")).thenReturn("Product with ID 1L Not Found");
        mockMvc.perform(delete("/products/product/1L"))
                .andExpect(status().isNotFound());
    }


}
