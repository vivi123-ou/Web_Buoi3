package vn.edu.demo_spmvc.service;

import vn.edu.demo_spmvc.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {
    CustomerDTO create(CustomerDTO dto);
    List<CustomerDTO> getAll();
}