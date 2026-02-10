package id.ac.ui.cs.advprog.eshops.service;

import id.ac.ui.cs.advprog.eshops.model.Product;
import java.util.List;
public interface ProductService {
    public Product create (Product product);
    public List <Product> findAll();
    void deleteProductById(String id);
    Product findById(String productId);
    Product update(Product product);

}
