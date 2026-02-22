package id.ac.ui.cs.advprog.eshops.controller;
// udah 100 persen
import id.ac.ui.cs.advprog.eshops.model.Product;
import id.ac.ui.cs.advprog.eshops.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    // Membuat "mock" (objek tiruan) dari ProductService
    @Mock
    private ProductService productService;

    // Memasukkan mock ProductService ke dalam ProductController
    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        // Menyiapkan MockMvc untuk menyimulasikan request web ke controller
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        Product product = new Product();

        mockMvc.perform(post("/product/create").flashAttr("product", product))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        // Memastikan metode create pada service benar-benar dipanggil
        verify(productService).create(any(Product.class));
    }

    @Test
    void testProductListPage() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        // Mengatur perilaku mock: jika findAll() dipanggil, kembalikan list produk
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", products));
    }

    @Test
    void testDeleteProduct() throws Exception {
        String productId = "123";

        mockMvc.perform(post("/product/delete/" + productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        // Memastikan metode delete pada service dipanggil dengan ID yang benar
        verify(productService).deleteProductById(productId);
    }

    @Test
    void testEditProductPage() throws Exception {
        String productId = "123";
        Product product = new Product();
        when(productService.findById(productId)).thenReturn(product);

        mockMvc.perform(get("/product/edit/" + productId))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", product));
    }

    @Test
    void testEditProductPost() throws Exception {
        Product product = new Product();

        mockMvc.perform(post("/product/edit").flashAttr("product", product))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        // Memastikan metode update pada service dipanggil
        verify(productService).update(any(Product.class));
    }
}