package vn.edu.demo_spmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.demo_spmvc.dto.PageResponseDTO;
import vn.edu.demo_spmvc.dto.ProductDTO;
import vn.edu.demo_spmvc.dto.ProductSearchDTO;
import vn.edu.demo_spmvc.entity.Product;
import vn.edu.demo_spmvc.mapper.ProductMapper;
import vn.edu.demo_spmvc.repository.OrderDetailRepository;
import vn.edu.demo_spmvc.repository.ProductRepository;
import vn.edu.demo_spmvc.service.ProductService;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public ProductDTO create(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm id: " + id));
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        return productRepository.findByDeletedFalse()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm id: " + id));

        // Không được xóa nếu đã có trong đơn hàng
        if (orderDetailRepository.existsByProductId(id)) {
            throw new RuntimeException("Không thể xóa sản phẩm đã có trong đơn hàng!");
        }

        // Soft delete
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<ProductDTO> search(ProductSearchDTO c, Pageable pageable) {
        Page<Product> page = productRepository.search(
                c.getName(), c.getMinPrice(), c.getMaxPrice(), c.getInStock(), pageable);
        List<ProductDTO> content = page.getContent()
                .stream().map(ProductMapper::toDTO).toList();
        return new PageResponseDTO<>(content, page.getNumber(),
                page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}