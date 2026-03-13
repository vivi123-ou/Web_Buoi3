package vn.edu.demo_spmvc.mapper;
//cách chuyển đổi giữa Enitity và DTO

import vn.edu.demo_spmvc.dto.ProductDTO;
import vn.edu.demo_spmvc.entity.Product;

public class ProductMapper {
    public static ProductDTO toDTO(Product product){
        return new ProductDTO(product.getId(), product.getName(),product.getPrice(), product.getQuantity());
    }
    public static Product toEntity(ProductDTO dto){
        return Product.builder().id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }
}
