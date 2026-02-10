package id.ac.ui.cs.advprog.eshops.repository;

import id.ac.ui.cs.advprog.eshops.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Repository
public class ProductRepository {
    private List<Product> productData= new ArrayList<>();
    public Product create (Product product){
        productData.add(product);
        return product;
    }
    public Iterator <Product> findAll(){
        return productData.iterator();
    }
    public void deleteById(String id) {
        Iterator<Product> iterator = productData.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }


    public Product findById(String productId){
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Product update(Product updatedProduct){
        for (Product product : productData) {
            if (product.getProductId().equals(updatedProduct.getProductId())) {
                product.setProductName(updatedProduct.getProductName());
                product.setProductQuantity(updatedProduct.getProductQuantity());
                return product;
            }
        }
        return null;
    }
}
