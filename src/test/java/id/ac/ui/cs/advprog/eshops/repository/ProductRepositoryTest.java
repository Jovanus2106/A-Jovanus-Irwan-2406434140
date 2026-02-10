package id.ac.ui.cs.advprog.eshops.repository;

import id.ac.ui.cs.advprog.eshops.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());

    }
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testDeletebyId(){
        Product product= new Product();
        product.setProductId("123");
        product.setProductName("Produk A");
        product.setProductQuantity(10);
        productRepository.create(product);
        productRepository.deleteById("123");
        Iterator<Product>iterator= productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testUpdateProduct(){

        Product product= new Product();
        product.setProductId("123");
        product.setProductName("Produk A");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updated= new Product();
        updated.setProductId("123");
        updated.setProductQuantity(20);
        updated.setProductName("Produk Baru");
        Product result= productRepository.update(updated);

        assertNotNull(result);
        assertEquals("Produk Baru", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }
    @Test
    void testFindById(){
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Produk A");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product found= productRepository.findById("123");
        assertNotNull(found);
        assertEquals("Produk A",product.getProductName());
    }

    @Test
    void testFindByIdNotFound() {
        Product product = new Product();
        product.setProductId("id-ada");
        product.setProductName("Produk Ada");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product result = productRepository.findById("id-tidak-ada");

        assertNull(result);
    }

    @Test
    void testUpdateProductNotFound() {
        Product existingProduct = new Product();
        existingProduct.setProductId("id-ada");
        existingProduct.setProductName("Produk Lama");
        existingProduct.setProductQuantity(10);
        productRepository.create(existingProduct);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("id-tidak-ada");
        updatedProduct.setProductName("Produk Baru");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.update(updatedProduct);

        assertNull(result);
    }

}