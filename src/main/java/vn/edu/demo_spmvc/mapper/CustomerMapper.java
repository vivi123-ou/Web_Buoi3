package vn.edu.demo_spmvc.mapper;

import vn.edu.demo_spmvc.dto.CustomerDTO;
import vn.edu.demo_spmvc.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO toDTO(Customer c) {
        return new CustomerDTO(c.getId(), c.getName(), c.getEmail(), c.getPhone());
    }

    public static Customer toEntity(CustomerDTO dto) {
        return Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
    }
}