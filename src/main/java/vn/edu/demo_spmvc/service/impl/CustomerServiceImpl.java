package vn.edu.demo_spmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.demo_spmvc.dto.CustomerDTO;
import vn.edu.demo_spmvc.entity.Customer;
import vn.edu.demo_spmvc.mapper.CustomerMapper;
import vn.edu.demo_spmvc.repository.CustomerRepository;
import vn.edu.demo_spmvc.service.CustomerService;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO create(CustomerDTO dto) {
        Customer customer = CustomerMapper.toEntity(dto);
        return CustomerMapper.toDTO(customerRepository.save(customer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAll() {
        return customerRepository.findAll()
                .stream().map(CustomerMapper::toDTO).toList();
    }
}