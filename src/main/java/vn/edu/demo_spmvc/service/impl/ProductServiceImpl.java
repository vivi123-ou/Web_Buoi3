package vn.edu.demo_spmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.demo_spmvc.dto.PageResponseDTO;
import vn.edu.demo_spmvc.dto.ProductDTO;
import vn.edu.demo_spmvc.dto.ProductSearchDTO;
import vn.edu.demo_spmvc.entity.Product;
import vn.edu.demo_spmvc.mapper.ProductMapper;
import vn.edu.demo_spmvc.repository.ProductRepository;
import vn.edu.demo_spmvc.service.ProductService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public ProductDTO create(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        return ProductMapper.toDTO(repository.save(product));
    }

    @Override
    public List<ProductDTO> getAll() {
        return repository.findByDeletedFalse()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Product p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        boolean hasOrder = p.getOrderDetails() != null && !p.getOrderDetails().isEmpty();
        if (hasOrder) {
            throw new RuntimeException("Khong the xoa san pham da co trong don hang!");
        }
        p.setDeleted(true);
        repository.save(p);
    }

    @Override
    public PageResponseDTO<ProductDTO> search(ProductSearchDTO c, Pageable pageable) {
        Page<Product> page = repository.search(
                c.getName(), c.getMinPrice(), c.getMaxPrice(), c.getInStock(), pageable);
        List<ProductDTO> content = page.getContent()
                .stream().map(ProductMapper::toDTO).toList();
        return new PageResponseDTO<>(content, page.getNumber(),
                page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}