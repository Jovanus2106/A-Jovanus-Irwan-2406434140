// penambahan unit test pada product service
package id.ac.ui.cs.advprog.eshops.service;
// udah 100 persen juga
import id.ac.ui.cs.advprog.eshops.model.Product;
import id.ac.ui.cs.advprog.eshops.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product dummyProduct;

    @BeforeEach
    void setUp() {
        dummyProduct = new Product();
        dummyProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        dummyProduct.setProductName("Sampo Cap Bambang");
        dummyProduct.setProductQuantity(100);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(dummyProduct)).thenReturn(dummyProduct);

        Product savedProduct = productService.create(dummyProduct);

        assertNotNull(savedProduct);
        assertEquals(dummyProduct.getProductId(), savedProduct.getProductId());

        verify(productRepository, times(1)).create(dummyProduct);
    }

    @Test
    void testFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(dummyProduct);
        Iterator<Product> iterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> allProducts = productService.findAll();

        assertFalse(allProducts.isEmpty());
        assertEquals(1, allProducts.size());
        assertEquals(dummyProduct.getProductName(), allProducts.get(0).getProductName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProductById() {
        String idToDelete = dummyProduct.getProductId();

        productService.deleteProductById(idToDelete);

        verify(productRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    void testFindById() {
        String targetId = dummyProduct.getProductId();

        when(productRepository.findById(targetId)).thenReturn(dummyProduct);

        Product foundProduct = productService.findById(targetId);

        assertNotNull(foundProduct);
        assertEquals(targetId, foundProduct.getProductId());
        assertEquals(dummyProduct.getProductName(), foundProduct.getProductName());

        verify(productRepository, times(1)).findById(targetId);
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.update(dummyProduct)).thenReturn(dummyProduct);

        Product updatedProduct = productService.update(dummyProduct);

        assertNotNull(updatedProduct);
        assertEquals(dummyProduct.getProductId(), updatedProduct.getProductId());

        verify(productRepository, times(1)).update(dummyProduct);
    }
}