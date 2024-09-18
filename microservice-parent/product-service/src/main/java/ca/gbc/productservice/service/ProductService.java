package ca.gbc.productservice.service;


import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest ProductRequest);
    List<ProductResponse> getAllProducts();
    String updateProduct(String id,ProductRequest ProductRequest);
    void deleteProduct(String id);


}


