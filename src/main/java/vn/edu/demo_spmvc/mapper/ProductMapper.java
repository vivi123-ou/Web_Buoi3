package vn.edu.demo_spmvc.mapper;

import vn.edu.demo_spmvc.dto.ProductDTO;
import vn.edu.demo_spmvc.entity.Product;

public class ProductMapper {
    public static ProductDTO toDTO(Product p) {
        return new ProductDTO(p.getId(), p.getName(), p.getPrice(), p.getQuantity());
    }

    public static Product toEntity(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }
}