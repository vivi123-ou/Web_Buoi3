package vn.edu.demo_spmvc.service;

import org.springframework.data.domain.Pageable;
import vn.edu.demo_spmvc.dto.PageResponseDTO;
import vn.edu.demo_spmvc.dto.ProductDTO;
import vn.edu.demo_spmvc.dto.ProductSearchDTO;
import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO dto);
    List<ProductDTO> getAll();
    void delete(Long id);
    PageResponseDTO<ProductDTO> search(ProductSearchDTO criteria, Pageable pageable);
}