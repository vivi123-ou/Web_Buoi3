package vn.edu.demo_spmvc.service;

import vn.edu.demo_spmvc.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO dto);
    List<ProductDTO> getAll();
    void delete(Long id);
}


