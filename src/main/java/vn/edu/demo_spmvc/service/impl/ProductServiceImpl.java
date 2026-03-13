package vn.edu.demo_spmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.demo_spmvc.dto.ProductDTO;
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
        return repository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
