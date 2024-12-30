package ie.atu.week3.ecomerceproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.atu.week3.ecomerceproject.DTO.Product;
import ie.atu.week3.ecomerceproject.Repository.ProductRepo;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRestClientBuilderBeanPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ObjectMapper objectMapper;


    @AfterEach
    void tearDown() {
        productRepo.deleteAll(); // Clean up after each test
    }

    @Test
    void testAddProduct() throws Exception {
        Product product = new Product("1L", "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("apple"))
                .andExpect(jsonPath("$.description").value("Riped Apple"))
                .andExpect(jsonPath("$.price").value(2.00))
                .andExpect(jsonPath("$.category").value("Fruits"))
                .andExpect(jsonPath("$.releaseDate").value("2010-12-05"))
                .andExpect(jsonPath("$.available").value("true"))
                .andExpect(jsonPath("$.quantity").value(50));

        Optional<Product> savedProduct = productRepo.findByName("apple").stream().findFirst();
        assertThat(savedProduct).isPresent();
        assertThat(savedProduct.get().getPrice()).isEqualTo(2.00);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product prod1 = new Product("1L", "apple", "Riped Apple", 2.00, "Fruits", "2010-12-05", "true", 50);
        Product prod2 = new Product("2L", "Banana", "Red Banana", 8.00, "Fruits", "2019-08-20", "true", 80);

        productRepo.save(prod1);
        productRepo.save(prod2);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("apple"))
                .andExpect(jsonPath("$[1].name").value("Banana"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Product product = new Product(null, "Apple", "Fresh Apple", 1.00, "Fruits", "2022-01-01", "true", 50);
        Product savedProduct = productRepo.save(product);

        mockMvc.perform(delete("/products/product/" + savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Product with ID " + savedProduct.getId() + " is Deleted"));

        // Verify product is deleted from the database
        Optional<Product> deletedProduct = productRepo.findById(savedProduct.getId());
        assertThat(deletedProduct).isNotPresent();
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = new Product(null, "Apple", "Fresh Apple", 1.00, "Fruits", "2022-01-01", "true", 50);
        Product savedProduct = productRepo.save(product);

        Product updatedProduct = new Product(savedProduct.getId(), "Banana", "Red Banana", 8.00, "Fruits", "2019-08-20", "true", 80);
        mockMvc.perform(put("/products/product/id/" + savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.description").value("Red Banana"))
                .andExpect(jsonPath("$.price").value(8.00))
                .andExpect(jsonPath("$.category").value("Fruits"))
                .andExpect(jsonPath("$.releaseDate").value("2019-08-20"))
                .andExpect(jsonPath("$.available").value("true"))
                .andExpect(jsonPath("$.quantity").value(80));

        Product updatedProductDb = productRepo.findById(savedProduct.getId()).orElseThrow();
        assertThat(updatedProductDb.getName()).isEqualTo("Banana");
        assertThat(updatedProductDb.getDescription()).isEqualTo("Red Banana");
        assertThat(updatedProductDb.getPrice()).isEqualTo(8.00);
    }
}
