package ca.gbc.productservice.service;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@slf4j
public class ProductServiceImpl implements ProductService {
    @Override
    public ProductResponse createProduct(ProductRequest ProductRequest) {
        return null;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return null;
    }

    @Override
    public String updateProduct(String id, ProductRequest ProductRequest) {
        return null;
    }

    @Override
    public void deleteProduct(String id) {

    }
}
